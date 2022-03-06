/*
    Algoritmes Avançats - Capitulo 1
    Autors:
        Jonathan Salisbury Vega
        Joan Sansó Pericàs
        Joan Vilella Candia
*/
package capitulo1.model;

import javax.swing.SwingWorker;

/**
 *
 * @author elsho
 */
public class Model {

    public static final String SQRT = "sqrt(n)", LOG = "log(n)", NLOG = "n*log(n)", N2 = "n^2";
    public static final String[] COMPLEXITIES = {SQRT, LOG, NLOG, N2};
    
    public static final int[] SIZES = {3000, 6000, 9000, 12000};
    
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
                    case NLOG -> nlog(n);
                    case N2 -> n2(n);
                }
                firePropertyChange("time", SIZES[n]/40, end - start);
            }
            setProgress(100);
            return null;
        }
        
        public void sqrt(int n) {
            start = System.nanoTime();
            int max = (int) Math.sqrt(SIZES[n]);
            System.out.println(max);
            for (int i = 0; i < max; i++) {
                sleep(); 
                setProgress(i*(100/SIZES.length)/max + n*(100/SIZES.length));
            }
            end = System.nanoTime();
        }

        public void log(int n) {
            start = System.nanoTime();
            int max = (int) Math.log10(n);
            for (int i = 0; i < max; i++) {
                sleep();
                setProgress(i*(100/SIZES.length)/max + n*(100/SIZES.length));
            }
            end = System.nanoTime();
     
        }

        public void nlog(int n) {
            start = System.nanoTime();
            int max = (int) ((int) n*Math.log10(n));
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < (int) Math.log10(n); j++) {
                    sleep();
                    setProgress(i*(100/SIZES.length)/max + n*(100/SIZES.length));
                }
            }
            end = System.nanoTime();
        }

        public void n2(int n) {
            start = System.nanoTime();
            int max = n*n;
            for (int i = 0; i < n; i++) {
                for(int j = 0; j < n; j++){
                    sleep();  
                    setProgress((1*n+j)*(100/SIZES.length)/max + n*(100/SIZES.length));
                }
            }
            end = System.nanoTime();
            //View.graficar(SIZES[i],time)
        }
        
        private void sleep() {
            try {
                Thread.sleep(100);
            } catch (InterruptedException ignore) {
                System.out.println("error");}
        }

    }
}
