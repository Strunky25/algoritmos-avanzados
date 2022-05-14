/*
    Algoritmes Avançats - Capitulo 5
    Autors:
        Jonathan Salisbury Vega
        Joan Sansó Pericàs
        Joan Vilella Candia
*/
package view;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
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
import javax.swing.JTextPane;
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
    private final ArrayList<Word> wrongWords;
    private int wrongIndex;
    
    /* Swing Components */
    private JButton checkBtn, fileBtn, nextBtn, preBtn, correctBtn, saveBtn;
    private JTextField wordTxtField;
    private JTextArea txtArea;
    private JTextPane checkedTxtPane;
    private JList sugList;
    private JScrollPane sugScrollPanel, txtScrollPanel, checkedScrollPanel;
    
    public View(){
        document = new DefaultStyledDocument();
        
        correctStyle = new StyleContext();
        correct = correctStyle.addStyle("correct", null);
        StyleConstants.setForeground(correct, Color.blue);
        
        wrongStyle = new StyleContext();
        wrong = wrongStyle.addStyle("wrong", null);
        StyleConstants.setForeground(wrong, Color.red);
        
        wrongWords = new ArrayList<>();
        
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
        
        saveBtn = new JButton("Save File");
        saveBtn.addActionListener((e) -> saveFile());
        
        nextBtn = new JButton("Next");
        nextBtn.addActionListener((e) -> changeWord(e));
        
        preBtn = new JButton("Previous");
        preBtn.addActionListener((e) -> changeWord(e));
        
        correctBtn = new JButton("Correct");
        correctBtn.addActionListener((e) -> correctWord());
        
        wordTxtField = new JTextField();
        wordTxtField.setEditable(false);
        wordTxtField.setBackground(Color.white);
        
        txtArea = new JTextArea(5, 20);
        txtScrollPanel = new JScrollPane(txtArea);
        
        checkedTxtPane = new JTextPane(document);
        checkedScrollPanel = new JScrollPane(checkedTxtPane);
        
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
                .addGap(76, 76, 76)
                .addComponent(fileBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(82, 82, 82)
                .addComponent(checkBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(211, 211, 211)
                .addComponent(saveBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(50, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                            .addComponent(txtScrollPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(50, 50, 50)
                            .addComponent(checkedScrollPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(50, 50, 50))
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(preBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(wordTxtField, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(nextBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(sugScrollPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 235, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(104, 104, 104)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(correctBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(178, 178, 178))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(checkedScrollPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                    .addComponent(txtScrollPanel))
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(fileBtn)
                    .addComponent(checkBtn)
                    .addComponent(saveBtn))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(77, 77, 77)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(wordTxtField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(preBtn)
                            .addComponent(nextBtn))
                        .addGap(80, 80, 80))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(sugScrollPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                .addComponent(correctBtn)
                .addContainerGap(51, Short.MAX_VALUE))
        );
        pack();
    }

    /* METHODS */
    
    // Add Listeners
    public void addListeners(ActionListener listener){
        checkBtn.addActionListener(listener);
    }
    
    public String[] getText(){
        return txtArea.getText().split("[\\p{Punct}\\s]+");
    }
    
    public void showResults(ArrayList<Word> results) throws BadLocationException{     
        int lastIndex = 0, thisIndex;
        document.insertString(0, "", correct);
        String txt = txtArea.getText();
        for(Word result: results) {
            //System.out.print("word = " + result.getValue());
            thisIndex = txt.indexOf(result.getValue(), lastIndex) + result.getValue().length();
            //System.out.print(" [" + lastIndex + ", " + thisIndex + "]");
            String aux = txt.substring(lastIndex, thisIndex);
            //System.out.println(", aux = " + aux);
            if(!result.isCorrect()){
                wrongWords.add(result);
                document.insertString(lastIndex, aux, wrong);
            } else {
                document.insertString(lastIndex, aux, correct);
            }
            lastIndex = thisIndex;
        }
        document.insertString(lastIndex, txt.substring(lastIndex), correct);
        if(!wrongWords.isEmpty()){
            wrongIndex = 0;
            wordTxtField.setText(wrongWords.get(wrongIndex).getValue());
            sugList.setListData(wrongWords.get(wrongIndex).getSuggestions().toArray());
        }
    }
    
    private void selectFile(){
        JFileChooser chooser = new JFileChooser(".");
        chooser.setFileFilter(new FileNameExtensionFilter("Text Files", "txt", "text"));
        if(chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION){
            File selectedFile = chooser.getSelectedFile();
            loadFileContent(selectedFile);
        }
    }

    private void loadFileContent(File file) {
        try {
            List<String> fileContent = Files.readAllLines(file.toPath(), StandardCharsets.UTF_8);
            txtArea.setText(String.join("\n", fileContent));
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private void changeWord(ActionEvent e) {
        if(wrongWords.isEmpty()){
            wordTxtField.setText("");
            sugList.setListData(new Object[]{""});
            return;
        }
        switch(e.getActionCommand()){
            case "Next" -> {
                wrongIndex = (wrongIndex + 1) > wrongWords.size() - 1 ? 0 : wrongIndex + 1;
                wordTxtField.setText(wrongWords.get(wrongIndex).getValue());
            }
            case "Previous" -> {
                wrongIndex = (wrongIndex - 1) < 0 ? wrongWords.size() - 1 : wrongIndex - 1;
                wordTxtField.setText(wrongWords.get(wrongIndex).getValue());
            }
        }
        sugList.setListData(wrongWords.get(wrongIndex).getSuggestions().toArray());
    }

    private void correctWord() {
        String word = wordTxtField.getText();
        String correctedWord = (String) sugList.getSelectedValue();
        if(correctedWord.equals("Ignore")){
            // replacto ro correct
            correctedWord = word;
        }
        
        String txt = checkedTxtPane.getText();
        try {
            document.replace(txt.indexOf(word), correctedWord.length(), correctedWord, correct);
        } catch (BadLocationException ex) {
            Logger.getLogger(View.class.getName()).log(Level.SEVERE, null, ex);
        }
        wrongWords.remove(wrongIndex);
        changeWord(new ActionEvent(nextBtn, 0, "Next"));
    }

    private void saveFile() {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileFilter(new FileNameExtensionFilter("Text Files", "txt", "text"));
        if(chooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION){
            File selectedFile = chooser.getSelectedFile();
            try(FileWriter fw = new FileWriter(selectedFile);
                    BufferedWriter writer = new BufferedWriter(fw);){
                writer.write(document.getText(0, document.getLength()));
            } catch (IOException | BadLocationException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
}
