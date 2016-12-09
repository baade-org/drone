package org.baade.drone.sift.image;

import org.baade.drone.sift.feature.IFeaturePointGather;

public interface IFeatureImage extends IImage {

	public IFeaturePointGather getRoughFeaturePointGather();

	public void setRoughFeaturePointGather(IFeaturePointGather roughFeaturePointGather);

	public IFeaturePointGather getStableFeaturePointGather();

	public void setStableFeaturePointGather(IFeaturePointGather stableFeaturePointGather);
}
