package com.adonax.sivi;

import com.adonax.sivi.gradients.GradientFunction;

public class MixerModel
{
	public enum Fields {
		WEIGHTS,
		MASTER,
		MAPPING,
		GRADIENT_DATA
	}
	
	public enum MapMethod{
		CLAMP,
		RING
	}
	
	public final float[] weights;
	public final float master;
	public final MapMethod mapping;
	public final NoiseData gradientData;
	
	public MixerModel(TopPanelModel settings)
	{
		weights = new float[settings.octaves];
		for (int i = 0; i < 4; i++) weights[i] = 0.25f;
			
		master = 1;
		mapping = MapMethod.CLAMP;
		gradientData = new NoiseData(settings.finalWidth, 
				settings.finalHeight);
	}
	
	public MixerModel(float[] weights, float master, MapMethod mapping, 
			NoiseData gradientData)
	{
		this.weights = weights;
		this.master = master;
		this.mapping = mapping;
		this.gradientData = gradientData;
	}

	public MixerModel(float[] weights, float master, MapMethod mapping, 
			GradientFunction gradientFunction, TopPanelModel settings)
	{
		this.weights = weights;
		this.master = master;
		this.mapping = mapping;

		int width = settings.finalWidth;
		int height = settings.finalHeight;
		float[] gradientData  = new float[ width * height];
		         
		for (int j = 0; j < height; j++)
		{
			for (int i = 0; i < width; i++)
			{
				gradientData[(j * width) + i] = 
						gradientFunction.get(i, j);
			}
		}

		this.gradientData = new NoiseData(width, height, gradientData);		
	}
	
	public static MixerModel updateMixSetting(MixerModel mixer,
			MixerModel.Fields field, Object value)
	{
		float[] weights = mixer.weights;
		float master = mixer.master;
		MapMethod mapping = mixer.mapping;
		NoiseData gradientData = mixer.gradientData;

		switch (field)
		{
		case WEIGHTS: weights = (float[])value; break;
		case MASTER: master = (Float)value; break;
		case MAPPING: mapping = (MapMethod)value; break;
		case GRADIENT_DATA: gradientData = (NoiseData)value;
		}
		
		return new MixerModel(
				weights,
				master,
				mapping,
				gradientData
		);
	}
}
