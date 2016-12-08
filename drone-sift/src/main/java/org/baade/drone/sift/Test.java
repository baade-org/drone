package org.baade.drone.sift;

public class Test {

	public static void main(String[] args) {
		// radius1();
		// double atanV = Math.tan(1.0/1);
		// System.out.println(atanV);
		// double cosv = Math.acos(atanV);
		// System.out.println(cosv);
		//
		// double pww = Math.pow(2, 1.0/2);
		// System.out.println(pww);

		// System.out.println(Math.log(10) / Math.log(2));
		//
		// int len = 100;
		// int[] arr = new int[len];
		// for (int i = 1; i <= len; i++) {
		// arr[i - 1] = i;
		// }
		//
		// for (int i = 1; i < 5 ; i++) {
		// int tempLen = (int)(len / Math.pow(2, i) + 0.5);
		// int[] arrTemp = new int[tempLen];
		// int arrTempIndex = 0;
		// int index = 0;
		// while(index < len && arrTempIndex < tempLen){
		// arrTemp[arrTempIndex ++ ] = arr[index];
		// index = (int)(Math.pow(2, i) * arrTempIndex);
		// }
		//
		// for(int j = 0; j < tempLen ; j++){
		// System.out.print(arrTemp[j] + " | ");
		// }
		// System.out.println();
		// }
		int radiu = 1;

		int count = 0;
		for (int x = 0; x <= radiu; x++) {
			for (int y = x; y <= radiu; y++) {
//				System.out.println(Math.sqrt((r * r - x * x)));
				System.out.println( x + "," + y);
				count ++;
			}

		}
		
		System.out.println(count);
		count = 0;
		for (int x = 0; x <= radiu; x++) {
			for (int y = 0; y <= radiu; y++) {
//				System.out.println(Math.sqrt((r * r - x * x)));
				System.out.println( x + "," + y);
				count++;
			}

		}
		System.out.println(count);
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

	private static void print(double[][] gaussTmep) {
		int width = gaussTmep[0].length;
		int height = gaussTmep.length;
		;
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
