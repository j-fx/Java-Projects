
import java.util.Arrays; // import class to sort array

public class SearchTime {
	public static void main(String[] args) {
		// declare and create an array of 100000 integers
		int[] random = new int[100000];

		// declare variable and initialize with random value between 0 and 100000
		int key = (int) (Math.random() * 100000);

		// declare variables to measure time of execution
		long startTime, endTime, executionTime;

		int result; // declare variable result

		// iterate length of random times
		for (int i = 0; i < random.length; i++) {
			// store random integers between 0 to 100000 into random[i]
			random[i] = (int) (Math.random() * 100000);
		}

		// store time before executing search
		startTime = System.nanoTime();

		// search key in random invoking method linearSearch
		// and store returning value
		result = linearSearch(random, key);

		// store time after executing search
		endTime = System.nanoTime();

		// store time between endTime and startTime
		executionTime = endTime - startTime;

		// display linear search results
		if (result < 0) // if negative, not found
			System.out.println("Key " + key + " not found. Linear search took " + executionTime + " nanoseconds.");
		else // else, number found
			System.out.println("Key " + key + " found. Linear search took " + executionTime + " nanoseconds.");

		// sort array numbers before executing binary search
		Arrays.sort(random);

		// store time before executing search
		startTime = System.nanoTime();

		// search key in random invoking method binarySearch
		// and store returning value
		result = binarySearch(random, key);

		// store time after executing search
		endTime = System.nanoTime();

		// store time between endTime and startTime
		executionTime = endTime - startTime;

		// display binary search results
		if (result < 0) // if negative, number not found
			System.out.println("Key " + key + " not found. Binary search took " + executionTime + " nanoseconds.");
		else // else, number found
			System.out.println("Key " + key + " found. Binary search took " + executionTime + " nanoseconds.");

	}

	/** The method for finding a key in the list */
	public static int linearSearch(int[] list, int key) {
		// iterate length of list times
		for (int i = 0; i < list.length; i++)
			if (key == list[i]) // if key is found
				return i; // return index i
		return -1; // else return -1
	}

	/** Use binary search to find the key in the list */
	public static int binarySearch(int[] list, int key) {
		int low = 0; // declare and initialize variable low
		int high = list.length - 1; // declare and initialize variable high

		System.out.println("test high" + high);
		while (high >= low) { // do while high is bigger or equal to low
			int mid = (low + high) / 2; // declare and initialize variable mid
			if (key < list[mid]) // if key smaller than list[mid]
				high = mid - 1; // update high variable to ignore right side of array
			else if (key == list[mid]) // else if key is equal to list[mid]
				return mid; // variable found, return mid
			else // else update low to ignore left side of array
				low = mid + 1;
		}

		return -1 - low; // return -insertion point -1
	}

}
