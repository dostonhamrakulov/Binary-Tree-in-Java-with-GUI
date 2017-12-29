package com.iidoston;
/**
 *
 * @author Doston Hamrakulov
 * 
 * References:
 * doston.hamrakulov@gmail.com
 *      
 * 
 */

import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.*;
import java.util.*;
//This Class is made for stablish the items we need to draw the tree inside the JPanel.
public class TheWindow extends JPanel implements ActionListener {
    
    static JFrame frame;
    static JOptionPane message = new JOptionPane();
    // the binary tree
    private BinaryTree tree = null;
    // the node location of the tree
    private HashMap nodeLocations = null;
    // the sizes of the subtrees
    private HashMap subtreeSizes = null;
    // do we need to calculate locations?
    private boolean dirty = true;
    // Default space between nodes
    private int parent2child = 10, child2child = 20;
    // helpers
    private Dimension empty = new Dimension(0, 0);
    private FontMetrics fm = null;
    
    /*When a button is pressed from the menu like "A F S D H" will make a different option
    a= add, f= add from file, s= search, d=Delete, H = Help.*/
    public TheWindow(BinaryTree tree){
      this.tree = tree;
      nodeLocations = new HashMap();
      subtreeSizes = new HashMap();
      registerKeyboardAction(this, "add", KeyStroke.getKeyStroke(KeyEvent.VK_A, 0), WHEN_IN_FOCUSED_WINDOW);
      registerKeyboardAction(this, "addFrom", KeyStroke.getKeyStroke(KeyEvent.VK_F, 0), WHEN_IN_FOCUSED_WINDOW);
      registerKeyboardAction(this, "search", KeyStroke.getKeyStroke(KeyEvent.VK_S, 0), WHEN_IN_FOCUSED_WINDOW);
      registerKeyboardAction(this, "delete", KeyStroke.getKeyStroke(KeyEvent.VK_D, 0), WHEN_IN_FOCUSED_WINDOW);
      registerKeyboardAction(this, "help", KeyStroke.getKeyStroke(KeyEvent.VK_H, 0), WHEN_IN_FOCUSED_WINDOW);
    }
    
    /* event handler for pressing a button from the menu, this will also show 
    messageboxes and will make different things,depending of the option selected*/
    public void actionPerformed(ActionEvent e) {
      String input;
      int a;
      if (e.getActionCommand().equals("add")) {
        input = JOptionPane.showInputDialog("Add an integer number:");
        try{ //Has the user introduced a number ?
                a =Integer.parseInt(input);
                tree.addNode(a);
                dirty = true;
                repaint();
            }
            //Ok, we want to remember him to write a number
        catch(NumberFormatException z){
                JOptionPane.showMessageDialog(frame, "Please, write an integer number");
            }
        }
      
      if (e.getActionCommand().equals("addFrom")) {
        //This help us for reading and storage of data
            BufferedReader reader = null;
            try { 
                    //The program needs the route of the text file that contains numbers
                /*In this case, we have selected a specefic route. But when the user wants to 
                read another text file with numbers, he has to change the route.
                So, don´t forget this.
                */
                    File file = new File("C:\\Users\\Raúl\\Desktop\\numeros.txt");
                    //We need another tool to read data; that is stored in "reader"
                    reader = new BufferedReader(new FileReader(file));
                    String line;
                    //The program starts reading each number of the text file
                    while ((line = reader.readLine()) != null) {
                        input = line;
                        tree.addNode(Integer.parseInt(input));
                        dirty = true;
                        repaint();
                    }
                }
            catch (IOException z) 
                {
                    //The user doesn´t know exactly where is the file text
                    z.printStackTrace();
                }
            //Always this is executed...
            finally {
                    try{
                        //Stop the actions that perform import.java.io.Filereader
                        reader.close();
                    }
                    //"Try" needs a catch, because of syntax. But, in the program is neve executed. 
                    catch (IOException z) {
                        z.printStackTrace();
                    }
                }
        }
      
      if (e.getActionCommand().equals("delete")) {
        input = JOptionPane.showInputDialog("Delete an integer number:");
        try{ //Has the user introduced a number ?
                a = Integer.parseInt(input);
                tree.deleteNode(a, tree.getRoot());
                dirty = true;
                repaint();
            }
        //Ok, we want to remember him to write a number
        catch(NumberFormatException z){
                JOptionPane.showMessageDialog(frame, "Please, write an integer number");
            }
      }
      
      if (e.getActionCommand().equals("search")) {
        input = JOptionPane.showInputDialog("Search an integer number:");
        try{ //Has the user introduced a number ?
                a =Integer.parseInt(input);
                Node aux = tree.searchNode(a, tree.getRoot());
                if (aux == null)
                    JOptionPane.showMessageDialog(frame, "The number " + a + " was not found");
                else
                    JOptionPane.showMessageDialog(frame, "The number " + a + " was found");
                dirty = true;
                repaint();
            }
        //Ok, we want to remember him to write a number
        catch(NumberFormatException z){
                JOptionPane.showMessageDialog(frame, "Please, write an integer number");
            }
      }
      
     if (e.getActionCommand().equals("help")) {
        JOptionPane.showMessageDialog(frame, "The operations you can use are:"
               + "\n a --- Add an integer number"
               + "\n f --- Add from file"
               + "\n s --- Search an integer number"
               + "\n d --- Delete an integer number"
               + "\n h --- Help (if you forgot this");
    }
}
    
