package org.baade.drone.sift.gauss.buff;

public class RGBBuff {

	private double r;
	private double g;
	private double b;
	
	public RGBBuff(int rgb){
		this(rgb >> 16 & 0xFF, rgb >> 8 & 0xFF, rgb & 0xFF);
	}
	
	public RGBBuff(double r, double g, double b){
		this.r = r;
		this.g = g;
		this.b = b;
	}
	
	public double getR() {
		return r;
	}
	public void setR(double r) {
		this.r = r;
	}
	public double getG() {
		return g;
	}
	public void setG(double g) {
		this.g = g;
	}
	public double getB() {
		return b;
	}
	public void setB(double b) {
		this.b = b;
	}
	
	public void gauss(){
		
	}
	
	public int getColor(){
		return (int)this.r << 16 | (int)this.g << 8 | (int)this.b;
	}
}
