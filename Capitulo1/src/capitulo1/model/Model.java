/*
    Algoritmes Avançats - Capitulo 1
    Autors:
        Jonathan Salisbury Vega
        Joan Sansó Pericàs
        Joan Vilella Candia
*/
package capitulo1.model;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingWorker;

/**
 *
 * @author elsho
 */
public class Model {

    public static final String SQRT = "sqrt(n)", LOG = "log(n)", N = "n", NLOG = "n*log(n)", N2 = "n^2";
    public static final String[] COMPLEXITIES = {SQRT, LOG, N, NLOG, N2};
    
    public static final int[] SIZES = {75, 150, 225, 300};
    
    public class Task extends SwingWorker<Void, Long> {
        
        private final String complexity;
        private long start, end;
        
        public Task(String complexity){
            this.complexity = complexity;
        }

        @Override
        protected Void doInBackground() throws Exception {
            setProgress(0);
            for(int n = 0; n < SIZES.length && !isCancelled(); n++){
                switch (complexity) {
                    case SQRT -> sqrt(n);
                    case LOG -> log(n);
                    case N -> n(n);
                    case NLOG -> nlog(n);
                    case N2 -> n2(n);
                }
                firePropertyChange("time", SIZES[n], end - start);
            }
            setProgress(100);
            return null;
        }
        
        public void sqrt(int n) {
            start = System.nanoTime();
            int max = (int) Math.sqrt(SIZES[n]);
            for (int i = 0; i < max; i++) {
                sleep(); 
                setProgress(i*(100/SIZES.length)/max + n*(100/SIZES.length));
            }
            end = System.nanoTime();
        }

        public void log(int n) {
            start = System.nanoTime();
            int max = (int) Math.log10(SIZES[n]);
            for (int i = 0; i < max; i++) {
                sleep();
                setProgress(i*(100/SIZES.length)/max + n*(100/SIZES.length));
            }
            end = System.nanoTime();
        }
        
        public void n(int n){
            start = System.nanoTime();
            for (int i = 0; i < SIZES[n]; i++) {
                sleep();
                setProgress(i*(100/SIZES.length)/SIZES[n] + n*(100/SIZES.length));
            }
            end = System.nanoTime();
        }

        public void nlog(int n) {
            start = System.nanoTime();
            int log = (int) Math.log10(SIZES[n]);
            int max = (SIZES[n]*log);
            for (int i = 0; i < SIZES[n]; i++) {
                for (int j = 0; j < log; j++) {
                    sleep();
                    setProgress((i*log+j)*(100/SIZES.length)/max + n*(100/SIZES.length));
                }
            }
            end = System.nanoTime();
        }

        public void n2(int n) {
            start = System.nanoTime();
            int max = SIZES[n]*SIZES[n];
            for (int i = 0; i < SIZES[n]; i++) {
                for(int j = 0; j < SIZES[n]; j++){
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    setProgress((i*SIZES[n]+j)*(100/SIZES.length)/max + n*(100/SIZES.length));
                }
            }
            end = System.nanoTime();
            //View.graficar(SIZES[i],time)
        }
        
        private void sleep() {
            try {
                Thread.sleep(20);
            } catch (InterruptedException ignore) {
                System.out.println("error");
            }
        }

    }
}
