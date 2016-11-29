package org.baade.drone.sift.img;

import org.baade.drone.sift.feature.IFeaturePoint;

/**
 * 图片
 * @author <a href="http://drone.baade.org">Baade Drone Project</a>
 *
 */
public interface IImageFeature extends IImage{
	
	/**
	 * 添加一个特征点
	 * @param featurePoint
	 */
	public void add(IFeaturePoint featurePoint);
}
