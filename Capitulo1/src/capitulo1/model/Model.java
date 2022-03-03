/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package capitulo1.model;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author elsho
 */
public class Model {

    public static final String SQRT = "sqrt(n)", LOG = "log(n)", NLOG = "n*log(n)", N2 = "n^2";
    public static final String[] COMPLEXITIES = {SQRT, LOG, NLOG, N2};
    public static final int[] SIZES = {50, 100, 150, 200};

    public void execute(String complexity) {
        //System.out.println(complexity);
        switch (complexity) {
            case SQRT ->
                sqrt();
            case LOG ->
                log();
            case NLOG ->
                nlog();
            case N2 ->
                n2();
        }
    }

    public void sqrt() {
        for (int i = 0; i < SIZES.length; i++) {
            long time = System.nanoTime();
            for (int j = 0; j < (int) Math.sqrt(SIZES[i]); j++) {
                sleep();
            }
            time = System.nanoTime() - time;
            //View.graficar(SIZES[i],time)
        }
    }

    public void log() {
        for (int i = 0; i < SIZES.length; i++) {
            long time = System.nanoTime();
            for (int j = 0; j < (int) Math.log10(SIZES[i]); j++) {
                sleep();
            }
            time = System.nanoTime() - time;
            //View.graficar(SIZES[i],time)
        }
    }

    public void nlog() {
        for (int i = 0; i < SIZES.length; i++) {
            long time = System.nanoTime();
            for (int k = 0; k < SIZES[i]; k++) {
                for (int j = 0; j < (int) Math.log10(SIZES[i]); j++) {
                    sleep();
                }
            }
            time = System.nanoTime() - time;
            //View.graficar(SIZES[i],time)
        }
    }

    public void n2() {
        for (int i = 0; i < SIZES.length; i++) {
            long time = System.nanoTime();
            for (int j = 0; j < SIZES[i]; j++) {
                for(int k = 0; k < SIZES[i];k++){
                sleep();                    
                }
            }
            time = System.nanoTime() - time;
            //View.graficar(SIZES[i],time)
        }
    }

    private void sleep() {
        try {
            Thread.sleep(5);
        } catch (InterruptedException ex) {
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
