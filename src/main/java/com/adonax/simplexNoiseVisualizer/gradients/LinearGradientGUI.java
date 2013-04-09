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

public class LinearGradientGUI extends JPanel
{
	private static final long serialVersionUID = 1L;

	private LinearGradientFunction model;
	private final JLabel equation;
	private final GradientGUI gradientGUI;
	
	LinearGradientGUI(LinearGradientFunction model, 
			GradientGUI gradientGUI)
	{
		this.model = model;
		this.gradientGUI = gradientGUI;
		
		TitledBorder combineTitledBorder = 
				BorderFactory.createTitledBorder("Edit linear gradient function");
		setBorder(combineTitledBorder);

		Border blackBorder = BorderFactory.createLineBorder(Color.BLACK);
		Color background = new Color(192, 216, 255);
		
		setLayout(new GridBagLayout());
		GridBagConstraints gbConstraints = new GridBagConstraints();
		gbConstraints.anchor = GridBagConstraints.LINE_START;

		JLabel xWidthLBL = new JLabel(" Horizontal width: " 
				+ MixerGUI.topPanel.getAppSettings().finalWidth + "    ");
		xWidthLBL.setBorder(blackBorder);
		xWidthLBL.setBackground(background);
		xWidthLBL.setOpaque(true);
		gbConstraints.gridwidth = 2;
		gbConstraints.gridx = 0;
		gbConstraints.gridy = 0;
		add(xWidthLBL, gbConstraints);
		gbConstraints.gridwidth = 1;

		JLabel xLeftLBL = new JLabel(" x Left:");
		xLeftLBL.setBorder(blackBorder);
		xLeftLBL.setBackground(background);
		xLeftLBL.setOpaque(true);
		xLeftLBL.setHorizontalAlignment(SwingConstants.RIGHT);
		gbConstraints.gridx = 0;
		gbConstraints.gridy = 1;
		gbConstraints.fill = GridBagConstraints.HORIZONTAL;
		add(xLeftLBL, gbConstraints);
		
		final JTextField xLeft = new JTextField(6);
		xLeft.setBorder(blackBorder);
		xLeft.setHorizontalAlignment(SwingConstants.CENTER);
		xLeft.setText(String.valueOf(model.xLeft));
		xLeft.addActionListener(new ActionListener()
		{	
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				setFunctionParam(LinearGradientFunction.Fields.XLEFT, 
						Float.valueOf(xLeft.getText()));
				update();
			}
		});
		gbConstraints.gridx = 1;
		gbConstraints.gridy = 1;
		add(xLeft, gbConstraints);
		
		JLabel xRightLBL = new JLabel(" x Right:");
		xRightLBL.setBorder(blackBorder);
		xRightLBL.setBackground(background);
		xRightLBL.setOpaque(true);
		xRightLBL.setHorizontalAlignment(SwingConstants.RIGHT);
		gbConstraints.gridx = 0;
		gbConstraints.gridy = 2;
		add(xRightLBL, gbConstraints);

