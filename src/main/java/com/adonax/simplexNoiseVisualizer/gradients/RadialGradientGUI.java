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

public class RadialGradientGUI extends JPanel
{
	private static final long serialVersionUID = 1L;

	private RadialGradientFunction model;
	private final JLabel equation;
	private final GradientGUI gradientGUI;
	
	RadialGradientGUI(RadialGradientFunction model, 
			GradientGUI gradientGUI)
	{
		this.model = model;
		this.gradientGUI = gradientGUI;
		TitledBorder combineTitledBorder = 
				BorderFactory.createTitledBorder("Edit radial gradient");
		setBorder(combineTitledBorder);

		Border blackBorder = BorderFactory.createLineBorder(Color.BLACK);
		Color background = new Color(192, 216, 255);
		
		setLayout(new GridBagLayout());
		GridBagConstraints gbConstraints = new GridBagConstraints();
		gbConstraints.anchor = GridBagConstraints.LINE_START;

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
		
		JLabel centerLabel = new JLabel(" Center location (in pixels), X: ");
		centerLabel.setBorder(blackBorder);
		centerLabel.setBackground(background);
		centerLabel.setOpaque(true);
		centerLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		gbConstraints.gridwidth = 1;		
		gbConstraints.gridx = 0;
		gbConstraints.gridy = 1;
		add(centerLabel, gbConstraints);

		final JTextField centerXLocation = new JTextField();
		centerXLocation.setBorder(blackBorder);
		centerXLocation.setHorizontalAlignment(SwingConstants.CENTER);
		centerXLocation.setText(String.valueOf(model.centerPoint.x));
		centerXLocation.addActionListener(new ActionListener()
		{	
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				setFunctionParam(RadialGradientFunction.Fields.CENTERX, 
						Integer.valueOf(centerXLocation.getText()));
				update();
			}
		});
		gbConstraints.gridx = 1;
		gbConstraints.gridy = 1;
		add(centerXLocation, gbConstraints);

		JLabel centerYLabel = new JLabel(" Y: ");
		centerYLabel.setBackground(background);
		centerYLabel.setBorder(blackBorder);
		centerYLabel.setOpaque(true);
		centerYLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		gbConstraints.gridx = 2;
		gbConstraints.gridy = 1;
		add(centerYLabel, gbConstraints);

		final JTextField centerYLocation = new JTextField(); 
		centerYLocation.setBorder(blackBorder);
		centerYLocation.setHorizontalAlignment(SwingConstants.CENTER);
		centerYLocation.setText(String.valueOf(model.centerPoint.y));
		centerYLocation.addActionListener(new ActionListener()
		{
			
			@Override
			public void actionPerformed(ActionEvent e)
			{
				setFunctionParam(
						RadialGradientFunction.Fields.CENTERY, 
						Integer.valueOf(centerYLocation.getText()));
				update();
			}
		});
		gbConstraints.gridx = 3;
		gbConstraints.gridy = 1;
		add(centerYLocation, gbConstraints);
		
		JLabel radiusLabel = new JLabel(" Radius (in pixels):");
		radiusLabel.setBorder(blackBorder);
		radiusLabel.setBackground(background);
		radiusLabel.setOpaque(true);
		radiusLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		gbConstraints.gridx = 0;
		gbConstraints.gridy = 2;
		add(radiusLabel, gbConstraints);

