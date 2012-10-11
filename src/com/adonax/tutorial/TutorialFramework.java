
package com.adonax.tutorial;

import java.awt.CardLayout;

import javax.swing.JDialog;
import javax.swing.JPanel;

import com.adonax.texturebuilder.STBPanel;

public class TutorialFramework extends JDialog {

	private static final long serialVersionUID = 1L;

	STBPanel host;
	CardLayout cardLayout;
	JPanel cards;
	
	public TutorialFramework(STBPanel host)
	{
		this.host = host;
	    setTitle("Tutorial");
	    
	    cardLayout = new CardLayout();
	    cards = new JPanel(cardLayout);
	    cards.setBounds(100, 100, 800, 800);
	    
	    
	    JPanel card1 = new TitleCard(800, 750, this);
	    JPanel card2 = new IntroCard(800, 750, this);
	    JPanel card3 = new SmoothNoiseCard(800, 750, this);
	    JPanel card4 = new TreeRingCard(800, 750, this);
	    JPanel card5 = new CloudCard(800, 750, this);
	    JPanel card6 = new TerraCard(800, 750, this);
	    JPanel card7 = new TemplateCard(800, 750, this);
	    JPanel card8 = new SimplexCard(800, 750, this);
	    JPanel card9 = new ColorMapCard(800, 750, this);
	    JPanel card10 = new FlamesCard(800, 750, this);
	    cards.add(card1, "Title Card");
	    cards.add(card2, "Intro Card");
	    cards.add(card3, "Smooth Noise");
	    cards.add(card4, "Tree Rings");
	    cards.add(card5, "Classic Clouds");
	    cards.add(card6, "Planet Terrain");
	    cards.add(card7, "Template Card");
	    cards.add(card8, "Simplex Card");
	    cards.add(card9, "ColorMap Card");
	    cards.add(card10, "Flames Card");
	    
	    add(cards);
	}

	public void setCard(String string) 
	{
		cardLayout.show(cards, string);
	}	
}