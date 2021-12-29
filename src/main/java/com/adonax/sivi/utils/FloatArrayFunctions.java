package com.adonax.sivi.utils;

public class FloatArrayFunctions
{
	// Linear interpolation of all values in two float arrays.
	public static float[] LerpTwoFloatArrays(float[] a, 
			int aFactor, float[] b, int bFactor)
	{
		float[] c = new float[a.length];
		
		for (int i = 0; i < a.length; i++)
		{
			c[i] = (a[i] * aFactor + b[i] * bFactor) / 
					(aFactor + bFactor);
		}

		return c;
	}
	
	public static float[] makeHorizonallySymmetricArray(float[] arr, int wd, int ht, 
			boolean singleCenter) 
	{
		float[] symArr;
		int yMirror;

		/*
		 * The result can either duplicate the last row (resulting in a final row
		 * count of ht * 2) or only have a single copy of the last row (resulting 
		 * in a final row count of ht * 2 - 1).
		 * The following sets up relevant variables for the specified scenario.
		 */
		if (singleCenter) {
			symArr = new float[wd * (ht * 2 - 1)];
			yMirror = ht * 2 - 2;
		} else {
			symArr = new float[wd * (ht * 2)];
			yMirror = ht * 2 - 1;
		}

		int xPtr = -1;
		int iMirror = yMirror * wd;
		boolean doMirrorCopy = true;	
		
		int ii = 0;
		int nn = arr.length;
		while (ii < nn ) {
			// if finished a row, set up to continue on the next row
			if (++xPtr >= wd) {
				xPtr = 0;
				yMirror--;
				if (yMirror < ht) {
					doMirrorCopy = false;
				}
				iMirror = yMirror * wd;
			}
			symArr[ii] = arr[ii];
			if (doMirrorCopy) {
				symArr[iMirror++] = arr[ii];				
			}
			ii++;
		}
		
		return symArr;
	}
	
	public static float[] makeVerticallySymmetricArray(float[] arr, int wd, int ht,
			boolean singleCenter) 
	{
		
		float[] symArr;
		int symRowLength;
		/*
		 * The result can either duplicate the last column (resulting in a final column
		 * count of wd * 2) or only have a single copy of the last column (resulting 
		 * in a final column count of wd * 2 - 1).
		 * The following sets up relevant variables for the specified scenario.
		 */
		if (singleCenter) {
			symArr = new float[(wd * 2 - 1) * ht];
			symRowLength = wd * 2 - 1;
		} else {
			symArr = new float[(wd * 2) * ht];
			symRowLength = wd * 2;
		}

		int xPtr = 0;
		int xLeft = 0;
		int y = 0;
		int ii = 0;
		int nn = arr.length;
		int xMirror = symRowLength - 1;
		
		while(ii < nn) {
			
			// normal situation, up to the next to last column
			if (xPtr++ < (wd - 1)) {
				symArr[xLeft++] = arr[ii];
				symArr[xMirror--] = arr[ii];				
			} else {
				// these actions on last column
				symArr[xLeft++] = arr[ii];
				if (!singleCenter) {
					symArr[xMirror--] = arr[ii];
				}
				
				// now reset for next row
				xPtr = 0;
				xLeft = ++y * symRowLength;
				xMirror = xLeft + symRowLength - 1;
			}
			
			ii++;
		}
		
		return symArr;
	}
}
