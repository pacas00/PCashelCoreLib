/*
 * Copyright (c) 2015 Peter Cashel
 * All rights reserved. 
 *
 * Project	: AssignmentProject-IntroJava-Original
 * Package	: net.petercashel.commonlib.sort
 * File	Name: QuickSort.java
 *
 * Last Modified: 10:32:31 AM 06/11/2015
 * 
 */
package net.petercashel.commonlib.sort;

/**
 * The Class QuickSort.
 *
 * @author Peter Cashel
 * @param <T>
 *            the generic type
 */
public class QuickSort<T extends Comparable<? super T>> {

	/**
	 * Quick sort. Generics Version, Expects implementation of Comparable
	 *
	 * @param <T>
	 *            the generic type
	 * @param array
	 *            the array of typed data to sort
	 */
	public static <T extends Comparable<? super T>> void QuickSort(T[] array) {
		QuickSort(array, 0, array.length);		
	}
	
	
	
	/**
	 * Quick sort. Generics Version, Expects implementation of Comparable, allows specification of start and end positions
	 *
	 * @param <T>
	 *            the generic type
	 * @param array
	 *            the array of typed data to sort
	 * @param start
	 *            the start index of where to sort from
	 * @param end
	 *            the end index of where to sort to
	 */
	public static <T extends Comparable<? super T>> void QuickSort(T[] array, int start, int end) {

		//Cancel if only 1 item is selected or if recursion has gone too far.
		if (end-start == 1 || end-start <= 0) return;

		//Position of last swapped item
		int lastSwappedTo = start; //Set to start, as we work with the position after this value

		//Select the pivot
		//Get the item half way though the list, with respect to the section we are working on 
		// Half of (end - start), added to start value.
		double d = (start + (end-start)/2);
		int pivotPos = (int)d ;


		//and swap it with the left (start) position, where it needs to be
		swap(array, start, pivotPos);

		//Set pivot point and store value
		pivotPos = start;
		T pivotVal = array[start];

		//Pivot is chosen, placed, and ready

		//For each item
		for (int pos = start + 1; pos < end; pos++) {
			//If its less than the pivot
			if (array[pos].compareTo(pivotVal) <= 0) {
				lastSwappedTo = lastSwappedTo + 1; // Update last swapped

				//Swap to one ahead of the last swapped item
				swap(array, lastSwappedTo, pos);


			}
		}
		//Swap pivot with final last swapped
		swap(array, lastSwappedTo, pivotPos);
		pivotPos = lastSwappedTo; //update pivotPos


		//Recurse into sub arrays
		QuickSort(array, start, pivotPos); //Handle First partition
		QuickSort(array, pivotPos + 1, end); //Handle Third partition

	}

	/**
	 * Swap. Swaps two pieces of typed data around in an array.
	 *
	 * @param <T>
	 *            the generic type
	 * @param array
	 *            the array of typed data to sort
	 * @param a
	 *            the position of data item A to be swapped
	 * @param b
	 *            the position of data item B to be swapped
	 */
	//Swaps 2 items around in the array.
	private static <T> void swap(T[] array, int a, int b) {
		T tmp = array[a];
		array[a] = array[b];
		array[b] = tmp;
	}


	// THE REBOXING! - Converts passed primative arrays into their class forms, then unboxes them after sort 
	//int and Integer, char vs Character, short vs Short, long vs Long, float vs Float or double vs Double.

	/**
	 * Quick sort. Primitive integer specific version. Converts primitive data type into object form using toObject methods for Generics to work.
	 *	Then calls the Generic method on the data and finally updates the original array.
	 *
	 * @param array
	 *            the array of typed data to sort
	 * @param start
	 *            the start index of where to sort from
	 * @param end
	 *            the end index of where to sort to
	 */
	//int array specific handler
	public static void QuickSort(int[] array, int start, int end) {
		Integer[] arr = toObject(array);
		QuickSort.QuickSort(arr, start, end);
		for (int i = 0; i < array.length; i++) {
			array[i] = arr[i];
		}		
	}

	/**
	 * Quick sort. char specific version. Converts primitive data type into object form using toObject methods for Generics to work.
	 *	Then calls the Generic method on the data and finally updates the original array.
	 * @param array
	 *            the array of typed data to sort
	 * @param start
	 *            the start index of where to sort from
	 * @param end
	 *            the end index of where to sort to
	 */
	//char array specific handler
	public static void QuickSort(char[] array, int start, int end) {
		Character[] arr = toObject(array);
		QuickSort.QuickSort(arr, start, end);
		for (int i = 0; i < array.length; i++) {
			array[i] = arr[i];
		}		
	}

