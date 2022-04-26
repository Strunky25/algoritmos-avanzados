package model;

/**
 * Caracter
 */
public class Caracter implements Comparable {

    byte value;
    int frecuency;

    public Caracter(byte value, int frecuency) {
        this.value = value;
        this.frecuency = frecuency;
    }

    public byte getValue() {
        return value;
    }

    public void setValue(byte value) {
        this.value = value;
    }

    public int getFrecuency() {
        return frecuency;
    }

    public void setFrecuency(int frecuency) {
        this.frecuency = frecuency;
    }

    @Override
    public int compareTo(Object o) {
        // TODO Auto-generated method stub
        Caracter a = (Caracter) o;
        if (this.frecuency > a.frecuency) {
            return 1;
        } else if (this.frecuency < a.frecuency) {
            return -1;
        } else {
            return 0;
        }
    }
}