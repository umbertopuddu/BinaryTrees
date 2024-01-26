import utilities.*;

public class tester{
    public static void main(String[] args){
    
        //To create any tree always pass in generics (i.e. "<...>" the)
        //class of the objects intended to store (e.g. Integer, Double, ...)
    
        //Create a binary tree either blank [pass no parameters]
        //or with defined root
        BinaryTree<String> one = new BinaryTree("Hello");
        one.addNode("Bye bye", one.getRoot(), false);
        one.addNode("See you later", one.getRoot(), true);
        
        //Create a binary search tree either blank [pass no parameters]
        //or with elements [pass elements divided by comma or an array]
        BinarySearchTree<Integer> two = new BinarySearchTree(0,1,2,3,5,6,7,8,9);
        
        //NOTE: The BinarySearchTree can only store object from subclasses of
        //the Comparable class (i.e. all types of numbers, Strings, ...)
        
        //You can use this command to balance a tree
        two.balance();
    
        //With this you create the window displaying your binary tree
        //Do not modify anything but the tree object you pass to display
        BinaryTreeVisualizer visual = new BinaryTreeVisualizer(two /*here you pass your tree object to be displayed*/);
    }
}

/* INSTRUCTIONS FOR THE VISUALIZER:
 - Use the arrows to move the image/view up, down, left, or right.
 - Use the "Zoom In" and "Zoom Out" buttons to enlarge or reduce the image/view.
*/
