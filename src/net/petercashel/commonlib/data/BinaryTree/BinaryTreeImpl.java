/*
 * Copyright (c) 2015 Peter Cashel
 * All rights reserved. 
 *
 * Project	: AssignmentProject-IntroJava-BinaryTreesofObjects
 * Package	: assignmentproject.BinaryTree
 * File	Name: BinaryTreeImpl.java
 *
 * Last Modified: 11:53:39 AM 06/11/2015
 * 
 */
package net.petercashel.commonlib.data.BinaryTree;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Stack;

/**
 * The Class BinaryTreeImpl.
 *
 * @param <E>
 *            the element type
 */
@SuppressWarnings("rawtypes")
public class BinaryTreeImpl<E extends Comparable<? super E>> implements BinaryTree<E> {
	
	/**
	 * The binaryTree search mode.
	 */
	private SearchMode searchMode = SearchMode.preorder;
	
	/**
	 * The root node.
	 */
	public TreeNode<E> root = null;

	/**
	 * The total count of objects in the tree.
	 */
	public int count;

	/**
	 * The internal Class TreeNode.
	 *
	 * @param <E>
	 *            the element type
	 */
	@SuppressWarnings("hiding")
	class TreeNode<E extends Comparable<? super E>> {
		
		/**
		 * The data in the mode.
		 */
		private E data;
		
		/**
		 * The parent node.
		 */
		private TreeNode parent;
		
		/**
		 * The left node.
		 */
		private TreeNode left;
		
		/**
		 * The right node.
		 */
		private TreeNode right;

		/**
		 * Instantiates a new tree node.
		 *
		 * @param newNodeData
		 *            the data to put into the new node
		 */
		public TreeNode(E newNodeData) {
			data = newNodeData;
			parent = left = right = null;
		}

		
		/**
		 * Insert a new data object into this node or subnodes.
		 *
		 * @param obj
		 *            the obj
		 */
		@SuppressWarnings("unchecked")
		public void insert(E obj) {
			if (data.compareTo(obj) > 0) {
				if (right != null) {
					right.insert(obj);
				} else {
					right = new TreeNode<E>(obj);
					right.parent = this;
				}
			} else {
				if (left != null) {
					left.insert(obj);
				} else {
					left = new TreeNode<E>(obj);
					left.parent = this;
				}
			}
		}
		
		/**
		 * Insert node into this node or subnodes.
		 *
		 * @param obj
		 *            the obj
		 */
		@SuppressWarnings("unchecked")
		public void insertNode(TreeNode<E> obj) {
			if (data.compareTo(obj.data) > 0) {
				if (right != null) {
					right.insertNode(obj);
				} else {
					right = obj;
					right.parent = this;
				}
			} else {
				if (left != null) {
					left.insertNode(obj);
				} else {
					left = obj;
					left.parent = this;
				}
			}
		}

		// leaf - no left or right
		/**
		 * Checks if it is a leaf node.
		 *
		 * @return true, if it is a leaf node
		 */
		//
		boolean isLeaf() {
			return ((left == null) && (right == null)) ? true : false;
		}

		/**
		 * Remove itself.
		 */
		@SuppressWarnings("unchecked")
		public void removeSelf() {
			//Are we parents left or right side
			boolean isLeft = false;
			try {
			if (parent.left != null) {
				
				if (parent.left == this)isLeft = true; //otherwise we must be on the right
			}
			} catch (NullPointerException nullex) {
				//null check failed, clearly, left side was null;
				// to be sure of no issues, check if parent is a thing, if not, panic.
				if (parent == null) {
					throw new RuntimeException("Invalid Node. No Parent");
				}
			}
						
			
			if (left == null && right == null) {
				// Simple, no children
				if (isLeft) {
					parent.left = null;
				} else {
					parent.right = null;
				}
				
			} else if ((left != null && right == null) || (left == null && right != null)) {
				// Single Child, Still simple
				if (left != null && right == null) {
					if (isLeft) {
						parent.left = left;
						left.parent = parent;
					} else {
						parent.right = left;
						left.parent = parent;
					}
				} else {
					if (isLeft) {
						parent.left = right;
						right.parent = parent;
					} else {
						parent.right = right;
						right.parent = parent;
					}
				}
				
			} else {
				// worst case, we have left and right children
				// compare to find lower, 
				int side = left.data.compareTo(right.data);
				
				//To hold the inserted side
				TreeNode held;
				
				if (side < 0) {
					//right is new this
					held = left;
				} else {
					//left is new this
					held = right;
					
				}
				
				if (side > -1) {
					if (isLeft) {
						parent.left = left;
						left.parent = parent;
					} else {
						parent.right = left;
						left.parent = parent;
					}
				} else {
					if (isLeft) {
						parent.left = right;
						right.parent = parent;
					} else {
						parent.right = right;
						right.parent = parent;
					}
				}
				
				// lowest becomes this,
				// other gets sent to insert()
				root.insertNode(held);				
			}
			
			
		}
	}

