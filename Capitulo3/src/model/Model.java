/*
    Algoritmes Avançats - Capitulo 3
    Autors:
        Jonathan Salisbury Vega
        Joan Sansó Pericàs
        Joan Vilella Candia
*/
package model;

import java.math.BigInteger;

/**
 * Class that contains the methods that simulate the various complexities.
 */
public class Model {

	/* Constants */

	/* Variables */

	/* Methods */
	public String multiply(String num1, String num2) {
		int res = 0, row = 1;
		for (int i = num2.length() - 1; i >= 0; i--) {
			int dig1 = Character.getNumericValue(num2.charAt(i));
			int row1 = 1, res1 = 0;
			for (int j = num1.length() - 1; j >= 0; j--) {
				int dig2 = Character.getNumericValue(num1.charAt(j));
				res1 += dig1 * dig2 * row1;
				row1 *= 10;
			}
			res += res1 * row;
			row *= 10;
		}
		return String.valueOf(res);
	}

	public String karatsuba(String num1, String num2) {
		return karatsuba(new BigInteger(num1), new BigInteger(num2)).toString();
	}

	private BigInteger karatsuba(BigInteger num1, BigInteger num2) {
		if (num1.intValue() < 10 || num2.intValue() < 10)
			return num1.multiply(num2);

		String num1s = num1.toString(), num2s = num2.toString();
		int min = Math.min(num1s.length(), num2s.length());
		int splitPoint = (int) Math.floor(min / 2);

		BigInteger high1 = new BigInteger(num1s.substring(0, splitPoint));
		BigInteger low1 = new BigInteger(num1s.substring(splitPoint, num1s.length()));
		BigInteger high2 = new BigInteger(num2s.substring(0, splitPoint));
		BigInteger low2 = new BigInteger(num2s.substring(splitPoint, num2s.length()));
		System.out.println(high1 + " " + low1 + " " + high2 + " " + low2);

		BigInteger z0 = karatsuba(low1, low2);
		BigInteger z1 = karatsuba(low1.add(high1), low2.add(high2));
		BigInteger z2 = karatsuba(high2, high2);

		BigInteger par1 = z2.multiply(BigInteger.TEN.pow(splitPoint * 2));
		BigInteger par2 = z1.subtract(z2).subtract(z0).multiply(BigInteger.TEN.pow(splitPoint));
		return par1.add(par2).add(z0);
	}
}
