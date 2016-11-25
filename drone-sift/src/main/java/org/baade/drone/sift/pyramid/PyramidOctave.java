package org.baade.drone.sift.pyramid;

import java.util.HashMap;
import java.util.Map;

import org.baade.drone.sift.img.IImage;

public class PyramidOctave implements IPyramidOctave {

	private Map<Integer, IImage> octave;
	
	public PyramidOctave(){
		
		octave = new HashMap<>();
	}

	@Override
	public void put(int index, IImage image) {
		octave.put(index, image);
	}

	@Override
	public void wirte(String outDir, int octaveNum) {
		if(octave != null && octave.size() != 0){
			for(Map.Entry<Integer, IImage> o : octave.entrySet()){
				int gaussLevel = o.getKey();
				IImage img = o.getValue();
				img.wirte(outDir, octaveNum, gaussLevel);
			}
		}
		
	}
}
