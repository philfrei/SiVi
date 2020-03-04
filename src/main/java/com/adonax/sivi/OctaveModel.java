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
package com.adonax.sivi;

/**
 * Immutable class representing parameters for a single texture channel.
 */
public class OctaveModel {

	public enum Fields {
		XSCALE,
		YSCALE,
		XTRANSLATE,
		YTRANSLATE,
		MINCLAMP,
		MAXCLAMP,
		NORMALIZE
	}
	
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

	public OctaveModel()
	{
		this(1f, 1f, 0f, 0f, -1f, 1f, 
				OctaveModel.NoiseNormalization.SMOOTH);
	}
	
	public OctaveModel(float xScale,
						 float yScale,
						 float xTranslate,
						 float yTranslate,
						 float minClamp,
						 float maxClamp,
			NoiseNormalization normalize) {
		this.xScale = xScale;
		this.yScale = yScale;
		this.xTranslate = xTranslate;
		this.yTranslate = yTranslate;
		this.minClamp = minClamp;
		this.maxClamp = maxClamp;
		this.normalize = normalize;
	}
	
	public static OctaveModel setTextureParam(OctaveModel textureParams, 
			OctaveModel.Fields field, Object value)
	{
		float xScale = textureParams.xScale;
		float yScale = textureParams.yScale;
		float xTranslate = textureParams.xTranslate;
		float yTranslate = textureParams.yTranslate;
		float minClamp = textureParams.minClamp;
		float maxClamp = textureParams.maxClamp;
		OctaveModel.NoiseNormalization normalize 
				= textureParams.normalize;

		switch (field)
		{
		case XSCALE: xScale = (Float)value; break;
		case YSCALE: yScale = (Float)value; break;
		case XTRANSLATE: xTranslate = (Float)value; break;
		case YTRANSLATE: yTranslate = (Float)value; break;
		case MINCLAMP: minClamp = (Float)value; break;
		case MAXCLAMP: maxClamp = (Float)value; break;
		case NORMALIZE: normalize = 
				(OctaveModel.NoiseNormalization)value; break;
		}
		
		return new OctaveModel(
				xScale,
				yScale,
				xTranslate,
				yTranslate,
				minClamp,
				maxClamp,
				normalize
		);
	}
}
