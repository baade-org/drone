package org.baade.drone.sift;

/**
 * 相关常量
 * @author <a href="http://drone.baade.org">Baade Drone Project</a>
 *
 */
public interface Const {

	/**
	 * 尺度数量（金字塔的层数）
	 */
	public static final int OCTAVE_COUNT = 3;
	
	/**
	 * 模糊等级的个数
	 */
	public static final int GAUSS_LEVEL_COUNT = 3;
	
	public static final double SIGMA_BASE = 1.6;
}
