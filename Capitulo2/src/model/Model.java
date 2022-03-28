/*
    Algoritmes Avançats - Capitulo 2
    Autors:
        Jonathan Salisbury Vega
        Joan Sansó Pericàs
        Joan Vilella Candia
*/
package model;

import model.chesspieces.Chesspiece;

/**
 * Class that contains the methods that simulate the various complexities.
 */
public class Model {
    
    /* Constants */
    
    /* Variables */
    private int[][] board;
    private int maxMoves, pieceMoves;
    private int[] pieceDx, pieceDy;
    private boolean stop, solution;
    private long start, end;
    

    public boolean solve(Chesspiece piece, int x, int y){
        this.pieceDx = piece.getDx();
        this.pieceDy = piece.getDy();
        this.pieceMoves = pieceDx.length;
        this.board[x][y] = 0;
        start = System.nanoTime();
        solution = recursiveSolve(x, y, 1);
        end = System.nanoTime();
        return solution;
    }
    
    private boolean recursiveSolve(int x, int y, int move){
        int nextX, nextY;
        if(stop) return false;
        if(move == maxMoves) return true;
        for (int i = 0; i < pieceMoves; i++) {
            nextX = x + pieceDx[i];
            nextY = y + pieceDy[i];
            if(isPossibleMove(nextX, nextY)){
                board[nextX][nextY] = move;
                if(recursiveSolve(nextX, nextY, move + 1)) return true;
                else board[nextX][nextY] = -1;
            }  
        }
        return false;
    }
    
    private boolean isPossibleMove(int x, int y){
        return (x >= 0 && x < board.length && y >= 0 && y < board.length && board[x][y] == -1);
    }
    
    public void setBoardSize(int size){
        this.board = new int[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                board[i][j] = -1;
            }
        }
        maxMoves = size * size;
    }
    
    public int[][] getBoard(){
        return this.board;
    }
    
    public void stop(){this.stop = true;}
    
    public void start(){this.stop = false;}

    public boolean isStopped() {
        return this.stop;
    }
    
    public double getTime(){
        return (end - start)/1000000000.0; // seconds
    }
}
