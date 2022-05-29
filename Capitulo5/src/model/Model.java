/*
    Algoritmes Avançats - Capitulo 5
    Autors:
        Jonathan Salisbury Vega
        Joan Sansó Pericàs
        Joan Vilella Candia
 */
package model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * Class that contains the word detection algorithm.
 */
public class Model extends AbstractModel{

    /* Enum of all the language dictionaries available */
    private enum Language {
        ENG,
        SPA,
        CAT;

        /* Method to get dictionary file name */
        public String getDicFilename() {
            return "dics/" + this.name() + ".dic";
        }
    }

    /* Variables */
    private Language lang; // detected language
    private ArrayList<String> dictionary; // detected language dictionary

    /* Methods */
    public Model() {
        dictionary = new ArrayList<>();
    }

    /**
     * Method that loops over given words, and compares them to every word in 
     * the detected language dictionary using levenshtein method.
     * @param words Array of string containing words to check
     * @return Hashmap with wrong words as key, and a list of suggestions as element.
     */
    public HashMap<String, ArrayList<String>> correct(String[] words) {
        long time = System.currentTimeMillis();
        detectLang(words);
        dictionary = readDict(this.lang);
        HashMap<String, ArrayList<String>> wrongWords = new HashMap<>();
        ArrayList<String> suggestions;
        int cnt = 0;
        for (String word : words) {
            suggestions = new ArrayList<>();
            int minDistance = Integer.MAX_VALUE;
            for (String dicWord : dictionary) {
                int dist = levenshtein(word, dicWord);
                if (dist == 0) {
                    wrongWords.remove(word);
                    break;
                } else { // Word not found on dict
                    if (dist == minDistance) {                
                        suggestions = wrongWords.get(word);
                        suggestions.add(dicWord);
                        wrongWords.put(word, (ArrayList<String>) suggestions.clone());
                    } else if (dist < minDistance) { // better wond found
                        minDistance = dist;  
                        wrongWords.remove(word);
                        suggestions.clear();
                        suggestions.add(dicWord);
                        wrongWords.put(word, (ArrayList<String>) suggestions.clone());
                    }
                }
            }
            if(wrongWords.containsKey(word)){ // add word as sugestion if its right.
                suggestions = wrongWords.get(word);
                suggestions.add(word);
            }
            firePropertyChange("progress", null, 100*(double)cnt++/(double)words.length);
        }
        time = System.currentTimeMillis() - time;
        //System.out.println("Time for "+words.length+" words: " + time);
        firePropertyChange("progress", null, 100.0);
        return wrongWords;
    }

    /**
     * Method that detects the languaje of given words.
     * @param words Array of strings containing words
     */
    private void detectLang(String[] words) {
        Language[] languages = Language.values();
        int[] wordsFound = new int[languages.length];
        for (int i = 0; i < languages.length; i++) {
            ArrayList<String> dict = readDict(languages[i]);
            int cnt = 0;
            for (String word : words) {
                if (dict.contains(word)) {
                    wordsFound[i]++;
                }
                cnt++;
            }
        }
        int max = 0;
        for (int i = 0; i < wordsFound.length; i++) {
            if (wordsFound[i] > wordsFound[max]) max = i;
        }
        //System.out.println("Detected Language: " + languages[max]);
        this.lang = languages[max];
    }

    /**
     * Method that reads the dictionary file of a given language.
     * @param language language to read
     * @return ArrayList containing all words of the dictionary
     */
    private ArrayList<String> readDict(Language language) {
        ArrayList<String> dict = new ArrayList<>();
        try (FileReader fr = new FileReader(language.getDicFilename());
                BufferedReader reader = new BufferedReader(fr)) {
            String word = reader.readLine();
            while (word != null) {
                dict.add(word);
                word = reader.readLine();
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return dict;
    }

    /**
     * Method that calculates the levenshtein distance between two given words.
     * @param word1 First Word to compare
     * @param word2 Second Word to compare
     * @return distance
     */
    private static int levenshtein(String word1, String word2) {
        int[][] dp = new int[word1.length() + 1][word2.length() + 1];
        for (int i = 0; i <= word1.length(); i++) {
            for (int j = 0; j <= word2.length(); j++) {
                if (i == 0) {
                    dp[i][j] = j;
                } else if (j == 0) {
                    dp[i][j] = i;
                } else {
                    dp[i][j] = min(dp[i - 1][j - 1]
                            + (word1.charAt(i - 1) == word2.charAt(j - 1) ? 0 : 1),
                            dp[i - 1][j] + 1, // delete
                            dp[i][j - 1] + 1); // insert
                }
            }
        }
        return dp[word1.length()][word2.length()];
    }

    /**
     * Aux method to find min number in a list.
     * @param nums nums to find min
     * @return min number
     */
    private static int min(int... nums) {
        return Arrays.stream(nums).min().orElse(
                Integer.MAX_VALUE);
    }
}
