package org.baade.drone.sift.gauss.buff;

import java.util.HashMap;
import java.util.Map;

import org.baade.drone.sift.gauss.IGaussTemplate;

public class ImagePixelGaussBuff {

	private int srcRGB;

	private Map<String, RGBBuff> rgbBuffs;

	public ImagePixelGaussBuff(IGaussTemplate gaussTemplate, int srcRGB) {
		this.srcRGB = srcRGB;
		rgbBuffs = new HashMap<>();
		initBuff(gaussTemplate);
	}

	private void initBuff(IGaussTemplate gaussTemplate) {
		int radius = gaussTemplate.getRadius();
		for (int x = -radius; x <= radius; x++) {
			for (int y = -radius; y <= radius; y++) {
				double weightValue = gaussTemplate.getWeightValue(x, y);
				double r = this.srcRGB >> 16 & 0xFF;
				double g = this.srcRGB >> 8 & 0xFF;
				double b = this.srcRGB & 0xFF;

				r = r * weightValue;
				r = g * weightValue;
				r = b * weightValue;
				
				RGBBuff rgbBuff = new RGBBuff(r, g, b);
				
				String key = getKey(x, y);
				rgbBuffs.put(key, rgbBuff);
			}
		}
	}

	public RGBBuff getRGB(int x, int y) {
		String key = getKey(x, y);
		return rgbBuffs.get(key);
	}

//	public void setRGB(int x, int y, RGBBuff rgbBuff) {
//		String key = getKey(x, y);
//		rgbBuffs.put(key, rgbBuff);
//	}

	/**
	 * 用xy的绝对值作为KEY
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	private String getKey(int x, int y) {
		// return Math.abs(x) + "_" + Math.abs(y);
		return x + "_" + y;
	}

}
