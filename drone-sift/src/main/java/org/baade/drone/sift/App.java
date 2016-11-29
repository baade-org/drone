package org.baade.drone.sift;

import java.io.IOException;

import org.baade.drone.sift.pyramid.BigPyramid;
import org.baade.drone.sift.pyramid.IPyramidBuilder;
import org.baade.drone.sift.pyramid.PyramidBuilder;


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
		
		IPyramidBuilder gpb = new PyramidBuilder();
		BigPyramid bigPyramid = gpb.build(source);
		System.out.println(bigPyramid);
		
		long end = System.currentTimeMillis();
		System.out.println(end - start); 
		
		bigPyramid.getGaussPyramid().wirte("D:/drone/t1/gauss");
		bigPyramid.getDogPyramid().wirte("D:/drone/t1/dog");
	}
}
