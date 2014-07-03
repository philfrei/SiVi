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
package com.adonax.simplexNoiseVisualizer;

import java.awt.*;
import java.awt.image.BufferedImage;

import javax.swing.*;

import com.adonax.simplexNoiseVisualizer.color.ColorMapSelectorGUI;
import com.adonax.simplexNoiseVisualizer.gradients.GradientGUIModel;
import com.adonax.simplexNoiseVisualizer.gradients.LinearGradientFunction;
import com.adonax.simplexNoiseVisualizer.gradients.RadialGradientFunction;
import com.adonax.simplexNoiseVisualizer.gradients.SinusoidalGradientFunction;

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
	
	public final ColorMapSelectorGUI colorMapGUI;
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
		finalDisplay = new FinalDisplay(appSettings);
		colorMapGUI = new ColorMapSelectorGUI(this);
		
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

		// TODO: restore code export (to MenuBar)
//		JButton exportBtn = new JButton("Export Code");
//		exportBtn.addActionListener(new ActionListener() {
//			@Override
//			public void actionPerformed(ActionEvent arg0)
//			{
//				java.util.List<TextureParams> textureParamsList = new ArrayList<TextureParams>(4);
//				textureParamsList.add(sts1.getTextureParams());
//				textureParamsList.add(sts2.getTextureParams());
//				textureParamsList.add(sts3.getTextureParams());
//				textureParamsList.add(sts4.getTextureParams());
//
//				new ExportFrame(STBPanel.this.host, textureParamsList, tc.getCombineParams());
//			}
//		});

		centerPanel = new JPanel();
		centerPanel.add(mixerGUI);
		centerPanel.add(colorMapGUI);
		centerPanel.add(finalDisplay);

		finalDisplay.setPreferredSize(
				new Dimension(appSettings.finalWidth + 16, 
						appSettings.finalHeight + 36));

		add(centerPanel, BorderLayout.CENTER);		
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
	}
	
	public void updateFinalDisplay(FinalDisplay newFinalDisplay)
	{
		centerPanel.remove(finalDisplay);
		finalDisplay = newFinalDisplay;
		centerPanel.add(finalDisplay);
		revalidate();
		repaint();
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
				appSettings.finalWidth, 
				appSettings.finalHeight, 
				octaveModels, mixerGUI.getMixerModel());
		
		BufferedImage image = TextureFunctions.makeImage(
				noiseData, mixerGUI.getMixerModel(), 
				ColorMapSelectorGUI.getColorMap());

		finalDisplay.update(image);
	}
}
