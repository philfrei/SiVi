/*
 *  This file is part of SiVi.
 *
 *  SiVi is free software: you can redistribute it and/or modify it
 *  under the terms of the GNU General Public License as published
 *  by the Free Software Foundation, either version 3 of the
 *  License, or (at your option) any later version.
 *
 *  SiVi is distributed in the hope that it will be useful, but
 *  WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public
 *  License along with SiVi.  If not, see
 *  <http://www.gnu.org/licenses/>.
 */
package com.adonax.simplexNoiseVisualizer.color;

import java.util.Arrays;

/**
 * Immutable class representing a color map.
 */
public class ColorMap {

	private final int[] data;

	public int get(int index)
	{
		return data[index];
	}
	
	public int size() { return data.length; }
	
	public ColorMap() {
		data = new int[256];

		// default data will be black to white gradient
		for (int i = 0;  i < 256;  i++) {
			data[i] = ColorMap.calculateARGB(255, i, i, i);
		}
	}

	public ColorMap(int[] data) {
		this.data = Arrays.copyOf(data, 256);
	}
	
	public static int calculateARGB(int a, int r, int g, int b)
	{
		return b + (g << 8) + (r <<16) + (a << 24);
	}
	
	public static int extractAlpha(int colorARGB)
	{
		return (colorARGB & 0xFF000000) >> 24;
	}
	
	public static int extractRed(int colorARGB)
	{
		return (colorARGB & 0x00FF0000) >> 16;
	}

	public static int extractGreen(int colorARGB)
	{
		return (colorARGB & 0x0000FF00) >> 8;
	}
	
	public static int extractBlue(int colorARGB)
	{
		return (colorARGB & 0x000000FF);
	}
}
