package com.adonax.animatedFlame;

import java.util.Timer;
import java.util.TimerTask;

public class Animator extends Timer
{
	Fireplace fireplace;
	Flames flames;
	long counter;
	long start;
	
	public Animator(Fireplace fireplace, Flames flames)
	{
		this.fireplace = fireplace;
		this.flames = flames;
	}
	
	public void start()
	{
		FlameTask flameTask = new FlameTask();
		schedule(flameTask, 0, 50);
		start = System.currentTimeMillis();
	}

	
	class FlameTask extends TimerTask
	{	
		@Override
		public void run()
		{
			counter++;
			if (counter % 64 == 0)
			{
				System.out.println(
					"setRGB:" + (System.currentTimeMillis() - start)/64f);
				start = System.currentTimeMillis();
			}
			
			flames.update();
			fireplace.repaint();
			
		}
	}
}
