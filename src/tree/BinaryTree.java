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
	
	public void updateHeight()
	{
		switch (getEmptySubTreeAmount())
		{
			case 0:
				this.height = 1 + (getLeft().getValue().compareTo(getRight().getValue()) > 0 ? getLeft().getHeight() : getRight().getHeight());
				break;
			case 1:
				this.height = 1 + (leftExists() ? getLeft().getHeight() : getRight().getHeight());
				break;
			case 2:
				this.height = 1;
				break;
				
		}
	}
/*
	public void updateHeightOfRoot()
	{
		BinaryTree<T> root = this.getRoot();
		System.out.println("up");
		if(this.height < root.getHeight()-1) 
		{
			if(root.getEmptySubTreeAmount() == 0)
			if(root.getEmptySubTreeAmount() == 1)
			{
				root.setHeight(this.height+1);
				return;
			}
			
			BinaryTree<T> highest = root.getLeft().getValue().compareTo(root.getLeft().getValue()) > 0? root.getLeft() : root.getRight();
			root.setHeight(highest.getHeight()+1);
		};
	}
*/
	public void swapTree(BinaryTree<T> t1, BinaryTree<T> t2)
	{
		if(t1 == null || t2 == null) return;
		
		BinaryTree<T> t1root = t1.getRoot();
		BinaryTree<T> t2root = t2.getRoot();
		BinaryTree<T> t1left = t1.getLeft();
		BinaryTree<T> t2left = t2.getLeft();
		BinaryTree<T> t1right = t1.getRight();
		BinaryTree<T> t2right = t2.getRight();

		if(t1.getValue().compareTo(t1root.getValue()) < 0) t1root.setLeft(t2);
		else t1root.setRight(t2);

		if(t2.getValue().compareTo(t2root.getValue()) < 0) t2root.setLeft(t1);
		else t2root.setRight(t1);

		t1.setRoot(t2root);
		t2.setRoot(t1root);

		t1.setLeft(t2left);
		t2.setLeft(t1left);

		t1.setRight(t2right);
		t2.setRight(t1right);

		if (t1.leftExists()) t1.getLeft().setRoot(t1);
		if (t2.leftExists()) t2.getLeft().setRoot(t2);
		if (t1.rightExists()) t1.getRight().setRoot(t1);
		if (t2.rightExists()) t2.getRight().setRoot(t2);

		if(t1.getRoot() == t2) t1.setRoot(t1);
		if(t2.getRoot() == t1) t2.setRoot(t2);

		

		t1.updateHeight();
		t2.updateHeight();
	}
	
	public Boolean add(T value) 
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
			updateHeight();
			return true;
		}
		Boolean inserted = nextTree.add(value);
		if(this.getHeight() - nextTree.getHeight() <= 1) updateHeight();
		return inserted;
	}

	public Boolean remove(T value) throws Throwable
	{	
		if(value == null) return false;
		int comparison = value.compareTo(this.value);
		if(comparison == 0){ 
			getRoot().removeChildTree(this, value.compareTo(getRoot().value) < 0);
			return true;
		}
		BinaryTree<T> nextTree = comparison < 0 ? this.leftTree : this.rightTree;
		System.out.println("hello: " + this.value);
		Boolean success = nextTree.remove(value);
		if(this.getHeight() - nextTree.getHeight() > 1) updateHeight();
		return success;

	}



	public void removeChildTree(BinaryTree<T> nextTree, Boolean deleteLeft) throws Throwable
	{
		switch(nextTree.getEmptySubTreeAmount())
		{
		case 2:
			if (deleteLeft)	setLeft(null);
			else			setRight(null);
			nextTree.finalize();
			updateHeight();
			return;
		case 1:
			BinaryTree<T> nextNextTree = (nextTree.leftExists() ? nextTree.getLeft() : nextTree.getRight());
			nextNextTree.setRoot(this);
			if(deleteLeft)	setLeft(nextNextTree);
			else			setRight(nextNextTree);
			nextTree.finalize();
			updateHeight();
			return;
		case 0:
			BinaryTree<T> eligible = findEligible(nextTree);
			swapTree(eligible, nextTree);
			nextTree.getRoot().removeChildTree(nextTree, nextTree.getValue().compareTo(nextTree.getRoot().getValue()) < 0);
			return;
		}
	}


	private BinaryTree<T> findEligible(BinaryTree<T> victim) {
		
		BinaryTree<T> eligible = null;
		
		if(victim.leftExists())
		{ 
			eligible = victim.getLeft();
			while(eligible.getRight() != null)
			{
				eligible = eligible.getRight();
			}
			return eligible;
		}

		if(victim.rightExists())
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
		if (!leftExists()) amount ++;
		if (!rightExists()) amount ++;
		
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

	public T enesimoElemento(int n)
	{
		int n_[] = {n};
		return enesimoElementoRecursive(n_);
	}

	private T enesimoElementoRecursive(int n[])
	{
		T v = null;
		if (leftExists()) v = getLeft().enesimoElementoRecursive(n);
		if (n[0] == 0) return v;

		n[0] --;
		if (n[0] == 0) return value;

		if (rightExists()) v = getRight().enesimoElementoRecursive(n);
		if (n[0] == 0) return v;

		return null;
	}

	public int posicao(T value)
	{
		int n_[] = {0};
		return posicaoRecursive(value, n_);
	}

	private int posicaoRecursive(T value, int n[])
	{
		int i = 0;
		if (leftExists()) i = getLeft().posicaoRecursive(value, n);
		if (n[0] == -1) return i;

		n[0] ++;
		if (this.value.compareTo(value) == 0)
		{
			int tmp = n[0];
			n[0] = -1;
			return tmp;
		}

		if (rightExists()) i = getRight().posicaoRecursive(value, n);
		if (n[0] == -1) return i;

		return i;
	}

	public String preOrder()
	{
		String s = "";
		return preOrderRecursive(s);
	}

	private String preOrderRecursive(String s)
	{
		s += " " + getValue().toString();
		if (leftExists()) s += getLeft().preOrder();
		if (rightExists()) s += getRight().preOrder();
		return s;
	}
	
	public int getChildAmount()
	{
		int v = 0;
		if (leftExists()) v += 1 + getLeft().getChildAmount();
		if (rightExists()) v += 1 + getRight().getChildAmount();
		return v;
	}

	public T mediana()
	{
		int v = 1 + (leftExists() ? 1 + getLeft().getChildAmount() : 0) + 
				(rightExists() ? 1 + getRight().getChildAmount() : 0);

		if (v % 2 == 0) v = v/2 - 1;
		else v = v/2;

		return enesimoElemento(v + 1);
	}
	
	public double media(T value)
	{
		BinaryTree<T> b = search(value);
		
		double sum = 0;
		double n[] = {0};
		
		sum = b.somaRecursive(n);
		
		return sum/n[0];
	}
	
	private double somaRecursive(double n[])
	{
		double sum = valueAsDouble();
		n[0] ++;
		if (leftExists()) sum += getLeft().somaRecursive(n);
		if (rightExists()) sum += getRight().somaRecursive(n);
		return sum;
	}
	
	public double valueAsDouble() {
		/*
		try {
			Double v = (double) value;
			return v;
		}
		catch(ClassCastException e) {
			return 1.0;
		}
		*/
		return 1.0;
	}
	
	private boolean leftExists() {
		return getLeft() != null;
	}
	
	private boolean rightExists() {
		return getRight() != null;
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
	
	public String toStringWithHeight() {
		String s = "BinaryTree\n";
		return s + toStringWithHeight("-");
	}
	
	public String toStringWithHeight(String dashes) {
		String s = this.height + dashes + value + "\n";
		dashes += "-";
		if(leftTree != null) { s += leftTree.toStringWithHeight(dashes); }
		if(rightTree != null) { s += rightTree.toStringWithHeight(dashes); }
		return s;
	}
	
	
	
}
