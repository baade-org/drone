package org.baade.drone.sift.converter;

import org.baade.drone.sift.img.IImage;

public interface IImageZoomConverter extends IImageConverter{

	public IImage convert(IImage src, IImage dist, double times);
	
	public IImage convert(IImage src, double times);
	
	public IImage convert(IImage src);
	
	public IImage down(IImage src);
}
