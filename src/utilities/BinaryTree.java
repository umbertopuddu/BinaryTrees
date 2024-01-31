package utilities;

import java.util.List;
import java.util.ArrayList;

public class BinaryTree<T> {
    
    private Node root;
    
    public class Node{
        private T data;
        private Node left;
        private Node right;
        private Node parent;
        private boolean direction;
        
        Node(T data){
            this.data = data;
            left = null;
            right = null;
        }
        
        Node(T data, boolean direction, Node parent){
            this.data = data;
            left = null;
            right = null;
            this.parent = parent;
            this.direction = direction;
        }
        
        Node getLeft(){
            return left;
        }
        
        Node getRight(){
            return right;
        }
        
        public T getData(){
            return data;
        }
        
        Node getParent() {
            return parent;
        }
        
        void setParent(Node parent){
            this.parent = parent;
        }
        
        boolean getDirection(){
            return direction;
        }
        
        void setDirection(boolean direction){
            this.direction = direction;
        }
        
        void setLeft(T left){
            this.left = new Node(left, false, this);
        }
        
        void setRight(T right){
            this.right = new Node(right, true, this);
        }
        
        void setRightNode(Node right){
            this.right = right;
            if (right != null) {
                right.setParent(this);
                right.setDirection(true);
            }
        }
        
        void setLeftNode(Node left){
            this.left = left;
            if (left != null) {
                left.setParent(this);
                left.setDirection(false);
            }
        }
        

        void setData(T data){
            this.data = data;
        }
        
        
    }
    
    public BinaryTree(){
        this.root = null;
    }
    public BinaryTree(T rootData){
        this.root = new Node(rootData);
    }
    
    public void addNode(T data, Node previous, boolean direction){
        if(!direction){
            previous.setLeft(data);
        }
        else{
            previous.setRight(data);
        }
    }
    
    public void removeNode(T data) throws Exception {
        if (!remove(root, data)) {
            throw new Exception("Element not found");
        }
    }
    
    private boolean remove(Node current, T remove) {
        if (current == null) {
            return false;
        }
        if (current.getData().equals(remove)) {
            erase(current);
            return true;
        } else {
            erase(find(remove));
            return true;
        }
    }
    
    public Node find(T data){
        return findNode(root, data);
    }
    
    private Node findNode(Node current, T data) {
        if (current == null) {
            return null;
        }
    
        if (current.getData().equals(data)) {
            return current;
        }
    
        Node leftResult = findNode(current.getLeft(), data);
        if (leftResult != null) {
            return leftResult;
        }
        Node rightResult = findNode(current.getRight(), data);
        if(rightResult != null){
            return rightResult; 
        }
        else{
            return null;
        }

    }

    
    private void erase(Node node) {
        if (node != null) {
            if (node.getParent() != null) {
                if (!node.getDirection()) {
                    node.getParent().setLeft(null);
                } else{
                    node.getParent().setRight(null);
                }
            }

            node.setLeft(null);
            node.setRight(null);
            node.setData(null);
        }
    }
        
    public List inorderTraversal(){
        List<T> sortedList = new ArrayList<>();
        inorderTraversal(root, sortedList);
        return sortedList;
    }

    private void inorderTraversal(Node root, List<T> sortedList) {
        if (root == null) return;
        inorderTraversal(root.left, sortedList);
        sortedList.add(root.getData());
        inorderTraversal(root.right, sortedList);
    }
    
    public List preorderTraversal(){
        List<T> sortedList = new ArrayList<>();
        postorderTraversal(root, sortedList);
        return sortedList;
    }

    private void preorderTraversal(Node root, List<T> sortedList) {
        if (root == null) return;
        sortedList.add(root.getData());
        inorderTraversal(root.left, sortedList);
        inorderTraversal(root.right, sortedList);
    }
    
    public List postorderTraversal(){
        List<T> sortedList = new ArrayList<>();
        postorderTraversal(root, sortedList);
        return sortedList;
    }

    private void postorderTraversal(Node root, List<T> sortedList) {
        if (root == null) return;
        inorderTraversal(root.left, sortedList);
        inorderTraversal(root.right, sortedList);
        sortedList.add(root.getData());
    }
    
    private Node balancedTree(List<T> sortedList, int start, int end) {
        if (start > end) return null;
        int mid = (start + end) / 2;
        Node node = new Node(sortedList.get(mid));
        node.setLeftNode(balancedTree(sortedList, start, mid - 1));
        node.setRightNode(balancedTree(sortedList, mid + 1, end));
        return node;
    }

    public void balance() {
        List<T> sortedList = new ArrayList<>();
        inorderTraversal(root, sortedList);
        this.setRoot(balancedTree(sortedList, 0, sortedList.size() - 1));
    }

    public Node getRoot(){
        return root;
    }
    
    public void setRoot(Node root){
        this.root = root;
    }

    public int getMaxLevel() {
        return getMaxLevelRecursive(root);
    }

    private int getMaxLevelRecursive(BinaryTree.Node node) {
        if (node == null) {
            return 0;
        }
        int leftDepth = getMaxLevelRecursive(node.getLeft());
        int rightDepth = getMaxLevelRecursive(node.getRight());
        return Math.max(leftDepth, rightDepth) + 1;
    }

    public static int getNodeDepth(BinaryTree.Node node) {
        int depth = 0;
        while (node != null) {
            depth++;
            node = node.getParent();
        }
        return depth;
    }


}
