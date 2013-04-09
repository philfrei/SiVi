package com.adonax.simplexNoiseVisualizer.gradients;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import com.adonax.simplexNoiseVisualizer.MixerGUI;

public class SinusoidalGradientGUI extends JPanel
{
	private static final long serialVersionUID = 1L;

	private SinusoidalGradientFunction model;
	private final JLabel equation;
	private final GradientGUI gradientGUI;
	
	SinusoidalGradientGUI(SinusoidalGradientFunction model, 
			GradientGUI gradientGUI)
	{
		this.model = model;
		this.gradientGUI = gradientGUI;
		
		TitledBorder combineTitledBorder = 
				BorderFactory.createTitledBorder("Edit sinusoidal gradient");
		setBorder(combineTitledBorder);

		Border blackBorder = BorderFactory.createLineBorder(Color.BLACK);
		Color background = new Color(192, 216, 255);
		
		setLayout(new GridBagLayout());
		GridBagConstraints gbConstraints = new GridBagConstraints();
		gbConstraints.anchor = GridBagConstraints.LINE_START;

		// FIRST ROW
		
		JLabel xWidthLBL = new JLabel(" Horizontal width: " 
				+ MixerGUI.topPanel.getAppSettings().finalWidth + "    " 
				+ " Vertical height: " 
				+ MixerGUI.topPanel.getAppSettings().finalHeight + " ");
		xWidthLBL.setBorder(blackBorder);
		xWidthLBL.setBackground(background);
		xWidthLBL.setOpaque(true);
		gbConstraints.fill = GridBagConstraints.HORIZONTAL; 
		gbConstraints.gridwidth = 4;
		gbConstraints.gridx = 0;
		gbConstraints.gridy = 0;
		add(xWidthLBL, gbConstraints);
		gbConstraints.gridwidth = 1;		
		
		// SECOND ROW
		
		JLabel originXLabel = new JLabel(" Origin X: ");
		originXLabel.setBorder(blackBorder);
		originXLabel.setBackground(background);
		originXLabel.setOpaque(true);
		originXLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		gbConstraints.gridx = 0;
		gbConstraints.gridy = 1;
		add(originXLabel, gbConstraints);

		final JTextField originXValue = new JTextField(6);
		originXValue.setBorder(blackBorder);
		originXValue.setHorizontalAlignment(SwingConstants.CENTER);
		originXValue.setText(String.valueOf(model.originX));
		originXValue.addActionListener(new ActionListener()
		{	
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				setFunctionParam(SinusoidalGradientFunction.Fields.ORIGINX, 
						Integer.valueOf(originXValue.getText()));
				update();
			}
		});
		gbConstraints.gridx = 1;
		gbConstraints.gridy = 1;
		add(originXValue, gbConstraints);	
		
		
		JLabel originYLabel = new JLabel(" Origin Y: ");
		originYLabel.setBorder(blackBorder);
		originYLabel.setBackground(background);
		originYLabel.setOpaque(true);
		originYLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		gbConstraints.gridx = 2;
		gbConstraints.gridy = 1;
		add(originYLabel, gbConstraints);

