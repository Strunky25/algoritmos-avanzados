/*
    Algoritmes Avançats - Capitulo 6
    Autors:
        Jonathan Salisbury Vega
        Joan Sansó Pericàs
        Joan Vilella Candia
 */
package view;

import controller.Controller;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JSpinner.DefaultEditor;
import javax.swing.SpinnerNumberModel;

import model.Heuristica;

/**
 * Class that contains the Swing GUI for the project.
 */
public class View extends JFrame {

    /* Constants */
    private int N = 3;
    private final Heuristica[] HEURISTICS = { Heuristica.INCORRECT_POSITIONS,
            Heuristica.MANHATTAN, Heuristica.EUCLIDEAN, Heuristica.ADJACENT_TILE_REVERSAL,
            Heuristica.OUT_OF_ROW_OUT_OF_COLUMN };

    /* Variables */
    private int[] order;
    private BufferedImage[] icon;
    private int x;

    /* Swing Components */
    private JButton imgBtn, shuffleBtn, solveBtn;
    private JPanel imgPanel;
    private JLabel[] label;
    private JLabel labelHeuristic, labelSize;
    private JComboBox comboBoxHeuristic;
    private JSpinner spinnerSize;
    private BufferedImage img;

    public View() {
        setTitle("Capitulo 6");
        setIconImage(new ImageIcon("resources/icon.png").getImage());
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        initComponents();
    }

    /**
     * Method that initializes swing components, and sets JFrame properties.
     */
    private void initComponents() {
        /* Init Components */
        imgBtn = new JButton("Load Image");
        imgBtn.addActionListener((e) -> loadImg());
        shuffleBtn = new JButton("Shuffle");
        shuffleBtn.addActionListener((e) -> shuffle());
        solveBtn = new JButton("Solve");

        imgPanel = new JPanel();
        imgPanel.setBackground(Color.lightGray);

        spinnerSize = new JSpinner(new SpinnerNumberModel(3, 3, 9, 1));
        ((DefaultEditor) spinnerSize.getEditor()).getTextField().setEditable(false);
        spinnerSize.addChangeListener((e) -> changeSize());
        comboBoxHeuristic = new JComboBox<>();
        comboBoxHeuristic.setActionCommand("ChangeHeuristic");
        labelHeuristic = new JLabel();
        labelSize = new JLabel();

        addComponents();
        setResizable(false);
        setLocationRelativeTo(null);
    }

