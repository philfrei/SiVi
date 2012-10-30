/*
 *  This file is part of SiVi.
 *
 *  SiVi is free software: you can redistribute it and/or modify it
 *  under the terms of the GNU General Public License as published
 *  by the Free Software Foundation, either version 3 of the 
 *  License, or (at your option) any later version.
 *
 *  SiVi is distributed in the hope that it will be useful, but
 *  WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public 
 *  License along with SiVi.  If not, see 
 *  <http://www.gnu.org/licenses/>.
 */

package com.adonax.texturebuilder;
 
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.util.ArrayList;

public class ColorAxis {

	public int[][] data;
	BufferedImage img;
	boolean hover;
	ArrayList<ColorBarPeg>colorBarPegs;
	boolean useHsbLerp;
	
	public ColorAxis()
	{
		data = new int[256][4];
		img = new BufferedImage(256, 16, 
				BufferedImage.TYPE_INT_ARGB);
		
		// default data will be black to white gradient
		WritableRaster raster = img.getRaster();
		
		int[] pixel = new int[4];
		pixel[3] = 255;
		
		for (int i = 0; i < 256; i++)
		{
			pixel[0] = i;
			pixel[1] = i;
			pixel[2] = i;
			
			data[i][0] = i;
			data[i][1] = i;
			data[i][2] = i;
			data[i][3] = 255;
			
			for (int j = 0; j < 16; j++)
			{
				raster.setPixel(i, j, pixel);
			}
		}
		
		colorBarPegs = new ArrayList<ColorBarPeg>();
		colorBarPegs.add(new ColorBarPeg(0, data[0][0], 
				data[0][1], data[0][2], 255));
		colorBarPegs.add(new ColorBarPeg(255, data[255][0], 
				data[255][1], data[255][2], 255));

	}
	
	public ColorAxis copy()
	{
		ColorAxis newCA = new ColorAxis();
		newCA.hover = hover;
		newCA.useHsbLerp = useHsbLerp;
		WritableRaster raster = newCA.img.getRaster();
		img.copyData(raster);
		for (int i = 0; i < 256; i++)
		{
			for (int j = 0; j < 4; j++)
			{
				newCA.data[i][j] = data[i][j];
			}
		}
		newCA.setColorBarPegs(colorBarPegs);
		
		return newCA;
	}
	
	public ArrayList<ColorBarPeg> getColorBarPegs()
	{
		ArrayList<ColorBarPeg> newRowData = new ArrayList<ColorBarPeg>();
		for (ColorBarPeg rd : colorBarPegs)
		{
			newRowData.add(new ColorBarPeg(rd.getLocation(), 
					rd.getrColor(), rd.getgColor(), rd.getbColor(), 255));
		}
		return newRowData;	
	}
	
	public void setColorBarPegs(ArrayList<ColorBarPeg> colorBarPegs)
	{
		this.colorBarPegs.clear();
		for (ColorBarPeg rd : colorBarPegs)
		{
			this.colorBarPegs.add(
					new ColorBarPeg(rd.getLocation(), 
							rd.getrColor(), rd.getgColor(), 
							rd.getbColor(), rd.getAlpha()));
		}
		update();
	}
	
