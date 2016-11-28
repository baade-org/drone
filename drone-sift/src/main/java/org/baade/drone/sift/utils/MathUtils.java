package org.baade.drone.sift.utils;

import org.baade.drone.sift.Const;

public class MathUtils {

	public static double gaussian(int xDistance, int yDistance, double sigma) {
//		double a = 1.0 / (2 * Math.PI * Math.pow(sigma, 2));
		double b = (Math.pow(xDistance, 2) + Math.pow(yDistance, 2)) / (2 * sigma * sigma);
		double c = Math.exp(-b) / (2 * Math.PI * Math.pow(sigma, 2));
		return c;
	}
	
	public static double getSigma(int octaveNum, int leveNum) {
		double on = Math.pow(2, octaveNum);
		double k = getK(leveNum);
		double sigma = on * k * Const.SIGMA_BASE;
		return sigma;
	}
	
	public static double getK(int leveNum) {
		double k = Math.pow(2, 1.0 / Const.GAUSS_LEVEL_COUNT);
		return Math.pow(k, leveNum);
	}

}
