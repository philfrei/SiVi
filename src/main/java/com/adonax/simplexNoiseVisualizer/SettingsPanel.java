package com.adonax.simplexNoiseVisualizer;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.adonax.simplexNoiseVisualizer.gradients.GradientGUI;
import com.adonax.simplexNoiseVisualizer.gradients.GradientGUIModel;
import com.adonax.simplexNoiseVisualizer.gradients.LinearGradientFunction;
import com.adonax.simplexNoiseVisualizer.gradients.RadialGradientFunction;
import com.adonax.simplexNoiseVisualizer.gradients.SinusoidalGradientFunction;

public class SettingsPanel extends JPanel
{

	private static final long serialVersionUID = 1L;

	Object parent;
	
	SettingsPanel(final TopPanel topPanel)
	{
		TopPanelModel settings = topPanel.getAppSettings();
		
		JLabel octavesLbl = new JLabel("Octaves");
		final JTextField octaves = new JTextField(
				String.valueOf(topPanel.getAppSettings().octaves), 5);
		octaves.addActionListener(new ActionListener()
		{			
			@Override
			public void actionPerformed(ActionEvent e)
			{
				/*
				 * Redisplay with new number of octaves, preserving 
				 * existing octaves where possible.
				 */
				int octaveChannels = Integer.valueOf(octaves.getText()); 
				TopPanelModel model = topPanel.getAppSettings();
				model = TopPanelModel.setAppSetting(model, 
						TopPanelModel.Fields.OCTAVES, 
						octaveChannels);
				topPanel.setAppSettings(model);
				
				MixerModel mm = topPanel.mixerGUI.getMixerModel();
				GradientGUIModel ggm = 
						topPanel.mixerGUI.getGradientGUI().getModel();
						
				float[] weights = mm.weights;
				float[] newWeights = new float[model.octaves];
				for (int i = 0; i < model.octaves; i++)
				{
					if (i < weights.length)
					{
						newWeights[i] = weights[i];
					}
					else
					{
						newWeights[i] = 0.25f; //TODO: default weight
					}
				}
				mm = MixerModel.updateMixSetting(mm, 
						MixerModel.Fields.WEIGHTS, newWeights);
				
				topPanel.updateMixerGUI(
						new MixerGUI(topPanel, mm, ggm));	
				
				topPanel.updateOctavesPanel();
				topPanel.updateOctaveDisplays();

			}
		});
		
		JLabel widthLbl = new JLabel("Width");
		final JTextField widthSetting = new JTextField(5);
		widthSetting.setText(String.valueOf(settings.finalWidth));
		widthSetting.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{	
				GradientGUI.closeAllGradientWindows();
				
				int newWidth = 
						Integer.valueOf(widthSetting.getText());
				
				TopPanelModel newSettings = 
						TopPanelModel.setAppSetting(
								topPanel.getAppSettings(), 
								TopPanelModel.Fields.FINAL_WIDTH, 
								newWidth);
				
				rebuildWithNewSizeSettings(topPanel, newSettings);
			}
		});
		
		JLabel heightLbl = new JLabel("Height");
		final JTextField heightSetting = new JTextField(5);
		heightSetting.setText(String.valueOf(settings.finalHeight));
		heightSetting.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				GradientGUI.closeAllGradientWindows();
				
				int newHeight = 
						Integer.valueOf(heightSetting.getText());
				
				TopPanelModel newSettings = 
						TopPanelModel.setAppSetting(
								topPanel.getAppSettings(), 
								TopPanelModel.Fields.FINAL_HEIGHT, 
								newHeight);

				rebuildWithNewSizeSettings(topPanel, newSettings);
			}
		});
		
		JLabel colorMapsLbl = new JLabel("Color Maps");
		JTextField colorMapsSetting = new JTextField(5);
		colorMapsSetting.setEnabled(false);
		colorMapsSetting.addActionListener(new ActionListener()
		{
			
			@Override
			public void actionPerformed(ActionEvent e)
			{
				// TODO Auto-generated method stub
				/*
				 * Goal: change the number of color maps in the
				 * color map GUI, non-destructively as much as 
				 * possible.
				 */
			}
		});
		
		setLayout(new GridBagLayout());
		GridBagConstraints gbConstraints = new GridBagConstraints();
		gbConstraints.anchor = GridBagConstraints.LINE_START;
		
		gbConstraints.gridx = 0;
		gbConstraints.gridy = 0;
		add(octavesLbl, gbConstraints);
		gbConstraints.gridx = 1;
		gbConstraints.gridy = 0;
		add(octaves, gbConstraints);
		
		gbConstraints.gridx = 0;
		gbConstraints.gridy = 1;
		add(widthLbl, gbConstraints);
		gbConstraints.gridx = 1;
		gbConstraints.gridy = 1;
		add(widthSetting, gbConstraints);
		
		gbConstraints.gridx = 0;
		gbConstraints.gridy = 2;
		add(heightLbl, gbConstraints);
		gbConstraints.gridx = 1;
		gbConstraints.gridy = 2;
		add(heightSetting, gbConstraints);
		
		gbConstraints.gridx = 0;
		gbConstraints.gridy = 3;
		add(colorMapsLbl, gbConstraints);
		gbConstraints.gridx = 1;
		gbConstraints.gridy = 3;
		add(colorMapsSetting, gbConstraints);
		
	}
	
	private GradientGUIModel reviseGradientGUIModel(
			GradientGUIModel ggm, 
			TopPanelModel oldMSettings, TopPanelModel newSettings)
	{
		
		LinearGradientFunction oldLGF = 
				(LinearGradientFunction)ggm.gradients[0];
		
		LinearGradientFunction newLGF = new LinearGradientFunction(
				newSettings.finalWidth, newSettings.finalHeight,
				oldLGF.xLeft, oldLGF.xRight,
				oldLGF.yTop, oldLGF.yBottom);
		
		RadialGradientFunction oldRGF = 
				(RadialGradientFunction)ggm.gradients[1];
		
		float widthScaling = newSettings.finalWidth / (float)oldMSettings.finalWidth; 
		float heightScaling = newSettings.finalHeight / (float)oldMSettings.finalHeight;
		
		// or should we leave 'center point' at same abs location?
		RadialGradientFunction newRGF = new RadialGradientFunction(
				(int)(oldRGF.centerPoint.getX() * widthScaling),
				(int)(oldRGF.centerPoint.getY() * heightScaling),
				// Tricky: assumes one changed, and other is 1:1
				(float)oldRGF.radius * widthScaling * heightScaling,
				oldRGF.edgeVal, oldRGF.centerVal);
		
		SinusoidalGradientFunction oldSGF =
				(SinusoidalGradientFunction)ggm.gradients[2];
		
		SinusoidalGradientFunction newSGF = 
				new SinusoidalGradientFunction(
						oldSGF.originX, oldSGF.originY,
						oldSGF.period, oldSGF.theta,
						oldSGF.highVal,	oldSGF.lowVal);
		
		return new GradientGUIModel(
				newLGF, newRGF, newSGF, ggm.selected);
	}
	
	private void rebuildWithNewSizeSettings(TopPanel topPanel, 
			TopPanelModel newSettings)
	{
		// General note: color not affected, thus ignored.
		OctaveModel[] octaveModels = 
				new OctaveModel[newSettings.octaves];
		for (int i = 0; i < newSettings.octaves; i++)
		{
			octaveModels[i] = 
					topPanel.octaveGUIs[i].getTextureParams();
		}

		GradientGUIModel gradientGUIModel = 
				reviseGradientGUIModel(
					topPanel.mixerGUI.getGradientGUI().getModel(),
					topPanel.getAppSettings(), 
					newSettings);	

		NoiseData gradientData = 
				GradientGUI.createGradientFunctionData(
						newSettings.finalWidth,
						newSettings.finalHeight,
						gradientGUIModel);
		
		MixerModel newMixerModel = 
				MixerModel.updateMixSetting(
						topPanel.mixerGUI.getMixerModel(), 
						MixerModel.Fields.GRADIENT_DATA, 
						gradientData);
		
		TextureModel sivi = new TextureModel(
				newSettings, 
				octaveModels, 
				newMixerModel, 
				gradientGUIModel, 
				null);
		
		topPanel.setAppSettings(sivi.appSettings);
		topPanel.loadOctavesPanel(sivi.octaveModels);
		topPanel.updateMixerGUI(new MixerGUI(
				topPanel, sivi.mixerModel, 
				sivi.gradientGUIModel));	
		topPanel.updateFinalDisplay(
				new FinalDisplay(sivi.appSettings));
		
		topPanel.updateOctaveDisplays();
		
	}
	
}
