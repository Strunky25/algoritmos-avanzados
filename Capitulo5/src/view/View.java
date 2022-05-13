/*
    Algoritmes Avançats - Capitulo 5
    Autors:
        Jonathan Salisbury Vega
        Joan Sansó Pericàs
        Joan Vilella Candia
*/
package view;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import model.Word;

/**
 * Class that contains the Swing GUI for the project.
 */
public class View extends JFrame{
    
    /* Constants */
    
    /* Variables */
    private final DefaultStyledDocument document;
    private final StyleContext correctStyle, wrongStyle;
    private final Style correct, wrong;
    
    /* Swing Components */
    private JButton checkBtn, fileBtn, nextBtn, preBtn, correctBtn;
    private JTextField wordTxtField;
    private JTextArea textArea;
    private JList sugList;
    private JScrollPane sugScrollPanel, txtScrollPanel;
    
    public View(){
        document = new DefaultStyledDocument();
        
        correctStyle = new StyleContext();
        correct = correctStyle.addStyle("correct", null);
        StyleConstants.setForeground(correct, Color.blue);
        StyleConstants.setBold(correct, true);
        
        wrongStyle = new StyleContext();
        wrong = wrongStyle.addStyle("wrong", null);
        StyleConstants.setForeground(wrong, Color.red);
        StyleConstants.setBold(wrong, true);
        
        initComponents();
    }
    
    /**
     * Method that initializes swing components, and sets JFrame properties.
     */
    private void initComponents(){
        setTitle("Capitulo 5");
        //setIconImage(new ImageIcon("resources/icon.png").getImage());
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        /* Init Components */
        checkBtn = new JButton("Check");
        fileBtn = new JButton("Load File");
        fileBtn.addActionListener((e) -> selectFile());
        nextBtn = new JButton("Next");
        preBtn = new JButton("Previous");
        correctBtn = new JButton("Correct");
        
        wordTxtField = new JTextField();
        wordTxtField.setEditable(false);
        
        textArea = new JTextArea(5, 20);
        textArea.setDocument(document);
        txtScrollPanel = new JScrollPane(textArea);
        
        sugList = new JList();
        sugScrollPanel = new JScrollPane(sugList);
        
        
        addComponents();
        setResizable(false);
        setLocationRelativeTo(null);
    }
    
    /**
     * Method that creates the frame layout with all its components.
     */
    private void addComponents(){
        /* Add Layout */
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(81, 81, 81)
                .addComponent(checkBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(fileBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(87, 87, 87))
            .addGroup(layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(preBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(wordTxtField, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(nextBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(71, 71, 71)
                        .addComponent(sugScrollPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 255, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(139, 139, 139)
                        .addComponent(correctBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(txtScrollPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 380, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(59, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(txtScrollPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(fileBtn)
                    .addComponent(checkBtn))
                .addGap(39, 39, 39)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(wordTxtField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(preBtn)
                    .addComponent(nextBtn))
                .addGap(18, 18, 18)
                .addComponent(sugScrollPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(correctBtn)
                .addContainerGap(36, Short.MAX_VALUE))
        );
        pack();
    }

    /* METHODS */
    
    // Add Listeners
    public void addListeners(ActionListener listener){
        checkBtn.addActionListener(listener);
    }
    
    public String[] getText(){
        return textArea.getText().split("[\\p{Punct}\\s]+");
    }
    
    public void showResults(ArrayList<Word> results){     
        int lastIndex = 0, thisIndex;
        String txt = textArea.getText();
        textArea.setText("");
        for(Word result: results) {
            if(!result.isCorrect()){
                System.out.println(result.getValue() + " not correct " + Arrays.toString(result.getSuggestions().toArray()));
            } else {
                try {
                    //System.out.println(result.getValue() + " is correct.");
                    thisIndex = txt.indexOf(result.getValue());
                    String aux = txt.substring(lastIndex, thisIndex);
                    System.out.println(aux);
                    document.insertString(lastIndex, aux, correct);
                    lastIndex = thisIndex;
                } catch (BadLocationException ex) {
                    Logger.getLogger(View.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
    
    private void selectFile(){
        JFileChooser chooser = new JFileChooser();
        chooser.setFileFilter(new FileNameExtensionFilter("Text Files", "txt", "text"));
        if(chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION){
            File selectedFile = chooser.getSelectedFile();
            loadFileContent(selectedFile);
        }
    }

    private void loadFileContent(File file) {
        try {
            List<String> fileContent = Files.readAllLines(file.toPath(), StandardCharsets.UTF_8);
            textArea.setText(String.join("\n", fileContent));
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
