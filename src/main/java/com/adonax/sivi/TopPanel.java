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
package com.adonax.sivi;

import java.awt.*;
import java.awt.image.BufferedImage;

import javax.swing.*;

import com.adonax.sivi.animation.AnimationPanel;
import com.adonax.sivi.color.ColorMapSelectorGUI;
import com.adonax.sivi.gradients.GradientGUIModel;
import com.adonax.sivi.gradients.LinearGradientFunction;
import com.adonax.sivi.gradients.RadialGradientFunction;
import com.adonax.sivi.gradients.SinusoidalGradientFunction;

//import com.adonax.texturebuilder.export.ExportFrame;

public class TopPanel extends JPanel
{
	private static final long serialVersionUID = 1L;

	public static int XPIXELS = 256;
	public static int YPIXELS = 256;
	
	private volatile TopPanelModel appSettings; 
	public void setAppSettings(TopPanelModel tpm)
	{
		appSettings = tpm;
	}
	public TopPanelModel getAppSettings()
	{
		return appSettings;
	}
	
	public final ColorMapPreprocessingGUI colorMapPreprocessingGUI;
	public final ColorMapSelectorGUI colorMapSelectorGUI;
	public final ColorMapGUI colorMapGUI;
	public OctaveGUI[] octaveGUIs;
	public MixerGUI mixerGUI; 
	
	private JPanel centerPanel;
	public FinalDisplay finalDisplay;
	public BufferedImage getFinalImage()
	{
		return finalDisplay.getImage();
	}
	private JScrollPane octaveScroll;
	
	private OctaveModel[] octaveModels;
	private AnimationPanel animationPanel;
	
	TopPanel()
	{
		this(new TopPanelModel());
	}
	
	TopPanel(TopPanelModel topPanelModel)
	{
		setLayout(new BorderLayout());

		appSettings = new TopPanelModel();
		
		boolean[] selected = new boolean[appSettings.octaves];

		mixerGUI = new MixerGUI(this, new MixerModel(appSettings), 
				new GradientGUIModel(
						new LinearGradientFunction(
								appSettings.finalWidth, 
								appSettings.finalHeight, 
								0, 0.5f, 0, 0), 
						new RadialGradientFunction(
								appSettings.finalWidth/2, 
								appSettings.finalHeight/2,
								appSettings.finalWidth/2, 
								0, 0.5f),
						new SinusoidalGradientFunction(
								0, 0, appSettings.finalWidth/4, 
								0, 0.5f, 0),
						selected
						));

		finalDisplay = new FinalDisplay();
		updateFinalDisplaySize(topPanelModel);

		colorMapSelectorGUI = new ColorMapSelectorGUI(this);
		colorMapPreprocessingGUI = new ColorMapPreprocessingGUI(this);
		colorMapGUI = new ColorMapGUI(colorMapPreprocessingGUI, 
				colorMapSelectorGUI);
		
		octaveModels = new OctaveModel[appSettings.octaves];
		for (int i = 0; i < appSettings.octaves; i++)
		{
			octaveModels[i] = new OctaveModel();
		}
		octaveGUIs = new OctaveGUI[appSettings.octaves];
		JPanel octavesPanel = makeOctavesPanel(octaveModels);
		
		octaveScroll = new JScrollPane(octavesPanel);
		octaveScroll.setPreferredSize(new Dimension(800, 434));	
		add(octaveScroll, BorderLayout.NORTH);

		centerPanel = new JPanel();
		centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.X_AXIS));
		centerPanel.add(mixerGUI);
		centerPanel.add(colorMapGUI);
		finalDisplay.setAlignmentY(Component.TOP_ALIGNMENT);
		centerPanel.add(finalDisplay);
		
		add(centerPanel, BorderLayout.SOUTH);		
		
		updateOctaveDisplays();
	}

	private JPanel makeOctavesPanel(OctaveModel[] om)
	{
		JPanel octavesPanel = new JPanel();
		octavesPanel.setLayout(
				new BoxLayout(octavesPanel,BoxLayout.LINE_AXIS));
		
		octaveGUIs = new OctaveGUI[appSettings.octaves];
		
		for (int i = 0; i < appSettings.octaves; i++)
		{
			octaveGUIs[i] = new OctaveGUI(om[i], this);
			octavesPanel.add(octaveGUIs[i]);
		}
		return octavesPanel;
	}
	
	public void updateOctavesPanel()
	{
		OctaveModel[] newOctaveModels = new OctaveModel[appSettings.octaves];
		
		// make array of OctaveModels, adding defaults if needed
		for (int i = 0; i < newOctaveModels.length; i++)
		{
			if (i < octaveGUIs.length )
			{
				newOctaveModels[i] = octaveModels[i];
			}
			else
			{
				newOctaveModels[i] = new OctaveModel();
			}
		}
		
		octaveModels = newOctaveModels;
		loadOctavesPanel(newOctaveModels);
	}
	
	public void loadOctavesPanel(OctaveModel[] octaveModels)
	{
		remove(octaveScroll);
		this.octaveModels = octaveModels;
		octaveScroll = new JScrollPane(makeOctavesPanel(octaveModels));
		octaveScroll.setPreferredSize(new Dimension(800, 434));	
		add(octaveScroll, BorderLayout.NORTH);
		revalidate();
	}
	
	public void updateMixerGUI(MixerGUI newMixerGUI)
	{
		centerPanel.remove(mixerGUI);
		centerPanel.remove(colorMapGUI);
		centerPanel.remove(finalDisplay);
		mixerGUI = newMixerGUI;
		centerPanel.add(mixerGUI);
		centerPanel.add(colorMapGUI);
		centerPanel.add(finalDisplay);

		revalidate();
		
		// TODO code smell, on the new MixerModel, but for different GUI
		colorMapPreprocessingGUI.updateColorMapPreprocessingGUI(
				newMixerGUI.getMixerModel().mapping);
	}
	
	public void updateFinalDisplaySize(TopPanelModel topPanelModel)
	{
		int fdWidth = topPanelModel.finalWidth;
		int fdHeight = topPanelModel.finalHeight;
		
		if (topPanelModel.isVerticallySymmetric) fdWidth *= 2;
		
		if (topPanelModel.isHorizontallySymmetric) fdHeight *= 2;
		
		Dimension preferredDims = 
				new Dimension(fdWidth + 15, fdHeight + 24);
		finalDisplay.setPreferredSize(preferredDims);
		finalDisplay.setMaximumSize(preferredDims);
	}
	
	public void updateOctaveDisplays()
	{
		colorMapGUI.repaint();
		
		for (OctaveGUI oct: octaveGUIs)
		{
			oct.update();
		}
		remix();
	}

	public void updateOctaveDisplay(int idx)
	{
		octaveGUIs[idx].update();
		remix();
	}
	
	// updates Final display graphic, only
	public void remix()
	{
		for (int i = 0; i < appSettings.octaves; i++)
		{
			octaveModels[i] = octaveGUIs[i].getOctaveModel();
		}
		NoiseData noiseData = TextureFunctions.makeNoiseDataArray(
				appSettings, octaveModels, mixerGUI.getMixerModel());
		
		BufferedImage image = TextureFunctions.makeImage(
				noiseData, mixerGUI.getMixerModel(), 
				ColorMapSelectorGUI.getColorMap());

		finalDisplay.update(image, appSettings);
		
		if (animationPanel != null)
		{
			animationPanel.requireReload();
		}
	}
	
	public void setAnimationPanel(AnimationPanel animationPanel)
	{
		this.animationPanel = animationPanel;
	}
}