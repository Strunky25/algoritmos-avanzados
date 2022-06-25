/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package model;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author elsho
 */
public enum FlagColor {

        BLACK, WHITE, RED, RED_ORANGE, ORANGE, YELLOW_ORANGE, YELLOW, YELLOW_GREEN, GREEN, BLUE_GREEN, BLUE, BLUE_VIOLET, VIOLET, VIOLET_RED;


        // public double distanceToColor(Color color) { // https://stackoverflow.com/questions/35113979/calculate-distance-between-colors-in-hsv-space
        //         double res;
        //         double min = Double.MAX_VALUE;
        //         float[] hsb = new float[3];
        //         float[] hsb2 = new float[3];
        //         Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), hsb);
        //         // System.out.println(Arrays.toString(hsb));
        //         double dh, ds, db;
        //         for (Color val : values) {
        //                 Color.RGBtoHSB(val.getRed(), val.getGreen(), val.getBlue(), hsb2);
        //                 dh = Math.min(Math.abs(hsb2[0] - hsb[0]), 360 - Math.abs(hsb2[0] - hsb[0]));
        //                 ds = Math.abs(hsb2[1] - hsb[1]);
        //                 db = Math.abs(hsb2[2] - hsb[2]);
        //                 res = Math.sqrt(dh * dh + ds * ds + db * db);
        //                 if (res < min)
        //                         min = res;
        //         }
        //         return min;
        // }

        @Override
        public String toString() {
                return this.name().toLowerCase();
        }
}
