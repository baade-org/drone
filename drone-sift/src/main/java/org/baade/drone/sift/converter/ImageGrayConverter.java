package org.baade.drone.sift.converter;

import java.awt.image.BufferedImage;

import org.baade.drone.sift.img.IImage;
import org.baade.drone.sift.img.Image;

public class ImageGrayConverter implements IImageConverter {

	@Override
	public IImage convert(IImage src, IImage dist) {
		BufferedImage bufferedImage = src.getBuffImg();
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
		IImage distImg = new Image(grayBufImage);
		return distImg;
	}
	
	private int color2Gray(int alpha, int red, int green, int blue) {
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

}
