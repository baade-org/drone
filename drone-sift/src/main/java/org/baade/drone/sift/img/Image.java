package org.baade.drone.sift.img;

import java.awt.image.BufferedImage;
import java.io.IOException;

public class Image implements IImage {

	private BufferedImage bufImg;
	private int width;
	private int height;

	private int[][] datas;

	public Image(BufferedImage bufImg) {
		if (bufImg != null) {

			this.width = bufImg.getWidth();
			this.height = bufImg.getHeight();
			datas = new int[this.width][this.height];
			this.bufImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			initDatas(bufImg);
		}

	}

	public Image(int width, int height) {
		this.width = width;
		this.height = height;
		this.bufImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

		this.datas = new int[this.width][this.height];
	}

	private void initDatas(BufferedImage bufImg) {
		for (int x = 0; x < this.width; x++) {
			for (int y = 0; y < this.height; y++) {
				int rgb = bufImg.getRGB(x, y);
				datas[x][y] = rgb;
				this.bufImg.setRGB(x, y, rgb);
			}
		}
	}

	@Override
	public BufferedImage getBufImg() {
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
	public ImageFormat getFormat() {
		// TODO Auto-generated method stub
		return null;
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

	@Override
	public int[][] getDatas() {
		return datas;
	}

}
