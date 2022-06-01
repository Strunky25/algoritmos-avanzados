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
    private static int N = 3;

    public static void setN(int N) {
        Model.N = N;
    }

    /* Variables */
    private Node sol;

    /* Methods */
    public void solve(int[][] start, int[][] end, int x, int y, Heuristic heuristica) {
        System.out.println(Arrays.deepToString(start));
        PriorityQueue<Node> pq = new PriorityQueue<>();
        Node root = new Node(start, x, y, x, y, 0, null);
        root.cost = heuristic(start, end, heuristica);
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
                    if (pq.contains(child)) {
                        continue;
                    } else {
                        child.cost = heuristic(child.matrix, end, heuristica);
                        pq.add(child);
                        System.out.println("Movement: " + DX[i] + " " + DY[i]);
                        // System.out.println(Arrays.deepToString(child.matrix));
                        // try {
                        // Thread.sleep(300);
                        // firePropertyChange("Update", null, child.getMatrix());
                        // } catch (InterruptedException ex) {
                        // Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
                        // }
                    }
                }
            }
        }
    }

    public static enum Heuristic {
        INCORRECT_POSITIONS,
        MANHATTAN,
        EUCLIDEAN,
        ADJACENT_TILE_REVERSAL,
        OUT_OF_ROW_OUT_OF_COLUMN
    }

    private int heuristic(int[][] start, int[][] end, Heuristic heuristica) {
        switch (heuristica) {
            case INCORRECT_POSITIONS:
                return incorrectPositions(start, end);
            case MANHATTAN:
                return manhattan(start, end);
            case EUCLIDEAN:
                return euclidean(start, end);
            case ADJACENT_TILE_REVERSAL:
                return adjacentTileReversal(start, end);
            case OUT_OF_ROW_OUT_OF_COLUMN:
                return outOfRowOutOfColumn(start, end);
            default:
                return incorrectPositions(start, end);
        }
    }

    private int incorrectPositions(int[][] start, int[][] end) {
        int count = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (start[i][j] != end[i][j]) {
                    count++;
                }
            }
        }
        // System.out.println(count);
        return count;
    }

    private int manhattan(int[][] start, int[][] end) {
        int count = 0;
        // sum of the distances of each tile from its goal position (manhattan distance)
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (start[i][j] != end[i][j]) {
                    int x = (start[i][j] - 1) / N;
                    int y = (start[i][j] - 1) % N;
                    count += Math.abs(i - x) + Math.abs(j - y);
                }
            }
        }
        // System.out.println(count);
        return count;
    }

    private int euclidean(int[][] start, int[][] end) {
        int count = 0;
        // sum of the distances of each tile from its goal position (euclidean distance)
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (start[i][j] != end[i][j]) {
                    int x = (start[i][j] - 1) / N;
                    int y = (start[i][j] - 1) % N;
                    count += Math.sqrt(Math.pow(i - x, 2) + Math.pow(j - y, 2));
                }
            }
        }
        // System.out.println(count);
        return count;
    }

    // NO SABEMOS SI FUNCIONA, FIXEAR
    private int adjacentTileReversal(int[][] start, int[][] end) {
        int count = 0;
        // Count the number of adjcent tile reversals.
        // An adjacent tile reversal is when two adjacent tiles are in the position of
        // the other tile.
        count = heuristic(start, end, Heuristic.MANHATTAN);
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (start[i][j] != end[i][j]) {
                    for (int k = 0; k < DX.length; k++) {
                        // print boolean variable as string
                        if (isPossible(i + DX[k], j + DY[k])) {
                            if (start[i][j] == end[i + DX[k]][j + DY[k]] && start[i + DX[k]][j + DY[k]] == end[i][j]) {
                                count++;
                            }
                        }
                    }
                }
            }
        }
        return count;
    }

    private int outOfRowOutOfColumn(int[][] start, int[][] end) {
        int count = 0;
        // Number of tiles out of row + Number of tiles out of column
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (start[i][j] != end[i][j]) {
                    if (i != start[i][j] / N) {
                        count++;
                    }
                    if (j != start[i][j] % N) {
                        count++;
                    }
                }
            }
        }
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
            // System.out.println("Should swap x:" + x + " y:" + y + " with x:" + xpos + "
            // y:" + ypos);
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
        public boolean equals(Object node) {
            Node other = (Node) node;
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    if (this.matrix[i][j] != other.matrix[i][j]) {
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
