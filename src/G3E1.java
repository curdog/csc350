package src;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class G3E1 {

	public static void main(String[] args) {

		String filenamei = null;
		String filenameo = null;

		BufferedReader stdin = new BufferedReader(new InputStreamReader(
				System.in));
		BufferedWriter fout = null;
		try {
			if (args.length == 0) {

				System.out.print("Enter Input Filename:  ");
				filenamei = stdin.readLine();

				System.out.print("Enter Output Filename:  ");
				filenameo = stdin.readLine();

			} else if (args.length == 1) {
				filenamei = args[0];

				System.out.print("Enter Output Filename:  ");
				filenameo = stdin.readLine();

			} else {
				filenamei = args[0];
				filenameo = args[1];
			}

		} catch (IOException e) {
			System.err.println("Input not recognized");
			System.err.println(e.toString());
			System.exit(3);
		}

		BufferedReader fin = null;

		try {
			fin = new BufferedReader(new FileReader(new File(filenamei)));
			fout = new BufferedWriter(new FileWriter(new File(filenameo)));
		} catch (IOException e) {

			System.err.println("File Not Found");
			System.err.println(e.toString());
			System.exit(2);
		}
		int ticks = 0;
		int firstmilage = 0;
		float feulUsed = 0.0f;
		float sumavg = 0.0f;
		String line = null;
		int lastmil = 0;

		try {
			while ((line = fin.readLine()) != null) {
				int mil = 0;
				float con = 0.0f;
				StringTokenizer linetok = new StringTokenizer(line);

				if (linetok.countTokens() < 2) {
					System.err.println("Data format error skipping line");
					continue;
				}

				mil = Integer.parseInt(linetok.nextToken());
				if (firstmilage == 0) {
					firstmilage = mil;
				}
				con = Float.parseFloat(linetok.nextToken());
				feulUsed += con;
				if (lastmil != 0) {
					float t = (float) (mil - lastmil) / con;
					System.out.println("Inst MPG: " + t);
					fout.write("Inst MPG: " + t + "\n");

					System.out.println("Avg MPG: "
							+ (float) (mil - firstmilage / feulUsed));
					fout.write("Avg MPG: "
							+ (float) (mil - firstmilage / feulUsed));
				}
				lastmil = mil;
				ticks++;

			}
			
			System.out.println("Total Milage: " + (lastmil - firstmilage) );
			

		} catch (IOException e) {
			System.err.println("IO Error");
			System.err.println(e.toString());
			System.exit(3);
		}

		try {
			fin.close();
			fout.close();
		} catch (IOException e) {
			System.err.println("File Error");
			System.err.println(e.toString());
			System.exit(2);
		}

	}

}