	/* (non-Javadoc)
	 * @see assignmentproject.BinaryTree.BinaryTree#add(E)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void add(E obj) {
		TreeNode n = new TreeNode(obj);
		if (root == null) {
			root = n;
			return;
		}

		root.insert(obj);
		count++;
	}

	/* (non-Javadoc)
	 * @see assignmentproject.BinaryTree.BinaryTree#contains(E)
	 */
	@Override
	public boolean contains(E obj) {
		switch (searchMode) {
		case preorder:
			return PreOrderSearch(root, obj);
		case postorder:
			return PostOrderSearch(root, obj);
		default:
		case inorder:
			return InOrderSearch(root, obj);
		}
	}

	/* (non-Javadoc)
	 * @see assignmentproject.BinaryTree.BinaryTree#remove(E)
	 */
	@Override
	public void remove(E obj) {
		TreeNodeboolean tnb = getNodeByData(obj);
		if (tnb.state == false) return;
		tnb.node.removeSelf();

	}

	/* (non-Javadoc)
	 * @see assignmentproject.BinaryTree.BinaryTree#iterator()
	 */
	@Override
	public Iterator<E> iterator() {
		// TODO Auto-generated method stub
		return new PreOrderIterator();
	}

	/* (non-Javadoc)
	 * @see assignmentproject.BinaryTree.BinaryTree#searchMode(assignmentproject.BinaryTree.Tree.IterateMode)
	 */
	@Override
	public void searchMode(SearchMode m) {
		if (m == SearchMode.levelorder)
			throw new RuntimeException("NOT IMPLEMENTED");
		searchMode = m;
	}

	// Print methods
	
	/**
	 * Prints all items in the tree depending on the search order.
	 */
	public void PrintAll() {
		switch (searchMode) {
		case preorder:
			PreOrderPrint(root);
		case postorder:
			PostOrderPrint(root);
		default:
		case inorder:
			InOrderPrint(root);
		}
	}

	// Pre-Order
	// P L R

	/**
	 * Pre order print.
	 *
	 * @param node
	 *            the node
	 */
	void PreOrderPrint(TreeNode node) {
		System.out.println(node.data.toString() + ", ");
		if (node.left != null)
			PreOrderPrint(node.left);
		if (node.right != null)
			PreOrderPrint(node.right);
	}

	// In-Order
	// L P R

	/**
	 * In order print.
	 *
	 * @param node
	 *            the node
	 */
	void InOrderPrint(TreeNode node) {

		if (node.left != null)
			InOrderPrint(node.left);
		System.out.println(node.data.toString() + ", ");
		if (node.right != null)
			InOrderPrint(node.right);

	}

	// Post-Order
	// L R P

	/**
	 * Post order print.
	 *
	 * @param node
	 *            the node
	 */
	void PostOrderPrint(TreeNode node) {

		if (node.left != null)
			PostOrderPrint(node.left);
		if (node.right != null)
			PostOrderPrint(node.right);
		System.out.println(node.data.toString() + ", ");
	}

	// Search methods - bool return

	// Pre-Order
	// P L R

	/**
	 * Pre order search.
	 *
	 * @param node
	 *            the node
	 * @param obj
	 *            the obj to search for
	 * @return true, if successful
	 */
	boolean PreOrderSearch(TreeNode node, E obj) {
		if (node.data == obj)
			return true;
		if (node.left != null) {
			if (PreOrderSearch(node.left, obj)) {
				return true;
			}
		}
		if (node.right != null) {
			if (PreOrderSearch(node.right, obj)) {
				return true;
			}
		}
		return false;
	}

	// In-Order
	// L P R

