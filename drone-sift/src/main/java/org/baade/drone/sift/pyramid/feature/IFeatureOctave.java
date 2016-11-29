package org.baade.drone.sift.pyramid.feature;

import org.baade.drone.sift.img.IImageFeature;
import org.baade.drone.sift.pyramid.IOctave;

public interface IFeatureOctave extends IOctave{

	public IImageFeature getImg();
	
}
