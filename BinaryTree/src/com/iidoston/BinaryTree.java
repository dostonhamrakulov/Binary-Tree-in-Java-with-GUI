/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iidoston;

import static com.iidoston.TheWindow.frame;
import javax.swing.JOptionPane;


/**
 *
 * @author Doston Hamrakulov
 * 
 * References:
 * doston.hamrakulov@gmail.com
 *      
 * 
 */

//This class sets the principal functions of this program, add, add from file, delete, search.
public class BinaryTree {
  // Set the root node as null to start the tree
  Node root = null;
  
  // Get the root node of the tree
  public Node getRoot() { return root; }
  
  /* This method will add a new node to the tree,if the root is null, the first value is placed there
  also the root wil get the first value inserted. If the conditional is not satisfactory, the method will call to 
  the method "addto"*/
  public Node addNode(int content) {
     if (root == null) {
        root = new Node(content);
        return root;
      }
      return addTo(root, content);
    }
    
  /* This method adds a new node comparing in the nodes inserted if its larger or lower than the content of 
  them, the new nodes will be stored in a null space*/
  private Node addTo(Node n, int c) {
      if (c < n.getContent()) {
        Node l = n.getLeft();
        if (l == null)
          return n.setLeft(c);
        else
          return addTo(l, c);
      } 
      else {
         Node r = n.getRight();
        if (r == null)
          return n.setRight(c);
        else
          return addTo(r, c);
      }
    }
  
  /*This method traverse the entire tree looking for the node with the data that is the user looking for, if the variable aux is 
   null its because its initial value is that and never changed because never found the data requested
  */
  public Node searchNode(int d, Node n){
      Node aux = null; //Not found
      if (n != null){
          if (d == n.getContent()){
            return n; //Found
            }
            else {
                if (d < n.getContent()) {
                    Node der = n.getLeft();
                    aux = searchNode(d, der);
                }
                else {
                    Node izq = n.getRight();
                    aux = searchNode(d, izq);
                }
            }
        }
        return aux;
      }
  
  /*This method first use the method searchNode to look for the data that the user is looking for, 
  if the data is not found will be displayed a messagebox showing that the element requested is not founded,
  however if the item is in the tree, the program will do the delete procedure, each procedure is comented below in each section.
  
  And, if the tree has only the root, you cannot erase it, becuase you need at least a right-child for the root
  for this program, because of the code. 
  */
  public Node deleteNode(int n, Node root){
      Node aux = searchNode(n, root);
      if (aux == null)
            JOptionPane.showMessageDialog(frame, "The number " + n + " was not found");
      else{
          // base case, tree is empty 
         if (root == null) { 
             return root; 
         } 
         // 1.A node is in left subtree 
         //  set root's leftchild to result of delete(root.left...) 
         else if (n < root.getContent()) { 
             root.left = deleteNode(n, root.left); 
         } 
         // 1.B node is in right subtree 
         //  set root's righth child to result of delete(root.right...) 
         else if (n > root.getContent()) { 
             root.right = deleteNode(n,root.right); 
         } 
         // 2 found data! 
         else { 
             // Case 1: no child 
             //  just set node to null (remove it) and return it 
             if (root.left == null && root.right == null) { 
                 root = null; 
             } 
             // Case 2: one child 
             // 2.A: no left child 
             else if (root.left == null) { 
                 Node temp = root; 
                 root = root.right; 
                 temp = null; 
             } 
             // 2.B: no right child 
             else if (root.right == null) { 
                 Node temp = root; 
                 root = root.left; 
                 temp = null; 
             } 
             // Case 3: 2 children 
             else { 
                 // get minimum element in right subtree 
                 //  set it to `root` and remove it from its 
                 //   original spot 
                 Node temp = findMin(root.right); 
                 root.content = temp.content; 
                 root.right = deleteNode(temp.getContent(),root.right); 
             } 
         } 
         return root; 
      }
      return root;
  }
  
  //This method looks for the most lower value of the tree.
  public static Node findMin(Node root) { 
         while (root.left != null) 
             root = root.left; 
         return root; 
     } 

}
