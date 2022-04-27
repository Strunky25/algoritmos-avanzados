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

    /* Methods */
    public Model() {
    }

    /**
     * Method that returns the frequencies of the characters in the text.
     * 
     * @param text Text to be analyzed.
     * @return HashMap with the frequencies of the characters.
     */
    private HashMap<Byte, Integer> getFrequencies(File f) {
        HashMap<Byte, Integer> frequencies = new HashMap<>();
        byte[] buffer = new byte[BUFFER_SIZE];  
        try (InputStream stream = new FileInputStream(f)){
            int readBytes = stream.read(buffer, 0, BUFFER_SIZE);
            while (readBytes != -1) {
                for (int i = 0; i < readBytes; i++) {
                    //frequencies.put(buffer[i], frequencies.containsKey(buffer[i]) ? frequencies.get(buffer[i]) + 1 : 1);
                    if (frequencies.containsKey(buffer[i])) {
                        frequencies.put(buffer[i], frequencies.get(buffer[i]) + 1);
                    } else {
                        frequencies.put(buffer[i], 1);
                    }
                }
                readBytes = stream.read(buffer, 0, BUFFER_SIZE);
            }
            //stream.close();
        } catch (IOException e) { System.out.println(e.getMessage());}
        return frequencies;
    }

    private PriorityQueue<Node> createHeap(HashMap<Byte, Integer> frequencies) {
        PriorityQueue<Node> queue = new PriorityQueue<Node>();
        //frequencies.forEach((k, v) -> queue.add(new Node(k, v)));
        for (Byte key : frequencies.keySet()) {
            queue.add(new Node(key, frequencies.get(key)));
        }
        return queue;
    }

    private Node createTree(PriorityQueue<Node> queue) {
        while (queue.size() > 1) {
            Node left = (Node) queue.poll();
            Node right = (Node) queue.poll();
            Node parent = new Node(left, right);
            queue.add(parent);
        }
        return (Node) queue.poll();
    }

    HashMap<Byte, String> codes;

    // traverse the huffman tree and generate the codes
    private void createCodes(Node root) {
        codes = new HashMap<Byte, String>();
        addNode(root, "");
    }

    private void addNode(Node node, String code) {
        if (node.isLeaf) {
            codes.put(node.value, code);
        } else {
            addNode(node.left, code + "0");
            addNode(node.right, code + "1");
        }
    }

    // Function to parse a String of bits into a byte
    private byte parseBits(String bits) {
        Integer aux = Integer.parseInt(bits, 2);
        return aux.byteValue();
    }

    private void writeCompressedFile(File f, Node root) {
        String path = f.getAbsolutePath();
        path += ".huff";
        byte[] data = new byte[BUFFER_SIZE];
        InputStream readStream = null;
        OutputStream writeStream = null;
        try {
            readStream = new FileInputStream(f);
            writeStream = new java.io.FileOutputStream(path);
        } catch (IOException e) {
            // System.out.println("Error reading file");
        }
        try {
            // We write one byte to reserve space for the offste of the last byte,
            // which we will replace at the end for the correct value
            writeStream.write(0);
            // System.out.println("written dummy byte");
            // then we write the huffman tree
            writeStream.close();
            ObjectOutputStream objStr = new ObjectOutputStream(new java.io.FileOutputStream(path, true));
            objStr.writeObject(root);
            // System.out.println("written huffman tree object");
            objStr.close();
            writeStream = new FileOutputStream(path, true);
            int retValue = readStream.read(data, 0, BUFFER_SIZE);
            String buffer = "";
            boolean first = true;
            // System.out.println();
            while (retValue != -1) {
                for (int i = 0; i < retValue; i++) {
                    buffer += codes.get(Byte.valueOf(data[i]));
                }
                // "101010101 10101101 01101010101 01010101 0101" //revisar si +1 o -1 en uno de
                // los dos strings
                // // System.out.println("buffer: " + buffer);
                String aux = buffer.substring(0, buffer.length() - (buffer.length() % 8));
                if (buffer.length() % 8 != 0) {
                    buffer = buffer.substring(buffer.length() - buffer.length() % 8);
                } else {
                    buffer = "";
                }
                byte[] wData = new byte[aux.length() / 8];
                for (int i = 0; i < aux.length() / 8; i++) {
                    wData[i] = parseBits(aux.substring(i * 8, (i + 1) * 8));
                    // System.out.print(aux.substring(i * 8, (i + 1) * 8)+" ");
                }
                writeStream.write(wData);
                // // System.out.println("written compressed data");
                retValue = readStream.read(data, 0, BUFFER_SIZE);
            }
            if (buffer.length() != 0) {
                // System.out.println("buffer length final: " + buffer.length());
                // 101
                int bits = 8 - buffer.length();
                buffer += String.join("", Collections.nCopies(bits, "0"));
                byte lastByte = parseBits(buffer);
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
            try {
                writeStream.close();
            } catch (IOException ignore) {
            }
            // Now we write the actual
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    public void compress(File selectedFile) {
        HashMap<Byte, Integer> frequencies = getFrequencies(selectedFile);
        PriorityQueue<Node> heap = createHeap(frequencies);
        Node root = createTree(heap);
        // System.out.println(root.preordenTraversal());
        createCodes(root);
        writeCompressedFile(selectedFile, root);
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
                        current = current.left;
                    } else {
                        current = current.right;
                    }
                    if (current.isLeaf) {
                        // System.out.print(current.value + " ");
                        fo.write(current.value);
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
