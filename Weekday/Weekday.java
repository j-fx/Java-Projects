import java.util.Scanner; // import Scanner class to take input

public class Weekday {
	public static void main(String[] args) {
		Scanner input = new Scanner(System.in); // create object input

		// declare variables
		int year, month, dayOfTheMonth;

		// initialize variables
		year = month = dayOfTheMonth = 0;

		while (true) {// repeat until storing correct year input
			// message to console asking for input
			System.out.print("Enter year: (e.g., 2015): ");
			if (input.hasNextInt()) { // if input identifies next integer
				year = input.nextInt(); // store integer value into year
				if (year >= 0) // if A.D. year
					break; // break while loop
				else // display message to console
					System.out.println("Year has to be A.D., please try again.\n");
			} else { // else, display error message to console
				System.out.println("Wrong input, please try again.\n");
				input.next(); // read next input
			}
		}

		while (true) {// repeat until storing correct month input
			// message to console asking for input
			System.out.print("Enter month: 1-12: ");
			if (input.hasNextInt()) { // if input identifies next integer
				month = input.nextInt(); // store integer value into month
				if (month > 0 && month < 13) // if month between 0 and 13
					break; // break while loop
				else // display message to console
					System.out.println("Wrong input, please try again.\n");
			} else { // else, display error message to console
				System.out.println("Wrong input, please try again.\n");
				input.next(); // read next input
			}
		}

		while (true) {// repeat until storing correct dayOfTheMounth input
			// message to console asking for input
			System.out.print("Enter the day of the month: 1-31: ");
			if (input.hasNextInt()) { // if input identifies next integer
				dayOfTheMonth = input.nextInt(); // store integer value into dayOfTheMonth
				if (dayOfTheMonth > 0 && dayOfTheMonth < 32) // if month between 0 and 32
					break; // break while loop
				else // display message to console
					System.out.println("Wrong input, please try again.\n");
			} else { // else, display error message to console
				System.out.println("Wrong input, please try again.\n");
				input.next(); // read next input
			}
		}

		input.close(); // close input

		// January and February are counted as months 13 and 14 of the previous year.
		if (month == 1) { // if January
			month = 13; // update month
			year--; // update year
		} else if (month == 2) { // else, if February
			month = 14; // update month
			year--; // update year
		}

		int century = year / 100; // declare and initialize century with year / 100

		// declare and initialize yearOfTheCentyry with year % 100
		int yearOfTheCentury = year % 100;

		// declare and create array of String with the days of the week
		String[] strDays = new String[] { "Saturday", "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday",
				"Friday" };

		// follow the Christian Zeller formula to calculate the dayOfTheWeek
		int dayOfTheWeek = ( // h =
		dayOfTheMonth + // q +
				(26 * (month + 1) / 10) + // (26( m+1) / 10) +
				yearOfTheCentury + // k +
				(yearOfTheCentury / 4) + // (k/4) +
				(century / 4) + // (j/4) +
				(5 * century) // (5j)
		) % 7; // module 7

		// display message to console
		System.out.println("Day of the week is " + strDays[dayOfTheWeek]);

	}

}
