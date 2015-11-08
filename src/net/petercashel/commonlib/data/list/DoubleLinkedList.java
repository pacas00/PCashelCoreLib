/*
 * Copyright (c) 2015 Peter Cashel
 * All rights reserved. 
 *
 * Project	: AssignmentProject-IntroJava-DoubleLinkedList
 * Package	: assignmentproject
 * File	Name: DoubleLinkedList.java
 *
 * Last Modified: 11:51:26 AM 06/11/2015
 * 
 */
package net.petercashel.commonlib.data.list;

import java.util.AbstractSequentialList;
import java.util.Iterator;
import java.util.ListIterator;

/**
 * The Class DoubleLinkedList.
 *
 * @author Peter Cashel
 *
 * @param <E>
 *            the element type
 */
public class DoubleLinkedList<E> extends AbstractSequentialList<E> implements Iterable<E> {

	@Override
	public Iterator<E> iterator() {
		return new DoubleLinkedListForwardIterator<E>();
	}

	/**
	 * Descending iterator.
	 *
	 * @return the iterator
	 */
	public Iterator<E> descendingIterator() {
		return new DoubleLinkedListReverseIterator<E>();
	}

	/**
	 * The first node in the list.
	 */
	public Node first = null;
	
	/**
	 * The last node in the list.
	 */
	public Node last = null;

	/**
	 * The total count of items in the list.
	 */
	public int count;

	/**
	 * The Node Class.
	 */
	class Node {
		
		/**
		 * The data stored in the Node.
		 */
		private E data;
		
		/**
		 * The previous node.
		 */
		private Node prev;
		
		/**
		 * The next node.
		 */
		private Node next;
		//private int index;

		/**
		 * Instantiates a new node.
		 *
		 * @param data
		 *            the data
		 */
		public Node(E data) {
			this.data = data;
		}		
	}

	/**
	 * Instantiates a new double linked list.
	 */
	public DoubleLinkedList() {
		first = null; 
		last = null;
	}

	/**
	 * Gets the node at a specific position.
	 *
	 * @param arg0
	 *            the arg0
	 * @return the node
	 */
	public Node getNode(int arg0) {
		if (arg0 > count) return null;
		int pos = 0;
		if (arg0 == 0) {
			return first;
		} else {
			Node n = first;
			while (n.next != null) {
				n = n.next;
				pos++;
				if (pos == arg0) {
					return n;
				}
			}
		}
		return null;
	}

	@Override
	public boolean add(E obj) {
		addLast(obj);
		return contains(obj);
	}

	/**
	 * Adds the data as a new node at the first position.
	 *
	 * @param obj
	 *            the obj
	 */
	public void addFirst(E obj) {
		if (first != null) {
			Node oldFirst = first;
			Node newFirst = new Node(obj);
			oldFirst.prev = newFirst;
			newFirst.next = oldFirst;
			first = newFirst;
		} else {
			Node newFirst = new Node(obj);
			first = newFirst;

		}
		count++;
	}

	/**
	 * Adds the data as a new node at the last position.
	 *
	 * @param obj
	 *            the obj
	 */
	public void addLast(E obj) {
		if (last != null) {
			Node oldlast = last;
			Node newlast = new Node(obj);
			oldlast.next = newlast;
			newlast.prev = oldlast;
			last = newlast;
		} else {
			Node newlast = new Node(obj);
			last = newlast;
			if (first == null) first = last;
		}
		count++;
	}

	/**
	 * Adds the data (obj) in a new node after the node n.
	 *
	 * @param n
	 *            the n
	 * @param obj
	 *            the obj
	 */
	public void addAfter(Node n, E obj) {
		Node before = n;
		Node after = n.next;

		Node newNode = new Node(obj);
		newNode.prev = before;
		newNode.next = after;
		before.next = newNode;
		after.prev = newNode;
		count++;
	}

	/**
	 * Adds the data (obj) in a new node before the node n.
	 *
	 * @param n
	 *            the n
	 * @param obj
	 *            the obj
	 */
	public void addBefore (Node n, E obj) {
		Node before = n.prev;
		Node after = n;

		Node newNode = new Node(obj);
		newNode.prev = before;
		newNode.next = after;
		before.next = newNode;
		after.prev = newNode;
		count++;
	}

	/**
	 * Deletes the node.
	 *
	 * @param n
	 *            the n
	 */
	public void delete(Node n) {
		Node before = n.prev;
		Node after = n.next;
		before.next = after;
		after.prev = before;
		n.data = null;
		n.next = null;
		n.prev = null;
		n = null;
		count--;
	}

