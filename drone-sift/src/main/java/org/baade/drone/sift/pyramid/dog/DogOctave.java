package org.baade.drone.sift.pyramid.dog;

import java.util.ArrayList;
import java.util.List;

import org.baade.drone.sift.img.IImage;
import org.baade.drone.sift.img.IImageDog;

public class DogOctave implements IDogOctave {

	private List<IImageDog> imgDogs;
	
	private int width;
	private int height;

	public DogOctave(int width, int height) {
		imgDogs = new ArrayList<>();
		this.width = width;
		this.height = height;
	}
	
	
	@Override
	public IImage get(int index) {
		int imgCount = getImgCount();
		IImageDog img = null;
		if (index >= 0 && index < imgCount) {
			img = imgDogs.get(index);
		}
		return img;
	}

	@Override
	public int getImgCount() {
		return imgDogs.size();
	}

	@Override
	public void add(int index, IImage img) {
		imgDogs.add(index, (IImageDog)img);
	}


	@Override
	public void wirte(String outDir, int octaveNum) {
		if (imgDogs != null && imgDogs.size() != 0) {
			for (int i = 0; i < imgDogs.size(); i++) {
				IImage img = imgDogs.get(i);
				img.wirte(outDir, octaveNum, i);
			}
		}
		
	}


	@Override
	public int getWidth() {
		return this.width;
	}


	@Override
	public int getHeight() {
		return this.height;
	}

}
