package org.baade.drone.sift;

/**
 * 相关常量
 * @author <a href="http://drone.baade.org">Baade Drone Project</a>
 *
 */
public interface Const {
	
	/**
	 * 图片默认放大尺寸
	 */
	public static final int IMG_ZOOM_DEFAULT_TIMES = 2;

	/**
	 * 尺度数量（金字塔的层数）
	 */
	public static final int OCTAVE_COUNT = 3;
	
	/**
	 * 模糊等级的个数
	 */
	public static final int GAUSS_LEVEL_COUNT = 3;
	
	public static final double SIGMA_BASE = 0.5;
	
	
	/**
	 * 图片降样采集的最小尺寸（高度或宽度）<br>
	 * 单位： 像素<br>
	 * 这个参数决定了高斯金字塔的塔层数
	 */
	public static final int IMG_MIN_WIDTH_OR_HEIGHT_SIZE = 30;
}
