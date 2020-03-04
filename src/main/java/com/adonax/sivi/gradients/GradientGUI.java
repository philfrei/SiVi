package com.adonax.sivi.gradients;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

import com.adonax.sivi.MixerGUI;
import com.adonax.sivi.MixerModel;
import com.adonax.sivi.NoiseData;

public class GradientGUI extends JPanel implements ActionListener
{
	private static final long serialVersionUID = 1L;

	private GradientGUIModel model;
	public GradientGUIModel getModel() { return model; }

	public static enum GradientMode {
		LINEAR,
		RADIAL,
		SINUSOIDAL
	}
	
	private JCheckBox[] gfCheckBoxes;
	private Border blackBorder, redBorder;
	private MixerGUI mixerGUI;
	
	public static ArrayList<JDialog> openWindows;
	
	private GradientGUI meThis = this;
	
	public GradientGUI(MixerGUI mixerGUI)
	{		
		this(mixerGUI, new GradientGUIModel());
	}
	
	public GradientGUI(MixerGUI mixerGUI, GradientGUIModel model)
	{
		this.mixerGUI = mixerGUI;
		this.model = model;
		openWindows = new ArrayList<JDialog>();
		
		blackBorder = BorderFactory.createLineBorder(Color.BLACK);
		redBorder = BorderFactory.createLineBorder(Color.RED);
		
		// TODO: new gradientGUIs are added in this area, AND 
		// in the actionListener, below
		
		setLayout(new GridBagLayout());
		GridBagConstraints gbConstraints = new GridBagConstraints();
		gbConstraints.anchor = GridBagConstraints.LINE_START;
		
		gfCheckBoxes = new JCheckBox[GradientMode.values().length];
		
		gfCheckBoxes[0] = new JCheckBox();
		gfCheckBoxes[0].setActionCommand("linear");
		gfCheckBoxes[0].setSelected(model.selected[0]);
		gfCheckBoxes[0].addActionListener(this);

		gbConstraints.gridx = 0;
		gbConstraints.gridy = 0;
		add(gfCheckBoxes[0], gbConstraints);

		final JLabel linearLabel = new JLabel(" Linear ");
		linearLabel.setName("l");
		makeIntoGradientFunctionLabel(linearLabel);
		
		gbConstraints.gridx = 1;
		gbConstraints.gridy = 0;
		add(linearLabel, gbConstraints);
		gfCheckBoxes[1] = new JCheckBox();
		gfCheckBoxes[1].setActionCommand("radial");
		gfCheckBoxes[1].setSelected(model.selected[1]);
		gfCheckBoxes[1].addActionListener(this);
		
		gbConstraints.gridx = 0;
		gbConstraints.gridy = 1;
		add(gfCheckBoxes[1], gbConstraints);
			
		final JLabel radialLabel = new JLabel(" Radial ");
		radialLabel.setName("r");
		makeIntoGradientFunctionLabel(radialLabel);

		gbConstraints.gridx = 1;
		gbConstraints.gridy = 1;
		add(radialLabel, gbConstraints);

		gfCheckBoxes[2] = new JCheckBox();
		gfCheckBoxes[2].setActionCommand("sinusoidal");
		gfCheckBoxes[2].setSelected(model.selected[2]);
		gfCheckBoxes[2].addActionListener(this);
		
		gbConstraints.gridx = 0;
		gbConstraints.gridy = 2;
		add(gfCheckBoxes[2], gbConstraints);
			
		final JLabel sinusoidalLabel = new JLabel(" Sinusoidal ");
		sinusoidalLabel.setName("s");
		makeIntoGradientFunctionLabel(sinusoidalLabel);

		gbConstraints.gridx = 1;
		gbConstraints.gridy = 2;
		add(sinusoidalLabel, gbConstraints);
	}

	private JLabel makeIntoGradientFunctionLabel(final JLabel lbl)
	{
		lbl.setBackground(new Color(216, 216, 255));
		lbl.setPreferredSize(new Dimension(100,16));
		lbl.setHorizontalAlignment(SwingConstants.CENTER);
		lbl.setOpaque(true);
		lbl.setBorder(blackBorder);
		lbl.addMouseListener(new EditListener());
		
        String html =
                "<html><p><font color=\"#000000\" " +
                "size=\"2\" face=\"SanSerif\">CLICK TO EDIT" +
                "</font></p></html>";
        lbl.setToolTipText(html);
		return lbl;
	}
	
