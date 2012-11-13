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
package com.adonax.tutorial.utilities;
 
public class ColorMap {

	/**
	 * colorBarPegs = [location, r, b, g, alpha]
	 * Must be in ascending order by location!
	 */
	public static int[]makeMap(int[][] colorBarPegs)
	{
		int[] data = new int[256];

		int idx, nextLoc;
		float redIncr, greenIncr, blueIncr, alphaIncr;
		float redSum, greenSum, blueSum, alphaSum;
		
		int lastRow = colorBarPegs.length - 1;
		int[] peg, nextPeg;
		for (int i = 0; i < lastRow; i++)
		{
			peg = colorBarPegs[i];

			idx = peg[0];

//			data[idx][0] = peg[1]; 
//			data[idx][1] = peg[2];
//			data[idx][2] = peg[3];
//			data[idx][3] = peg[4];

			data[idx] = calculateARGB(peg[4], peg[1], peg[2], peg[3]);

			redSum = peg[1]; 
			greenSum = peg[2];
			blueSum = peg[3];
			alphaSum = peg[4];
					
			nextPeg = colorBarPegs[i + 1];
			nextLoc = nextPeg[0];
			float cols = nextLoc - idx;
		
			redIncr = (nextPeg[1] - peg[1])/cols;
			greenIncr = (nextPeg[2] - peg[2])/cols;
			blueIncr = (nextPeg[3] - peg[3])/cols;
			alphaIncr = (nextPeg[4] - peg[4])/cols;
			
			for (int x = idx; x < nextLoc; x++)
			{
				redSum += redIncr;
//				data[x][0] = Math.round(redSum);
				greenSum += greenIncr;
//				data[x][1] = Math.round(greenSum);
				blueSum += blueIncr;
//				data[x][2] = Math.round(blueSum);
				alphaSum += alphaIncr;
//				data[x][3] = Math.round(alphaSum);
				
				
				
				data[x] = calculateARGB(Math.round(alphaSum),
						Math.round(redSum),
						Math.round(greenSum), 
						Math.round(blueSum));
			}
		}
		
		peg = colorBarPegs[lastRow];
		data[255] = calculateARGB(peg[4], peg[1], peg[2], peg[3]);
//		data[255][0] = peg[1];
//		data[255][1] = peg[2];
//		data[255][2] = peg[3];
//		data[255][3] = peg[4];
		
		return data;
	}
	
	private static int calculateARGB(int a, int r, int g, int b)
	{
		return b + (g << 8) + (r <<16) + (a << 24);
	}
}
