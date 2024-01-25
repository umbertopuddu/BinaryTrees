package utilities;

public class BinarySearchTree<T extends Comparable<T>> extends BinaryTree<T> {

    public BinarySearchTree(){
        super();
    }

    public BinarySearchTree(T... args) {
        super();
        if (args.length > 0) {
            super.setRoot(new Node(args[0]));
            for (int i = 1; i < args.length; i++) {
                this.addNode(args[i]);
            }
        }
    }
    
    public void addNode(T... values) {
        int start = 0;
        if(super.getRoot() == null){
            start++;
            super.setRoot(new Node(values[0]));
        }
        for(int i = start; i < values.length; i++){
            addRecursive(super.getRoot(), values[i]);
        }
    }
    
    private Node addRecursive(Node current, T value) {
        if (current == null) {
            return new Node(value);
        }
        if (value.compareTo(current.getData()) < 0) {
            Node leftChild = addRecursive(current.getLeft(), value);
            if (current.getLeft() == null) {
                current.setLeft(leftChild.getData());
            }
        } else if (value.compareTo(current.getData()) > 0) {
            Node rightChild = addRecursive(current.getRight(), value);
            if (current.getRight() == null) {
                current.setRight(rightChild.getData());
            }
        }
        return current;
    }
    
    public void removeNode(T... values) throws Exception {
        for(T value : values){
            super.setRoot(removeRecursive(super.getRoot(), value));   
        }
    }
    
    public void removeNode(T value) throws Exception {
        super.setRoot(removeRecursive(super.getRoot(), value));
    }
    
    private Node removeRecursive(Node current, T value) {
        if (current == null) {
            return null;
        }
    
        if (value == current.getData()) {
            if (current.getLeft() != null && current.getRight() != null) {
                Node maxNode = findMaxLeft(current);
                current.setData(maxNode.getData());
                current.setLeftNode(removeRecursive(current.getLeft(), maxNode.getData()));
            } 
            else {
                return (current.getLeft() != null) ? current.getLeft() : current.getRight();
            }
        } else if (value.compareTo(current.getData()) < 0) {
            current.setLeftNode(removeRecursive(current.getLeft(), value));
        } else {
            current.setRightNode(removeRecursive(current.getRight(), value));
        }
        return current;
    }

    
    public Node findMaxLeft(Node node) {
        if (node == null) {
            return null;
        }
        Node current = node.getLeft();
        while (current != null && current.getRight() != null) {
            current = current.getRight();
        }
        return current;
    }


    public Node find(T data){
        return findNode(super.getRoot(), data);
    }
    
    private Node findNode(Node current, T value){
        if(current.getData().equals(value)){
            return current;
        }
        else if (current.getData().compareTo(value) > 0) {
            return findNode(current.getLeft(), value);            
        }
        else{
            return findNode(current.getRight(), value);
        }
    }


}