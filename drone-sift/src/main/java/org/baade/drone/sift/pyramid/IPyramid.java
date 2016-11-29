package org.baade.drone.sift.pyramid;

public interface IPyramid {

	public void add(int index, IOctave octave);

	public IOctave get(int index);
	
	public int getOctaveCount();
	
	public void wirte(String outDir);
}
