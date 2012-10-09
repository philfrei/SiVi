package com.adonax.texturebuilder.test;

import java.util.ArrayList;

import com.adonax.texturebuilder.ColorBarPeg;
import com.adonax.texturebuilder.STBPanel;
import com.adonax.texturebuilder.SimplexTextureBuilder;
import com.adonax.texturebuilder.SimplexTextureSource;
import com.adonax.texturebuilder.TextureCombiner;

public class TestStsSettings {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		SimplexTextureBuilder.main(null);

		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		STBPanel stbPanel = SimplexTextureBuilder.frame.stbPanel;
		
		SimplexTextureSource[] sts = new SimplexTextureSource[4];
		sts[0] = stbPanel.sts1;
		sts[1] = stbPanel.sts2;
		sts[2] = stbPanel.sts3;
		sts[3] = stbPanel.sts4;
		
		
		sts[0].setXScaleVal(2.0);
		sts[0].setYScaleVal(2.0);
		sts[1].setXScaleVal(4.0);
		sts[1].setYScaleVal(4.0);
		sts[2].setXScaleVal(8.0);
		sts[2].setYScaleVal(8.0);
		sts[3].setXScaleVal(16.0);
		sts[3].setYScaleVal(16.0);
		
		
//		sts[0].setXTranslationVal(100);
//		sts[1].setYTranslationVal(-50);
//		sts[2].setMinVal(-0.5);
//		sts[3].setMaxVal(0.5);	
		
		ArrayList<ColorBarPeg>colorBarPegs = new ArrayList<ColorBarPeg>();
		colorBarPegs.add(new ColorBarPeg(0, 0, 0, 255, 255));
		colorBarPegs.add(new ColorBarPeg(255, 255, 255, 255, 255));
		stbPanel.getColorAxis(0).setColorBarPegs(colorBarPegs);
		
		sts[1].setColorAxis(stbPanel.getColorAxis(0));
		sts[2].setColorAxis(stbPanel.getColorAxis(0));
		sts[3].setColorAxis(stbPanel.getColorAxis(0));
		
		TextureCombiner txc = stbPanel.tc;
		
		txc.setStage1Weight(0, 32);
		txc.setStage1Weight(1, 16);
		txc.setStage1Weight(2, 8);
		txc.setStage1Weight(3, 4);
		
		txc.setStage2Weight(0, 64);
		txc.setStage2Weight(1, 64);
		txc.setStage2Weight(2, 64);
		txc.setStage2Weight(3, 64);
		
		txc.clickStage2Button(0);
		txc.clickStage2Button(1);
		txc.clickStage2Button(2);
		txc.clickStage2Button(3);
		
		stbPanel.update();
	}

}
