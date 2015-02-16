package src;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class G3E1 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String filename = null;
		BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
		if ( args.length == 0){
			System.out.print("Enter Filename:  ");
			try {
				filename = stdin.readLine();
			} catch (IOException e){
				System.err.println("Input not recognized");
				System.err.println( e.toString() );
				System.exit(1);
			}
			
		} else {
			filename = args[0];
		}
		
		BufferedReader fin = null;
		
		try {
			fin = new BufferedReader(new FileReader( new File(filename)));
		} catch (FileNotFoundException e) {
			
			System.err.println("");
			System.err.println(e.toString());
			System.exit(2);
		}
		
	}

}
