package reader;

import tree.BinaryTree;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class CommandReader {
	
	public static void readCommands(BinaryTree<Integer> tree)
	{		
		try {
			
			File file = new File("Commands.txt");
			Scanner reader = new Scanner(file);
			
			while (reader.hasNextLine())
			{
				String data = reader.nextLine();
				
				String command = "";
				int arg = 0;
				
				executeCommand(tree, command, arg);
				
			}
			
			reader.close();
		}
		catch(FileNotFoundException e) {
			
			System.out.println("No file found :(");
			e.printStackTrace();
		}
	}
	
	private static void executeCommand(BinaryTree<Integer> tree, String command, int arg)
	{
		if (command == "MEDIANA") {
			System.out.println("Median: " + tree.calculateMedian());
		}
		
		if (command == "IMPRIMA") {
			System.out.println("???? tafuck is imprima");
		}
		
		if (command == "ENESIMO") {
			System.out.println("Element at " + arg + ": " + tree.findElementByIndex(arg));
		}
		
		if (command == "REMOVA") {
			try {
				tree.remove(arg);
				System.out.println(arg + " Removed from tree: ");
				printTree(tree);
			}
			catch(Throwable e)
			{
				System.out.println("Could not remove node :(");
				e.printStackTrace();
			}
		}
		
		if (command == "CHEIA") {
			System.out.println("Is the tree full? " + tree.isFull());
		}
		
		if (command == "COMPLETA") {
			System.out.println("Is the tree complete? " + tree.isComplete());
		}
		
		if (command == "POSICAO") {
			System.out.println("Position of " + arg + ": " + tree.search(arg));
		}
	}
	
	private static void printTree(BinaryTree<Integer> tree)
	{
		System.out.println(tree.showTreeDashesWithHeight());
	}
}