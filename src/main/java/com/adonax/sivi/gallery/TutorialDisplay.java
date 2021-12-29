package com.adonax.sivi.gallery;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import com.adonax.sivi.MixerGUI;
import com.adonax.sivi.NoiseData;
import com.adonax.sivi.TextureFunctions;
import com.adonax.sivi.TextureModel;
import com.adonax.sivi.TopPanel;

public class TutorialDisplay extends JPanel 
{
	private static final long serialVersionUID = 1L;
	
	TutorialDisplay(final TextureModel sivi, final TopPanel topPanel)
	{
		TitledBorder combineTitledBorder = 
				BorderFactory.createTitledBorder(
						"Click to load into visualizer");
		setBorder(combineTitledBorder);
		NoiseData noiseData =  
				TextureFunctions.makeNoiseDataArray(
						sivi.appSettings,
						sivi.octaveModels,
						sivi.mixerModel);
		final BufferedImage image = TextureFunctions.makeImage(
				noiseData, sivi.mixerModel, 
				sivi.colorAxis.getColorMap());
		
		JComponent imagePanel = new JComponent() 
		{

			private static final long serialVersionUID = 1L;
			
			@Override
			public void paintComponent(Graphics g) {
				g.drawImage(image, 0, 0, null);
			}
			
			@Override
			public Dimension getPreferredSize() 
			{
				return new Dimension(
						image.getWidth(), image.getHeight());
			}
		};
		add(imagePanel);
		imagePanel.addMouseListener(new MouseListener()
		{	
			@Override
			public void mouseReleased(MouseEvent arg0){}
			
			@Override
			public void mousePressed(MouseEvent arg0){}
			
			@Override
			public void mouseExited(MouseEvent arg0)
			{
				// TODO normal border
			}
			
			@Override
			public void mouseEntered(MouseEvent arg0)
			{
				// TODO light up border
			}
			
			@Override
			public void mouseClicked(MouseEvent arg0)
			{
				topPanel.setAppSettings(sivi.appSettings);
				topPanel.loadOctavesPanel(sivi.octaveModels);
				topPanel.updateMixerGUI(new MixerGUI(
						topPanel, sivi.mixerModel, sivi.gradientGUIModel));	
				topPanel.colorMapSelectorGUI.setColorAxis(0, sivi.colorAxis);
				topPanel.colorMapSelectorGUI.setSelected(0, false);
//				topPanel.updateFinalDisplay(
//						new FinalDisplay(sivi.appSettings));
				topPanel.updateFinalDisplaySize(sivi.appSettings);
				
				topPanel.updateOctaveDisplays();
			}
		});
	}
}
