package com.adonax.simplexNoiseVisualizer.gradients;

import com.adonax.simplexNoiseVisualizer.MixerGUI;

public class LinearGradientFunction implements GradientFunction
{
	public enum Fields {
		FUNCTIONWIDTH,
		FUNCTIONHEIGHT,
		XLEFT,
		XRIGHT,
		YTOP,
		YBOTTOM
	}
	
	public final float xLeft, xRight, yTop, yBottom;
	public final int functionWidth, functionHeight;
	
	public LinearGradientFunction()
	{
		this(MixerGUI.topPanel.getAppSettings().finalWidth, 
				MixerGUI.topPanel.getAppSettings().finalHeight, 
				0, 0.5f, 0, 0);
	}
	
	public LinearGradientFunction(int functionWidth, int functionHeight,
			float xLeft, float xRight, float yTop, float yBottom)
	{
		this.functionWidth = functionWidth;
		this.functionHeight = functionHeight;
		this.xLeft = xLeft;
		this.xRight = xRight;
		this.yTop = yTop;
		this.yBottom = yBottom;
	}
			
	@Override
	public float get(int x, int y)
	{
		float val = (((xRight - xLeft)/(float)functionWidth) * x + xLeft)
				+ (((yBottom - yTop)/(float)functionHeight) * y + yTop);
		return val;
	}
	
	public String toString()
	{
		StringBuilder s = new StringBuilder("LinearGradientFunction:" + "\n");
		s.append("  functionWidth=" + functionWidth + "\n");
		s.append("  functionHeight=" + functionHeight + "\n");
		s.append("  xLeft=" + xLeft + "\n");
		s.append("  xRight=" + xRight + "\n");
		s.append("  yTop=" + yTop + "\n");
		s.append("  yBottom=" + yBottom);
		return s.toString();
	}
	
	public void dump()
	{
		for (int i = 0, n = functionWidth * functionHeight; i < n; i++)
			System.out.println("i:" + get(i % functionWidth, i / functionWidth));
	}
}