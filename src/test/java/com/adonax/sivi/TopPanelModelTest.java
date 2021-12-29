package com.adonax.sivi;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class TopPanelModelTest {

	@Test
	void testTopPanelModel() {
		TopPanelModel tpm = new TopPanelModel();
		
		assertEquals(tpm.octaves, 4);
		assertEquals(tpm.finalWidth, 480);
		assertEquals(tpm.finalHeight, 240);
		assertEquals(tpm.colorMaps, 6);
		assertEquals(tpm.isVerticallySymmetric, false);
		assertEquals(tpm.isHorizontallySymmetric, false);
	}

	@Test
	void testTopPanelModelIntIntIntIntBooleanBoolean() {
		TopPanelModel tpm = new TopPanelModel(5, 256, 256, 4, true, true);
		
		assertEquals(tpm.octaves, 5);
		assertEquals(tpm.finalWidth, 256);
		assertEquals(tpm.finalHeight, 256);
		assertEquals(tpm.colorMaps, 4);
		assertEquals(tpm.isVerticallySymmetric, true);
		assertEquals(tpm.isHorizontallySymmetric, true);
	}

	@Test
	void testSetAppSetting() {
		TopPanelModel tpm0 = new TopPanelModel();
		TopPanelModel tpm1 = TopPanelModel.setAppSetting(tpm0, TopPanelModel.Fields.OCTAVES, 8);
		assertEquals(tpm1.octaves, 8);
		assertEquals(tpm1.finalWidth, tpm0.finalWidth);
		assertEquals(tpm1.finalHeight, tpm0.finalHeight);
		assertEquals(tpm1.colorMaps, tpm0.colorMaps);
		assertEquals(tpm1.isVerticallySymmetric, tpm0.isVerticallySymmetric);
		assertEquals(tpm1.isHorizontallySymmetric, tpm0.isHorizontallySymmetric);
		
		// Do we need to do this with every field?
				
	}

}
