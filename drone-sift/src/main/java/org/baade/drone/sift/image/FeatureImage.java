package org.baade.drone.sift.image;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.baade.drone.sift.feature.IFeaturePoint;
import org.baade.drone.sift.feature.IFeaturePointGather;

public class FeatureImage extends AbstractImage implements IFeatureImage {

	private IFeaturePointGather roughFeaturePointGather;
	private IFeaturePointGather stableFeaturePointGather;

	public FeatureImage(int width, int height, int indexInOctave) {
		super(width, height, indexInOctave);
	}

	@Override
	public IFeaturePointGather getRoughFeaturePointGather() {
		return roughFeaturePointGather;
	}

	@Override
	public void setRoughFeaturePointGather(IFeaturePointGather roughFeaturePointGather) {
		this.roughFeaturePointGather = roughFeaturePointGather;
	}

	@Override
	public IFeaturePointGather getStableFeaturePointGather() {
		return stableFeaturePointGather;
	}

	@Override
	public void setStableFeaturePointGather(IFeaturePointGather stableFeaturePointGather) {
		this.stableFeaturePointGather = stableFeaturePointGather;
	}

	@Override
	public void write(String dir) {
		int width = this.getWidth();
		int height = this.getHeight();
		int imageType = this.getBuffImg().getType();
		int roughSize = roughFeaturePointGather.size();
		BufferedImage roughBuffImg = new BufferedImage(width, height, imageType);
		for (int i = 0; i < roughSize; i++) {
			IFeaturePoint fp = roughFeaturePointGather.getFeaturePoint(i);
			if(fp.isMax()){
				int rgb = 0x000000;
				roughBuffImg.setRGB(fp.getX(), fp.getY(), rgb);
			}else{
				int rgb = 0xFFFFFF;
				roughBuffImg.setRGB(fp.getX(), fp.getY(), rgb);
			}
		}
		try {
			String fileName = this.getIndexInOctave() + "_" + System.currentTimeMillis() + "_rough.jpg";
			String filePath = dir + "/" + fileName;
			ImageIO.write(roughBuffImg, "jpg", new File(filePath));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
