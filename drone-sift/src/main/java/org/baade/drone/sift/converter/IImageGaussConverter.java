package org.baade.drone.sift.converter;

import org.baade.drone.sift.gauss.IGaussTemplate;
import org.baade.drone.sift.pyramid.BigPyramid;
import org.baade.drone.sift.pyramid.gauss.IGaussPyramid;

public interface IImageGaussConverter extends IImageConverter{

	public IGaussTemplate buildTemplate(double sigma);
	
	
//	public IImage buildGaussImg(IGaussTemplate gaussTemp, IImage src, IImage dist);
//	
//	
//	public IImage buildGaussImg(IImage src, IImage dist, double sigma);
	
	
	public BigPyramid buildGaussPyramid(IGaussPyramid gaussPyramid);
}
