package model;

public enum Heuristica {
    INCORRECT_POSITIONS,
    MANHATTAN,
    EUCLIDEAN,
    ADJACENT_TILE_REVERSAL,
    OUT_OF_ROW_OUT_OF_COLUMN;

    private static final int[] DX = { 1, 0, -1, 0 };
    private static final int[] DY = { 0, -1, 0, 1 };

    public static int heuristic(int[][] start, int[][] GOAL, Heuristica heuristica) {
        switch (heuristica) {
            case INCORRECT_POSITIONS:
                return incorrectPositions(start, GOAL);
            case MANHATTAN:
                return manhattan(start, GOAL);
            case EUCLIDEAN:
                return euclidean(start, GOAL);
            case ADJACENT_TILE_REVERSAL:
                return adjacentTileReversal(start, GOAL);
            case OUT_OF_ROW_OUT_OF_COLUMN:
                return outOfRowOutOfColumn(start, GOAL);
            default:
                return incorrectPositions(start, GOAL);
        }
    }

    public static int incorrectPositions(int[][] start, int[][] GOAL) {
        int count = 0;
        for (int i = 0; i < GOAL.length; i++) {
            for (int j = 0; j < GOAL.length; j++) {
                if (start[i][j] != GOAL[i][j]) {
                    count++;
                }
            }
        }
        // System.out.println(count);
        return count;
    }

    public static int manhattan(int[][] start, int[][] GOAL) {
        int count = 0;
        // sum of the distances of each tile from its goal position (manhattan distance)
        for (int i = 0; i < GOAL.length; i++) {
            for (int j = 0; j < GOAL.length; j++) {
                if (start[i][j] != GOAL[i][j]) {
                    int x = (start[i][j] - 1) / GOAL.length;
                    int y = (start[i][j] - 1) % GOAL.length;
                    count += Math.abs(i - x) + Math.abs(j - y);
                }
            }
        }
        // System.out.println(count);
        return count;
    }

    public static int euclidean(int[][] start, int[][] GOAL) {
        int count = 0;
        // sum of the distances of each tile from its goal position (euclidean distance)
        for (int i = 0; i < GOAL.length; i++) {
            for (int j = 0; j < GOAL.length; j++) {
                if (start[i][j] != GOAL[i][j]) {
                    int x = (start[i][j] - 1) / GOAL.length;
                    int y = (start[i][j] - 1) % GOAL.length;
                    count += Math.sqrt(Math.pow(i - x, 2) + Math.pow(j - y, 2));
                }
            }
        }
        // System.out.println(count);
        return count;
    }

    public static int adjacentTileReversal(int[][] start, int[][] GOAL) {
        int count = 0;
        // Count the number of adjcent tile reversals.
        // An adjacent tile reversal is when two adjacent tiles are in the position of
        // the other tile.
        count = heuristic(start, GOAL, Heuristica.MANHATTAN);
        for (int i = 0; i < GOAL.length; i++) {
            for (int j = 0; j < GOAL.length; j++) {
                if (start[i][j] != GOAL[i][j]) {
                    for (int k = 0; k < DX.length; k++) {
                        // print boolean variable as string
                        if (Model.isPossible(i + DX[k], j + DY[k])) {
                            if (start[i][j] == GOAL[i + DX[k]][j + DY[k]]
                                    && start[i + DX[k]][j + DY[k]] == GOAL[i][j]) {
                                count++;
                            }
                        }
                    }
                }
            }
        }
        return count;
    }

    public static int outOfRowOutOfColumn(int[][] start, int[][] GOAL) {
        int count = 0;
        for (int i = 0; i < GOAL.length; i++) {
            for (int j = 0; j < GOAL.length; j++) {
                if (start[i][j] != GOAL[i][j]) {
                    if (i != start[i][j] / GOAL.length) {
                        count++;
                    }
                    if (j != start[i][j] % GOAL.length) {
                        count++;
                    }
                }
            }
        }
        return count;
    }
}
