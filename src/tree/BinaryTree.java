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

	public T findElementByIndex(int n)
	{
		int n_[] = {n};
		return findElementByIndexRecursive(n_);
	}

	private T findElementByIndexRecursive(int n[])
	{
		T v = null;
		if (leftExists()) v = getLeft(). findElementByIndexRecursive(n);
		if (n[0] == 0) return v;

		n[0] --;
		if (n[0] == 0) return value;

		if (rightExists()) v = getRight(). findElementByIndexRecursive(n);
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

	public int getChildAmount()
	{
		int v = 0;
		if (leftExists()) v += 1 + getLeft().getChildAmount();
		if (rightExists()) v += 1 + getRight().getChildAmount();
		return v;
	}

	public T calculateMedian()
	{
		int v = 1 + (leftExists() ? 1 + getLeft().getChildAmount() : 0) + 
				(rightExists() ? 1 + getRight().getChildAmount() : 0);

		if (v % 2 == 0) v = v/2 - 1;
		else v = v/2;

		return findElementByIndex(v + 1);
	}
	
	public double calculateAverage(T root) throws InvalidTargetObjectTypeException
	{
		return calculateAverage(search(root));
	}

	public double calculateAverage(BinaryTree<T> root) throws InvalidTargetObjectTypeException
	{
		if(!(root.getValue() instanceof Number)){
			String s = "Class '" + root.getValue().getClass().getSimpleName() + "' cannot be asserted to a number type.";
			throw new InvalidTargetObjectTypeException(s);
		}

		Double averageParcels[] = {((Number) root.getValue()).doubleValue(), 1.0};
		root.calculateAverageRecursive(averageParcels);		
		return averageParcels[0]/averageParcels[1];
	}

	private void calculateAverageRecursive(Double averageParcels[])
	{
		if (leftExists())
		{
			averageParcels[1]++;
			averageParcels[0] += ((Number)getLeft().getValue()).doubleValue();
			getLeft().calculateAverageRecursive(averageParcels);
		}
		if (rightExists())
		{
			averageParcels[1]++;
			averageParcels[0] += ((Number) getRight().getValue()).doubleValue();
			getRight().calculateAverageRecursive(averageParcels);
		}
	}
	
	public boolean isFull() // MetalAlchemist
	{
		boolean b[] = {true};
		isCompleteRecursive(b, true);
		return b[0];
	}
	
	public boolean isComplete()
	{
		boolean b[] = {true};
		isCompleteRecursive(b, false);
		return b[0];
	}
	
	private void isCompleteRecursive(boolean b[], boolean checkForFull)
	{
		if (!b[0]) return;
		
		if (leftExists() && rightExists()) {
			getLeft().isCompleteRecursive(b, checkForFull);
			getRight().isCompleteRecursive(b, checkForFull);
			return;
		}
		else {
			if (leftExists() || rightExists()) { // one of them is missing, not ok
			
				if (!checkForFull)
				{
					if (leftExists()) if (getLeft().getEmptySubTreeAmount() != 2) b[0] = false;
					if (rightExists()) if (getRight().getEmptySubTreeAmount() != 2) b[0] = false;
				}
				else {
					b[0] = false;
				}
				return;
			}
			return;
		}
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
		return s + showTreeDashes();
	}

	public String toString(int i) {
		String s = "BinaryTree\n" + "Root: " + this.getValue() + "\n";
		if (i == 1) return showTreeDashes();
		if (i == 2) return showTreeChain();
		return "";
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
		DecimalFormat df = new DecimalFormat();
		df.setMaximumFractionDigits(2);
		String strV = df.format(value).toString();
		spaces += "     ";	
		dashes = dashes.substring(0, dashes.length() - 5);
		String s = spaces + strV + dashes.substring(0, dashes.length() - strV.length()) + "\n";
		if(leftTree != null) { s += leftTree.showTreeDashes(spaces, dashes); }
		if(rightTree != null) { s += rightTree.showTreeDashes(spaces, dashes); }
		return s;
	}
	
	public String showTreeDashesWithHeight() {
		String s = "BinaryTree\n";
		return s + showTreeDashesWithHeightRecursive("-");
	}
	
	private String showTreeDashesWithHeightRecursive(String dashes) {
		String s = this.height + dashes + value + "\n";
		dashes += "-";
		if(leftTree != null) { s += leftTree.showTreeDashesWithHeightRecursive(dashes); }
		if(rightTree != null) { s += rightTree.showTreeDashesWithHeightRecursive(dashes); }
		return s;
	}
	
	public String toStringPreOrder()
	{
		String s = "";
		return toStringPreOrderRecursive(s);
	}

	private String toStringPreOrderRecursive(String s)
	{
		s += " " + getValue().toString();
		if (leftExists()) s += getLeft().toStringPreOrder();
		if (rightExists()) s += getRight().toStringPreOrder();
		return s;
	}
}
