/*
 * Copyright (c) 2015 Peter Cashel
 * All rights reserved. 
 *
 * Project	: AssignmentProject-IntroJava-BinaryTreesofObjects
 * Package	: assignmentproject.BinaryTree
 * File	Name: BinaryTree.java
 *
 * Last Modified: 11:53:39 AM 06/11/2015
 * 
 */
package net.petercashel.commonlib.data.BinaryTree;

import java.util.Iterator;

/**
 * The Interface BinaryTree.
 *
 * @param <E>
 *            the element type
 */
public interface BinaryTree<E extends Comparable<? super E>> {

	/**
	 * Store an object in the tree. The object must conform to type Comparable
	 * in order to be inserted in the correct location. Multiple objects representing the
	 * same value can be added.
	 *
	 * @param obj reference to Comparable object to add.
	 */
	void add(E obj);
	/**
	 * Determine whether the tree contains an object with the same value as the
	 * argument.
	 *
	 * @param obj reference to Comparable object whose value will be searched for.
	 * @return true if the value is found.
	 */
	
	boolean contains(E obj);
	/**
	 * Remove an object with a matching value from the tree. If multiple
	 * matches are possible, only the first matching object is removed.
	 *
	 * @param obj Remove an object with a matching value from the tree.
	 */
	void remove(E obj);
	/**
	 * Return a new tree iterator object.
	 *
	 * @return new iterator object.
	 */
	
	Iterator<E> iterator();

	/**
	 * Sets the Search mode the binaryTree operates in.
	 * Note: not all trees may implement all modes
	 *
	 * @param m
	 *            the search mode
	 */
	void searchMode(SearchMode m);

	/**
	 * Size of the tree
	 *
	 * @return the int
	 */
	int size();

	/**
	 * Gets the item at position i, where ordering is determined by search mode.
	 *
	 * @param i
	 *            the i
	 * @return the e
	 */
	E get(int i);

	/**
	 * Gets hte Index of the item, where ordering is determined by search mode.
	 *
	 * @param obj
	 *            the obj
	 * @return the int
	 */
	int indexOf(E obj);

	/**
	 * Removes the item at position i, where ordering is determined by search mode.
	 *
	 * @param i
	 *            the i
	 */
	void remove(int i);
	
	/**
	 * The Enum SearchMode.
	 */
	enum SearchMode {
		
		/**
		 * The preorder search mode.
		 */
		preorder,
		
		/**
		 * The postorder search mode.
		 */
		postorder,
		
		/**
		 * The inorder search mode.
		 */
		inorder,
		
		/**
		 * The levelorder search mode.
		 */
		levelorder
	}

}