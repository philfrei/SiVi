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

import java.awt.event.*;

import javax.swing.*;

import com.adonax.tutorial.TutorialFramework;

public class STBPanel extends JPanel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ColorAxis[] colorAxisSet;
	
	public TextureCombiner tc;
	public SimplexTextureSource sts1;
	public SimplexTextureSource sts2;
	public SimplexTextureSource sts3;
	public SimplexTextureSource sts4;
	
	public ColorBarSet cbSet;	
	ColorBarSelectorDialog cbSelector;
	final static int BARS = 7;
	
	private TutorialFramework tutorial;
	
	public ColorAxis getColorAxis(int index)
	{
		return colorAxisSet[index];
	}
	
	
	STBPanel(int width, int height, final STBFrame host)
	{
		setLayout(null);		
		
		colorAxisSet = new ColorAxis[BARS];
		for (int i = 0; i < BARS; i++)
		{
			colorAxisSet[i] = new ColorAxis();
		}
		
		cbSelector = new ColorBarSelectorDialog(colorAxisSet);
		cbSelector.setTitle("Select a Color Axis");
		cbSelector.setModal(true);
		
		tc = new TextureCombiner(580, 256);
		
		sts1 = new SimplexTextureSource(4, 0, 256, 544, 
						colorAxisSet[0], this);
		sts2 = new SimplexTextureSource(268, 0, 256, 544, 
						colorAxisSet[0], this);
		sts3 = new SimplexTextureSource(532, 0, 256, 544, 
						colorAxisSet[0], this);
		sts4 = new SimplexTextureSource(796, 0, 256, 544, 
						colorAxisSet[0], this);
		
		add(sts1);
		add(sts2);
		add(sts3);
		add(sts4);
		add(tc);
		
		sts1.setBounds(4, 0, 256, 544);
		sts2.setBounds(268, 0, 256, 544);
		sts3.setBounds(532, 0, 256, 544);
		sts4.setBounds(796, 0, 256, 544);
		
		tc.setBounds(16, 550, 580, 256);
		
		tc.setSTS(sts1, 0);
		tc.setSTS(sts2, 1);
		tc.setSTS(sts3, 2);
		tc.setSTS(sts4, 3);
			
		JButton tutorialBtn = new JButton("Tutorial");
		tutorialBtn.setBounds(640, height - 36, 100, 24);
		tutorialBtn.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				tutorial.setVisible(true);	
			}
		});
		add(tutorialBtn);
		
		
		cbSet = new ColorBarSet(BARS, 700, 550, 264, 256, 
				colorAxisSet, this);
		cbSet.setBounds(788, 550, 264, 256);
		add(cbSet);
		
	    tutorial = new TutorialFramework(this);
        tutorial.setBounds(100, 100, 800 + 8, 700 + 34);
	    
		update();
		
//		tutorialBtn.doClick();
	}
	
	public void update()
	{
		sts1.update();
		sts2.update();
		sts3.update();
		sts4.update();
		
		cbSet.repaint();
	}
	
	public void remix()
	{
//		System.out.println("update call via STB.remix");
		tc.update();
	}
}
