package com.adonax.sivi.utils;

public class NoiseMachineWithSimplexNoise implements NoiseEngine {

	@Override
	public double noise(double xin, double yin) 
	{	
		return SimplexNoise.noise(xin, yin);
	}

	@Override
	public double noise(double xin, double yin, double zin) 
	{
		return SimplexNoise.noise(xin, yin, zin);
	}
}
