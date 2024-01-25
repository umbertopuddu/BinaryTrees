package utilities;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import java.util.Random;

public class BinaryTreeVisualizer {

    BinaryTree tree;

    public BinaryTreeVisualizer(BinaryTree tree){
        this.tree = tree;
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }

    private void createAndShowGUI() {
        JFrame frame = new JFrame("Binary Tree Visualizer");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 400);
        BinaryTreePanel<Integer> panel = new BinaryTreePanel<>(tree);
        frame.add(panel);

        frame.setVisible(true);
    }
}