import java.util.Scanner; // import Scanner class to take input

public class CreditCard {
	public static void main(String[] args) {
		Scanner input = new Scanner(System.in); // create object input

		long creditCard = 0; // declare and initialize variable

		while (true) { // repeat until storing correct creditCard input
			// message to console asking for input
			System.out.println("Enter credit card number as a long integer:  ");
			if (input.hasNextLong()) { // if input identifies next integer
				creditCard = input.nextLong(); // store integer value into creditCard
				break; // break while loop
			} else { // else, display error message to console
				System.out.println("Wrong input, please try again.\n");
				input.next(); // read next input
			}
		}
		input.close(); // close input

		if (isValid(creditCard)) { // if method isValid with credit card as argument returns true
			System.out.println("Card is valid"); // card is valid, display message
		} else { // else, card is invalid
			System.out.println("Card is invalid"); // display message
		}
	}

	/** Return true if the card number is valid */
	public static boolean isValid(long number) {
		int size = getSize(number); // declare and initialize variable with size of number
		if (size >= 13 && size <= 16) { // if size between 13 and 16 (including both)
			// declare and create array with card prefixes
			int[] prefix = { 4, 5, 37, 6 };

			for (int i = 0; i < prefix.length; i++) { // check if prefix math
				// call method prefixMatched with number and prefix[i] as argument
				if (prefixMatched(number, prefix[i])) { // if PrefixMatched returns true
					int result = 0; // declare and initialize variable

					// call methods with number as argument and add returning value to result
					result += sumOfOddPlace(number);
					result += sumOfDoubleEvenPlace(number);

					if (result % 10 == 0) // if result is divisible by 10
						return true; // number is valid
					else /// else,
						return false;// number is invalid
				}
			}
			return false; // prefix do not match, number is invalid
		} else // else,
			return false; // number is invalid

	}

	/** Get the result from Step 2 */
	public static int sumOfDoubleEvenPlace(long number) {
		int sum = 0; // declare and initialize variable
		int size = getSize(number); // declare and initialize variable with size of number

		// convert number to string and store value into strNumber
		String strNumber = String.valueOf(number);

		// iterate trough even places from number from right to left
		for (int i = size - 2; i >= 0; i -= 2) {
			char charDigit = strNumber.charAt(i); // store digit index i into charDigit
			int digit = Character.getNumericValue(charDigit); // store charDigit into digit as integer

			// update with double digit, use method getDigit in case of digit * 2 resulting
			// in more than 1 digit
			digit = getDigit(digit * 2);
			sum += digit; // add digit to sum
		}
		return sum; // return sum of doubled even-place digits in number

	}

	/**
	 * Return this number if it is a single digit, otherwise, return the sum of the
	 * two digits
	 */
	public static int getDigit(int number) {
		if (number > 9) { // if number more than 1 digit
			return number - 9; // return sum of two digits in number
		} else { // else,
			return number; // return number
		}
	}

	/** Return sum of odd-place digits in number */
	public static int sumOfOddPlace(long number) {
		int sum = 0; // declare and initialize variable
		int size = getSize(number); // declare and initialize variable with size of number

		// declare and initialize strNumber with conversion of number to string
		String strNumber = String.valueOf(number);

		// iterate trough odd places from number from right to left
		for (int i = size - 1; i >= 0; i -= 2) {
			char charDigit = strNumber.charAt(i); // store digit index i into charDigit
			int digit = Character.getNumericValue(charDigit); // store charDigit into digit as integer
			sum += digit; // add digit to sum
		}
		return sum; // return sum of odd-place digits in number
	}

	/** Return true if the digit d is a prefix for number */
	public static boolean prefixMatched(long number, int d) {
		int size = getSize(d); // store size of d into size
		// declare and initialize prefix with prefix of number
		long prefix = getPrefix(number, size);

		// if prefix is equal to d return true, else return false
		if (prefix == d)
			return true;
		else
			return false;
	}

	/** Return the number of digits in d */
	public static int getSize(long d) {
		// size = d log base 10. + 1 is used in case of number 0
		int size = (int) (Math.log10(d) + 1);
		return size; // return size
	}

	/**
	 * Return the first k number of digits from number. If the number of digits in
	 * number is less than k, return number.
	 */
	public static long getPrefix(long number, int k) {

		int size = getSize(number); // store size of number into size
		if (size < k) { // if size less than k
			return number; // return number
		} else { // else,
			long prefix = 0; // declare and initialize variable prefix
			String strNumber = String.valueOf(number); // store number as String into strNumber

			/// iterate k times;
			for (int i = 0, j = k - 1; i < k; i++, j--) {
				// declare and initialize digit with integer value from strNumber index i
				int digit = Character.getNumericValue(strNumber.charAt(i));
				// add to prefix digit multiplied by power of 10 related its index
				prefix += digit * Math.pow(10, j);
			}
			return prefix; // return prefix
		}
	}
}
