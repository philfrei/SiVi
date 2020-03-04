package com.adonax.sivi.gradients;

public class GradientGUIModel
{
	public final GradientFunction[] gradients;
		public final boolean[] selected;
	
	public static enum Fields {
		LINEAR,
		RADIAL,
		SINUSOIDAL,
		SELECTED
	}
	
	public GradientGUIModel()
	{	
		gradients = new GradientFunction[3];
		gradients[0] = new LinearGradientFunction();
		gradients[1] = new RadialGradientFunction();
		gradients[2] = new SinusoidalGradientFunction();
		
		selected = new boolean[3];
	}
	
	public GradientGUIModel(LinearGradientFunction linearGradient,
			RadialGradientFunction radialGradient,
			SinusoidalGradientFunction sinusoidalGradient,
			boolean[] selected)
	{	
		gradients = new GradientFunction[3];
		gradients[0] = linearGradient;
		gradients[1] = radialGradient;
		gradients[2] = sinusoidalGradient;
		
		this.selected = selected;
	}

	static public GradientGUIModel updateGradientGUIModel(
			GradientGUIModel model, 
			GradientGUIModel.Fields field, Object value)
	{
		LinearGradientFunction linear = 
				(LinearGradientFunction)model.gradients[0]; 
		RadialGradientFunction radial = 
				(RadialGradientFunction)model.gradients[1];
		SinusoidalGradientFunction sinusoidal =	
				(SinusoidalGradientFunction)model.gradients[2];
		boolean[] selected = model.selected;
		
		switch (field)
		{
		case LINEAR: linear = (LinearGradientFunction)value; break;
		case RADIAL: radial = (RadialGradientFunction)value; break;
		case SINUSOIDAL: sinusoidal = (SinusoidalGradientFunction)value;
			break;
		case SELECTED: selected = (boolean[])value;
		}
		
		return new GradientGUIModel(linear, radial, sinusoidal, selected);
	}
}
