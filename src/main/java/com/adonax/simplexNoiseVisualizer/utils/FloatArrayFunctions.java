package com.adonax.simplexNoiseVisualizer.utils;

public class FloatArrayFunctions
{
	// Linear interpolation of all values in two float arrays.
	public static float[] LerpTwoFloatArrays(float[] a, 
			int aFactor, float[] b, int bFactor)
	{
		float[] c = new float[a.length];
		
		for (int i = 0; i < a.length; i++)
		{
			c[i] = (a[i] * aFactor + b[i] * bFactor) / 
					(aFactor + bFactor);
		}

		return c;
	}
}
