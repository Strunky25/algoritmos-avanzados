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
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
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
    private static final int BUFFER_SIZE = 1024;
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
                    frequencies[(Byte.valueOf(data[i])).intValue()+127]++;
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
                queue.add(new Node(i, frecuencies[i]));
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

    HashMap<Integer,String> codes;
    //traverse the huffman tree and generate the codes
    private void createCodes(Node root) {
        codes = new HashMap<Integer,String>();
        addNode(root,"");
    }

    private void addNode(Node node, String code){
        if(node.isLeaf){
            codes.put(node.value,code);
        }else{
            addNode(node.left,code+"0");
            addNode(node.right,code+"1");
        }
    }

    //Function to parse a String of bits into a byte
    private byte parseBits(String bits) {
        int value = 0;
        for (int i = 0; i < bits.length(); i++) {
            value += (bits.charAt(i) == '1' ? 1 : 0) << (bits.length() - i - 1);
        }
        return (byte) (value - 127);
    }
    

    private void writeCompressedFile(File f, Node root){
        String path = f.getAbsolutePath();
        path = path.substring(0, path.lastIndexOf("."));
        path += ".huff";
        byte[] data = new byte[BUFFER_SIZE];
        InputStream readStream = null;
        OutputStream writeStream = null;
        try{
            readStream = new FileInputStream(f);
            writeStream = new java.io.FileOutputStream(path);
        } catch (IOException e) {
            System.out.println("Error reading file");
        }
        try {
            //We write one byte to reserve space for the offste of the last byte,
            // which we will replace at the end for the correct value
            writeStream.write(0);
            //then we write the huffman tree
            writeStream.close();
            ObjectOutputStream objStr = new ObjectOutputStream(new java.io.FileOutputStream(path, true));
            objStr.writeObject(root);
            objStr.close();
            writeStream = new FileOutputStream(path,true);
            int retValue = readStream.read(data, 0, BUFFER_SIZE);
            String buffer = "";
            while (retValue != -1) {
                for(int i = 0; i < retValue; i++) {
                    buffer+= codes.get((Byte.valueOf(data[i])).intValue()+127);
                }
                retValue = readStream.read(data, 0, BUFFER_SIZE);
                String aux = buffer.substring(0, buffer.length() / 8);
                if(buffer.length() % 8 != 0) {
                    buffer = buffer.substring(buffer.length() / 8);
                }
                byte[] wData = new byte[aux.length() / 8];
                for(int i = 0; i < aux.length() / 8; i++) {
                    wData[i] = parseBits(aux.substring(i * 8, (i + 1) * 8));
                }
                writeStream.write(wData);
                if(retValue == -1 && buffer.length() != 0) {
                    //pad the last byte with 0's and count the number of bits
                    int bits = buffer.length();
                    while(bits % 8 != 0) {
                        buffer += "0";
                    }
                    byte lastByte = parseBits(buffer);
                    writeStream.write(lastByte);
                    writeStream.close();
                    writeStream = new FileOutputStream(path,false);
                }
            }
            readStream.close();
            writeStream.close();
            //Now we write the actual
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    public void compress(File selectedFile) {
        int[] frequencies = getFrequencies(selectedFile);
        PriorityQueue<Node> heap = createHeap(frequencies);
        Node root = createTree(heap);
        System.out.println(root.preordenTraversal());
        createCodes(root);
        writeCompressedFile(selectedFile, root);
    }

    public void decompress(File selectedFile) {

    }
}
