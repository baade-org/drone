package org.baade.drone.sift.pyramid;

import org.baade.drone.sift.pyramid.dog.IDogPyramid;
import org.baade.drone.sift.pyramid.gauss.IGaussPyramid;

public class BigPyramid {
	
	private IGaussPyramid gaussPyramid; 
	private IDogPyramid dogPyramid;

	public BigPyramid(IGaussPyramid gaussPyr, IDogPyramid dogPyr){
		this.gaussPyramid = gaussPyr;
		this.dogPyramid = dogPyr;
	}

	public IGaussPyramid getGaussPyramid() {
		return gaussPyramid;
	}

	public void setGaussPyramid(IGaussPyramid gaussPyramid) {
		this.gaussPyramid = gaussPyramid;
	}

	public IDogPyramid getDogPyramid() {
		return dogPyramid;
	}

	public void setDogPyramid(IDogPyramid dogPyramid) {
		this.dogPyramid = dogPyramid;
	}
	
	
}
