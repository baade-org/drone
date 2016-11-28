package org.baade.drone.sift.converter;

import org.baade.drone.sift.gauss.GaussTemplate;
import org.baade.drone.sift.gauss.IGaussTemplate;
import org.baade.drone.sift.gauss.buff.IImageGaussBuff;
import org.baade.drone.sift.gauss.buff.ImagePixelGaussBuff;
import org.baade.drone.sift.gauss.buff.RGBBuff;
import org.baade.drone.sift.img.IImage;
import org.baade.drone.sift.utils.MathUtils;

public class ImageGaussConverter implements IImageGaussConverter {

	@Override
	public IImage convert(IImage src, IImage dist) {
		return null;
	}

	@Override
	public IGaussTemplate buildTemplate(double sigma) {
//		int dimension = 2 * (int)Math.round(sigma) + 1; // 保证高斯模板是奇数
		int dimension = 3; // 保证高斯模板是奇数
		int radius = 2;
//		IGaussTemplate gaussTemp = new GaussArrayTemplate(dimension);
		
		IGaussTemplate gaussTemp = new GaussTemplate(radius);
		double totalWeightValue = 0.0;
//		for (int x = 0; x < dimension; x++) {
//			for (int y = 0; y < dimension; y++) {
//				// double weightValue = getWeightValue(x, y, sigma);
//				double weightValue = MathUtils.gaussian(x, y, sigma);
//
//				totalWeightValue += weightValue;
//				gaussTemp.setWeightValue(x, y, weightValue);
//			}
//		}
		
		for (int x = -radius; x <= radius; x++) {
			for (int y = -radius; y <= radius; y++) {
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
//				double changedValue = currentValue / totalWeightValue ;
//				gaussTemp.setWeightValue(x, y, changedValue);
//			}
//		}
//		
		double td = 0.0;
		for (int x = -radius; x <= radius; x++) {
			for (int y = -radius; y <= radius; y++) {
				double currentValue = gaussTemp.getWeightValue(x, y);
				double changedValue = currentValue / totalWeightValue ;
				td += changedValue;
				gaussTemp.setWeightValue(x, y, changedValue);
			}
		}
		System.out.println(td);
		return gaussTemp;
	}

	@Override
	public IImage buildGaussImg(IGaussTemplate gaussTemp, IImage src, IImage dist) {
		int width = src.getWidth();
		int height = src.getHeight();
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				int rgb = gaussRGB(x, y, gaussTemp, src);

				dist.setRGB(x, y, rgb);
			}
		}
		return dist;
	}
	
	private int gaussRGB(int x, int y, IGaussTemplate gaussTemp, IImage sourceImage) {
		IImageGaussBuff imgGaussBuff = (IImageGaussBuff)sourceImage;
		
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

		for (int i = startX; i <= endX; i++) {
			for (int j = startY; j <= endY; j++) {
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
				ImagePixelGaussBuff pgb = imgGaussBuff.getImagePixelGaussBuff(ci, cj);

				int currX = x - ci;
				int currY = y - cj;
				
				RGBBuff rgbBuff = pgb.getRGB(currX, currY);
				rd += rgbBuff.getR();
				gd += rgbBuff.getG();
				bd += rgbBuff.getB();
				
//				double weightValue = gaussTemp.getWeightValue(currX, currY);
//
//				int currRGB = 0;
//				try {
//					currRGB = sourceImage.getRGB(ci, cj);
//				} catch (Exception e) {
//					// TODO Auto-generated catch block
////					e.printStackTrace();
//				}
//				int r = currRGB >> 16 & 0xFF;
//				int g = currRGB >> 8 & 0xFF;
//				int b = currRGB & 0xFF;
//
//				rd += r * weightValue;
//				gd += g * weightValue;
//				bd += b * weightValue;

			}
		}

		int r = (int) (rd);
		int g = (int) (gd);
		int b = (int) (bd);
		if(x == 50){
			int old =sourceImage.getRGB(x, y);
			int oldr = old >> 16 & 0xFF;
			int oldg = old >> 8 & 0xFF;
			int oldb = old & 0xFF;
		}
		
		int rgb = r << 16 | g << 8 | b;
		return rgb;
	}

	@Override
	public IImage buildGaussImg(IImage src, IImage dist, double sigma) {
		IGaussTemplate gaussTemp = buildTemplate(sigma);
		return buildGaussImg(gaussTemp, src, dist);
	}
	

}
