package org.baade.drone.sift.pyramid.dog;

import org.baade.drone.sift.image.IImage;
import org.baade.drone.sift.pyramid.AbstractPyramid;

public class DogPyramid extends AbstractPyramid {

	public DogPyramid(int octaveCount, IImage src) {
		super(octaveCount, src);
	}
	public DogPyramid(int octaveCount) {
		super(octaveCount, null);
	}

}
