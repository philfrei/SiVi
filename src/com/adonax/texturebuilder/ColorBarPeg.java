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
