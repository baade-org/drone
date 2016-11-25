package org.baade.drone.sift.img;

/**
 * 图片格式
 * @author <a href="http://drone.baade.org">Baade Drone Project</a>
 *
 */
public enum ImageFormat {

	/**
	 * JPG
	 */
	JPG("jpg"),

	/**
	 * JPEG
	 */
	JPEG("jpeg"),
	
	/**
	 * PNG
	 */
	PNG("png"),
	
	/**
	 * GIF
	 */
	GIF("gif"),
	
	/**
	 * TIFF
	 */
	TIFF("tiff"),
	
	/**
	 * SVG
	 */
	SVG("svg"),
	
	
	/**
	 * 默认格式
	 */
	DEFAULT(JPG)
	;
	private String name;
	private ImageFormat(String name){
		this.name = name;
	}
	private ImageFormat(ImageFormat imageFormat){
		this.name = imageFormat.getName();
	}
	
	public String getName(){
		return this.name;
	}
}
