package org.baade.drone.sift.pyramid;

import java.util.HashMap;
import java.util.Map;

import org.baade.drone.sift.img.IImage;

public class GaussPyramid implements IGaussPyramid {
	
	private IImage sourceImg;
	
	private Map<Integer, IPyramidOctave> pyramid;
	
	public GaussPyramid(IImage sourceImg){
		this.sourceImg = sourceImg;
		pyramid = new HashMap<>();
	}

	@Override
	public IImage getSourceImg() {
		return this.sourceImg;
	}

	@Override
	public void put(int index, IPyramidOctave pyramidOctave) {
		pyramid.put(index, pyramidOctave);
	}

	@Override
	public void wirte(String outDir) {
		if(pyramid != null && pyramid.size() != 0){
			for(Map.Entry<Integer, IPyramidOctave> p : pyramid.entrySet()){
				int octaveNum = p.getKey();
				IPyramidOctave octave = p.getValue();
				octave.wirte(outDir, octaveNum);
			}
		}
	}

}