		final JTextField radiusValue = new JTextField();
		radiusValue.setBorder(blackBorder);
		radiusValue.setHorizontalAlignment(SwingConstants.CENTER);
		radiusValue.setText(String.valueOf(model.radius));
		radiusValue.addActionListener(new ActionListener()
		{
			
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				setFunctionParam(RadialGradientFunction.Fields.RADIUS, 
						Double.valueOf(radiusValue.getText()));
				update();
			}
		});
		gbConstraints.gridwidth = 1;
		gbConstraints.gridx = 1;
		gbConstraints.gridy = 2;
		add(radiusValue, gbConstraints);
		
		JLabel edgeValLabel = new JLabel(" Edge value:");
		edgeValLabel.setBorder(blackBorder);
		edgeValLabel.setBackground(background);
		edgeValLabel.setOpaque(true);
		edgeValLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		gbConstraints.gridx = 0;
		gbConstraints.gridy = 3;
		add(edgeValLabel, gbConstraints);		
		
		final JTextField edgeValue = new JTextField();
		edgeValue.setBorder(blackBorder);
		edgeValue.setHorizontalAlignment(SwingConstants.CENTER);
		edgeValue.setText(String.valueOf(model.edgeVal));
		edgeValue.addActionListener(new ActionListener()
		{
			
			@Override
			public void actionPerformed(ActionEvent e)
			{
				setFunctionParam(
						RadialGradientFunction.Fields.EDGEVAL, 
						Float.valueOf(edgeValue.getText()));
				update();
			}
		});
		gbConstraints.gridx = 1;
		gbConstraints.gridy = 3;
		add(edgeValue, gbConstraints);		
		
		JLabel centerValLabel = new JLabel(" Center value:");
		centerValLabel.setBorder(blackBorder);
		centerValLabel.setBackground(background);
		centerValLabel.setOpaque(true);
		centerValLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		gbConstraints.gridx = 2;
		gbConstraints.gridy = 3;
		add(centerValLabel, gbConstraints);		
		
		final JTextField centerValue = new JTextField();
		centerValue.setBorder(blackBorder);
		centerValue.setHorizontalAlignment(SwingConstants.CENTER);
		centerValue.setText(String.valueOf(model.centerVal));
		centerValue.addActionListener(new ActionListener()
		{
			
			@Override
			public void actionPerformed(ActionEvent e)
			{
				setFunctionParam(
						RadialGradientFunction.Fields.CENTERVAL, 
						Float.valueOf(centerValue.getText()));
				update();
			}
		});
		gbConstraints.gridx = 3;
		gbConstraints.gridy = 3;
		add(centerValue, gbConstraints);		
		
		equation = new JLabel(" ");
		equation.setBorder(blackBorder);
		equation.setBackground(new Color(216, 255, 216));
		equation.setOpaque(true);
		gbConstraints.gridwidth = 4;
		gbConstraints.gridx = 0;
		gbConstraints.gridy = 4;
		add(equation, gbConstraints);
		
		update();
	}
	
	public void setFunctionParam(
			RadialGradientFunction.Fields field, 
			Object value) 
	{
		int centerXLocation = model.centerPoint.x;
		int centerYLocation = model.centerPoint.y;
		double radius = model.radius;
		float edgeVal = model.edgeVal;
		float centerVal = model.centerVal;

		switch (field)
		{
		case CENTERX: centerXLocation = (Integer)value; break;
		case CENTERY: centerYLocation = (Integer)value; break;
		case RADIUS: radius = (Double)value; break;
		case EDGEVAL: edgeVal = (Float)value; break;
		case CENTERVAL: centerVal = (Float)value; break;
		}
		
		model =  new RadialGradientFunction(
				centerXLocation,
				centerYLocation,
				radius,
				edgeVal,
				centerVal
		);
	}
	
	private void updateEquationString()
	{
		 StringBuilder eq = new StringBuilder(" f(x, y) = ");
		 eq.append(model.edgeVal + " + ");
		 eq.append("((["+ model.centerPoint.x + ", ");
		 eq.append(model.centerPoint.y + "].distance(X, Y) ");
		 eq.append("/ " + model.radius + " ) * ");
		 eq.append(" (" + model.centerVal + " - " + model.edgeVal);
		 eq.append(")");
		 equation.setText(eq.toString());
		 repaint();
	}
	
	private void update()
	{
		updateEquationString();
		gradientGUI.setGradientFunction(
				GradientGUI.GradientMode.RADIAL, model);
	}
}
