package org.baade.drone.sift.pyramid;

import org.baade.drone.sift.img.IImage;

public interface IPyramidBuilder {

	public BigPyramid build(String imgPath);
	
	public BigPyramid build(IImage src);
}
