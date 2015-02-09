import java.io.*;
import java.util.Hashtable;
import java.util.StringTokenizer;

class Program2 {
	public static void main(String[] args) throws IOException {
		BufferedReader kbd = new BufferedReader(
				new InputStreamReader(System.in));

		int sum = 0;

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
<<<<<<< HEAD
		Hashtable<Integer, Word> dictionary = new Hashtable<Integer, Word>();

=======
		Hashtable<Integer, Word> dictionary = new Hashtable<Integer,Word>();
		
		//printwriter tp wrote to the output file
		PrintWriter pw = new PrintWriter(output);
		
>>>>>>> origin/master
		// get filename
		do {
			// no arguments were entered in the command line
			if (args.length == 0) {
				// prompt for file
				System.out.println("Enter an input file:");
				input = kbd.readLine();

				// prompt for output file
				System.out.println("Enter an output file:");

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

			// attempt to open files
			try {
				inf = new FileInputStream(input);
				ouf = new FileOutputStream(output);
				fail = false;
			} catch (FileNotFoundException e) {
				// file open fail
				System.out.println(input);
				System.out.println(output);
				System.out.println(e.toString());
				System.out.println("Cannot open file(s)");
				fail = true;
			}
		} while (fail);
		// files should be good here, will be easier to handle using these
		BufferedReader infread = new BufferedReader(new InputStreamReader(inf));
		BufferedWriter oufwrite = new BufferedWriter(
				new OutputStreamWriter(ouf));

		// read file until EOF into inbuffer
		// we swallow the whole potato instead of chunks
		String temp = "";
		try {
			do {
				// stupid to re-add the \n but readLine removes it. can screw up
				// delimiters
				temp = infread.readLine();
				inbuffer = inbuffer.concat("\n" + temp);
			} while (temp != null);

		} catch (IOException e) {
			System.out.println("Error reading file");
		}

		// ------------GETTING THE TOKENS------------
		StringTokenizer inline = new StringTokenizer(inbuffer, " \t.,\n\r;");
		// while there are token

<<<<<<< HEAD
		while (inline.hasMoreTokens()) {
			// get token
			String token = inline.nextToken();

			// test string for integer
=======
			} // end while
			
			//printing to the output file with PrintWriter(pw)
			pw.println("The sum of integers is: " + sum);
			
			// close input file
>>>>>>> origin/master
			try {
				sum += Integer.parseInt(token);
			} catch (NumberFormatException e) {
				// definately not a number
				// look in dict
				int hash = token.hashCode();
				// have word?
				if (dictionary.containsKey(hash)) {
					dictionary.get(hash).inc();
				}
				// not have word? add it
				else {
					dictionary.put(hash, new Word(token));
				}

			}

		} // end while

		// close input file
		try {
			ouf.close();
			inf.close();
		} catch (IOException e) {
			System.out.println("Error closing files");
		}

		System.out.println(sum);

	} // end public
}// end class

class Word {
	String s;
	int count;

	public Word(String w) {
		s = w;
		count = 0;
	}

	public void inc() {
		count++;
	}

	public String getWord() {
		return s;
	}

	public int hashCode() {
		return s.hashCode();
	}

	public String toString() {
		return s + ": " + count;
	}
}
