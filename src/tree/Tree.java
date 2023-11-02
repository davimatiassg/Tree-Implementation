package tree;
import java.lang.Comparable;

public class Tree<T extends Comparable<T>> {
	
	T value;
	int height;
	Tree<T> root;
	Tree<T> leftTree;
	Tree<T> rightTree;
	
	public Tree(T value, Tree<T> root, Tree<T> leftTree, Tree<T> rightTree) {
		this.value = value;
		this.root = root;
		this.leftTree = leftTree;
		this.rightTree = rightTree;
		this.height = 1;
		
	}

	public Tree(T value, Tree<T> leftTree, Tree<T> rightTree) {
		this.value = value;
		this.root = this;
		this.leftTree = leftTree;
		this.rightTree = rightTree;
		this.height = 1;
	}
	
	public Tree(T value, Tree<T> root) {
		this.value = value;
		this.root = root;
		this.height = 1;
	}
	
	public Tree(T value) {
		this.value = value;
		this.root = this;
		this.height = 1;
	}

	public T getValue() {
		return value;
	}

	public void setValue(T value) {
		this.value = value;
	}

	public Tree<T> getLeft() {
		return leftTree;
	}

	public void setLeft(Tree<T> leftTree) {
		this.leftTree = leftTree;
	}

	public Tree<T> getRight() {
		return rightTree;
	}

	public void setRight(Tree<T> rightTree) {
		this.rightTree = rightTree;
	}

	public Tree<T> getRoot() {
		return root;
	}

	public void setRoot(Tree<T> root) {
		this.root = root;
	}	
	
	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}
	
	public void updateHeight(Tree<T> nextTree)
	{
		if(nextTree.getHeight() + 1 >= this.height)
		{
			this.height++;
		}
	}
	
	public int getEmptySubTreeAmount()
	{
		int amount = 0;
		if (getLeft() == null) amount ++;
		if (getRight() == null) amount ++;
		
		return amount;
	}
	
	public boolean add(T value) 
	{
		int comparison = value.compareTo(this.value);
		
		if(comparison == 0) { return false; }
		
		Tree<T> nextTree = comparison < 0 ? this.leftTree : this.rightTree;
		
		if(nextTree == null) 
		{
			nextTree = new Tree<T>(value, this);
			if(comparison > 0)
			{
				this.setRight(nextTree)
			}
			else {
				this.setLeft(nextTree);
			}
			updateHeight(nextTree);
			return true;
		}
		return nextTree.add(value);
	}
	
	public void remove(T value) 
	{
		Tree<T> victim = search(value);
		
		if (victim == null) return;
		
		int v = victim.getValue().compareTo(victim.getRoot().getValue());
		
		switch(victim.getEmptySubTreeAmount())
		{
			case 2:
				if (v > 0)
				{
					victim.getRoot().setRight(null);
				} else {
					victim.getRoot().setLeft(null);
				}
				victim = null;
			break;
			
			case 1:
				if (v > 0)
				{
					victim.getRoot().setLeft(null);
				} else {
					victim.getRoot().setLeft(null);
				}
				
				if (victim.getLeft() == null)
				{
					victim.getRight().setRoot(victim.getRoot());
				}
				else {
					victim.getLeft().setRoot(victim.getRoot());
				}
				
			break;
			
			case 0:
				
				Tree<T> successor = findSucessor(victim);
				if (successor == null) { return; }
				Tree<T> nextTree = successor.getRight() == null ? successor.getLeft() : successor.getRight();
				
				//TODO
				
			break;
		}
	}
	
	
	private Tree<T> findSucessor(Tree<T> victim) {
		// TODO Auto-generated method stub
		return null;
	}

	private int getEmptySubTreeAmount() {
		// TODO Auto-generated method stub
		return 0;
	}

	public Tree<T> search(T value) 
	{
		int comparison = value.compareTo(this.value);
		Tree<T> nextTree = comparison < 0 ? leftTree : rightTree;
		if(comparison == 0) { return this; }
		if(nextTree == null) { return null; }
		return nextTree.search(value);
	}
	
}
