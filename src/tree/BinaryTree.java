package tree;
import java.lang.Comparable;
import java.text.DecimalFormat;

import javax.management.modelmbean.InvalidTargetObjectTypeException;


public class BinaryTree<T extends Comparable<T>> {
	
	T value;
	int height;
	BinaryTree<T> root;
	BinaryTree<T> leftTree;
	BinaryTree<T> rightTree;
	int leftNodes; // amount of nodes to the left
	int rightNodes; // amount of nodes to the right
	
	// !!!!!!!!!!!!!!!!!!!!what como q o pai sabe da existencia dessa arvore?
	public BinaryTree(T value, BinaryTree<T> root, BinaryTree<T> leftTree, BinaryTree<T> rightTree) {
		this.value = value;
		this.root = root;
		this.leftTree = leftTree;
		this.rightTree = rightTree;
		this.height = 1;
		this.leftNodes = 1;
		this.rightNodes = 1;
	}

	// Creates a tree with 'value' being the root and two children
	public BinaryTree(T value, BinaryTree<T> leftTree, BinaryTree<T> rightTree) {
		this.value = value;
		this.root = this;
		this.leftTree = leftTree;
		this.rightTree = rightTree;
		this.height = 1;
		this.leftNodes = 1;
		this.rightNodes = 1;
	}
	
	// !!!!!!!!!!!!!!!!!!!!what como q o pai sabe da existencia dessa arvore?
	public BinaryTree(T value, BinaryTree<T> root) {
		this.value = value;
		this.root = root;
		this.height = 1;
		this.leftNodes = 0;
		this.rightNodes = 0;
	}
	
