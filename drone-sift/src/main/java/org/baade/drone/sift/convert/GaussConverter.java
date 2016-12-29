package org.baade.drone.sift.convert;


import java.awt.image.BufferedImage;

import org.baade.drone.gauss.iir.GaussBuilder;
import org.baade.drone.gauss.iir.Kernel;
import org.baade.drone.gauss.iir.KernelBuilder;
import org.baade.drone.sift.Const;
import org.baade.drone.sift.image.IImage;
import org.baade.drone.sift.image.Image;

public class GaussConverter implements IGaussConverter {

	@Override
	public IImage convert(IImage image, int octaveIndex, int gaussLevelIndex) {
//		Kernel kernel = buildKernel(octaveIndex, gaussLevelIndex);
//		ConvolveOp op = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, 
//				new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ALPHA_INTERPOLATION_SPEED));
//		BufferedImage distBuffImage = new BufferedImage(image.getWidth(), image.getHeight(), image.getBuffImg().getType());
//		op.filter(image.getBuffImg(), distBuffImage);
//		
//		try {
//			ImageIO.write(distBuffImage, ".jpg", new File("d:/drone/sss.jpg"));
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		float sigma = getSigma(octaveIndex, gaussLevelIndex);
		Kernel kernel = KernelBuilder.build(sigma);
		BufferedImage distBuffImage = new BufferedImage(image.getWidth(), image.getHeight(), image.getBuffImg().getType());
		GaussBuilder.blur(image.getBuffImg(), distBuffImage, kernel);
		IImage distImg = new Image(distBuffImage);
		return distImg;
	}

//	private Kernel buildKernel(int octaveIndex, int gaussLevelIndex) {
//		double sigma = getSigma(octaveIndex, gaussLevelIndex);
//		int radius = (int) (3 * sigma);
//		if (radius % 2 != 0) {// 半径不是偶数的话
//			radius += 1;// 保证 半径是偶数
//		}
//		int dimension = radius * 2 + 1;
//		
//		double[][] values = new double[dimension][dimension];
//		double totalValue = 0.0;
//		int i = 0;
//		int j = 0;
//		int xIndex = 0;
//		int yIndex = 0;
//		for (i = -radius; i <= radius; i++) {
//			for (j = -radius; j <= radius; j++) {
//				double currGV = gaussValue(sigma, i, j);
//				totalValue += currGV;
//				values[xIndex][yIndex++] = currGV;
//			}
//			xIndex++;
//			yIndex = 0;
//		}
//		
//		float[] kernelValues = new float[dimension * dimension];
//		int index = 0;
//		xIndex = 0;
//		yIndex = 0;
//		for (i = -radius; i <= radius; i++) {
//			for (j = -radius; j <= radius; j++) {
//				double currGV = values[xIndex][yIndex++];
//				float v =  (float)(currGV / totalValue);
//				kernelValues[index++] = v;
//			}
//			xIndex++;
//			yIndex = 0;
//		}
//
//		return new Kernel(dimension, dimension, kernelValues);
//	}

	private double getK() {
		return Math.pow(2, 1.0 / Const.GAUSS_LEVEL_COUNT);
	}

	private float getSigma(int octaveIndex, int gaussLevelIndex) {
		float a = (float)(Math.pow(2, octaveIndex));
		float b = (float)(Math.pow(getK(), gaussLevelIndex));
		return a * b * Const.SIGMA_BASE;
	}

	private double gaussValue(double sigma, int x, int y) {
		double a = 1.0 / (2 * Math.PI * sigma * sigma);
		double b = -(x * x + y * y) / (2 * sigma * sigma);
		double c = Math.pow(Math.E, b);
		return a * c;
	}
}
