package com.adonax.sivi;

import javax.swing.JTextPane;

public class AboutPane 
{
	static private String txt = 
			"SiVi was written by Phil Freihofner\n"
			+ "with contributions from Sumio Kiyooki.\n"
			+ "\n"
			+ "Simplex Noise was originally developed by \n"
			+ "Ken Perlin. We make use of an implementation \n"
			+ "written and placed in the public domain by\n"
			+ "Stefan Gustavson.\n\n"
			+ "http://www.itn.liu.se/~stegu/simplexnoise/SimplexNoise.java";
	
	public static JTextPane getAboutText()
	{
		JTextPane tp = new JTextPane();
		tp.setEditable(false);
		tp.setText(txt);
		
		return tp;
	}
	
	
}
