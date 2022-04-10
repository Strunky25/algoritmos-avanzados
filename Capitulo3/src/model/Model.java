/*
    Algoritmes Avançats - Capitulo 3
    Autors:
        Jonathan Salisbury Vega
        Joan Sansó Pericàs
        Joan Vilella Candia
*/
package model;

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

}
