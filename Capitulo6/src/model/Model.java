/*
    Algoritmes Avançats - Capitulo 6
    Autors:
        Jonathan Salisbury Vega
        Joan Sansó Pericàs
        Joan Vilella Candia
 */
package model;

import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class that contains the methods that simulate the various complexities.
 */
public class Model extends AbstractModel {

    /* Constants */
    private static final int[] DX = {1, 0, -1, 0};
    private static final int[] DY = {0, -1, 0, 1};
    private static final int N = 3;

    /* Variables */
    private Node sol;

    /* Methods */
    public boolean isSolvable(int[][] matrix) {
        int count = 0;
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                if (matrix[j][i] > 0 && matrix[j][i] > matrix[i][j]) {
                    count++;
                }
            }
        }
        return count % 2 == 0;
    }

    public void solve(int[][] start, int[][] end, int x, int y) {
        System.out.println(Arrays.deepToString(start));
        PriorityQueue<Node> pq = new PriorityQueue<>();
        Node root = new Node(start, x, y, x, y, 0, null);
        root.cost = heuristic(start, end);
        pq.add(root);
        while (!pq.isEmpty()) {
            Node min = pq.poll();
            if (min.cost == 0) {
                System.out.println(Arrays.deepToString(min.matrix));
                sol = min;
                return;
            }
            for (int i = 0; i < DX.length; i++) {
                if (isPossible(min.x + DX[i], min.y + DY[i])) {
                    Node child = new Node(min.matrix, min.x, min.y, min.x + DX[i], min.y + DY[i], min.level + 1, min);
                    if(pq.contains(child)){
                        continue;
                    } else{
                        child.cost = heuristic(child.matrix, end);
                        pq.add(child);
                        // System.out.println("Movement: "+DX[i]+" "+DY[i]);
                        // System.out.println(Arrays.deepToString(child.matrix));
                        // try {
                        //     Thread.sleep(10000);
                        //     firePropertyChange("Update", null, child.getMatrix());
                        // } catch (InterruptedException ex) {
                        //     Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
                        // }    
                    }
                }
            }
            System.out.println("\n");
        }
    }

    private int heuristic(int[][] start, int[][] end) {
        int count = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (start[i][j] != end[i][j]) {
                    count++;
                }
            }
        }
        //System.out.println(count);
        return count;
    }

    private int heuristic2(int[][] start, int[][] end) {
        int count = 0;
        //sum of the distances of each tile from its goal position (manhattan distance)
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (start[i][j] != end[i][j]) {
                    int x = 0, y = 0;
                    while (start[i][j] != end[i][j]) {
                        x = end[i][j];
                        y = end[i][j];
                    }
                    count += Math.abs(i - x) + Math.abs(j - y);
                }
            }
        }
        return count;
    }

    private int heuristic3(int[][] start, int[][] end) {
        int count = 0;
        //sum of the distances of each tile from its goal position (euclidean distance)
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (start[i][j] != end[i][j]) {
                    int x = i, y = j;
                    while (start[x][y] != end[x][y]) {
                        x = end[x][y];
                        y = end[x][y];
                    }
                    count += Math.sqrt(Math.pow(i - x, 2) + Math.pow(j - y, 2));
                }
            }
        }
        return count;
    }

    private int heuristic4(int[][] start, int[][] end) {
        int count = 0;
        //take into account the number of direct adjacent tile reversals 
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (start[i][j] != end[i][j]) {
                    int x = i, y = j;
                    while (start[x][y] != end[x][y]) {
                        x = end[x][y];
                        y = end[x][y];
                    }
                    if (x == i) {
                        if (y < j) {
                            count++;
                        } else {
                            count--;
                        }
                    } else {
                        if (x < i) {
                            count++;
                        } else {
                            count--;
                        }
                    }
                }
            }
        }
        return count;
    }

    private int heuristic5(int[][] start, int[][] end) {
        int count = 0;
        // Number of tiles out of row + Number of tiles out of column
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (start[i][j] != end[i][j]) {
                    int x = i, y = j;
                    while (start[x][y] != end[x][y]) {
                        x = end[x][y];
                        y = end[x][y];
                    }
                    if (x != i) {
                        count++;
                    }
                    if (y != j) {
                        count++;
                    }
                }
            }
        }
        return count;
    }

    private int heuristic6(int[][] start, int[][] end) {
        int count = 0;
        return count;
    }

    public Node getSolution() {
        return this.sol;
    }

    private boolean isPossible(int x, int y) {
        return (x >= 0 && x < N && y >= 0 && y < N);
    }

    public static class Node implements Comparable<Node> {

        private final Node parent;
        private int[][] matrix;
        private int x, y, cost, level;

        public Node(int[][] mat, int x, int y, int xpos, int ypos, int lvl, Node parent) {
            this.parent = parent;
            this.matrix = new int[mat.length][];
            for (int i = 0; i < mat.length; i++) {
                this.matrix[i] = mat[i].clone();
            }
            
            
            int temp = matrix[y][x];
            matrix[y][x] = matrix[ypos][xpos];
            matrix[ypos][xpos] = temp;

            // System.out.println("Original:\t"+Arrays.toString(mat[0]));
            // System.out.println("\t\t"+Arrays.toString(mat[1]));
            // System.out.println("\t\t"+Arrays.toString(mat[2]));
            // System.out.println("Should swap x:" + x + " y:" + y + " with x:" + xpos + " y:" + ypos);
            // System.out.println("Swapped:\t"+Arrays.toString(matrix[0]));
            // System.out.println("\t\t"+Arrays.toString(matrix[1]));
            // System.out.println("\t\t"+Arrays.toString(matrix[2])+"\n\n");

            this.x = xpos;
            this.y = ypos;
            this.cost = Integer.MAX_VALUE;
            this.level = lvl;
        }

        @Override
        public int compareTo(Node node) {
            return (this.cost + this.level) - (node.cost + node.level);
        }
        @Override
        public boolean equals(Object node){
            Node other = (Node) node;
            for(int i = 0; i < N; i++){
                for(int j = 0; j < N; j++){
                    if(this.matrix[i][j] != other.matrix[i][j]){
                        return false;
                    }
                }
            }
            return true;
        }

        public int[][] getMatrix() {
            return matrix;
        }

        public Node getParent() {
            return parent;
        }
    }
}
