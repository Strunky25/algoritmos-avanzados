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

/**
 * Class that contains the methods that simulate the various complexities.
 */
public class Model {
    
    /* Constants */
    public enum Language {
        ENG,
        SPA,
        CAT
    }
    
    /* Variables */
    private Language lang;
    private ArrayList<String> dictionary;
    
    
    /* Methods */
    
    public Model() {
        dictionary = new ArrayList<>();
    }
    
    
    public void correct(String[] words){
        detectLang();
        readDict();
        int minDistance = Integer.MAX_VALUE;
        String bestWord = null;
        for(String word: words) {
            System.out.print("Word " + word + " is: ");
            for(String dicWord: dictionary){
                int dist = levenshtein(word, dicWord);
                if(dist == 0){
                    System.out.println("Correct");
                    bestWord = null;
                    break;
                } else if(dist < minDistance){
                    bestWord = dicWord;
                }
            }
            if(bestWord != null){
                System.out.println("Incorrecto " + bestWord);
            }
        }
    }
    
    private void detectLang(){
        this.lang = Language.SPA;
    }
    
    private void readDict(){
        try (FileReader fr = new FileReader("dics/" + lang.name() + ".dic");
                BufferedReader reader = new BufferedReader(fr)){
            String word = reader.readLine();
            while(word != null){
                this.dictionary.add(word);
                word = reader.readLine();
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    private static int levenshtein(String x, String y) {
        int[][] dp = new int[x.length() + 1][y.length() + 1];
        for (int i = 0; i <= x.length(); i++) {
            for (int j = 0; j <= y.length(); j++) {
                if (i == 0) dp[i][j] = j;
                else if (j == 0) dp[i][j] = i;
                else {
                    dp[i][j] = min(dp[i - 1][j - 1] + x.charAt(i - 1) == y.charAt(j - 1) ? 0 : 1, dp[i - 1][j] + 1, dp[i][j - 1] + 1);
                    //costOfSubstitution(x.charAt(i - 1), y.charAt(j - 1));
                }
            }
        }
        return dp[x.length()][y.length()];
    }
    
    public static int costOfSubstitution(char a, char b) {
        return a == b ? 0 : 1;
    }
    
    public static int min(int... numbers) {
        return Arrays.stream(numbers)
          .min().orElse(Integer.MAX_VALUE);
    }
}
