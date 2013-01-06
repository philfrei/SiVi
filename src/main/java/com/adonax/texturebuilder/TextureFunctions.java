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

import com.adonax.utils.SimplexNoise;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


// temporarily used by combine()
class ChannelGroup {
	ArrayList<Integer> members = new ArrayList<Integer>();
	// x, y noise data, merged via "mode" function
	float[][] noiseVals = new float[256][256];
}


/**
 * Pure functions to generate and combine textures.
 */
public class TextureFunctions {

	/**
	 * Generate the data necessary to create a single texture.
	 *
	 * @param width     image width
	 * @param height    image height
	 * @param tp        texture parameters from SimplexTextureSource
	 *
	 * @return TextureData
	 */
	public static TextureData generate(int width, int height, TextureParams tp) {
		float[][] noiseArray = new float[width][height];

		for (int j = 0; j < height; j++) {
			float y = (j * tp.yScale) / height + tp.yTranslate;

			for (int i = 0; i < width; i++) {
				float x = (i * tp.xScale) / width + tp.xTranslate;

				float noiseVal = (float) SimplexNoise.noise(x, y);
				noiseVal = Math.min(Math.max(noiseVal, tp.minClamp), tp.maxClamp);

				if (tp.normalize == TextureParams.NoiseNormalization.SMOOTH) {
					noiseVal = (noiseVal + 1) / 2;
				} else if (tp.normalize == TextureParams.NoiseNormalization.ABS) {
					noiseVal = Math.abs(noiseVal);
				}

				noiseArray[i][j] = noiseVal;
			}
		}

		return new TextureData(width, height, noiseArray, tp.spectrum);
	}

