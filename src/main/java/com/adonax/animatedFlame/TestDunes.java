package com.adonax.animatedFlame;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.adonax.tutorial.utilities.ColorMap;
import com.adonax.utils.SimplexNoise;

class TestDunes extends JPanel
{
	TestDunes(int width, int height)
	{
		this.width = width + 20;
		this.height = height + 20;
		
		// ColorBar
		int[][] colorBarPegs = {{0, 0, 0, 255, 255},
				{128, 204, 0, 0, 255},
				{255, 255, 255, 102, 255}};
		int[] colorAxis = ColorMap.makeMap(colorBarPegs);
		
		
		BufferedImage image = new BufferedImage(width, height,
	            BufferedImage.TYPE_INT_ARGB);
	    int[] pixel = new int[4]; //[0]=r, [1]=g, [2]=b, [3]=alpha
	    pixel[3] = 255;
	    
	    float[] xScales = {4, 8, 16, 32};
	    float[] yScales = {2, 4, 8, 16};
	    float[] octaveAmplitude = {8, 4, 2, 1};
	    float amplitudeSum = 0;
	    int octaves = xScales.length;

	    for (int i = 0; i < octaves; i++) amplitudeSum += 
	    		octaveAmplitude[i];
	    
//	    System.out.println("ampsum:" + amplitudeSum);
//	    System.out.println("octaves:" + octaves);
	    
	    float noiseSum = 0;
	    	    	    		
	    for (int y = 0; y < height; y++)
	    {
	    	for (int x = 0; x < width; x++)
	        {
	    		noiseSum = 0;
	    		
	    		double a = 0.3;
	    		
	    		for (int i = 0; i < octaves; i++)
	    		{
	    			noiseSum += SimplexNoise.noise(x * xScales[i] /128f, 
	    					y * yScales[i] / 128f + a, a)
	    					* ( octaveAmplitude[i]/(float)amplitudeSum );
	    		}
	    		
	    		int nSum = (int)(noiseSum * 128 + y*255f/height);
	    		
//	    		System.out.println("y" + y + " x:" + x + " ns:" + noiseSum);
	    		
	    		
	    		
                // clamp
                if (nSum > 255) nSum = 255;
	            if (nSum < 0) nSum = 0;
	
//	            pixel[0] = colorAxis[nSum][0];
//	            pixel[1] = colorAxis[nSum][1];
//	            pixel[2] = colorAxis[nSum][2];
//	            
//	            raster.setPixel(x, y, pixel);
	            
	            image.setRGB(x, y, colorAxis[nSum]);
	        }
//	    	System.out.println(xValue[0]);
	    }

	    testImage = image;
	    
	    repaint();
	    
	}

	public void paintComponent(Graphics g)
	{
		Graphics2D g2 = (Graphics2D) g;
		
		g2.setBackground(Color.WHITE);
		g2.clearRect(0, 0, width, height);

		g2.drawImage(testImage, 10, 10, null);
	}	

	public static void main(String[] args) {

	    EventQueue.invokeLater(new Runnable(){
		    
	    	public void run()
	    	{	
	    		JFrame frame = new JFrame();
	    		frame.setDefaultCloseOperation(
	    				JFrame.EXIT_ON_CLOSE);
				/*
				 *  Added a bit of space to account for the Frame's 
				 *  border & title bar.
				 */	    		
	    		frame.setSize(512 + 8 + 20, 256 + 34 + 20);
	    		frame.setTitle("Test Anastropic Texture Image");
	    		TestDunes panel = 
	    				new TestDunes(512, 256);
	    		frame.add(panel);
	    		frame.setVisible(true);
	    	}
	    });
	}
	
	private static final long serialVersionUID = 1L;
	private int width, height;
	private BufferedImage testImage;
	
}