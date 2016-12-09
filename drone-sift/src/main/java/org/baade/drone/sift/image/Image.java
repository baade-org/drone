package org.baade.drone.sift.image;

import java.awt.image.BufferedImage;

public class Image extends AbstractImage {

	public Image(int width, int height, int indexInOctave) {
		super(width, height, indexInOctave);
	}

	public Image(BufferedImage buffImg) {
		super(buffImg, 0);
	}
	
	public Image(BufferedImage buffImg, int indexInOctave) {
		super(buffImg, indexInOctave);
	}
}
