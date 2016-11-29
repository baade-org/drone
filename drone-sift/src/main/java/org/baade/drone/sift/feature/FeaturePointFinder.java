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
			IDogOctave dogOctave = (IDogOctave)dogPyrmid.get(i);
			
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
			IImageDog imgDog = (IImageDog)dogOctave.get(i);
			for (int x = 0; x < width; x++) {
				for (int y = 0; y < height; y++) {
					int currRGB = imgDog.getRGB(x, y);
				}
			}
		}
		
		return featureOctave;
		
	}

}
