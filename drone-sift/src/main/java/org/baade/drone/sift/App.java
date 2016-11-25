package org.baade.drone.sift;

import java.io.IOException;

import org.baade.drone.sift.img.IImageTransformer;
import org.baade.drone.sift.img.ImageTransformer;
import org.baade.drone.sift.pyramid.IGaussPyramid;

/**
 * Hello world!
 *
 */
public class App {
	
//	public static String source="D:\\timg.jpg";
	
	public static String source="D:\\d3_default.png";
	
//	public static String source="D:\\abcd.jpg";
	
	
	public static void main(String[] args) throws IOException {
		System.out.println("Hello World!");
		
		IImageTransformer imgTran = new ImageTransformer();
		IGaussPyramid gaussPyramid = imgTran.toGray(source, false);
		System.out.println(gaussPyramid);
		
		gaussPyramid.wirte("D:/drone/t1");
	}
}
