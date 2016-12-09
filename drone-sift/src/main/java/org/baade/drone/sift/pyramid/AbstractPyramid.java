package org.baade.drone.sift.pyramid;

import java.io.File;

import org.baade.drone.sift.image.IImage;

public abstract class AbstractPyramid implements IPyramid {

	private int count;
	private IOctave[] octaves;
	private IImage src;

	public AbstractPyramid(){
		
	}
			
	public AbstractPyramid(int octaveCount, IImage src) {
		this.count = octaveCount;
		this.src = src;
		this.octaves = new IOctave[this.count];
	}

	@Override
	public IOctave getOctave(int index) {
		return octaves[index];
	}

	@Override
	public int getCount() {
		return this.count;
	}

	@Override
	public void addOctave(int index, IOctave octave) {
		octaves[index] = octave;
	}

	@Override
	public IImage getSrcImg() {
		return this.src;
	}

	@Override
	public void write(String dir) {
		File dirFile = new File(dir);
		if (!dirFile.exists()) {
			dirFile.mkdirs();
		}
		for (int i = 0; i < this.count; i++) {
			octaves[i].write(dir);
		}
	}
}
