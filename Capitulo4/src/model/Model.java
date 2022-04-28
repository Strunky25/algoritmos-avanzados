/*
    Algoritmes Avançats - Capitulo 4
    Autors:
        Jonathan Salisbury Vega
        Joan Sansó Pericàs
        Joan Vilella Candia
*/
package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.util.Collections;
import java.util.HashMap;
import java.util.PriorityQueue;

/**
 * Class that contains the methods that simulate the various complexities.
 */
public class Model {

    /* Constants */
    private static final int BUFFER_SIZE = 512;

    /* Variables */
    private HashMap<Byte, String> huffmanCodes;

    /* Methods */
    public Model() {}

    public void compress(File input) {
        HashMap<Byte, Integer> frequencies = getFrequencies(input);
        double entropy = calculateEntropy(frequencies);
        System.out.println("Entropy: " + entropy);
        PriorityQueue<Node> heap = createHeap(frequencies);
        Node treeRoot = createTree(heap);
        // System.out.println(root.preordenTraversal());
        createCodes(treeRoot);
        double expectedSize = calculateExpectedSize(frequencies);
        System.out.println("Expected size: (bytes)" + expectedSize);
        System.out.println("Expected compression ratio: " + (1 - (expectedSize / input.length())));
        writeCompressedFile(input, treeRoot);
    }

    private double calculateEntropy(HashMap<Byte,Integer> freq){
        double entropy = 0;
        for(Byte b : freq.keySet()){
            double prob = (double)freq.get(b)/(double)freq.size();
            entropy += prob * (Math.log(prob)/Math.log(2));
        }
        return -entropy;
    }

    private double calculateExpectedSize(HashMap<Byte,Integer> freq){
        double expectedSize = 0;
        for(Byte b : freq.keySet()){
            int freqB = freq.get(b);
            expectedSize += freqB * (huffmanCodes.get(b).length()/8);
        }
        return expectedSize;
    }


    
    /**
     * Method that returns the frequencies of the characters in the text.
     * 
     * @param text Text to be analyzed.
     * @return HashMap with the frequencies of the characters.
     */
    private HashMap<Byte, Integer> getFrequencies(File input) {
        HashMap<Byte, Integer> frequencies = new HashMap<>();
        byte[] buffer = new byte[BUFFER_SIZE];  
        try (InputStream stream = new FileInputStream(input)){
            int readBytes = stream.read(buffer, 0, BUFFER_SIZE);
            while (readBytes != -1) {
                for (int i = 0; i < readBytes; i++) {
                    frequencies.put(buffer[i], frequencies.containsKey(buffer[i]) ? frequencies.get(buffer[i]) + 1 : 1);
                }
                readBytes = stream.read(buffer, 0, BUFFER_SIZE);
            }
        } catch (IOException e) { System.out.println(e.getMessage());}
        return frequencies;
    }

    private PriorityQueue<Node> createHeap(HashMap<Byte, Integer> frequencies) {
        PriorityQueue<Node> queue = new PriorityQueue<Node>();
        frequencies.forEach((k, v) -> queue.add(new Node(k, v)));
        return queue;
    }

    private Node createTree(PriorityQueue<Node> queue) {
        while (queue.size() > 1) {
            Node parent = new Node((Node) queue.poll(), (Node) queue.poll());
            queue.add(parent);
        }
        return (Node) queue.poll();
    }

    // traverse the huffman tree and generate the codes
    private void createCodes(Node root) {
        huffmanCodes = new HashMap<Byte, String>();
        addNode(root, "");
    }

    private void addNode(Node node, String code) {
        if (node.isLeaf()) {
            huffmanCodes.put(node.getValue(), code);
        } else {
            addNode(node.getLeft(), code + "0");
            addNode(node.getRight(), code + "1");
        }
    }
    
