package com.adonax.texturebuilder.test;

/************************************************************
* The following code template is provided to allow the reader a 
* convenient way to test the code examples in each tutorial 
* section. To use, copy the example and paste it into the 
* following class, between the two comments inside the 
* TestSimplexImageCode constructor:
* ***********drop test code between the two comments (TOP)
* ***********drop test code between the two comments (BOTTOM)
* The class should be give the name: "TestSimplexImageCode".
**************************************************************/

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.adonax.tutorial.utilities.ColorMap;
import com.adonax.utils.SimplexNoise;

class TestSimplexImageCode extends JPanel
{
	TestSimplexImageCode(int width, int height)
	{
		this.width = width;
		this.height = height;
		
//***********drop test code between the two comments (TOP)
	       int[][] pegs = { // location, R, B, G, alpha 
	                {0, 255, 255, 255, 255},
	                {40, 153, 153, 153, 255},
	                {64, 0, 128, 0, 255},
	                {95, 96, 176, 0, 255},
	                {99, 224, 224, 128, 255},
	                {100, 204, 204, 204, 255},
	                {128, 64, 64, 255, 255},
	                {255, 0, 0, 192, 255}};
	        int[] colorMap = ColorMap.makeMap(pegs);

	        BufferedImage image = new BufferedImage(700, 160,
	                BufferedImage.TYPE_INT_ARGB);
//	        WritableRaster raster = image.getRaster();
	        int[] pixel = new int[4]; //[0]=r, [1]=g, [2]=b, [3]=alpha
	        pixel[3] = 255; // alpha = opaque

	        int[] octaveScale = {1, 4, 16, 64};
	        int[] octaveAmplitude = {64, 16, 4, 1};
	        int octaves = octaveScale.length;

	        float amplitudeSum = 0;
	        for (int i : octaveAmplitude) amplitudeSum += i;	

	        double noiseSum = 0;

	        for (int y = 0; y < 160; y++)
	        {
	            for (int x = 0; x < 700; x++)
	            {
	                noiseSum = 0;
	                for (int i = 0; i < octaves; i++)
	                {
	                    noiseSum += SimplexNoise.noise(
	                          x * octaveScale[i]/256f,
	                          y * octaveScale[i]/256f )
	                          * ( octaveAmplitude[i]/amplitudeSum );
	                }
	                noiseSum = (noiseSum * 0.5) + .5;
	                noiseSum *= 256;
	                noiseSum = Math.max(noiseSum, 0);
	                noiseSum = Math.min(noiseSum, 255);

	                int idx = (int)noiseSum;
//	                pixel[0] = colorMap[idx][0];
//	                pixel[1] = colorMap[idx][1];
//	                pixel[2] = colorMap[idx][2];
//
//	                raster.setPixel(x, y, pixel);
	                
	                image.setRGB(x, y, colorMap[idx]);
	            }
	        }
//***********drop test code within the two comments (BOTTOM)

	    testImage = image;	    
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
	    		frame.setSize(728, 214);
	    		frame.setTitle("Test Simplex Texture Image");
	    		TestSimplexImageCode panel = 
	    				new TestSimplexImageCode(720, 180);
	    		frame.add(panel);
	    		frame.setVisible(true);
	    	}
	    });
	}
	
	private static final long serialVersionUID = 1L;
	private int width, height;
	private BufferedImage testImage;
	
}