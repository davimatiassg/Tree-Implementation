package view;

import javax.management.modelmbean.InvalidTargetObjectTypeException;

import tree.BinaryTree;
import reader.TreeReader;
import reader.CommandReader;

public class TreeView {
	
	public static void main(String[] args) 
	{
		BinaryTree<Integer> tree = TreeReader.ReadBinaryTree(args[0]);
		CommandReader.readCommands(args[1], tree);
	} 

	public static BinaryTree<Integer> defaultBinaryTree()
	{
		BinaryTree<Integer> t = new BinaryTree<Integer>(50);
		t.add(35);
		t.add(25);
		t.add(30);
		t.add(70);
		t.add(40);
		t.add(65);
		t.add(80);
		t.add(90);
		return t;
	}
	
}


