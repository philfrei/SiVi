package com.adonax.sivi.utils;

public class NoiseEngines {

	public enum Sources 
	{
		OPEN_SIMPLEX("Open Simplex", new NoiseMachineWithOpenSimplex()),
		PERLIN_SIMPLEX("Simplex Noise", new NoiseMachineWithSimplexNoise());
		
		private final String name;
		private final NoiseEngine noiseEngine;
		
		Sources (String name, NoiseEngine noiseEngine)
		{
			this.name = name;
			this.noiseEngine = noiseEngine;
		}
		
		public String getName() { return name;}
		public NoiseEngine getNoiseEngine() { return noiseEngine; }
	}

	
	
}
