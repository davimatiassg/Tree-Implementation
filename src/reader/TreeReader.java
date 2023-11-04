package reader;

import tree.BinaryTree;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class TreeReader {
	
	public static BinaryTree<Integer> ReadBinaryTree()
	{
		try {
		
			File file = new File("a.txt");
			Scanner reader = new Scanner(file);
			
			while (reader.hasNextLine())
			{
				String data = reader.nextLine();
				System.out.println(data);
			}
			
			reader.close();
		
		}
		catch(FileNotFoundException e) {
			
			System.out.println("No file found :(");
			e.printStackTrace();
			
		}
		
		return null;
	}
}