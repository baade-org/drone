package org.baade.drone.sift.pyramid;

import org.baade.drone.sift.image.IImage;

public interface IPyramid {

	public IOctave getOctave(int index);
	
	public int getCount();
	
	public void addOctave(int index, IOctave octave);
	
	public IImage getSrcImg();
	
	public void write(String dir);
}
