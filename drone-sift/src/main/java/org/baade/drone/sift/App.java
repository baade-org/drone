package org.baade.drone.sift;

import java.io.IOException;



/**
 * Hello world!
 *
 */
public class App {
	
//	public static String source="D:\\timg.jpg";
	
//	public static String source="D:\\d3_default.png";
	
	public static String source="D:\\abcd.jpg";
	
	
	public static void main(String[] args) throws IOException {
		System.out.println("Hello World!");
		
//		IImageTransformer imgTran = new ImageTransformer();
//		IGaussPyramid gaussPyramid = imgTran.toGray(source, false);
//		System.out.println(gaussPyramid);
		
		long start = System.currentTimeMillis();
		
		long end = System.currentTimeMillis();
		System.out.println(end - start); 
		
	}
}
