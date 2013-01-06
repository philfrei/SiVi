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
package com.adonax.texturebuilder;

import java.util.Arrays;

/**
 * Immutable class representing a color spectrum.
 */
public class ColorSpectrum {

	public final int[] data;  // TODO-SK: temporarily public for TextureFunctions

	public ColorSpectrum() {
		data = new int[256];

		// default data will be black to white gradient
		for (int i = 0;  i < 256;  i++) {
			data[i] = calculateARGB(255, i, i, i);
		}
	}

	public ColorSpectrum(int[] data) {
		this.data = Arrays.copyOf(data, 256);
	}

	public int getRed(int index) {
		return (data[index] & 0x00FF0000) >> 16;
	}

	public int getGreen(int index) {
		return (data[index] & 0x0000FF00) >> 8;
	}

	public int getBlue(int index) {
		return (data[index] & 0x000000FF);
	}

	public int getARGB(int index) {
		return data[index];
	}

	private int calculateARGB(int a, int r, int g, int b) {
		return b + (g << 8) + (r <<16) + (a << 24);
	}
}
