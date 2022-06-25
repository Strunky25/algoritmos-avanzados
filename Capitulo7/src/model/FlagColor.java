/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package model;

import java.awt.Color;

/**
 *
 * @author elsho
 */
public enum FlagColor {

    BLACK,
    WHITE,
    RED(346, 15),
    ORANGE(16, 45),
    YELLOW(46, 75),
    YELLOW_GREEN(76, 105),
    LIGHT_GREEN(106, 135),
    GREEN(136, 165),
    CYAN(166, 195),
    BLUE(196, 225),
    DARK_BLUE(226, 255),
    VIOLET(256, 285),
    MAGENTA(286, 315),
    FUCSIA(316, 345);

    private int min, max;

    private FlagColor() {
    }

    private FlagColor(int min, int max) {
        this.min = min;
        this.max = max;
    }

    public static int getColor(Color color) {
        int res = -1;
        float[] hsb = new float[3];
        Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), hsb);
        if(hsb[2] <= 0.1){
            return 0; //return black
        } else if(hsb[2] > 0.9 && hsb[1] <= 0.1){
            return 1; //return white
        }
        FlagColor[] values = FlagColor.values();
        hsb[0] *= 360;
        int hue = Math.round(hsb[0]);
        for(int i = 0; i < values.length; i++) {
            if(values[i].equals(WHITE) || values[i].equals(BLACK)) continue;
            if((values[i].min > values[i].max) && (hue <= values[i].max || hue >= values[i].min))res = i;
            else if(hue >= values[i].min && hue <= values[i].max) res = i;
        }
        if(res == -1){
            System.out.println(hue);
        }
        return res;
    }

    @Override
    public String toString() {
        String lower = this.name().toLowerCase();
        return lower.replace("_", " ");
    }
}
