package reader;

import tree.BinaryTree;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class TreeReader {
	
	public static BinaryTree<Integer> ReadBinaryTree(String filename)
	{
		try {
		
			File file = new File(filename);
			Scanner reader = new Scanner(file);
			
			BinaryTree<Integer> tree = null;
			int data;
			boolean first = true;
			
			while (reader.hasNextLine())
			{
				data = reader.nextInt();
				
				if (first) tree = new BinaryTree<Integer>(data);
				else tree.add(data);
				
				first = false;
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