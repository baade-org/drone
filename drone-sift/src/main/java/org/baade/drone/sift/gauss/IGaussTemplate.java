package org.baade.drone.sift.gauss;

public interface IGaussTemplate {


	public double getWeightValue(int x, int y);
	
	public void setWeightValue(int x, int y, double weightValue);
	
	public int getRadius();
	
	public int getDimension();
}