	/**
	 * Quick sort. short specific version. Converts primitive data type into object form using toObject methods for Generics to work.
	 *	Then calls the Generic method on the data and finally updates the original array.
	 * @param array
	 *            the array of typed data to sort
	 * @param start
	 *            the start index of where to sort from
	 * @param end
	 *            the end index of where to sort to
	 */
	//short array specific handler
	public static void QuickSort(short[] array, int start, int end) {
		Short[] arr = toObject(array);
		QuickSort.QuickSort(arr, start, end);
		for (int i = 0; i < array.length; i++) {
			array[i] = arr[i];
		}		
	}

	/**
	 * Quick sort. long specific version. Converts primitive data type into object form using toObject methods for Generics to work.
	 *	Then calls the Generic method on the data and finally updates the original array.
	 * @param array
	 *            the array of typed data to sort
	 * @param start
	 *            the start index of where to sort from
	 * @param end
	 *            the end index of where to sort to
	 */
	//long array specific handler
	public static void QuickSort(long[] array, int start, int end) {
		Long[] arr = toObject(array);
		QuickSort.QuickSort(arr, start, end);
		for (int i = 0; i < array.length; i++) {
			array[i] = arr[i];
		}		
	}

	/**
	 * Quick sort. Float specific version. Converts primitive data type into object form using toObject methods for Generics to work.
	 *	Then calls the Generic method on the data and finally updates the original array.
	 * @param array
	 *            the array of typed data to sort
	 * @param start
	 *            the start index of where to sort from
	 * @param end
	 *            the end index of where to sort to
	 */
	//float array specific handler
	public static void QuickSort(float[] array, int start, int end) {
		Float[] arr = toObject(array);
		QuickSort.QuickSort(arr, start, end);
		for (int i = 0; i < array.length; i++) {
			array[i] = arr[i];
		}		
	}

	/**
	 * Quick sort. Double specific version. Converts primitive data type into object form using toObject methods for Generics to work.
	 *	Then calls the Generic method on the data and finally updates the original array.
	 * @param array
	 *            the array of typed data to sort
	 * @param start
	 *            the start index of where to sort from
	 * @param end
	 *            the end index of where to sort to
	 */
	//double array specific handler
	public static void QuickSort(double[] array, int start, int end) {
		Double[] arr = toObject(array);
		QuickSort.QuickSort(arr, start, end);
		for (int i = 0; i < array.length; i++) {
			array[i] = arr[i];
		}		
	}


	// TODO
	// Move into net.petercashel.commonlib : conversion class

	//Primitive to Object

	/**
	 * To object. Utility Method. Converts an array of the primative type into an array of the object form of the primitive.
	 *
	 * @param doubleArray
	 *            the double array
	 * @return the double[]
	 */
	public static Double[] toObject(double[] doubleArray) {

		Double[] result = new Double[doubleArray.length];
		for (int i = 0; i < doubleArray.length; i++) {
			result[i] = Double.valueOf(doubleArray[i]);
		}
		return result;
	}

	/**
	 * To object. Utility Method. Converts an array of the primative type into an array of the object form of the primitive.
	 *
	 * @param floatArray
	 *            the float array
	 * @return the float[]
	 */
	public static Float[] toObject(float[] floatArray) {

		Float[] result = new Float[floatArray.length];
		for (int i = 0; i < floatArray.length; i++) {
			result[i] = Float.valueOf(floatArray[i]);
		}
		return result;
	}

	/**
	 * To object. Utility Method. Converts an array of the primative type into an array of the object form of the primitive.
	 *
	 * @param longArray
	 *            the long array
	 * @return the long[]
	 */
	public static Long[] toObject(long[] longArray) {

		Long[] result = new Long[longArray.length];
		for (int i = 0; i < longArray.length; i++) {
			result[i] = Long.valueOf(longArray[i]);
		}
		return result;
	}

	/**
	 * To object. Utility Method. Converts an array of the primative type into an array of the object form of the primitive.
	 *
	 * @param shortArray
	 *            the short array
	 * @return the short[]
	 */
	public static Short[] toObject(short[] shortArray) {

		Short[] result = new Short[shortArray.length];
		for (int i = 0; i < shortArray.length; i++) {
			result[i] = Short.valueOf(shortArray[i]);
		}
		return result;
	}

	/**
	 * To object. Utility Method. Converts an array of the primative type into an array of the object form of the primitive.
	 *
	 * @param charArray
	 *            the char array
	 * @return the character[]
	 */
	public static Character[] toObject(char[] charArray) {

		Character[] result = new Character[charArray.length];
		for (int i = 0; i < charArray.length; i++) {
			result[i] = Character.valueOf(charArray[i]);
		}
		return result;
	}

	/**
	 * To object. Utility Method. Converts an array of the primative type into an array of the object form of the primitive.
	 *
	 * @param intArray
	 *            the int array
	 * @return the integer[]
	 */
	public static Integer[] toObject(int[] intArray) {

		Integer[] result = new Integer[intArray.length];
		for (int i = 0; i < intArray.length; i++) {
			result[i] = Integer.valueOf(intArray[i]);
		}
		return result;
	}
}
