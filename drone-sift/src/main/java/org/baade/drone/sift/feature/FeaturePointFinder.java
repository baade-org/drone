package org.baade.drone.sift.feature;

import org.baade.drone.sift.img.IImageDog;
import org.baade.drone.sift.img.IImageFeature;
import org.baade.drone.sift.img.ImageFeature;
import org.baade.drone.sift.pyramid.dog.IDogOctave;
import org.baade.drone.sift.pyramid.dog.IDogPyramid;
import org.baade.drone.sift.pyramid.feature.FeatureOctave;
import org.baade.drone.sift.pyramid.feature.FeaturePyramid;
import org.baade.drone.sift.pyramid.feature.IFeatureOctave;
import org.baade.drone.sift.pyramid.feature.IFeaturePyramid;

public class FeaturePointFinder implements IFeaturePointFinder {

	@Override
	public IFeaturePyramid find(IDogPyramid dogPyrmid) {
		IFeaturePyramid featurePyramid = new FeaturePyramid();

		int octaveCount = dogPyrmid.getOctaveCount();
		for (int i = 0; i < octaveCount; i++) {
			IDogOctave dogOctave = (IDogOctave) dogPyrmid.get(i);

			IFeatureOctave featureOctave = find(dogOctave);

			featurePyramid.add(i, featureOctave);
		}
		return featurePyramid;
	}

	public IFeatureOctave find(IDogOctave dogOctave) {
		int width = dogOctave.getWidth();
		int height = dogOctave.getHeight();
		IImageFeature imgFeature = new ImageFeature(width, height);
		IFeatureOctave featureOctave = new FeatureOctave(imgFeature);

		int imgCount = dogOctave.getImgCount();
		for (int i = 1; i < imgCount - 1; i++) {
			IImageDog imgDog = (IImageDog) dogOctave.get(i);
			for (int x = 0; x < width; x++) {
				for (int y = 0; y < height; y++) {
//					int currRGB = imgDog.getRGB(x, y);
					int minOrMaxStatus = isMaxOrMin(imgDog, i, x, y, dogOctave);
					if(minOrMaxStatus == 0){//是最小值
						IFeaturePoint fp = new FeaturePoint(x, y, false);
						imgFeature.add(fp);
					}else if(minOrMaxStatus == 1){//是最大值
						IFeaturePoint fp = new FeaturePoint(x, y, true);
						imgFeature.add(fp);
					}else{
						//既不是最大值，也不是最小值
					}
				}
			}
		}

		return featureOctave;

	}

	private int isMaxOrMin(IImageDog currentImgDog, int currentIndex, int x, int y, IDogOctave dogOctave) {
		int currRGB = currentImgDog.getRGB(x, y);
		int width = currentImgDog.getWidth();
		int height = currentImgDog.getHeight();

		boolean isMin = true;
		boolean isMax = true;
		for (int ix = -1; ix <= 1; ix++) {
			if(!isMin && !isMax){//不是最大 也不是最小值就跳出
				break;
			}
			for (int jy = -1; jy <= 1; jy++) {
//				if(ix != 0 && jy != 0){//说明不是他自己
					int currX = x + ix;
					int currY = y + jy;
					if(currX >= 0 && currX < width){
						if(currY >= 0 && currY < height){
							int rgb0 = currentImgDog.getRGB(currX, currY);
							int rgbA1 = dogOctave.get(currentIndex - 1).getRGB(currX, currY);
							int rgbS1 = dogOctave.get(currentIndex + 1).getRGB(currX, currY);
							
							if(currRGB > rgb0 || currRGB > rgbA1 || currRGB > rgbS1){
								isMin = false;
							}
							if(currRGB < rgb0 || currRGB < rgbA1 || currRGB < rgbS1){
								isMax = false;
							}
						}
					}
//				}
			}
		}

		if(isMin){
			return 0;
		}else if(isMax){
			return 1;
		}
		return -1;
	}
}