	/**
	 * Combine multiple textures into one.
	 *
	 * @param textures    to combine
	 * @param cp          combine parameters from TextureCombiner
	 */
	// TODO-SK: temporarily imageCombined passed in and thus not a pure function
	public static void combine(List<TextureData> textures, CombineParams cp, BufferedImage imageCombined) {
		int channels = textures.size();

		int width = -1;
		int height = -1;

		// ensure all textures are the same size
		for (TextureData texture : textures) {
			if (width == -1) {
				width = texture.width;
			} else {
				assert(width == texture.width);
			}

			if (height == -1) {
				height = texture.height;
			} else {
				assert(height == texture.height);
			}
		}

		// There are between 1-4 channel groups and each one has at least one member.
		// ChannelGroup.members is a set of indices ranging from 0-3

		float[][] noiseArray = new float[width][height];

		CombineParams combineParams = cp;
		Map<CombineParams.ChannelMode, ChannelGroup> channelData = new HashMap<CombineParams.ChannelMode, ChannelGroup>();

		for (CombineParams.ChannelMode stage1ChannelMode : combineParams.getGroups())
		{
			ChannelGroup cg = new ChannelGroup();
			for (int i = 0;  i < channels;  i++) {
				if (stage1ChannelMode == combineParams.getChannelMode(i)) {
					cg.members.add(i);
				}
			}
			channelData.put(stage1ChannelMode, cg);

			// SUM mode
			if (stage1ChannelMode == CombineParams.ChannelMode.ADD)
			{
				float[] weight = new float[channels];
				for (int i : cg.members) {
					weight[i] = combineParams.getStage1Weight(i) / 64f;
				}

				for (int j = 0; j < 256; j++) {
					for (int i = 0; i < 256; i++) {
						float sum = 0;
						for (int idx : cg.members) {
							sum += textures.get(idx).noiseArray[i][j] * weight[idx];
						}
						cg.noiseVals[i][j] = sum;
					}
				}
			}

			if (stage1ChannelMode == CombineParams.ChannelMode.LERP)
			{
				float[] weight = new float[channels];
				float weightSum = 0;

				for (int i : cg.members) {
					weightSum += combineParams.getStage1Weight(i);
				}
				for (int i : cg.members) {
					weight[i] = combineParams.getStage1Weight(i) / weightSum;
				}

				for (int j = 0; j < 256; j++) {
					for (int i = 0; i < 256; i++) {
						float sum = 0;
						for (int idx : cg.members) {
							sum += textures.get(idx).noiseArray[i][j] * weight[idx];
						}
						cg.noiseVals[i][j] = sum;
					}
				}
			}

			if (stage1ChannelMode == CombineParams.ChannelMode.SIN)
			{
				for (int j = 0; j < 256; j++) {
					for (int i = 0; i < 256; i++) {
						float sum = 0;
						for (int idx : cg.members) {
							sum += textures.get(idx).noiseArray[i][j] * (combineParams.getStage1Weight(idx) / 128f);
						}
						cg.noiseVals[i][j] = (float)Math.sin(i/24f + sum);
					}
				}
			}

			if (stage1ChannelMode == CombineParams.ChannelMode.XDIM)
			{
				for (int j = 0; j < 256; j++) {
					for (int i = 0; i < 256; i++) {
						float sum = 0;
						for (int idx : cg.members) {
							sum += textures.get(idx).noiseArray[i][j] * (combineParams.getStage1Weight(idx) / 128f);
						}
						cg.noiseVals[i][j] = (i/256f + sum);
					}
				}
			}

			if (stage1ChannelMode == CombineParams.ChannelMode.YDIM)
			{
				for (int j = 0; j < 256; j++) {
					for (int i = 0; i < 256; i++) {
						float sum = 0;
						for (int idx : cg.members) {
							sum += textures.get(idx).noiseArray[i][j] * (combineParams.getStage1Weight(idx) / 128f);
						}
						cg.noiseVals[i][j] = (j/256f + sum);
					}
				}
			}


			if (stage1ChannelMode == CombineParams.ChannelMode.CIRC)
			{
				Point middle = new Point(128, 128);

				for (int j = 0; j < 256; j++) {
					for (int i = 0; i < 256; i++) {
						float sum = 0;
						for (int idx : cg.members) {
							sum += textures.get(idx).noiseArray[i][j] * (combineParams.getStage1Weight(idx) / 128f);
						}
						float r = (float)middle.distance(i, j)/192f;
						r = Math.min(r, 1);

						cg.noiseVals[i][j] = (r + sum);
					}
				}
			}
		}

		// now turn this into a graphic...via the colormap

		// stage 2

		ArrayList<CombineParams.ChannelMode> channelArray = new ArrayList<CombineParams.ChannelMode>();
		for (int c = 0;  c < channels;  c++) {
			CombineParams.ChannelMode channelMode = combineParams.getChannelMode(c);

			if (!channelArray.contains(channelMode)) {
				channelArray.add(channelMode);
			}
		}

		int chCount = channelArray.size();
		double[] weight = new double[chCount];
		int[][] cMapData = new int[chCount][256]; // ch, x,
				//sts[cg.members.get(0)].colorAxis.data;

		for (int i = 0; i < chCount; i++) {
			weight[i] = combineParams.getStage2Weight(channelArray.get(i));  // value from slider/txtfield
			cMapData[i] = textures.get(channelData.get(channelArray.get(i)).members.get(0)).spectrum.data;  //colormap data, shared
		}

		double lerpSum = 0;
		for (int cgIdx = 0; cgIdx < chCount; cgIdx++) {
			if (combineParams.getGroupMode(channelArray.get(cgIdx)) == CombineParams.GroupMode.LERP) {
				lerpSum += weight[cgIdx];
			}
		}
		for (int cgIdx = 0; cgIdx < chCount; cgIdx++) {
			if (combineParams.getGroupMode(channelArray.get(cgIdx)) == CombineParams.GroupMode.LERP) {
				weight[cgIdx] /= lerpSum;  // lerp factor
			} else {
				// presumably SUM or MOD
				weight[cgIdx] /= 64;  // add factor
			}
		}  // weights are now a fraction of 1

		double rPixel, gPixel, bPixel;
		for (int j = 0; j < 256; j++) {
			for (int i = 0; i < 256; i++) {
				// for each pixel
				rPixel = 0;
				gPixel = 0;
				bPixel = 0;

				float denormalizingFactor = 255f;
				for (int channelGroupIdx = 0; channelGroupIdx < chCount; channelGroupIdx++) {
					CombineParams.ChannelMode stage1ChannelModeKey = channelArray.get(channelGroupIdx);

					int colorMapIdx = (int)(channelData.get(stage1ChannelModeKey).noiseVals[i][j] * denormalizingFactor);

					if (combineParams.getGroupMode(stage1ChannelModeKey) == CombineParams.GroupMode.ADD ||
							combineParams.getGroupMode(stage1ChannelModeKey) == CombineParams.GroupMode.LERP) {
						colorMapIdx = Math.min(255, Math.max(0, colorMapIdx));
					} else if (combineParams.getGroupMode(stage1ChannelModeKey) == CombineParams.GroupMode.RING) {
						while (colorMapIdx < 0) colorMapIdx += 256;
						colorMapIdx %= 256;
					} else {
						throw new RuntimeException("not handled! " + stage1ChannelModeKey);
					}

					rPixel += ColorAxis.getRed(cMapData[channelGroupIdx][colorMapIdx]) * weight[channelGroupIdx];
					gPixel += ColorAxis.getGreen(cMapData[channelGroupIdx][colorMapIdx]) * weight[channelGroupIdx];
					bPixel += ColorAxis.getBlue(cMapData[channelGroupIdx][colorMapIdx]) * weight[channelGroupIdx];

				}
				int pixel = ColorAxis.calculateARGB(255,
						(int)Math.min(255, Math.max(0, rPixel)),
						(int)Math.min(255, Math.max(0, gPixel)),
						(int)Math.min(255, Math.max(0, bPixel)));

				imageCombined.setRGB(i, j, pixel);
			}
		}

		// TODO-SK: temporary
		//return new TextureData(width, height, noiseArray, tp.spectrum);
	}
}