	/**
	 * In order search.
	 *
	 * @param node
	 *            the node
	 * @param obj
	 *            the obj to search for
	 * @return true, if successful
	 */
	boolean InOrderSearch(TreeNode node, E obj) {
		if (node.left != null) {
			if (InOrderSearch(node.left, obj)) {
				return true;
			}
		}
		if (node.data == obj)
			return true;
		if (node.right != null) {
			if (InOrderSearch(node.right, obj)) {
				return true;
			}
		}
		return false;
	}

	// Post-Order
	// L R P

	/**
	 * Post order search.
	 *
	 * @param node
	 *            the node
	 * @param obj
	 *            the obj to search for
	 * @return true, if successful
	 */
	boolean PostOrderSearch(TreeNode node, E obj) {
		if (node.left != null) {
			if (PostOrderSearch(node.left, obj)) {
				return true;
			}
		}
		if (node.right != null) {
			if (PostOrderSearch(node.right, obj)) {
				return true;
			}
		}
		if (node.data == obj)
			return true;
		return false;
	}
	
	
	
	// Search methods - TreeNodeboolean return (TreeNode+Boolean)

	/**
	 * The Class TreeNodeboolean. Custom object to return both a boolean and a result
	 */
	public class TreeNodeboolean {
		
		/**
		 * The boolean state.
		 */
		boolean state;
		
		/**
		 * The node.
		 */
		TreeNode node;
		
		/**
		 * Instantiates a new tree nodeboolean.
		 *
		 * @param b
		 *            the b
		 * @param n
		 *            the n
		 */
		TreeNodeboolean(boolean b, TreeNode n) {
			state = b; node = n;
		}
	}
	
	// Pre-Order
	// P L R

	/**
	 * Pre order search tree node.
	 *
	 * @param node
	 *            the node
	 * @param obj
	 *            the obj
	 * @return the tree nodeboolean
	 */
	TreeNodeboolean PreOrderSearchTreeNode(TreeNode node, E obj) {
		if (node.data == obj)
			return new TreeNodeboolean(true, node);
		if (node.left != null) {
			TreeNodeboolean tnb = PreOrderSearchTreeNode(node.left, obj);
			if (tnb.state) {
				return new TreeNodeboolean(true, tnb.node);
			}
		}
		if (node.right != null) {
			TreeNodeboolean tnb = PreOrderSearchTreeNode(node.right, obj);
			if (tnb.state) {
				return new TreeNodeboolean(true, tnb.node);
			}
		}
		return new TreeNodeboolean(false, node);
	}

	// In-Order
	// L P R

	/**
	 * In order search tree node.
	 *
	 * @param node
	 *            the node
	 * @param obj
	 *            the obj
	 * @return the tree nodeboolean
	 */
	TreeNodeboolean InOrderSearchTreeNode(TreeNode node, E obj) {
		if (node.left != null) {
			TreeNodeboolean tnb = InOrderSearchTreeNode(node.left, obj);
			if (tnb.state) {
				return new TreeNodeboolean(true, tnb.node);
			}
		}
		if (node.data == obj)
			return new TreeNodeboolean(true, node);
		if (node.right != null) {
			TreeNodeboolean tnb = InOrderSearchTreeNode(node.right, obj);
			if (tnb.state) {
				return new TreeNodeboolean(true, tnb.node);
			}
		}
		return new TreeNodeboolean(false, node);
	}

	// Post-Order
	// L R P

	/**
	 * Post order search tree node.
	 *
	 * @param node
	 *            the node
	 * @param obj
	 *            the obj
	 * @return the tree nodeboolean
	 */
	TreeNodeboolean PostOrderSearchTreeNode(TreeNode node, E obj) {
		if (node.left != null) {
			TreeNodeboolean tnb = PostOrderSearchTreeNode(node.left, obj);
			if (tnb.state) {
				return new TreeNodeboolean(true, tnb.node);
			}
		}
		if (node.right != null) {
			TreeNodeboolean tnb = PostOrderSearchTreeNode(node.right, obj);
			if (tnb.state) {
				return new TreeNodeboolean(true, tnb.node);
			}
		}
		if (node.data == obj)
			return new TreeNodeboolean(true, node);
		return new TreeNodeboolean(false, node);
	}
	
