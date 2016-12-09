package org.baade.drone.sift.feature;

import org.baade.drone.sift.pyramid.IPyramid;

public interface IFeaturePointBuilder {

	public IPyramid buildRough(IPyramid dogPyramid);
}
