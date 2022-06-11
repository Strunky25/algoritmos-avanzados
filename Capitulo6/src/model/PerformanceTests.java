package model;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import view.View;

public class PerformanceTests {

    private int NTESTS = 96;
    private int MAX_SIZE = 4;
    private Model m;
    private View v;

    public PerformanceTests(Model m, View v) {
        this.m = m;
        this.v = v;
    }

    public PerformanceTests(Model m, View v, int n) {
        NTESTS = n;
        this.m = m;
        this.v = v;
    }

    public void start() {
        Heuristic[] heuristics = { Heuristic.INCORRECT_POSITIONS, Heuristic.MANHATTAN, Heuristic.EUCLIDEAN,
                Heuristic.ADJACENT_TILE_REVERSAL, Heuristic.OUT_OF_ROW_OUT_OF_COLUMN };
        for (int i = 3; i <= MAX_SIZE; i++) {
            for (Heuristic h : heuristics) {
                System.out.println("Solving for size " + i + " with heuristic " + h.name());
                try {
                    FileWriter fw = new FileWriter("performanceTests/" + i + h.toString() + ".csv");
                    BufferedWriter bw = new BufferedWriter(fw);
                    m.setN(i);
                    if(i==3) NTESTS = 70;
                    if(i==4) NTESTS = 20;
                    for (int j = 0; j < NTESTS; j++) {
                        System.out.println("Test " + j+"/"+NTESTS);
                        v.setN(i);
                        v.shuffle();
                        int[][] startPos = v.getOrder();
                        int[] pos = v.getPos();
                        long time = System.nanoTime();
                        m.solve(startPos, pos[1], pos[0], Heuristic.INCORRECT_POSITIONS);
                        time = System.nanoTime() - time;
                        bw.write(time + ",");
                    }
                    bw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
