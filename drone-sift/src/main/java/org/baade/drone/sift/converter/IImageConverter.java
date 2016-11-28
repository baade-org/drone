package org.baade.drone.sift.converter;

import org.baade.drone.sift.img.IImage;

public interface IImageConverter {

	public IImage convert(IImage src, IImage dist);
}
