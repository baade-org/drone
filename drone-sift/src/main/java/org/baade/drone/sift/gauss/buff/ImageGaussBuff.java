package org.baade.drone.sift.gauss.buff;

import java.awt.image.BufferedImage;
import java.io.IOException;

import org.baade.drone.sift.gauss.IGaussTemplate;
import org.baade.drone.sift.img.ImageFormat;
import org.baade.drone.sift.img.ImageUtils;

public class ImageGaussBuff implements IImageGaussBuff{

	private ImagePixelGaussBuff[][] pixelGaussBuffs;

	private BufferedImage bufImg;
	private int width;
	private int height;

	private IGaussTemplate gaussTemplate;

	public ImageGaussBuff(IGaussTemplate gaussTemplate, BufferedImage bufImg) {
		this.gaussTemplate = gaussTemplate;
		init(bufImg);
	}

	public void init(BufferedImage bufImg) {
		this.width = bufImg.getWidth();
		this.height = bufImg.getHeight();
		pixelGaussBuffs = new ImagePixelGaussBuff[this.width][this.height];
		this.bufImg = new BufferedImage(width, height, bufImg.getType());
		initDatas(bufImg);
	}

	private void initDatas(BufferedImage srcBufImg) {
		for (int x = 0; x < this.width; x++) {
			for (int y = 0; y < this.height; y++) {
				int rgb = srcBufImg.getRGB(x, y);
				pixelGaussBuffs[x][y] = new ImagePixelGaussBuff(this.gaussTemplate, rgb);
				this.bufImg.setRGB(x, y, rgb);
				System.out.println( x + ":" + y);
			}
		}
	}

	@Override
	public ImagePixelGaussBuff getImagePixelGaussBuff(int x, int y) {
		return pixelGaussBuffs[x][y];
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
		return this.bufImg.getRGB(x, y);
	}

	@Override
	public void setRGB(int x, int y, int rgb) {
		this.bufImg.setRGB(x, y, rgb);
	}

	@Override
	public void wirte(String outDir, int octaveNum, int gaussLevel) {
		String name = octaveNum + "_" + gaussLevel + ".jpg";
		String outFile = outDir + "/" + name;
		try {
			ImageUtils.write(this.bufImg, outFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public int[][] getDatas() {
		// TODO Auto-generated method stub
		return null;
	}


}
