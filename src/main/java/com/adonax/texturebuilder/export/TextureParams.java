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
package com.adonax.texturebuilder.export;

/**
 * Immutable class representing parameters for a single texture source.
 */
public class TextureParams {

	public enum NoiseNormalization {
		SMOOTH,
		ABS,
		NONE
	}

	// typically ranges from 0.125 to 128.0
	public final float xScale;
	public final float yScale;

	// typically ranges from -256.0 to 256.0
	public final float xTranslate;
	public final float yTranslate;

	// typically ranges from -1.0 to 1.0
	public final float minClamp;
	public final float maxClamp;

	public final NoiseNormalization normalize;

	public final ColorSpectrum spectrum;

	public TextureParams(float xScale,
						 float yScale,
						 float xTranslate,
						 float yTranslate,
						 float minClamp,
						 float maxClamp,
			NoiseNormalization normalize,
				 ColorSpectrum spectrum) {
		this.xScale = xScale;
		this.yScale = yScale;
		this.xTranslate = xTranslate;
		this.yTranslate = yTranslate;
		this.minClamp = minClamp;
		this.maxClamp = maxClamp;
		this.normalize = normalize;
		this.spectrum = spectrum;
	}
}
