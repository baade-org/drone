package org.baade.drone.sift.pyramid.gauss;

import org.baade.drone.sift.img.IImage;
import org.baade.drone.sift.pyramid.IPyramid;

public interface IGaussPyramid  extends IPyramid {

	public IImage getSourceImg();
	
}