	/**
	 * Delete the first node.
	 */
	public void deleteFirst() {
		if (first.next != null) {
			Node newFirst = first.next;
			newFirst.prev = null;
			first = newFirst;
		} else {
			first = null;
		}
		count--;
	}

	/**
	 * Delete the last node.
	 */
	public void deleteLast() {
		if (last.prev != null) {
			Node newLast = last.prev;
			newLast.next = null;
			last = newLast;
		} else {
			last = null;
		}
		count--;
	}

	@Override
	public int size() {
		return count();
	}

	/**
	 * return a count of the items in the list.
	 *
	 * @return the int
	 */
	public int count() { return count;}

	/**
	 * Take the first data item, removing it in the process.
	 *
	 * @return the first data item
	 */
	public E takeFirst() {
		Node n = first;
		deleteFirst();
		return n.data;
	}

	/**
	 * Take the last data item, removing it in the process.
	 *
	 * @return the last data item
	 */
	public E takeLast() {
		Node n = last;
		deleteLast();
		return n.data;
	}

	/**
	 * Gets the first data item.
	 *
	 * @return the first data item
	 */
	public E getFirst() {
		Node n = first;
		return n.data;
	}

	/**
	 * Gets the last data item.
	 *
	 * @return the last data item
	 */
	public E getLast() {
		Node n = last;
		return n.data;
	}

	/**
	 * The Class DoubleLinkedListForwardIterator.
	 *
	 * @param <T>
	 *            the generic type
	 */
	public class DoubleLinkedListForwardIterator<T> implements Iterator<E> {
		
		/**
		 * The index node.
		 */
		private Node index; 
		
		/**
		 * The next node is the first node.
		 */
		private boolean firstIsNext = false;

		/**
		 * Instantiates a new double linked list forward iterator.
		 */
		private DoubleLinkedListForwardIterator() {
			index = first;
			firstIsNext = true;
		}

		@Override
		public boolean hasNext() {
			if (index != null)
			return index.next != null;
			else return false;
		}

		@Override
		public E next() {
			if (firstIsNext)
				firstIsNext = false;
			else
				index = index.next;
			return index.data;
		}

		/**
		 * Checks for previous.
		 *
		 * @return true, if successful
		 */
		public boolean hasPrevious() {
			return index.prev != null;
		}

		/**
		 * Previous data item.
		 *
		 * @return the previous data item.
		 */
		public E previous() {
			index = index.prev;
			return index.data;
		}

		/*
		 * @see java.util.Iterator#remove()
		 */
		@Override
		public void remove() {
			Node before = null;
			if (index.prev != null) {
				before = index.prev;
			}
			Node after = null;
			if (index.next != null) {
				after = index.next;
			}

			if (before == null && after != null) {
				//First
				after.prev = null;
				index.next = null;
				first = after;
				index = after;
				firstIsNext = true;
				
			} else if (before != null && after != null) {
				//Somewhere in the middle
				after.prev = before;
				before.next = after;
				index.prev = null;
				index.next = null;
				index.data = null;
				index = before;				
				
			} else if (before != null && after == null) {
				//Last
				before.next = null;
				index.prev = null;
				index = null;
				
			}
		}
	}

	/**
	 * The Class DoubleLinkedListReverseIterator.
	 *
	 * @param <T>
	 *            the generic type
	 */
	public class DoubleLinkedListReverseIterator<T> implements Iterator<E> {
		
		/**
		 * The index of the current node.
		 */
		private Node index; 
		
		/**
		 * The next item is the last index.
		 */
		private boolean lastIsNext = false;

		/**
		 * Instantiates a new double linked list reverse iterator.
		 */
		private DoubleLinkedListReverseIterator() {
			index = last;
		}

		@Override
		public boolean hasNext() {
			return index.prev != null;
		}

		@Override
		public E next() {
			if (lastIsNext)
				lastIsNext = false;
			else
				index = index.prev;
			return index.data;
		}

		/*
		 * @see java.util.Iterator#remove()
		 */
		@Override
		public void remove() {
			Node before = null;
			if (index.prev != null) {
				before = index.prev;
			}
			Node after = null;
			if (index.next != null) {
				after = index.next;
			}

			if (before == null && after != null) {
				//First
				after.prev = null;
				index.next = null;
				first = after;
				index = after;
				lastIsNext = true;
				
			} else if (before != null && after != null) {
				//Somewhere in the middle
				after.prev = before;
				before.next = after;
				index.prev = null;
				index.next = null;
				index.data = null;
				index = before;				
				
			} else if (before != null && after == null) {
				//Last
				before.next = null;
				index.prev = null;
				index = null;
				
			}
		}
	}

