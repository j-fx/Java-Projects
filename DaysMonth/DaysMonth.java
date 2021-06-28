import java.util.Scanner; // import Scanner class to take input

import java.time.YearMonth; // import YearMonth class to get length of month

public class DaysMonth {
  public static void main(String[] args) {
    int month = 0; // declare and initialize variable month
    int year = -1; // declare and initialize variable year

    Scanner input = new Scanner(System.in); // create object input

    // repeat until correct month input
    while (month < 1 || month > 12 ){
      // message to console asking for input
      System.out.print("Please, enter the month. (For example: 2): ");
      month = input.nextInt(); // take input
    }


    // repeat until get input between -1 and 3001
    while (year < 1 || year > 3000){
      // message to console asking for input
      System.out.print("Please, enter the year. (For example: 2012): ");
      year = input.nextInt(); // take input
    }

    //close input
    input.close();

    // create object yearMonthObjec with year and month as arguments
    YearMonth yearMonthObject = YearMonth.of(year, month);

    // variable input takes lengthOfMonth in object yearMonthObject
    int daysInMonth = yearMonthObject.lengthOfMonth();


    String monthString;
    switch (month) {
      case 1:  monthString = "January";       break;
      case 2:  monthString = "February";      break;
      case 3:  monthString = "March";         break;
      case 4:  monthString = "April";         break;
      case 5:  monthString = "May";           break;
      case 6:  monthString = "June";          break;
      case 7:  monthString = "July";          break;
      case 8:  monthString = "August";        break;
      case 9:  monthString = "September";     break;
      case 10: monthString = "October";       break;
      case 11: monthString = "November";      break;
      case 12: monthString = "December";      break;
      default: monthString = "Invalid month"; break;
    }

    // display different message depends on the year
    if (year < 2020){
      // display message to console. (For example: February 2012 had 29 days.)
      System.out.println(monthString + " " + year + " had " + daysInMonth + " days.");
    } else if (year == 2020){
      // display message to console. (For example: February 2020 have 29 days.)
      System.out.println(monthString + " " + year + " have " + daysInMonth + " days.");
    } else {
      // display message to console. (For example: February 2021 will have 28 days.)
      System.out.println(monthString + " " + year + " will have " + daysInMonth + " days.");
    }
  }
}
