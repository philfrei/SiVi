/*
 *  This file is part of SiVi.
 *
 *  SiVi is free software: you can redistribute it and/or modify it
 *  under the terms of the GNU General Public License as published
 *  by the Free Software Foundation, either version 3 of the 
 *  License, or (at your option) any later version.
 *
 *  SiVi is distributed in the hope that it will be useful, but
 *  WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public 
 *  License along with SiVi.  If not, see 
 *  <http://www.gnu.org/licenses/>.
 */
package com.adonax.tutorial;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.adonax.texturebuilder.ColorAxis;
import com.adonax.texturebuilder.ColorBarPeg;
import com.adonax.texturebuilder.STBPanel;
import com.adonax.texturebuilder.SimplexTextureSource;
import com.adonax.texturebuilder.TextureCombiner;
import com.adonax.tutorial.utilities.MakeTexturedImage;

public class TerraCard extends JPanel implements MouseListener,
	MouseMotionListener
{
	private static final long serialVersionUID = 1L;
	
	int width, height;
	BufferedImage image;
	Rectangle imageRect;
	boolean hover;
	final TutorialFramework tf;
	
	public TerraCard (int width, int height, final TutorialFramework tf)
	{
		setLayout(null);
		
		this.width = width;
		this.height = height;
		this.tf = tf;
		ColorAxis colorAxis = new ColorAxis();
		ArrayList<ColorBarPeg> colorBarPegs = new ArrayList<ColorBarPeg>();
		colorBarPegs.add(new ColorBarPeg(0, 255, 255, 255, 255));
		colorBarPegs.add(new ColorBarPeg(40, 153, 153, 153, 255));
		colorBarPegs.add(new ColorBarPeg(64, 0, 128, 0, 255));
		colorBarPegs.add(new ColorBarPeg(95, 96, 176, 0, 255));
		colorBarPegs.add(new ColorBarPeg(99, 224, 224, 128, 255));
		colorBarPegs.add(new ColorBarPeg(100, 204, 204, 204, 255));
		colorBarPegs.add(new ColorBarPeg(128, 64, 64, 255, 255));
		colorBarPegs.add(new ColorBarPeg(255, 0, 0, 192, 255));
		colorAxis.setColorBarPegs(colorBarPegs);
		
		float[] xScales = {1, 4, 16, 64};
		float[] yScales = {1, 4, 16, 64};
		float[] xTranslations = {0, 0, 0, 0};
		float[] yTranslations = {0, 0, 0, 0};
		float[] volumes = {0.7529412f, 0.1882353f, 
				0.047058824f, 0.011764706f};
		
		
		image = MakeTexturedImage.make(700, 160, colorAxis, 
				xScales, yScales, xTranslations, yTranslations,
				volumes, 0);
		imageRect = new Rectangle(50, 36, 700, 160);
		
		NavigationPanel np = new NavigationPanel(tf,
				"Classic Clouds", "Title Card", "Flames Card");
		np.setBounds((width - 428)/2, 0, 428, 32);
		add(np);
		
		JEditorPane textArea = new JEditorPane();
		textArea.setEditable(false);
	
		URL introURL = TutorialFramework.class.getResource(
				"pageContent/Terra.html");
		try
		{
			textArea.setPage(introURL);
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		JScrollPane scroller = new JScrollPane(textArea);
		scroller.setBounds(50, 200, width - 100, height - 300);
		add(scroller);

		addMouseListener(this);
		addMouseMotionListener(this);
	}
	
	private void setupTextureBuilder()
	{
		STBPanel stbPanel = tf.host;
		SimplexTextureSource[] sts = new SimplexTextureSource[4];
		sts[0] = stbPanel.sts1;
		sts[1] = stbPanel.sts2;
		sts[2] = stbPanel.sts3;
		sts[3] = stbPanel.sts4;
		
		sts[0].setXScaleVal(1);
		sts[0].setYScaleVal(1);
		sts[0].setMap(0);
		sts[1].setXScaleVal(4);
		sts[1].setYScaleVal(4);
		sts[1].setMap(0);
		sts[2].setXScaleVal(16);
		sts[2].setYScaleVal(16);
		sts[2].setMap(0);
		sts[3].setXScaleVal(64);
		sts[3].setYScaleVal(64);
		sts[3].setMap(0);
		
		ArrayList<ColorBarPeg>colorBarPegs = new ArrayList<ColorBarPeg>();
		colorBarPegs.add(new ColorBarPeg(0, 255, 255, 255, 255));
		colorBarPegs.add(new ColorBarPeg(40, 153, 153, 153, 255));
		colorBarPegs.add(new ColorBarPeg(64, 0, 128, 0, 255));
		colorBarPegs.add(new ColorBarPeg(95, 96, 176, 0, 255));
		colorBarPegs.add(new ColorBarPeg(99, 224, 224, 128, 255));
		colorBarPegs.add(new ColorBarPeg(100, 204, 204, 204, 255));
		colorBarPegs.add(new ColorBarPeg(128, 64, 64, 255, 255));
		colorBarPegs.add(new ColorBarPeg(255, 0, 0, 192, 255));

		stbPanel.getColorAxis(0).setColorBarPegs(colorBarPegs);
		
		sts[1].setColorAxis(stbPanel.getColorAxis(0));
		sts[2].setColorAxis(stbPanel.getColorAxis(0));
		sts[3].setColorAxis(stbPanel.getColorAxis(0));
		
		TextureCombiner txc = stbPanel.tc;
		
		txc.setStage1Weight(0, 64);
		txc.setStage1Weight(1, 16);
		txc.setStage1Weight(2, 4);
		txc.setStage1Weight(3, 1);
		
		txc.setStage2Weight(0, 64);
		txc.setStage2Weight(1, 64);
		txc.setStage2Weight(2, 64);
		txc.setStage2Weight(3, 64);
		
		String s;
		while (true)
		{
			s = txc.clickStage1Button(0);
			if (s.equals("lerp")) break;
		}
		while (true)
		{
			s = txc.clickStage1Button(1);
			if (s.equals("lerp")) break;
		}
		while (true)
		{
			s = txc.clickStage1Button(2);
			if (s.equals("lerp")) break;
		}
		while (true)
		{
			s = txc.clickStage1Button(3);
			if (s.equals("lerp")) break;
		}		

		while (true)
		{
			s = txc.clickStage2Button(0);
			if (s.equals("lerp")) break;
		}
		while (true)
		{
			s = txc.clickStage2Button(1);
			if (s.equals("lerp")) break;
		}
		while (true)
		{
			s = txc.clickStage2Button(2);
			if (s.equals("lerp")) break;
		}
		while (true)
		{
			s = txc.clickStage2Button(3);
			if (s.equals("lerp")) break;
		}		
		stbPanel.update();
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		Graphics2D g2 = (Graphics2D)g;
		
		RenderingHints hint = new RenderingHints( 
				RenderingHints.KEY_TEXT_ANTIALIASING,
				RenderingHints.VALUE_TEXT_ANTIALIAS_ON );
			
		g2.setRenderingHints( hint );
	
		// Background
		g2.setColor(new Color(255, 255, 255));
		g2.fillRect(0, 0, width, height);

		g2.drawImage(image, 50, 36, null);
	
		g2.setColor(hover ? Color.RED : Color.BLACK);
		g2.draw(imageRect);
	}

	@Override
	public void mouseClicked(MouseEvent arg0)
	{
		if (imageRect.contains(arg0.getPoint()))
		{
			setupTextureBuilder();
		}
	}

	@Override
	public void mouseEntered(MouseEvent arg0){}

	@Override
	public void mouseExited(MouseEvent arg0){}

	@Override
	public void mousePressed(MouseEvent arg0){}

	@Override
	public void mouseReleased(MouseEvent arg0){}

	@Override
	public void mouseDragged(MouseEvent e){}

	@Override
	public void mouseMoved(MouseEvent e)
	{
		if (imageRect.contains(e.getPoint()))
		{
			if (hover == false)
			{
				hover = true;
				repaint();
			}
		}
		else
		{
			if (hover == true)
			{
				hover = false;
				repaint();
			}
		}
	}
}
