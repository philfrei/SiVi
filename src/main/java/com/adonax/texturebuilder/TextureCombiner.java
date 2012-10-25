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
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class TextureCombiner extends JPanel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	BufferedImage image;
	WritableRaster raster;
	int[] pixel = new int[4];
	private int width, height;
	private int cols, rows;

	private final int channels = 4;

	private SimplexTextureSource[] sts = 
			new SimplexTextureSource[channels];
	
	public void setSTS(SimplexTextureSource sts, int n)
	{
		this.sts[n] = sts;
	}
	
	private JLabel[] stage2Label = new JLabel[channels];
	
	private JButton[] stage1Button = new JButton[channels];
	private JButton[] stage2Button = new JButton[channels];
	private int[] stage1Mode = new int[channels];
	private int[] stage2Mode = new int[channels];
	
	private final int ADD = 0;
	private final int LERP = 1;
	private final int SIN = 2;
	private final int XDIM = 3;
	private final int YDIM = 4;
	private final int RING = 2;
	private final int CIRC = 5;
	
	private final String[] stageOneText = 
		{"add", "lerp", "sin", "xDim", "yDim", "circ"};
	private final String[] stageTwoText =
		{"add", "lerp", "ring"};
	
	public String clickStage1Button(int channel)
	{
		stage1Button[channel].doClick();
		return stage1Button[channel].getText();
	}
	public String clickStage2Button(int channel)
	{
		stage2Button[channel].doClick();
		return stage2Button[channel].getText();
	}
	
	
	private JSlider[] weightSlider = new JSlider[channels];	
	private JTextField[] weightVal = new JTextField[channels];
	private int[] weightStage1 = new int[channels];
	
	public void setStage1Weight(int channel, int weight)
	{
		weightStage1[channel] = weight;
		weightSlider[channel].setValue(weight);
		weightVal[channel].setText(String.valueOf(weight));
	}
	
	private JSlider[] weightStage2Slider = new JSlider[channels];	
	private JTextField[] weightStage2Val = new JTextField[channels];
	private int[] weightStage2 = new int[channels];
	
	public void setStage2Weight(int channel, int weight)
	{
		weightStage2[channel] = weight;
		weightStage2Slider[channel].setValue(weight);
		weightStage2Val[channel].setText(String.valueOf(weight));
	}
	
