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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class NavigationPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	JButton previous;
	JButton contents;
	JButton next;
	
	String previousCardID;
	String contentsCardID;
	String nextCardID;
	
	public NavigationPanel(final TutorialFramework tf,
			final String previousCardID,
			final String contentsCardID, 
			final String nextCardID)
	{
		this.contentsCardID = contentsCardID;
		this.previousCardID = previousCardID;
		this.nextCardID = nextCardID;
		
		setLayout(null);
		
		previous = new JButton("<previous");
		previous.setHorizontalTextPosition(SwingConstants.CENTER);
		previous.setBounds(4, 4, 120, 24);
		previous.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				tf.setCard(previousCardID);
			}
		});
		add(previous);
		
		contents = new JButton("<contents>");
		contents.setHorizontalTextPosition(SwingConstants.CENTER);
		contents.setBounds(154, 4, 120, 24);
		contents.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				tf.setCard(contentsCardID);
			}
		});
		add(contents);
		
		next = new JButton("next>");
		next.setHorizontalTextPosition(SwingConstants.CENTER);
		next.setBounds(304, 4, 120, 24);
		next.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				tf.setCard(nextCardID);
			}
		});
		add(next);
	}
}
