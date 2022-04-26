/*
    Algoritmes Avançats - Capitulo 4
    Autors:
        Jonathan Salisbury Vega
        Joan Sansó Pericàs
        Joan Vilella Candia
*/
package model;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.PriorityQueue;
/**
 * Class that contains the methods that simulate the various complexities.
 */
public class Model {

    /* Constants */
    private static final int BUFFER_SIZE = 256;
    /* Variables */
    private HashMap frequencies;

    /* Methods */
    public Model() {
        this.frequencies = new HashMap<>();
    }

    /**
     * Method that returns the frequencies of the characters in the text.
     * 
     * @param text Text to be analyzed.
     * @return HashMap with the frequencies of the characters.
     */
    private int[] getFrequencies(File f) {
        int[] frequencies = new int[256];
        // read file with readbtyes
        byte[] data = new byte[BUFFER_SIZE];
        InputStream stream = null;
        try{
            stream = new FileInputStream(f);
        } catch (IOException e) {
            System.out.println("Error reading file");
        }
        try {
            int retValue = stream.read(data, 0, BUFFER_SIZE);
            while (retValue != -1) {
                for (int i = 0; i < retValue; i++) {
                    frequencies[data[i]]++;
                }
                retValue = stream.read(data, 0, BUFFER_SIZE);
            }
            stream.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return frequencies;
    }


    private PriorityQueue<Node> createHeap(int[] frecuencies) {
        PriorityQueue<Node> queue = new PriorityQueue<Node>();
        for(int i = 0; i < frecuencies.length; i++) {
            if(frecuencies[i] != 0) {
                queue.add(new Node((byte)i, frecuencies[i]));
            }
        }
        return queue;
    }

    private Node createTree(PriorityQueue<Node> queue) {
        while(queue.size() > 1) {
            Node node1 = (Node) queue.poll();
            Node node2 = (Node) queue.poll();
            Node node3 = new Node(node1, node2);
            queue.add(node3);
        }
        return (Node) queue.poll();
    }


    public void compress(File selectedFile) {
        System.out.println("aaaaa");
        int[] frequencies = getFrequencies(selectedFile);
        PriorityQueue<Node> heap = createHeap(frequencies);
        Node root = createTree(heap);
        System.out.println(root.preordenTraversal());
    }

    public void decompress(File selectedFile) {

    }
}
