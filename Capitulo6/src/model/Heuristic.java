package model;

public enum Heuristic {
    INCORRECT_POSITIONS,
    MANHATTAN,
    EUCLIDEAN,
    ADJACENT_TILE_REVERSAL,
    OUT_OF_ROW_OUT_OF_COLUMN;

    public int calculate(int[][] matrix) {
        return switch (this) {
            case INCORRECT_POSITIONS -> incorrectPositions(matrix);
            case MANHATTAN -> manhattan(matrix);
            case EUCLIDEAN -> euclidean(matrix);
            case ADJACENT_TILE_REVERSAL -> adjacentTileReversal(matrix);
            case OUT_OF_ROW_OUT_OF_COLUMN -> outOfRowOutOfColumn(matrix);
            default -> incorrectPositions(matrix);
        };
    }

    private int incorrectPositions(int[][] matrix) {
        int count = 0;
        for (int i = 0; i < Model.GOAL.length; i++) {
            for (int j = 0; j < Model.GOAL.length; j++) {
                if (matrix[i][j] != Model.GOAL[i][j]) {
                    count++;
                }
            }
        }
        // System.out.println(count);
        return count;
    }

    private int manhattan(int[][] matrix) {
        int count = 0;
        // sum of the distances of each tile from its goal position (manhattan distance)
        for (int i = 0; i < Model.GOAL.length; i++) {
            for (int j = 0; j < Model.GOAL.length; j++) {
                if (matrix[i][j] != Model.GOAL[i][j]) {
                    int x = (matrix[i][j] - 1) / Model.GOAL.length;
                    int y = (matrix[i][j] - 1) % Model.GOAL.length;
                    count += Math.abs(i - x) + Math.abs(j - y);
                }
            }
        }
        // System.out.println(count);
        return count;
    }

    private int euclidean(int[][] matrix) {
        int count = 0;
        // sum of the distances of each tile from its goal position (euclidean distance)
        for (int i = 0; i < Model.GOAL.length; i++) {
            for (int j = 0; j < Model.GOAL.length; j++) {
                if (matrix[i][j] != Model.GOAL[i][j]) {
                    int x = (matrix[i][j] - 1) / Model.GOAL.length;
                    int y = (matrix[i][j] - 1) % Model.GOAL.length;
                    count += Math.sqrt(Math.pow(i - x, 2) + Math.pow(j - y, 2));
                }
            }
        }
        // System.out.println(count);
        return count;
    }

    public static int adjacentTileReversal(int[][] matrix) {
        int count = 0;
        // Count the number of adjcent tile reversals.
        // An adjacent tile reversal is when two adjacent tiles are in the position of
        // the other tile.
        count = MANHATTAN.calculate(matrix);
        for (int i = 0; i < Model.GOAL.length; i++) {
            for (int j = 0; j < Model.GOAL.length; j++) {
                if (matrix[i][j] != Model.GOAL[i][j]) {
                    for (int k = 0; k < Model.DX.length; k++) {
                        // print boolean variable as string
                        if (Model.isPossible(i + Model.DX[k], j + Model.DY[k])) {
                            if (matrix[i][j] == Model.GOAL[i + Model.DX[k]][j + Model.DY[k]]
                                    && matrix[i + Model.DX[k]][j + Model.DY[k]] == Model.GOAL[i][j]) {
                                count++;
                            }
                        }
                    }
                }
            }
        }
        return count;
    }

    private int outOfRowOutOfColumn(int[][] matrix) {
        int count = 0;
        for (int i = 0; i < Model.GOAL.length; i++) {
            for (int j = 0; j < Model.GOAL.length; j++) {
                if (matrix[i][j] != Model.GOAL[i][j]) {
                    if (i != matrix[i][j] / Model.GOAL.length) {
                        count++;
                    }
                    if (j != matrix[i][j] % Model.GOAL.length) {
                        count++;
                    }
                }
            }
        }
        return count;
    }
}
