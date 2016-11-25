package org.baade.drone.sift.img;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.baade.drone.sift.Const;
import org.baade.drone.sift.gauss.GaussBuilder;
import org.baade.drone.sift.gauss.IGaussTemplate;
import org.baade.drone.sift.pyramid.GaussPyramid;
import org.baade.drone.sift.pyramid.IGaussPyramid;
import org.baade.drone.sift.pyramid.IPyramidOctave;
import org.baade.drone.sift.pyramid.PyramidOctave;
import org.baade.drone.sift.utils.MathUtils;

public class ImageTransformer implements IImageTransformer {

	@Override
	public IGaussPyramid toGray(String imgPath, boolean isWeighting) throws IOException {
		BufferedImage bufImg = ImageIO.read(new File(imgPath));
		IImage sourceImage = new Image(bufImg);
		IGaussPyramid gaussPyramid = buildPyramid(sourceImage);
		if (!isWeighting) {

		}

		return gaussPyramid;
	}

	private IGaussPyramid buildPyramid(IImage sourceImage) {
		try {
			ImageUtils.write(sourceImage.getBufImg(), "d:/jjjj_01.jpg");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		IImage grayImg = sourceImage;
//		try {
//			grayImg = transformGrayWeighting(sourceImage);
//			ImageUtils.write(grayImg.getBufImg(), "d:/jjjj.jpg");
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
		
		IGaussPyramid gaussPyramid = new GaussPyramid(grayImg);
		// IImage doubleImg = zoomInImage(sourceImage, 2);// 先放大两倍
//		int octaveCount = Const.OCTAVE_COUNT;
		int octaveCount = 0;
		int gaussLevelCount = Const.GAUSS_LEVEL_COUNT;
		
		IImage doubleImg = zoomInImage(grayImg, 2);
		
		IPyramidOctave firstPyramidOctave = buildFirstPyramidOctave(doubleImg);
		
		gaussPyramid.put(0, firstPyramidOctave);
//		while(true){
//			if(octaveCount == 5){
//				break;
//			}
//			IPyramidOctave pyramidOctave = buildPyramidOctave(doubleImg, octaveCount);
//			
//			for (int i = 0; i < gaussLevelCount; i++) {
//				double sigma = MathUtils.getSigma(octaveCount, i);
//				IImage cuurnetLevelImg = buildLevelImg(doubleImg, sigma);
//				pyramidOctave.put(i, cuurnetLevelImg);
//				
//			}
//			gaussPyramid.put(octaveCount, pyramidOctave);
//			
//			octaveCount++;
//		}
		

//		for (int i = 0; i < octaveCount; i++) {
//			// double timer = Math.pow(2, i);
//			double timer = 1.0 / Math.pow(2, i - 1);
////			double timer = 1.0 / Math.pow(2, i);
//			IImage timerImg = zoomInImage(grayImg, timer);
//			IPyramidOctave pyramidOctave = buildPyramidOctave(timerImg, i);
//
//			gaussPyramid.put(i, pyramidOctave);
//		}

		return gaussPyramid;
	}
	
	private IPyramidOctave buildFirstPyramidOctave(IImage sourceImage) {
		IPyramidOctave pyramidOctave = new PyramidOctave();
		IImage sourceImageTemp = sourceImage;
		int[][] datas = sourceImage.getDatas();
		int width = sourceImage.getWidth();
		int height = sourceImage.getHeight();
		
		int gaussLevelCount = Const.GAUSS_LEVEL_COUNT + 3;
		for (int i = 0; i < gaussLevelCount; i++) {
			double sigma = MathUtils.getSigma(0, i);
			IImage img = buildLevelImg(sourceImageTemp, sigma);
			pyramidOctave.put(i, img);
			
//			IImage cuurnetLevelImg = buildLevelImg(sourceImageTemp, sigma);
//			pyramidOctave.put(i, cuurnetLevelImg);
//			System.out.println(cuurnetLevelImg.getBufImg());
//			sourceImageTemp = new Image(img.getBufImg()) ;
			
		}
		return pyramidOctave;
	}

	private IPyramidOctave buildPyramidOctave(IImage sourceImage, int octaveNum) {
		IPyramidOctave pyramidOctave = new PyramidOctave();
		int gaussLevelCount = Const.GAUSS_LEVEL_COUNT + 3;
		IImage sourceImageTemp = sourceImage;
		for (int i = 0; i < gaussLevelCount; i++) {
//			double sigma = getSigma(octaveNum, i);
			double sigma = MathUtils.getSigma(octaveNum, i);
			IImage cuurnetLevelImg = buildLevelImg(sourceImageTemp, sigma);
			pyramidOctave.put(i, cuurnetLevelImg);
			
			sourceImageTemp = new Image(cuurnetLevelImg.getBufImg()) ;
			
//			sourceImageTemp = new Image(cuurnetLevelImg.getWidth(), cuurnetLevelImg.getHeight()) ;
		}
		return pyramidOctave;
	}

	private IImage buildLevelImg(IImage sourceImage, double sigma) {
//		int radius = (int)(6 * sigma + 1);
		IGaussTemplate gaussTemp = GaussBuilder.buildTemplate(sigma);
		
		IImage levelImg = GaussBuilder.buildImg(gaussTemp, sourceImage);

//		IImage levelImg = new Image(sourceImage.getBufImg());
//		int width = levelImg.getWidth();
//		int height = levelImg.getHeight();
//		for (int x = 0; x < width; x++) {
//			for (int y = 0; y < height; y++) {
//				// double g = getG(x, y, sigma);
//
//				// double g = getG(x, y, sigma, width, height);
//				// double rgb = sourceImage.getRGB(x, y);
//				// double guassRGB = g * rgb;
//				// double guassRGB = sigma * rgb;
//
//				int guassRGB = calcRGB(gaussTemp, radius, x, y, width, height, sourceImage);
//				levelImg.setRGB(x, y, guassRGB);
//			}
//		}
		return levelImg;
	}

	private int calcRGB(IGaussTemplate gaussTemp, int radius, int x, int y, int width, int height,
			IImage sourceImage) {
		int startX = x - radius;
//		startX = startX > 0 ? startX : x;
		int startY = y - radius;
//		startY = startY > 0 ? startY : y;

		int endX = x + radius;
//		endX = endX > width ? width : endX;
		int endY = y + radius;
//		endY = endY > height ? height : endY;
		
//		if(startX < 0 || startY < 0 || endX >  width || endY > height){
//			return sourceImage.getRGB(x, y);
//		}

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
				
//				rd += r * 0.5;
//				gd += g * 0.5;
//				bd += b * 0.5;
				
				
			}
		}

