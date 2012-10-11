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

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;

import com.adonax.texturebuilder.ColorAxis;
import com.adonax.texturebuilder.ColorBarPeg;
import com.adonax.texturebuilder.STBPanel;
import com.adonax.texturebuilder.SimplexTextureSource;
import com.adonax.texturebuilder.TextureCombiner;
import com.adonax.tutorial.utilities.MakeTexturedImage;

public class SmoothNoiseCard extends JPanel implements MouseListener,
	MouseMotionListener
{
	private static final long serialVersionUID = 1L;
	
	int width, height;
	BufferedImage image;
	Rectangle imageRect;
	boolean hover;
	final TutorialFramework tf;
	
	public SmoothNoiseCard (int width, int height, final TutorialFramework tf)
	{
		setLayout(null);
		
		this.width = width;
		this.height = height;
		this.tf = tf;
		ColorAxis colorAxis = new ColorAxis();
		ArrayList<ColorBarPeg> colorBarPegs = new ArrayList<ColorBarPeg>();
		colorBarPegs.add(new ColorBarPeg(0, 0, 0, 0, 255));
		colorBarPegs.add(new ColorBarPeg(255, 255, 255, 255, 255));
		colorAxis.setColorBarPegs(colorBarPegs);
		
		float[] xScales = {2};
		float[] yScales = {2};
		float[] xTranslations = {0};
		float[] yTranslations = {0};
		float[] volumes = {1.0f};
		
		image = MakeTexturedImage.make(700, 160, colorAxis, 
				xScales, yScales, xTranslations, yTranslations,
				volumes, 0);
		imageRect = new Rectangle(50, 36, 700, 160);
		
		NavigationPanel np = new NavigationPanel(tf, 
				"Intro Card", "Title Card", "Tree Rings");
		np.setBounds((width - 428)/2, 0, 428, 32);
		add(np);
		
//		JEditorPane textArea = new JEditorPane();
		JTextPane textArea = new JTextPane();
		textArea.setEditable(false);
	
		URL introURL = TutorialFramework.class.getResource(
				"pageContent/smoothNoise.html");

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
		
		sts[0].setXScaleVal(2);
		sts[0].setYScaleVal(2);
		sts[0].setMap(0);
		sts[1].setXScaleVal(2);
		sts[1].setYScaleVal(2);
		sts[1].setMap(0);
		sts[2].setXScaleVal(2);
		sts[2].setYScaleVal(2);
		sts[2].setMap(0);
		sts[3].setXScaleVal(2);
		sts[3].setYScaleVal(2);
		sts[3].setMap(0);
		
		ArrayList<ColorBarPeg>colorBarPegs = new ArrayList<ColorBarPeg>();
		colorBarPegs.add(new ColorBarPeg(0, 0, 0, 0, 255));
		colorBarPegs.add(new ColorBarPeg(255, 255, 255, 255, 255));
		stbPanel.getColorAxis(0).setColorBarPegs(colorBarPegs);

		ArrayList<ColorBarPeg>blankPegs = new ArrayList<ColorBarPeg>();
		blankPegs.add(new ColorBarPeg(0, 0, 0, 0, 255));
		blankPegs.add(new ColorBarPeg(255, 0, 0, 0, 255));
		stbPanel.getColorAxis(1).setColorBarPegs(blankPegs);
		
		sts[1].setColorAxis(stbPanel.getColorAxis(1));
		sts[2].setColorAxis(stbPanel.getColorAxis(1));
		sts[3].setColorAxis(stbPanel.getColorAxis(1));
		
		TextureCombiner txc = stbPanel.tc;
		
		txc.setStage1Weight(0, 64);
		txc.setStage1Weight(1, 0);
		txc.setStage1Weight(2, 0);
		txc.setStage1Weight(3, 0);
		
		txc.setStage2Weight(0, 64);
		txc.setStage2Weight(1, 0);
		txc.setStage2Weight(2, 0);
		txc.setStage2Weight(3, 0);
		
		String s;
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
			tf.setVisible(false);
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
