package com.adonax.simplexNoiseVisualizer.gradients;

import com.adonax.simplexNoiseVisualizer.MixerGUI;

public class SinusoidalGradientFunction implements GradientFunction
{
	public enum Fields {
		PERIOD,
		ORIGINX,
		ORIGINY,
		THETA,
		HIGHVAL,
		LOWVAL
	}
	public final float period;
	public final int originX, originY;	
	public final float theta;
	public final float highVal, lowVal;
	
	public SinusoidalGradientFunction()
	{
		this(0, 0, MixerGUI.topPanel.getAppSettings().finalWidth/4, 
				0, 0.5f, 0);
	}
	
	public SinusoidalGradientFunction(int originX, int originY, 
			float period, float theta, 
			float highVal, float lowVal)
	{
		this.period = period;
		this.originX = originX;
		this.originY = originY;
		this.theta = theta;
		this.highVal = highVal;
		this.lowVal = lowVal;
	}
			
	@Override
	public float get(int x, int y)
	{
		float val = 0;
		
		// for now, just use x component
		double radians = ((x - originX)% period) / period;
		val = (float)Math.sin(radians * 2 * Math.PI);
		
		float factor = (highVal - lowVal)/2;
		val = lowVal + factor * val;
		
		return val;
	}
	
	public String toString()
	{
		StringBuilder s = new StringBuilder("SinusoidalGradientFunction:" + "\n");
		s.append("  period=" + period + "\n");
		s.append("  origin=[" + originX + ", " + originY + "\n");
		s.append("  theta=" + theta + "\n");
		s.append("  highVal=" + highVal + "\n");
		s.append("  lowVal=" + lowVal);
		return s.toString();
	}
	
	public void dump()
	{
		dump(0, 0, MixerGUI.topPanel.getAppSettings().finalWidth, 
				MixerGUI.topPanel.getAppSettings().finalHeight);
	}
	
	public void dump(int x0, int y0, int x1, int y1)
	{
		int idx = 0;
		for (int j = y0; j < y1; j++)
		{
			for (int i = x0; i < x1; i++)
			{
				System.out.println("" + idx++ + ":" + get(i, j));
			}
		}
	}
	
}