//Assignment 1
//Sean Curtis

import java.util.*;

class Program1 {
	
	private static double grades_sum = 0.0;
	private static int num_entries = 0;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("Average(again) A Getting Started Program");
		System.out.println("Grades below 0.0 will quit");
		
		Scanner input = new Scanner( System.in );
		
		double last = 0.0;
		
		while ( last >= 0.0 ){
			System.out.print("Enter grade: " );
			
			try {
				last = input.nextDouble();
			} catch ( InputMismatchException e ) {
				System.out.println("You typed anything but a number :-(");
				last = 0.0;
				continue;
			}
			
			grades_sum += last;
			num_entries++;
		}
		
		System.out.println("Totals:");
		System.out.println("Grades Entered: " + num_entries);
		System.out.println("Grade Sum: " + grades_sum );
		last = grades_sum / (double)num_entries;
		
		if( last == Double.NEGATIVE_INFINITY ){
			System.out.println("You have failed to the maxium degree");
		} else if ( last == Double.NaN ){
			System.out.println("I don't know what your grade ended up being, but its not of this world");
		} else if ( last == Double.POSITIVE_INFINITY ){
			System.out.println("You really know everything, don't you?");
		} else {
			System.out.println("Your grade is: " + last);
		}
		
		
	}

}
