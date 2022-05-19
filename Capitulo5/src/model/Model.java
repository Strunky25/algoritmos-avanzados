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
 * Class that contains the methods that simulate the various complexities.
 */
public class Model {

    /* Constants */
    private static final int N_DETECT_WORDS = 5;

    private enum Language {
        ENG,
        SPA,
        CAT;

        public String getDicFilename() {
            return "dics/" + this.name() + ".dic";
        }
    }

    /* Variables */
    private Language lang;
    private ArrayList<String> dictionary;

    /* Methods */
    public Model() {
        dictionary = new ArrayList<>();
    }

    public HashMap<String, ArrayList<String>> correct(String[] wordsValues) {
        detectLang(wordsValues);
        //System.out.println("Detected language: " + lang);
        dictionary = readDict(this.lang);

        //ArrayList<String> wrongWords = new ArrayList<>();
        HashMap<String, ArrayList<String>> wrongWords = new HashMap<>();
        ArrayList<String> suggestions;
        
        for (String wordValue : wordsValues) {
            suggestions = new ArrayList<>();
            int minDistance = Integer.MAX_VALUE;
            for (String dicWord : dictionary) {
                int dist = levenshtein(wordValue, dicWord);
                if (dist == 0) {
                    wrongWords.remove(wordValue);
                    //word.setCorrect(true);
                    break;
                } else {
                    if (dist == minDistance) {                
                        suggestions = wrongWords.get(wordValue);
                        suggestions.add(dicWord);
                        wrongWords.put(wordValue, (ArrayList<String>) suggestions.clone());
                    } else if (dist < minDistance) {
                        minDistance = dist;  
                        wrongWords.remove(wordValue);
                        suggestions.clear();
                        suggestions.add(dicWord);
                        wrongWords.put(wordValue, (ArrayList<String>) suggestions.clone());
                    }
                }
            }
//            if (!word.isCorrect()) {
//                word.addSuggestion("Ignore");
//            }
//            words.add(word);
        }
        return wrongWords;
    }

    private void detectLang(String[] words) {
        Language[] languages = Language.values();
        int[] wordsFound = new int[languages.length];
        for (int i = 0; i < languages.length; i++) {
            ArrayList<String> dict = readDict(languages[i]);
            for (String word : words) {
                if (dict.contains(word)) {
                    wordsFound[i]++;
                }
            }
        }
        int max = 0;
        for (int i = 0; i < wordsFound.length; i++) {
            //System.out.println("words found in " + languages[i] + ": " + wordsFound[i]);
            if (wordsFound[i] > wordsFound[max]) {
                max = i;
            }
        }
        this.lang = languages[max];
    }

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

    static int min(int... nums) {
        return Arrays.stream(nums).min().orElse(
                Integer.MAX_VALUE);
    }
}
