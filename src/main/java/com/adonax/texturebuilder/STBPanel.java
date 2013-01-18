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

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;

import com.adonax.texturebuilder.export.ExportFrame;
import com.adonax.tutorial.TutorialFramework;

public class STBPanel extends JPanel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// stored to be parent of sub-dialogs
	private final STBFrame host;

	private ColorAxis[] colorAxisSet;
	
	public TextureCombiner tc;
	public SimplexTextureSource sts1;
	public SimplexTextureSource sts2;
	public SimplexTextureSource sts3;
	public SimplexTextureSource sts4;
	
	public ColorBarSet cbSet;	
	ColorMapSelectorDialog cbSelector;
	final static int BARS = 7;
	
	private TutorialFramework tutorial;
	
	public ColorAxis getColorAxis(int index)
	{
		return colorAxisSet[index];
	}


	STBPanel(int width, int height, final STBFrame host)
	{
		this.host = host;

		setLayout(new BorderLayout());
		
		colorAxisSet = new ColorAxis[BARS];
		for (int i = 0; i < BARS; i++)
		{
			colorAxisSet[i] = new ColorAxis();
		}
		
		cbSelector = new ColorMapSelectorDialog(colorAxisSet);
		cbSelector.setTitle("Select a Color Map");
		cbSelector.setModal(true);
		
		tc = new TextureCombiner(640, 286);
		
		sts1 = new SimplexTextureSource(4, 0, 256, 544,
						colorAxisSet[0], this);
		sts2 = new SimplexTextureSource(268, 0, 256, 544, 
						colorAxisSet[0], this);
		sts3 = new SimplexTextureSource(532, 0, 256, 544, 
						colorAxisSet[0], this);
		sts4 = new SimplexTextureSource(796, 0, 256, 544, 
						colorAxisSet[0], this);

		JPanel textureSourcesPanel = new JPanel();
		textureSourcesPanel.setLayout(null);

		textureSourcesPanel.add(sts1);
		textureSourcesPanel.add(sts2);
		textureSourcesPanel.add(sts3);
		textureSourcesPanel.add(sts4);

		add(textureSourcesPanel, BorderLayout.NORTH);

		int stsWidth = sts1.getPreferredSize().width;
		int stsHeight = sts1.getPreferredSize().height;
		int shim = 10;

		textureSourcesPanel.setPreferredSize(new Dimension(4*stsWidth, stsHeight+shim));

		sts1.setBounds(0*stsWidth + 0*shim, 0, stsWidth, stsHeight);
		sts2.setBounds(1*stsWidth + 1*shim, 0, stsWidth, stsHeight);
		sts3.setBounds(2*stsWidth + 2*shim, 0, stsWidth, stsHeight);
		sts4.setBounds(3*stsWidth + 3*shim, 0, stsWidth, stsHeight);

		tc.setSTS(sts1, 0);
		tc.setSTS(sts2, 1);
		tc.setSTS(sts3, 2);
		tc.setSTS(sts4, 3);

		JButton tutorialBtn = new JButton("Tutorial");
		tutorialBtn.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				tutorial.setVisible(true);	
			}
		});

		JButton exportBtn = new JButton("Export Code");
		exportBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				java.util.List<TextureParams> textureParamsList = new ArrayList<TextureParams>(4);
				textureParamsList.add(sts1.getTextureParams());
				textureParamsList.add(sts2.getTextureParams());
				textureParamsList.add(sts3.getTextureParams());
				textureParamsList.add(sts4.getTextureParams());

				new ExportFrame(STBPanel.this.host, textureParamsList, tc.getCombineParams());
			}
		});

		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

		buttonPanel.add(tutorialBtn);
		buttonPanel.add(exportBtn);

		add(buttonPanel, BorderLayout.SOUTH);

		cbSet = new ColorBarSet(BARS, 700, 550, 300, 286,
				colorAxisSet, this);

		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(null);

		centerPanel.add(tc);
		int tcWidth = tc.getPreferredSize().width;
		int tcHeight = tc.getPreferredSize().height;
		tc.setBounds(0, 0, tcWidth, tcHeight);
		centerPanel.add(cbSet);
		cbSet.setBounds(tcWidth+shim, 0, cbSet.getPreferredSize().width, cbSet.getPreferredSize().height);

		add(centerPanel, BorderLayout.CENTER);

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
