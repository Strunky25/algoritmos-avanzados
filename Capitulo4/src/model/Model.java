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

/**
 * Class that contains the methods that simulate the various complexities.
 */
public class Model {

    /* Constants */
    private static final int BUFFER_SIZE = 512;
    private static final int BYTE_SIZE = 8;

    /* Variables */
    private HashMap<Byte, String> huffmanCodes;
    private final byte[] buffer;

    /* Methods */
    public Model() {
        buffer = new byte[BUFFER_SIZE];
    }

    public void compress(File input) {
        HashMap<Byte, Integer> frequencies = getFrequencies(input);
        Huffman huffman = new Huffman();
        huffman.createTree(frequencies);
        huffmanCodes = huffman.generateCodes();
        Huffman.Node root = huffman.getTree();
        File output = new File(input.getPath() + ".huff");
        compressAndWriteFile(input, output, root);
        
        double entropy = calculateTheoreticalEntropy(frequencies, input);
        double expectedSize = calculateExpectedSize(frequencies);
        System.out.println("Expected size: (bytes)" + expectedSize);
        System.out.println("Original size: (bytes)" + input.length());
        System.out.println("Expected compression ratio: " + expectedSize / input.length());
        System.out.println("Theoretical Entropy: " + entropy);
        System.out.println("Actual Entropy: " + calculateActualEntropy(frequencies, output));
    }

    /**
     * Method that returns the frequencies of the bytes in the file.
     * 
     * @param input File to be analyzed.
     * @return HashMap with the frequencies of the btyes.
     */
    private HashMap<Byte, Integer> getFrequencies(File input) {
        //Create the HashMap for the frequencies
        HashMap<Byte, Integer> frequencies = new HashMap<>();
        //Start reading the file, reading into the buffer and incrementing the frequency of the bytes read.  
        try (InputStream stream = new FileInputStream(input)) {
            int readBytes = stream.read(buffer, 0, BUFFER_SIZE);
            while (readBytes != -1) {
                for (int i = 0; i < readBytes; i++) {
                    //If the byte is not in the HashMap, add it with a frequency of 1.
                    //If it is, increment the frequency.
                    frequencies.put(buffer[i], frequencies.containsKey(buffer[i]) ? frequencies.get(buffer[i]) + 1 : 1);
                }
                readBytes = stream.read(buffer, 0, BUFFER_SIZE);
            }
        } catch (IOException e) { System.out.println(e.getMessage());}
        return frequencies;
    }
    
    private double calculateTheoreticalEntropy(HashMap<Byte, Integer> freqs, File input) {
        double entropy = 0;
        double size = input.length();
        //Calculate the entropy of the file, summing the entropy of each byte.
        for(Byte key : freqs.keySet()){
            double prob = (double) freqs.get(key)/size;
            entropy += prob * (Math.log(prob) /Math.log(2));
        }
        return -entropy;
    }

    private double calculateActualEntropy(HashMap<Byte,Integer> freq, File out){
        double entropy = 0;
        double filesize = out.length();
        //Calculate the entropy of the file, summing the entropy of each byte.
        for(Byte key : freq.keySet()){
            double prob = (double) freq.get(key)/filesize;
            entropy += prob * (Math.log(prob) /Math.log(2));
        }
        return -entropy;
    }
    
    private double calculateExpectedSize(HashMap<Byte,Integer> freq){
        //Calculate the expected size of the file, without the size of the huffman tree and the offset byte,
        // so just the compressed data.
        double expectedSize = 0;
        for(Byte key : freq.keySet()){
            expectedSize += freq.get(key) * (huffmanCodes.get(key).length()/(double)BYTE_SIZE);
        }
        return expectedSize;
    }

