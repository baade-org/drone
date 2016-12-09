package org.baade.drone.sift.convert;

import org.baade.drone.sift.image.IImage;

public interface IGaussConverter {

	public IImage convert(IImage image, int octaveIndex, int gaussLevelIndex);
	
	
}