		final JTextField xRight = new JTextField(6);
		xRight.setBorder(blackBorder);
		xRight.setHorizontalAlignment(SwingConstants.CENTER);
		xRight.setText(String.valueOf(model.xRight));
		xRight.addActionListener(new ActionListener()
		{
			
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				setFunctionParam(LinearGradientFunction.Fields.XRIGHT, 
						Float.valueOf(xRight.getText()));
				update();
			}
		});
		gbConstraints.gridx = 1;
		gbConstraints.gridy = 2;
		add(xRight, gbConstraints);

		JLabel yHeightLBL = new JLabel(" Vertical height: " 
				+ MixerGUI.topPanel.getAppSettings().finalHeight + " ");
		yHeightLBL.setBorder(blackBorder);
		yHeightLBL.setBackground(background);
		yHeightLBL.setOpaque(true);
		gbConstraints.gridwidth = 2;
		gbConstraints.gridx = 3;
		gbConstraints.gridy = 0;
		add(yHeightLBL, gbConstraints);
		gbConstraints.gridwidth = 1;
		
		JLabel yTopLBL = new JLabel(" y Top:");
		yTopLBL.setBackground(background);
		yTopLBL.setBorder(blackBorder);
		yTopLBL.setOpaque(true);
		yTopLBL.setHorizontalAlignment(SwingConstants.RIGHT);
		gbConstraints.gridx = 3;
		gbConstraints.gridy = 1;
		add(yTopLBL, gbConstraints);

		final JTextField yTop = new JTextField(6);
		yTop.setBorder(blackBorder);
		yTop.setHorizontalAlignment(SwingConstants.CENTER);
		yTop.setText(String.valueOf(model.yTop));
		yTop.addActionListener(new ActionListener()
		{
			
			@Override
			public void actionPerformed(ActionEvent e)
			{
				setFunctionParam(
						LinearGradientFunction.Fields.YTOP, 
						Float.valueOf(yTop.getText()));
				update();
			}
		});
		gbConstraints.gridx = 4;
		gbConstraints.gridy = 1;
		add(yTop, gbConstraints);
	
		JLabel yBottomLBL = new JLabel(" y Bottom:");
		yBottomLBL.setBorder(blackBorder);
		yBottomLBL.setBackground(background);
		yBottomLBL.setOpaque(true);
		yBottomLBL.setHorizontalAlignment(SwingConstants.RIGHT);
		gbConstraints.gridx = 3;
		gbConstraints.gridy = 2;
		add(yBottomLBL, gbConstraints);		
		
		final JTextField yBottom = new JTextField(6);
		yBottom.setBorder(blackBorder);
		yBottom.setHorizontalAlignment(SwingConstants.CENTER);
		yBottom.setText(String.valueOf(model.yBottom));
		yBottom.addActionListener(new ActionListener()
		{
			
			@Override
			public void actionPerformed(ActionEvent e)
			{
				setFunctionParam(
						LinearGradientFunction.Fields.YBOTTOM, 
						Float.valueOf(yBottom.getText()));
				update();
			}
		});
		gbConstraints.gridx = 4;
		gbConstraints.gridy = 2;
		add(yBottom, gbConstraints);		
		
		equation = new JLabel(" ");
		equation.setBorder(blackBorder);
		equation.setBackground(new Color(216, 255, 216));
		equation.setOpaque(true);
		gbConstraints.gridwidth = 5;
		gbConstraints.gridx = 0;
		gbConstraints.gridy = 3;
		add(equation, gbConstraints);
		gbConstraints.gridwidth = 1;
		
		update();
	}
	
	public void setFunctionParam(
			LinearGradientFunction.Fields field, 
			Object value) 
	{
		int functionWidth = model.functionWidth;
		int functionHeight = model.functionHeight;
		float xLeft = model.xLeft;
		float xRight = model.xRight;
		float yTop = model.yTop;
		float yBottom = model.yBottom;

		switch (field)
		{
		case FUNCTIONWIDTH: functionWidth = (Integer)value; break;
		case FUNCTIONHEIGHT: functionHeight = (Integer)value; break;
		case XLEFT: xLeft = (Float)value; break;
		case XRIGHT: xRight = (Float)value; break;
		case YTOP: yTop = (Float)value; break;
		case YBOTTOM: yBottom = (Float)value; break;
		}
		
		model =  new LinearGradientFunction(
				functionWidth,
				functionHeight,
				xLeft,
				xRight,
				yTop,
				yBottom
		);
	}
	
	private void updateEquationString()
	{
		 StringBuilder eq = new StringBuilder(" f(x, y) = ");
		 eq.append("( " + model.xLeft);
		 eq.append(" + ((" + model.xRight + " - " + model.xLeft + ")");
		 eq.append(" / " + model.functionWidth + ") * x )");
		 eq.append(" + ( " + model.yTop);
		 eq.append(" + ((" + model.yBottom + " - " + model.yTop + ")");
		 eq.append(" / " + model.functionHeight + ") * y )");
		 equation.setText(eq.toString());
		 repaint();
	}
	
	private void update()
	{
		updateEquationString();
		gradientGUI.setGradientFunction(
				GradientGUI.GradientMode.LINEAR, model);
	}
}