//	private JCheckBox overflowOption, underflowOption;
//	private boolean overflowPrevented, underflowPrevented;
	
	ArrayList<ChannelGroup> channelGroups; // stage2 members
	
	TextureCombiner(int width, int height)
	{
		this.width = width;
		this.height = height;
		cols = 256;
		rows = 256;
		
		image = new BufferedImage(cols, rows, BufferedImage.TYPE_INT_ARGB);
		raster = image.getRaster();
		
		setLayout(null);

		JLabel[] stage1Labels = new JLabel[channels];
		
		for (int i = 0; i < channels; i++)
		{
			stage1Labels[i] = new JLabel(String.valueOf(i));
			stage1Labels[i].setBounds(2, i * 32, 18, 24);
			stage1Labels[i].setHorizontalAlignment(SwingConstants.CENTER);
			stage1Labels[i].setBackground(new Color(224, 255, 255));
			stage1Labels[i].setOpaque(true);
			add(stage1Labels[i]);
						
			stage1Mode[i] = ADD;
			stage1Button[i] = makeMultiStageButton(i, 0,
					stageOneText, stage1Mode);
			add(stage1Button[i]);
		
			weightSlider[i] = makeWeightingSlider(i);
			add(weightSlider[i]);
					
			weightVal[i] = makeWeightingTextField(i);
			add(weightVal[i]);
			
			stage2Label[i] = new JLabel(String.valueOf(i));
			stage2Label[i].setBounds(2, i * 32 + 128, 32, 24);
			stage2Label[i].setHorizontalAlignment(
					SwingConstants.RIGHT);
			stage2Label[i].setBackground(new Color(224, 255, 196));
			stage2Label[i].setOpaque(true);
			add(stage2Label[i]);
	
			stage2Mode[i] = LERP;
			stage2Button[i] = makeMultiStageButton(i, 128,
					stageTwoText, stage2Mode);
			add(stage2Button[i]);
		
			weightStage2Slider[i] = makeStage2Slider(i, 128);
			add(weightStage2Slider[i]);
			
			weightStage2Val[i] = makeStage2TextField(i, 128);
			add(weightStage2Val[i]);	
		}
		
	}
	
	private JButton makeMultiStageButton(final int idx, int yStart, 
			final String[] stageTexts, final int[] stageModes)
	{
		final JButton newButton = new JButton(stageTexts[stageModes[idx]]);
		newButton.setBounds(38, idx * 32 + yStart, 64, 24);
		newButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) 
			{
				stageModes[idx] += 1;
				if (stageModes[idx] >= stageTexts.length)
				{
					stageModes[idx] = 0;
				}
								
				newButton.setText(stageTexts[stageModes[idx]]);
				update();
			}
		});
		return newButton;
	}
	
	
	private JSlider makeWeightingSlider(final int idx)
	{
		weightStage1[idx] = 16;
		final JSlider weightSlider = new JSlider(0, 64, 
				weightStage1[idx]);
		weightSlider.setBounds(110, idx * 32, 128, 24);

		weightSlider.addChangeListener(new ChangeListener(){

			@Override
			public void stateChanged(ChangeEvent arg0) {
				weightStage1[idx] = weightSlider.getValue();
				weightVal[idx].setText(
						String.valueOf(weightStage1[idx]));
				update();
			}
		});

		return weightSlider;
	}

	private JSlider makeStage2Slider(final int idx, int yStart)
	{
		weightStage2[idx] = 64;
		final JSlider weightSlider = new JSlider(0, 64, 
				weightStage2[idx]);
		weightSlider.setBounds(110, idx * 32 + yStart, 128, 24);

		weightSlider.addChangeListener(new ChangeListener()
		{
			@Override
			public void stateChanged(ChangeEvent arg0) 
			{
				weightStage2[idx] = weightSlider.getValue();
				weightStage2Val[idx].setText(
						String.valueOf(weightStage2[idx]));
				update();
			}
		});

		return weightSlider;
	}

	private JTextField makeWeightingTextField(final int idx)
	{
		weightStage1[idx] = 16;
		final JTextField newJTextField = new JTextField(
				String.valueOf(weightStage1[idx]));
		newJTextField.setBounds(262, idx * 32, 48, 24);	

		newJTextField.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0) {
				weightSlider[idx].setValue(
						Integer.valueOf(newJTextField.getText()));
			}
		});
		
		return newJTextField;	
	}

	private JTextField makeStage2TextField(final int idx, int yStart)
	{
		weightStage2[idx] = 64;
		final JTextField newJTextField = new JTextField(
				String.valueOf(weightStage2[idx]));
		newJTextField.setBounds(262, idx * 32 + yStart, 48, 24);	

		newJTextField.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0) {
				weightStage2Slider[idx].setValue(
						Integer.valueOf(newJTextField.getText()));
			}
		});
		
		return newJTextField;	
	}
	
	public void update()
	{
		update2ndStage();
		
		for (ChannelGroup cg : channelGroups)
		{
			
//			System.out.println("ch group:" + 
//				channelGroups.indexOf(cg));
					
			// SUM mode
			if (stage1Mode[cg.members.get(0)] == ADD)
			{
				float[] weight = new float[channels];
				for (int i : cg.members)
				{
					weight[i] = weightStage1[i] / 64f;
				}
				
				for (int j = 0; j < 256; j++)
				{
					for (int i = 0; i < 256; i++)
					{
						float sum = 0;
						for (int idx : cg.members)
						{
							sum += sts[idx].noiseArray[i][j]
								* weight[idx];
						}
						cg.noiseVals[i][j] = sum;
					}
				}					
			}

			if (stage1Mode[cg.members.get(0)] == LERP)
			{
				float[] weight = new float[channels];
				float weightSum = 0;
				
				for (int i : cg.members)
				{
					weightSum += weightStage1[i];
				}
				for (int i : cg.members)
				{
					weight[i] = weightStage1[i] / weightSum;
				}
				
				for (int j = 0; j < 256; j++)
				{
					for (int i = 0; i < 256; i++)
					{
						float sum = 0;
						for (int idx : cg.members)
						{
							sum += sts[idx].noiseArray[i][j]
								* weight[idx];
						}
						cg.noiseVals[i][j] = sum;
					}
				}					
			}
			
			
			
			if (stage1Mode[cg.members.get(0)] == SIN)
			{
				for (int j = 0; j < 256; j++)
				{
					for (int i = 0; i < 256; i++)
					{
						float sum = 0;
						for (int idx : cg.members)
						{
							sum += sts[idx].noiseArray[i][j]
								* (weightStage1[idx] / 128f);
						}
						cg.noiseVals[i][j] = (float)Math.sin(i/24f + sum);
					}
				}					
			}
			
			if (stage1Mode[cg.members.get(0)] == XDIM)
			{
				for (int j = 0; j < 256; j++)
				{
					for (int i = 0; i < 256; i++)
					{
						float sum = 0;
						for (int idx : cg.members)
						{
							sum += sts[idx].noiseArray[i][j]
									* (weightStage1[idx] / 128f);
						}
						cg.noiseVals[i][j] = (i/256f + sum);
					}
				}					
			}

			if (stage1Mode[cg.members.get(0)] == YDIM)
			{
				for (int j = 0; j < 256; j++)
				{
					for (int i = 0; i < 256; i++)
					{
						float sum = 0;
						for (int idx : cg.members)
						{
							sum += sts[idx].noiseArray[i][j]
									* (weightStage1[idx] / 128f);
						}
						cg.noiseVals[i][j] = (j/256f + sum);
					}
				}					
			}
			
			
			if (stage1Mode[cg.members.get(0)] == CIRC)
			{
				Point middle = new Point(128, 128);
				for (int j = 0; j < 256; j++)
				{
					for (int i = 0; i < 256; i++)
					{
						float sum = 0;
						for (int idx : cg.members)
						{
							sum += sts[idx].noiseArray[i][j]
									* (weightStage1[idx] / 128f);
						}
						float r = (float)middle.distance(i, j)/192f;
						r = Math.min(r, 1);
						
						cg.noiseVals[i][j] = (r + sum);
					}
				}
			}
		}
		
		// now turn this into a graphic...via the colormap
		
		// stage 2 
		
		int chCount = channelGroups.size();
		double[] weight = new double[chCount];
		int[][][] cMapData = new int[chCount][256][4]; // ch, x, pixel 
				//sts[cg.members.get(0)].colorAxis.data;
		
		for (int i = 0; i < chCount; i++)
		{
			weight[i] = weightStage2[i];  // value from slider/txtfield
			cMapData [i] = sts[channelGroups.get(i).members.get(0)]
					.colorAxis.data;  //colormap data, shared
		}
		
		double lerpSum = 0;
		for (int cgIdx = 0; cgIdx < chCount; cgIdx++)
		{
			if (stage2Mode[cgIdx] == LERP)
			{
				lerpSum += weight[cgIdx];
			}
		}
		for (int cgIdx = 0; cgIdx < chCount; cgIdx++)
		{
			if (stage2Mode[cgIdx] == LERP)
			{
				weight[cgIdx] /= lerpSum;  // lerp factor
			}
			else // presumably SUM or MOD
			{
				weight[cgIdx] /= 64;  // add factor
			}
		}  // weights are now a fraction of 1
	
		double rPixel, gPixel, bPixel;
		pixel[3] = 255;
		for (int j = 0; j < 256; j++)
		{
			for (int i = 0; i < 256; i++)
			{			
				// for each pixel
				rPixel = 0;
				gPixel = 0;
				bPixel = 0;
				
				float denormalizingFactor = 255f;
				for (int channelGroupIdx = 0; channelGroupIdx < chCount; channelGroupIdx++)
				{
					int colorMapIdx = (int)(channelGroups.get(channelGroupIdx)
							.noiseVals[i][j] * denormalizingFactor);
					
					if (stage2Mode[channelGroupIdx] == ADD || stage2Mode[channelGroupIdx] == LERP)
					{
						colorMapIdx = Math.min(255, Math.max(0, colorMapIdx));				
					} 
					else if (stage2Mode[channelGroupIdx] == RING)
					{
						while (colorMapIdx < 0) colorMapIdx += 256;
						colorMapIdx %= 256;
					}
					
//					if (colorMapIdx > 255) 
//					{
//						System.out.println("intercept cmIdx:" + colorMapIdx);
//						System.out.println("stage2Mode:" + stage2Mode[channelGroupIdx]);
//					}
					
					rPixel += cMapData[channelGroupIdx][colorMapIdx][0] * weight[channelGroupIdx];
					gPixel += cMapData[channelGroupIdx][colorMapIdx][1] * weight[channelGroupIdx];
					bPixel += cMapData[channelGroupIdx][colorMapIdx][2] * weight[channelGroupIdx];
				
				}
				pixel[0] = (int) Math.min(255, Math.max(0, rPixel));
				pixel[1] = (int) Math.min(255, Math.max(0, gPixel));
				pixel[2] = (int) Math.min(255, Math.max(0, bPixel));
				
				raster.setPixel(i, j, pixel);
			}
		}

		
		
		
		repaint();
	}

	private void update2ndStage()
	{
		channelGroups = new ArrayList<ChannelGroup>();
		boolean[] isChannelGrouped = new boolean[channels];
		
		ColorAxis matchColorAxis;
//		boolean matchUseColorMap;
		int matchChannelMode;
		
		for (int i = 0; i < channels; i++)
		{
			if (isChannelGrouped[i] == false)
			{
				// 1st always assigned to new group
				ChannelGroup newGroup = new ChannelGroup();
				newGroup.members.add(i);
				isChannelGrouped[i] = true;
				channelGroups.add(newGroup);
				
				// info for match test
				matchColorAxis = sts[i].colorAxis;
				matchChannelMode = stage1Mode[i];
//				matchUseColorMap = sts[i].useColorMapSelected;
				
				// are there other matches in remaining channels?
				for (int j = i + 1; j < channels; j++)
				{
					if (matchColorAxis == sts[j].colorAxis
							&& matchChannelMode == stage1Mode[j])
//							&& matchUseColorMap == sts[j].useColorMapSelected)
					{
						newGroup.members.add(j);
						isChannelGrouped[j] = true;
					}
				}
			}			
		}	
			
		// second pass, assign groups to GUI stage2 channels
		for (int idx = 0; idx < channels; idx++)
		{
			if (idx < channelGroups.size())
			{
				// handle row visibility
				stage2Label[idx].setVisible(true);
				stage2Button[idx].setVisible(true);
				weightStage2Slider[idx].setVisible(true);
				weightStage2Val[idx].setVisible(true);
			
				String lbl = "";
				for (int ii : channelGroups.get(idx).members)
				{
					lbl = lbl + String.valueOf(ii); 
				}
				stage2Label[idx].setText(lbl);
			}
			else
			{
				stage2Label[idx].setVisible(false);
				stage2Button[idx].setVisible(false);
				weightStage2Slider[idx].setVisible(false);
				weightStage2Val[idx].setVisible(false);
			}
		}
	}
	
	private class ChannelGroup
	{
		ArrayList<Integer> members = new ArrayList<Integer>();
		// x, y noise data, merged via "mode" function
		float[][] noiseVals = new float[256][256];
		
	}
	
	
	public void paintComponent(Graphics g)
	{
		Graphics2D g2 = (Graphics2D) g;
		
		// refresh screen
		g2.setBackground(new Color(255, 255, 255, 255));  //230,250,255
		g2.clearRect(0, 0, width, height);

		g2.setPaint(Color.BLACK);
		g2.drawLine(0, 124, width - (256 + 8), 124);
		g2.drawImage(image, width - 256, 0, null);
	}
}