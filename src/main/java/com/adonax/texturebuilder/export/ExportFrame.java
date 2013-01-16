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

import com.adonax.texturebuilder.CombineParams;
import com.adonax.texturebuilder.TextureParams;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;


public class ExportFrame extends JDialog {

	private static final long serialVersionUID = 1L;

	public ExportFrame(JFrame parent, java.util.List<TextureParams> textureParamsList, CombineParams combineParams) {
		super(parent);

		setTitle("Export Code");
		setModal(true);

		setLayout(new BorderLayout());

		final JTabbedPane cards = new JTabbedPane();

		// supported langauges instantiated here
		final java.util.List<ExportCode> supportedLangs = new ArrayList<ExportCode>();
		final java.util.List<JTextArea> textAreas = new ArrayList<JTextArea>();

		supportedLangs.add(new ExportCodeJava(textureParamsList, combineParams));
		supportedLangs.add(new ExportCodeScala(textureParamsList, combineParams));

		for (ExportCode code : supportedLangs) {
			JTextArea textArea = new JTextArea();

			String codeStr = code.getCode();
			int pos = codeStr.indexOf("// --- begin dynamically generated code");
			if (pos < 0) {
				pos = 0;
			}

			textArea.setText(codeStr);
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
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setSelectedFile(new File("TestTemplate." + supportedLangs.get(i).getLang().toLowerCase()));

				int userSelection = fileChooser.showSaveDialog(ExportFrame.this);
				if (userSelection == JFileChooser.APPROVE_OPTION) {
					File saveAsFile = fileChooser.getSelectedFile();

					BufferedWriter writer = null;
					try {
						writer = new BufferedWriter(new FileWriter(saveAsFile));
						writer.write(textAreas.get(i).getText());
					} catch (IOException e) {
						System.out.println("exception caught" + e);
					} finally {
						try {
							if (writer != null) {
								writer.close();
							}
						} catch (IOException e) {
							// ignore
						}
					}
				}
			}
		});

		JButton copyButton = new JButton("Copy to Clipboard");
		copyButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				int i = cards.getSelectedIndex();
				StringSelection data = new StringSelection(textAreas.get(i).getText());
				Toolkit.getDefaultToolkit().getSystemClipboard().setContents(data, data);
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
