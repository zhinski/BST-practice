//Michael Dobrzanski
import java.util.Arrays;

/**
 * Please put your name here!
 * 
 * A binary search tree implementing a set concept for Comparable objects such
 * as Strings, Integers, etc. Uses the "binary search invariant": For each node
 * n, all nodes to the left of n have data which is less than n.data and all
 * nodes to the right have data which is greater than n.data.
 *
 * @param <T>
 *            Type of data to store in the tree
 */
public class BSTree<T extends Comparable<T>> {
	private static class Node<T extends Comparable<T>> {
		/**
		 * Create a leaf node (definition of leaf: node with no children)
		 * 
		 * @param data
		 */
		public Node(T data) {
			this.data = data;
			left = right = null;
		}

		// Won't include a parent pointer/reference
		public T data;
		// Left reference goes to "smaller" data values
		// Right reference goes to "larger" data values
		// There is no way to "go back to where you started"
		public Node<T> left, right;
	}

	// Entry point to the tree (just one of them!)
	private Node<T> root;
	// It's kind of unnecessary to have "first" and "last" reference.

	/**
	 * Create an empty tree with no data.
	 */
	public BSTree() {
		// Our entry point refers to nothing because the tree is empty.
		root = null;
	}

	/**
	 * Add a piece of data to the tree (include as a member of the set)
	 * 
	 * @param data
	 * @return true if we added it, false if we didn't (already in the tree)
	 */
	public boolean add(T data) {
		// Special case: what if root is null?
		// If it's null, we have to add to the root.
		if (root == null) {
			// Plug a (leaf) node into root!
			root = new Node<>(data);
			// We're done and we can return true.
			return true;
		}
		// Otherwise, launch into a recursive helper method.
		// Two ways to structure our code:
		// We can write a method for the Node class,
		// or write a method that takes a Node parameter.
		// Focus on the second way?
		return add(root, data);
	}

	private boolean add(Node<T> node, T data) {
		// Idea: We will make a choice based on the comparison of
		// parameter data to the node data.
		// Go left if parameter data is less than node data.
		// Go right if parameter data is greater than node data.
		// Equal: return false (already there, can't add)

		// How do you compare strings?!?!
		// We actually saw this with the Collection Mystery.
		// Do you remember the method we used?
		// Use the compareTo method: therefore, T must
		// implement Comparable.

		// Save the result of the comparison.
		int comp = data.compareTo(node.data);
		// Go left if parameter data is less than node data.
		if (comp < 0) {
			// Do recursion to the left
			// What if there is no left node?
			// Hey, that's where the data belongs!
			if (node.left == null) {
				node.left = new Node<>(data);
				return true; // We did add it.
			}
			// "The left node knows how to solve this problem"
			return add(node.left, data);
		} else if (comp > 0) {
			// Right case: "Mirror version" of left case
			if (node.right == null) {
				node.right = new Node<>(data);
				return true;
			}
			// "The right node knows how to solve this problem"
			return add(node.right, data);
		}
		// If we are here, we aren't less than or greater than...
		// We are equal to! (Note: doubles are weird and NaN
		// doesn't this property, but Doubles are OK)

		// If we're trying to add something that's already in
		// the "set", what should we do?
		return false; // Don't add data to the tree.
	}

	/**
	 * Return true if tree contains data, false otherwise.
	 * 
	 * @param data
	 * @return
	 */
	public boolean contains(T data) {
		if (root == null) {
			return false;
		}
		return contains(root, data);
	}

	private boolean contains(Node<T> node, T data) {
		int comp = data.compareTo(node.data);
		// Go left if parameter data is less than node data.
		if (comp < 0) {
			// Do recursion to the left
			// What if there is no left node?
			// Hey, that's where the data belongs!
			if (node.left == null) {
				return false; // We did add it.
			}
			// "The left node knows how to solve this problem"
			return contains(node.left, data);
		} else if (comp > 0) {
			// Right case: "Mirror version" of left case
			if (node.right == null) {
				return false;
			}
			// "The right node knows how to solve this problem"
			return contains(node.right, data);
		}
		// If we are here, we aren't less than or greater than...
		// We are equal to! (Note: doubles are weird and NaN
		// doesn't this property, but Doubles are OK)

		// If we're trying to find something that's already in
		// the "set", what should we do?
		return true; // It's here!
	}

	/**
	 * Print every piece of data in the tree in order.
	 */
	public void print() {
		print(root);
	}

	private void print(Node<T> node) {
		if (node == null)
			return;
		print(node.left);
		System.out.println(node.data);
		print(node.right);
	}

	/**
	 * Print a visual representation of the tree. Rotate your head left to see it!
	 */
	public void printStructure() {
		printStructure(root, 0);
	}

	private void printStructure(Node<T> node, int level) {
		if (node == null)
			return;
		// Larger things go first (will be on the right when rotating head left)
		printStructure(node.right, level + 1); // One more level below node
		char[] indent = new char[2 * level];
		Arrays.fill(indent, ' ');
		System.out.print(indent);
		System.out.println(node.data);
		printStructure(node.left, level + 1); // One more level below node
	}

	/**
	 * Return the size of the whole tree.
	 * 
	 * @return
	 */
	public int size() {
		return size(root);
	}

	/**
	 * Return the size of the subtree rooted at this node. "bough of the tree"
	 * 
	 * @param node
	 * @return
	 */
	private int size(Node<T> node) {
		if (node == null)
			return 0;
		return size(node.left) + size(node.right) + 1;
	}

	/**
	 * Calculate the height of the tree (maximum path length) Also the maximum
	 * number of steps from the root, plus one (or 0 if there is no root)
	 * 
	 * @return
	 */
	public int height() {
		return height(root);
	}

