/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.ArrayList;

/**
 *
 * @author elsho
 */
public class Word {
    
    private String value;
    private boolean correct;
    private final ArrayList<String> suggestions;

    public Word(String value) {
        this.value = value;
        this.correct = false;
        this.suggestions = new ArrayList<>();
    }

    public String getValue() {
        return value;
    }
    
    public void setValue(String value) {
        this.value = value;
    }

    public boolean isCorrect() {
        return correct;
    }
    
    public void setCorrect(boolean isCorrect) {
        this.correct = isCorrect;
    }

    public ArrayList<String> getSuggestions() {
        return suggestions;
    }

    public void addSuggestion(String suggestion) {
        this.suggestions.add(suggestion);
    }
    
    public void removeSuggestions(){
        this.suggestions.clear();
    }
}
