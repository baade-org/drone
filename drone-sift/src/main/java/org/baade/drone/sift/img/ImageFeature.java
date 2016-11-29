package org.baade.drone.sift.img;

import java.awt.image.BufferedImage;
import java.io.IOException;

import org.baade.drone.sift.feature.IFeaturePoint;

public class ImageFeature implements IImageFeature {

	private int width;
	private int height;
	private BufferedImage buffImg; 
	
	public ImageFeature(int width, int height){
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
	public void add(IFeaturePoint featurePoint) {
		int x = featurePoint.getX();
		int y = featurePoint.getY();
		if(featurePoint.isMax()){//最大值用白色表示
			int rgb = 255 << 16 | 255 << 8 | 255;
			this.setRGB(x, y, rgb);
		}else{//最大值用黄色表示
			int rgb = 255 << 16 | 255 << 8 | 0;
			this.setRGB(x, y, rgb);
		}
	}
}
