import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.*;
import java.util.StringTokenizer;
//easier to count words
import java.util.HashMap;

class Program2 {
	public static void main(String[] args) {
		BufferedReader kbd = new BufferedReader(
				new InputStreamReader(System.in));

		// declarations
		// Word w = new Word();
		// Word k = new Word();
		int i, sum, wordQuant;
		sum = 0;
		boolean found = false;
		boolean fail = false;
		FileInputStream inf = null;
		FileOutputStream ouf = null;

		String[] ionames = new String[2];

		// input filename
		String input = "input";

		// output filename
		String output = "output";

		// declare inbuffer array
		String inbuffer = "";

		// get filename
		do {
			// no arguments were entered in the command line
			if (args.length == 0) {
				// prompt for file
				System.out.println("Enter an input file:");
				input = "input";
				input = kbd.readLine();

				// prompt for output file
				System.out.println("Enter an output file:");
				output = "output";
				output = kbd.readLine();

				// an input file was entered in the command line
			} else if (args.length == 1) {
				// prompt for file
				System.out.println("Enter an output file:");
				output = "output";
				output = kbd.readLine();

				// both an input and output file have been entered
			} else if (args.length == 2) {
				input = args[0];
				output = args[1];
				System.out.println("Your input file: " + input);
				System.out.println("Your output file: " + output);
			}

			// attempt to open file
			try {
				inf = new FileInputStream(input);
				fail = false;
			} catch (FileNotFoundException e) {
				// file open fail
				System.out.println("Cannot open file");
				fail = true;
			}
		} while (!fail);

		// read file until EOF
		try {
			do {
				i = inf.read();
				if (i != -1)
					System.out.print((char) i);

			} while (i != -1);
		} catch (IOException e) {
			System.out.println("Error reading file");
		}

		// close file
		try {
			inf.close();
		} catch (IOException e) {
			System.out.println("Error closing file");
		}

		// ------------GETTING THE TOKENS------------
		inbuffer = input.readLine();
		StringTokenizer inline = new StringTokenizer(inbuffer, " \t.,\n\r");
		while (inbuffer != null) {
			// parse inbuffer into tokens
			// input delimiters

			// while there are token
			while (inline.hasMoreTokens()) {
				// get token
				String token1 = inline.nextToken();

				// test string for integer
				if (Character.isDigit(token1)) {
					// convert string token1 to numeric digit
					int digit = Integer.valueOf(token1);
					// add digit to the accumulating sum
					sum = sum + digit;
				} else {

					// search for the word in the buffer
					int x;
					for (x = 0; x < inbuffer.length; x++) {
						if (inbuffer[x] == token1) {
							found = true;
							break;
						} // end if
					} // end for

					// found = false the word was not found
					// found = true the word was found
					// check if the word exists
					if (found) {
						// add 1
						wordQuant++;
					} else {
						System.out.println(token1 + " was not found in");
						System.out.println("It has not been created");
						// create Word
						// adjust index
					} // end if
				} // end if

			} // end while
			readLine();
		} // end while

	} // end public
}// end class

