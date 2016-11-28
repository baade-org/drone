package org.baade.drone.sift.pyramid;

import org.baade.drone.sift.img.IImage;

public interface IGaussPyramidBuilder {

	public IGaussPyramid build(String imgPath);
	
	public IGaussPyramid build(IImage src);
}
