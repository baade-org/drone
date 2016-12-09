package org.baade.drone.sift.feature;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractFeaturePointGather implements IFeaturePointGather {

	private List<IFeaturePoint> list;

	public AbstractFeaturePointGather() {
		list = new ArrayList<>();
	}
	
	@Override
	public void addFeaturePoint(IFeaturePoint featurePoint) {
		list.add(featurePoint);
	}

	@Override
	public IFeaturePoint getFeaturePoint(int index) {
		return list.get(index);
	}

	@Override
	public int size() {
		return list.size();
	}
	
	
}
