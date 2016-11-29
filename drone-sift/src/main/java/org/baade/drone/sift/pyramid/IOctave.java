package org.baade.drone.sift.pyramid;

import org.baade.drone.sift.img.IImage;

public interface IOctave {

	public void add(int index, IImage img);

	public IImage get(int index);

	public int getImgCount();

	public void wirte(String outDir, int octaveNum);

	public int getWidth();

	public int getHeight();
}
