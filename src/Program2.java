package src;

//Java Program 2
//Laurel, Sean, Kris
//Group 3

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.*;

 class Program2
 {
	public static void main(String[] args)
	{
	BufferedReader kbd = new BufferedReader(new InputStreamReader(System.in));
	
	//declarations
	Word w = new Word();
	Word k = new Word();
	int i;
	boolean fail = false;
	FileInputStream inf = null;
	Word[] words = new Word[100];
	String[] ionames = new String[2];
	
	String filename = "filename";
	//get filename
	do {
		//String filename;
		if(args.length != 1 || fail )
		{
			//prompt for file
			System.out.println("Enter Input file:");
			filename = "filename";
			
		} else {
			args[0] = filename;
		}
		
		//attempt to open file
		try
		{
			inf = new FileInputStream( filename);
			fail = false;
		} catch(FileNotFoundException e) {
			//file open fail
			System.out.println("Cannot open file");
			fail = true;
					
		}
	}while( ! fail);	
	//read file until EOF 
		try{
		do{
			i = inf.read();
			if(i!= -1) System.out.print((char) i);
		  }
		while( i != -1);
			} catch(IOException e){
				System.out.println("Error reading file");
				}
				
	//close file
	try{
		inf.close();
		} catch(IOException e){
			System.out.println("Error closing file");
			}
				
	} //end public
}//end class


class Word{
	public Word(){
	
	}
	
	public void addWord( String s ){
	
	}

}
