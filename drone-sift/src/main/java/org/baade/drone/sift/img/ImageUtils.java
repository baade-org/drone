package org.baade.drone.sift.img;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageUtils {

	public static BufferedImage transformGray(String imgPath) throws IOException {
		return transformGray(imgPath, false);
	}

	public static BufferedImage transformGray(String imgPath, boolean isWeighting) throws IOException {
		BufferedImage grayImage = null;
		if (isWeighting) {
			grayImage = transformGrayWeighting(imgPath);
		} else {
			grayImage = transformGrayCommon(imgPath);
		}
		return grayImage;
	}

	private static BufferedImage transformGrayCommon(String imgPath) throws IOException {
		BufferedImage image = ImageIO.read(new File(imgPath));
		int width = image.getWidth();
		int height = image.getHeight();
		BufferedImage grayImage = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				int rgb = image.getRGB(i, j);
				grayImage.setRGB(i, j, rgb);
			}
		}
		return grayImage;

	}

	private static BufferedImage transformGrayWeighting(String imgPath) throws IOException {
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

	public static void write(BufferedImage bufferedImage, String outFilePath) throws IOException {
		write(bufferedImage, new File(outFilePath));
	}

	public static void write(BufferedImage bufferedImage, File outFile) throws IOException {
		write(bufferedImage, "jpg", outFile);
	}

	public static void write(BufferedImage bufferedImage, String fileFormat, String outFilePath) throws IOException {
		write(bufferedImage, fileFormat, new File(outFilePath));
	}

	public static void write(BufferedImage bufferedImage, String fileFormat, File outFile) throws IOException {
		ImageIO.write(bufferedImage, fileFormat, outFile);
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

	public static BufferedImage zoomInDoubleImage(BufferedImage originalImage) {
		return zoomInImage(originalImage, 2);
	}

	public static BufferedImage zoomInDoubleImage(String imgPath) throws IOException {
		return zoomInDoubleImage(getBufferedImage(imgPath));
	}

	private static BufferedImage getBufferedImage(String imgPath) throws IOException {
		return ImageIO.read(new File(imgPath));
	}
}
