package tree;
import java.lang.Comparable;

public class Tree<T extends Comparable<T>> {
	
	T value;
	Tree<T> root;
	Tree<T> leftTree;
	Tree<T> rightTree;
	
	public Tree(T value, Tree<T> root, Tree<T> leftTree, Tree<T> rightTree) {
		super();
		this.value = value;
		this.root = root;
		this.leftTree = leftTree;
		this.rightTree = rightTree;
	}

	public Tree(T value, Tree<T> leftTree, Tree<T> rightTree) {
		this.value = value;
		this.leftTree = leftTree;
		this.rightTree = rightTree;
	}
	
	public Tree(T value, Tree<T> root) {
		this.value = value;
		this.root = root;
	}
	
	public Tree(T value) {
		this.value = value;
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

	public void setRightTree(Tree<T> rightTree) {
		this.rightTree = rightTree;
	}

	public Tree<T> getRoot() {
		return root;
	}

	public void setRoot(Tree<T> root) {
		this.root = root;
	}
	
	public void add(T value) 
	{
		int comparison = value.compareTo(this.value);
		Tree<T> nextTree = comparison > 0 ? leftTree : rightTree;
		
		if(nextTree == null) 
		{
			if(comparison > 0) 
			{
				leftTree = new Tree<T>(value, this);
				return;
			}
			rightTree = new Tree<T>(value, this);
			return;
		}
		nextTree.add(value);
	}
	
	public void remove(T value) 
	{
		Tree<T> removal = search(value);
		Tree<T> removalRoot = removal.getRoot();
		Tree<T> removalLeft = removal.getLeft();
		Tree<T> removalRight = removal.getRight();
		//TODO
		if(removal == null) { return; }
		if(removalLeft != null) 
		{
			removal = removal.getLeft();
			while(removal.getRight() != null) 
			{
				removal = removal.getLeft();
			}
			removal.setRoot(removalRoot);
			if(removalRoot != null) { removalRoot.setLeft(removal); }
		}
		
	}
	
	
	public Tree<T> search(T value) 
	{
		int comparison = value.compareTo(this.value);
		Tree<T> nextTree = comparison > 0 ? leftTree : rightTree;
		if(nextTree == null) { return null; }
		return nextTree.search(value);
	}
	
}
