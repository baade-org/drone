package org.baade.drone.sift.gauss;

import java.util.HashMap;
import java.util.Map;

public class GaussTemplate implements IGaussTemplate {

	private Map<String, Double> values;

	private int radius;

	public GaussTemplate(int radius) {
		values = new HashMap<String, Double>();
		this.radius = radius;
	}

	@Override
	public double getWeightValue(int x, int y) {
		String key = getKey(x, y);
		return values.get(key);
	}

	@Override
	public void setWeightValue(int x, int y, double weightValue) {
		String key = getKey(x, y);
		values.put(key, weightValue);
	}

	/**
	 * 用xy的绝对值作为KEY
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	private String getKey(int x, int y) {
		// return Math.abs(x) + "_" + Math.abs(y);
		return x + "_" + y;
	}

	@Override
	public int getRadius() {
		return this.radius;
	}

	@Override
	public int getDimension() {
		return 2 * this.radius + 1;
	}

}
