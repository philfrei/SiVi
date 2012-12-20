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
package com.adonax.texturebuilder.export;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;


public class ExportFrame extends JDialog {

	public ExportFrame(JFrame parent) {
		super(parent);

		setTitle("Export");
		setModal(true);

		setLayout(new BorderLayout());

		final JTabbedPane cards = new JTabbedPane();

		// TODO: properly instantiate the texture parameters
		TextureParams params = new TextureParams();

		// supported langauges instantiated here
		final java.util.List<ExportCode> supportedLangs = new ArrayList<ExportCode>();
		final java.util.List<JTextArea> textAreas = new ArrayList<JTextArea>();

		supportedLangs.add(new ExportCodeJava(params));
		supportedLangs.add(new ExportCodeScala(params));

		for (ExportCode code : supportedLangs) {
			JTextArea textArea = new JTextArea();

			String codeStr = code.getCode();
			int pos = codeStr.indexOf("// --- end dynamically generated code");
			if (pos < 0) {
				pos = 0;
			}

			textArea.setText(code.getCode());
			textArea.setCaretPosition(pos);
			textAreas.add(textArea);

			cards.add(new JScrollPane(textArea), code.getLang());
		}

		add(cards, BorderLayout.CENTER);


		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

		JButton saveButton = new JButton("Save As...");
		saveButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				int i = cards.getSelectedIndex();
				System.out.println("selected index is: " + i);
				System.out.println("TODO: save code to file: " + textAreas.get(i).getText());
			}
		});

		JButton copyButton = new JButton("Copy to Clipboard");
		copyButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				int i = cards.getSelectedIndex();
				System.out.println("selected index is: " + i);
				System.out.println("TODO: copy code to clipboard: " + textAreas.get(i).getText());
			}
		});

		JButton closeButton = new JButton("Close");
		closeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				ExportFrame.this.dispose();
			}
		});


		buttonPanel.add(saveButton);
		buttonPanel.add(copyButton);
		buttonPanel.add(closeButton);

		add(buttonPanel, BorderLayout.SOUTH);

		setPreferredSize(new Dimension(600, 500));

		pack();
		setLocationRelativeTo(parent);
		setVisible(true);
	}
}
