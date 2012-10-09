package com.adonax.texturebuilder;

import java.util.Comparator;

class ColorBarPegSort implements Comparator<ColorBarPeg>
{
	@Override
	public int compare(ColorBarPeg o1, ColorBarPeg o2) 
	{
		Integer i1 = o1.getLocation();
		Integer i2 = o2.getLocation();
		return i1.compareTo(i2);
	}		
}
