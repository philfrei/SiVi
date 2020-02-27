package com.adonax.simplexNoiseVisualizer;

public class TopPanelModel
{
	public enum Fields {
		OCTAVES,
		FINAL_WIDTH,
		FINAL_HEIGHT,
		COLOR_MAPS
	}
	
	public final int octaves;
	public final int finalWidth;
	public final int finalHeight;
	public final int colorMaps;	
	
	public TopPanelModel()
	{
		this.octaves = 4;
		this.finalWidth = 480;
		this.finalHeight = 240;
		this.colorMaps = 6;
	}

	public TopPanelModel(int octaves, int finalWidth,
			int finalHeight, int colorMaps)
	{
		this.octaves = octaves;
		this.finalWidth = finalWidth;
		this.finalHeight = finalHeight;
		this.colorMaps = colorMaps;
	}
	
	public static TopPanelModel setAppSetting(TopPanelModel tpm,
			TopPanelModel.Fields field, Object value)
	{
		
		// warning: local shadowing 'octaves'
		int octaves = tpm.octaves;
		int finalWidth = tpm.finalWidth;
		int finalHeight = tpm.finalHeight;
		int colorMaps = tpm.colorMaps;
		
		switch (field)
		{
		case OCTAVES: octaves = (Integer)value; break;
		case FINAL_WIDTH: finalWidth = (Integer)value; break;
		case FINAL_HEIGHT: finalHeight = (Integer)value; break;
		case COLOR_MAPS: colorMaps = (Integer)value; break;
		}	
		
		return new TopPanelModel(octaves, finalWidth, 
				finalHeight, colorMaps);
	}
}
