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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.CharBuffer;

public class ExportCodeJava extends ExportCode {

	public ExportCodeJava(TextureParams params) {
		super("Java", params);
	}

	private String generateBlock() {
		String result =
				"        for (int y = 0; y < height; y++) {\n" +
				"            for (int x = 0; x < width; x++) {\n" +
				"                double noiseValue = SimplexNoise.noise(x/128f, y/128f);\n" +
				"\n" +
				"                // normalization function - 3 possibilties\n" +
				"                // none, average, or abs\n" +
				"                noiseValue = (noiseValue + 1) / 2;\n" +
				"                noiseValue *= 256;\n" +
				"\n" +
				"                pixel[0] = (int)noiseValue;\n" +
				"                pixel[1] = (int)noiseValue;\n" +
				"                pixel[2] = (int)noiseValue;\n" +
				"                pixel[3] = 255; // opaque;\n" +
				"\n" +
				"                raster.setPixel(x, y, pixel);\n" +
				"            }\n" +
				"        }\n";

		return result;
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
