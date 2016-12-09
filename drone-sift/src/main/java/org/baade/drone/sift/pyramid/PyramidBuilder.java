package org.baade.drone.sift.pyramid;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.baade.drone.sift.Const;
import org.baade.drone.sift.convert.GaussConverter;
import org.baade.drone.sift.image.IImage;
import org.baade.drone.sift.image.Image;
import org.baade.drone.sift.pyramid.dog.DogOctave;
import org.baade.drone.sift.pyramid.dog.DogPyramid;
import org.baade.drone.sift.pyramid.gauss.GaussOctave;
import org.baade.drone.sift.pyramid.gauss.GaussPyramid;

public class PyramidBuilder implements IPyramidBuilder {

	private GaussConverter gaussConverter;

	public PyramidBuilder() {
		gaussConverter = new GaussConverter();
	}

	@Override
	public IPyramid buildGaussPyramid(IImage src) {
		IPyramid pyramid = null;
		if (src != null) {
			int octaveCount = getOctaveCount(src.getWidth(), src.getHeight());
			pyramid = new GaussPyramid(octaveCount, src);

			int gaussLevelCount = Const.GAUSS_LEVEL_COUNT + 3;

			IImage currertImg = src;
			for (int oIndex = 0; oIndex < octaveCount; oIndex++) {
				IOctave octave = new GaussOctave(gaussLevelCount, oIndex);
				for (int lIndex = 0; lIndex < gaussLevelCount; lIndex++) {
					if (oIndex == 0 && lIndex == 0) {
						octave.addImage(lIndex, currertImg);
					} else if (lIndex == 0) {
						IOctave upOctave = pyramid.getOctave(oIndex - 1);
						IImage upLastImg = upOctave.getImage(gaussLevelCount - 1);// 上一层的最后一张图片
						int width = upLastImg.getWidth();
						int height = upLastImg.getHeight();
						int currentWidth = width / 2; // 缩小一倍
						int currentHeight = height / 2;
						IImage tmpImg = new Image(currentWidth, currentHeight, 0);
						for (int x = 0; x < currentWidth; x++) {
							for (int y = 0; y < currentHeight; y++) {
								int upX = 2 * x;
								int upY = 2 * y;
								if (upX < width && upY < height) {
									tmpImg.setRGB(x, y, upLastImg.getRGB(upX, upY));
								}
							}
						}
						currertImg = gaussConverter.convert(tmpImg, oIndex, lIndex);
						octave.addImage(lIndex, currertImg);
					} else {
						currertImg = gaussConverter.convert(currertImg, oIndex, lIndex);
						octave.addImage(lIndex, currertImg);
					}
				}

				pyramid.addOctave(oIndex, octave);
			}
		}
		return pyramid;
	}

	@Override
	public IPyramid buildGaussPyramid(String imgPath) {
		BufferedImage buffImg = null;
		try {
			buffImg = ImageIO.read(new File(imgPath));
		} catch (IOException e) {
			e.printStackTrace();
		}
		IPyramid pyramid = null;
		if (buffImg != null) {
			IImage src = new Image(buffImg);
			pyramid = buildGaussPyramid(src);
		}
		return pyramid;
	}

	private int getOctaveCount(int srcWidth, int srcHeigth) {
		int minSize = srcWidth >= srcHeigth ? srcHeigth : srcWidth;
		minSize *= 2;// 扩大两倍
		double counts = minSize * 1.0 / Const.IMG_MIN_WIDTH_OR_HEIGHT_SIZE;

		int oc = (int) (Math.log(counts) / Math.log(2));

		return oc;
	}

	@Override
	public IPyramid buildDogPyramid(IPyramid gaussPyramid) {
		int octaveCount = gaussPyramid.getCount();
		IPyramid dogPyramid = new DogPyramid(octaveCount);
		int gaussLevelCount = Const.GAUSS_LEVEL_COUNT + 3;
		for (int i = 0; i < octaveCount; i++) {
			IOctave gaussOctave = gaussPyramid.getOctave(i);
			IOctave dogOctave = new DogOctave(Const.GAUSS_LEVEL_COUNT + 2, i);
			for (int j = 1; j < gaussLevelCount; j++) {
				IImage currImg = gaussOctave.getImage(j);
				IImage upImg = gaussOctave.getImage(j - 1);
				int width = currImg.getWidth();
				int height = currImg.getHeight();
				IImage dogImg = new Image(width, height, j - 1);
				for (int x = 0; x < width; x++) {
					for (int y = 0; y < height; y++) {
						int rgb = currImg.getRGB(x, y) - upImg.getRGB(x, y);
						dogImg.setRGB(x, y, rgb);
					}
				}
				dogOctave.addImage(j - 1, dogImg);
			}
			dogPyramid.addOctave(i, dogOctave);
		}

		return dogPyramid;
	}

}
