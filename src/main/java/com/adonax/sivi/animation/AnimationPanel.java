package com.adonax.sivi.animation;

/*
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

import com.adonax.sivi.MixerModel;
import com.adonax.sivi.NoiseData;
import com.adonax.sivi.OctaveModel;
import com.adonax.sivi.TextureFunctions;
import com.adonax.sivi.TopPanel;
import com.adonax.sivi.TopPanelModel;
import com.adonax.sivi.color.ColorMap;
import com.adonax.sivi.color.ColorMapSelectorGUI;
import com.adonax.sivi.utils.FloatArrayFunctions;

public class AnimationPanel extends JPanel
{
	private static final long serialVersionUID = 1L;

	private BufferedImage[] images = new BufferedImage[0];
	
	// Model variables
	private int imagesCount;
	private float zIncr;
	private int overlap;
	private volatile int deltaTime;

	// state booleans
	private boolean modelIsValid;
	private boolean graphicsHaveBeenCompiled;
	
	// GUI 
	final JTextField stepsFld = new JTextField();
	final JTextField zIncrFld = new JTextField();
	final JTextField overlapFld = new JTextField();
	final JTextField deltaTimeFld = new JTextField();			
	
	private JButton btnLoad, btnRunAnimation, btnExport;
	private JSlider imgSlider;
	private boolean animationRunning;
	
	private TopPanel topPanel;
	
	public AnimationPanel(final TopPanel topPanel)
	{		
		this.topPanel = topPanel;
		topPanel.setAnimationPanel(this);
		
		JLabel stepsLbl = new JLabel("Steps");
		stepsLbl.setHorizontalTextPosition(SwingConstants.RIGHT);
		stepsFld.setHorizontalAlignment(JTextField.RIGHT);
		stepsFld.setMargin(new Insets(0, 2, 0, 2));
		stepsFld.setColumns(4);
		stepsFld.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				zIncrFld.requestFocus();
				updateForm();
			}
		});
		
		JLabel zIncrLbl = new JLabel("Z Increment");
		zIncrFld.setHorizontalAlignment(JTextField.RIGHT);
		zIncrFld.setMargin(new Insets(0, 2, 0, 2));
		zIncrFld.setColumns(4);
		zIncrFld.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				overlapFld.requestFocus();
				updateForm();
			}
		});
	
		JLabel overlapLbl = new JLabel("Overlap");
		overlapFld.setHorizontalAlignment(JTextField.RIGHT);
		overlapFld.setMargin(new Insets(0, 2, 0, 2));
		overlapFld.setColumns(4);
		overlapFld.addActionListener(new ActionListener()
		{		
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				deltaTimeFld.requestFocus();
				updateForm();
			}
		});		
		
		JLabel deltaTimeLbl = new JLabel("Frame time (millis)");
		deltaTimeLbl.setHorizontalTextPosition(SwingConstants.RIGHT);
		deltaTimeFld.setHorizontalAlignment(JTextField.RIGHT);
		deltaTimeFld.setMargin(new Insets(0, 2, 0, 2));
		deltaTimeFld.setColumns(4);
		deltaTimeFld.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				try
				{
					deltaTime = Integer.valueOf(deltaTimeFld.getText());
				}
				catch (NumberFormatException e)
				{
					deltaTime = 0;
				}
				updateForm();
				btnLoad.requestFocus();
			}
		});

		stepsFld.setText("50");
		zIncrFld.setText("0.01");
		overlapFld.setText("0");
		deltaTimeFld.setText("20");
		
		btnRunAnimation = new JButton("View");
		btnRunAnimation.setEnabled(false);
		btnRunAnimation.addActionListener(new ActionListener()
		{		
			@Override
			public void actionPerformed(ActionEvent e)
			{
				toggleAnimator();
			}
		});

		btnLoad = new JButton("Load");
		btnLoad.setEnabled(false);
		btnLoad.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				loadImageArray();
				btnRunAnimation.setEnabled(true);
				imgSlider.setEnabled(true);
				btnExport.setEnabled(true);
				btnLoad.setEnabled(false);
				viewImage(0);
				btnRunAnimation.requestFocus();
			}
		});

		imgSlider = new JSlider(0, 20, 0);
		imgSlider.setEnabled(false);
		imgSlider.addChangeListener(new ChangeListener()
		{		
			@Override
			public void stateChanged(ChangeEvent e)
			{
				viewImage(imgSlider.getValue());
			}
		});
			
		btnExport = new JButton("Export Animated GIF");
		btnExport.setEnabled(false);
		btnExport.addActionListener(new ActionListener()
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
					fileName = new File(fs.getSelectedFile().getAbsoluteFile()
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
		add(btnLoad, gbConstraints);
		gbConstraints.gridx = 1;
		gbConstraints.gridy = 4;
		add(btnRunAnimation, gbConstraints);	
		
		gbConstraints.gridx = 0;
		gbConstraints.gridy = 5;
		gbConstraints.gridwidth = 2;
		add(imgSlider, gbConstraints);
		
		gbConstraints.gridx = 0;
		gbConstraints.gridy = 6;
		add(btnExport, gbConstraints);	
	}

	// To be called when action on other forms changes the graphics data.
	public void requireReload()
	{
		// turn off animator if it is running
		if (animationRunning) toggleAnimator(); 

		// Changes to the graphics data do not require a model update
		// but they do require a recompile.
		graphicsHaveBeenCompiled = false;
		
		updateForm();
	}
	
	// Called whenever a field has been edited.
	private void updateForm()
	{
		updateModel();
		updateControls();
	}
	
	// Updates the model, but may allow bad data, so also need validate()
	private void updateModel()
	{
		try
		{
			imagesCount = Integer.valueOf(stepsFld.getText());
		}
		catch (NumberFormatException e)
		{
			imagesCount = 100; // default value
		}
		if (images.length != imagesCount)
		{
			images = new BufferedImage[imagesCount];
		}
		stepsFld.setText(String.valueOf(imagesCount));
		
		try
		{
			zIncr = Float.valueOf(zIncrFld.getText());
		}
		catch (NumberFormatException e)
		{
			zIncr = 0.01f;
		}
		
		try
		{
			overlap = Integer.valueOf(overlapFld.getText());
		}
		catch (NumberFormatException e)
		{
			overlap = 0;
		}
				
		try
		{
			deltaTime = Integer.valueOf(deltaTimeFld.getText());			
		}
		catch (NumberFormatException e)
		{
			deltaTime = 20;
		}

		// Assumes we've changed the model and now need a recompile
		graphicsHaveBeenCompiled = false;
		
		validateModel();
	}

	private void validateModel()
	{
		boolean modelIsOK = true;
		String errMessage = "";
		String errSpacer = "";
		
		if (imagesCount < 2)
		{
			errMessage = "Animation must have at least 2 frames.";
			errSpacer = " ";
			modelIsOK = false;
		}
		
		if (zIncr == 0)
		{
			errMessage = errMessage + errSpacer 
					+ "The function increment must be non-zero.";
			errSpacer = " ";
			modelIsOK = false;
		}
		
		if (deltaTime < 1)
		{
			errMessage = errMessage + errSpacer 
					+ "The time per frame must be a positive number of milliseconds.";
			errSpacer = " ";
			modelIsOK = false;
		}
		
		if (modelIsOK)
		{
			modelIsValid = true;
		}
		else
		{
			System.out.println(errMessage);
			// TODO - make a popup
			modelIsValid = false;
		}		
	}

	private void updateControls()
	{
		if (modelIsValid)
		{
			btnLoad.setEnabled(!graphicsHaveBeenCompiled);
			imgSlider.setEnabled(graphicsHaveBeenCompiled);
			btnRunAnimation.setEnabled(graphicsHaveBeenCompiled);
			btnExport.setEnabled(graphicsHaveBeenCompiled);
		}
		else
		{
			btnLoad.setEnabled(false);
			imgSlider.setEnabled(false);
			btnRunAnimation.setEnabled(false);
			btnExport.setEnabled(false);
		}
	}
	
	/*
	 * IMAGE LOADING
	 * This routine can take a long time to execute. In part,
	 * this is because we are using 3D calls instead of 2D as
	 * used in the rest of the program. Also, there are "double"
	 * calls per image in cases of overlap.
	 * TODO: create a "working/in progress" popup?
	 * TODO: This routine can fail due to over taxing memory. No
	 * exception or management has been implemented for this.
	 * TODO: Would it make sense to cache calls for overlap areas?
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
				
				float[] noiseArray = 
						FloatArrayFunctions.LerpTwoFloatArrays(
								nd.noiseArray, aFactor, 
								ndOverlap.noiseArray, bFactor);
				
				nd = new NoiseData(imgWidth, imgHeight, noiseArray);
			}
			
			z += zIncr;
			
			images[i] = TextureFunctions.makeImage(nd, mm, cm);
		}
		
		imgSlider.setMaximum(Math.max(0, imagesCount - 1));
		imgSlider.setValue(0);
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
			btnRunAnimation.setText("View");
			timer.cancel();
		}
		else
		{
			btnRunAnimation.setText("Stop");
			animationRunning = true;
			timerIdx = 0;
			timer = new Timer();
			timer.schedule(new AnimationTask(), 0, deltaTime);
		}
	}

	// intended use: called from container to use when it closes
	public void stopTimer()
	{
		if (timer != null) timer.cancel();
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
}