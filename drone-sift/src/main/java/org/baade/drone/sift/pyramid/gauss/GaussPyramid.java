package org.baade.drone.sift.pyramid.gauss;

import org.baade.drone.sift.image.IImage;
import org.baade.drone.sift.pyramid.AbstractPyramid;

public class GaussPyramid extends AbstractPyramid {

	public GaussPyramid(int octaveCount, IImage src) {
		super(octaveCount, src);
	}


}
