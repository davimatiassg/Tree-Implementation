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
				this.height = 1 + (getLeft() != null ? getLeft().getHeight() : getRight().getHeight());
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
/*
	public Boolean remove(T value) throws Throwable
	{
		if(value.compareTo(this.value) == 0)
		{
			removeChildTree(this.root, );
		}
	}

	public Boolean recRemove(T value, int LastComparison) throws Throwable
	{
		if(value == null) return false;
		int comparison = value.compareTo(this.value);
		BinaryTree<T> nextTree = comparison < 0 ? leftTree : rightTree;
		System.out.println("hello: " + value);
		if(comparison != 0)
		{
			System.out.println("hello: " + value);
			Boolean success = nextTree.remove(value);
			if(this.getHeight() - nextTree.getHeight() > 1) updateHeight();
			return success;
		}
		this.getRoot().removeChildTree(nextTree, value.compareTo(this.getRoot().getValue()) < 0 );
		return true; 
	}

	public void removeChildTree(BinaryTree<T> nextTree, Boolean isLeftChild) throws Throwable
	{
		switch(nextTree.getEmptySubTreeAmount())
		{
			case 2:
				if (isLeftChild)	setLeft(null);
				else				setRight(null);
				updateHeight();
				nextTree.finalize();
			break;
			case 1:
				BinaryTree<T> nextNextTree = nextTree.getLeft() != null ? nextTree.getLeft() : nextTree.getRight();
				nextNextTree.setRoot(this);
				if(isLeftChild)	setLeft(nextNextTree);
				else			setRight(nextNextTree);
				updateHeight();
				nextTree.finalize();
			break;
			case 0:
				BinaryTree<T> eligible = findEligible(nextTree);
				swapTree(eligible, nextTree);
				removeChildTree(nextTree, nextTree.getRoot().getLeft() == nextTree);
			break;
		}
	}

	*/
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

	public T enesimoElemento(int n)
	{
		int n_[] = {n};
		return enesimoElementoRecursive(n_);
	}

	private T enesimoElementoRecursive(int n[])
	{
		T v = null;
		if (getLeft() != null) v = getLeft().enesimoElementoRecursive(n);
		if (n[0] == 0) return v;

		n[0] --;
		if (n[0] == 0) return value;

		if (getRight() != null) v = getRight().enesimoElementoRecursive(n);
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
		if (getLeft() != null) i = getLeft().posicaoRecursive(value, n);
		if (n[0] == -1) return i;

		n[0] ++;
		if (this.value.compareTo(value) == 0)
		{
			int tmp = n[0];
			n[0] = -1;
			return tmp;
		}

		if (getRight() != null) i = getRight().posicaoRecursive(value, n);
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
		if (getLeft() != null) s += getLeft().preOrder();
		if (getRight() != null) s += getRight().preOrder();
		return s;
	}
	
	public int getChildAmount()
	{
		int v = 0;
		if (getLeft() != null) v += 1 + getLeft().getChildAmount();
		if (getRight() != null) v += 1 + getRight().getChildAmount();
		return v;
	}

	public T mediana()
	{
		int v = 1 + (getLeft() != null ? 1 + getLeft().getChildAmount() : 0) + 
				(getRight() != null ? 1 + getRight().getChildAmount() : 0);

		v = v/2;

		return enesimoElemento(v);
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
