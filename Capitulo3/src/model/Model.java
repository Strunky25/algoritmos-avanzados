/*
    Algoritmes Avançats - Capitulo 3
    Autors:
        Jonathan Salisbury Vega
        Joan Sansó Pericàs
        Joan Vilella Candia
 */
package model;

import java.util.Collections;
import java.util.Random;

/**
 * Class that contains the methods that simulate the various complexities.
 */
public class Model {

    /* Constants */
    public static final int N_TESTS = 900;
    public static final int cache = 0; // quitar o poner si al cambiar NTESTS y recompilar no actualiza
    private static final Random random = new Random();

    /* Variables */
    public static boolean tested = false;
    public static int[] classic = new int[N_TESTS - 1];
    private static int nMix = 350;
    private long startTime, endTime;

    /* Methods */
    public void setNMix(int nMix) {
        this.nMix = nMix;
    }

    public String multiply(String num1, String num2) {
        startTime = System.nanoTime();
        /*
         * Comprobaciones iniciales: si los numeros difieren en signo, significa que
         * eñ resultado va a ser negativo, asi que guardamos esa informacion y quitamos
         * los signos negativos a los numeros si los tienen.
         */
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
        // Guardamos en n1 el numero mayor (en longitud) y en n2 el menor
        String n1 = num1.length() > num2.length() ? num1 : num2;
        String n2 = num1.length() > num2.length() ? num2 : num1;
        // Inicializamos el resultado parcial a 0
        String result = "0";
        // Invertimos los numeros para que sea mas facil multiplicar
        n1 = new StringBuilder(n1).reverse().toString();
        n2 = new StringBuilder(n2).reverse().toString();
        // Hacemos la multiplicacion digito a digito
        int carry, dig1, dig2, aux;
        for (int i = 0; i < n2.length(); i++) {
            carry = 0;
            dig1 = Character.getNumericValue(n2.charAt(i));
            String partialRes = "";
            for (int j = 0; j < n1.length(); j++) {
                dig2 = Character.getNumericValue(n1.charAt(j));
                aux = dig1 * dig2 + carry;
                carry = aux / 10;
                aux = aux % 10;
                partialRes = String.valueOf(aux) + partialRes;
            }
            // Hacemos la suma de este nivel, añadiendo "i" zeros como en
            // la multiplicacion clasica
            if (carry > 0) {
                partialRes = String.valueOf(carry) + partialRes;
            }
            result = add(result, partialRes + String.join("", Collections.nCopies(i, "0")));
        }
        // Si el resultado tenia que ser negativo, le añadimos el signo.
        String res = isNeg ? "-" + result : result;
        endTime = System.nanoTime();
        return res;
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
        if (carry > 0) {
            result = String.valueOf(carry) + result;
        }
        return result;
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
            } else {
                carry = 0;
            }
            result = String.valueOf(aux) + result;
        }
        return flipped ? "-" + result : result;
    }

    /**
     *
     * @param num1  a string representing a number
     * @param num2  a string representing a number
     * @param mixed if true, will use classic multiplication when its more
     *              efficient to do so
     * @return the product of num1 and num2
     *
     */
    public String karatsuba(String num1, String num2, boolean mixed) {
        startTime = System.nanoTime();
        // Quitamos los zeros de delante por si el usuario escribe 001 o 0001
        num1 = removeLeadingZeros(num1);
        num2 = removeLeadingZeros(num2);
        // Comprobamos si el resultado va a ser positivo o negativo dependiendo
        // de si tienen el mismo signo o no.
        // Si tienen el mismo signo, el resultado va a ser positivo
        // Si no, el resultado va a ser negativo
        // En el caso de que un numero sea negativo, le quitamos el signo para poder
        // hacer la multiplicacion (haremos multiplicacion positiva siempre) y
        // asignaremos el signo al final
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
        // Si los numeros diferian en signo, ponemos el signo negativo en el resultado,
        // sino, no
        String resultado = isNeg ? "-" + karatsubaRec(num1, num2, mixed) : karatsubaRec(num1, num2, mixed);
        endTime = System.nanoTime();
        return resultado;
    }

    private String karatsubaRec(String num1, String num2, boolean mixed) {
        if (mixed && (num1.length() < nMix || num2.length() < nMix)) {
            return multiply(num1, num2);
        } else if (num1.length() < 2 || num2.length() < 2) {
            return multiply(num1,num2);
        }

        int num1Size = num1.length();
        int num2Size = num2.length();
        int max = Math.max(num1Size, num2Size);
        int splitPoint = max / 2;
        if(splitPoint*2 != max){
            splitPoint++;
        }
        //Rellenamos de zeros para que tengan la misma longitud
        num1 = String.join("", Collections.nCopies(2*splitPoint-num1Size, "0")) + num1;
        num2 = String.join("", Collections.nCopies(2*splitPoint-num2Size, "0")) + num2;

        //Partimos los numeros en dos partes
        String a = num1.substring(0, splitPoint);
        String b = num1.substring(splitPoint);
        String c = num2.substring(0, splitPoint);
        String d = num2.substring(splitPoint);
        //Hacemos las multiplicaciones de karatsuba
        String ac = karatsubaRec(a, c, mixed);
        String bd = karatsubaRec(b, d, mixed);
        //Juntando soluciones y añadiendo los zeros a las partes que van a la parte alta del numero
        String abcd_ac_bd = sub(sub(karatsubaRec(add(a,b),add(c,d),mixed),ac),bd);
        String part1 = ac + String.join("", Collections.nCopies(2*splitPoint, "0"));
        String part2 = abcd_ac_bd + String.join("", Collections.nCopies(splitPoint, "0"));
        String res = add(add(part1,part2),bd);
        //Quitamos los zeros de delante que se habian añadido
        res = removeLeadingZeros(res);
        return res;
    }

    public boolean isSmaller(String num1, String num2) {
        int n1 = num1.length(), n2 = num2.length();
        if (n1 < n2) {
            return true;
        }
        if (n2 < n1) {
            return false;
        }

        for (int i = 0; i < n1; i++) {
            if (num1.charAt(i) < num2.charAt(i)) {
                return true;
            } else if (num1.charAt(i) > num2.charAt(i)) {
                return false;
            }
        }
        return false;
    }

    // remove leading zeros: possible formats: 0000025 or -000005894
    private String removeLeadingZeros(String num) {
        boolean neg = false;
        if (num.charAt(0) == '-') {
            num = num.substring(1);
            neg = true;
        }
        int i = 0;
        while (i < num.length() && num.charAt(i) == '0') {
            i++;
        }
        if (i == num.length()) {
            return "0";
        }
        return neg ? "-" + num.substring(i) : num.substring(i);
    }

    private boolean isZero(String num) {
        if (num.charAt(0) == '-') {
            num = num.substring(1);
        }
        for (int i = 0; i < num.length(); i++) {
            if (num.charAt(i) != '0') {
                return false;
            }
        }
        return true;
    }

    public String generateNumber(int lenght) {
        String num = "";
        num += (random.nextInt(9) + 1) + "";
        for (int i = 0; i < lenght - 1; i++) {
            num += (random.nextInt(10)) + "";
        }
        return num;
    }

    public long[] calculateN(int n) {
        long timeTrad, timeKara, timeMix;
        String num1 = generateNumber(n);
        String num2 = generateNumber(n);

        timeTrad = System.nanoTime();
        String resTrad = multiply(num1, num2);
        timeTrad = System.nanoTime() - timeTrad;

        num1 = generateNumber(n);
        num2 = generateNumber(n);
        timeKara = System.nanoTime();
        String resKara = karatsuba(num1, num2, false);
        timeKara = System.nanoTime() - timeKara;

        num1 = generateNumber(n);
        num2 = generateNumber(n);
        timeMix = System.nanoTime();
        String resMix = karatsuba(num1, num2, true);
        timeMix = System.nanoTime() - timeMix;

        return new long[] { timeTrad, timeKara, timeMix };
    }

    public double getTime() {
        return (endTime - startTime) / 1000000000.0;
    }
}
