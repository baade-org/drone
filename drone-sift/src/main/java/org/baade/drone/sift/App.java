package org.baade.drone.sift;

import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;

import org.baade.drone.sift.feature.FeaturePointBuilder;
import org.baade.drone.sift.feature.IFeaturePointBuilder;
import org.baade.drone.sift.pyramid.IPyramid;
import org.baade.drone.sift.pyramid.IPyramidBuilder;
import org.baade.drone.sift.pyramid.PyramidBuilder;

/**
 * Hello world!
 *
 */
public class App {

	// public static String source="D:\\timg.jpg";

//	 public static String source="D:\\d3_default.png";

	public static String source = "D:\\abcd.jpg";

	public static void main(String[] args) throws Exception {
		System.out.println("Hello World!");

		// IImageTransformer imgTran = new ImageTransformer();
		// IGaussPyramid gaussPyramid = imgTran.toGray(source, false);
		// System.out.println(gaussPyramid);

		long start = System.currentTimeMillis();

		IPyramidBuilder pyramidBuilder = new PyramidBuilder();
		IPyramid gaussPyramid = pyramidBuilder.buildGaussPyramid(source);
		
		long end = System.currentTimeMillis();
		System.out.println(end - start);
		gaussPyramid.write("d:/drone/t1/gauss");
		
		start = System.currentTimeMillis();
		IPyramid dogPyramid = pyramidBuilder.buildDogPyramid(gaussPyramid);
		end = System.currentTimeMillis();
		System.out.println(end - start);
		dogPyramid.write("d:/drone/t1/dog");
		
		start = System.currentTimeMillis();
		IFeaturePointBuilder fpBuilder = new FeaturePointBuilder();
		IPyramid fpPyramid = fpBuilder.buildRough(dogPyramid);
		end = System.currentTimeMillis();
		System.out.println(end - start);
		fpPyramid.write("d:/drone/t1/fp");
		
//		System.out.println(1.0/25);

	}

	public static BufferedImage convoleOp(float[] elems, BufferedImage origin) {
		Kernel kernel = new Kernel(5, 5, elems);
		ConvolveOp op = new ConvolveOp(kernel);
		BufferedImage filteredImage = new BufferedImage(origin.getWidth(), origin.getHeight(), origin.getType());
		op.filter(origin, filteredImage);
		return filteredImage;
	}

	/**
	 * 模糊
	 * 
	 * @param origin
	 * @return
	 */
	public static BufferedImage blur(BufferedImage origin) {
//		float weight = 0.1111111F;
		float weight = 0.04F;
//		float[] elements = new float[9];
		float[] elements = new float[25];
		for (int i = 0; i < 25; i++) {
			elements[i] = weight;
		}
		return convoleOp(elements, origin);
	}
}
