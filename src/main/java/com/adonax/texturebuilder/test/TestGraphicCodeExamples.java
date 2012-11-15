package com.adonax.texturebuilder.test;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.adonax.tutorial.utilities.ColorMap;
import com.adonax.utils.SimplexNoise;

public class TestGraphicCodeExamples
{

	/**
	 * @param args
	 */
	public static TestImagesBuildCodeFrame frame;
	
	public static void main(String[] args) {

	    EventQueue.invokeLater(new Runnable(){
		    
	    	public void run()
	    	{	
	    		frame = new TestImagesBuildCodeFrame();
	    		frame.setDefaultCloseOperation(
	    				JFrame.EXIT_ON_CLOSE);
	    		frame.setVisible(true);
	    	}
	    });
	}
}


class TestImagesBuildCodeFrame extends JFrame
{
	private static final long serialVersionUID = 1L;
	
	public TestImagesBuildCodeFrame(){
		
		/*
		 *  Added a bit of space to account for the Frame's 
		 *  border & title bar.
		 */
		setSize(728, 854); 
	    
		TestImagesPanel panel = new TestImagesPanel(720, 820);
		add(panel);
	}
}

class TestImagesPanel extends JPanel
{
	private static final long serialVersionUID = 1L;
	private int width, height;
	
	BufferedImage[] images = new BufferedImage[7];
	
	TestImagesPanel(int width, int height)
	{
		this.width = width;
		this.height = height;
		
		images[0] = testImage0();
		images[1] = testImage1();
		images[2] = testImage2();
		images[3] = testImage3();
		images[4] = testImage4();
		images[5] = testImage5();
		images[6] = testImage6();
	}
	
	BufferedImage testImage0()
	{
	    BufferedImage image = new BufferedImage(700, 160,
	    			BufferedImage.TYPE_INT_ARGB);
	    WritableRaster raster = image.getRaster();
	    int[] pixel = new int[4]; //[0]=r, [1]=g, [2]=b, [3]=alpha
	    double noiseValue = 0;

	    for (int y = 0; y < 160; y++)
	    {
	        for (int x = 0; x < 700; x++)
	        {
	            noiseValue = SimplexNoise.noise(x/128f, y/128f);
	            noiseValue = (noiseValue + 1) / 2;
	            noiseValue *= 255;

	            pixel[0] = (int)noiseValue;
	            pixel[1] = (int)noiseValue;
	            pixel[2] = (int)noiseValue;
	            pixel[3] = 255; // opaque;

	            raster.setPixel(x, y, pixel);
	        }
	    }
	    
	    return image;
	}
	
	BufferedImage testImage1()
	{
		   BufferedImage image = new BufferedImage(700, 160,
		            BufferedImage.TYPE_INT_ARGB);
		    WritableRaster raster = image.getRaster();

		    int[] pixel = new int[4]; //[0]=r,[1]=g,[2]=b,[3]=alpha
		    pixel[2] = 0; // no blue involved
		    pixel[3] = 255; // always opaque

		    double noiseValue = 0;

		    // dark brown = Color(64, 32, 0)
		    int redA = 64;
		    int greenA = 32;
		    // medium brown = Color(128, 96, 0)
		    int redB = 128;
		    int greenB = 96;

		    int rings = 4;

		    for (int y = 0; y < 160; y++)
		    {
		        for (int x = 0; x < 700; x++)
		        {
		            noiseValue = SimplexNoise.noise(x/128f, y/128f);
		            noiseValue *= rings;
		            noiseValue = noiseValue - Math.floor(noiseValue);

		            pixel[0] = (int)(noiseValue * (redB - redA) + redA);
		            pixel[1] = (int)(noiseValue * (greenB - greenA) 
		                             + greenA);

		            raster.setPixel(x, y, pixel);
		        }
		    }

	    
	    return image;
	}
	
	private BufferedImage testImage2()
	{
		int width = 700; int height = 160;
		
		//*****************Code dropped here:
	    BufferedImage image = new BufferedImage(width, height,
	            BufferedImage.TYPE_INT_ARGB);
	    WritableRaster raster = image.getRaster();
	    int[] pixel = new int[4]; //[0]=r, [1]=g, [2]=b, [3]=alpha
	    
	    // the blue value is always at maximum
	    pixel[2] = 255;
	    pixel[3] = 255; // opaque

	    int[] octaveScale = {2, 4, 8, 16};
	    int[] octaveAmplitude = {8, 4, 2, 1};

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
	            noiseSum = (1 + noiseSum) / 2;
	            noiseSum *= 255;

	            pixel[0] = (int)noiseSum;
	            pixel[1] = pixel[0];

	            raster.setPixel(x, y, pixel);
	        }
	    }

		return image;
	}
	
	private BufferedImage testImage3()
	{
		int width = 700; int height = 160;
		
		//*****************Code dropped here:

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
        int[] pixel = new int[4];
        pixel[3] = 255; // alpha = opaque

        int[] octaveScale = {1, 4, 16, 64};
        int[] octaveAmplitude = {64, 16, 4, 1};
        int octaves = octaveScale.length;

        float amplitudeSum = 0;
        for (int i : octaveAmplitude)
        {
            amplitudeSum += i;	
        }
        
        double noiseSum = 0;

        for (int y = 0; y < height; y++)
        {
            for (int x = 0; x < width; x++)
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

                image.setRGB(x, y, colorMap[idx]);
            }
        }
        
	    return image;
	}
	
	private BufferedImage testImage4()
	{
	    BufferedImage image = new BufferedImage(700, 160,
	            BufferedImage.TYPE_INT_ARGB);
	    return image;
	}
	
	private BufferedImage testImage5()
	{
	    BufferedImage image = new BufferedImage(700, 160,
	            BufferedImage.TYPE_INT_ARGB);
	    return image;
	}
	
	private BufferedImage testImage6()
	{
	    BufferedImage image = new BufferedImage(700, 160,
	            BufferedImage.TYPE_INT_ARGB);
	    return image;
	}
	
	public void paintComponent(Graphics g)
	{
		Graphics2D g2 = (Graphics2D) g;
		
		// refresh screen
		g2.setBackground(new Color(255, 255, 255, 255));  //230,250,255
		g2.clearRect(0, 0, width, height);

		g2.drawImage(images[0], 10, 10, null);
		g2.drawImage(images[1], 10, 180, null);
		g2.drawImage(images[2], 10, 350, null);
		g2.drawImage(images[3], 10, 520, null);
		g2.drawImage(images[4], 10, 690, null);
	}
	
}