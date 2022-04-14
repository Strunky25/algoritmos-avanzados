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
	//remove leading zeros: possible formats: 0000025 or -000005894
	private String removeLeadingZeros(String num){
		boolean neg = false;
		if(num.charAt(0) == '-'){
			num = num.substring(1);
			neg = true;
		}
		int i = 0;
		while(i < num.length() && num.charAt(i) == '0'){
			i++;
		}
		if(i == num.length()){
			return "0";
		}
		return neg ? "-" + num.substring(i) : num.substring(i);
	}

	public String karatsuba(String num1, String num2) {
		// addTest();
		// subTest();
		//Quitamos los zeros de delante por si el usuario escribe 001 o 0001
		num1 = removeLeadingZeros(num1);
		num2 = removeLeadingZeros(num2);

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
		BigInteger n1 = new BigInteger(num1);
		BigInteger n2 = new BigInteger(num2);
		BigInteger result = n1.multiply(n2);
		if (isNeg)
			result = result.negate();
		System.out.println("Expected: "+result);

		//Fix para que funcione para numeros de distintas longitudes:
		/*
		Básicamente añadimos ceros a la derecha para que sean dos numeros del mismo tamaño
		Al final, despues de hacer la multiplicacion, eliminamos la misma cantidad de ceros
		que habiamos añadido al principio para que el resultado sea el esperado.
		*/
		int diff = num1.length() - num2.length();
		if (diff > 0) {
			num2 = num2 + String.join("", Collections.nCopies(diff, "0"));
		} else if (diff < 0) {
			num1 = num1 + String.join("", Collections.nCopies(-diff, "0"));
		}
		String res = isNeg ? "-" + karatsubaRec(num1, num2) : karatsubaRec(num1, num2);
		res = res.substring(0,res.length()-Math.abs(diff));
		System.out.println("Result: "+res);
		if(res.equals(result.toString()))
			System.out.println("Karatsuba: Test passed");
		return res;
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
			return Integer.parseInt(num1) * Integer.parseInt(num2) + "";
		}
		int num1Size = num1.length();
		int num2Size = num2.length();
		int splitPoint = Math.max(num1Size,num2Size) /2;
		int splitNum1 = num1Size - splitPoint;
		int splitNum2 = num2Size - splitPoint;

		//Arreglar karatsuba. Funciona cuando uno de los numeros tiene un numero par de digitos
		String a = num1.substring(0, splitNum1);
		String b = num1.substring(splitNum1);
		String c = num2.substring(0, splitNum2);
		String d = num2.substring(splitNum2);
		
		String ac = karatsubaRec(a, c);
		String bd = karatsubaRec(b, d);
		String adbc = karatsubaRec(add(a, b), add(c, d));
		String adbc_ = sub(adbc, ac);
		String adbc__ = sub(adbc_, bd);
		String part1 = ac + String.join("", Collections.nCopies(splitPoint*2, "0"));
		String part2 = adbc__ + String.join("", Collections.nCopies(splitPoint, "0"));
		part2 = add(part2, bd);
		return add(part1, part2);
	}
}
