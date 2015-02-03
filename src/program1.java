package src;

//Assignment 1
//Sean Curtis

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

class Program1 {
	
	private static double grades_sum = 0.0;
	private static int num_entries = 0;

	public static void main(String[] args) {
		//intro message
		System.out.println("Average(again) A Getting Started Program");
		System.out.println("Grades below 0.0 will quit");
		//input
		
		//BufferedReader in2 = new BufferedReader( new InputStreamReader(System.in)  );
		
		BufferedReader input = new BufferedReader( new InputStreamReader(System.in) );
		//temp var
		double last = 0.0;
		//entry loop
		while ( last >= 0.0 || Double.isNaN(last) ){
			System.out.print("Enter grade \"0-100\": " );
			//mandatory idiot check
			try {
				last = Double.parseDouble(input.readLine());
			} catch ( NumberFormatException e ) {
				System.out.println("You typed anything but an acceptable number :-(");
				last = 0.0;
				
				
				continue;
			} catch ( IOException ioe){
				System.out.println("Error in input stream.  Attempting again.");
				
			}
			
			//adjust values
			if( last >= 0.0 || Double.isNaN(last) ){
				grades_sum += last;
				num_entries++;
			}
		}
		//end game screen
		System.out.println("-----Totals------");
		System.out.println("Grades Entered: " + num_entries);
		System.out.println("Grade Sum: " + grades_sum );
		last = grades_sum / (double)num_entries;
		//print average, check for cases
		if( last == Double.NEGATIVE_INFINITY ){
			System.out.println("Your grade is: " + last);
			System.out.println("You have failed to the maxium degree. Congratulations, especially so, because this is not an executable branch.");
		} else if ( Double.isNaN(last) ){
			System.out.println("Your grade is: " + last);
			System.out.println("I don't know what your grade ended up being, but its not a number.");
		} else if ( last == Double.POSITIVE_INFINITY ){
			System.out.println("Your grade is: " + last);
			System.out.println("You really know everything. Don't you?");
		} else {
			System.out.println("Your grade is: " + last);
		}
		
		//close input (not necessary for a RTE but gets rid of yellow squiggly lines)
		input.close();
	}

}
