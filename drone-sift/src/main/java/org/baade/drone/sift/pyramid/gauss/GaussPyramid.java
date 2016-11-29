package org.baade.drone.sift.pyramid.gauss;

import java.util.ArrayList;
import java.util.List;

import org.baade.drone.sift.img.IImage;
import org.baade.drone.sift.pyramid.IOctave;

public class GaussPyramid implements IGaussPyramid {
	
	private IImage sourceImg;
	private List<IGaussOctave> octaves;
	
	public GaussPyramid(IImage sourceImg){
		this.sourceImg = sourceImg;
		octaves = new ArrayList<>();
	}

	@Override
	public IImage getSourceImg() {
		return this.sourceImg;
	}

	@Override
	public void wirte(String outDir) {
		if(octaves != null && octaves.size() != 0){
			for (int i = 0; i < octaves.size(); i++) {
				IGaussOctave octave = octaves.get(i);
				octave.wirte(outDir, i);
			}
		}
	}

	@Override
	public void add(int index, IOctave octave) {
		if(octave instanceof IGaussOctave){
			IGaussOctave o = (IGaussOctave)octave;
			octaves.add(index, o);
		}
	}

	@Override
	public IOctave get(int index) {
		int count = getOctaveCount();
		IGaussOctave octave = null;
		if (index >= 0 && index < count) {
			octave = octaves.get(index);
		}
		return octave;
	}

	@Override
	public int getOctaveCount() {
		return octaves.size();
	}

}
