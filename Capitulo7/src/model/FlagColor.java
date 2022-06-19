/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package model;

import java.awt.Color;
import java.util.ArrayList;

/**
 *
 * @author elsho
 */
public enum FlagColor {
    WHITE("255-255-255#250-250-250#245-245-245#240-240-240#235-235-235"
            + "#230-230-230"),
    BLACK("0-0-0#5-5-5#10-10-10#15-15-15#20-20-20#25-25-25"),
    RED("114-020-034#179-036-040#231-037-018#197-029-052#254-000-000"
            + "#248-000-000#217-080-048#204-006-005#213-048-050#230-050-068"
            + "#179-040-033#234-137-154#211-110-112#161-035-018#193-135-107"
            + "#120-031-025#100-036-036#065-034-039#094-033-041#117-021-030"
            + "#155-017-030#162-035-029#165-032-025#175-043-030"),
    GREEN("000-255-000#005-250-005#010-245-010#028-084-045#049-127-067"
            + "#032-096-061#061-100-045#048-132-070#137-172-118#189-236-182"
            + "#087-166-057#076-145-065#030-089-069#088-114-070#053-104-045"
            + "#045-087-044#040-114-051#049-102-080"),
    BLUE("000-000-255#005-005-250#010-010-245#027-085-131#063-136-143"
            + "#006-057-113#034-113-179#059-131-189#002-086-105#062-095-138"
            + "#030-045-110#032-033-079#042-046-075"),
    YELLOW("255-255-000#250-250-005#245-245-010#250-210-001#243-218-011"
            + "#248-243-053#237-255-033#225-204-079#229-190-001"),
    ORANGE("235-106-014#236-124-038#216-075-032#247-094-037#255-164-032"
            + "#244-070-017#255-117-020#237-118-014");

    private final ArrayList<Color> values;

    private FlagColor(String rgbStrings) {
        values = new ArrayList<>();
        String rgbValues[] = rgbStrings.split("#");
        for (String rgbValue : rgbValues) {
            String[] rgb = rgbValue.split("-");
            values.add(new java.awt.Color(Integer.parseInt(rgb[0]),
                    Integer.parseInt(rgb[1]), Integer.parseInt(rgb[2])));
        }
    }
    
    public double distanceToColor(Color color) {
        double res;
        double min = Double.MAX_VALUE;
        float[] hsb = new float[3];
        float[] hsb2 = new float[3];
        Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), hsb);
        double dh, ds, db;
        for (Color val: values) {
            Color.RGBtoHSB(val.getRed(), val.getGreen(), val.getBlue(), hsb2);
            dh = hsb[0] - hsb2[0];
            ds = hsb[1] - hsb2[1];
            db = hsb[2] - hsb2[2];
            res = Math.sqrt(dh*dh + ds*ds + db*db);
            if (res < min) min = res;
        }
        return min;
    }
    
    @Override
    public String toString(){
        return this.name().toLowerCase();
    }
}
