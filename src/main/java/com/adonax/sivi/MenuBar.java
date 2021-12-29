package com.adonax.sivi;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.adonax.sivi.animation.AnimationPanel;
import com.adonax.sivi.color.ColorAxis;
import com.adonax.sivi.gallery.GalleryDialog;
import com.adonax.sivi.gradients.GradientGUIModel;


public class MenuBar
{
	private TopPanel topPanel;
	
	private JMenuItem exportGIF, exportPNG, exportJPG;
	
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
				topPanel.colorMapSelectorGUI.setColorAxis(0, sivi.colorAxis);
				topPanel.colorMapSelectorGUI.setSelected(0, false);
				topPanel.updateFinalDisplaySize(sivi.appSettings);		
				topPanel.updateOctaveDisplays();
			}
		});
		JMenuItem loadFile = new JMenuItem("Load", KeyEvent.VK_L);
		loadFile.setEnabled(false);
		JMenuItem closeFile = new JMenuItem("Close", KeyEvent.VK_C);
		closeFile.setEnabled(false);
		JMenuItem saveFile = new JMenuItem("Save", KeyEvent.VK_S);
		saveFile.setEnabled(false);
		JMenu importSubMenu = new JMenu("Import");
		importSubMenu.setEnabled(false);
		JMenu exportSubMenu = new JMenu("Export");
		exportSubMenu.setEnabled(true);
		
			exportGIF = new JMenuItem("Export GIF", KeyEvent.VK_G);
			exportGIF.addActionListener(new ExportMenuListener());
			exportSubMenu.add(exportGIF);
			
			exportPNG = new JMenuItem("Export PNG", KeyEvent.VK_P);
			exportPNG.addActionListener(new ExportMenuListener());
			exportSubMenu.add(exportPNG);
			
			exportJPG = new JMenuItem("Export JPG", KeyEvent.VK_J);
			exportJPG.addActionListener(new ExportMenuListener());
			exportSubMenu.add(exportJPG);
		
		
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
		JMenuItem viewGallery = new JMenuItem("Gallery", KeyEvent.VK_T);
		viewGallery.addActionListener(new ActionListener()
		{
			
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
			    GalleryDialog tutorial = 
			    		new GalleryDialog(topPanel);
		        tutorial.setBounds(50, 50, 1000 + 8, 650 + 34);
				tutorial.setVisible(true);
			}
		});
				
//		JMenuItem codeGeneratorSubMenu = 
//				new JMenuItem("Code Generator", KeyEvent.VK_C);
//		codeGeneratorSubMenu.setEnabled(false);
		
		JMenuItem animatorPanel = new JMenuItem("Animation Tool", KeyEvent.VK_A);
	    animatorPanel.addActionListener(new ActionListener()
		{
			
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				JDialog animationDialog = new JDialog(){
					private static final long serialVersionUID = 1L;
				};
				final AnimationPanel animationPanel = new AnimationPanel(topPanel);
				animationDialog.add(animationPanel);
				animationDialog.setTitle("Animation Panel");
				animationDialog.setBounds(0, 0, 250, 320);
//				animationDialog.setModal(true);
				animationDialog.addWindowListener(new WindowAdapter() {
						public void windowClosing(WindowEvent e)
						{
						    animationPanel.stopTimer();
						    topPanel.remix();
						}
				});
				animationDialog.setVisible(true);				
			}
		});
	    
	    
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
				settingsDialog.setBounds(0, 0, 300, 300);
				settingsDialog.setModal(true);
//				settingsDialog.setAlwaysOnTop(true);
				settingsDialog.setVisible(true);
			}
		});
	    
		viewMenu.add(viewGallery);
//		viewMenu.add(codeGeneratorSubMenu);
		viewMenu.add(animatorPanel);
		viewMenu.addSeparator();
		viewMenu.add(settingsDialog);
		
		menuBar.add(viewMenu);
		
		JMenu helpMenu = new JMenu("Help");
		JMenuItem codeSampleItem = new JMenuItem("Code Sample");
		codeSampleItem.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				JDialog codeSampleDialog = new JDialog(){
					private static final long serialVersionUID = 1L;
				};
				codeSampleDialog.add(CodeSample.getCodeSamplePane());
				codeSampleDialog.setTitle("Code Sample");
				codeSampleDialog.setBounds(0, 0, 600, 700);
				codeSampleDialog.setModal(true);
//				settingsDialog.setAlwaysOnTop(true);
				codeSampleDialog.setVisible(true);				
			}
		});
		helpMenu.add(codeSampleItem);
		
		
		JMenuItem aboutItem = new JMenuItem("About", KeyEvent.VK_A);
		aboutItem.addActionListener(new ActionListener()
		{
			
			@Override
			public void actionPerformed(ActionEvent e)
			{
				JDialog aboutDialog = new JDialog(){
					private static final long serialVersionUID = 1L;
				};
				aboutDialog.add(AboutPane.getAboutText());
				aboutDialog.setTitle("About");
				aboutDialog.setBounds(0, 0, 400, 200);
				aboutDialog.setModal(true);
//				settingsDialog.setAlwaysOnTop(true);
				aboutDialog.setVisible(true);
			}
		});
		helpMenu.add(aboutItem);
		
		menuBar.add(helpMenu);

		return menuBar;
	}
	
	class ExportMenuListener implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent arg0)
		{
			String graphicFormat = "";
			if (arg0.getSource() == exportGIF)
			{
				graphicFormat = "gif";
			}
			else if (arg0.getSource() == exportPNG)
			{
				graphicFormat = "png";
			}
			else if (arg0.getSource() == exportJPG)
			{
				graphicFormat = "jpg";
			}
			
			BufferedImage bi = topPanel.getFinalImage();
			
			String fileName;
			JFileChooser fs = new JFileChooser();
			FileNameExtensionFilter filter = new FileNameExtensionFilter(
			        "Graphic format " + graphicFormat, graphicFormat);
			fs.setFileFilter(filter);
			int returnVal = fs.showSaveDialog(null);
			if(returnVal == JFileChooser.APPROVE_OPTION) 
			{
				fileName = fs.getSelectedFile().getAbsoluteFile()+ "." 
						+ graphicFormat;
				
				try
				{
					ImageIO.write(bi, "png", new FileOutputStream(fileName));
				} 
				catch (IOException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				System.out.println("You saved to file: " +
						fileName);      
			}
			else 
			{
				System.out.println("File not saved.");
			}
		}
		
	}
}