	// Creates a root
	public BinaryTree(T value) {
		this.value = value;
		this.root = this;
		this.height = 1;
		this.leftNodes = 0;
		this.rightNodes = 0;
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

	public int getChildAmount()
	{	
		return rightNodes + leftNodes;
	}
	
	private void incrementNodeCount(boolean isLeft)
	{
		if (isLeft)
		{
			leftNodes ++;
			return;
		}
		rightNodes ++;
	}
	
	private boolean leftExists() {
		return getLeft() != null;
	}
	
	private boolean rightExists() {
		return getRight() != null;
	}

	public void updateHeight()
	{
		this.height = 1;
		switch (getEmptySubTreeAmount())
		{
			case 0:
				this.height += (getLeft().getValue().compareTo(getRight().getValue()) > 0 ? getLeft().getHeight() : getRight().getHeight());
				break;
			case 1:
				this.height += (leftExists() ? getLeft().getHeight() : getRight().getHeight());
				break;
		}
	}

	public void swapTree(BinaryTree<T> t1, BinaryTree<T> t2)
	{
		if(t1 == null || t2 == null) return;
		
		T aux = t1.getValue();
		t1.setValue(t2.getValue());
		t2.setValue(aux);

		t1.updateHeight();
		t2.updateHeight();
	}

	public BinaryTree<T> search(T value) 
	{
		int comparison = value.compareTo(this.value);
		BinaryTree<T> nextTree = comparison < 0 ? leftTree : rightTree;
		if(comparison == 0) return this;
		if(nextTree == null) return null;
		return nextTree.search(value);
	}
	
	public Boolean add(T value) 
	{
		int comparison = value.compareTo(this.value);
		if(comparison == 0) return false;

		BinaryTree<T> nextTree = comparison < 0 ? leftTree : rightTree;
		if(nextTree == null) 
		{
			nextTree = new BinaryTree<T>(value, this);
			if(comparison > 0)
			{
				setRight(nextTree);
				rightNodes ++;
			}
			else{
				setLeft(nextTree);
				leftNodes ++;
			}
			updateHeight();
			return true;
		}
		Boolean inserted = nextTree.add(value);
		if(inserted)
		{
			incrementNodeCount(comparison < 0);
			if(getHeight() - nextTree.getHeight() <= 1) updateHeight();
		}
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
			eligible.getRoot().removeChildTree(eligible, eligible.getValue().compareTo(eligible.getRoot().getValue()) < 0);
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
		if (getLeft() == null) amount ++;
		if (getRight() == null) amount ++;
		return amount;
	}

	public T findElementByIndex(int n)
	{
		int n_[] = {n};
		return findElementByIndexRecursive(n_);
	}

	private T findElementByIndexRecursive(int n[])
	{
		T v = null;
		if (leftExists()) v = getLeft().findElementByIndexRecursive(n);
		if (n[0] == 0) return v;

		n[0] --;
		if (n[0] == 0) return value;

		if (rightExists()) v = getRight().findElementByIndexRecursive(n);
		if (n[0] == 0) return v;

		return null;
	}

	public int findIndexByElement(T value)
	{
		int n_[] = {0};
		return findIndexByElementRecursive(value, n_);
	}

	private int findIndexByElementRecursive(T value, int n[])
	{
		int i = 0;
		if (leftExists()) i = getLeft().findIndexByElementRecursive(value, n);
		if (n[0] == -1) return i;

		n[0] ++;
		if (this.value.compareTo(value) == 0)
		{
			int tmp = n[0];
			n[0] = -1;
			return tmp;
		}

		if (rightExists()) i = getRight().findIndexByElementRecursive(value, n);
		if (n[0] == -1) return i;

		return i;
	}

	public T calculateMedian()
	{
		int v = 1 + getChildAmount();	

		v =  v%2 == 0 ? v/2 - 1 : v/2;

		return findElementByIndex(v + 1);
	}
	
	public double calculateAverage(T root) throws InvalidTargetObjectTypeException
	{
		BinaryTree<T> rt = search(root);
		if (rt == null) return -1;
		return rt.calculateAverage();
	}

	public double calculateAverage() throws InvalidTargetObjectTypeException
	{
		if(!(this.getValue() instanceof Number)){
			String s = "Can not calculate the average of non-numeric values and Class '" + this.getValue().getClass().getSimpleName() + "' cannot be asserted to a numerical type.";
			throw new InvalidTargetObjectTypeException(s);
		}

		Double averageParcels[] = {((Number) this.getValue()).doubleValue(), 1.0};
		this.sumSubtrees(averageParcels);		
		return averageParcels[0]/averageParcels[1];
	}

	private void sumSubtrees(Double averageParcels[])
	{
		if (leftExists())
		{
			averageParcels[1]++;
			averageParcels[0] += ((Number)getLeft().getValue()).doubleValue();
			getLeft().sumSubtrees(averageParcels);
		}
		if (rightExists())
		{
			averageParcels[1]++;
			averageParcels[0] += ((Number) getRight().getValue()).doubleValue();
			getRight().sumSubtrees(averageParcels);
		}
	}
	
	public boolean isFull() // MetalAlchemist
	{
		boolean b[] = {true};
		completenessCheck(b, 0);
		return b[0];
	}
	
	public boolean isComplete()
	{
		boolean b[] = {true};
		completenessCheck(b, 1);
		return b[0];
	}
	
	private void completenessCheck(boolean b[], int heightGap)
	{
		if (!b[0]) return;
		int t = 0;
		if(leftExists()){ t += getLeft().getHeight(); }
		if(rightExists()){ t -= getRight().getHeight(); }
		if(Math.abs(t) > heightGap) {
			b[0] = false;
			return;
		}

		if(leftExists()){ getLeft().completenessCheck(b, heightGap);}
		if(rightExists()){ getRight().completenessCheck(b, heightGap);}
	}

	@Override
	public String toString() {
		String s = "BinaryTree\n";
		return s + showTreeDashes();
	}

	public String toString(int i) {
		String s = "BinaryTree\n" + "Root: " + this.getValue() + "\n";
		if (i == 1) return showTreeDashes();
		if (i == 2) return showTreeChain();
		return s;
	}

	public String showTreeChain()
	{
		String s = "(" + this.getValue();
		if(leftExists()) s += getLeft().showTreeChain();
		if(rightExists()) s += getRight().showTreeChain();
		return s+")";
	}

	public String showTreeDashes() { return showTreeDashes("", 
	"_____________________________________________________"+
	"_____________________________________________________"+
	"_____________________________________________________"); }
	
	private String showTreeDashes(String spaces, String dashes) {
		String strV = value.toString();
		try{
			DecimalFormat df = new DecimalFormat();
			df.setMaximumFractionDigits(2);
			strV = df.format(value).toString();
		} catch (Exception e)
		{
			//pass. The type is not numeric.
		}
		
		spaces += "     ";	
		dashes = dashes.substring(0, dashes.length() - 5);
		String s = spaces + strV + dashes.substring(0, dashes.length() - strV.length()) + "\n";
		if(leftExists()) { s += leftTree.showTreeDashes(spaces, dashes); }
		if(rightExists()) { s += rightTree.showTreeDashes(spaces, dashes); }
		return s;
	}
	
	public String showTreeDashesWithHeight() {
		String s = "BinaryTree\n";
		return s + showTreeDashesWithHeightRecursive("-");
	}
	
	private String showTreeDashesWithHeightRecursive(String dashes) {
		String s = this.height + dashes + value + "\n";
		dashes += "-";
		if(leftExists()) { s += leftTree.showTreeDashesWithHeightRecursive(dashes); }
		if(rightExists()) { s += rightTree.showTreeDashesWithHeightRecursive(dashes); }
		return s;
	}
	
	public String toStringPreOrder()
	{
		String s = "";
		s += " " + getValue().toString();
		if (leftExists()) s += getLeft().toStringPreOrder();
		if (rightExists()) s += getRight().toStringPreOrder();
		return s;
	}


}
