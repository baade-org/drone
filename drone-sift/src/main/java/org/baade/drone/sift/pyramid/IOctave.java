package org.baade.drone.sift.pyramid;

import org.baade.drone.sift.image.IImage;

public interface IOctave {

	public IImage getImage(int index);
	
	public int getCount();
	
	public int getIndexInPyramid();
	
	public void addImage(int index, IImage img);
	
	public void write(String dir);
}
