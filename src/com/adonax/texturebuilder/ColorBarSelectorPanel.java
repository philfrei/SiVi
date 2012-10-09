package com.adonax.texturebuilder;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JPanel;

public class ColorBarSelectorPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ColorAxis[] colorAxisSet;
	private int width = 264;
	private int height = 226;
	private int hover;
	private int bars;
	private SimplexTextureSource sts;
	private ColorBarSelectorDialog dialogHost;
	
	ColorBarSelectorPanel(final ColorAxis[] colorAxisSet, 
			ColorBarSelectorDialog colorBarSelectorDialog)
	{
		this.colorAxisSet = colorAxisSet;
		bars = colorAxisSet.length;
		
		this.dialogHost = colorBarSelectorDialog;
		hover = -1; // nothing selected, default
		
		addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
			}
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
			}
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub	
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub	
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				if (hover > -1)
				{
					// then update sts.setColorAxis()
					sts.colorAxis = colorAxisSet[hover];
					sts.update();
					dialogHost.setVisible(false);
				}
			}
		});
		
		addMouseMotionListener(new MouseMotionListener() {
			
			@Override
			public void mouseMoved(MouseEvent arg0) {
				int newHover = getHover(arg0.getY());
				if (newHover != hover)
				{
					hover = newHover;
					repaint();
				}
			}
			
			@Override
			public void mouseDragged(MouseEvent arg0) {
				// TODO Auto-generated method stub
			}
		});
	}
	
	public void setCallback(SimplexTextureSource sts) {
		this.sts = sts;
	}

	private int getHover(int y)
	{
		for (int i = 0; i < bars; i++)
		{
			if (y >= 4 + i * 32 && y <= 28 + (i * 32))
			{
				return i;
			}
		}
		return -1;
	}
	
	@Override
	public void paintComponent(Graphics g) 
	{
		Graphics2D g2 = (Graphics2D)g;
		g2.setPaint(Color.CYAN);
		g2.fillRect(0, 0, width, height);
		
		for (int i = 0; i < bars; i++)
		{
			g2.setPaint(i == hover ? Color.RED : Color.BLACK);
			g2.fillRect(4, 4 + i * 32, 256, 24);
			g2.drawImage(colorAxisSet[i].img, 4, 8 + i * 32, null);
		}
	}
}
