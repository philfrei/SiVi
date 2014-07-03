package com.adonax.simplexNoiseVisualizer.animation;

/*
 * TODO: Better management of the keyboard input of the data. 
 * It is vexing to always have to hit <Enter>. 
 * Surely this can be better handled.
 * 
 * TODO: Create a provision for incorporating additional 
 * animation vectors, such as translation or scaling.
 */

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;


import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.adonax.simplexNoiseVisualizer.MixerModel;
import com.adonax.simplexNoiseVisualizer.NoiseData;
import com.adonax.simplexNoiseVisualizer.OctaveModel;
import com.adonax.simplexNoiseVisualizer.TextureFunctions;
import com.adonax.simplexNoiseVisualizer.TopPanel;
import com.adonax.simplexNoiseVisualizer.TopPanelModel;
import com.adonax.simplexNoiseVisualizer.color.ColorMap;
import com.adonax.simplexNoiseVisualizer.color.ColorMapSelectorGUI;
import com.adonax.simplexNoiseVisualizer.utils.FloatArrayFunctions;

public class AnimationPanel extends JPanel
{
	
	private BufferedImage[] images;
	private int imagesCount;
	private float zIncr;
	private int overlap;
	private int imgIndex;
	private int deltaTime;
	private boolean imagesUpdated;
	
	private JButton loadBtn, viewBtn, exportBtn;
	private JSlider imgSlider;
	private boolean animationRunning;
	
	private TopPanel topPanel;
	
