package org.baade.drone.sift.img;

import java.awt.image.BufferedImage;
import java.io.IOException;

public class Image implements IImage {

	private BufferedImage bufImg;
	private int width;
	private int height;

	public Image(BufferedImage bufImg) {
		if (bufImg != null) {
			this.width = bufImg.getWidth();
			this.height = bufImg.getHeight();
			this.bufImg = bufImg;
		}
	}

	@Override
	public BufferedImage getBuffImg() {
		return this.bufImg;
	}

	@Override
	public int getWidth() {
		return this.width;
	}

	@Override
	public int getHeight() {
		return this.height;
	}


	@Override
	public int getRGB(int x, int y) {
		// return datas[x][y];
		return this.bufImg.getRGB(x, y);
	}

	@Override
	public void setRGB(int x, int y, int rgb) {
		// datas[x][y] = rgb;
		this.bufImg.setRGB(x, y, rgb);
	}

	@Override
	public void wirte(String outDir, int octaveNum, int gaussLevel) {
		// BufferedImage targetImage = new BufferedImage(width, height,
		// BufferedImage.TYPE_INT_RGB);
		// for (int x = 0; x < this.width; x++) {
		// for (int y = 0; y < this.height; y++) {
		// int rgb = datas[x][y];
		// // rgb = (rgb << 16) | (rgb << 8) | rgb;
		//
		// // rgb = (rgb << 16);
		// targetImage.setRGB(x, y, rgb);
		// }
		// }
		String name = octaveNum + "_" + gaussLevel + ".jpg";
		String outFile = outDir + "/" + name;
		try {
			ImageUtils.write(this.bufImg, outFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


}
