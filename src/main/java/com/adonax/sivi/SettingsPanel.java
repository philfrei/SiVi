package com.adonax.sivi;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.adonax.sivi.gradients.GradientGUI;
import com.adonax.sivi.gradients.GradientGUIModel;
import com.adonax.sivi.gradients.LinearGradientFunction;
import com.adonax.sivi.gradients.RadialGradientFunction;
import com.adonax.sivi.gradients.SinusoidalGradientFunction;
import com.adonax.sivi.utils.NoiseEngines;

public class SettingsPanel extends JPanel
{

	private static final long serialVersionUID = 1L;

	Object parent;
	private JComboBox<NoiseEngines.Sources> cboNoiseEngines;
	TopPanelModel settings;
	
	SettingsPanel(final TopPanel topPanel)
	{
		settings = topPanel.getAppSettings();
		
		JLabel octavesLbl = new JLabel("Octaves   ");
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
						// default value TODO *magic number* antipattern 
						newWeights[i] = 0.25f; 
					}
				}
				mm = MixerModel.updateMixSetting(mm, 
						MixerModel.Fields.WEIGHTS, newWeights);
				
				topPanel.updateMixerGUI(new MixerGUI(topPanel, mm, ggm));	
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
	
		JSeparator sepEngine = new JSeparator(SwingConstants.HORIZONTAL);
		sepEngine.setPreferredSize(new Dimension(250, 1));
		JLabel lblNoiseEngine = new JLabel("Engine");
		cboNoiseEngines = new JComboBox<NoiseEngines.Sources>(
				NoiseEngines.Sources.values());
		cboNoiseEngines.setSelectedItem(TextureFunctions.getNoiseSource());
		cboNoiseEngines.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				TextureFunctions.setNoiseEngine(
						(NoiseEngines.Sources)cboNoiseEngines.getSelectedItem());
				topPanel.updateOctaveDisplays();
			}
		});
		
		JSeparator sepReflection = new JSeparator(SwingConstants.HORIZONTAL);
		sepReflection.setPreferredSize(new Dimension(250, 1));		
		JLabel lblReflections = new JLabel("DISPLAY REFLECTIONS:  ");
		JCheckBox chkBoxXAxisReflection = new JCheckBox("X-Axis");
		chkBoxXAxisReflection.setSelected(settings.isVerticallySymmetric);
		chkBoxXAxisReflection.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				TopPanelModel newSettings = TopPanelModel.setAppSetting(
						settings, TopPanelModel.Fields.IS_VERTICALLY_SYMMETRIC, 
						chkBoxXAxisReflection.isSelected());
				rebuildWithNewSizeSettings(topPanel, newSettings);
			}
		});
		
		JCheckBox chkBoxYAxisReflection = new JCheckBox("Y-Axis");
		chkBoxYAxisReflection.setSelected(settings.isHorizontallySymmetric);
		chkBoxYAxisReflection.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				TopPanelModel newSettings = TopPanelModel.setAppSetting(
						settings, TopPanelModel.Fields.IS_HORIZONTALLY_SYMMETRIC, 
						chkBoxYAxisReflection.isSelected());
				rebuildWithNewSizeSettings(topPanel, newSettings);
			}
		});

//		JSeparator sepWrap = new JSeparator(SwingConstants.HORIZONTAL);
//		sepWrap.setPreferredSize(new Dimension(250, 1));
//		JLabel lblWraparound = new JLabel("WRAP (for tiling):  ");
//		JCheckBox chkWrapVertical = new JCheckBox("Wrap Vertically");
//		JCheckBox chkWrapHorizontal = new JCheckBox("Wrap Horizontally");	
		
		setLayout(new GridBagLayout());
		GridBagConstraints gbConstraints = new GridBagConstraints();
		gbConstraints.anchor = GridBagConstraints.LINE_START;
		
		int gridYval = 0;
		
		gbConstraints.gridx = 0;
		gbConstraints.gridy = gridYval;
		add(octavesLbl, gbConstraints);
		gbConstraints.gridx = 1;
		gbConstraints.gridy = gridYval;
		add(octaves, gbConstraints);
		
		gbConstraints.gridx = 0;
		gbConstraints.gridy = ++gridYval;
		add(widthLbl, gbConstraints);
		gbConstraints.gridx = 1;
		gbConstraints.gridy = gridYval;
		add(widthSetting, gbConstraints);
		
		gbConstraints.gridx = 0;
		gbConstraints.gridy = ++gridYval;
		gbConstraints.insets = new Insets(0,0,5,0);
		add(heightLbl, gbConstraints);
		gbConstraints.gridx = 1;
		gbConstraints.gridy = gridYval;
		add(heightSetting, gbConstraints);
						
		// Reflection
		gbConstraints.gridx = 0;
		gbConstraints.gridy = ++gridYval;
		gbConstraints.gridwidth = 2;
		gbConstraints.insets = new Insets(0, 0, 0, 0);
		add(sepReflection, gbConstraints);
		gbConstraints.gridx = 0;
		gbConstraints.gridy = ++gridYval;
		gbConstraints.gridwidth = 0;
		add(lblReflections, gbConstraints);
		gbConstraints.gridx = 1;
		gbConstraints.gridy = ++gridYval;
		gbConstraints.gridwidth = 1;
		add(chkBoxXAxisReflection, gbConstraints);
		gbConstraints.gridx = 1;
		gbConstraints.gridy = ++gridYval;
		add(chkBoxYAxisReflection, gbConstraints);

		// Wrap
//		gbConstraints.gridx = 0;
//		gbConstraints.gridy = ++gridYval;
//		gbConstraints.gridwidth = 2;
//		add(sepWrap, gbConstraints);
//		gbConstraints.gridx = 0;
//		gbConstraints.gridy = ++gridYval;
//		gbConstraints.gridwidth = 2;
//		add(lblWraparound, gbConstraints);
//		gbConstraints.gridx = 1;
//		gbConstraints.gridy = ++gridYval;
//		gbConstraints.gridwidth = 1;
//		add(chkWrapVertical, gbConstraints);
//		gbConstraints.gridx = 1;
//		gbConstraints.gridy = ++gridYval;
//		add(chkWrapHorizontal, gbConstraints);

		// Engine
		gbConstraints.gridx = 0;
		gbConstraints.gridy = ++gridYval;
		gbConstraints.gridwidth = 2;
		gbConstraints.insets = new Insets(0,0,5,0);		
		add(sepEngine, gbConstraints);
		gbConstraints.gridx = 0;
		gbConstraints.gridy = ++gridYval;
		gbConstraints.insets = new Insets(0,0,0,0);

		add(lblNoiseEngine, gbConstraints);
		gbConstraints.gridx = 1;
		gbConstraints.gridy = gridYval;
		add(cboNoiseEngines, gbConstraints);

	}
	
	private GradientGUIModel reviseGradientGUIModel(
			GradientGUIModel ggm, 
			TopPanelModel oldMSettings, TopPanelModel newSettings)
	{
		settings = newSettings;
		
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
		settings = newSettings;
		
		// General note: color not affected, thus ignored.
		OctaveModel[] octaveModels = 
				new OctaveModel[newSettings.octaves];
		for (int i = 0; i < newSettings.octaves; i++)
		{
			octaveModels[i] = 
					topPanel.octaveGUIs[i].getOctaveModel();
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
		topPanel.updateFinalDisplaySize(sivi.appSettings);
		topPanel.updateOctaveDisplays();
	}
}
