package org.baade.drone.sift.gauss;

import org.baade.drone.sift.img.IImage;
import org.baade.drone.sift.img.Image;
import org.baade.drone.sift.utils.MathUtils;

public class GaussBuilder {

	public static IGaussTemplate buildTemplate(double sigma) {
		int dimension = 2 * (int)Math.round(sigma) + 1; // 保证高斯模板是奇数
		// int dimension = 2;
		// IGaussTemplate gaussTemp = new GaussTemplate();
		IGaussTemplate gaussTemp = new GaussArrayTemplate(dimension);
		double totalWeightValue = 0.0;
		for (int x = 0; x < dimension; x++) {
			for (int y = 0; y < dimension; y++) {
				// double weightValue = getWeightValue(x, y, sigma);
				double weightValue = MathUtils.gaussian(x, y, sigma);

				totalWeightValue += weightValue;
				gaussTemp.setWeightValue(x, y, weightValue);
			}
		}
		// 加权平均，使之和等于1
//		for (int x = 0; x < dimension; x++) {
//			for (int y = 0; y < dimension; y++) {
//				double currentValue = gaussTemp.getWeightValue(x, y);
//				double changedValue = currentValue / totalWeightValue;
//				gaussTemp.setWeightValue(x, y, changedValue);
//			}
//		}
		return gaussTemp;
	}

	public static IImage buildImg(IGaussTemplate gaussTemp, IImage sourceImage) {
		IImage targetImage = new Image(sourceImage.getBufImg());

		int width = sourceImage.getWidth();
		int height = sourceImage.getHeight();
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				int rgb = gaussRGB(x, y, gaussTemp, sourceImage);

				targetImage.setRGB(x, y, rgb);
			}
		}
		return targetImage;
	}


	private static int gaussRGB(int x, int y, IGaussTemplate gaussTemp, IImage sourceImage) {
		int radius = gaussTemp.getRadius();
		int width = sourceImage.getWidth();
		int height = sourceImage.getHeight();

		int startX = x - radius;
		// startX = startX > 0 ? startX : x;
		int startY = y - radius;
		// startY = startY > 0 ? startY : y;

		int endX = x + radius;
		// endX = endX > width ? width : endX;
		int endY = y + radius;
		// endY = endY > height ? height : endY;

		// if(startX < 0 || startY < 0 || endX > width || endY > height){
		// return sourceImage.getRGB(x, y);
		// }

		double rd = 0.0;
		double gd = 0.0;
		double bd = 0.0;

		for (int i = startX; i < endX; i++) {
			for (int j = startY; j < endY; j++) {
				int ci = i;
				int cj = j;
				if(i < 0){
					ci = -1*i;
				}
				if(i >= width){
					ci = width -(i - width)- 1;
				}
				
				if(j < 0){
					cj = -1*j;
				}
				if(j >= height){
					cj = height - (j - height)- 1;
				}

				int currX = x - ci;
				int currY = y - cj;
				double weightValue = gaussTemp.getWeightValue(currX, currY);

				int currRGB = sourceImage.getRGB(ci, cj);
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

		return r << 16 | g << 8 | b;
	}
}
