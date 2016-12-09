package org.baade.drone.sift.image;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public abstract class AbstractImage implements IImage {
	
	private int width;
	private int height;
	private BufferedImage buffImg;
	private int index;
	public AbstractImage(){
		
	}

	public AbstractImage(int width, int height, int indexInOctave){
		this.width = width;
		this.height = height;
		this.index = indexInOctave;
		this.buffImg = new BufferedImage(this.width, this.height, BufferedImage.TYPE_INT_RGB);
	}
	
	public AbstractImage(BufferedImage buffImg, int indexInOctave){
		this.width = buffImg.getWidth();
		this.height = buffImg.getHeight();
		this.index = indexInOctave;
		this.buffImg = buffImg;
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
	public BufferedImage getBuffImg() {
		return this.buffImg;
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
	public int getIndexInOctave() {
		return this.index;
	}
	@Override
	public void write(String filePath) {
		try {
			ImageIO.write(this.buffImg, "jpg", new File(filePath));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
