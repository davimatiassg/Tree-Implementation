package tree;
import java.lang.Comparable;

public class BinaryTree<T extends Comparable<T>> {
	
	T value;
	int height;
	BinaryTree<T> root;
	BinaryTree<T> leftTree;
	BinaryTree<T> rightTree;
	
	public BinaryTree(T value, BinaryTree<T> root, BinaryTree<T> leftTree, BinaryTree<T> rightTree) {
		this.value = value;
		this.root = root;
		this.leftTree = leftTree;
		this.rightTree = rightTree;
		this.height = 1;
		
	}

	public BinaryTree(T value, BinaryTree<T> leftTree, BinaryTree<T> rightTree) {
		this.value = value;
		this.root = this;
		this.leftTree = leftTree;
		this.rightTree = rightTree;
		this.height = 1;
	}
	
	public BinaryTree(T value, BinaryTree<T> root) {
		this.value = value;
		this.root = root;
		this.height = 1;
	}
	
	public BinaryTree(T value) {
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

	public BinaryTree<T> getLeft() {
		return leftTree;
	}

	public void setLeft(BinaryTree<T> leftTree) {
		this.leftTree = leftTree;
	}

	public BinaryTree<T> getRight() {
		return rightTree;
	}

	public void setRight(BinaryTree<T> rightTree) {
		this.rightTree = rightTree;
	}

	public BinaryTree<T> getRoot() {
		return root;
	}

	public void setRoot(BinaryTree<T> root) {
		this.root = root;
	}	
	
	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}
	
	public void updateHeight(BinaryTree<T> nextTree)
	{
		if(nextTree.getHeight() + 1 >= this.height)
		{
			this.height++;
		}
	}

	public void swapTree(BinaryTree<T> t1, BinaryTree<T> t2)
	{
		if(t1 == null || t2 == null) return;
		
		BinaryTree<T> t1root = t1.getRoot();
		BinaryTree<T> t2root = t2.getRoot();
		BinaryTree<T> t1left = t1.getLeft();
		BinaryTree<T> t2left = t2.getLeft();
		BinaryTree<T> t1right = t1.getRight();
		BinaryTree<T> t2right = t2.getRight();

		t1.setRoot(t2root);
		t2.setRoot(t1root);

		t1.setLeft(t2left);
		t2.setLeft(t1left);

		t1.setRight(t2right);
		t2.setRight(t1right);

		if (t1left != null) t1left.setRoot(t2);

		if (t2left != null) t2left.setRoot(t1);

		if (t1right != null) t1right.setRoot(t2);

		if (t2right != null) t2right.setRoot(t1);

		if(t1.getRoot() == t2){t1.setRoot(t1);}
		if(t2.getRoot() == t1){t2.setRoot(t2);}
	}
	
	public boolean add(T value) 
	{
		int comparison = value.compareTo(this.value);
		
		if(comparison == 0) return false;
		
		BinaryTree<T> nextTree = comparison < 0 ? this.leftTree : this.rightTree;
		
		if(nextTree == null) 
		{
			nextTree = new BinaryTree<T>(value, this);
			if(comparison > 0)
			{
				this.setRight(nextTree);
			}
			else {
				this.setLeft(nextTree);
			}
			updateHeight(nextTree);
			return true;
		}
		return nextTree.add(value);
	}
	
	public BinaryTree<T> remove(T value) 
	{
		BinaryTree<T> victim = search(value);
		
		if (victim == null) return this;
		
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

				BinaryTree<T> nextTree = victim.getLeft() != null ? victim.getLeft() : victim.getRight(); 

				if (v > 0)
				{
					victim.getRoot().setRight(nextTree);
				} else {
					victim.getRoot().setLeft(nextTree);
				}
				
				nextTree.setRoot(victim.getRoot());
			break;
			
			case 0:
				BinaryTree<T> eligible = findEligible(victim);
				if (eligible == null) return this; //Redundanct

				swapTree(eligible, victim);

				BinaryTree<T> victimsRoot = victim.getRoot();

				if (victim.getValue().compareTo(victimsRoot.getValue()) < 0)
				{
					victimsRoot.setLeft(victim.getRight());
					victim.getRight().setRoot(victimsRoot);
				}
				else {
					victimsRoot.setRight(victim.getLeft());
					victim.getLeft().setRoot(victimsRoot);
				}
				
			break;
		}

		if (value.compareTo(this.getValue()) == 0)
		{
			BinaryTree<T> newRoot = this;
			while (newRoot.getRoot() != newRoot) newRoot = newRoot.getRoot(); 
			return newRoot;
		}
		 
		return this;
	}
	
	
	private BinaryTree<T> findEligible(BinaryTree<T> victim) {
		
		BinaryTree<T> eligible = null;
		
		if(victim.getLeft() != null)
		{ 
			eligible = victim.getLeft();
			while(eligible.getRight() != null)
			{
				eligible = eligible.getRight();
			}
			return eligible;
		}

		if(victim.getRight() != null)
		{ 
			eligible = victim.getRight();
			while(eligible.getLeft() != null)
			{
				eligible = eligible.getLeft();
			}
			return eligible;
		}
		
		return null;
	}
	
	private int getEmptySubTreeAmount()
	{
		int amount = 0;
		if (getLeft() == null) amount ++;
		if (getRight() == null) amount ++;
		
		return amount;
	}

	public BinaryTree<T> search(T value) 
	{
		int comparison = value.compareTo(this.value);
		BinaryTree<T> nextTree = comparison < 0 ? leftTree : rightTree;
		if(comparison == 0) return this;
		if(nextTree == null) return null;
		return nextTree.search(value);
	}

	/*
	public T enesimoElemento(int n)
	{
		return enesimoElementoRecursive(n);
	}

	private T enesimoElementoRecursive(int n)
	{
		if (n == 0)
		{
			return value;
		}
		if (getLeft() != null) getLeft().enesimoElementoRecursive(n - 1);
		if (getRight() != null) getRight().enesimoElementoRecursive(n - 1);
	}
	*/

	public String preOrder()
	{
		String s = "";
		return preOrderRecursive(s);
	}

	private String preOrderRecursive(String s)
	{
		s += " " + getValue().toString();
		if (getLeft() != null) s += getLeft().preOrder();
		if (getRight() != null) s += getRight().preOrder();
		return s;
	}
	

	@Override
	public String toString() {
		String s = "BinaryTree\n";
		return s + toString("");
	}
	
	public String toString(String dashes) {
		String s = dashes + value + "\n";
		dashes += "-";
		if(leftTree != null) { s += leftTree.toString(dashes); }
		if(rightTree != null) { s += rightTree.toString(dashes); }
		return s;
	}
	
	
	
	
}
