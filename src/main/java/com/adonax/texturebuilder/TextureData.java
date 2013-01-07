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

/**
 * Immutable data structure containing the return result from a single texture
 * generation function.  Contains all necessary data to generate an image.
 */
public class TextureData {

	public final int width;
	public final int height;

	public final float[][] noiseArray;
	public final ColorSpectrum spectrum;

	public TextureData(int width, int height, float[][] noiseArray, ColorSpectrum spectrum) {
		this.width = width;
		this.height = height;

		assert(width > 0);
		assert(height > 0);
		assert(noiseArray.length == width * height);
		this.noiseArray = noiseArray;
		this.spectrum = spectrum;
	}
}
