package reader;

import tree.BinaryTree;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class CommandReader {
	
	public static void readCommands(String filename, BinaryTree<Integer> tree)
	{		
		try {
			
			File file = new File(filename);
			Scanner reader = new Scanner(file);
			
			while (reader.hasNextLine())
			{
				String data = reader.nextLine();
				String command = "", argStr = "";
				int arg = 0;
				
				boolean readingCommand = true;
				
				// Finding 'command' and 'arg'
				for (int i = 0;i < data.length();i ++)
				{			
					char char_ = data.charAt(i);
					
					if (char_ == ' ')
					{
						readingCommand = false;
					}
					else {
						if (readingCommand) command += char_;
						else argStr += char_;
					}
				}
				
				if (argStr.length() > 0) arg = Integer.parseInt(argStr);
				
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
		if (command.equals("MEDIANA")) {
			System.out.println("Median: " + tree.calculateMedian());
			return;
		}
		
		if (command.equals("IMPRIMA")) {
			
			if(arg == 1 || arg == 2) 
			{
				System.out.println(tree.toString(arg));
			}
			System.out.println("Argument not coherent with this command.");
		}
		
		if (command.equals("ENESIMO")) {
			System.out.println("Element at " + arg + ": " + tree.findElementByIndex(arg));
		}
		
		if (command.equals("REMOVA")) {
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
		
		if (command.equals("CHEIA")) {
			if(tree.isFull()) System.out.println("The tree is a Full tree.");
			else System.out.println("The tree is not Full.");
		}
		
		if (command.equals("COMPLETA")) {
			if(tree.isComplete()) System.out.println("The tree is a Complete tree.");
			else System.out.println("The tree is not Complete.");
		}
		
		if (command.equals("POSICAO")) {
			System.out.println("Position of " + arg + ": " + tree.search(arg));
		}
	}
	
	private static void printTree(BinaryTree<Integer> tree)
	{
		System.out.println(tree.showTreeDashesWithHeight());
	}
}