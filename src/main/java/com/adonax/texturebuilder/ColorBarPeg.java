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

public class ColorBarPeg
{
	private int rColor, gColor, bColor;
	private int alpha;
	private int location;
	
	public void setrColor(int rColor)
	{
		this.rColor = rColor;
	}

	public void setgColor(int gColor)
	{
		this.gColor = gColor;
	}

	public void setbColor(int bColor)
	{
		this.bColor = bColor;
	}

	public void setLocation(int location)
	{
		this.location = location;
	}

	public int getrColor()
	{
		return rColor;
	}

	public int getgColor()
	{
		return gColor;
	}

	public int getbColor()
	{
		return bColor;
	}

	public int getAlpha()
	{
		return alpha;
	}

	public int getLocation()
	{
		return location;
	}

	// Constructor
	public ColorBarPeg(int location, int rColor, int gColor,
			int bColor, int alpha)
	{
		this.location = location;
		this.rColor = rColor;
		this.gColor = gColor;
		this.bColor = bColor;
		this.alpha = alpha;
	}	
}
