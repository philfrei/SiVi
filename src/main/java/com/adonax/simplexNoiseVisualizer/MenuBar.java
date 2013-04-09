package com.adonax.simplexNoiseVisualizer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JDialog;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import com.adonax.simplexNoiseVisualizer.color.ColorAxis;
import com.adonax.simplexNoiseVisualizer.gradients.GradientGUIModel;
import com.adonax.simplexNoiseVisualizer.tutorial.TutorialFramework;


public class MenuBar
{
	private TopPanel topPanel;
	
	MenuBar(TopPanel topPanel)
	{
		this.topPanel = topPanel;
	}
	
	
	public JMenuBar create()
	{
		JMenuBar menuBar = new JMenuBar();
		
		JMenu fileMenu = new JMenu("File");
		JMenuItem newFile = new JMenuItem("New", KeyEvent.VK_N);
		newFile.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				TopPanelModel settings = new TopPanelModel();
				OctaveModel[] octaves = 
						new OctaveModel[settings.octaves];
				for (int i = 0; i < settings.octaves; i++)
				{
					octaves[i] = new OctaveModel();
				}
				MixerModel mixer = new MixerModel(settings);
				ColorAxis colorAxis = new ColorAxis();
				TextureModel sivi = new TextureModel(settings, octaves,
						mixer, new GradientGUIModel(), colorAxis);

				topPanel.setAppSettings(sivi.appSettings);
				topPanel.loadOctavesPanel(sivi.octaveModels);
				topPanel.updateMixerGUI(new MixerGUI(
						topPanel, sivi.mixerModel, 
						new GradientGUIModel()));	
				topPanel.colorMapGUI.setColorAxis(0, sivi.colorAxis);
				topPanel.colorMapGUI.setSelected(0, false);
				topPanel.updateFinalDisplay(
						new FinalDisplay(sivi.appSettings));
				
				topPanel.updateOctaveDisplays();
			}
		});
		JMenuItem loadFile = new JMenuItem("Load", KeyEvent.VK_L);
		loadFile.setEnabled(false);
		JMenuItem closeFile = new JMenuItem("Close", KeyEvent.VK_C);
		closeFile.setEnabled(false);
		JMenuItem saveFile = new JMenuItem("Save", KeyEvent.VK_S);
		saveFile.setEnabled(false);
		JMenuItem importSubMenu = new JMenuItem("Import", KeyEvent.VK_I);
		importSubMenu.setEnabled(false);
		JMenuItem exportSubMenu = new JMenuItem("Export", KeyEvent.VK_E);
		exportSubMenu.setEnabled(false);
		JMenuItem quitApplication = new JMenuItem("Quit", KeyEvent.VK_Q);
		quitApplication.addActionListener(new ActionListener()
		{
			
			@Override
			public void actionPerformed(ActionEvent e)
			{
				System.exit(0);
			}
		});
			
		fileMenu.add(newFile);
		fileMenu.add(loadFile);
		fileMenu.add(closeFile);
		fileMenu.add(saveFile);
		fileMenu.addSeparator();
		fileMenu.add(importSubMenu);
		fileMenu.add(exportSubMenu);
		fileMenu.addSeparator();
		fileMenu.add(quitApplication);
			
		menuBar.add(fileMenu);

		JMenu viewMenu = new JMenu("View");
		JMenuItem viewTutorial = new JMenuItem("Tutorial", KeyEvent.VK_T);
		viewTutorial.addActionListener(new ActionListener()
		{
			
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
			    TutorialFramework tutorial = 
			    		new TutorialFramework(topPanel);
		        tutorial.setBounds(100, 100, 800 + 8, 700 + 34);
				tutorial.setVisible(true);
			}
		});
		
		
		JMenuItem codeGeneratorSubMenu = 
				new JMenuItem("Code Generator", KeyEvent.VK_C);
		codeGeneratorSubMenu.setEnabled(false);
		JMenuItem animatorPanel = new JMenuItem("Animator", KeyEvent.VK_A);
	    animatorPanel.setEnabled(false);
		JMenuItem settingsDialog = new JMenuItem("Settings", KeyEvent.VK_S);
	    settingsDialog.addActionListener(new ActionListener()
		{	
			@Override
			public void actionPerformed(ActionEvent e)
			{
				JDialog settingsDialog = new JDialog(){
					private static final long serialVersionUID = 1L;
				};
				settingsDialog.add(new SettingsPanel(topPanel));
				settingsDialog.setTitle("Edit Application Settings");
				settingsDialog.setBounds(0, 0, 250, 150);
				settingsDialog.setModal(true);
//				settingsDialog.setAlwaysOnTop(true);
				settingsDialog.setVisible(true);
			}
		});
	    
		viewMenu.add(viewTutorial);
		viewMenu.add(codeGeneratorSubMenu);
		viewMenu.add(animatorPanel);
		viewMenu.addSeparator();
		viewMenu.add(settingsDialog);
		
		menuBar.add(viewMenu);
		
		JMenu helpMenu = new JMenu("Help");
		JMenuItem aboutItem = new JMenuItem("About", KeyEvent.VK_A);
		helpMenu.add(aboutItem);
		
		menuBar.add(helpMenu);

		return menuBar;
	}
}
