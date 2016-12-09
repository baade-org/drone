package org.baade.drone.sift.pyramid;

import org.baade.drone.sift.image.IImage;

public abstract class AbstractOctave implements IOctave {
	private int count;
	private int index;
	private IImage[] images;

	public AbstractOctave(int imgCount, int indexInPyramid) {
		this.count = imgCount;
		this.index = indexInPyramid;
		images = new IImage[this.count];
	}
	public AbstractOctave(){
		
	}

	@Override
	public IImage getImage(int index) {
		return images[index];
	}

	@Override
	public int getCount() {
		return this.count;
	}

	@Override
	public int getIndexInPyramid() {
		return this.index;
	}

	@Override
	public void addImage(int index, IImage img) {
		images[index] = img;
	}

	@Override
	public void write(String dir) {
		for (int i = 0; i < this.count; i++) {
			String fileName = this.index + "_" + i + ".jpg";
			String filePath = dir + "/" + fileName;
			images[i].write(filePath);
		}
	}
}
