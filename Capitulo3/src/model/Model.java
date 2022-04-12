/*
    Algoritmes Avançats - Capitulo 3
    Autors:
        Jonathan Salisbury Vega
        Joan Sansó Pericàs
        Joan Vilella Candia
*/
package model;

import java.math.BigInteger;
import java.util.Collections;

/**
 * Class that contains the methods that simulate the various complexities.
 */
public class Model {

	/* Constants */

	/* Variables */

	/* Methods */

	public String multiply(String num1, String num2) {
		boolean isNeg = false;
		if (num1.charAt(0) == '-') {
			if (num2.charAt(0) == '-') {
				num2 = num2.substring(1);
			} else {
				isNeg = true;
			}
			num1 = num1.substring(1);
		} else if (num2.charAt(0) == '-') {
			isNeg = true;
			num2 = num2.substring(1);
		}
		String n1 = num1.length() > num2.length() ? num1 : num2;
		String n2 = num1.length() > num2.length() ? num2 : num1;
		String result = "0";
		n1 = new StringBuilder(n1).reverse().toString();
		n2 = new StringBuilder(n2).reverse().toString();
		for (int i = 0; i < n2.length(); i++) {
			int carry = 0;
			int dig1 = Character.getNumericValue(n2.charAt(i));
			String partialRes = "";
			for (int j = 0; j < n1.length(); j++) {
				int dig2 = Character.getNumericValue(n1.charAt(j));
				int aux = dig1 * dig2 + carry;
				carry = aux / 10;
				aux = aux % 10;
				partialRes = String.valueOf(aux) + partialRes;
			}
			if (carry > 0)
				partialRes = String.valueOf(carry) + partialRes;
			result = add(result, partialRes + String.join("", Collections.nCopies(i, "0")));
		}
		return isNeg ? "-" + result : result;
	}

	public String add(String num1, String num2) {
		if (num1.charAt(0) == '-') {
			if (num2.charAt(0) == '-') {
				return "-" + add(num1.substring(1), num2.substring(1));
			} else {
				return sub(num2, num1.substring(1));
			}
		} else if (num2.charAt(0) == '-') {
			return sub(num1, num2.substring(1));
		}
		String n1 = num1.length() > num2.length() ? num1 : num2;
		String n2 = num1.length() > num2.length() ? num2 : num1;
		String result = "";
		int diff = Math.abs(n1.length() - n2.length());
		n2 = String.join("", Collections.nCopies(diff, "0")) + n2;
		n1 = new StringBuilder(n1).reverse().toString();
		n2 = new StringBuilder(n2).reverse().toString();
		int carry = 0;
		for (int i = 0; i < n1.length(); i++) {
			int dig1 = Character.getNumericValue(n1.charAt(i));
			int dig2 = Character.getNumericValue(n2.charAt(i));
			int aux = dig1 + dig2 + carry;
			result = String.valueOf(aux % 10) + result;
			carry = aux / 10;
		}
		if (carry > 0)
			result = String.valueOf(carry) + result;
		return result;
	}

	public boolean isSmaller(String num1, String num2) {
		int n1 = num1.length(), n2 = num2.length();
		if (n1 < n2)
			return true;
		if (n2 < n1)
			return false;

		for (int i = 0; i < n1; i++)
			if (num1.charAt(i) < num2.charAt(i))
				return true;
			else if (num1.charAt(i) > num2.charAt(i))
				return false;
		return false;
	}

	public String sub(String num1, String num2) {
		if (num1.charAt(0) == '-') {
			if (num2.charAt(0) == '-') {
				return sub(num2.substring(1), num1.substring(1));
			} else {
				return "-" + add(num1.substring(1), num2);
			}
		} else if (num2.charAt(0) == '-') {
			return add(num1, num2.substring(1));
		}
		boolean flipped = false;
		if (isSmaller(num1, num2)) {
			String temp = num1;
			num1 = num2;
			num2 = temp;
			flipped = true;
		}
		int diff = num1.length() - num2.length();
		num2 = String.join("", Collections.nCopies(diff, "0")) + num2;
		num1 = new StringBuilder(num1).reverse().toString();
		num2 = new StringBuilder(num2).reverse().toString();
		int carry = 0;
		String result = "";
		for (int i = 0; i < num1.length(); i++) {
			int dig1 = Character.getNumericValue(num1.charAt(i));
			int dig2 = Character.getNumericValue(num2.charAt(i));
			int aux = dig1 - dig2 + carry;
			if (aux < 0) {
				aux += 10;
				carry = -1;
			} else
				carry = 0;
			result = String.valueOf(aux) + result;
		}
		return flipped ? "-" + result : result;
	}

	public String karatsuba(String num1, String num2) {
		boolean isNeg = false;
		if (num1.charAt(0) == '-') {
			if (num2.charAt(0) == '-') {
				num2 = num2.substring(1);
			} else {
				isNeg = true;
			}
			num1 = num1.substring(1);
		} else if (num2.charAt(0) == '-') {
			isNeg = true;
			num2 = num2.substring(1);
		}
		return isNeg ? "-" + karatsubaRec(num1, num2) : karatsubaRec(num1, num2);
	}

	private boolean isZero(String num){
		for(int i = 0; i < num.length(); i++)
			if(num.charAt(i) != '0')
				return false;
		return true;
	}

	private String karatsubaRec(String num1, String num2) {
		if (isZero(num1) || isZero(num2))
			return "0";
		if (num1.length() < 2 || num2.length() < 2){
			System.out.println(num1 + " multiplyed by " + num2);
			return multiply(num1, num2);
		}
		int n = Math.max(num1.length(), num2.length());
		int splitPoint = (int) Math.ceil(n/2);
		long diff = num1.length() - num2.length();
		if(diff < 0) {
			diff = -diff;
			num1 = String.join("", Collections.nCopies((int)diff, "0")) + num1;
		} else{
			num2 = String.join("", Collections.nCopies((int)diff, "0")) + num2;
		}

		//Arreglar karatsuba. Funciona cuando uno de los numeros tiene un numero par de digitos
		String high1 = num1.substring(0, splitPoint);
		String low1 = num1.substring(splitPoint);
		String high2 = num2.substring(0, splitPoint);
		String low2 = num2.substring(splitPoint);
		
		String z0 = karatsubaRec(low1, low2);
		String z1 = karatsubaRec(add(low1, high1), add(low2, high2));
		String z2 = karatsubaRec(high1, high2);

		String par1 = z2 + String.join("", Collections.nCopies(2 * splitPoint, "0"));
		String par2 = sub(sub(z1,z2),z0) + String.join("", Collections.nCopies(splitPoint, "0"));
		return add(add(par1,par2),z0);
	}
}
