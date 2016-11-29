package org.baade.drone.sift.pyramid.feature;

import org.baade.drone.sift.img.IImage;
import org.baade.drone.sift.img.IImageFeature;

public class FeatureOctave implements IFeatureOctave {

	private IImageFeature imgFeature;
	public FeatureOctave(IImageFeature imgFeature) {
		this.imgFeature = imgFeature;
	}
	
	
	@Override
	public IImage get(int index) {
		return null;
	}

	@Override
	public int getImgCount() {
		return 0;
	}

	@Override
	public void add(int index, IImage img) {
		
	}
	@Override
	public void wirte(String outDir, int octaveNum) {
		this.imgFeature.wirte(outDir, octaveNum, 0);
	}


	@Override
	public IImageFeature getImg() {
		return this.imgFeature;
	}


	@Override
	public int getWidth() {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public int getHeight() {
		// TODO Auto-generated method stub
		return 0;
	}

}