        private void writeCompressedFile(File output, Node root) {
        String path = output.getAbsolutePath() + ".huff";
        byte[] buffer = new byte[BUFFER_SIZE];
        try {
            FileInputStream readStream = new FileInputStream(output);
            
            /* Reserve space for the offset, will be overwritten after */
            FileOutputStream writeStream = new FileOutputStream(path);
            writeStream.write(0);
            writeStream.close();
            
            /* Write Huffman Tree Object */
            ObjectOutputStream objStr = new ObjectOutputStream(new FileOutputStream(path, true));
            objStr.writeObject(root);
            objStr.close();
            
            String strBuffer = "";
            writeStream = new FileOutputStream(path, true);
            int readBytes = readStream.read(buffer, 0, BUFFER_SIZE);
            while (readBytes != -1) {
                for (int i = 0; i < readBytes; i++) {
                    strBuffer += huffmanCodes.get(buffer[i]);
                }
                // "101010101 10101101 01101010101 01010101 0101" //revisar si +1 o -1 en uno de
                // los dos strings
                // // System.out.println("buffer: " + buffer);
                String aux = strBuffer.substring(0, strBuffer.length() - (strBuffer.length() % 8));
                if (strBuffer.length() % 8 != 0) {
                    strBuffer = strBuffer.substring(strBuffer.length() - strBuffer.length() % 8);
                } else {
                    strBuffer = "";
                }
                byte[] wData = new byte[aux.length() / 8];
                for (int i = 0; i < aux.length() / 8; i++) {
                    wData[i] = parseBits(aux.substring(i * 8, (i + 1) * 8));
                    // System.out.print(aux.substring(i * 8, (i + 1) * 8)+" ");
                }
                writeStream.write(wData);
                // // System.out.println("written compressed data");
                readBytes = readStream.read(buffer, 0, BUFFER_SIZE);
            }
            if (strBuffer.length() != 0) {
                // System.out.println("buffer length final: " + buffer.length());
                // 101
                int bits = 8 - strBuffer.length();
                strBuffer += String.join("", Collections.nCopies(bits, "0"));
                byte lastByte = parseBits(strBuffer);
                writeStream.write(lastByte);
                // System.out.println("written last byte");
                writeStream.flush();
                writeStream.close();
                RandomAccessFile ra = new RandomAccessFile(path, "rw");
                ra.write((byte) bits);
                // System.out.println("written offset byte at start of file: " + bits + " bits");
                ra.close();
            }
            readStream.close();
            writeStream.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    // Function to parse a String of bits into a byte
    private byte parseBits(String bits) {
        Integer aux = Integer.parseInt(bits, 2);
        return aux.byteValue();
    }

    // parse byte array to string of bits
    private String byteToBinaryString(byte b) {
        String bits = "";
        for (int i = 0; i < 8; i++) {
            bits += (b & (1 << (7 - i))) != 0 ? "1" : "0";
        }
        return bits;
    }

    // TODO: FIX LAST COUPLE BYTES ARE NOT WRITTEN
    public void decompress(File selectedFile) {
        try (FileInputStream fi = new FileInputStream(selectedFile)) {
            // 101
            byte extraBits = fi.readNBytes(1)[0];
            // System.out.println();
            // System.out.println("read offset byte");
            ObjectInputStream oi = new ObjectInputStream(fi);
            Node root = (Node) oi.readObject();
            // System.out.println("read huffman tree object");
            // System.out.println(root.preordenTraversal());
            // System.out.println(codes.toString());
            // reading bytes after the object works, you dont have to close the oi stream
            String path = selectedFile.getAbsolutePath();
            path = path.substring(0, path.length() - 5);
            path = path.replace(path.substring(path.lastIndexOf(".")),
                    "DECOMPRESSED" + path.substring(path.lastIndexOf(".")));
            // System.out.println("decompressed file path: " + path);
            OutputStream fo = new FileOutputStream(path);
            byte[] data = new byte[BUFFER_SIZE];
            int bytesRead = fi.read(data);
            boolean first = true;
            String buffer = "";
            // System.out.println();
            while (bytesRead != -1) {
                for (int i = 0; i < bytesRead; i++) {
                    buffer += byteToBinaryString(data[i]); // "101011011010101011101001000101010001010 101 00000"
                }
                // if the number of bytes read if smaller than the buffer size, that means that
                // we reached EOF
                // and we have to remove the extra padding bits from the binary string
                if (bytesRead < BUFFER_SIZE) {
                    // System.out.println("bytes read is not buffer size");
                    // System.out.println("extraBits to remove: " + extraBits);
                    // System.out.println("size before: " + buffer.length());
                    buffer = buffer.substring(0, buffer.length() - extraBits);
                    // System.out.println("size after: " + buffer.length());
                    bytesRead = fi.read(data, 0, BUFFER_SIZE);
                    // System.out.println("should be -1: " + bytesRead);
                } else {
                    // We read again, and if EOF is reached, we know that the buffer string
                    // contains the last byte, so we dont have to read again. So we remove
                    // the extra padding bits from the string
                    bytesRead = fi.read(data, 0, BUFFER_SIZE);
                    if (bytesRead == -1) {
                        // System.out.println("next iteration is -1");

                        // System.out.println("extraBits to remove: " + extraBits);
                        // System.out.println("size before: " + buffer.length());
                        buffer = buffer.substring(0, buffer.length() - extraBits);
                        // System.out.println("size after: " + buffer.length());
                    }
                }
                // traverse the huffman tree with the string of bits
                String currentSubstring = "";
                Node current = root;
                for (int i = 0; i < buffer.length(); i++) {
                    currentSubstring += buffer.charAt(i);
                    if (buffer.charAt(i) == '0') {
                        current = current.getLeft();
                    } else {
                        current = current.getRight();
                    }
                    if (current.isLeaf()) {
                        // System.out.print(current.value + " ");
                        fo.write(current.getValue());
                        current = root;
                        currentSubstring = "";
                    }
                }
                if (currentSubstring.length() != 0) {
                    buffer = currentSubstring;
                } else {
                    buffer = "";
                }
            }
            // System.out.println();
            // System.out.println("buffer size (should be 0): " + buffer.length());
            // System.out.println("buffer: "+buffer);
            fo.flush();
            fo.close();
            fi.close();
            // System.out.println("closed both streams");
        } catch (IOException | ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}