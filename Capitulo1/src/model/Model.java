/*
    Algoritmes Avançats - Capitulo 1
    Autors:
        Jonathan Salisbury Vega
        Joan Sansó Pericàs
        Joan Vilella Candia
*/
package model;

import javax.swing.SwingWorker;

/**
 * Class that contains the methods that simulate the various complexities.
 */
public class Model {
    
    /* Constants */
    public static final String  LOG = "log(n)", SQRT = "sqrt(n)", N = "n", NLOG = "n*log(n)", N2 = "n^2";
    public static final String[] COMPLEXITIES = {LOG, SQRT, N, NLOG, N2};
    
    public static final int[] SIZES = {1,2,3,4,5,6,7,8,9,10};//{2, 4, 6, 8, 10, 12};
    
    /**
     * Inner class that performs the simulations in another thread.
     */
    public class Task extends SwingWorker<Void, Long> {
        
        private final String complexity;
        private long start, end;
        
        public Task(String complexity){
            this.complexity = complexity;
        }

        @Override
        protected Void doInBackground() throws Exception {
            setProgress(0);
            for(int n = 0; n < SIZES.length; n++){
                if(isCancelled()){
                    return null;
                }
                switch (complexity) {
                    case LOG -> log(n);
                    case SQRT -> sqrt(n);
                    case N -> n(n);
                    case NLOG -> nlog(n);
                    case N2 -> n2(n);
                }
                double x = (double)(n + 1)/SIZES.length; // X escalada a tamaño de muestras
                double y = ((end - start)/10000000); // 10^7
               // System.out.println("time: " + y);
                firePropertyChange("point", x, y);
            }
            setProgress(100);
            return null;
        }

        public void log(int n) {
            start = System.nanoTime();
            double max = (Math.log(SIZES[n])/Math.log(2));
            for (int i = 0; i < max; i++) {
                sleep();
                setProgress((int)(i*(100/SIZES.length)/max + n*(100/SIZES.length)));
            }
            end = System.nanoTime();
        }
        
        public void sqrt(int n) {
            start = System.nanoTime();
            double max = Math.sqrt(SIZES[n]);
            for (int i = 0; i < max; i++) {
                sleep(); 
                setProgress((int)(i*(100/SIZES.length)/max + n*(100/SIZES.length)));
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
            double log = Math.log(SIZES[n])/Math.log(2);
            int max = (int) (SIZES[n]*log);
            for (int i = 0; i < SIZES[n]; i++) {
                for (int j = 0; j < log; j++) {
                    sleep();
                    setProgress((int)(i*log+j)*(100/SIZES.length)/max + n*(100/SIZES.length));
                }
            }
            end = System.nanoTime();
        }

        public void n2(int n) {
            start = System.nanoTime();
            int max = SIZES[n]*SIZES[n];
            for (int i = 0; i < SIZES[n]; i++) {
                for(int j = 0; j < SIZES[n]; j++){
                    sleep();
                    setProgress((i*SIZES[n]+j)*(100/SIZES.length)/max + n*(100/SIZES.length));
                }
            }
            end = System.nanoTime();
            //View.graficar(SIZES[i],time)
        }
        
        private void sleep() {
            try {
                Thread.sleep(40);
            } catch (InterruptedException ignore) {}
        }

    }
}
