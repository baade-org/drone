package org.baade.drone.sift.img;

public class RGB {

	private int r;
	private int g;
	private int b;
	
	public RGB(int rgb){
		this(rgb >> 16 & 0xFF, rgb >> 8 & 0xFF, rgb & 0xFF);
	}
	
	public RGB(int r, int g, int b){
		this.r = r;
		this.g = g;
		this.b = b;
	}
	
	public int getR() {
		return r;
	}
	public void setR(int r) {
		this.r = r;
	}
	public int getG() {
		return g;
	}
	public void setG(int g) {
		this.g = g;
	}
	public int getB() {
		return b;
	}
	public void setB(int b) {
		this.b = b;
	}
	
	public void gauss(){
		
	}
	
	public int getColor(){
		return this.r << 16 | this.g << 8 | this.b;
	}
}