		final JTextField originYValue = new JTextField(6);
		originYValue.setBorder(blackBorder);
		originYValue.setHorizontalAlignment(SwingConstants.CENTER);
		originYValue.setText(String.valueOf(model.originY));
		originYValue.addActionListener(new ActionListener()
		{	
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				setFunctionParam(SinusoidalGradientFunction.Fields.ORIGINY, 
						Integer.valueOf(originYValue.getText()));
				update();
			}
		});
		gbConstraints.gridx = 3;
		gbConstraints.gridy = 1;
		add(originYValue, gbConstraints);

		// THIRD ROW
		
		JLabel periodLabel = new JLabel(" Period(in pixels): ");
		periodLabel.setBackground(background);
		periodLabel.setBorder(blackBorder);
		periodLabel.setOpaque(true);
		periodLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		gbConstraints.gridx = 0;
		gbConstraints.gridy = 2;
		add(periodLabel, gbConstraints);

		final JTextField periodValue = new JTextField(6);
		periodValue.setBorder(blackBorder);
		periodValue.setHorizontalAlignment(SwingConstants.CENTER);
		periodValue.setText(String.valueOf(model.period));
		periodValue.addActionListener(new ActionListener()
		{
			
			@Override
			public void actionPerformed(ActionEvent e)
			{
				setFunctionParam(
						SinusoidalGradientFunction.Fields.PERIOD, 
						Float.valueOf(periodValue.getText()));
				update();
			}
		});
		gbConstraints.gridx = 1;
		gbConstraints.gridy = 2;
		add(periodValue, gbConstraints);
		
		JLabel thetaLabel = new JLabel(" Angle (in radians):");
		thetaLabel.setBorder(blackBorder);
		thetaLabel.setBackground(background);
		thetaLabel.setOpaque(true);
		thetaLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		gbConstraints.gridx = 2;
		gbConstraints.gridy = 2;
		add(thetaLabel, gbConstraints);

		final JTextField radianValue = new JTextField(6);
		radianValue.setBorder(blackBorder);
		radianValue.setHorizontalAlignment(SwingConstants.CENTER);
		radianValue.setText(String.valueOf(model.theta));
		radianValue.addActionListener(new ActionListener()
		{
			
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				setFunctionParam(SinusoidalGradientFunction.Fields.THETA, 
						Float.valueOf(radianValue.getText()));
				update();
			}
		});
		gbConstraints.gridwidth = 1;
		gbConstraints.gridx = 3;
		gbConstraints.gridy = 2;
		add(radianValue, gbConstraints);
		gbConstraints.gridwidth = 1;
		radianValue.setEnabled(false);

		// FOURTH ROW
		
		JLabel highValLabel = new JLabel(" High value:");
		highValLabel.setBorder(blackBorder);
		highValLabel.setBackground(background);
		highValLabel.setOpaque(true);
		highValLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		gbConstraints.gridx = 0;
		gbConstraints.gridy = 3;
		add(highValLabel, gbConstraints);		
		
		final JTextField highValue = new JTextField(6);
		highValue.setBorder(blackBorder);
		highValue.setHorizontalAlignment(SwingConstants.CENTER);
		highValue.setText(String.valueOf(model.highVal));
		highValue.addActionListener(new ActionListener()
		{
			
			@Override
			public void actionPerformed(ActionEvent e)
			{
				setFunctionParam(
						SinusoidalGradientFunction.Fields.HIGHVAL, 
						Float.valueOf(highValue.getText()));
				update();
			}
		});
		gbConstraints.gridx = 1;
		gbConstraints.gridy = 3;
		add(highValue, gbConstraints);		
		
		JLabel lowValLabel = new JLabel(" Low value:");
		lowValLabel.setBorder(blackBorder);
		lowValLabel.setBackground(background);
		lowValLabel.setOpaque(true);
		lowValLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		gbConstraints.gridx = 2;
		gbConstraints.gridy = 3;
		add(lowValLabel, gbConstraints);		
		
		final JTextField lowValue = new JTextField(6);
		lowValue.setBorder(blackBorder);
		lowValue.setHorizontalAlignment(SwingConstants.CENTER);
		lowValue.setText(String.valueOf(model.lowVal));
		lowValue.addActionListener(new ActionListener()
		{
			
			@Override
			public void actionPerformed(ActionEvent e)
			{
				setFunctionParam(
						SinusoidalGradientFunction.Fields.LOWVAL, 
						Float.valueOf(lowValue.getText()));
				update();
			}
		});
		gbConstraints.gridx = 3;
		gbConstraints.gridy = 3;
		add(lowValue, gbConstraints);		
		
		// FIFTH ROW
		
		equation = new JLabel(" ");
		equation.setBorder(blackBorder);
		equation.setBackground(new Color(216, 255, 216));
		equation.setOpaque(true);
		gbConstraints.gridwidth = 4;
		gbConstraints.gridx = 0;
		gbConstraints.gridy = 4;
		add(equation, gbConstraints);
		gbConstraints.gridwidth = 1;
		
		update();
	}
	
	public void setFunctionParam(
			SinusoidalGradientFunction.Fields field, 
			Object value) 
	{
		
		int originX = model.originX;
		int originY = model.originY;
		float period = model.period;
		float theta = model.theta;
		float highVal = model.highVal;
		float lowVal = model.lowVal;

		switch (field)
		{
		case ORIGINX: originX = (Integer)value; break;
		case ORIGINY: originY = (Integer)value; break;
		case PERIOD: period = (Float)value; break;
		case THETA: theta = (Float)value; break;
		case HIGHVAL: highVal = (Float)value; break;
		case LOWVAL: lowVal = (Float)value; break;
		}
		
		model =  new SinusoidalGradientFunction(
				originX, 
				originY,
				period,
				theta,
				highVal,
				lowVal
		);
	}
	
	private void updateEquationString()
	{
		 StringBuilder eq = new StringBuilder(" f(x, y) = ");
//		 eq.append(model.edgeVal + " + ");
//		 eq.append("((["+ model.centerPoint.x + ", ");
//		 eq.append(model.centerPoint.y + "].distance(X, Y) ");
//		 eq.append("/ " + model.radius + " ) * ");
//		 eq.append(" (" + model.centerVal + " - " + model.edgeVal);
//		 eq.append(")");
		 equation.setText(eq.toString());
		 repaint();
	}
	
	private void update()
	{
		updateEquationString();
		gradientGUI.setGradientFunction(
				GradientGUI.GradientMode.SINUSOIDAL, model);
	}
}
