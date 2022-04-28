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
    private static final int BYTE_SIZE = 8;

    /* Variables */
    private HashMap<Byte, String> huffmanCodes;

    /* Methods */
    public Model() {}

    public void compress(File input) {
        HashMap<Byte, Integer> frequencies = getFrequencies(input);
        PriorityQueue<Node> heap = createHeap(frequencies);
        Node treeRoot = createTree(heap);
        createCodes(treeRoot);
        
        double entropy = calculateEntropy(frequencies);
        double expectedSize = calculateExpectedSize(frequencies);
        System.out.println("Expected size: (bytes)" + expectedSize);
        System.out.println("Expected compression ratio: " + (1 - (expectedSize / input.length())));
        System.out.println("Entropy: " + entropy);
        
        File output = new File(input.getPath() + ".huff");
        writeCompressedFile(input, output, treeRoot);
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
        PriorityQueue<Node> queue = new PriorityQueue<>();
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

    // Cambiar a Iterativo??
    private void createCodes(Node root) {
        huffmanCodes = new HashMap<>();
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
    
    private double calculateEntropy(HashMap<Byte,Integer> freq){
        double entropy = 0;
        for(Byte key : freq.keySet()){
            double prob = (double) freq.get(key)/freq.size();
            entropy += prob * (Math.log(prob) /Math.log(2));
        }
        return -entropy;
    }
    
    private double calculateExpectedSize(HashMap<Byte,Integer> freq){
        double expectedSize = 0;
        for(Byte key : freq.keySet()){
            expectedSize += freq.get(key) * (huffmanCodes.get(key).length()/BYTE_SIZE);
        }
        return expectedSize;
    }
    
    private void writeCompressedFile(File input, File output, Node treeRoot) {
        try (FileInputStream reader = new FileInputStream(input);
                FileOutputStream writer = new FileOutputStream(output);){ 
            /* Reserve space for the offset, will be overwritten after */
            writer.write(0);

            /* Write Huffman Tree Object */
            ObjectOutputStream objStr = new ObjectOutputStream(writer);
            objStr.writeObject(treeRoot);

            String strBuffer = "";
            byte[] bufferIn = new byte[BUFFER_SIZE], bufferOut;
            int readBytes = reader.read(bufferIn, 0, BUFFER_SIZE);
            while (readBytes != -1) {
                for (int i = 0; i < readBytes; i++) {
                    strBuffer += huffmanCodes.get(bufferIn[i]);
                }
                int nBytes = strBuffer.length() / BYTE_SIZE * BYTE_SIZE;
                String aux = strBuffer.substring(0, nBytes);
                bufferOut = new byte[aux.length() / BYTE_SIZE];
                for (int i = 0; i < bufferOut.length; i++) {
                    bufferOut[i] = parseByte(aux.substring(i * BYTE_SIZE, (i + 1) * BYTE_SIZE));
                }
                writer.write(bufferOut);
                strBuffer = strBuffer.substring(nBytes);
                readBytes = reader.read(bufferIn, 0, BUFFER_SIZE);
            }
            if (strBuffer.length() != 0) writeLastByte(strBuffer, output, writer);
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }
    
    private void writeLastByte(String buffer, File output, FileOutputStream writer){
        int nBits = BYTE_SIZE - buffer.length();
        buffer += String.join("", Collections.nCopies(nBits, "0"));
        byte lastByte = parseByte(buffer);
        try (RandomAccessFile ra = new RandomAccessFile(output, "rw");){
            writer.write(lastByte);
            ra.write((byte) nBits);
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }

    // Function to parse a String of bits into a byte
    private byte parseByte(String byteStr) {
        Integer aux = Integer.parseInt(byteStr, 2);
        return aux.byteValue();
    }

    // parse byte array to string of bits
    private String byteToBinaryString(byte b) {
        String bits = "";
        for (int i = 0; i < BYTE_SIZE; i++) {
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
                    "_decompressed" + path.substring(path.lastIndexOf(".")));
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