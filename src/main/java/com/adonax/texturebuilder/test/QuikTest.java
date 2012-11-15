package com.adonax.texturebuilder.test;


import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;

public class QuikTest
{

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
//		float v = 0.33f;
//		System.out.println(16/31f * v);
//		System.out.println(8/31f * v);
//		System.out.println(4/31f * v);
//		System.out.println(2/31f * v);

		
		BufferedImage img = new BufferedImage(3, 3, 
				BufferedImage.TYPE_INT_ARGB);
		WritableRaster raster = img.getRaster();
		int[] pixel = new int[4];
		
		pixel[0] = 0;
		pixel[1] = 0;
		pixel[2] = 0;
		pixel[3] = 0;
		
		img.setRGB(0, 0, 255);
		
		raster.getPixel(0, 0, pixel);
		
		System.out.println("255  r:" + pixel[0] + "  g:" + pixel[1] 
				+ "  b:" + pixel[2] + "  a:" + pixel[3]);
		
		img.setRGB(0,  0, 255 << 8);
		
		raster.getPixel(0, 0, pixel);
		
		System.out.println("255 << 8 " + (255 << 8) + "   r:" + pixel[0] + "  g:" + pixel[1] 
				+ "  b:" + pixel[2] + "  a:" + pixel[3]);

		img.setRGB(0,  0, 255 << 16);
		
		raster.getPixel(0, 0, pixel);
		
		System.out.println("255 << 16: " + (255 << 16) + "   r:" + pixel[0] + "  g:" + pixel[1] 
				+ "  b:" + pixel[2] + "  a:" + pixel[3]);
		
		img.setRGB(0,  0, 255 << 24);
		
		raster.getPixel(0, 0, pixel);
		
		System.out.println("255 << 16: " + (255 << 24) + "   r:" + pixel[0] + "  g:" + pixel[1] 
				+ "  b:" + pixel[2] + "  a:" + pixel[3]);
		
		
	}

}
