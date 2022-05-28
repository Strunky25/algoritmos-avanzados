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
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JProgressBar;
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

/**
 * Class that contains the Swing GUI for the project.
 */
public class View extends JFrame {

    /* Constants */
 /* Variables */
    private final DefaultStyledDocument document;
    private final StyleContext correctStyle, wrongStyle;
    private final Style correct, wrong;
    private final ArrayList<String> wrongWords;
    private HashMap<String, ArrayList<String>> results;
    private int wrongIndex;
    private String part1;
    private String part2;

    /* Swing Components */
    private JLabel inputLbl, outputLbl, sugLbl, wrongLbl;
    private JButton checkBtn, fileBtn, nextBtn, preBtn, correctBtn, saveBtn;
    private JTextField wordTxtField;
    private JTextArea txtArea;
    private JTextPane checkedTxtPane;
    private JList sugList;
    private JScrollPane sugScrollPanel, txtScrollPanel, checkedScrollPanel;
    private JProgressBar progressBar;

    public View() {
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
    private void initComponents() {
        setTitle("Capitulo 5");
        setIconImage(new ImageIcon("resources/icon.png").getImage());
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        inputLbl = new JLabel("Input Text");
        outputLbl = new JLabel("Corrected Text");
        sugLbl = new JLabel("Suggestions");
        wrongLbl = new JLabel("Wrong Words");

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
        correctBtn.addActionListener((e) -> {
            try {
                correctWord();
            } catch (BadLocationException ex) {
                Logger.getLogger(View.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        wordTxtField = new JTextField();
        wordTxtField.setEditable(false);
        wordTxtField.setBackground(Color.white);

        txtArea = new JTextArea(5, 20);
        txtScrollPanel = new JScrollPane(txtArea);

        checkedTxtPane = new JTextPane(document);
        checkedTxtPane.setBackground(Color.white);
        checkedTxtPane.setEditable(false);
        checkedScrollPanel = new JScrollPane(checkedTxtPane);

        sugList = new JList();
        sugScrollPanel = new JScrollPane(sugList);
        
        progressBar = new JProgressBar();

        addComponents();
        setResizable(false);
        setLocationRelativeTo(null);
    }

    /**
     * Method that creates the frame layout with all its components.
     */
    private void addComponents() {
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
            .addGroup(layout.createSequentialGroup()
                .addGap(203, 203, 203)
                .addComponent(inputLbl)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(outputLbl)
                .addGap(183, 183, 183))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(50, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(txtScrollPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(50, 50, 50)
                            .addComponent(checkedScrollPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(50, 50, 50))
                        .addGroup(layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(preBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(wordTxtField, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(nextBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(correctBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(74, 74, 74))
                                .addGroup(layout.createSequentialGroup()
                                    .addGap(137, 137, 137)
                                    .addComponent(wrongLbl)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(sugScrollPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 235, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGap(104, 104, 104)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(sugLbl)
                        .addGap(194, 194, 194))))
            .addComponent(progressBar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(inputLbl)
                    .addComponent(outputLbl))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
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
                        .addGap(55, 55, 55)
                        .addComponent(wrongLbl)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(wordTxtField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(preBtn)
                            .addComponent(nextBtn)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addComponent(sugLbl)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(sugScrollPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(correctBtn)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 21, Short.MAX_VALUE)
                .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        pack();
    }

    /* METHODS */
    // Add Listeners
    public void addListeners(ActionListener listener) {
        checkBtn.addActionListener(listener);
    }

    public String[] getText() {
        return txtArea.getText().split("[\\p{Punct}\\s]+");
    }

    public void showResults(HashMap<String, ArrayList<String>> results){
        try {
            this.results = results;
            int lastIndex = 0, thisIndex;
            document.insertString(0, "", correct);
            checkedTxtPane.setText("");
            String txt = txtArea.getText();
            String[] words = getText();
            for (String word : words) {
                thisIndex = txt.indexOf(word, lastIndex) + word.length();
                String aux = txt.substring(lastIndex, thisIndex);
                if (results.containsKey(word)) {
                    wrongWords.add(word);
                    document.insertString(lastIndex, aux, wrong);
                } else {
                    document.insertString(lastIndex, aux, correct);
                }
                lastIndex = thisIndex;
            }   document.insertString(lastIndex, txt.substring(lastIndex), correct);
            if (!wrongWords.isEmpty()) {
                wrongIndex = 0;
                wordTxtField.setText(wrongWords.get(wrongIndex));
                sugList.setListData(results.get(wrongWords.get(wrongIndex)).toArray());
                
                String wrongWord = wrongWords.get(wrongIndex);
                String regex = "^" + wrongWord + "| " + wrongWord + "|\\." + wrongWord + "|\\?" + wrongWord + "|[^A-z]" + wrongWord;
                //System.out.println("Word to find: " + wrongWord + " . Regex: " + regex);
                Pattern pattern = Pattern.compile(regex);
                Matcher matcher = pattern.matcher(txt);
                int index = txt.length();
                if (matcher.find()) {
                    //System.out.println("word found");
                    index = matcher.start();
                    this.part1 = txt.substring(0, index);
                    this.part2 = txt.substring(index);
                }
                //System.out.println("part1: " + part1);
                //System.out.println("part2: " + part2);
            }
        } catch (BadLocationException ex) {
            Logger.getLogger(View.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void setProgress(int progress){
        this.progressBar.setValue(progress);
    }
    
    private String[] getTokens(String text) {
        return text.split("[\\p{Punct}\\s]+");
    }

    private void selectFile() {
        JFileChooser chooser = new JFileChooser("./test/");
        chooser.setFileFilter(new FileNameExtensionFilter("Text Files", "txt", "text"));
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
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
        if (wrongWords.isEmpty()) {
            wordTxtField.setText("");
            sugList.setListData(new Object[]{""});
            return;
        }
        switch (e.getActionCommand()) {
            case "Next" -> {
                if (wrongIndex + 1 > wrongWords.size() - 1) {
                    wrongIndex = 0;
                    wordTxtField.setText(wrongWords.get(wrongIndex));
                    //esto de aqui es lo mismo que hacemos en show results
                    String wrongWord = wrongWords.get(wrongIndex);
                    String text = part1 + part2;
                    String regex = "^" + wrongWord + "| " + wrongWord + "|\\." + wrongWord + "|\\?" + wrongWord + "|[^A-z]" + wrongWord;
                    //System.out.println("Word to find: " + wrongWord + " . Regex: " + regex);
                    Pattern pattern = Pattern.compile(regex);
                    Matcher matcher = pattern.matcher(text);
                    int index = text.length();
                    if (matcher.find()) {
                        //System.out.println("word found");
                        index = matcher.start();
                        this.part1 = text.substring(0, index);
                        this.part2 = text.substring(index);
                    }
                    //hasta aqui
                } else {
                    wrongIndex = wrongIndex + 1;
                    wordTxtField.setText(wrongWords.get(wrongIndex));
                    String wrongWord = wrongWords.get(wrongIndex);
                    String text = part1 + part2;
                    String regex = "^" + wrongWord + "| " + wrongWord + "|\\." + wrongWord + "|\\?" + wrongWord + "|[^A-z]" + wrongWord;
                    //System.out.println("Word to find: " + wrongWord + " . Regex: " + regex);
                    Pattern pattern = Pattern.compile(regex);
                    Matcher matcher = pattern.matcher(part2.substring(2));
                    if (matcher.find()) {
                        System.out.println("word found");
                        int index = matcher.start() + 2;
                        this.part1 = part1 + part2.substring(0, index);
                        this.part2 = part2.substring(index);
                    }
                }
                //System.out.println("part1: " + part1);
                //System.out.println("part2: " + part2);
            }
            case "Previous" -> {
                if (wrongIndex - 1 < 0) {
                    wrongIndex = wrongWords.size() - 1;
                    wordTxtField.setText(wrongWords.get(wrongIndex));
                    String wrongWord = wrongWords.get(wrongIndex);
                    String text = part1 + part2;
                    String regex = "^" + wrongWord + "| " + wrongWord + "|\\." + wrongWord + "|\\?" + wrongWord + "|[^A-z]" + wrongWord;
                    //System.out.println("Word to find: " + wrongWord + " . Regex: " + regex);
                    Pattern pattern = Pattern.compile(regex);
                    Matcher matcher = pattern.matcher(text);
                    int index = text.length();
                    while (matcher.find()) {
                        index = matcher.start();
                    }
                    this.part1 = text.substring(0, index);
                    this.part2 = text.substring(index);

                } else {
                    wrongIndex = wrongIndex - 1;
                    wordTxtField.setText(wrongWords.get(wrongIndex));
                    String wrongWord = wrongWords.get(wrongIndex);
                    String regex = "^" + wrongWord + "| " + wrongWord + "|\\." + wrongWord + "|\\?" + wrongWord + "|[^A-z]" + wrongWord;
//                    System.out.println("Word to find: " + wrongWord + " . Regex: " + regex);
                    Pattern pattern = Pattern.compile(regex);
                    Matcher matcher = pattern.matcher(part1);
                    int index = part1.length();
                    while (matcher.find()) {
                        index = matcher.start();
                    }
                    this.part2 = part1.substring(index) + part2;
                    this.part1 = part1.substring(0, index);
                }

                //System.out.println("part1: " + part1);
                //System.out.println("part2: " + part2);
            }
        }
        sugList.setListData(results.get(wrongWords.get(wrongIndex)).toArray());
    }

    private void correctWord() throws BadLocationException {
        String word = wordTxtField.getText();
        String correctedWord = (String) sugList.getSelectedValue();
        if (correctedWord == null) return;
        if (correctedWord.equals("Ignore")) {
            correctedWord = word;
        }
        int txtlong = part1.length() + part2.length();
        part2 = part2.replaceFirst(word, correctedWord);
        String txt = part1 + part2;
        String[] words = getTokens(txt);
        int thisIndex, lastIndex = 0;
        document.replace(0, txtlong, "", wrong);

        for (String word2 : words) {
            thisIndex = txt.indexOf(word2, lastIndex) + word2.length();
            String aux = txt.substring(lastIndex, thisIndex);
            if (results.containsKey(word2)) {
                document.insertString(lastIndex, aux, wrong);
            } else {
                document.insertString(lastIndex, aux, correct);
            }
            lastIndex = thisIndex;
        }
        document.insertString(lastIndex, txt.substring(lastIndex), correct);
        wrongWords.remove(wrongIndex);
        changeWord(new ActionEvent(nextBtn, 0, "Next"));
    }

    private void saveFile() {
        JFileChooser chooser = new JFileChooser(".");
        chooser.setFileFilter(new FileNameExtensionFilter("Text Files", "txt", "text"));
        if (chooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            File selectedFile = chooser.getSelectedFile();
            try ( FileWriter fw = new FileWriter(selectedFile);  BufferedWriter writer = new BufferedWriter(fw);) {
                writer.write(document.getText(0, document.getLength()));
            } catch (IOException | BadLocationException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
}