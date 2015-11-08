/*
 * Copyright (c) 2015 Peter Cashel
 * All rights reserved. 
 *
 * Project	: AssignmentProject-IntroJava-Original
 * Package	: net.petercashel.commonlib.sort.threaded
 * File	Name: ThreadedQuickSort.java
 *
 * Last Modified: 11:32:42 AM 06/11/2015
 * 
 */
package net.petercashel.commonlib.sort.threaded;

import net.petercashel.commonlib.sort.QuickSort;
import static net.petercashel.commonlib.sort.QuickSort.toObject;
import java.util.concurrent.atomic.AtomicReferenceArray;

/**
 * The Class ThreadedQuickSort.
 *
 * @author Peter Cashel
 * @param <T>
 *            the generic type
 */
public class ThreadedQuickSort<T extends Comparable<? super T>> {

	/**
	 * Quicksort. Private working method, called by the threads to do the actual sorting.
	 *
	 * @param <T>
	 *            the generic type
	 * @param dataArray
	 *            the array of typed data to sort
	 * @param start
	 *            the start index of where to sort from
	 * @param end
	 *            the end index of where to sort to
	 */
	//The part that sorts - private
	private static <T extends Comparable<? super T>> void QuickSort(AtomicReferenceArray<T> dataArray, int start, int end) {
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
		swap(dataArray, start, pivotPos);

		//Set pivot point and store value
		pivotPos = start;
		T pivotVal = dataArray.get(start);

		//Pivot is chosen, placed, and ready

		//For each item
		for (int pos = start + 1; pos < end; pos++) {
			//If its less than the pivot
			if (dataArray.get(pos).compareTo(pivotVal) <= (0) ) {
				lastSwappedTo = lastSwappedTo + 1; // Update last swapped

				//Swap to one ahead of the last swapped item
				swap(dataArray, lastSwappedTo, pos);
			}
		}

		//Swap pivot with final last swapped
		swap(dataArray, lastSwappedTo, pivotPos);
		pivotPos = lastSwappedTo; //update pivotPos

		//Recurse into sub arrays
		QuickSort(dataArray, start, pivotPos); //Handle First partition
		QuickSort(dataArray, pivotPos + 1, end); //Handle Third partition
	}

	//Public methods - also thread handing
	
	/**
	 * Quicksort.  This method takes the array and wraps it in an AtomicReferenceArray to make it thread safe. 
	 * Then creates a number of threads to perform the processing, starts and monitors them until they finished.
	 * Once all threads are complete, the contents of the thread safe array is copied into the original array.
	 *
	 * @param <T>
	 *            the generic type
	 * @param dataArray
	 *            the array of typed data to sort
	 * @param start
	 *            the start index of where to sort from
	 * @param end
	 *            the end index of where to sort to
	 */
	public static <T extends Comparable<? super T>> void QuickSort(T[] dataArray, int start, int end) {
		//Create thread-safe array
		AtomicReferenceArray<T> threadSafeDataArray = new AtomicReferenceArray<>(dataArray);

		//Get cores
		final int cores = Runtime.getRuntime().availableProcessors();

		//Array to store threads in
		Thread[] threadsArray = new Thread[cores];

		//Number of elements per thread (with final thread taking everything left)
		final int splitSize = dataArray.length / cores;

		for (int i = 0; i < cores; i++) {

			final int iCore = i;

			threadsArray[i] = new Thread(){
				public void run(){
					System.out.println("Thread " + iCore +" Running");
					if (iCore < cores) {
						QuickSort(threadSafeDataArray, splitSize * iCore, (splitSize * iCore) + splitSize);
					} else {
						QuickSort(threadSafeDataArray, splitSize * iCore, (splitSize * iCore) + end);
					}
					System.out.println("Thread " + iCore +" Finished");
				}
			};

			threadsArray[i].start();			
		}		

		for (Thread t : threadsArray) {
			while(t.isAlive()) {
				// Should lock this thread while this happens. This thread (not the *cores* number of threads)
				// is the control thread and we only want to continue once each thread returns
			}
		}

		for (int i = 0; i < threadSafeDataArray.length(); i++) dataArray[i] = threadSafeDataArray.get(i);

	}

