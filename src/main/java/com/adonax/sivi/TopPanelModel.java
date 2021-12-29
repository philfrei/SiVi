package com.adonax.sivi;

public class TopPanelModel
{
	public enum Fields {
		OCTAVES,
		FINAL_WIDTH,
		FINAL_HEIGHT,
		COLOR_MAPS,
		IS_VERTICALLY_SYMMETRIC,
		IS_HORIZONTALLY_SYMMETRIC
	}
	
	public final int octaves;
	public final int finalWidth;
	public final int finalHeight;
	public final int colorMaps;
	public final boolean isVerticallySymmetric;
	public final boolean isHorizontallySymmetric;
	
	public TopPanelModel()
	{
		this.octaves = 4;
		this.finalWidth = 480;
		this.finalHeight = 240;
		this.colorMaps = 6;
		this.isVerticallySymmetric = false;
		this.isHorizontallySymmetric = false;
	}

	public TopPanelModel(int octaves, int finalWidth, int finalHeight, int colorMaps, 
			boolean isVerticallySymmetric, boolean isHorizontallySymmetric)
	{
		this.octaves = octaves;
		this.finalWidth = finalWidth;
		this.finalHeight = finalHeight;
		this.colorMaps = colorMaps;
		this.isVerticallySymmetric = isVerticallySymmetric;
		this.isHorizontallySymmetric = isHorizontallySymmetric;
	}
	
	public static TopPanelModel setAppSetting(TopPanelModel tpm,
			TopPanelModel.Fields field, Object value)
	{	
		// warning: local shadowing
		int octaves = tpm.octaves;
		int finalWidth = tpm.finalWidth;
		int finalHeight = tpm.finalHeight;
		int colorMaps = tpm.colorMaps;
		boolean isVerticallySymmetric = tpm.isVerticallySymmetric;
		boolean isHorizontallySymmetric = tpm.isHorizontallySymmetric;
		
		switch (field)
		{
		case OCTAVES: octaves = (Integer)value; break;
		case FINAL_WIDTH: finalWidth = (Integer)value; break;
		case FINAL_HEIGHT: finalHeight = (Integer)value; break;
		case COLOR_MAPS: colorMaps = (Integer)value; break;
		case IS_VERTICALLY_SYMMETRIC: isVerticallySymmetric = (boolean)value; break;
		case IS_HORIZONTALLY_SYMMETRIC: isHorizontallySymmetric = (boolean)value;
		}	
		
		return new TopPanelModel(octaves, finalWidth, finalHeight, colorMaps, 
				isVerticallySymmetric, isHorizontallySymmetric);
	}
}
