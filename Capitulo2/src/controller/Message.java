/*
    Algoritmes Avan�ats - Capitulo 2
    Autors:
        Jonathan Salisbury Vega
        Joan Sans� Peric�s
        Joan Vilella Candia
 */
package controller;

/**
 *
 * @author elsho
 */
public class Message {
    
    public static final String DEFAULT = "Select a piece and click a square to place it.\nClick compute to obtain a solution.";
    public static final String COMPUTING = "Computing solution...";
    public static final String SOLUTION_FOUND = "Solution found.";
    public static final String SOLUTION_FORMAT = "Solution found in %f seconds.";
    public static final String STOPPED = "Execution stopped.";
    public static final String NO_SOLUTION_FOUND = "No Solution was found.";
    public static final String COMPUTE_FIRST = "Please Compute a solution before animating";
    public static final String ANIMATING = "Animating solution...";
    public static final String ANIMATION_STOPPED = "Animation stopped.";
    
    public enum Type {
        INFO,
        SUCCESS,
        ERROR
    }
    
    public static String solutionWithTime(double time){
        return String.format(SOLUTION_FORMAT, time);
    }
}
