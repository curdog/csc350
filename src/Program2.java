import java.io.*;
import java.util.StringTokenizer;
//easier to count words
import java.util.HashMap;

class Program2 {
	public static void main(String[] args) {
		BufferedReader kbd = new BufferedReader(
				new InputStreamReader(System.in));

		int i, sum, wordQuant;
		sum = 0;
		boolean found = false;
		boolean fail = false;

		FileInputStream inf = null;
		FileOutputStream ouf = null;

		// input filename
		String input = "input";

		// output filename
		String output = "output";

		// declare inbuffer
		String inbuffer = "";
		// word dictionary
		Hashtable<Integer, String> dictionary;
		
		
		// get filename
		do {
			// no arguments were entered in the command line
			if (args.length == 0) {
				// prompt for file
				System.out.println("Enter an input file:");
				input = kbd.readLine();

				// prompt for output file
				System.out.println("Enter an output file:");
				output = "output";
				output = kbd.readLine();

				// an input file was entered in the command line
			} else if (args.length == 1) {
				// prompt for file
				System.out.println("Enter an output file:");
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
				ouf = new FileOutputStream(output);
				fail = false;
			} catch (FileNotFoundException e) {
				// file open fail
				System.out.println("Cannot open file(s)");
				fail = true;
			}
		} while (!fail);
		//files should be good here, will be easier to handle using these
		BufferedReader infread = new BufferedReader( new InputStreamReader( inf ) );
		BufferedWriter oufwrite = new BufferedWriter( new OutputStreamWriter( ouf ));
		
		// read file until EOF into inbuffer
		String temp = "";
		try {
			do {
				// stupid to re-add the \n but easier to read 
				temp = infread.readLine();
				inbuffer = inbuffer.concat("\n" + temp);
			} while (temp != null);

		} catch (IOException e) {
			System.out.println("Error reading file");
		}


		// ------------GETTING THE TOKENS------------
		StringTokenizer inline = new StringTokenizer(inbuffer, " \t.,\n\r;");
			// while there are token
			while (inline.hasMoreTokens()) {
				// get token
				String token1 = inline.nextToken();

				// test string for integer
				if (Integer.) {
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

			// close input file
			try {
				inf.close();
			} catch (IOException e) {
				System.out.println("Error closing file");
			}

			
	} // end public
}// end class

