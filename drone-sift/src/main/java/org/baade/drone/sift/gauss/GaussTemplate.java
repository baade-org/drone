package org.baade.drone.sift.gauss;

import java.util.HashMap;
import java.util.Map;

public class GaussTemplate implements IGaussTemplate {
	
	private Map<String, Double> values;
	
	public GaussTemplate(){
		values = new HashMap<String, Double>();
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
	 * @param x
	 * @param y
	 * @return
	 */
	private String getKey(int x, int y){
		return Math.abs(x) + "_" + Math.abs(y);
	}

	@Override
	public int getRadius() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getDimension() {
		// TODO Auto-generated method stub
		return 0;
	}

}
