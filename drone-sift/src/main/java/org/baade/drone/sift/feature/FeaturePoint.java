package org.baade.drone.sift.feature;

public class FeaturePoint implements IFeaturePoint {

	private int x;
	private int y;
	private boolean isMax;
	
	public FeaturePoint(int x, int y, boolean isMax){
		this.x = x;
		this.y = y;
		this.isMax = isMax;
	}
	
	
	@Override
	public boolean isMax() {
		return this.isMax;
	}

	@Override
	public int getX() {
		return this.x;
	}

	@Override
	public int getY() {
		return this.y;
	}

}
