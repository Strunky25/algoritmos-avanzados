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
    private static final int N_DETECT_WORDS = 3;
    
    private enum Language {
        ENG,
        SPA,
        CAT;
  
        public String getDicFilename(){
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
    
    
    public ArrayList<Word> correct(String[] wordsValues){
        detectLang(wordsValues);
        System.out.println("Detected language: " + lang);
        dictionary = readDict(this.lang);
        ArrayList<Word> words = new ArrayList<>();
        for(String wordValue: wordsValues) {
            Word word = new Word(wordValue);
            int minDistance = Integer.MAX_VALUE;
            for(String dicWord: dictionary){
                int dist = levenshtein(wordValue, dicWord);
                if(dist == 0){
                    word.setCorrect(true);
                    break;
                } else if(dist < minDistance){
                    minDistance = dist;
                    word.addSuggestion(dicWord);
                }
            }
            words.add(word);
        }
        return words;
    }
    
    private void detectLang(String [] words){
        for(Language language: Language.values()){
            ArrayList<String> dict = readDict(language);
            int wordsFound = 0;
            for(String word: words) {
                if(dict.contains(word)) wordsFound++;
                if(wordsFound == N_DETECT_WORDS){
                    this.lang = language;
                    return;
                }
            }
            if(wordsFound != 0){
                this.lang = language; // temp if words length < detect words
            }
        }
    }
    
    private ArrayList<String> readDict(Language language){
        ArrayList<String> dict = new ArrayList<>();
        try (FileReader fr = new FileReader(language.getDicFilename());
                BufferedReader reader = new BufferedReader(fr)){
            String word = reader.readLine();
            while(word != null){
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
        for (int i = 0; i <= word1.length(); i++){
            for (int j = 0; j <= word2.length(); j++) {
                if (i == 0) dp[i][j] = j;
                else if (j == 0) dp[i][j] = i;
                else {
                    dp[i][j] = min(dp[i - 1][j - 1]
                        + (word1.charAt(i - 1) == word2.charAt(j - 1) ? 0 : 1),
                        dp[i - 1][j] + 1, // delete
                        dp[i][j - 1] + 1); // insert
                }
            }
        }
        return dp[word1.length()][word2.length()];
    }
 
    static int min(int... nums){
        return Arrays.stream(nums).min().orElse(
            Integer.MAX_VALUE);
    }
}
