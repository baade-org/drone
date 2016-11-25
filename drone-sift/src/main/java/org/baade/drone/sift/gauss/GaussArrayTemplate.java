package org.baade.drone.sift.gauss;

public class GaussArrayTemplate implements IGaussTemplate {

	private double[][] values;
	private int dimension;

	public GaussArrayTemplate(int dimension) {
		this.dimension = dimension;
		values = new double[dimension][dimension];
	}

	@Override
	public double getWeightValue(int x, int y) {
		x = Math.abs(x);
		y = Math.abs(y);
		return values[x][y];
	}

	@Override
	public void setWeightValue(int x, int y, double weightValue) {
		x = Math.abs(x);
		y = Math.abs(y);
		values[x][y] = weightValue;
	}

	@Override
	public int getRadius() {
		return (this.dimension - 1)/2;
	}

	@Override
	public int getDimension() {
		return this.dimension;
	}

}