	/* (non-Javadoc)
	 * @see assignmentproject.BinaryTree.BinaryTree#size()
	 */
	@Override
	public int size() {
		return count;
	}
	
	/**
	 * Gets the node by data.
	 *
	 * @param obj
	 *            the obj
	 * @return the node by data
	 */
	private BinaryTreeImpl<E>.TreeNodeboolean getNodeByData(E obj) {
		switch (searchMode) {
		case preorder:
			return PreOrderSearchTreeNode(root, obj);
		case postorder:
			return PostOrderSearchTreeNode(root, obj);
		default:
		case inorder:
			return InOrderSearchTreeNode(root, obj);
		}
	}
	
	
	
	// Beware, Iterator<E> lies below
	//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	//!!!!!!!!!!!!!!!!!!!!!!NOTE TO ACCESSOR!!!!!!!!!!!!!!!!!!!!!!!!!
	//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	//
	// The iterator below is not my code. 
	// This is copied from http://stackoverflow.com/a/12851421
	// I plan to revise this code and write my own version, for now,
	// it remains as a learning resource and functional code
	//
	//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	
	

	/**
	 * * Simple pre-order iterator class. An iterator object will sequence
	 * through * the tree contents in ascending order. * A stack is used to keep
	 * track of where the iteration has reached in the tree. * Note that if new
	 * items are added or removed during an iteration, there is no * guarantee
	 * that the iteration will continue correctly.
	 */
	private class PreOrderIterator implements Iterator<E> {
		
		/**
		 * The nodes.
		 */
		private Stack<TreeNode<E>> nodes = new Stack<TreeNode<E>>();

		/** * Construct a new iterator for the current tree object. */
		public PreOrderIterator() {
			pushLeft(root);
		}

		/**
		 * * Get next object in sequence. * * @return next object in sequence
		 * or null if the end of the sequence has * been reached.
		 */
		public E next() {
			if (nodes.isEmpty()) {
				return null;
			}
			TreeNode<E> node = nodes.pop();
			pushLeft(node.right);
			return node.data;
		}

		/**
		 * * Determine if there is another object in the iteration sequence. *
		 * * @return true if another object is available in the sequence.
		 */
		public boolean hasNext() {
			return !nodes.isEmpty();
		}

		/**
		 * * The remove operation is not supported by this iterator. This
		 * illustrates * that a method required by an implemented interface can
		 * be written to not * support the operation but should throw an
		 * exception if called. * UnsupportedOperationException is a subclass of
		 * RuntimeException and is * not required to be caught at runtime, so
		 * the remove method does not * have a throws declaration. Calling
		 * methods do not have to use a try/catch * block pair. * * @throws
		 * UnsupportedOperationException if method is called.
		 */
		public void remove() {
			throw new UnsupportedOperationException();
		}

		/**
		 * * Helper method used to push node objects onto the stack to keep *
		 * track of the iteration.
		 *
		 * @param node
		 *            the node
		 */
		private void pushLeft(TreeNode<E> node) {
			while (node != null) {
				nodes.push(node);
				node = node.left;
			}
		}
	}
	
	
	//Get, Remove and indexOf

	/* (non-Javadoc)
	 * @see assignmentproject.BinaryTree.BinaryTree#get(int)
	 */
	@Override
	public E get(int i) {
		Iterator<E> itr = iterator(); 
		int _count = -1;
		while (itr.hasNext()) {
			_count++;
			if (_count == i) {
				return itr.next();
			} else itr.next();
		}
		
		return null;
	}
	
	/* (non-Javadoc)
	 * @see assignmentproject.BinaryTree.BinaryTree#indexOf(E)
	 */
	@Override
	public int indexOf(E obj) {
		Iterator<E> itr = iterator();
		int count = -1;
		while (itr.hasNext()) {
			E data = itr.next();
			count++;
			if (data.compareTo(obj) == 0) {
				return count;
			}
		}
		return -1;
	}
	
	/* (non-Javadoc)
	 * @see assignmentproject.BinaryTree.BinaryTree#remove(int)
	 */
	@Override
	public void remove(int i) {
		Iterator<E> itr = iterator(); 
		int count = -1;
		while (itr.hasNext()) {
			count++;
			if (count == i) {
				E data = itr.next();
				remove(data);
				return;
			} else itr.next();
		}
		
		
	}

}
