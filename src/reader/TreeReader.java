package reader;

import tree.BinaryTree;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class TreeReader {
	
	public static BinaryTree<Integer> ReadBinaryTree()
	{
		try {
		
			File file = new File("binaryTree.txt");
			Scanner reader = new Scanner(file);
			
			BinaryTree<Integer> tree = null;
			int data;
			int i = 0; while (reader.hasNextLine())
			{
				data = reader.nextInt();
				
				if (i == 0) tree = new BinaryTree<Integer>(data);
				else tree.add(data);
				
				i ++;
			}
			
			reader.close();
			
			return tree;
		}
		catch(FileNotFoundException e) {
			
			System.out.println("No file found :(");
			e.printStackTrace();
			return null;
		}
	}
}