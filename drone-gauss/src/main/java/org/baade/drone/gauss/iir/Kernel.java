package org.baade.drone.gauss.iir;

public class Kernel {

	private float sigma;
	private float q;
	private float q2;
	private float q3;
	private float[] bArr;
	private float B;
	private float N;
	
	public Kernel(float sigma){
		this.bArr = new float[4];
		this.sigma = sigma;
	}
	
	public float getSigma() {
		return sigma;
	}
	public void setSigma(float sigma) {
		this.sigma = sigma;
	}
	public float getQ() {
		return q;
	}
	public void setQ(float q) {
		this.q = q;
	}
	public float getQ2() {
		return q2;
	}
	public void setQ2(float q2) {
		this.q2 = q2;
	}
	public float getQ3() {
		return q3;
	}
	public void setQ3(float q3) {
		this.q3 = q3;
	}
	public float[] getbArr() {
		return bArr;
	}
	public void setbArr(float[] bArr) {
		this.bArr = bArr;
	}
	public float getB() {
		return B;
	}
	public void setB(float b) {
		B = b;
	}
	public float getN() {
		return N;
	}
	public void setN(float n) {
		N = n;
	}
	
	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		str.append("Kernel: {");
		str.append("sigma:" + this.sigma + " | ");
		str.append("q:" + this.q + " | ");
		str.append("q2:" + this.q2 + " | ");
		str.append("q3:" + this.q3 + " | ");
		str.append("B:" + this.B + " | ");
		str.append("N:" + this.N + " | ");
		str.append("bArr:[" + this.bArr[0] + ","  + this.bArr[1] + ","  + this.bArr[2] + ","  + this.bArr[3] + "]}");
		
		return str.toString();
	}
}
