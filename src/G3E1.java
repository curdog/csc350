//package src;

/******************************
 * Take Home Group Exam 1
 * Technical Computing Using Java CET 350
 * Group: 3
 * Laurel Kris Sean
 * mil1484@calu.edu fie4795@calu.edu cur3040@calu.edu
 *****************************/
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/******Exit Codes********
 *  1- General Error
 *  2- System file error
 *  3- User Idiot error
 */

public class G3E1 {

	public static void main(String[] args) {

		String filenamei = null;
		String filenameo = null;

		BufferedReader stdin = new BufferedReader(new InputStreamReader(
				System.in));
		BufferedWriter fout = null;
		try {
			if (args.length == 0) {

				//prompt for the input file
				System.out.print("Enter Input Filename:  ");
				filenamei = stdin.readLine();

				//prompt for the output file
				System.out.print("Enter Output Filename:  ");
				filenameo = stdin.readLine();

				//if args = 1
			} else if (args.length == 1) {
				filenamei = args[0];

				//prompt for output
				System.out.print("Enter Output Filename:  ");
				filenameo = stdin.readLine();

			//both arguments were given
			} else {
				filenamei = args[0];
				filenameo = args[1];
			}

		} catch (IOException e) {
			//error
			System.err.println("Input not recognized");
			System.err.println(e.toString());
			System.exit(3);
		}

		BufferedReader fin = null;
		//check not same file
		if(filenamei.equals(filenameo)){
			//error, same in/out file
			System.err.println("You can't have the in and out files be the same");
			//exit
			System.exit(3);
		}
		
		try {
			//declarations
			File input = new File(filenamei);
			File output = new File(filenameo);
			fin = new BufferedReader(new FileReader(input));
			fout = new BufferedWriter(new FileWriter(output));
			
				//check if the file exists
				if(input.exists())
				{
					//file does exist
					System.out.println("\nThis file does exist\n");
				}else{
						//file does not exist, error, exit
						System.out.println("This file does not exist\n");
						System.out.println("Good-bye");
						//exit
						System.exit(1);
				}//end if
					
				//check if output exists
					if(output.exists())
					{
						//file exists, overwrite
						System.out.println("The out file exist\n");
						System.out.println("OVERWRITING.....\n");
					}else{
						//outfile does not exist, exit
						System.out.println("The out file does not exist\n");
						System.out.println("Good-bye");
						//exit
						System.exit(1);
					}//end if
					
		} catch (IOException e) {

			//error, file not found
			System.err.println("File Not Found");
			System.err.println(e.toString());
			//exit
			System.exit(2);
		}
		//declarations
		int ticks = 0;
		int firstmilage = 0;
		float feulUsed = 0.0f;
		float sumavg = 0.0f;
		String line = null;
		int lastmil = 0;
		float totalmileage = 0.0f;
		int mil = 0;
		
		try {
			while ((line = fin.readLine()) != null) {
				
				float con = 0.0f;
				StringTokenizer linetok = new StringTokenizer(line);

				if (linetok.countTokens() < 2) {
					System.err.println("Data format error skipping line");
					continue;
				}

				//convert to an integer
				mil = Integer.parseInt(linetok.nextToken());
				if (firstmilage == 0) {
					firstmilage = mil;
				}
				//convert to a float
				con = Float.parseFloat(linetok.nextToken());
				feulUsed += con;
				if (lastmil != 0) {
					float t = (float) (mil - lastmil) / con;
					//printing & writing the inst MPG
					System.out.println("Inst MPG: " + t);
					fout.write("Inst MPG: " + t + "\n");
					sumavg += t;
					//printing & writing the avg MPG
					System.out.println("Avg MPG: "
							+ (float) ( sumavg / ticks));
					fout.write("Avg MPG: "
							+ (float) ( sumavg / ticks)+"\n");
					totalmileage = mil - lastmil + totalmileage;
				}
				lastmil = mil;
				//number of entries
				ticks++;
				//accumulating the total mileage
					

			}
			
			System.out.println("\n--------------------TOTALS--------------------\n");
			fout.write("\n--------------------TOTALS--------------------" + "\n" + "\n");
			//printing the total mileage to the screen
			System.out.println("Total Distance Driven: " + (lastmil - firstmilage) + " miles" );
			//writing the total mileage to the out file
			fout.write("Total Distance Driven: " + (lastmil - firstmilage)+ " miles" +"\n");
			
			//each entry is 5 minute intervals, mult ticks by 5
			System.out.println("Total Time Driven: " + (ticks * 5) + " minutes");
			fout.write("Total Time Driven: " + (ticks * 5) + " minutes" + "\n");
			
			//printing & writing total fuel used
			System.out.println("Total Fuel Used: " + feulUsed);
			fout.write("Total Fuel Used: " + feulUsed + "\n");
			
			//total average MPG from all the data
			//total mileage / total fuel used
			System.out.println("Average MPG for all data: " + totalmileage/feulUsed);
			fout.write("Average MPG for all data: " + totalmileage/feulUsed);
			
			
		} catch (IOException e) {
			//error
			System.err.println("IO Error");
			System.err.println(e.toString());
			System.exit(3);
		}

		try {
			//closing the files
			fin.close();
			fout.close();
		} catch (IOException e) {
			//error
			System.err.println("File Error");
			System.err.println(e.toString());
			System.exit(2);
		}

	}

}