		int r = (int)(rd);
		int g = (int)(gd);
		int b = (int)(bd);
		
//		int r = (int)Math.round(rd);
//		int g = (int)Math.round(gd);
//		int b = (int)Math.round(bd);
		return r << 16 | g << 8 | b;
//		return r << 16 | b;
//		return (int)rgb;
	}

//	private int cla(int currRGB, double weightValue) {
//		int r = currRGB >> 16;
//		int g = currRGB >> 8 & 0xFF;
//		int b = currRGB & 0xFF;
//		
//		r = (int)Math.round(r * weightValue);
//		g = (int)Math.round(g * weightValue);
//		b = (int)Math.round(b * weightValue);
//
//		return r << 16 | g << 8 | b;
//	}

	private double getK() {
		return Math.pow(2, 1.0 / Const.GAUSS_LEVEL_COUNT);
	}

	private double getSigma(int octaveNum, int levelNum) {
		octaveNum += 1;
		double k = getK();
		double sigma = octaveNum * Math.pow(k, levelNum) * Const.SIGMA_BASE;
		return sigma;
	}

	private double getG(int x, int y, double sigma) {
		double a = 1.0 / (2 * Math.PI * Math.pow(sigma, 2));
		double b = (Math.pow(x, 2) + Math.pow(y, 2)) / (2 * Math.pow(sigma, 2));
		double c = Math.pow(Math.E, b);

		return a * c;
	}

	private double getG(int x, int y, double sigma, int width, int height) {
		double a = 1.0 / (2 * Math.PI * Math.pow(sigma, 2));

		// double b = Math.pow((x-width/2.0), 2) + Math.pow((x-height/2.0), 2);

		double b = Math.pow(3, 2);
		double c = -b / (2 * Math.pow(sigma, 2));
		double d = Math.pow(Math.E, c);

		return a * d;
	}

	private BufferedImage transformGrayCommon(IImage image) throws IOException {
		int width = image.getWidth();
		int height = image.getHeight();
		BufferedImage grayImage = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				int rgb = image.getBufImg().getRGB(i, j);
				grayImage.setRGB(i, j, rgb);
			}
		}
		return grayImage;

	}

	private BufferedImage transformGrayWeighting(String imgPath) throws IOException {
		BufferedImage bufferedImage = ImageIO.read(new File(imgPath));
		BufferedImage grayImage = new BufferedImage(bufferedImage.getWidth(), bufferedImage.getHeight(),
				bufferedImage.getType());
		for (int i = 0; i < bufferedImage.getWidth(); i++) {
			for (int j = 0; j < bufferedImage.getHeight(); j++) {
				final int color = bufferedImage.getRGB(i, j);
				final int r = (color >> 16) & 0xff;
				final int g = (color >> 8) & 0xff;
				final int b = color & 0xff;
				int gray = (int) (0.3 * r + 0.59 * g + 0.11 * b);
				int newPixel = color2Gray(255, gray, gray, gray);
				grayImage.setRGB(i, j, newPixel);
			}
		}
		return grayImage;
	}
	
	private IImage transformGrayWeighting(IImage sourceImage) throws IOException {
		BufferedImage bufferedImage = sourceImage.getBufImg();
		BufferedImage grayBufImage = new BufferedImage(bufferedImage.getWidth(), bufferedImage.getHeight(),
				bufferedImage.getType());
		for (int i = 0; i < bufferedImage.getWidth(); i++) {
			for (int j = 0; j < bufferedImage.getHeight(); j++) {
				final int color = bufferedImage.getRGB(i, j);
				final int r = (color >> 16) & 0xff;
				final int g = (color >> 8) & 0xff;
				final int b = color & 0xff;
				int gray = (int) (0.3 * r + 0.59 * g + 0.11 * b);
				int newPixel = color2Gray(255, gray, gray, gray);
				grayBufImage.setRGB(i, j, newPixel);
			}
		}
		IImage grayImage = new Image(grayBufImage);
		return grayImage;
	}

	private static int color2Gray(int alpha, int red, int green, int blue) {
		int newPixel = 0;
		newPixel += alpha;
		newPixel = newPixel << 8;
		newPixel += red;
		newPixel = newPixel << 8;
		newPixel += green;
		newPixel = newPixel << 8;
		newPixel += blue;
		return newPixel;

	}

	public static BufferedImage zoomInImage(BufferedImage originalImage, Integer times) {
		int width = originalImage.getWidth() * times;
		int height = originalImage.getHeight() * times;
		BufferedImage newImage = new BufferedImage(width, height, originalImage.getType());
		Graphics g = newImage.getGraphics();
		g.drawImage(originalImage, 0, 0, width, height, null);
		g.dispose();
		return newImage;

	}

	public IImage zoomInImage(IImage image, double times) {
		int width = (int) (image.getWidth() * times);
		int height = (int) (image.getHeight() * times);
		BufferedImage newImage = new BufferedImage(width, height, image.getBufImg().getType());
		Graphics g = newImage.getGraphics();
		g.drawImage(image.getBufImg(), 0, 0, width, height, null);
		g.dispose();

		IImage zoomedImg = new Image(newImage);
		return zoomedImg;

	}

}
