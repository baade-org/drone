package org.baade.drone.sift.feature;

import org.baade.drone.sift.pyramid.dog.IDogPyramid;
import org.baade.drone.sift.pyramid.feature.IFeaturePyramid;

public interface IFeaturePointFinder {

	public IFeaturePyramid find(IDogPyramid dogPyrmid);
}
