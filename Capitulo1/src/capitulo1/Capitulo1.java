/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package capitulo1;

import capitulo1.controller.Controller;
import capitulo1.view.View;
import capitulo1.model.Model;
import com.formdev.flatlaf.FlatLightLaf;
import javax.swing.SwingUtilities;

/**
 *
 * @author elsho
 */
public class Capitulo1 implements Runnable{

    private Model model;
    private Controller controller;
    private View view;
   
    @Override
    public void run() {
        model = new Model();
        view = new View();
        controller = new Controller(model, view);
        view.setVisible(true);
    }
   
    
    public static void main(String[] args) {
        FlatLightLaf.setup();
        Capitulo1 prog = new Capitulo1();
        SwingUtilities.invokeLater(prog); 
    }
}
