package org.baade.drone.sift.img;

import java.awt.image.BufferedImage;
import java.io.IOException;

import org.baade.drone.sift.gauss.IGaussTemplate;

public class ImageGauss implements IImageGauss {

	private IGaussTemplate template;
	private int width;
	private int height;
	private BufferedImage buffImg; 
	
	public ImageGauss(IGaussTemplate template, int width, int height){
		this.template = template;
		this.width = width;
		this.height = height;
		this.buffImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
	}
	
	@Override
	public BufferedImage getBuffImg() {
		return this.buffImg;
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
		return this.buffImg.getRGB(x, y);
	}

	@Override
	public void setRGB(int x, int y, int rgb) {
		this.buffImg.setRGB(x, y, rgb);
	}

	@Override
	public void wirte(String outDir, int octaveNum, int gaussLevel) {
		String name = octaveNum + "_" + gaussLevel + ".jpg";
		String outFile = outDir + "/" + name;
		try {
			ImageUtils.write(this.buffImg, outFile);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@Override
	public IGaussTemplate getTemplate() {
		return this.template;
	}

}