	public AnimationPanel(final TopPanel topPanel)
	{
		
		this.topPanel = topPanel;
		
		//*********** Controls ************
		
		JLabel stepsLbl = new JLabel("Steps");
		stepsLbl.setHorizontalTextPosition(SwingConstants.RIGHT);
		final JTextField stepsFld = new JTextField();
		stepsFld.setHorizontalAlignment(JTextField.RIGHT);
		stepsFld.setMargin(new Insets(0, 2, 0, 2));
		stepsFld.setColumns(4);
		stepsFld.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				imagesCount = Integer.valueOf(stepsFld.getText());
				images = new BufferedImage[imagesCount];
				imagesUpdated = false;
				updateForm();
			}
		});	
		
		JLabel zIncrLbl = new JLabel("Z Increment");
		final JTextField zIncrFld = new JTextField();
		zIncrFld.setHorizontalAlignment(JTextField.RIGHT);
		zIncrFld.setMargin(new Insets(0, 2, 0, 2));
		zIncrFld.setColumns(4);
		zIncrFld.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				zIncr = Float.valueOf(zIncrFld.getText());
				imagesUpdated = false;
				updateForm();
			}
		});
	
		JLabel overlapLbl = new JLabel("Overlap");
		final JTextField overlapFld = new JTextField();
		overlapFld.setHorizontalAlignment(JTextField.RIGHT);
		overlapFld.setMargin(new Insets(0, 2, 0, 2));
		overlapFld.setColumns(4);
		overlapFld.addActionListener(new ActionListener()
		{		
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				overlap = Integer.valueOf(overlapFld.getText());
				imagesUpdated = false;
				updateForm();
			}
		});		
		
		JLabel deltaTimeLbl = new JLabel("Frame time (millis)");
		deltaTimeLbl.setHorizontalTextPosition(SwingConstants.RIGHT);
		final JTextField deltaTimeFld = new JTextField();
		deltaTimeFld.setHorizontalAlignment(JTextField.RIGHT);
		deltaTimeFld.setMargin(new Insets(0, 2, 0, 2));
		deltaTimeFld.setColumns(4);
		deltaTimeFld.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				deltaTime = Integer.valueOf(deltaTimeFld.getText());
				updateForm();
			}
		});

		viewBtn = new JButton("View");
		viewBtn.setEnabled(false);
		viewBtn.addActionListener(new ActionListener()
		{		
			@Override
			public void actionPerformed(ActionEvent e)
			{
				toggleAnimator();
			}
		});

		
		loadBtn = new JButton("Load");
		loadBtn.setEnabled(false);
		loadBtn.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				loadImageArray();
				imagesUpdated = true;
				updateForm();
			}
		});
			
		
		imgSlider = new JSlider(0, 20, 0);
		imgSlider.setEnabled(false);
		imgSlider.addChangeListener(new ChangeListener()
		{		
			@Override
			public void stateChanged(ChangeEvent e)
			{
				imgIndex = imgSlider.getValue();
				viewImage(imgIndex);
			}
		});
		
		
		exportBtn = new JButton("Export Animated GIF");
		exportBtn.setEnabled(false);
		exportBtn.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				String graphicFormat = "gif";
				File fileName;
				
				JFileChooser fs = new JFileChooser();
				FileNameExtensionFilter filter = new FileNameExtensionFilter(
				        "Graphic format " + graphicFormat, graphicFormat);
				fs.setFileFilter(filter);
				int returnVal = fs.showSaveDialog(null);
				if(returnVal == JFileChooser.APPROVE_OPTION) 
				{
					fileName = new File(
							fs.getSelectedFile().getAbsoluteFile()
							+ "." + graphicFormat);
					
					try
					{
						AnimatedGifExport.Export(images, deltaTime, fileName);
					} 
					catch (IOException e)
					{
						e.printStackTrace();
					}					
				}
				else 
				{
					System.out.println("File not saved.");
				}
			}
		});
		
		//************* LAYOUT ************
		
		setLayout(new GridBagLayout());
		GridBagConstraints gbConstraints = new GridBagConstraints();
		gbConstraints.anchor = GridBagConstraints.LINE_START;
		gbConstraints.insets = new Insets(2, 5, 2, 5);
		
		gbConstraints.gridx = 0;
		gbConstraints.gridy = 0;
		add(stepsLbl, gbConstraints);
		gbConstraints.gridx = 1;
		gbConstraints.gridy = 0;
		add(stepsFld, gbConstraints);
		
		gbConstraints.gridx = 0;
		gbConstraints.gridy = 1;
		add(zIncrLbl, gbConstraints);
		gbConstraints.gridx = 1;
		gbConstraints.gridy = 1;
		add(zIncrFld, gbConstraints);

		gbConstraints.gridx = 0;
		gbConstraints.gridy = 2;
		add(overlapLbl, gbConstraints);
		gbConstraints.gridx = 1;
		gbConstraints.gridy = 2;
		add(overlapFld, gbConstraints);		
		
		gbConstraints.gridx = 0;
		gbConstraints.gridy = 3;
		add(deltaTimeLbl, gbConstraints);
		gbConstraints.gridx = 1;
		gbConstraints.gridy = 3;
		add(deltaTimeFld, gbConstraints);	
		
		gbConstraints.gridx = 0;
		gbConstraints.gridy = 4;
		add(loadBtn, gbConstraints);
		gbConstraints.gridx = 1;
		gbConstraints.gridy = 4;
		add(viewBtn, gbConstraints);	
		
			
		gbConstraints.gridx = 0;
		gbConstraints.gridy = 5;
		gbConstraints.gridwidth = 2;
		add(imgSlider, gbConstraints);
		
		gbConstraints.gridx = 0;
		gbConstraints.gridy = 6;
		add(exportBtn, gbConstraints);	
	}

	private void updateForm()
	{
		updateControls();
		repaint();
	}
	
	private void updateControls()
	{
		if (imagesCount > 0 && zIncr > 0 )
		{
			loadBtn.setEnabled(!imagesUpdated);			
			imgSlider.setMaximum(Math.max(0, imagesCount - 1));
			imgSlider.setEnabled(imagesUpdated);
			
			if (deltaTime > 0 )
			{
				viewBtn.setEnabled(imagesUpdated);
				exportBtn.setEnabled(imagesUpdated);
			}
			else
			{
				viewBtn.setEnabled(false);
				exportBtn.setEnabled(false);
			}
		}
		else
		{
			loadBtn.setEnabled(false);
			imgSlider.setEnabled(false);
			viewBtn.setEnabled(false);
			exportBtn.setEnabled(false);
		}
	}
	
	/*
	 * IMAGE LOADING
	 * This routine can take a long time to execute. In part,
	 * this is because we are using 3D calls instead of 2D as
	 * used in the rest of the program. Also, there are "double"
	 * calls per image in cases of overlap.
	 * 
	 * TODO: This routine can fail due to over taxing memory. No
	 * exception or management has been implemented for this.
	 */
	private void loadImageArray()
	{
		// initialize
		float z = 0;
		TopPanelModel tpm = topPanel.getAppSettings();
		int imgWidth = tpm.finalWidth;
		int imgHeight = tpm.finalHeight;
		
		int octaves = tpm.octaves;
		OctaveModel[] om = new OctaveModel[octaves];
		for (int i = 0; i < octaves; i++)
		{
			om[i] = topPanel.octaveGUIs[i].getOctaveModel();
		}
		
		MixerModel mm = topPanel.mixerGUI.getMixerModel();
		ColorMap cm = ColorMapSelectorGUI.getColorMap(); 
		
		NoiseData nd = null;
		NoiseData ndOverlap = null;
		
		// call for each image
		for (int i = 0; i < imagesCount; i++)
		{
			nd = TextureFunctions.makeNoiseDataArray(
					imgWidth, imgHeight, om, mm, z);
			
//			System.out.println("i:" + i + "  n:" + imagesCount 
//					+ "  overlap:" + overlap);
			
			/*
			 *  Overlap computations.
			 *  
			 *  TODO: OK, I admit I was counting on my fingers and toes.  
			 *  Definitely room for improvement here. Goal, to
			 *  implement Ken Perlin's algorithm: 
			 *  
			 *  	replace the function F(x,y,z) with: 
			 * 		F2(x,y,z) = (1-z)*F(x,y,z) + z*F(x,y,z-1) 
			 *  
			 *  http://www.noisemachine.com/talk1/24b.html
			 *  
			 *  Would be great if someone improved the following, 
			 *  made it easier to read. Phil Freihofner 7/2/2014.
			 */
			if (i >= imagesCount - overlap )
			{
				ndOverlap = TextureFunctions.makeNoiseDataArray(
						imgWidth, imgHeight, om, mm, 
						(i - imagesCount) * zIncr);
				
				int bFactor = (overlap + 1) - (imagesCount - i);
				int aFactor = (overlap + 1) - bFactor;
				
				//System.out.println("aF:" + aFactor + "   bF:" + bFactor);
				
				/*
				 *  TODO: The following linear interpolation of 
				 *  the noise data, used to smoothly blend together 
				 *  the end and the beginning of the image loop, tends
				 *  to push values towards the center of the ColorMap.
				 *  This can lead to a temporarily visible change
				 *  in the appearance of the texture.
				 *  
				 *  Any thoughts on how to eliminate this undesired 
				 *  artifact?
				 */
				float[] noiseArray = 
						FloatArrayFunctions.LerpTwoFloatArrays(
								nd.noiseArray, aFactor, 
								ndOverlap.noiseArray, bFactor);
				
				nd = new NoiseData(imgWidth, imgHeight, noiseArray);
			}
			
			z += zIncr;
			
			images[i] = TextureFunctions.makeImage(nd, mm, cm);
			
		}
	}
	
	private void viewImage(int i)
	{
		topPanel.finalDisplay.update(images[i]);
	}

	Timer timer;
	int timerIdx;
	
	private void toggleAnimator()
	{
		if (animationRunning)
		{
			animationRunning = false;
			viewBtn.setText("View");
			timer.cancel();
		}
		else
		{
			viewBtn.setText("Stop");
			animationRunning = true;
			timerIdx = 0;
			timer = new Timer();
			timer.schedule(new AnimationTask(), 0, deltaTime);
		}
	}

	class AnimationTask extends TimerTask
	{
		@Override
		public void run()
		{
			viewImage(timerIdx);
			timerIdx++;
			if (timerIdx >= images.length) 
			{
				timerIdx = 0;
			}
		}
	}
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
}

