package org.baade.drone.sift.img;

import org.baade.drone.sift.gauss.IGaussTemplate;

/**
 * 图片
 * @author <a href="http://drone.baade.org">Baade Drone Project</a>
 *
 */
public interface IImageGauss extends IImage{
	
	public IGaussTemplate getTemplate();
	
}
