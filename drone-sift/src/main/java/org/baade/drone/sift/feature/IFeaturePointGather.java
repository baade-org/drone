package org.baade.drone.sift.feature;

public interface IFeaturePointGather {

	
	public void addFeaturePoint(IFeaturePoint featurePoint);
	
	public IFeaturePoint getFeaturePoint(int index);
	
	public int size();
}
