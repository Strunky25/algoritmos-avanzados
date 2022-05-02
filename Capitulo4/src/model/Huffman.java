/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.PriorityQueue;

/**
 *
 * @author elsho
 */
public class Huffman {

    private HashMap<Byte, String> codes;
    private PriorityQueue<Node> queue;
    private Node treeRoot;

    public Huffman() {
        codes = new HashMap<>();
        queue = new PriorityQueue<>();
        treeRoot = null;
    }

    public void createTree(HashMap<Byte, Integer> freqs) {
        //Create the heap with the frequencies, creating a Node object for each byte and adding it to the heap.
        //The Node objects are comparable by frequency, so the heap is ordered by frequency.
        freqs.forEach((k, v) -> queue.add(new Node(k, v)));
        //Create the tree by merging the two nodes with the lowest frequency.
        while (queue.size() > 1) {
            Node parent = new Node(queue.poll(), queue.poll());
            queue.add(parent);
        }
        treeRoot = queue.poll();
    }

    /**
     * Method that creates the Huffman codes for each byte.
     * @return HashMap containing Codes
     */
    public HashMap<Byte, String> generateCodes() {
        generateCode(treeRoot, "");
        return codes;
    }

    public Node getTree() {
        return treeRoot;
    }

    /**
     * Method that creates the huffman codes for each byte.
     * @param root Root of the tree.
     */
    private void generateCode(Node node, String code) {
        //Recursive method that creates the huffman codes for each byte, starting from the root,
        //with a preorden traversal.
        if (node.isLeaf) {
            codes.put(node.value, code);
        } else {
            generateCode(node.left, code + "0");
            generateCode(node.right, code + "1");
        }
    }

    public static class Node implements Comparable<Node>, Serializable {
        private byte value;
        private int frecuency;
        private boolean isLeaf;
        private Node left, right;

        public Node(byte value, int frecuency) {
            this.value = value;
            this.frecuency = frecuency;
            this.isLeaf = true;
        }

        public Node(Node left, Node right) {
            this.left = left;
            this.right = right;
            this.frecuency = left.frecuency + right.frecuency;
            this.isLeaf = false;
        }

        @Override
        public int compareTo(Node node) {
            if (this.frecuency < node.frecuency) {
                return -1;
            } else if (this.frecuency > node.frecuency) {
                return 1;
            }
            return 0;        
        }
        
        public Node getLeft() {
            return left;
        }

        public Node getRight() {
            return right;
        }
        
        public boolean isLeaf(){
            return isLeaf;
        }
        
        public byte getValue(){
            return value;
        }
    }
}