	public void setGradientFunction(GradientMode mode,
			GradientFunction gradientFunction)
	{
		switch (mode)
		{
		case LINEAR: 
			model = GradientGUIModel.updateGradientGUIModel(
					model, GradientGUIModel.Fields.LINEAR, gradientFunction);
			break;
			
		case RADIAL: 
			model = GradientGUIModel.updateGradientGUIModel(
					model, GradientGUIModel.Fields.RADIAL,
					gradientFunction);
			break;
			
		case SINUSOIDAL: 
			model = GradientGUIModel.updateGradientGUIModel(
					model, GradientGUIModel.Fields.SINUSOIDAL, 
					gradientFunction);
			break;
			
		default:
			assert false: mode;
		}
		
		update();
	}
	
	static public NoiseData createGradientFunctionData(
			int gradientWidth, int gradientHeight, GradientGUIModel ggm)
	{
		float[] noiseArray = new float[gradientWidth * gradientHeight];
		int idx = 0;
		for (int k = 0; k < gradientHeight; k++)
		{
			for (int j = 0; j < gradientWidth; j++)
			{
				for (int i = 0, n = ggm.gradients.length; i < n; i++)
				{
					if (ggm.selected[i])
					{
						noiseArray[idx] += ggm.gradients[i].get(j, k);
					}
				}
				idx++;
			}
		}
		
		return new NoiseData(gradientWidth, gradientHeight, 
				noiseArray);
	}		
	
	@Override
	public void actionPerformed(ActionEvent arg0)
	{
		boolean selected[] = new boolean[GradientMode.values().length];
		for (int i = 0; i < GradientMode.values().length; i++)
		{
			selected[i] = gfCheckBoxes[i].isSelected();
		}
		model = GradientGUIModel.updateGradientGUIModel(
					model, GradientGUIModel.Fields.SELECTED, selected);
		
		update();
	}
	
	private void update()
	{
		NoiseData noiseData = 
				GradientGUI.createGradientFunctionData(
					MixerGUI.topPanel.getAppSettings().finalWidth,
					MixerGUI.topPanel.getAppSettings().finalHeight,
					model);
		
		mixerGUI.setMixerModel(MixerModel.updateMixSetting(
				mixerGUI.getMixerModel(), 
				MixerModel.Fields.GRADIENT_DATA, 
				noiseData));
		
		MixerGUI.topPanel.remix();
	}
	
	public static void closeAllGradientWindows()
	{
		for (JDialog jd : openWindows)
		{
			jd.dispose();
		}
		openWindows.clear();
	}
	
	private static boolean isThisTypeOpen(String type)
	{
		for (JDialog jd : openWindows)
		{
			if (jd.getName().equals(type))
			{
				return true;
			}
		}
		return false;
	}
	
	
	class EditListener implements MouseListener
	{
		@Override
		public void mouseClicked(MouseEvent arg0)
		{
//			if (arg0.getClickCount() == 2)
//			{			
				JPanel jp = new JPanel();
				if (arg0.getComponent().getName().equals("l"))
				{
					if (GradientGUI.isThisTypeOpen("l")) return;
					
					jp = new LinearGradientGUI(
							(LinearGradientFunction)
							model.gradients[0],
							meThis);
				}
				if (arg0.getComponent().getName().equals("r"))
				{
					if (GradientGUI.isThisTypeOpen("r")) return;
					
					jp = new RadialGradientGUI(
							(RadialGradientFunction)
							model.gradients[1],
							meThis);
				}
				if (arg0.getComponent().getName().equals("s"))
				{
					if (GradientGUI.isThisTypeOpen("s")) return;
					
					jp = new SinusoidalGradientGUI(
							(SinusoidalGradientFunction)
							model.gradients[2],
							meThis);
				}
				
				JDialog gf = new JDialog(){
					private static final long serialVersionUID = 1L;
				};
				gf.add(jp);
				gf.setName(arg0.getComponent().getName());
				gf.setTitle("Edit gradient");
				gf.setBounds(0, 0, 500, 200);
//				gf.setAlwaysOnTop(true);
				gf.setModal(true);
				gf.setVisible(true);
						
//				openWindows.add(gf);
				
//			}	
		}
				
		@Override
		public void mouseEntered(MouseEvent arg0)
		{
			JLabel lbl = (JLabel)arg0.getComponent();
			lbl.setBorder(redBorder);
			repaint();
		}
		
		@Override
		public void mouseExited(MouseEvent arg0)
		{
			JLabel lbl = (JLabel)arg0.getComponent();
			lbl.setBorder(blackBorder);
			repaint();
		}

		@Override
		public void mousePressed(MouseEvent arg0){}

		@Override
		public void mouseReleased(MouseEvent arg0){}
		
	}
}
