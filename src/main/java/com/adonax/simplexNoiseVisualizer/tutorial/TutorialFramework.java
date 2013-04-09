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

package com.adonax.simplexNoiseVisualizer.tutorial;

import java.awt.CardLayout;
import java.awt.Dimension;

import javax.swing.JDialog;
import javax.swing.JPanel;

import com.adonax.simplexNoiseVisualizer.TopPanel;

public class TutorialFramework extends JDialog {

	private static final long serialVersionUID = 1L;

	CardLayout cardLayout;
	JPanel cards;
	public final TopPanel topPanel;
	
	public TutorialFramework(TopPanel topPanel)
	{
	    setTitle("Tutorial");
	    
	    this.topPanel = topPanel;
	    
	    cardLayout = new CardLayout();
	    cards = new JPanel(cardLayout);
	    cards.setPreferredSize(new Dimension(800, 750));
	    
	    
	    JPanel card1 = new TitleCard(this);
	    JPanel card2 = new IntroCard(800, 750, this);
	    JPanel card3 = new SmoothNoiseCard(this);
	    JPanel card4 = new TreeRingCard(this);
	    JPanel card5 = new CloudCard(this);
	    JPanel card6 = new TerraCard(this);
	    JPanel card7 = new TemplateCard(800, 750, this);
	    JPanel card8 = new SimplexCard(800, 750, this);
	    JPanel card9 = new ColorMapCard(800, 750, this);
	    JPanel card10 = new SolarFlareCard(this);
	    cards.add(card1, "Title Card");
	    cards.add(card2, "Intro Card");
	    cards.add(card3, "Smooth Noise");
	    cards.add(card4, "Tree Rings");
	    cards.add(card5, "Classic Clouds");
	    cards.add(card6, "Planet Terrain");
	    cards.add(card7, "Template Card");
	    cards.add(card8, "Simplex Card");
	    cards.add(card9, "ColorMap Card");
	    cards.add(card10, "Flare Card");
	    
	    add(cards);
	}

	public void setCard(String string) 
	{
		cardLayout.show(cards, string);
	}	
}