    /**
     * Method that creates the frame layout with all its components.
     */
    private void addComponents() {
        order = new int[N * N];
        icon = new BufferedImage[N * N];
        label = new JLabel[N * N];
        for (int i = 0; i < N * N; i++) {
            order[i] = i;
            label[i] = new JLabel();
            label[i].setBorder(BorderFactory.createLineBorder(Color.black));
        }
        x = N * N - 1;

        try {
            img = ImageIO.read(new File("test/DEFAULT.jpeg"));
        } catch (IOException ex) {
            Logger.getLogger(View.class.getName()).log(Level.SEVERE, null, ex);
        }
        updateImgPanel();

        /* Add Layout */
        GridLayout grid = new GridLayout(N, N);
        imgPanel.setLayout(grid);
        for (JLabel lab : label) {
            imgPanel.add(lab);
        }
        imgPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                swapTiles(e);
            }
        });
        imgBtn.setText("Load Image");

        shuffleBtn.setText("Shuffle");

        solveBtn.setText("Solve");

        comboBoxHeuristic
                .setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Incorrect tiles", "Manhattan Distance",
                        "Euclidean Distance", "Adjacent reversal tiles", "Wrong Row, Wrong Column" }));

        labelHeuristic.setText("Choose a heuristic:");

        labelSize.setText("Choose size:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(83, 83, 83)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(imgBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 89,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(72, 72, 72)
                                                .addComponent(shuffleBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 79,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED,
                                                        javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(solveBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 67,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addComponent(imgPanel, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(101, Short.MAX_VALUE))
                        .addGroup(layout.createSequentialGroup()
                                .addGap(123, 123, 123)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(labelHeuristic, javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(comboBoxHeuristic, 0, javax.swing.GroupLayout.DEFAULT_SIZE,
                                                Short.MAX_VALUE))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED,
                                                        javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(spinnerSize, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                        javax.swing.GroupLayout.DEFAULT_SIZE,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(184, 184, 184))
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(117, 117, 117)
                                                .addComponent(labelSize)
                                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE,
                                                        Short.MAX_VALUE)))));
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(22, 22, 22)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(labelHeuristic)
                                        .addComponent(labelSize))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(spinnerSize, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(comboBoxHeuristic, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addComponent(imgPanel, javax.swing.GroupLayout.PREFERRED_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(imgBtn)
                                        .addComponent(shuffleBtn)
                                        .addComponent(solveBtn))
                                .addContainerGap(65, Short.MAX_VALUE)));
        pack();
    }

    /* METHODS */
    // Add Listeners
    public void addListeners(ActionListener listener) {
        this.solveBtn.addActionListener(listener);
        this.comboBoxHeuristic.addActionListener(listener);
    }

    public int[][] getOrder() {
        int[][] array2d = new int[N][N];
        for (int i = 0; i < array2d.length; i++) {
            for (int j = 0; j < array2d.length; j++) {
                array2d[i][j] = order[i * N + j];
            }
        }
        return array2d;
    }

    public int[] getPos() {
        return new int[] { x / N, x % N };
    }

    public void showResults(int[][] result) {
        order = Stream.of(result).flatMapToInt(IntStream::of).toArray();
        for (int i = 0; i < N * N; i++) {
            if (order[i] == 8) {
                x = i;
            }
            if (icon[order[i]] == null) {
                label[i].setIcon(null);
            } else {
                label[i].setIcon(new ImageIcon(icon[order[i]]));
            }
        }
    }

    public Heuristica getHeuristica() {
        return HEURISTICS[comboBoxHeuristic.getSelectedIndex()];
    }

    private void loadImg() {
        JFileChooser chooser = new JFileChooser("test");
        // filtrar solo pillar imagenes
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            try {
                img = ImageIO.read(chooser.getSelectedFile());
                updateImgPanel();
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    private void updateImgPanel() {
        // filtrar solo pillar imagenes
        int width = img.getWidth() / N;
        int height = img.getHeight() / N;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (i == N - 1 && j == N - 1) {
                    icon[i * N + j] = null;
                } else {
                    icon[i * N + j] = img.getSubimage(j * width, i * height, width, height);
                }

                if (icon[i * N + j] == null) {
                    label[i * N + j].setIcon(null);
                } else {
                    label[i * N + j].setIcon(new ImageIcon(icon[i * N + j]));
                }
            }
        }
        pack();
    }

    public int getN() {
        return N;
    }

    private void changeSize() {
        N = (int) spinnerSize.getValue();
        imgPanel.removeAll();
        getContentPane().removeAll();
        addComponents();
    }

    private void swapTiles(MouseEvent evt) {
        int size = img.getWidth() / N;
        int i = evt.getY() / size;
        int j = evt.getX() / size;
        int emptyX = this.x % N;
        int emptyY = this.x / N;
        System.out.println("size: " + size);
        System.out.println(" evt.getX(): " + evt.getX() + ",  evt.getY(): " + evt.getY());
        System.out.println("i: " + i + ", j: " + j);
        System.out.println("emptyX: " + emptyX + ", emptyY: " + emptyY + "\n");
        if (i != emptyY || j != emptyX) {
            
            System.out.println("tomapre");
            if (i == emptyY + 1 && j == emptyX) {
                swap(N);
            } else if (i == emptyY - 1 && j == emptyX) {
                swap(-N);
            } else if (i == emptyY && j == emptyX + 1) {
                swap(1);
            } else if (i == emptyY && j == (emptyX - 1)) {
                swap(-1);
            }
            for (int k = 0; k < N * N; k++) {
                if (icon[order[k]] == null) {
                    label[k].setIcon(null);
                } else {
                    label[k].setIcon(new ImageIcon(icon[order[k]]));
                }
                label[k].repaint();
            }
        }
        this.imgPanel.repaint();

    }

    private void shuffle() {
        Random random = new Random();
        final int UP = 0, DOWN = 1, LEFT = 2, RIGHT = 3;
        int previousMovement = -1;
        int numMovimientos = 10; // 10 + N * N * N;

        for (int i = 0; i < numMovimientos; i++) {
            int move = random.nextInt(4);
            switch (move) {
                case UP -> {
                    if (previousMovement != DOWN && x / N > 0) {
                        swap(-N);
                        previousMovement = UP;
                    } else {
                        numMovimientos++;
                    }
                }
                case DOWN -> {
                    if (previousMovement != UP && x / N < N - 1) {
                        swap(N);
                        previousMovement = DOWN;
                    } else {
                        numMovimientos++;
                    }
                }
                case LEFT -> {
                    if (previousMovement != RIGHT && x % N > 0) {
                        swap(-1);
                        previousMovement = LEFT;
                    } else {
                        numMovimientos++;
                    }
                }
                case RIGHT -> {
                    if (previousMovement != LEFT && x % N < N - 1) {
                        swap(1);
                        previousMovement = RIGHT;
                    } else {
                        numMovimientos++;
                    }
                }
            }
        }
        for (int i = 0; i < N * N; i++) {
            if (icon[order[i]] == null) {
                label[i].setIcon(null);
            } else {
                label[i].setIcon(new ImageIcon(icon[order[i]]));
            }
        }
    }

    private void swap(int movement) {
        int temp = order[x];
        order[x] = order[x + movement];
        order[x + movement] = temp;
        x += movement;
    }
}
