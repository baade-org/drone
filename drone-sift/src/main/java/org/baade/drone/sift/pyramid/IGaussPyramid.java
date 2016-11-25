package org.baade.drone.sift.pyramid;

import org.baade.drone.sift.img.IImage;

public interface IGaussPyramid {

	public IImage getSourceImg();
	
	
	public void put(int index, IPyramidOctave pyramidOctave);
	
	public void wirte(String outDir);
}