	/**
	 * Quick sort. Type specific version of main method. See main method for details.
	 *
	 * @param array
	 *            the array of typed data to sort
	 * @param start
	 *            the start index of where to sort from
	 * @param end
	 *            the end index of where to sort to
	 */
	//Integer array specific version
	public static void QuickSort(Integer[] array, int start, final int end) {
		//Create thread-safe array
		AtomicReferenceArray<Integer> threadArray = new AtomicReferenceArray<>(array);

		//Get cores
		final int cores = Runtime.getRuntime().availableProcessors();

		//Array to store threads in
		Thread[] threads = new Thread[cores];

		//Number of elements per thread (with final thread taking everything left)
		final int splitSize = array.length / cores;

		for (int i = 0; i < cores; i++) {

			final int iCore = i;

			threads[i] = new Thread(){
				public void run(){
					System.out.println("Thread " + iCore +" Running");
					if (iCore < cores) {
						QuickSort(threadArray, splitSize * iCore, (splitSize * iCore) + splitSize);
					} else {
						QuickSort(threadArray, splitSize * iCore, (splitSize * iCore) + end);
					}
					System.out.println("Thread " + iCore +" Finished");
				}
			};

			threads[i].start();			
		}		

		for (Thread t : threads) {
			while(t.isAlive()) {
				//Should lock this thread while this happens. This thread (not the *cores* number of threads)
				// is the control thread and we only want to continue once each thread returns
			}
		}

		for (int i = 0; i < threadArray.length(); i++) array[i] = threadArray.get(i);

	}

	/**
	 * Quick sort. Type specific version of main method. See main method for details.
	 *
	 * @param array
	 *            the array of typed data to sort
	 * @param start
	 *            the start index of where to sort from
	 * @param end
	 *            the end index of where to sort to
	 */
	//Character array specific version
	public static void QuickSort(Character[] array, int start, int end) {
		//Create thread-safe array
		AtomicReferenceArray<Character> threadArray = new AtomicReferenceArray<>(array);

		//Get cores
		final int cores = Runtime.getRuntime().availableProcessors();

		//Array to store threads in
		Thread[] threads = new Thread[cores];

		//Number of elements per thread (with final thread taking everything left)
		final int splitSize = array.length / cores;

		for (int i = 0; i < cores; i++) {

			final int iCore = i;

			threads[i] = new Thread(){
				public void run(){
					System.out.println("Thread " + iCore +" Running");
					if (iCore < cores) {
						QuickSort(threadArray, splitSize * iCore, (splitSize * iCore) + splitSize);
					} else {
						QuickSort(threadArray, splitSize * iCore, (splitSize * iCore) + end);
					}
					System.out.println("Thread " + iCore +" Finished");
				}
			};

			threads[i].start();			
		}		

		for (Thread t : threads) {
			while(t.isAlive()) {
				//Should lock this thread while this happens. This thread (not the *cores* number of threads)
				// is the control thread and we only want to continue once each thread returns
			}
		}

		for (int i = 0; i < threadArray.length(); i++) array[i] = threadArray.get(i);
	}

	/**
	 * Quick sort. Type specific version of main method. See main method for details.
	 *
	 * @param array
	 *            the array of typed data to sort
	 * @param start
	 *            the start index of where to sort from
	 * @param end
	 *            the end index of where to sort to
	 */
	//Short array specific version
	public static void QuickSort(Short[] array, int start, int end) {
		//Create thread-safe array
		AtomicReferenceArray<Short> threadArray = new AtomicReferenceArray<>(array);

		//Get cores
		final int cores = Runtime.getRuntime().availableProcessors();

		//Array to store threads in
		Thread[] threads = new Thread[cores];

		//Number of elements per thread (with final thread taking everything left)
		final int splitSize = array.length / cores;

		for (int i = 0; i < cores; i++) {

			final int iCore = i;

			threads[i] = new Thread(){
				public void run(){
					System.out.println("Thread " + iCore +" Running");
					if (iCore < cores) {
						QuickSort(threadArray, splitSize * iCore, (splitSize * iCore) + splitSize);
					} else {
						QuickSort(threadArray, splitSize * iCore, (splitSize * iCore) + end);
					}
					System.out.println("Thread " + iCore +" Finished");
				}
			};

			threads[i].start();			
		}		

		for (Thread t : threads) {
			while(t.isAlive()) {
				//Should lock this thread while this happens. This thread (not the *cores* number of threads)
				// is the control thread and we only want to continue once each thread returns
			}
		}

		for (int i = 0; i < threadArray.length(); i++) array[i] = threadArray.get(i);
	}

	/**
	 * Quick sort. Type specific version of main method. See main method for details.
	 *
	 * @param array
	 *            the array of typed data to sort
	 * @param start
	 *            the start index of where to sort from
	 * @param end
	 *            the end index of where to sort to
	 */
	//Long array specific version
	public static void QuickSort(Long[] array, int start, int end) {
		//Create thread-safe array
		AtomicReferenceArray<Long> threadArray = new AtomicReferenceArray<>(array);

		//Get cores
		final int cores = Runtime.getRuntime().availableProcessors();

		//Array to store threads in
		Thread[] threads = new Thread[cores];

		//Number of elements per thread (with final thread taking everything left)
		final int splitSize = array.length / cores;

		for (int i = 0; i < cores; i++) {

			final int iCore = i;

			threads[i] = new Thread(){
				public void run(){
					System.out.println("Thread " + iCore +" Running");
					if (iCore < cores) {
						QuickSort(threadArray, splitSize * iCore, (splitSize * iCore) + splitSize);
					} else {
						QuickSort(threadArray, splitSize * iCore, (splitSize * iCore) + end);
					}
					System.out.println("Thread " + iCore +" Finished");
				}
			};

			threads[i].start();			
		}		

		for (Thread t : threads) {
			while(t.isAlive()) {
				//Should lock this thread while this happens. This thread (not the *cores* number of threads)
				// is the control thread and we only want to continue once each thread returns
			}
		}

		for (int i = 0; i < threadArray.length(); i++) array[i] = threadArray.get(i);
	}
	
