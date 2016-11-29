package org.baade.drone.sift.converter;

import java.awt.image.BufferedImage;

import org.baade.drone.sift.Const;
import org.baade.drone.sift.img.IImage;
import org.baade.drone.sift.img.Image;

public class ImageZoomConverter implements IImageZoomConverter {

	@Override
	public IImage convert(IImage src, IImage dist) {
		return this.convert(src, dist, Const.IMG_ZOOM_DEFAULT_TIMES);
	}

	@Override
	public IImage convert(IImage src, IImage dist, double times) {

		//
		// int width = (int) (src.getWidth() * times);
		// int height = (int) (src.getHeight() * times);
		// BufferedImage newBufImg = new BufferedImage(width, height,
		// src.getBufImg().getType());
		// Graphics g = newBufImg.getGraphics();
		// g.drawImage(src.getBufImg(), 0, 0, width, height, null);
		// g.dispose();
		//
		// IImage zoomedImg = null;
		// if (dist == null) {
		// zoomedImg = new Image(newBufImg);
		// } else {
		// dist.init(newBufImg);
		// }
		// return zoomedImg;

		int srcWidth = src.getWidth();
		int srcHeight = src.getHeight();

		int distWidth = (int) (srcWidth * times);
		int distHeight = (int) (srcHeight * times);

		BufferedImage newBufImg = new BufferedImage(distWidth, distHeight, src.getBuffImg().getType());

		double tw = srcWidth * 1.0 /distWidth;
		double th = srcHeight * 1.0 /distHeight;
		for (int x = 0; x < distWidth; x++) {
			for (int y = 0; y < distHeight; y++) {
				int srcx = (int)(x * tw);
				int srcy = (int)(y * th);
				newBufImg.setRGB(x, y, src.getRGB(srcx, srcy));
			}
		}
		IImage zoomedImg = null;
		if (dist == null) {
			zoomedImg = new Image(newBufImg);
		} 
		return zoomedImg;
	}

	@Override
	public IImage convert(IImage src, double times) {
		return this.convert(src, null, times);
	}

	@Override
	public IImage convert(IImage src) {
		return this.convert(src, null);
	}

	@Override
	public IImage down(IImage src) {
		BufferedImage bufImg = src.getBuffImg();
		int width = src.getWidth();
		int height = src.getHeight();

		BufferedImage distBufImg = new BufferedImage(width / 2, height / 2, bufImg.getType());

		for (int i = 1, x = 0; i < width; i += 2, x++) {
			for (int j = 1, y = 0; j < height; j += 2, y++) {
				distBufImg.setRGB(x, y, src.getRGB(i, j));
			}
		}
		IImage distImg = new Image(distBufImg);
		return distImg;
	}

}
