/* File: DateFormatApp.java - July 2014 */
import java.util.*;
import java.util.regex.*;

/**
 * Reads a stream of dates from standard input. If a line is in a recognised
 * format and is a valid date then it is reformatted and printed, otherwise the
 * line and a relevant error message is printed.
 *
 * A line is in a recognised format 'day <sep> month <sep> year' if:
 *    1. 'day' is a one or two digit integer,
 *    2. 'month' is a one or two digit integer or the first three letters of a
 *       month in lower (e.g. jan), upper (e.g. JAN), or title case (e.g. Jan),
 *    3. 'year' is a two or four digit integer, which stands for a year between
 *       1950 and 2049 if the former is true, and
 *    4. '<sep>' is a single space, a hyphen, or a slash.
 *
 * A date is valid if:
 *    1. it is between the years 1753 and 3000, and 
 *    2. the month and day are valid (noting leap years).
 *
 * @author Andrew Ashton
 */
public class DateFormatApp {
  
   /**
    * Reads from standard input and sends each individual line to be handled.
    *
    * @param args not used
    */
   public static void main(String[] args) {
      Scanner input = new Scanner(System.in);   
      while (input.hasNextLine()) {
         handleLine(input.nextLine());
      }
   }
   
   /**
    * If the given line matches the regular expression (which specifies the
    * recognised formats) then it is sent to be handled as a date, otherwise an 
    * error message is printed.
    *
    * @param line the line to be handled
    */
   private static void handleLine(String line) {
      /* Each group in the following regular expression is contained in
         brackets, with '\\2' referring to the second group. */
      final Pattern REG_EX = Pattern.compile("([0-9][0-9]?)" + "([ /-])"    
         + "([0-9][0-9]?|[a-z]{3}|[A-Z]{3}|[A-Z][a-z]{2})" + "\\2"
         + "([0-9]{2}|[0-9]{4})");
      Matcher m = REG_EX.matcher(line);
      
      if (m.matches()) {
         handleDate(line, m);
      } else {
         System.err.println(line + " - Format not recognised.");
      }      
   }
   
   /**
    * Converts the 'day', 'month', and 'year' of the date into integer values,
    * and then prints the date in the desired format 
    *
    *    'dd <space> Mon <space> yyyy'
    * 
    * if they are valid, otherwise an error message is printed.
    *
    * @param date the date to be handled
    * @param m the matcher of the line to the regular expression
    */
   private static void handleDate(String date, Matcher m) {
      String day = m.group(1), // The first group matched in 'REG_EX'.
         month = m.group(3), 
         year = m.group(4);
         
      int intDay = dayToInt(day);
      int intMonth = monthToInt(month);
      int intYear = yearToInt(year);
      
      if (validDate(intDay, intMonth, intYear)) {
         System.out.println(formatDay(intDay) + " " + formatMonth(intMonth) +
            " " + formatYear(intYear));
      } else {
         System.err.println(date + " - Date not valid.");
      }
   }
   
   /**
    * Returns the day as an integer value.
    *
    * @param day the day to be returned as an integer
    * @return the day as an integer
    */
   private static int dayToInt(String day) {
      return Integer.parseInt(day);
   }
   
   /**
    * Returns the month as an integer value. 
    *
    * @param month the month to be returned as an integer
    * @return the month as an integer
    */
   private static int monthToInt(String month) {
      Scanner input = new Scanner(month);
      if (input.hasNextInt()) { // If 'month' is an integer.
         return Integer.parseInt(month);
      }
      String[] monthArray = {"jan", "feb", "mar", "apr", "may", "jun", "jul", 
         "aug", "sep", "oct", "nov", "dec"};
      month = month.toLowerCase();
      /* 0 is returned if the month does not appear in the above array. */
      return  Arrays.asList(monthArray).indexOf(month) + 1;
   }
   
   /**
    * Returns the year as an integer value.
    *
    * @param year the year to be returned as an integer
    * @return the year as an integer
    */
   private static int yearToInt(String year) {
      int intYear = Integer.parseInt(year);
      if (intYear < 50) {
         return 2000 + intYear;
      }
      if (intYear < 100) {
         return 1900 + intYear;
      }
      return intYear;
   }
   
   /**
    * Returns 'true' if the day, month, and year (given as integers) correspond
    * to a valid date.
    *
    * @param day the day as an integer
    * @param month the month as an integer
    * @param year the year as an integer
    * @return true if the day, month, and year correspond to a valid date
    */
   private static boolean validDate(int day, int month, int year) {
      if (year < 1753 || 3000 < year || month < 1 || 12 < month ||
         day < 1 || 31 < day) {
         return false;
      }
      if ((month == 9 || month == 4 || month == 6 || month == 11) && day > 30) {
         return false;
      }
      if (month == 2 && day > 29) {
         return false;
      }
      if (month == 2 && day > 28 && !isLeapYear(year)) {
         return false;
      }
      return true;
   }
   
   /**
    * Returns 'true' if the given year is a leap year.
    *
    * @param year the year to be checked if it is a leap year
    * @return true if the year is a leap year
    */
   private static boolean isLeapYear(int year) {
      return year % 4 == 0 && !(year % 100 == 0 && year % 400 != 0);
   }
   
   /**
    * Returns the day, given as an integer, as a 'dd' string, appending a 
    * leading zero if necessary.
    *
    * @param day the day, as an integer, to formatted
    * @return the day as a 'dd' string
    */
   private static String formatDay(int day) {
      return day < 10 ? "0" + day : Integer.toString(day);
   }
   
   /**
    * Returns the month, given as an integer, as a 'Mon' string.
    *
    * @param month the month, as an integer, to be formatted
    * @return the month as an 'Mon' string
    */
   private static String formatMonth(int month) {
      String[] monthArray = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", 
         "Aug", "Sep", "Oct", "Nov", "Dec"};
      return monthArray[month - 1];
   }
   
   /**
    * Returns the year, given as an integer, as a 'yyyy' string.
    *
    * @param year the year, as an integer, to be formatted
    * @return the year as a 'yyyy' string
    */
   private static String formatYear(int year) {
      return Integer.toString(year);
   }
 
}
