package org.baade.drone.sift.pyramid.feature;

import java.util.ArrayList;
import java.util.List;

import org.baade.drone.sift.pyramid.IOctave;

public class FeaturePyramid implements IFeaturePyramid{

	private List<IFeatureOctave> octaves;
	
	public FeaturePyramid(){
		octaves = new ArrayList<>();
	}
	
	@Override
	public void add(int index, IOctave octave) {
		octaves.add(index, (IFeatureOctave)octave);
	}

	@Override
	public IOctave get(int index) {
		int count = getOctaveCount();
		IFeatureOctave octave = null;
		if (index >= 0 && index < count) {
			octave = octaves.get(index);
		}
		return octave;
	}

	@Override
	public int getOctaveCount() {
		return octaves.size();
	}

	@Override
	public void wirte(String outDir) {
		if(octaves != null && octaves.size() != 0){
			for (int i = 0; i < octaves.size(); i++) {
				IFeatureOctave octave = octaves.get(i);
				octave.wirte(outDir, i);
			}
		}
		
	}

}
