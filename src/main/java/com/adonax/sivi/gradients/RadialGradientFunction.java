package com.adonax.sivi.gradients;

import java.awt.Point;

import com.adonax.sivi.MixerGUI;

public class RadialGradientFunction implements GradientFunction
{
	public enum Fields {
		CENTERX,
		CENTERY,
		RADIUS,
		EDGEVAL,
		CENTERVAL
	}
	
	public final double radius;
	public final float edgeVal, centerVal;
	public final Point centerPoint;
	
	public RadialGradientFunction()
	{
		this(MixerGUI.topPanel.getAppSettings().finalWidth/2, 
				MixerGUI.topPanel.getAppSettings().finalHeight/2,
				MixerGUI.topPanel.getAppSettings().finalWidth/2, 
				0, 0.5f);
	}
	
	public RadialGradientFunction(int centerX, int centerY, double radius,
			float edgeVal, float centerVal)
	{
		this.centerPoint = new Point(centerX, centerY);
		this.radius = radius;
		this.centerVal = centerVal;
		this.edgeVal = edgeVal;
	}
			
	@Override
	public float get(int x, int y)
	{
		float val;
		float distance = (float)centerPoint.distance(x, y);
		{
			val = centerVal + ((edgeVal - centerVal) * (float)(distance/radius));
		}
		return val;
	}
	
	public String toString()
	{
		StringBuilder s = new StringBuilder("RadialGradientFunction:" + "\n");
		s.append("  centerX=" + centerPoint.x + "\n");
		s.append("  centerY=" + centerPoint.y + "\n");
		s.append("  radius=" + radius + "\n");
		s.append("  edgeVal=" + edgeVal + "\n");
		s.append("  centerVal=" + centerVal);
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