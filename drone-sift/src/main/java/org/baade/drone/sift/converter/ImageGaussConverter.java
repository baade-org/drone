package org.baade.drone.sift.converter;

import org.baade.drone.sift.gauss.GaussTemplate;
import org.baade.drone.sift.gauss.IGaussTemplate;
import org.baade.drone.sift.img.IImage;
import org.baade.drone.sift.img.IImageDog;
import org.baade.drone.sift.img.IImageGauss;
import org.baade.drone.sift.img.ImageDog;
import org.baade.drone.sift.pyramid.BigPyramid;
import org.baade.drone.sift.pyramid.dog.DogOctave;
import org.baade.drone.sift.pyramid.dog.DogPyramid;
import org.baade.drone.sift.pyramid.dog.IDogOctave;
import org.baade.drone.sift.pyramid.dog.IDogPyramid;
import org.baade.drone.sift.pyramid.gauss.IGaussOctave;
import org.baade.drone.sift.pyramid.gauss.IGaussPyramid;
import org.baade.drone.sift.utils.MathUtils;

public class ImageGaussConverter implements IImageGaussConverter {

	@Override
	public IImage convert(IImage src, IImage dist) {
		return null;
	}

	@Override
	public IGaussTemplate buildTemplate(double sigma) {
		// int dimension = 2 * (int)Math.round(sigma) + 1; // 保证高斯模板是奇数
//		int dimension = 3; // 保证高斯模板是奇数
		int radius = 1;

		IGaussTemplate gaussTemp = new GaussTemplate(radius);
		double totalWeightValue = 0.0;

		for (int x = -radius; x <= radius; x++) {
			for (int y = -radius; y <= radius; y++) {
				// double weightValue = getWeightValue(x, y, sigma);
				double weightValue = MathUtils.gaussian(x, y, sigma);

				totalWeightValue += weightValue;
				gaussTemp.setWeightValue(x, y, weightValue);
			}
		}

		for (int x = -radius; x <= radius; x++) {
			for (int y = -radius; y <= radius; y++) {
				double currentValue = gaussTemp.getWeightValue(x, y);
				double changedValue = currentValue / totalWeightValue;
				gaussTemp.setWeightValue(x, y, changedValue);
			}
		}
		return gaussTemp;
	}


//	@Override
//	public IImage buildGaussImg(IImage src, IImage dist, double sigma) {
//		IGaussTemplate gaussTemp = buildTemplate(sigma);
//		return buildGaussImg(gaussTemp, src, dist);
//	}

	@Override
	public BigPyramid buildGaussPyramid(IGaussPyramid gaussPyramid) {
		int octaveCount = gaussPyramid.getOctaveCount();
		IDogPyramid dogPyramid = new DogPyramid();
//		System.out.println(gaussPyramid);
		for (int i = 0; i < octaveCount; i++) {
			IGaussOctave gaussOctave = (IGaussOctave) gaussPyramid.get(i);
			
			IDogOctave dogOctave = new DogOctave(gaussOctave.getWidth(), gaussOctave.getHeight());
			
			buildGaussOctave(gaussOctave, dogOctave);
			
			dogPyramid.add(i, dogOctave);
		}
		return new BigPyramid(gaussPyramid, dogPyramid);
	}

	private void buildGaussOctave(IGaussOctave gaussOctave, IDogOctave dogOctave) {
		int count = gaussOctave.getImgCount();
		IImage srcImg = gaussOctave.getSrcImg();
		int width = srcImg.getWidth();
		int height = srcImg.getHeight();
		System.out.println(gaussOctave);
		for (int i = 0; i < count; i++) {// 每个高斯图片
			IImageGauss imgGauss = (IImageGauss)gaussOctave.get(i);
			IImageDog imgDog = null;
			if(i > 0){
				imgDog = new ImageDog(width, height);
				dogOctave.add(i - 1, imgDog);
			}
			
			for (int x = 0; x < width; x++) {
				for (int y = 0; y < height; y++) {
					int rgb = calcRGB(imgGauss, srcImg, x, y);
					imgGauss.setRGB(x, y, rgb);
					if(imgDog != null){
						int dogRGB = rgb - gaussOctave.get(i - 1).getRGB(x, y);
						imgDog.setRGB(x, y, dogRGB);
					}
				}
			}
		}
	}

	private int calcRGB(IImageGauss imgGauss, IImage srcImg, int x, int y) {
		// System.out.println(imgGauss + " --- " + x + ":" + y);
		IGaussTemplate gaussTemp = imgGauss.getTemplate();
		int width = imgGauss.getWidth();
		int height = imgGauss.getHeight();

		int radius = gaussTemp.getRadius();

		int startX = x - radius;
		int startY = y - radius;

		int endX = x + radius;
		int endY = y + radius;

		double rd = 0.0;
		double gd = 0.0;
		double bd = 0.0;

		for (int i = startX; i <= endX; i++) {
			for (int j = startY; j <= endY; j++) {
				int currX = getIndex(i, width);
				int currY = getIndex(j, height);

				int xDistance = x - currX;
				int yDistance = y - currY;
				double weightValue = gaussTemp.getWeightValue(xDistance, yDistance);

				int currRGB = srcImg.getRGB(currX, currY);

				int r = currRGB >> 16 & 0xFF;
				int g = currRGB >> 8 & 0xFF;
				int b = currRGB & 0xFF;

				rd += r * weightValue;
				gd += g * weightValue;
				bd += b * weightValue;
			}
		}
		int r = (int) (rd);
		int g = (int) (gd);
		int b = (int) (bd);
		int rgb = r << 16 | g << 8 | b;
//		imgGauss.setRGB(x, y, rgb);
		
		return rgb;
	}

	private int getIndex(int index, int maxIndex) {
		if (index < 0) {
			index = -1 * index;
		}
		if (index >= maxIndex) {
			index = maxIndex - (index - maxIndex) - 1;
		}
		return index;
	}
}
