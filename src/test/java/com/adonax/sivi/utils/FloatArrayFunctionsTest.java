package com.adonax.sivi.utils;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class FloatArrayFunctionsTest {

	static float[] testArray;
	
	@BeforeAll
	static void initTestArray() {
		testArray = new float[100];
		for (int i = 0; i < 100; i++) {
			testArray[i] = i;
		}
	}
	
	@Test
	void TestMakeHorizontallySymmetricArray() {
		float[] symArray = FloatArrayFunctions.makeHorizonallySymmetricArray(testArray, 10, 10, false);

		for (int i = 0, n = symArray.length; i < n; i++) {
			System.out.println(i + ":" + symArray[i]);
		}
		
		assertEquals(testArray[0], symArray[0], 0.01f);
		assertEquals(testArray[0], symArray[190], 0.01f);
		assertEquals(testArray[9], symArray[9], 0.01f);
		assertEquals(testArray[9], symArray[199], 0.01f);
		assertEquals(testArray[25], symArray[25], 0.01f);
		assertEquals(testArray[25], symArray[175], 0.01f);
		assertEquals(testArray[75], symArray[75], 0.01f);
		assertEquals(testArray[75], symArray[125], 0.01f);
		assertEquals(testArray[90], symArray[90], 0.01f);	
		assertEquals(testArray[90], symArray[100], 0.01f);	
		assertEquals(testArray[99], symArray[99], 0.01f);
		assertEquals(testArray[99], symArray[109], 0.01f);

		symArray = FloatArrayFunctions.makeHorizonallySymmetricArray(testArray, 10, 10, true);

		for (int i = 0, n = symArray.length; i < n; i++) {
			System.out.println(i + ":" + symArray[i]);
		}
		
		assertEquals(testArray[0], symArray[0], 0.01f);
		assertEquals(testArray[0], symArray[180], 0.01f);
		assertEquals(testArray[9], symArray[9], 0.01f);
		assertEquals(testArray[9], symArray[189], 0.01f);
		assertEquals(testArray[25], symArray[25], 0.01f);
		assertEquals(testArray[25], symArray[165], 0.01f);
		assertEquals(testArray[75], symArray[75], 0.01f);
		assertEquals(testArray[75], symArray[115], 0.01f);
		assertEquals(testArray[90], symArray[90], 0.01f);	
		assertNotEquals(testArray[90], symArray[100], 0.01f);	
		assertEquals(testArray[99], symArray[99], 0.01f);
		assertNotEquals(testArray[99], symArray[109], 0.01f);		
	
		 /* 
		 *  0   ..  8
		 *  9
		 *  18
		 *  27
		 *  36
		 *  45
		 *  54
		 *  63 
		 *  72  ..  80 
		 *  81  ..  89
		 *  90
		 *  99
		 *  108
		 *  117
		 *  126
		 *  135
		 *  144  .. 152
		 *  153  .. 161
		 */
	
	}

	@Test
	void TestMakeVerticallySymmetricArray() {
		float[] symArray = FloatArrayFunctions.makeVerticallySymmetricArray(testArray, 10, 10, false);

		for (int i = 0, n = symArray.length; i < n; i++) {
			System.out.println(i + ":" + symArray[i]);
		}

		assertEquals(testArray[0], symArray[0], 0.01f);
		assertEquals(testArray[0], symArray[19], 0.01f);
		assertEquals(testArray[9], symArray[9], 0.01f);
		assertEquals(testArray[9], symArray[10], 0.01f);
		assertEquals(testArray[25], symArray[45], 0.01f);
		assertEquals(testArray[25], symArray[54], 0.01f);
		assertEquals(testArray[75], symArray[145], 0.01f);
		assertEquals(testArray[75], symArray[154], 0.01f);
		assertEquals(testArray[90], symArray[180], 0.01f);	
		assertEquals(testArray[90], symArray[199], 0.01f);	
		assertEquals(testArray[99], symArray[189], 0.01f);
		assertEquals(testArray[99], symArray[190], 0.01f);

		symArray = FloatArrayFunctions.makeVerticallySymmetricArray(testArray, 10, 10, true);

		for (int i = 0, n = symArray.length; i < n; i++) {
			System.out.println(i + ":" + symArray[i]);
		}

		assertEquals(testArray[0], symArray[0], 0.01f);
		assertEquals(testArray[0], symArray[18], 0.01f);
		assertEquals(testArray[9], symArray[9], 0.01f);
		assertNotEquals(testArray[9], symArray[10], 0.01f);
		
		assertEquals(testArray[25], symArray[43], 0.01f);
		assertEquals(testArray[25], symArray[51], 0.01f);
		assertEquals(testArray[75], symArray[138], 0.01f);
		assertEquals(testArray[75], symArray[146], 0.01f);

		assertEquals(testArray[90], symArray[171], 0.01f);	
		assertEquals(testArray[90], symArray[189], 0.01f);	
		assertEquals(testArray[99], symArray[180], 0.01f);

		
		/*
		 *   0 ..   9 ..  18
		 *  19 ..  28 ..  37
		 *  38 ..  47 ..  56
		 *  57 ..  66 ..  75
		 *  76 ..  85 ..  94
		 *  95 .. 104 .. 113
		 * 114 .. 123 .. 133
		 * 133 .. 142 .. 151 
		 * 152 .. 161 .. 170
		 * 171 .. 180 .. 189
		 */
	}
}
