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
 * Extend to support a new language for export.
 */
abstract public class ExportCode {

	private final String lang;
	private final TextureParams params;

	public ExportCode(String lang, TextureParams params) {
		this.lang = lang;
		this.params = params;
	}

	/**
	 * @return Language string
	 */
	public String getLang() {
		return lang;
	}

	/**
	 * @return self-contained code that implements specified texture.
	 */
	abstract public String getCode();
}
