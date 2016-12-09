package org.baade.drone.sift.feature;

import org.baade.drone.sift.Const;
import org.baade.drone.sift.image.FeatureImage;
import org.baade.drone.sift.image.IFeatureImage;
import org.baade.drone.sift.image.IImage;
import org.baade.drone.sift.pyramid.IOctave;
import org.baade.drone.sift.pyramid.IPyramid;
import org.baade.drone.sift.pyramid.feature.FeatureOctave;
import org.baade.drone.sift.pyramid.feature.FeaturePyramid;

public class FeaturePointBuilder implements IFeaturePointBuilder {

	@Override
	public IPyramid buildRough(IPyramid dogPyramid) {
		int octaveCount = dogPyramid.getCount();
		IPyramid fpPyramid = new FeaturePyramid(octaveCount);
		int dogLevelCount = Const.GAUSS_LEVEL_COUNT + 2;
		for (int i = 0; i < octaveCount; i++) {
			IOctave dogOctave = dogPyramid.getOctave(i);
			int width = dogOctave.getImage(0).getWidth();
			int height = dogOctave.getImage(0).getHeight();
			IOctave fpOctave = new FeatureOctave(1, i);// 每一层只有一张特征点图片
			IFeatureImage fpImg = new FeatureImage(width, height, 0);
			IFeaturePointGather roughFeaturePointGather = new RoughFeaturePointGather();
			for (int j = 1; j < dogLevelCount - 1; j++) {
				IImage currentImg = dogOctave.getImage(j);
				IImage upImg = dogOctave.getImage(j - 1);
				IImage downImg = dogOctave.getImage(j + 1);

				for (int x = 0; x < width; x++) {
					for (int y = 0; y < height; y++) {
						int fpStatus = calcFP(x, y, currentImg, upImg, downImg, width, height);
						IFeaturePoint fp = null;
						if (fpStatus == 0) {// 说明是最小
							fp = new FeaturePoint(x, y, false);
						} else if (fpStatus == 1) {// 说明是最大
							fp = new FeaturePoint(x, y, true);
						}
						if (fp != null) {
							roughFeaturePointGather.addFeaturePoint(fp);
						}

					}
				}
			}
			fpImg.setRoughFeaturePointGather(roughFeaturePointGather);
			fpOctave.addImage(0, fpImg);// 每一层只有一张特征点图片
			fpPyramid.addOctave(i, fpOctave);

		}
		return fpPyramid;
	}

	// 共26点比较
	private int calcFP(int x, int y, IImage currentImg, IImage upImg, IImage downImg, int width, int height) {
		int currRGB = currentImg.getRGB(x, y);
		int lx = 0;
		int ly = 0;
		int[] values = new int[27];
		int index = 0;
		for (int i = -1; i <= 1; i++) {
			for (int j = -1; j <= 1; j++) {
				lx = x + i;
				lx = lx >= 0 ? lx : -lx;
				ly = x + j;
				ly = ly >= 0 ? ly : -ly;

				lx = lx >= width ? width * 2 - lx - 1 : lx;
				ly = ly >= height ? height * 2 - ly - 1 : ly;

				try {
					values[index++] = currentImg.getRGB(lx, ly);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				values[index++] = upImg.getRGB(lx, ly);
				values[index++] = downImg.getRGB(lx, ly);
			}
		}

		return calc(values, currRGB, x, y,currentImg);
	}

	private int calc(int[] values, int value, int x, int y,IImage currentImg) {
		int len = values.length;
		boolean isMax = false;
		boolean isMin = false;
		// 是不是最大
		for (int i = 0; i < len; i++) {
			int currValue = values[i];
			if (value > currValue) {
				isMax = true;
			} else {
				isMax = false;
				break;
			}
		}
		if (!isMax) {
			// 不是最大，再判断是不是最小
			for (int i = 0; i < len; i++) {
				int currValue = values[i];
				if (value < currValue) {
					isMin = true;
				} else {
					isMin = false;
					break;
				}
			}
		}

		if (isMin) {
			if(calcStable(x, y, currentImg)){
				return 0;
			}
			
		} else if (isMax) {
			if(calcStable(x, y, currentImg)){
				return 1;
			}
		}
		return -1;
	}

	private boolean calcStable(int x, int y, IImage currentImg) {
		int r = 3;
		if(y  < 2 || x < 2 
				|| x > (currentImg.getWidth() - 2)
				|| y > (currentImg.getHeight() - 2)){ // 边缘不计算
			return false;
			
		}

		int xy00 = currentImg.getRGB(x - 1, y - 1);
		int xy01 = currentImg.getRGB(x - 1, y);
		int xy02 = currentImg.getRGB(x - 1, y + 1);
		int xy10 = currentImg.getRGB(x, y - 1);// gaussImage[y][x - 1];
		int xy11 = currentImg.getRGB(x, y);// gaussImage[y][x];
		int xy12 = currentImg.getRGB(x, y + 1);// gaussImage[y][x + 1];
		int xy20 = currentImg.getRGB(x + 1, y - 1);// gaussImage[y + 1][x -
														// 1];
		int xy21 = currentImg.getRGB(x + 1, y);// gaussImage[y + 1][x];
		int xy22 = currentImg.getRGB(x + 1, y + 1);// gaussImage[y + 1][x +
														// 1];

		double dxx = xy10 + xy12 - 2 * xy11;
		double dyy = xy01 + xy21 - 2 * xy11;
		double dxy = (xy22 - xy20) - (xy02 - xy00);
		/// hessian矩阵的对角线值和行列式
		double trH = dxx + dyy;
		double detH = dxx * dyy;// -dxy*dxy;

		/// 邻域的均值
		double avg = (xy00 + xy01 + xy02 + xy10 + xy11 + xy12 + xy20 + xy21 + xy22) / 9;
		/// 领域方差
		double DX = (xy00 - avg) * (xy00 - avg) + (xy01 - avg) * (xy01 - avg) + (xy02 - avg) * (xy02 - avg)
				+ (xy10 - avg) * (xy10 - avg) + (xy11 - avg) * (xy11 - avg) + (xy12 - avg) * (xy12 - avg)
				+ (xy20 - avg) * (xy20 - avg) + (xy21 - avg) * (xy21 - avg) + (xy22 - avg) * (xy22 - avg);
		DX = DX / 9;

		double threshold = (double) (r + 1) * (r + 1) / r;
		if ((detH > 0 && (trH * trH) / detH < threshold) && (DX >= 0.03)) {
			/// 主曲率小于阈值，则不是需要剔除的边缘响应点;方差大于0.03的为高对比度
			return true;
		}
		
		return false;
	}

}
