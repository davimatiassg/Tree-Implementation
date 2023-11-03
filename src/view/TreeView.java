package view;

import tree.BinaryTree;

public class TreeView {
	
	
	
	public static void main(String[] args) 
	{
		BinaryTree<Integer> tree = defaultBinaryTree();
		
		System.out.println(tree.toStringWithHeight());
		
		
		//System.out.println(tree.preOrder());
		
		//System.out.println(tree.enesimoElemento(6));
		//
		//System.out.println(tree.mediana());
		//System.out.println(tree.media(35));
		System.out.println(tree.isFull());
		/*
		
		
		
		BinaryTree<Integer> search = tree.search(90);
		System.out.println(search.toString()); 
		*/
		/*
		System.out.println("Rm30");
		BinaryTree<Integer> rm30 = defaultBinaryTree();
		try {
			rm30.remove(30);
		} catch (Throwable e) {
			System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
			e.printStackTrace();
		}
		System.out.println(rm30.toStringWithHeight());
		
		System.out.println("Rm40");
		BinaryTree<Integer> rm40 = defaultBinaryTree();
		try {
			rm40.remove(40);
		} catch (Throwable e) {
			System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
			e.printStackTrace();
		}
		System.out.println(rm40.toStringWithHeight());
		
		System.out.println("Rm50");
		BinaryTree<Integer> rm50 = defaultBinaryTree();
		try {
			rm50.remove(50);
		} catch (Throwable e) {
			System.out.println("AAAAAAAAAAAAAAA");
			e.printStackTrace();
		}
		while(rm50.getRoot() != rm50) rm50 = rm50.getRoot();
		System.out.println(rm50.toString());
		*/
	} 

	public static BinaryTree<Integer> defaultBinaryTree()
	{
		BinaryTree<Integer> t = new BinaryTree<Integer>(50);
		t.add(35);
		t.add(25);
		//t.add(30);
		t.add(70);
		t.add(40);
		t.add(65);
		//t.add(80);
		t.add(90);
		//t.add(100);
		//t.add(101);
		
		return t;
	}
	
}


