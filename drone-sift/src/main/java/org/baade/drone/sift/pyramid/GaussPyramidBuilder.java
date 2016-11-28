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
import org.baade.drone.sift.gauss.buff.ImageGaussBuff;
import org.baade.drone.sift.img.IImage;
import org.baade.drone.sift.img.Image;
import org.baade.drone.sift.utils.MathUtils;

public class GaussPyramidBuilder implements IGaussPyramidBuilder {

	private IImageConverter grayConverter;
	private IImageGaussConverter gaussConverter;
	private IImageZoomConverter zoomConverter;

	public GaussPyramidBuilder() {
		grayConverter = new ImageGrayConverter();
		gaussConverter = new ImageGaussConverter();
		zoomConverter = new ImageZoomConverter();
	}

	@Override
	public IGaussPyramid build(IImage src) {
		IGaussPyramid gaussPyramid = new GaussPyramid(src);

		IImage doubleImg = zoomConverter.convert(src);
//		doubleImg = grayConverter.convert(doubleImg, doubleImg);
		int gaussLevelCount = Const.GAUSS_LEVEL_COUNT + 3;

		int index = 0;
		
		IImage imgTemp = doubleImg;
		int currentMinSize = getMinSize(imgTemp);
		while (true) {
			currentMinSize = getMinSize(imgTemp);
			if(currentMinSize <= Const.IMG_MIN_WIDTH_OR_HEIGHT_SIZE){
				break;
			}
			
			IPyramidOctave pyramidOctave = new PyramidOctave();
			
			for (int i = 0; i < gaussLevelCount; i++) {
				double sigma = MathUtils.getSigma(index, i);
				IGaussTemplate gaussTemp = gaussConverter.buildTemplate(sigma);
//				IImage distImg = new Image(imgTemp.getBufImg());
				
				IImage distImg = new ImageGaussBuff(gaussTemp, imgTemp.getBufImg());
				
				gaussConverter.buildGaussImg(imgTemp, distImg, sigma);
				pyramidOctave.put(i, distImg);

//				imgTemp = distImg;
				System.out.println(distImg);
			}
			gaussPyramid.put(index++, pyramidOctave);
			
			imgTemp = zoomConverter.down(imgTemp);
		}

		return gaussPyramid;
	}

	private int getMinSize(IImage img) {
		int width = img.getWidth();
		int height = img.getHeight();
		return width >= height ? height : width;
	}

	@Override
	public IGaussPyramid build(String imgPath) {
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
