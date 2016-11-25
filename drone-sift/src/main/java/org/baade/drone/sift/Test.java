package org.baade.drone.sift;

public class Test {

	public static void main(String[] args) {
		radius1();
	}

	private static void radius1() {
		int width = 3;
		int height = 3;
		double[][] gaussTmep = new double[width][height];
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				gaussTmep[x][y] = getG(x, y);
			}
		}
		print(gaussTmep);
	}
	
	private static void print(double[][] gaussTmep){
		int width = gaussTmep[0].length;
		int height =  gaussTmep.length;;
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				System.out.print(gaussTmep[x][y] + ",");
			}
			System.out.println();
		}
		
	}

	private static double getG(int x, int y) {
		double a = 1.0 / (2 * Math.PI * Math.pow(1.5, 2));
		double b = -(Math.pow(x, 2) + Math.pow(y, 2)) / (2 * Math.pow(1.5, 2));
		double c = Math.pow(Math.E, b);
		return a * c;
	}
}
