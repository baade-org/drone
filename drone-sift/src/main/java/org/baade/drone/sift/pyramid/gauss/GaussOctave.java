package org.baade.drone.sift.pyramid.gauss;

import java.util.ArrayList;
import java.util.List;

import org.baade.drone.sift.img.IImage;
import org.baade.drone.sift.img.IImageGauss;

public class GaussOctave implements IGaussOctave {

	private List<IImageGauss> imgGausses;
	
	private IImage srcImg;
	
	private int width;
	private int height;

	public GaussOctave(IImage srcImg) {
		imgGausses = new ArrayList<>();
		this.srcImg = srcImg;
		this.width = srcImg.getWidth();
		this.height = srcImg.getHeight();
	}

	@Override
	public void wirte(String outDir, int octaveNum) {
		if (imgGausses != null && imgGausses.size() != 0) {
			for (int i = 0; i < imgGausses.size(); i++) {
				IImage img = imgGausses.get(i);
				img.wirte(outDir, octaveNum, i);
			}
		}
	}

	@Override
	public void add(int index, IImage img) {
		imgGausses.add(index, (IImageGauss)img);
	}

	@Override
	public IImageGauss get(int index) {
		int imgCount = getImgCount();
		IImageGauss img = null;
		if (index >= 0 && index < imgCount) {
			img = imgGausses.get(index);
		}
		return img;
	}

	@Override
	public int getImgCount() {
		return imgGausses.size();
	}

	@Override
	public IImage getSrcImg() {
		return this.srcImg;
	}

	@Override
	public int getWidth() {
		return this.width;
	}

	@Override
	public int getHeight() {
		return this.height;
	}

}
