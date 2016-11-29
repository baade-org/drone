package org.baade.drone.sift.pyramid.gauss;

import org.baade.drone.sift.img.IImage;
import org.baade.drone.sift.pyramid.IOctave;

public interface IGaussOctave extends IOctave {

	public IImage getSrcImg();
}
