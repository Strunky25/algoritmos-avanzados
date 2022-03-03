/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package capitulo1.controller;

import capitulo1.model.Model;
import capitulo1.view.View;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author elsho
 */
public class Controller {
    
    private final Model model;
    private final View view;
    
    public Controller(Model model, View view){
        this.model = model;
        this.view = view;
        this.view.addAnimateListener(new AnimateListener());
    }
    
    public class AnimateListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            String complexity = view.getChosenComplexity();
            view.setRunning(true);
            model.execute(complexity);
            view.setRunning(false);
        }
        
    } 
}
