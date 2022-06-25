/*
    Algoritmes Avançats - Capitulo 7
    Autors:
        Jonathan Salisbury Vega
        Joan Sansó Pericàs
        Joan Vilella Candia
 */
package model;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 * Class that contains the methods that simulate the various complexities.
 */
public class Model {

    /* Constants */
    private static final File BD_FILE = new File("data/bd.dat");
    private static final FlagColor[] COLORS = FlagColor.values();
    private final int BLACK = 0;
    private final int WHITE = 1;
    private final int RED = 2;
    private final int RED_ORANGE = 3;
    private final int ORANGE = 4;
    private final int YELLOW_ORANGE = 5;
    private final int YELLOW = 6;
    private final int YELLOW_GREEN = 7;
    private final int GREEN = 8;
    private final int BLUE_GREEN = 9;
    private final int BLUE = 10;
    private final int BLUE_VIOLET = 11;
    private final int VIOLET = 12;
    private final int VIOLET_RED = 13;

    private final int N_COLORS = 14;

    /* Variables */
    private final PropertyChangeSupport propertyChangeSupport;
    private BufferedImage flag;
    private String country;
    private HashMap<String, double[]> database;

    public Model() {
        propertyChangeSupport = new PropertyChangeSupport(this);
    }

    /* Methods */
    public void loadDatabase(File dir, final int N_TESTS) {
        if (database != null) {
            return;
        }
        if (BD_FILE.exists()) {
            // leer
            try (ObjectInputStream reader = new ObjectInputStream(new FileInputStream(BD_FILE))) {
                database = (HashMap<String, double[]>) reader.readObject();
            } catch (IOException | ClassNotFoundException ex) {
                System.err.println(ex.getMessage());
            }
        } else {
            // crear
            firePropertyChange("creating DB", null, true);
            database = new HashMap<>();
            int cnt = 0;
            File[] files = dir.listFiles();
            for (File file : files) {
                firePropertyChange("update DB", null, cnt * 100.0 / (double) files.length);
                loadFlag(file);
                double[] perc = getRealPercentages();
                Locale loc = new Locale("en", file.getName().replace(".png", ""));
                country = loc.getDisplayCountry();
                database.put(country, perc);
                cnt++;
            }
            // guardar
            try (ObjectOutputStream writer = new ObjectOutputStream(new FileOutputStream(BD_FILE))) {
                writer.writeObject(database);
            } catch (IOException ex) {
                System.err.println(ex.getMessage());
            }
            firePropertyChange("update DB", null, 100.0);
        }
    }

    public void loadFlag(File flagFile) {
        try {
            flag = ImageIO.read(flagFile);
            Locale loc = new Locale("en", flagFile.getName().replace(".png", ""));
            country = loc.getDisplayCountry();
        } catch (IOException ex) {
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public double[] getColorPercentages(final int N_TESTS) {
        if (flag == null || database == null) {
            return null;
        }
        double x, y;
        int[] colorCount = new int[N_COLORS];
        for (int i = 0; i < N_TESTS; i++) {
            x = Math.random() * flag.getWidth();
            y = Math.random() * flag.getHeight();
            Color color = new Color(flag.getRGB((int) x, (int) y));
            //System.out.println(color);
            int pos = getClosestColorIndex2(color);
            //System.out.println(COLORS[pos]);
            colorCount[pos]++;
        }
        double[] percentages = new double[COLORS.length];
        for (int i = 0; i < colorCount.length; i++) {
            percentages[i] = (double) colorCount[i] / N_TESTS;
        }
        return percentages;
    }

    public String findCountry(double[] percentages) {
        country = null;
        double min = Double.MAX_VALUE, dist;
        for (Map.Entry<String, double[]> entry : database.entrySet()) {
            double[] countryPerc = entry.getValue();
            dist = distanceBetweenColorPercentages(percentages, countryPerc);
            if (dist < min) {
                min = dist;
                country = entry.getKey();
            }
        }
        return country;
    }

    public File getRandomFlag(File dir) {
        File[] files = dir.listFiles();
        Random rand = new Random();
        File flagFile = files[rand.nextInt(files.length)];
        return flagFile;
    }

    public BufferedImage getFlagImage() {
        return this.flag;
    }

    public String getCountryName() {
        return this.country;
    }

    public BufferedImage getFlagImage(String countryName) {
        Map<String, String> countries = new HashMap<>();
        for (String iso : Locale.getISOCountries()) {
            Locale l = new Locale("en", iso);
            countries.put(l.getDisplayCountry(), iso);
        }
        BufferedImage img = null;
        try {
            String name = countries.get(countryName);
            String fileName = "resources/flags/" + name + ".png";
            img = ImageIO.read(new File(fileName));
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
        return img;
    }

    //Devolvemos el indice
    private int getClosestColorIndex2(Color color){
        float[] hsb = new float[3];
        Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), hsb);
        hsb[0] = hsb[0]*12; //Asi tenemos Hue en 0-12, Saturacion entre 0-1 y Brillo entre 0-1
        if(hsb[2] <= 0.1){
            return BLACK; //return black
        } else if(hsb[2] > 0.9 && hsb[1] <= 0.1){
            return WHITE; //return white
        } else{
            return 2+(int)hsb[0]; //return color
        }
    }

    // private int getClosestColorIndex(Color color) {
    //     double min = Double.MAX_VALUE, dist;
    //     int pos = -1;
    //     for (int i = 0; i < COLORS.length; i++) {
    //         dist = COLORS[i].distanceToColor(color);
    //         //System.out.println(COLORS[i] + ": " + dist);
    //         if (dist < min) {
    //             min = dist;
    //             pos = i;
    //         }
    //     }
    //     return pos;
    // }

    private Double distanceBetweenColorPercentages(double[] flag, double[] country) {
        if (flag.length != country.length) {
            return null;
        }
        double[] diff = new double[flag.length];
        double sum = 0;
        for (int i = 0; i < diff.length; i++) {
            diff[i] = country[i] - flag[i];
            diff[i] *= diff[i];
            sum += diff[i];
        }
        return Math.sqrt(sum);
    }

    private double[] getRealPercentages() {
        int[] colorCount = new int[COLORS.length];
        for (int i = 0; i < flag.getWidth(); i++) {
            for (int j = 0; j < flag.getHeight(); j++) {
                Color color = new Color(flag.getRGB(i, j));
                int pos = getClosestColorIndex2(color);
                colorCount[pos]++;
            }
        }
        double[] percentages = new double[COLORS.length];
        for (int i = 0; i < colorCount.length; i++) {
            percentages[i] = (double) colorCount[i] / (flag.getWidth() * flag.getHeight());
        }
        return percentages;
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.removePropertyChangeListener(listener);
    }

    protected void firePropertyChange(String propertyName, Object oldValue, Object newValue) {
        propertyChangeSupport.firePropertyChange(propertyName, oldValue, newValue);
    }
}
