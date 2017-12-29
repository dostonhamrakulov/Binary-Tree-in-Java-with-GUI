/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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

//Sets the principal tools we need to construct the tree
public class Node{
    //constructor
    int content;
    Node left;
    Node right;
    
    //This method put data in the Node of the tree
    public Node(int c) {
		this.content = c;
	}
    
    //This method gets the data of a Node in the tree
    public int getContent() { return content; }
    
    //This method gets the next node located in the left of another node
    public Node getLeft() { return left; }
    /*In the binary tree sets the node to the left, only if it's less than other node.
    it also receives an int, int that will be stored in the node seted*/
    public Node setLeft(int content) {
	left = new Node(content);
	return left;
    }
    
    //This method gets the next node located in the right of another node
    public Node getRight() { return right; }
    /*In the binary tree sets the node to the right, only if it's larger than other node.
    it also receives an int, int that will be stored in the node seted*/
    public Node setRight(int content) {
	right = new Node(content);
	return right;
    }   
}