	/**
	 * Quick sort. Type specific version of main method. See main method for details.
	 *
	 * @param array
	 *            the array of typed data to sort
	 * @param start
	 *            the start index of where to sort from
	 * @param end
	 *            the end index of where to sort to
	 */
	//Float array specific version
	public static void QuickSort(Float[] array, int start, int end) {
		//Create thread-safe array
		AtomicReferenceArray<Float> threadArray = new AtomicReferenceArray<>(array);

		//Get cores
		final int cores = Runtime.getRuntime().availableProcessors();

		//Array to store threads in
		Thread[] threads = new Thread[cores];

		//Number of elements per thread (with final thread taking everything left)
		final int splitSize = array.length / cores;

		for (int i = 0; i < cores; i++) {
			final int iCore = i;

			threads[i] = new Thread(){
				public void run(){
					System.out.println("Thread " + iCore +" Running");
					if (iCore < cores) {
						QuickSort(threadArray, splitSize * iCore, (splitSize * iCore) + splitSize);
					} else {
						QuickSort(threadArray, splitSize * iCore, (splitSize * iCore) + end);
					}
					System.out.println("Thread " + iCore +" Finished");
				}
			};
			threads[i].start();			
		}		

		for (Thread t : threads) { 	// For each of our threads
			while(t.isAlive()) {	// Wait till it finishes
				//Should lock this thread while this happens. This thread (not the *cores* number of threads)
				// is the control thread and we only want to continue once each thread returns
			}
		}

		//Repack into original array.
		for (int i = 0; i < threadArray.length(); i++) array[i] = threadArray.get(i);
	}

	/**
	 * Quick sort. Type specific version of main method. See main method for details.
	 *
	 * @param array
	 *            the array of typed data to sort
	 * @param start
	 *            the start index of where to sort from
	 * @param end
	 *            the end index of where to sort to
	 */
	//Double array specific version
	public static void QuickSort(Double[] array, int start, int end) {
		//Create thread-safe array
		AtomicReferenceArray<Double> threadArray = new AtomicReferenceArray<>(array);

		//Get cores
		final int cores = Runtime.getRuntime().availableProcessors();

		//Array to store threads in
		Thread[] threads = new Thread[cores];

		//Number of elements per thread (with final thread taking everything left)
		final int splitSize = array.length / cores;

		for (int i = 0; i < cores; i++) {

			final int iCore = i;

			threads[i] = new Thread(){
				public void run(){
					System.out.println("Thread " + iCore +" Running");
					if (iCore < cores) {
						QuickSort(threadArray, splitSize * iCore, (splitSize * iCore) + splitSize);
					} else {
						QuickSort(threadArray, splitSize * iCore, (splitSize * iCore) + end);
					}
					System.out.println("Thread " + iCore +" Finished");
				}
			};

			threads[i].start();			
		}		

		for (Thread t : threads) {
			while(t.isAlive()) {
				//Should lock this thread while this happens. This thread (not the *cores* number of threads)
				// is the control thread and we only want to continue once each thread returns
			}
		}

		for (int i = 0; i < threadArray.length(); i++) array[i] = threadArray.get(i);
	}

	/**
	 * Quick sort. primitive integer specific version. Converts primitive data type into object form using toObject methods for Generics to work.
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
	 *
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
	 *
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
	 *
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
	 * Quick sort. float specific version. Converts primitive data type into object form using toObject methods for Generics to work.
	 *	Then calls the Generic method on the data and finally updates the original array.
	 *
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
	 * Quick sort. double specific version. Converts primitive data type into object form using toObject methods for Generics to work.
	 *	Then calls the Generic method on the data and finally updates the original array.
	 *
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

	/**
	 * Swap. Swaps two pieces of typed data around in an array.
	 *
	 * @param <T>
	 *            the generic type
	 * @param threadArray
	 *            the thread safe dataArray of typed data to sort
	 * @param a
	 *            the position of data item A to be swapped
	 * @param b
	 *            the position of data item B to be swapped
	 */
	//Swaps 2 items around in the array.
	private static <T> void swap(AtomicReferenceArray<T> dataArray, int a, int b) {
		T tmp = dataArray.get(a);
		dataArray.set(a, dataArray.get(b));
		dataArray.set(b, tmp);
	}
}
