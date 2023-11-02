package view;

import tree.BinaryTree;

public class TreeView {
	
	
	
	public static void main(String[] args) 
	{
		BinaryTree<Integer> tree = defaultBinaryTree();
		
		System.out.println(tree.toString());
		
		System.out.println(tree.preOrder());
		
		
		/*
		BinaryTree<Integer> search = tree.search(90);
		System.out.println(search.toString()); 
		System.out.println("Rm30");
		BinaryTree<Integer> rm30 = defaultBinaryTree();
		rm30.remove(30);
		System.out.println(rm30.toString());
		
		System.out.println("Rm40");
		BinaryTree<Integer> rm40 = defaultBinaryTree();
		rm40.remove(40);
		System.out.println(rm40.toString());
		
		System.out.println("Rm50");
		BinaryTree<Integer> rm50 = defaultBinaryTree().remove(50);
		//while(rm50.getRoot() != rm50) rm50 = rm50.getRoot();
		System.out.println(rm50.toString());
		*/
	} 

	public static BinaryTree<Integer> defaultBinaryTree()
	{
		BinaryTree<Integer> t = new BinaryTree(50);
		t.add(35);
		t.add(40);
		t.add(25);
		t.add(38);
		t.add(30);
		t.add(70);
		t.add(90);
		t.add(80);
		t.add(65);
		return t;
	}
	
}


