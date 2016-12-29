package org.baade.drone.gauss.iir;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class GaussBuilder {

	public static void blur(BufferedImage srcImg, BufferedImage dstImg, Kernel kernel) {
		if (srcImg == null) {
			throw new RuntimeException("src BufferedImage is null");
		}
		int width = srcImg.getWidth();
		int height = srcImg.getHeight();
		int imageType = srcImg.getType();
		if (dstImg == null) {
			dstImg = new BufferedImage(width, height, imageType);
		}
		float[] in = toBuffImg(srcImg);
		float[] out = new float[in.length];
		for (int y = 0; y < height; y++) {
			// gausssmooth(srcImg, dstImg, y * width, y * width, width, 1,
			// kernel);
			// blur(srcImg, dstImg, y * width, width, 1, kernel);

			blurHorizontal(in, out, y, width, 1, kernel);
		}

//		for (int x = 0; x < width; x++) {
//			// gausssmooth(srcImg, dstImg, x, x, height, width, kernel);
//			// blur(srcImg, dstImg, x * width, height, width, kernel);
//
////			 blurVertical(in, out, x, height, width, kernel);
//		}
//2300402
		restoreBuffImg(dstImg, out);
	}

	private static void restoreBuffImg(BufferedImage dstImg, float[] out) {
		int width = dstImg.getWidth();
		int height = dstImg.getHeight();

		int index = 0;

		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				float r = out[index++];
				float g = out[index++];
				float b = out[index++];

				int rgb = ((int) r) << 16 | ((int) g) << 8 | ((int) b);

				dstImg.setRGB(x, y, rgb);

				// dstImg.setRGB(x, y, ((int) out[index++]));
			}
		}
	}

	private static float[] toBuffImg(BufferedImage srcImg) {
		int width = srcImg.getWidth();
		int height = srcImg.getHeight();
		 float[] buffImg = new float[width * height * 3];

//		float[] buffImg = new float[width * height];

		int index = 0;

		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				int color = srcImg.getRGB(x, y);
				int r = (color >> 16) & 0xff;
				int g = (color >> 8) & 0xff;
				int b = color & 0xff;

				buffImg[index++] = r;
				buffImg[index++] = g;
				buffImg[index++] = b;

				// buffImg[index++] = color;
			}
		}
		return buffImg;
	}
	public static void blurHorizontal(float[] in, float[] out, int rowNum, int size, int rowstride, Kernel kernel) {
		int i, n, bufsize;
		float[] w1, w2;
		
		bufsize = size * 3 + 9;
		//size -= 3;
		w1 = new float[bufsize];
		w2 = new float[bufsize];
		
		int startIndex = rowNum * size * 3;
//		int endIndex = startIndex + size * 3;  6912000
		
		w1[0 * 3] = in[startIndex];
		w1[1 * 3] = in[startIndex];
		w1[2 * 3] = in[startIndex];

		w1[0 * 3 + 1] = in[startIndex + 1];
		w1[1 * 3 + 1] = in[startIndex + 1];
		w1[2 * 3 + 1] = in[startIndex + 1];

		w1[0 * 3 + 2] = in[startIndex + 2];
		w1[1 * 3 + 2] = in[startIndex + 2];
		w1[2 * 3 + 2] = in[startIndex + 2];
		
		for (i = 0, n = 3 * 3; i < size * 3; i += 3, n += 3) {
			int currentIndex = startIndex + i * rowstride;
			w1[n] = (float) (kernel.getB() * in[currentIndex]
					+ ((kernel.getbArr()[1] * w1[n - 1 * 3] + 
							kernel.getbArr()[2] * w1[n - 2 * 3] + 
							kernel.getbArr()[3] * w1[n - 3 * 3]) / kernel.getbArr()[0]));

			w1[n + 1] = (float) (kernel.getB() * in[currentIndex + 1]
					+ ((kernel.getbArr()[1] * w1[n - 1 * 3 + 1] + 
							kernel.getbArr()[2] * w1[n - 2 * 3 + 1] + 
							kernel.getbArr()[3] * w1[n - 3 * 3 + 1]) / kernel.getbArr()[0]));

			w1[n + 2] = (float) (kernel.getB() * in[currentIndex + 2]
					+ ((kernel.getbArr()[1] * w1[n - 1 * 3 + 2] + 
							kernel.getbArr()[2] * w1[n - 2 * 3 + 2] + 
							kernel.getbArr()[3] * w1[n - 3 * 3 + 2]) / kernel.getbArr()[0]));

		}
		/*--------------------------------------------*/
		w2[bufsize - 1 * 3] = w1[bufsize - 3];
        w2[bufsize - 2 * 3] = w1[bufsize - 3];
        w2[bufsize - 3 * 3] = w1[bufsize - 3];

        w2[bufsize - 1 * 3 + 1] = w1[bufsize - 2];
        w2[bufsize - 2 * 3 + 1] = w1[bufsize - 2];
        w2[bufsize - 3 * 3 + 1] = w1[bufsize - 2];

        w2[bufsize - 1 * 3 + 2] = w1[bufsize - 1];
        w2[bufsize - 2 * 3 + 2] = w1[bufsize - 1];
        w2[bufsize - 3 * 3 + 2] = w1[bufsize - 1];

        for (i = size * 3, n = bufsize - 9 - 1; i > 0; i -= 3, n -= 3)
        {
        	int currentIndex = startIndex + i * rowstride - 1;
            w2[n] = out[currentIndex] = (float)(kernel.getB()*w1[n] +
                ((kernel.getbArr()[1] * w2[n + 1 * 3 + 0] +
                		kernel.getbArr()[2] * w2[n + 2 * 3 + 0] +
                		kernel.getbArr()[3] * w2[n + 3 * 3 + 0]) / kernel.getbArr()[0]));
            
            w2[n - 1] = out[currentIndex - 1] = (float)(kernel.getB()*w1[n  - 1] +
                    ((kernel.getbArr()[1] * w2[n + 1 * 3 - 1] +
                    		kernel.getbArr()[2] * w2[n + 2 * 3 - 1] +
                    		kernel.getbArr()[3] * w2[n + 3 * 3 - 1]) / kernel.getbArr()[0]));
            w2[n - 2] = out[currentIndex - 2] = (float)(kernel.getB()*w1[n  - 2] +
                    ((kernel.getbArr()[1] * w2[n + 1 * 3  - 2] +
                    		kernel.getbArr()[2] * w2[n + 2 * 3 - 2] +
                    		kernel.getbArr()[3] * w2[n + 3 * 3 - 2]) / kernel.getbArr()[0]));
        }
	}

	public static void blurVertical(float[] in, float[] out, int columnNum, int size, int rowstride, Kernel kernel) {
		int i, n, bufsize, currentIndex;
		float[] w1, w2;
		
		bufsize = size * 3 + 9;
		//size -= 3;
		w1 = new float[bufsize];
		w2 = new float[bufsize];
		
		int startIndex = columnNum * 3;
//		int endIndex = startIndex + size * 3;  6912000
		
		w1[0 * 3] = in[startIndex];
		w1[1 * 3] = in[startIndex];
		w1[2 * 3] = in[startIndex];

		w1[0 * 3 + 1] = in[startIndex + 1];
		w1[1 * 3 + 1] = in[startIndex + 1];
		w1[2 * 3 + 1] = in[startIndex + 1];

		w1[0 * 3 + 2] = in[startIndex + 2];
		w1[1 * 3 + 2] = in[startIndex + 2];
		w1[2 * 3 + 2] = in[startIndex + 2];
		
		for (i = 0, n = 3 * 3; i < size; i++, n += 3) {
			currentIndex = startIndex + i * rowstride * 3;
			w1[n] = (float) (kernel.getB() * in[currentIndex]
					+ ((kernel.getbArr()[1] * w1[n - 1 * 3] + 
							kernel.getbArr()[2] * w1[n - 2 * 3] + 
							kernel.getbArr()[3] * w1[n - 3 * 3]) / kernel.getbArr()[0]));

			w1[n + 1] = (float) (kernel.getB() * in[currentIndex + 1]
					+ ((kernel.getbArr()[1] * w1[n - 1 * 3 + 1] + 
							kernel.getbArr()[2] * w1[n - 2 * 3 + 1] + 
							kernel.getbArr()[3] * w1[n - 3 * 3 + 1]) / kernel.getbArr()[0]));

			w1[n + 2] = (float) (kernel.getB() * in[currentIndex + 2]
					+ ((kernel.getbArr()[1] * w1[n - 1 * 3 + 2] + 
							kernel.getbArr()[2] * w1[n - 2 * 3 + 2] + 
							kernel.getbArr()[3] * w1[n - 3 * 3 + 2]) / kernel.getbArr()[0]));

		}
		
		/*--------------------------------------------*/
		w2[bufsize - 1 * 3] = w1[bufsize - 3];
        w2[bufsize - 2 * 3] = w1[bufsize - 3];
        w2[bufsize - 3 * 3] = w1[bufsize - 3];

        w2[bufsize - 1 * 3 + 1] = w1[bufsize - 2];
        w2[bufsize - 2 * 3 + 1] = w1[bufsize - 2];
        w2[bufsize - 3 * 3 + 1] = w1[bufsize - 2];

        w2[bufsize - 1 * 3 + 2] = w1[bufsize - 1];
        w2[bufsize - 2 * 3 + 2] = w1[bufsize - 1];
        w2[bufsize - 3 * 3 + 2] = w1[bufsize - 1];

        for (i = size - 1, n = bufsize - 9 - 1; i > 0; i--, n -= 3)
        {
        	currentIndex = startIndex + i * rowstride * 3 + 1;
            w2[n] = out[currentIndex] = (float)(kernel.getB()*w1[n] +
                ((kernel.getbArr()[1] * w2[n + 1 * 3 + 0] +
                		kernel.getbArr()[2] * w2[n + 2 * 3 + 0] +
                		kernel.getbArr()[3] * w2[n + 3 * 3 + 0]) / kernel.getbArr()[0]));
            
            w2[n - 1] = out[currentIndex - 1] = (float)(kernel.getB()*w1[n  - 1] +
                    ((kernel.getbArr()[1] * w2[n + 1 * 3 - 1] +
                    		kernel.getbArr()[2] * w2[n + 2 * 3 - 1] +
                    		kernel.getbArr()[3] * w2[n + 3 * 3 - 1]) / kernel.getbArr()[0]));
            w2[n - 2] = out[currentIndex - 2] = (float)(kernel.getB()*w1[n  - 2] +
                    ((kernel.getbArr()[1] * w2[n + 1 * 3  - 2] +
                    		kernel.getbArr()[2] * w2[n + 2 * 3 - 2] +
                    		kernel.getbArr()[3] * w2[n + 3 * 3 - 2]) / kernel.getbArr()[0]));
        }
	}


	private static final String IMG_PATH = "D:/abcd.jpg";

	public static void main(String[] args) {
		try {
			
			BufferedImage srcImg = ImageIO.read(new File(IMG_PATH));
			
			float sigma = 0.5f;
			for(int i=0;i<10;i++){
				sigma += 0.5f;
				long sTime = System.currentTimeMillis();
				Kernel kernel = KernelBuilder.build(sigma);
				int width = srcImg.getWidth();
				int height = srcImg.getHeight();
				int imageType = srcImg.getType();
				BufferedImage dstImg = new BufferedImage(width, height, imageType);
				blur(srcImg, dstImg, kernel);
				long eTime = System.currentTimeMillis();
				System.out.println("time: " + (eTime - sTime));
				File outFile = new File("D:/drone/iir/abcd_blur_"+ i +".jpg");
				ImageIO.write(dstImg, "jpg", outFile);
				
//				srcImg = ImageIO.read(outFile);
			}
			
			

			System.out.println();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
