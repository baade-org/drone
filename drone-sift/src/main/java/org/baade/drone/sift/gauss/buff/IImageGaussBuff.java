package org.baade.drone.sift.gauss.buff;

import org.baade.drone.sift.img.IImage;

public interface IImageGaussBuff extends IImage{

	public ImagePixelGaussBuff getImagePixelGaussBuff(int x, int y);
}
