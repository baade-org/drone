package org.baade.drone.sift.img;

import java.awt.image.BufferedImage;

/**
 * 图片
 * @author <a href="http://drone.baade.org">Baade Drone Project</a>
 *
 */
public interface IImage {
	
	public void init(BufferedImage bufImg);

	
	public BufferedImage getBufImg();
	
	public int getWidth();
	
	public int getHeight();
	
	public ImageFormat getFormat();
	
	public int getRGB(int x, int y);
	
	public void setRGB(int x, int y, int rgb);
	
	public void wirte(String outDir, int octaveNum, int gaussLevel);
	
	public int[][] getDatas();
}
