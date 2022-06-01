/*
    Algoritmes Avançats - Capitulo 6
    Autors:
        Jonathan Salisbury Vega
        Joan Sansó Pericàs
        Joan Vilella Candia
 */
package view;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Class that contains the Swing GUI for the project.
 */
public class View extends JFrame {

    /* Constants */
    private static final int N = 3;

    /* Variables */
    private int[] order;
    private BufferedImage[] icon;
    private int x;

    /* Swing Components */
    private JButton imgBtn, shuffleBtn, solveBtn;
    private JPanel imgPanel;
    private JLabel[] label;

    public View() {
        initComponents();
    }

    /**
     * Method that initializes swing components, and sets JFrame properties.
     */
    private void initComponents() {
        setTitle("Capitulo 6");
        setIconImage(new ImageIcon("resources/icon.png").getImage());
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        /* Init Components */
        imgBtn = new JButton("Load Image");
        imgBtn.addActionListener((e) -> loadImg());
        shuffleBtn = new JButton("Shuffle");
        shuffleBtn.addActionListener((e) -> shuffle());
        solveBtn = new JButton("Solve");

        imgPanel = new JPanel();
        imgPanel.setBackground(Color.lightGray);

        order = new int[N * N];
        icon = new BufferedImage[N * N];
        label = new JLabel[N * N];
        for (int i = 0; i < N * N; i++) {
            order[i] = i;
            label[i] = new JLabel();
            label[i].setBorder(BorderFactory.createLineBorder(Color.black));
        }
        x = N * N - 1;
        addComponents();
        setResizable(false);
        setLocationRelativeTo(null);
    }

    /**
     * Method that creates the frame layout with all its components.
     */
    private void addComponents() {
        /* Add Layout */
        GridLayout grid = new GridLayout(N, N);
        imgPanel.setLayout(grid);
        for (JLabel lab : label) {
            imgPanel.add(lab);
        }
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(50, 50, 50)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(imgBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(72, 72, 72)
                                                .addComponent(shuffleBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(solveBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addComponent(imgPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(50, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(33, 33, 33)
                                .addComponent(imgPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(29, 29, 29)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(imgBtn)
                                        .addComponent(shuffleBtn)
                                        .addComponent(solveBtn))
                                .addContainerGap(64, Short.MAX_VALUE))
        );
        pack();
    }

    /* METHODS */
    // Add Listeners
    public void addListeners(ActionListener listener) {
        this.solveBtn.addActionListener(listener);
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
        return new int[]{x / N, x % N};
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

    private void loadImg() {
        JFileChooser chooser = new JFileChooser("test");
        // filtrar solo pillar imagenes
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            try {
                BufferedImage img = ImageIO.read(chooser.getSelectedFile());
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
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    /* 
    private void shuffle() {
        Random random = new Random();
        int[] moves = {1, -N, -1, N};
        for (int i = 0; i < N * N; i++) {
            int move = moves[random.nextInt(moves.length)];
            int j = i + move;
            if(j > 0 && j < N * N){
                int temp = order[i];
                order[i] = order[j];
                order[j] = temp;
            }
        }
        //System.out.println(Arrays.toString(order));
        for (int i = 0; i < N * N; i++) {
            if(order[i] == 8) x = i;
            if(icon[order[i]] == null){
                label[i].setIcon(null);
            }
            else label[i].setIcon(new ImageIcon(icon[order[i]]));
        }
    }
     */
    private void shuffle() {
        Random random = new Random();
        final int UP = 0, DOWN = 1, LEFT = 2, RIGHT = 3;
        int previousMovement = -1;
        int numMovimientos = 10 + N * N * N;

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
