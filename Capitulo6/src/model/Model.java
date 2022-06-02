/*
    Algoritmes Avançats - Capitulo 6
    Autors:
        Jonathan Salisbury Vega
        Joan Sansó Pericàs
        Joan Vilella Candia
        Julián Wallis Medina
 */
package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;

public class Model extends AbstractModel {

    /* Constants */
    private static final int[] DX = {1, 0, -1, 0};
    private static final int[] DY = {0, -1, 0, 1};
    private static int N = 3;

    /* Variables */
    private Node sol;
    private static int[][] GOAL = {{ 0, 1, 2 }, { 3, 4, 5 }, { 6, 7, 8 }};

    /* Methods */
    public static void setN(int N) {
        Model.N = N;
        Model.GOAL = new int[N][N];
        for (int i = 0; i < GOAL.length; i++) {
            for (int j = 0; j < GOAL[0].length; j++) {
                GOAL[i][j] = i * N + j;
            }
        }
    }
    
    public void solve(int[][] start, int x, int y, Heuristica heuristica) {
        System.out.println(Arrays.deepToString(start));
        PriorityQueue<Node> pq = new PriorityQueue<>();
        Node root = new Node(start, x, y, x, y, 0, null);
        root.cost = Heuristica.heuristic(start, GOAL,heuristica);
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
                        child.cost = Heuristica.heuristic(child.matrix, GOAL, heuristica);
                        pq.add(child);
                        System.out.println("Movement: " + DX[i] + " " + DY[i]);
                    }
                }
            }
        }
    }


    public ArrayList<Node> getSolution() {
        ArrayList<Node> moves = new ArrayList<>();
        while (sol != null) {
            moves.add(sol);
            sol = sol.getParent();
        }
        return moves;
    }

    public static boolean isPossible(int x, int y) {
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