	//Interface required stuff below


	/**
	 * The Class DoubleLinkedListIterator.
	 *
	 * @param <T>
	 *            the generic type
	 */
	public class DoubleLinkedListIterator<T> implements ListIterator<E> {
		
		/**
		 * The curr index.
		 */
		private Node currIndex;
		
		/**
		 * The pos.
		 */
		private int pos = 0;
		
		/**
		 * The lock.
		 */
		private boolean lock = false;
		
		/**
		 * The first is next.
		 */
		private boolean firstIsNext = false;

		/**
		 * Instantiates a new double linked list iterator.
		 *
		 * @param i
		 *            the i
		 */
		private DoubleLinkedListIterator(int i) {
			currIndex = first;
			firstIsNext = true;
		}

		@Override
		public boolean hasNext() {
			return currIndex.next != null;
		}

		@Override
		public E next() {
			lock = false;
			if (firstIsNext)
				firstIsNext = false;
			else
				currIndex = currIndex.next;
			pos++;
			return currIndex.data;
		}

		public boolean hasPrevious() {
			return currIndex.prev != null;
		}

		public E previous() {
			lock = false;
			currIndex = currIndex.prev;
			pos--;
			return currIndex.data;
		}

		/*
		 * @see java.util.Iterator#remove()
		 */
		@Override
		public void remove() {
			if (lock) throw new IllegalStateException();
			lock = true;
			Node before = null;
			if (currIndex.prev != null) {
				before = currIndex.prev;
			}
			Node after = null;
			if (currIndex.next != null) {
				after = currIndex.next;
			}

			if (before == null && after != null) {
				//First
				after.prev = null;
				currIndex.next = null;
				first = after;
				currIndex = after;
				firstIsNext = true;
				
			} else if (before != null && after != null) {
				//Somewhere in the middle
				after.prev = before;
				before.next = after;
				currIndex.prev = null;
				currIndex.next = null;
				currIndex.data = null;
				currIndex = before;				
				
			} else if (before != null && after == null) {
				//Last
				before.next = null;
				currIndex.prev = null;
				currIndex = null;
				
			}
		}

		@Override
		public void add(E e) {
			lock = true;
			Node current = currIndex;
			Node oldNext = currIndex.next;
			
			Node n = new Node(e);
			n.prev = current;
			n.next = oldNext;
			current.next = n;
			oldNext.prev = n;
			
			
		}

		@Override
		public int nextIndex() {
			if (pos == count) return size();
			else
			return pos + 1;
		}

		@Override
		public int previousIndex() {
			if (pos == 0) return 0; //should apparently return -1,
			else
			return pos - 1;
		}

		@Override
		public void set(E e) {
			if (lock) throw new IllegalStateException();
			lock = true;
			currIndex.data = e;
		}
	}

	public E get(int arg0) {
		if (arg0 > count) return null;
		int pos = 0;
		if (arg0 == 0) {
			return first.data;
		} else {
			Node n = first;
			while (n.next != null) {
				n = n.next;
				pos++;
				if (pos == arg0) {
					return n.data;
				}
			}
		}
		return null;
	}

	public E remove(int arg0) {
		E data = null;
		int pos = 0;
		if (arg0 == 0) {
			data = first.data;
			deleteFirst();
		} else if (arg0 == count) {
			data = last.data;
			deleteLast();
		} else {
			Node n = first;
			while (n.next != null) {
				n = n.next;
				pos++;
				if (pos == arg0) {
					data = n.data;
					delete(n);
				}
			}
		}
		return data;
	}

	@Override
	public E set(int arg0, E arg1) {
		int pos = 0;
		if (arg0 == 0) {
			first.data = arg1;
			return first.data;
		} else {
			Node n = first;
			while (n.next != null) {
				n = n.next;
				pos++;
				if (pos == arg0) {
					n.data = arg1;
					return n.data;
				}
			}
		}
		return null;
	}

	@Override
	public boolean contains(Object arg0) {
		if (count > 0) {
			if (first.data == arg0) {
				return true;
			} else {
				Node n = first;
				while (n.next != null) {
					n = n.next;
					if (n.data == arg0) {
						return true;
					}
				}
			}
		}
		return false;
	}

	@Override
	public ListIterator<E> listIterator(int arg0) {
		return new DoubleLinkedListIterator<E>(arg0);
	}
}
