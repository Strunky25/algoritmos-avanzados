/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package capitulo1.model;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author elsho
 */
public class Model {
    
    public static final String SQRT = "sqrt(n)", LOG = "log(n)", NLOG = "n*log(n)", N2 = "n^2";
    public static final String[] COMPLEXITIES = {SQRT, LOG, NLOG, N2};
    

    
    public void execute(String complexity){
        //System.out.println(complexity);
        switch(complexity){
            case SQRT -> sqrt();
            case LOG -> log();
            case NLOG -> nlog();
            case N2 -> n2();
        }
    }
    
    public void sqrt(){
        System.out.println("hola");
        sleep();
    }
    
    public void log(){}
    
    public void nlog(){}
    
    public void n2(){}
    
    private void sleep(){
        try {
            Thread.sleep(5000);
        } catch (InterruptedException ex) {
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
