package org.baade.drone.sift.img;

import java.io.IOException;

import org.baade.drone.sift.pyramid.IGaussPyramid;

/**
 * 图片转换器
 * @author <a href="http://drone.baade.org">Baade Drone Project</a>
 *
 */
public interface IImageTransformer {

	
	/**
	 * 将图片转换成灰度图片
	 * @param imgPath 图片路径
	 * @param isWeighting 是否加权
	 * @return BufferedImage
	 * @throws IOException
	 */
	public IGaussPyramid toGray(String imgPath, boolean isWeighting) throws IOException ;
}
