package org.baade.drone.sift.pyramid;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.baade.drone.sift.Const;
import org.baade.drone.sift.converter.IImageConverter;
import org.baade.drone.sift.converter.IImageGaussConverter;
import org.baade.drone.sift.converter.IImageZoomConverter;
import org.baade.drone.sift.converter.ImageGaussConverter;
import org.baade.drone.sift.converter.ImageGrayConverter;
import org.baade.drone.sift.converter.ImageZoomConverter;
import org.baade.drone.sift.gauss.IGaussTemplate;
import org.baade.drone.sift.img.IImage;
import org.baade.drone.sift.img.IImageGauss;
import org.baade.drone.sift.img.Image;
import org.baade.drone.sift.img.ImageGauss;
import org.baade.drone.sift.pyramid.gauss.GaussOctave;
import org.baade.drone.sift.pyramid.gauss.GaussPyramid;
import org.baade.drone.sift.pyramid.gauss.IGaussOctave;
import org.baade.drone.sift.pyramid.gauss.IGaussPyramid;
import org.baade.drone.sift.utils.MathUtils;

public class PyramidBuilder implements IPyramidBuilder {

	private IImageConverter grayConverter;
	private IImageGaussConverter gaussConverter;
	private IImageZoomConverter zoomConverter;

	public PyramidBuilder() {
		grayConverter = new ImageGrayConverter();
		gaussConverter = new ImageGaussConverter();
		zoomConverter = new ImageZoomConverter();
	}

	@Override
	public BigPyramid build(IImage src) {
		IGaussPyramid gaussPyramid = new GaussPyramid(src);
//		IImage doubleImg = zoomConverter.convert(src);
//		doubleImg = grayConverter.convert(doubleImg, doubleImg);
//		IImage doubleImg = zoomConverter.convert(src);
		IImage doubleImg = grayConverter.convert(src, src);
		int gaussLevelCount = Const.GAUSS_LEVEL_COUNT + 3;

		int index = 0;
		
		IImage imgTemp = doubleImg;
		int currentMinSize = getMinSize(imgTemp);
		while (true) {
			IGaussOctave pyramidOctave = new GaussOctave(imgTemp);
			for (int i = 0; i < gaussLevelCount; i++) {
				double sigma = MathUtils.getSigma(index, i);
				IGaussTemplate gaussTemp = gaussConverter.buildTemplate(sigma);
				IImageGauss imgGauss = new ImageGauss(gaussTemp, imgTemp.getWidth(), imgTemp.getHeight());
				
				pyramidOctave.add(i, imgGauss);

			}
			gaussPyramid.add(index++, pyramidOctave);
			
			currentMinSize = getMinSize(imgTemp);
			if(currentMinSize <= Const.IMG_MIN_WIDTH_OR_HEIGHT_SIZE){
				break;
			}
			imgTemp = zoomConverter.down(imgTemp);
		}
		BigPyramid bp = gaussConverter.buildGaussPyramid(gaussPyramid);
		return bp;
	}

	private int getMinSize(IImage img) {
		int width = img.getWidth();
		int height = img.getHeight();
		return width >= height ? height : width;
	}

	@Override
	public BigPyramid build(String imgPath) {
		try {
			BufferedImage bufImg = ImageIO.read(new File(imgPath));
			IImage sourceImage = new Image(bufImg);
			return build(sourceImage);
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
}
