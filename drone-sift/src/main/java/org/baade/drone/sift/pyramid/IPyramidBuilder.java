package org.baade.drone.sift.pyramid;

import org.baade.drone.sift.image.IImage;

public interface IPyramidBuilder {

	public IPyramid buildGaussPyramid(IImage src);
	
	public IPyramid buildGaussPyramid(String imgPath);
	
	public IPyramid buildDogPyramid(IPyramid gaussPyramid);
}