    private void compressAndWriteFile(File input, File output, Huffman.Node treeRoot) {
        //Open the read and write streams.
        try (FileInputStream reader = new FileInputStream(input);
                FileOutputStream writer = new FileOutputStream(output);) {
            /* Reserve space for the offset of bits of the last byte, will be overwritten after
            if the offset is different than 0 (so it will be overwritten if the last byte has needed padding) */
            writer.write(0);
            /* Write Huffman Tree Object */
            ObjectOutputStream objStr = new ObjectOutputStream(writer);
            objStr.writeObject(treeRoot);
            //Initiate the buffer that will contain the compressed data of each read buffer.
            String strBuffer = "";
            byte[] bufferOut;
            int readBytes = reader.read(buffer, 0, BUFFER_SIZE);
            while (readBytes != -1) {
                //Adding the huffman codes to the String buffer.
                for (int i = 0; i < readBytes; i++) {
                    strBuffer += huffmanCodes.get(buffer[i]);
                }
                //If the String buffer length is not a multiple of 8, only process the substring
                //that contains whole bytes and leave the rest in the buffer for the next read.
                int nBytes = strBuffer.length() / BYTE_SIZE * BYTE_SIZE;
                String aux = strBuffer.substring(0, nBytes);
                bufferOut = new byte[aux.length() / BYTE_SIZE];
                //Convert the String buffer to a byte array.
                for (int i = 0; i < bufferOut.length; i++) {
                    bufferOut[i] = parseByte(aux.substring(i * BYTE_SIZE, (i + 1) * BYTE_SIZE));
                }
                //Write the compressed data into the file
                writer.write(bufferOut);
                //Remove the already written data from the buffer and only leave the
                //last bits for the next iteration (the ones that are not a multiple of 8).
                strBuffer = strBuffer.substring(nBytes);
                //Read the next buffer.
                readBytes = reader.read(buffer, 0, BUFFER_SIZE);
            }
            //If the last byte has needed padding, write the offset of the last byte.
            if (strBuffer.length() != 0)
                writeLastByte(strBuffer, output, writer);
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }

    private void writeLastByte(String buffer, File output, FileOutputStream writer) {
        //Get how many zeroes are needed to fill the last byte.
        int nBits = BYTE_SIZE - buffer.length();
        //Fill the last byte with zeroes.
        buffer += String.join("", Collections.nCopies(nBits, "0"));
        //Convert the String to a byte and write it to the file.
        byte lastByte = parseByte(buffer);
        try (RandomAccessFile ra = new RandomAccessFile(output, "rw");) {
            writer.write(lastByte);
            //Now access the file with a random access and overwrite the offset of 
            //the last byte at the start of the file.
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

    // parse byte to string of bits (ones and zeroes)
    private String byteToBinaryString(byte b) {
        String bits = "";
        for (int i = 0; i < BYTE_SIZE; i++) {
            bits += (b & (1 << (7 - i))) != 0 ? "1" : "0";
        }
        return bits;
    }
    
    public void decompress(File input) {
        File output = new File(input.getPath() + ".dec");
        aux(input, output);
    }
    
    private void aux(File input, File output){
        //Open the read stream.
        try (FileInputStream reader = new FileInputStream(input)) {
            //Read the first byte, which contains how many zeroes have been
            // padded to the last byte of the file.
            byte extraBits = reader.readNBytes(1)[0];
            //After that, read the Huffman Tree Object.
            ObjectInputStream oi = new ObjectInputStream(reader);
            Huffman.Node root = (Huffman.Node) oi.readObject();
            //Now the pointer of the file reader is at the start of the compressed data.
            //Create the path of the decompressed file, adding "_decompressed" to the name of the file.
            String path = input.getAbsolutePath();
            path = path.substring(0, path.length() - 5);
            path = path.replace(path.substring(path.lastIndexOf(".")),
                    "_decompressed" + path.substring(path.lastIndexOf(".")));
            //And open the write stream of the decompressed file.
            OutputStream fo = new FileOutputStream(path);
            String strBuffer = "";
            //Read the compressed data into the byte buffer.
            int bytesRead = reader.read(buffer);
            while (bytesRead != -1) {
                for (int i = 0; i < bytesRead; i++) {
                    //Add the bits of the byte to the String buffer
                    strBuffer += byteToBinaryString(buffer[i]);
                }
                // if the number of bytes read if smaller than the buffer size, that means that
                // we reached EOF
                // and we have to remove the extra padding bits read at the start from the binary string
                if (bytesRead < BUFFER_SIZE) {
                    strBuffer = strBuffer.substring(0, strBuffer.length() - extraBits);
                    bytesRead = reader.read(buffer, 0, BUFFER_SIZE);
                } else {
                    // We read again, and if EOF is reached, we know that the buffer string
                    // contains the last byte, so we dont have to read again. So we remove
                    // the extra padding bits from the string
                    bytesRead = reader.read(buffer, 0, BUFFER_SIZE);
                    if (bytesRead == -1) {
                        strBuffer = strBuffer.substring(0, strBuffer.length() - extraBits);
                    }
                }
                // traverse the huffman tree with the string of bits
                //Initiate a string representing the current traversal of the Huffman Tree
                //This will allow us to keep going with the decoding process between two
                // different read iterations, because the code of the last byte might be cut and read into
                //the next buffer. If that happens, when we reach the end of the decoding process, we wont be
                //at a leaf node, so we will add the current traversal to the String buffer and start
                //the decoding process again in the next iteration
                //When a byte is decoded, the traversal of the tree is reset and the current
                //substring is emptied.
                String currSubStr = "";
                Huffman.Node current = root;
                for (int i = 0; i < strBuffer.length(); i++) {
                    currSubStr += strBuffer.charAt(i);
                    if (strBuffer.charAt(i) == '0') {
                        current = current.getLeft();
                    } else {
                        current = current.getRight();
                    }
                    if (current.isLeaf()) {
                        fo.write(current.getValue());
                        current = root;
                        currSubStr = "";
                    }
                }
                strBuffer = currSubStr.length() != 0 ? currSubStr : "";
            }
            fo.flush();
            fo.close();
            reader.close();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
}