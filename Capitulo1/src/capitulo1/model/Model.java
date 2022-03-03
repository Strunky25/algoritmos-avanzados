/*
 * Clici nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Clici nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.iava to edit this template
 */
package capitulo1.model;

import java.lang.System.Logger;
import java.lang.System.Logger.Level;
import javax.swing.SwingWorker;

/**
 *
 * @author elsho
 */
public class Model {

    public static final String SQRT = "sqrt(n)", LOG = "log(n)", NLOG = "n*log(n)", N2 = "n^2";
    public static final String[] COMPLEXITIES = {SQRT, LOG, NLOG, N2};
    
    public static final int[] SIZES = {50, 100, 150, 200};

    public class Task extends SwingWorker<Void, Void> {
        
        private final String complexity;
        
        public Task(String complexity){
            this.complexity = complexity;
        }

        @Override
        protected Void doInBackground() throws Exception {
            switch (complexity) {
                case SQRT -> sqrt();
                case LOG -> log();
                case NLOG -> nlog();
                case N2 -> n2();
            }
            return null;
        }

        public void sqrt() {
            setProgress(0);
            for(int n = 0; n < SIZES.length; n++){
                long time = System.nanoTime();
                int max = (int) Math.sqrt(SIZES[n]);
                for (int i = 0; i < max && !isCancelled(); i++) {
                    sleep(); 
                    setProgress(i*25/max + n*25);
                }
                time = System.nanoTime() - time;
                //View.graficar(SIZES[i],time)
            }
            setProgress(100);
        }

        public void log() {
            for(int n: SIZES){
                long time = System.nanoTime();
                for (int i = 0; i < (int) Math.log10(n); i++) {
                    sleep();
                }
                time = System.nanoTime() - time;
                //View.graficar(SIZES[i],time)
            }
        }

        public void nlog() {
            for(int n: SIZES){
                long time = System.nanoTime();
                for (int i = 0; i < n; i++) {
                    for (int j = 0; j < (int) Math.log10(n); j++) {
                        sleep();
                    }
                }
                time = System.nanoTime() - time;
                //View.graficar(SIZES[i],time)
            }
        }

        public void n2() {
            for(int n: SIZES){
                long time = System.nanoTime();
                for (int i = 0; i < n; i++) {
                    for(int j = 0; j < n; j++){
                    sleep();                    
                    }
                }
                time = System.nanoTime() - time;
                //View.graficar(SIZES[i],time)
            }
        }
        
        private void sleep() {
            try {
                Thread.sleep(750);
            } catch (InterruptedException ignore) {}
        }

    }
}
