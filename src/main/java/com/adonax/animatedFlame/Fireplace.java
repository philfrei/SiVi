package com.adonax.animatedFlame;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JComponent;

public class Fireplace extends JComponent
{
	private static final long serialVersionUID = 1L;
	private Flames flames;
	
	public Flames getFlames()
	{
		return flames;
	}
	
	private BufferedImage image;
	
	public Fireplace(int width, int height)
	{
		flames = new Flames(width, height);
		image = flames.getImage();
	}
	
	public void paintComponent(Graphics g)
	{
		g.drawImage(image, 0, 0, null);
	}
	
	
	
	
}
