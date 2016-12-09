package org.baade.drone.sift.image;

import java.awt.image.BufferedImage;

public interface IImage {

	public int getWidth();
	
	public int getHeight();
	
	public BufferedImage getBuffImg();
	
	public int getRGB(int x, int y);
	
	public void setRGB(int x, int y, int rgb);
	
	public int getIndexInOctave();
	
	public void write(String filePath);
}
