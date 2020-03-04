package com.adonax.sivi.utils;

public class NoiseMachineWithOpenSimplex implements NoiseEngine {

	private OpenSimplexNoise openSimplexNoise = new OpenSimplexNoise();
	
	@Override
	public double noise(double xin, double yin) 
	{	
		return openSimplexNoise.eval(xin, yin);
	}

	@Override
	public double noise(double xin, double yin, double zin) 
	{
		return openSimplexNoise.eval(xin, yin, zin);
	}

}
