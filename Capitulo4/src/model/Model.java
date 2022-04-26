/*
    Algoritmes Avançats - Capitulo 4
    Autors:
        Jonathan Salisbury Vega
        Joan Sansó Pericàs
        Joan Vilella Candia
*/
package model;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.logging.FileHandler;

/**
 * Class that contains the methods that simulate the various complexities.
 */
public class Model {

    /* Constants */

    /* Variables */
    private HashMap frequencies;

    /* Methods */
    public Model() {
        this.frequencies = new HashMap<>();
    }

    /**
     * Method that returns the frequencies of the characters in the text.
     * 
     * @param text Text to be analyzed.
     * @return HashMap with the frequencies of the characters.
     */
    private int[] getFrequencies(String fileName) {
        // read file with readbtyes
        Path path = Paths.get(fileName);
        byte[] data = null;
        try {
            data = Files.readAllBytes(path);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if (data == null) {
            return null;
        }
        // count frequencies of the array
        for(int i = 0; i < data.length; i++){
            
        }
        return null;
    }

    public void compress(File selectedFile) {

    }

    public void decompress(File selectedFile) {

    }
}
