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

	public String add(String num1, String num2) {
		if (num1.length() > num2.length()) {
			String t = num1;
			num1 = num2;
			num2 = t;
		}

		// Take an empty String for storing result
		String str = "";

		// Calculate length of both String
		int n1 = num1.length(), n2 = num2.length();

		// Reverse both of Strings
		num1 = new StringBuilder(num1).reverse().toString();
		num2 = new StringBuilder(num2).reverse().toString();

		int carry = 0;
		for (int i = 0; i < n1; i++) {
			// Do school mathematics, compute sum of
			// current digits and carry
			int sum = ((int) (num1.charAt(i) - '0') +
					(int) (num2.charAt(i) - '0') + carry);
			str += (char) (sum % 10 + '0');

			// Calculate carry for next step
			carry = sum / 10;
		}

		// Add remaining digits of larger number
		for (int i = n1; i < n2; i++) {
			int sum = ((int) (num2.charAt(i) - '0') + carry);
			str += (char) (sum % 10 + '0');
			carry = sum / 10;
		}

		// Add remaining carry
		if (carry > 0)
			str += (char) (carry + '0');

		// reverse resultant String
		str = new StringBuilder(str).reverse().toString();

		return str;
	}

	public boolean isSmaller(String str1, String str2) {
		// Calculate lengths of both string
		int n1 = str1.length(), n2 = str2.length();
		if (n1 < n2)
			return true;
		if (n2 < n1)
			return false;

		for (int i = 0; i < n1; i++)
			if (str1.charAt(i) < str2.charAt(i))
				return true;
			else if (str1.charAt(i) > str2.charAt(i))
				return false;

		return false;
	}

	public String findDiff(String str1, String str2) {
		// Before proceeding further, make sure str1
		// is not smaller
		if (isSmaller(str1, str2)) {
			String t = str1;
			str1 = str2;
			str2 = t;
		}

		// Take an empty string for storing result
		String str = "";

		// Calculate length of both string
		int n1 = str1.length(), n2 = str2.length();

		// Reverse both of strings
		str1 = new StringBuilder(str1).reverse().toString();
		str2 = new StringBuilder(str2).reverse().toString();

		int carry = 0;

		// Run loop till small string length
		// and subtract digit of str1 to str2
		for (int i = 0; i < n2; i++) {
			// Do school mathematics, compute difference of
			// current digits
			int sub = ((int) (str1.charAt(i) - '0')
					- (int) (str2.charAt(i) - '0') - carry);

			// If subtraction is less then zero
			// we add then we add 10 into sub and
			// take carry as 1 for calculating next step
			if (sub < 0) {
				sub = sub + 10;
				carry = 1;
			} else
				carry = 0;

			str += (char) (sub + '0');
		}

		// subtract remaining digits of larger number
		for (int i = n2; i < n1; i++) {
			int sub = ((int) (str1.charAt(i) - '0') - carry);

			// if the sub value is -ve, then make it
			// positive
			if (sub < 0) {
				sub = sub + 10;
				carry = 1;
			} else
				carry = 0;

			str += (char) (sub + '0');
		}

		// reverse resultant string
		return new StringBuilder(str).reverse().toString();
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
		BigInteger z1 = karatsuba(new BigInteger(add(low1.toString(), high1.toString())),
				new BigInteger(add(low2.toString(), high2.toString())));
		BigInteger z2 = karatsuba(high2, high2);

		BigInteger par1 = z2.multiply(BigInteger.TEN.pow(splitPoint * 2)); // esto no es concatenar "siplitpoint*2"
																			// zeros?
		BigInteger par2 = z1.subtract(z2).subtract(z0).multiply(BigInteger.TEN.pow(splitPoint));
		return par1.add(par2).add(z0);
	}
}
