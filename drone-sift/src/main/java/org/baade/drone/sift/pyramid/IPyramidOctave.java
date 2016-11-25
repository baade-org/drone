package org.baade.drone.sift.pyramid;

import org.baade.drone.sift.img.IImage;

public interface IPyramidOctave {

	
	public void put(int index, IImage image);
	
	public void wirte(String outDir, int octaveNum);
	
}
