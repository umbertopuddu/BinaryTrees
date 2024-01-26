package utilities;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.Map;

public class BinaryTreePanel<T> extends JPanel {
    private BinaryTree<T> binaryTree;
    private Map<BinaryTree.Node, Point> nodePositions;
    private int yOffset;
    private double zoom = 1.0;
    private int panOffsetX = 0;
    private int panOffsetY = 0;
    private Point lastMousePosition;
    private final int maxDepth;
    private Timer panTimer;
    private int panDeltaX = 0;
    private int panDeltaY = 0;

    public BinaryTreePanel(BinaryTree<T> binaryTree) {
        this.binaryTree = binaryTree;
        this.nodePositions = new HashMap<>();
        this.yOffset = 50;
        maxDepth = binaryTree.getMaxLevel();

        JButton zoomInButton = new JButton("Zoom In");
        JButton zoomOutButton = new JButton("Zoom Out");


        zoomInButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                zoom *= 1.2;
                updateTree(binaryTree);
                requestFocusInWindow();
            }
        });

        zoomOutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                zoom /= 1.2;
                updateTree(binaryTree);
                requestFocusInWindow();
            }
        });

        add(zoomInButton);
        add(zoomOutButton);

        panTimer = new Timer(20, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panOffsetX += panDeltaX;
                panOffsetY += panDeltaY;
                updateTree(binaryTree);
            }
        });

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int panStep = 5;
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_LEFT:
                        panDeltaX = panStep;
                        break;
                    case KeyEvent.VK_RIGHT:
                        panDeltaX = -panStep;
                        break;
                    case KeyEvent.VK_UP:
                        panDeltaY = panStep;
                        break;
                    case KeyEvent.VK_DOWN:
                        panDeltaY = -panStep;
                        break;
                }
                panTimer.start();
            }

            @Override
            public void keyReleased(KeyEvent e) {
                panTimer.stop();
                panDeltaX = 0;
                panDeltaY = 0;
            }
        });


        setFocusable(true);
        requestFocusInWindow();


    }

    private void calculateNodePositions(BinaryTree.Node node, int x, int y, int depth, int levelWidth, Graphics g) {
        if (node != null) {
            int stringWidth = g.getFontMetrics().stringWidth(String.valueOf(node.getData()));
            int horizontalSpacing = (int) (((45 + 1/2*stringWidth) * Math.pow(2, maxDepth - depth)) * zoom);

            if (node.getLeft() != null) {
                calculateNodePositions(node.getLeft(), x - horizontalSpacing / 2, y + yOffset, depth + 1, levelWidth, g);
            }

            Point nodePos = new Point((int) ((x + panOffsetX) * zoom), (int) ((y + panOffsetY) * zoom));
            nodePositions.put(node, nodePos);

            if (node.getRight() != null) {
                calculateNodePositions(node.getRight(), x + horizontalSpacing / 2, y + yOffset, depth + 1, levelWidth, g);
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int levelWidth = (int) (getWidth() * zoom);
        int centerX = (int) (levelWidth / 2);
        int centerY = (int) (yOffset * zoom);
        nodePositions.clear();
        calculateNodePositions(binaryTree.getRoot(), centerX, centerY, 0, levelWidth, g);
        drawTree(g, binaryTree.getRoot());
    }

    private void drawTree(Graphics g, BinaryTree.Node node) {
        if (node != null) {
            Point nodePos = nodePositions.get(node);

            // Set the new font size
            Font myFont = new Font(g.getFont().getName(), g.getFont().getStyle(), (int)(14*zoom));
            g.setFont(myFont);

            String nodeData = String.valueOf(node.getData());

            // Now metrics should be calculated with the new font
            FontMetrics metrics = g.getFontMetrics();
            int stringWidth = metrics.stringWidth(nodeData);
            int stringHeight = metrics.getHeight();

            int horizontalPadding = 10;
            int verticalPadding = 5;
            int ovalWidth = stringWidth + horizontalPadding;
            int ovalHeight = stringHeight + verticalPadding;

            int textX = nodePos.x - stringWidth / 2;
            int textY = nodePos.y + stringHeight / 4;

            g.setColor(Color.BLACK);
            g.drawOval(nodePos.x - ovalWidth / 2, nodePos.y - ovalHeight / 2, ovalWidth, ovalHeight);
            g.drawString(nodeData, textX, textY);

            if (node.getParent() != null) {
                Point parentPos = nodePositions.get(node.getParent());
                int lineStartX = nodePos.x;
                int lineStartY = nodePos.y - ovalHeight / 2;
                int lineEndX = parentPos.x;
                int lineEndY = parentPos.y + ovalHeight / 2;
                g.drawLine(lineStartX, lineStartY, lineEndX, lineEndY);
            }

            drawTree(g, node.getLeft());
            drawTree(g, node.getRight());
        }
    }


    public void updateTree(BinaryTree<T> binaryTree) {
        this.binaryTree = binaryTree;
        this.nodePositions.clear();
        repaint();
    }
}