	public void update()
	{
		int idx, cols;
		float redIncr, greenIncr, blueIncr, alphaIncr;
		float redSum, greenSum, blueSum, alphaSum;
		float hueIncr, saturationIncr, brightnessIncr;
		float hueSum, saturationSum, brightnessSum;
		float[] hsbStartVals = new float[3];
		float[] hsbEndVals = new float[3];
		Color color;
		
		int lastRow = colorBarPegs.size() - 1;
		
		for (int i = 0; i < lastRow; i++)
		{
			idx = colorBarPegs.get(i).getLocation();
			redSum = colorBarPegs.get(i).getrColor(); 
			greenSum = colorBarPegs.get(i).getgColor();
			blueSum = colorBarPegs.get(i).getbColor();
			alphaSum = colorBarPegs.get(i).getAlpha();
			
//			System.out.println("Peg " + i + "=" + alphaSum);
			
			data[idx][0] = (int)redSum; 
			data[idx][1] = (int)greenSum;
			data[idx][2] = (int)blueSum;
			data[idx][3] = (int)alphaSum;
					
			
			cols = colorBarPegs.get(i + 1).getLocation() - idx;
		
			if (useHsbLerp)
			{
				hsbStartVals = Color.RGBtoHSB(data[idx][0], 
						data[idx][1], data[idx][2],
						hsbStartVals);
				
				hsbEndVals = Color.RGBtoHSB(
						(int)colorBarPegs.get(i + 1).getrColor(), 
						(int)colorBarPegs.get(i + 1).getgColor(),
						(int)colorBarPegs.get(i + 1).getbColor(),
						hsbEndVals);
				
				hueSum = hsbStartVals[0];
				saturationSum = hsbStartVals[1];
				brightnessSum = hsbStartVals[2];
		
//				System.out.println("a:" + hsbStartVals[0] +
//						" b:" + hsbEndVals[0]);
				if (Math.abs(hsbEndVals[0] - hsbStartVals[0]) > 0.5)
				{
					if (hsbEndVals[0] > hsbStartVals[0])
					{
						hsbStartVals[0] += 1;						
					}
					else
					{
						hsbEndVals[0] += 1;
					}
				}
				hueIncr = (hsbEndVals[0] - hsbStartVals[0])/cols;
				saturationIncr = (hsbEndVals[1] - hsbStartVals[1])/cols;
				brightnessIncr = (hsbEndVals[2] - hsbStartVals[2])/cols;
				
//				System.out.println(
//						"hueStart:" + hueSum
//						+ " saturationStart" + saturationSum
//						+ " brightnessStart" + brightnessSum
//						+ " hueEnd:" + hsbEndVals[0]
//						+ " hueIncr:" + hueIncr
//						+ " satIncr:" + saturationIncr
//						+ " brightIncr:" + brightnessIncr
//								);
				
				for (int x = idx, n = idx + cols; x < n; x++)
				{
					hueSum += hueIncr;
					saturationSum += saturationIncr;
					brightnessSum += brightnessIncr;
					
					color = new Color(Color.HSBtoRGB(hueSum, saturationSum, brightnessSum));
					data[x][0] = color.getRed();
					data[x][1] = color.getGreen();
					data[x][2] = color.getBlue();
					
//					{System.out.println("h:" + hueSum + " s:" + saturationSum 
//							+ " b:" + brightnessSum + " color:" + color
//							+ " R:" + colorAxis.data[x][0]
//							+ " G:" + colorAxis.data[x][1]
//							+ " B:" + colorAxis.data[x][2]);
//					}
				}
				
				
			}
			else
			{
				redIncr = (colorBarPegs.get(i + 1).getrColor() - redSum)/cols;
				greenIncr = (colorBarPegs.get(i + 1).getgColor() - greenSum)/cols;
				blueIncr = (colorBarPegs.get(i + 1).getbColor() - blueSum)/cols;
				alphaIncr = (colorBarPegs.get(i + 1).getAlpha() - alphaSum)/cols;
				
				for (int x = idx; x < idx + cols; x++)
				{
					redSum += redIncr;
					data[x][0] = Math.round(redSum);
					greenSum += greenIncr;
					data[x][1] = Math.round(greenSum);
					blueSum += blueIncr;
					data[x][2] = Math.round(blueSum);
					alphaSum += alphaIncr;
					data[x][3] = Math.round(alphaSum);
				}
			}
		}
		
		data[255][0] = colorBarPegs.get(lastRow).getrColor();
		data[255][1] = colorBarPegs.get(lastRow).getgColor();
		data[255][2] = colorBarPegs.get(lastRow).getbColor();
		data[255][3] = colorBarPegs.get(lastRow).getAlpha();
		
		// update the ColorBar image
		WritableRaster raster = img.getRaster();
		int[] pixel = new int[4];
		
		for (int i = 0; i < 256; i++)
		{
			pixel[0] = data[i][0];
			pixel[1] = data[i][1];
			pixel[2] = data[i][2];
			pixel[3] = data[i][3];

//			System.out.println(i + ":" + data[i][3]);
			
			for (int j = 0; j < 16; j++)
			{
				raster.setPixel(i, j, pixel);
			}
		}		
	}
}
