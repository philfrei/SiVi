package com.adonax.sivi.utils;

public class NoiseEngines {

	public enum Sources 
	{
		OPEN_SIMPLEX(
				"Open Simplex", new NoiseMachineWithOpenSimplex(), 1/256f),
		PERLIN_SIMPLEX(
				"Simplex Noise", new NoiseMachineWithSimplexNoise(), 1/512f);
		
		private final String name;
		private final NoiseEngine noiseEngine;
		private final float latticeDFactor;
		
		/*
		 * Parameters
		 *   name 			String that will appear in cbo selector
		 *   noiseEngine	class that includes noise() call
		 *   latticeDFactor Intended to help "equalize" the scaling values.
		 *   				The notion here is that a single unit of 
		 *   				scaling will yield a field where the lattices 
		 *   				are spaced 256 pixels apart. 
		 */
		Sources (String name, NoiseEngine noiseEngine, float latticeDFactor)
		{
			this.name = name;
			this.noiseEngine = noiseEngine;
			this.latticeDFactor = latticeDFactor;
		}
		
		public String getName() { return name;}
		public NoiseEngine getNoiseEngine() { return noiseEngine; }
		public float getLatticeFactor() {return latticeDFactor; }
	}
}
