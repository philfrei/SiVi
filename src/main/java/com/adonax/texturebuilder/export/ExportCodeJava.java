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

import com.adonax.texturebuilder.CombineParams;
import com.adonax.texturebuilder.TextureParams;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.CharBuffer;
import java.util.List;

public class ExportCodeJava extends ExportCode {

	public ExportCodeJava(List<TextureParams> textureParamsList, CombineParams combineParams) {
		super("Java", textureParamsList, combineParams);
	}

	private String generateCodeFor(TextureParams tp) {
		StringBuilder sb = new StringBuilder();

		sb.append("        textures.add(TextureFunctions.generate(width, height, new TextureParams(\n");
		sb.append("                ").append(tp.xScale).append("f,\n");
		sb.append("                ").append(tp.yScale).append("f,\n");
		sb.append("                ").append(tp.xTranslate).append("f,\n");
		sb.append("                ").append(tp.yTranslate).append("f,\n");
		sb.append("                ").append(tp.minClamp).append("f,\n");
		sb.append("                ").append(tp.maxClamp).append("f,\n");
		sb.append("                TextureParams.NoiseNormalization.").append(tp.normalize).append(",\n");
		sb.append("                new ColorMap( new int[] { ").append(tp.colorMap.get(0));
		for (int i = 1;  i < tp.colorMap.size();  i++) {
			sb.append(", ").append(tp.colorMap.get(i));
		}
		sb.append("} )\n");
		sb.append("        )));\n");

		return sb.toString();
	}

	private String generateCodeFor(CombineParams cp) {
		StringBuilder sb = new StringBuilder();

		sb.append("        Map<CombineParams.ChannelMode, CombineParams.GroupMode> groupModes = new HashMap<CombineParams.ChannelMode, CombineParams.GroupMode>();\n");
		for (CombineParams.ChannelMode channelMode : cp.getGroups()) {
			sb.append("        groupModes.put(CombineParams.ChannelMode.");
			sb.append(channelMode);
			sb.append(", CombineParams.GroupMode.");
			sb.append(cp.getGroupMode(channelMode));
			sb.append(");\n");
		}

		sb.append("\n");
		sb.append("        Map<CombineParams.ChannelMode, Integer> stage2weights = new HashMap<CombineParams.ChannelMode, Integer>();\n");
		for (CombineParams.ChannelMode channelMode : cp.getGroups()) {
			sb.append("        stage2weights.put(CombineParams.ChannelMode.");
			sb.append(channelMode);
			sb.append(", ");
			sb.append(cp.getStage2Weight(channelMode));
			sb.append(");\n");
		}

		sb.append("\n");
		sb.append("        CombineParams combine = new CombineParams(\n");
		sb.append("                ").append(cp.numChannels).append(",\n");

		sb.append("                new CombineParams.ChannelMode[] { CombineParams.ChannelMode.");
		sb.append(cp.getChannelMode(0));
		for (int i = 1;  i < cp.numChannels;  i++) {
			sb.append(", CombineParams.ChannelMode.").append(cp.getChannelMode(i));
		}
		sb.append(" },\n");

		sb.append("                new int[] { ");
		sb.append(cp.getStage1Weight(0));
		for (int i = 1;  i < cp.numChannels;  i++) {
			sb.append(", ").append(cp.getStage1Weight(i));
		}
		sb.append(" },\n");

		sb.append("                groupModes,\n");
		sb.append("                stage2weights\n");
		sb.append("        );\n");

		return sb.toString();

	}

	private String generateBlock() {
		StringBuilder sb = new StringBuilder();

		sb.append("\n");
		sb.append("        List<TextureData> textures = new ArrayList<TextureData>();\n");
		for (TextureParams tp : textureParamsList) {
			sb.append(generateCodeFor(tp));
		}
		sb.append("\n");
		sb.append(generateCodeFor(combineParams));

		return sb.toString();
	}

	@Override
	public String getCode() {
		try {
			InputStream resourceAsStream = getClass().getResourceAsStream("TemplateJava.txt");
			CharBuffer buffer = CharBuffer.allocate(resourceAsStream.available());

			BufferedReader reader = new BufferedReader(new InputStreamReader(resourceAsStream));

			reader.read(buffer);
			buffer.rewind();

			String template = buffer.toString();

			reader.close();
			resourceAsStream.close();

			return template.replace("{0}", generateBlock());
		} catch (IOException e) {
			throw new RuntimeException("exception caught", e);
		}
	}
}
