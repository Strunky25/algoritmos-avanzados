package model;

public class Node implements Comparable<Node> {
    byte value;
    int frecuency;
    boolean isLeaf;
    Node left;
    Node right;

    public Node(Node left, Node right) {
        this.left = left;
        this.right = right;
        this.frecuency = left.frecuency + right.frecuency;
        this.value = 0;
        this.isLeaf = false;
    }

    public Node(byte value, int frecuency) {
        this.value = value;
        this.frecuency = frecuency;
        this.isLeaf = true;
    }

    public byte getValue() {
        return value;
    }

    public void setValue(byte value) {
        this.value = value;
    }

    public int getFrecuency() {
        return frecuency;
    }

    public void setFrecuency(int frecuency) {
        this.frecuency = frecuency;
    }

    public Node getLeft() {
        return left;
    }

    public void setLeft(Node left) {
        this.left = left;
    }

    public Node getRight() {
        return right;
    }

    public void setRight(Node right) {
        this.right = right;
    }

    @Override
    public String toString() {
        return "TreeNode [value=" + value + ", frecuency=" + frecuency + "]";
    }

    public String inorderTraversal() {
        String result = "";
        if (left != null) {
            result += left.inorderTraversal();
        }
        if (this.isLeaf) {
            char v = (char) (value & 0xFF);
            result += v + ",";
        }
        result += frecuency + " ";
        if (right != null) {
            result += right.inorderTraversal();
        }
        return result;
    }

    public String preordenTraversal() {
        String result = "";
        if (this.isLeaf) {
            char v = (char) (value & 0xFF);
            result += v + ",";
        }
        result += frecuency + " ";
        if (left != null) {
            result += left.preordenTraversal();
        }
        if (right != null) {
            result += right.preordenTraversal();
        }
        return result;
    }

    @Override
    public int compareTo(Node o) {
        if (this.frecuency < o.frecuency) {
            return -1;
        }
        if (this.frecuency > o.frecuency) {
            return 1;
        }
        return 0;
    }

}