	private int height(Node<T> node) {
		if (node == null)
			return 0;
		return Math.max(height(node.right), height(node.left)) + 1;
	}

	/**
	 * Remove an element from the tree, if it exists.
	 * 
	 * @param element
	 * @return true if removed element, false if element wasn't in tree
	 */
	public boolean remove(T element) {
		if (root == null)
			return false;
		boolean ret = remove(root, element);
		if (root.data == null)
			root = null;
		return ret;
	}

	private boolean remove(Node<T> node, T element) {
		// Idea: find what we want to remove
		int comparison = element.compareTo(node.data);
		// Go left if element < data (comparison < 0)
		if (comparison < 0) { // If it were in the tree,
			// it would be to the left
			if (node.left == null)
				return false;
			boolean ret = remove(node.left, element);
			// If left set its room on fire, remove it.
			if (node.left.data == null)
				node.left = null;
			return ret;
		}
		if (comparison > 0) { // If it were in the tree,
			// it would be to the right
			if (node.right == null)
				return false;
			boolean ret = remove(node.right, element);
			if (node.right.data == null)
				node.right = null;
			return ret;
		}
		// At this point, we found what we're looking for
		// Let's try to find the successor of this node's data
		// What if right is null?
		if (node.right == null) {
			// We can replace ourself with our left child.
			// Unless it doesn't exist!
			if (node.left == null) {
				// We have to burst into flames and vanish.
				// We have to tell our parent that we are going away.
				node.data = null; // Signal to the parent we are going away.
			} else {
				// Replace ourselves with left child.
				node.data = node.left.data;
				node.right = node.left.right;
				// We have to reassign left last.
				node.left = node.left.left;
			}
			return true; // Deletion is happening.
		}
		// We can use a loop to make successor go to the smallest
		// node underneath it, and that is the true successor.
		Node<T> successor = node.right; // Go right first...
		Node<T> parent = node; // Parent to successor
		while (successor.left != null) {
			parent = successor; // Parent to the successor is the old successor
			successor = successor.left;
		}
		// "Move successor into the current node"
		node.data = successor.data;
		// "Successor goes away and becomes its right child"
		// Parent to successor should stop talking to successor
		if (parent.left == successor) {
			// Successor is parent's left child
			parent.left = successor.right; // Becomes right child
		} else {
			// Successor must be the right child of parent
			// The only time it's a right child is if we made
			// that first right turn before going left
			// We could assert parent == node
			parent.right = successor.right; // (Still) Becomes right child
		}
		return true;
	}

	/**
	 * Returns the data value of the node that can reach both a and b in the least
	 * number of steps. If the tree doesn't contain both a and b, return null.
	 * 
	 * @param a
	 * @param b
	 * @return data value
	 */

	public T reachesBoth(T a, T b) {
		if (!contains(a)|| !contains(b)) {
			return null;
		}
		Node <T> current = root, right = current.right, left = current.left;
		if (left != null && contains(left, a) && contains(left, b)) {
			current = left;
		}
		if (right != null && contains(right, a) && contains(right, b)) {
			current = right;
		}
		while (true) {
			if (left != null && contains(left, a) && contains(left, b)) {
				current = left;
				left = current.left;
				right = current.right;
				continue;
			}
			if (right != null && contains(right, a) && contains(right, b)) {
				current = right;
				left = current.left;
				right = current.right;
				continue;
			}
			return current.data;
		}
	}


	/**
	 * Among all the nodes which are farthest from the root, find the one which is
	 * farthest to the right. (If there is only one node which is farthest from the
	 * root, that one node would have to be the rightmost lowest.)
	 * 
	 * @return data value of said node
	 */

	public T findRightmostLowest() {
		//find nodes where height is greatest (deepest nodes)
		//find greatest(right-most) value at greatest depth
		//height: (node == null) returns 0 --> height counts down from root, not up
		if (root == null) {
			return null;
		}
		Node <T> current = root;
		while (true) {
			if (height(current.right) != 0 && height(current.left) <= height(current.right)) {
				current = current.right;
			}
			else if (height(current.left) != 0 && height(current.left) > height(current.right)){
				current = current.left;
			}
			else
				return current.data;
		}
	}

	/**
	 * Return the ith indexed element according to the Comparable sorted order of
	 * the tree. The smallest (leftmost) node has index 0 and the largest
	 * (rightmost) node has index size() - 1.
	 * 
	 * @param i
	 *            index
	 * @return element, or null if i is out of range.
	 */
	public T findByOrder(int i) {
		//check sub tree size to handle some work
		//like finding a value at an index.  Max = size - 1; use "size" method to check subtree size
		// TODO: Implement for extra credit.
//		if (root == null) {
//		return null;
//	}
//	Node <T> current = root, right = current.right, left = current.left;
//	while (true) {
//		if (height(current.right) != 0 && height(current.left) <= height(current.right)) {
//			current = current.right;
//		}
//		else if (height(current.left) != 0 && height(current.left) > height(current.right)){
//			current = current.left;
//		}
//		else
//			return current.data;
//	}
//}

		return null;
	}

	/**
	 * EXTRA CREDIT: Balance the tree. The new root should be the
	 * findKthLargest(size()/2) node. Recursively, the root of each subtree should
	 * also be the size/2-largest node (indexed from 0) of that subtree. This method
	 * should not call new and should execute in O(n log n) time for full credit.
	 */
	public void balance() {
		//find median.  Median becomes the root.  
		//Anything in the left sub tree greater than median goes to right subtree (same for right side)
		//Median of sub trees become children of root, rinse repeat finding medians until sorted
		// TODO: Implement for extra credit.
	}

}
