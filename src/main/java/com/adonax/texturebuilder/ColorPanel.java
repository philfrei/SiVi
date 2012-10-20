package com.adonax.texturebuilder;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JColorChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class ColorPanel extends JPanel implements ActionListener,
	MouseListener, MouseMotionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	int width, height;
	
	Rectangle[] colorPad;
	JTextField[] locationVal, redVal, greenVal, blueVal;
	JButton[] delButton;
	JLabel barPixel;
	JButton flip, negative;

	boolean useHsbLerp;
	
	ArrayList<ColorBarPeg> colorBarPegs;
	ColorAxis colorAxis;
	
	public void setColorAxis(ColorAxis sourceColorAxis)
	{
		colorAxis.data = sourceColorAxis.data;
		colorAxis.img = sourceColorAxis.img;
		colorAxis.colorBarPegs = sourceColorAxis.colorBarPegs;
		this.colorBarPegs = colorAxis.colorBarPegs;
	}
	
	STBPanel host;
	
	final int BAR_HEIGHT = 16;
	final int ROWS = 8;
	
	public ColorPanel(int width, int height, 
			ColorAxis colorAxis, STBPanel host) 
	{
		setLayout(null);
		this.host = host;
		this.width = width;
		this.height = height;
		this.colorAxis = colorAxis;
		
		barPixel = new JLabel("0");
		barPixel.setHorizontalAlignment(SwingConstants.CENTER);
		barPixel.setBounds(0, 0, 36, 24);
		barPixel.setVisible(false);
		barPixel.setOpaque(true);
		add(barPixel);

		flip = new JButton("reverse");
		flip.setBounds(6, 32, 88, 24);
		flip.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) 
			{
				for (ColorBarPeg rd : colorBarPegs)
				{
					rd.setLocation(255 - rd.getLocation());
				}
				sortAndDisplay();
				update();
			}});
		add(flip);
		
		negative = new JButton("negative");
		negative.setBounds(110, 32, 88, 24);
		negative.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				for (ColorBarPeg rd : colorBarPegs)
				{
					rd.setrColor(255 - rd.getrColor());
					rd.setgColor(255 - rd.getgColor());
					rd.setbColor(255 - rd.getbColor());
				}
				sortAndDisplay();
				update();
			}
		});
		add(negative);
	
		final JCheckBox hsbCheckBox = new JCheckBox("HSB lerp");
		hsbCheckBox.setBounds(206, 32, 100, 24);
		
		hsbCheckBox.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				useHsbLerp = hsbCheckBox.getModel().isSelected();
				sortAndDisplay();
				update();				
			}});
		add(hsbCheckBox);
		
		
		colorPad = new Rectangle[ROWS];
		locationVal = new JTextField[ROWS];
		redVal = new JTextField[ROWS];
		greenVal = new JTextField[ROWS];
		blueVal = new JTextField[ROWS];
		delButton = new JButton[ROWS];
		
		colorBarPegs = colorAxis.colorBarPegs;
		
		for (int i = 0; i < 8; i++)
		{
			int yloc = (i * 32) + 64;
			locationVal[i] = new JTextField();
			locationVal[i].setActionCommand("l:" + i);
			locationVal[i].addActionListener(this);
			locationVal[i].setBounds( 4, yloc, 32, 24);
			add(locationVal[i]);
			
			colorPad[i] = new Rectangle( 42, yloc, 64, 22);
			
			redVal[i] = new JTextField();
			redVal[i].setActionCommand("r:" + i);
			redVal[i].addActionListener(this);
			redVal[i].setBounds( 114, yloc, 32, 24);
			add(redVal[i]);
			
			greenVal[i] = new JTextField();
			greenVal[i].setActionCommand("g:" + i);
			greenVal[i].addActionListener(this);
			greenVal[i].setBounds( 148, yloc, 32, 24);
			add(greenVal[i]);
			
			blueVal[i] = new JTextField();
			blueVal[i].setActionCommand("b:" + i);
			blueVal[i].addActionListener(this);
			blueVal[i].setBounds( 182, yloc, 32, 24);
			add(blueVal[i]);
			
			delButton[i] = new JButton("del");
			delButton[i].setActionCommand("d:" + i);
			delButton[i].addActionListener(this);
			delButton[i].setBounds( 224, yloc, 72, 24);
			add(delButton[i]);
		}
		
		// Spec is derived from the data????

		sortAndDisplay();	
		
		addMouseListener(this);
		addMouseMotionListener(this);
	}

	public void sortAndDisplay()
	{
		Collections.sort(colorBarPegs, new ColorBarPegSort());
		
		for (int index = 0; index < ROWS; index++)
		{
			if (index < colorBarPegs.size())
			{
				locationVal[index].setText(
						String.valueOf(
								colorBarPegs.get(index).getLocation()));
				locationVal[index].setVisible(true);
				redVal[index].setText(
						String.valueOf(colorBarPegs.get(index).getrColor()));
				redVal[index].setVisible(true);
				greenVal[index].setText(
						String.valueOf(colorBarPegs.get(index).getgColor()));
				greenVal[index].setVisible(true);
				blueVal[index].setText(
						String.valueOf(colorBarPegs.get(index).getbColor()));
				blueVal[index].setVisible(true);
				if (colorBarPegs.get(index).getLocation() == 0 ||
						colorBarPegs.get(index).getLocation() == 255)
				{
					delButton[index].setVisible(false);
				}
				else
				{
					delButton[index].setVisible(true);
				}	
			}
			else
			{
				locationVal[index].setVisible(false);
				redVal[index].setVisible(false);
				greenVal[index].setVisible(false);
				blueVal[index].setVisible(false);
				delButton[index].setVisible(false);
			}
		}
		repaint();
	}
	
	private void update()
	{
		colorAxis.useHsbLerp = this.useHsbLerp;
		
		colorAxis.update();
		host.update();
		
		
	}
	
	private int clickInColorPad(Point clickPoint)
	{
		for (int i = 0, n = colorBarPegs.size(); i < n; i++)
		{
			if (colorPad[i].contains(clickPoint))
			{
				return i;
			}
		}
		return -1;
	}

	private boolean inTopColorBar(Point clickPoint)
	{
		return clickPoint.getY() >= 4 
				&& clickPoint.getY() < 4 + BAR_HEIGHT
				&& clickPoint.getX() >= 16 
				&& clickPoint.getX() < (16 + 256);
	}
	
	@Override
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		Point p = arg0.getPoint();
		if (inTopColorBar(p))
		{
			barPixel.setText(String.valueOf(arg0.getX() - 16));
			barPixel.setBounds(arg0.getX(), arg0.getY() + 18, 
					24, 18);
			barPixel.setVisible(true);
		}
		else
		{
			barPixel.setVisible(false);
		}
		repaint();
	}
	
	@Override
	public void mouseClicked(MouseEvent arg0) 
	{
		// are we in a color rectangle? 
		int cpIdx = clickInColorPad(arg0.getPoint());
		if (cpIdx > -1)
		{
			Color pickedColor = new Color(colorAxis.data[cpIdx][0], 
					colorAxis.data[cpIdx][1], colorAxis.data[cpIdx][2]);
			Color newColor = JColorChooser.showDialog(this, 
					"Pick a color", pickedColor);
			if (newColor == null) 
			{
				return;
			}
			else
			{
				colorBarPegs.get(cpIdx).setrColor(newColor.getRed());
				colorBarPegs.get(cpIdx).setgColor(newColor.getGreen());
				colorBarPegs.get(cpIdx).setbColor(newColor.getBlue());
				sortAndDisplay();
				update();
			}
		}
		
		// in top bar?
		if (colorBarPegs.size() >= 8) return;
		
		if (inTopColorBar(arg0.getPoint()))
		{
			int idx = arg0.getX() - 16;
			Color pickedColor = new Color(colorAxis.data[idx][0], 
					colorAxis.data[idx][1], colorAxis.data[idx][2]);
			Color newColor = JColorChooser.showDialog(this, 
					"Pick a color", pickedColor);
			if (newColor == null) return;
			
			if (colorBarPegs.size() < 8)
			{
				colorBarPegs.add(new ColorBarPeg(idx, newColor.getRed(),
						newColor.getGreen(), newColor.getBlue(), 255));
				sortAndDisplay();
				update();
			}
		}
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
	}
	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
	}
	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
	}
	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void actionPerformed(ActionEvent arg0) 
	{
		
		String[] cmd = arg0.getActionCommand().split(":");
		
		// delButtons pack index as ActionCommand
		if (cmd[0].equals("d"))
		{
			int idx = Integer.valueOf(cmd[1]);
			colorBarPegs.remove(idx);
		}
		
		if (cmd[0].equals("l"))
		{
			int idx = Integer.valueOf(cmd[1]);
			colorBarPegs.get(idx).setLocation(
					Integer.valueOf(locationVal[idx].getText()));
		}

		if (cmd[0].equals("r"))
		{
			int idx = Integer.valueOf(cmd[1]);
			colorBarPegs.get(idx).setrColor(
					Integer.valueOf(redVal[idx].getText()));
		}

		if (cmd[0].equals("g"))
		{
			int idx = Integer.valueOf(cmd[1]);
			colorBarPegs.get(idx).setgColor(
					Integer.valueOf(greenVal[idx].getText()));
		}

		if (cmd[0].equals("b"))
		{
			int idx = Integer.valueOf(cmd[1]);
			colorBarPegs.get(idx).setbColor(
					Integer.valueOf(blueVal[idx].getText()));
		}

		
		sortAndDisplay();
		update();
		
	}
	
	
	public void paintComponent(Graphics g)
	{
		Graphics2D g2 = (Graphics2D)g;
		
		g2.setPaint(Color.WHITE);
		g2.fillRect(0, 0, width, height);
		
		g2.drawImage(colorAxis.img, 16, 4, null);
		for (int i = 0; i < ROWS; i++)
		{
			if (i < colorBarPegs.size())
			{
				g2.setColor(new Color(colorBarPegs.get(i).getrColor(),	
						colorBarPegs.get(i).getgColor(), 
						colorBarPegs.get(i).getbColor()));
				g2.fill(colorPad[i]);
				g2.setColor(Color.BLACK);
				g2.draw(colorPad[i]);
			}
		}
	}

}