    // This method calculates the node locations, to make it look stablish 
    private void calculateLocations() {
      nodeLocations.clear();
      subtreeSizes.clear();
      Node root = tree.getRoot();
      if (root != null) {
        calculateSubtreeSize(root);
        calculateLocation(root, Integer.MAX_VALUE, Integer.MAX_VALUE, 0);
      }
    }
    
    // This method calculates the size of a subtree rooted at n
    private Dimension calculateSubtreeSize(Node n) {
      if (n == null) return new Dimension(0, 0);
      String s = Integer.toString(n.getContent());
      Dimension ld = calculateSubtreeSize(n.getLeft());
      Dimension rd = calculateSubtreeSize(n.getRight());
      int h = fm.getHeight() + parent2child + Math.max(ld.height, rd.height);
      int w = ld.width + child2child + rd.width;
      Dimension d = new Dimension(w, h);
      subtreeSizes.put(n, d);
      return d;
    }
    
    // This method calculates the location of the nodes in the subtree rooted at n
    private void calculateLocation(Node n, int left, int right, int top) {
      if (n == null) return;
      Dimension ld = (Dimension) subtreeSizes.get(n.getLeft());
      if (ld == null) ld = empty;
      Dimension rd = (Dimension) subtreeSizes.get(n.getRight());
      if (rd == null) rd = empty;
      int center = 0;
      if (right != Integer.MAX_VALUE)
        center = right - rd.width - child2child/2;
      else if (left != Integer.MAX_VALUE)
        center = left + ld.width + child2child/2;
      int width = fm.stringWidth(Integer.toString(n.getContent()));
      Rectangle r = new Rectangle(center - width/2 - 3, top, width + 6, fm.getHeight());
      nodeLocations.put(n, r);
      calculateLocation(n.getLeft(), Integer.MAX_VALUE, center - child2child/2, top + fm.getHeight() + parent2child);
      calculateLocation(n.getRight(), center + child2child/2, Integer.MAX_VALUE, top + fm.getHeight() + parent2child);
    }
    
    // This method draws the tree using the pre-calculated locations. We need necessary a graphic
    private void drawTree(Graphics2D g, Node n, int px, int py, int yoffs) {
      if (n == null) return;
      Rectangle r = (Rectangle) nodeLocations.get(n);
      g.draw(r);
      g.drawString(Integer.toString(n.getContent()), r.x + 3, r.y + yoffs);
     if (px != Integer.MAX_VALUE)
       g.drawLine(px, py, r.x + r.width/2, r.y);
     drawTree(g, n.getLeft(), r.x + r.width/2, r.y + r.height, yoffs);
     drawTree(g, n.getRight(), r.x + r.width/2, r.y + r.height, yoffs);
   }
   
    //This method will draw our tree, this receives a graphic called "g" 
    public void paint(Graphics g) {
     super.paint(g);
     fm = g.getFontMetrics();
     // if node locations not calculated
     if (dirty) {
       calculateLocations();
       dirty = false;
     }
     Graphics2D g2d = (Graphics2D) g;
     g2d.translate(getWidth() / 2, parent2child);
     drawTree(g2d, tree.getRoot(), Integer.MAX_VALUE, Integer.MAX_VALUE, fm.getLeading() + fm.getAscent());
     fm = null;
   }
   
   /*At the start of the program will show a messagebox with all the commands that 
    can be used to work this program correctly,also set the dimension of the principal 
    window */
   public static void main(String[] args) {
        // TODO code application logic here
       BinaryTree tree = new BinaryTree();
       JFrame f = new JFrame("Binary Tree");
       JOptionPane.showMessageDialog(frame, "Welcome"
               + "\n\nThis program works typing some letters from your keyboard"
               + "\nSo, the operations you can use are:"
               + "\n a --- Add an integer number"
               + "\n f --- Add from file"
               + "\n s --- Search an integer number"
               + "\n d --- Delete an integer number"
               + "\n h --- Help (if you forgot this");
        f.getContentPane().add(new TheWindow(tree));
        // create and add an event handler for window closing event
        f.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
            System.exit(0);
        }
     });
     f.setBounds(50, 50, 700, 700);
     f.show();
    }    
}
