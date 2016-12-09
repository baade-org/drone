package org.baade.drone.sift.pyramid.feature;

import org.baade.drone.sift.pyramid.AbstractOctave;

public class FeatureOctave extends AbstractOctave{

	
	public FeatureOctave(int imgCount, int indexInPyramid) {
		super(imgCount, indexInPyramid);
	}
	

	@Override
	public void write(String dir) {
		for (int i = 0; i < this.getCount(); i++) {
//			String fileName = this.getIndexInPyramid() + "_" + i + ".jpg";
//			String filePath = dir + "/" + fileName;
//			images[i].write(dir);
			
			this.getImage(i).write(dir);
		}
	}
}
