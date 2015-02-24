package src;

//creates the GUI
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.Button;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Label;
import java.awt.List;
import java.awt.TextField;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

//assignment 3
//cet 350 java
//Laurel Kris Sean Group 3
//MIL1484, FIE4795, CUR3040

class Program3 extends Frame implements ActionListener, WindowListener {
	
	private static final long serialVersionUID = 8881457993993365761L;
	
	//creates the grid lines
	GridBagLayout gbl;
	GridBagConstraints gbc;
	
	//creates buttons on GUI
	Button okButt;
	Button tarSelect;

	List fileListing;

	//creates labels on GUI
	Label src;
	Label srcLabel;
	Label mesg;
	Label targetBase;
	Label fileLabel;
	Label targetName;

	TextField fileNameField;
	
	//creates files for file structure
	File curDir;
	File curTarget;
	File desTarget;

	boolean targeted = false;

	public static void main(String[] args) {
		File defFile = null;
		Program3 wind = new Program3(".");

		wind.setVisible(true);

		// file handling with arguments
		if (args.length >= 1) {
			defFile = new File(args[0]);
		} else {
			// default to cur Dir
			defFile = new File( ".").getParentFile();
		}

		if (defFile.isDirectory()) {
			new Program3(defFile.getAbsolutePath());
		} else {
			new Program3(System.getenv("user.dir"));
		}
	}
	
	public Program3( String path){
		//creates GUI options with their readings
		okButt = new Button("OK");
		tarSelect = new Button("TARGET");
		fileListing = new List();
		src = new Label("SOURCE:");
		mesg = new Label("");
		targetBase = new Label("COPY TO:");
		fileNameField = new TextField();
		fileLabel = new Label("File Name: ");
		srcLabel = new Label("Source: ");

		//sets the current directory
		curDir = new File( path );
		gbl = new GridBagLayout();

		gbc = new GridBagConstraints();

		this.addWindowListener(this);
		this.setResizable(true);
		this.setLayout(gbl);

		fileListing = new List();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 3;
		gbc.ipady = 200;
		gbc.ipadx = 400;
		this.add(fileListing, gbc);
		fileListing.add("..");
		for (int i = 0; i < curDir.listFiles().length; i++) {
			fileListing.add(curDir.listFiles()[i].getName());
		}

		gbc.ipady = 0;
		gbc.ipadx = 0;
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		this.add(srcLabel, gbc);

		gbc.gridx = 1;
		gbc.gridy = 1;
		gbc.gridwidth = gbc.REMAINDER;
		this.add(src, gbc);

		gbc.gridwidth = 1;
		gbc.gridx = 0;
		gbc.gridy = 2;
		this.add(tarSelect, gbc);

		gbc.gridx = 1;
		gbc.gridy = 2;
		gbc.gridwidth = gbc.REMAINDER;
		this.add(targetBase, gbc);

		gbc.gridwidth = 1;
		gbc.gridx = 0;
		gbc.gridy = 3;
		this.add(fileLabel, gbc);

		gbc.gridx = 1;
		gbc.gridy = 3;
		gbc.ipadx = 100;
		this.add(fileNameField, gbc);

		gbc.ipadx = 0;
		gbc.gridx = 2;
		gbc.gridy = 3;
		this.add(okButt, gbc);

		gbc.gridx = 0;
		gbc.gridy = 4;
		this.add(mesg, gbc);

		this.pack();

		this.setTitle(curDir.getAbsolutePath());
		// listen
		tarSelect.addActionListener(this);
		okButt.addActionListener(this);
		fileListing.addActionListener(this);
	}

	/*
	 * public void display(String name){ String[] filenames; int count;
	 * 
	 * if(name != null){ //go up a directory if(name.equals ".."){ //get parent
	 * curDir = new File(curDir.getParent()); }else{ //new file File f = new
	 * File(curDir, name);}
	 * 
	 * //if new file is a directory? if(f.isDirectory()){ curDirectory = new
	 * File(curDir, name) //its a file }else if(){ //is it a source? //is it a
	 * target? } }else{ filenames = curDir.list(); }
	 * 
	 * //filename = null? if(filenames = null){ filenames = new String[]; }else{
	 * //update title bar //step through each entry of the filenames }
	 * 
	 * if(current item is a directory){ //get new string array of the directory
	 * list //check each item child for a directory }
	 * 
	 * // if(directory original get a "+"){ //after going through the entire
	 * list list.removeAll(); }
	 * 
	 * //if curDir parent is null if(curDir.getParent != null){ list.add("..");
	 * //copy filenames to this list } }//display
	 */

	@Override
	public void windowActivated(WindowEvent e) {
	}

	@Override
	public void windowClosed(WindowEvent e) {
	}

	@Override
	public void windowClosing(WindowEvent e) {
		System.exit(0);
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
	}

	@Override
	public void windowIconified(WindowEvent e) {
	}

	@Override
	public void windowOpened(WindowEvent e) {
	}

	public void listingAction() {
		if (fileListing.getSelectedItem().equals("..")) {
			File t = curDir.getAbsoluteFile().getParentFile();
			
			if (t == null) {
				mesg.setText("Top of Hiearchy");
				return;
			} else {
				System.out.println( t.getAbsolutePath());
				fileListing.removeAll();
				fileListing.add("..");
			}
			
			curDir = t.getAbsoluteFile();
			
			for (int i = 0; i < curDir.listFiles().length; i++) {
				fileListing.add(curDir.listFiles()[i].getName());
			}
		} else {
			File t = new File(curDir.getAbsolutePath()
					+ fileListing.getSelectedItem());
			if (t == null) {
				mesg.setText("Bad Entry");
			} else if (t.isDirectory()) {
				curDir = t;

				for (int i = 0; i < curDir.listFiles().length; i++) {

					fileListing.add(curDir.listFiles()[i].getName());
				}
				this.setTitle(curDir.getAbsolutePath());
			} else {
				//target toggle
				if (!targeted) {
					src.setText(t.getAbsolutePath());
				} else {
					targetBase.setText(t.getAbsolutePath());
				}
			}
		}
//		String[] files = curDir.listFiles();
//		if(files == null) 									// if no files in the directory
//			files = new String[1];		
//		else { 	
//			this.setTitle(curDir.getAbsolutePath()); 					// set the title
//					
//			for(int i = 0; i < files.length; i++) {					//look for a directory and add "+"
//				if(hasDirectory(files[i]))
//					files[i]+="+";
//			}
//		}	
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// clear messages
		mesg.setText("");

		if (e.getSource().equals(okButt)) {
			//do move file here
			//set label to targetBase
		targetBase.setText(curDir.getAbsolutePath()+fileListing.getSelectedItem());
			//will copy if conditions are met
		if(checkCopy())
			//copy file
				copy();
			targeted = false;

		} else if (e.getSource().equals(fileListing)) {
			listingAction();
		} else if (e.getSource().equals(tarSelect)) {
			File t = new File(curDir.getAbsolutePath()
					+ fileListing.getSelectedItem());
			//current directory is placed in the target label
			//if target button is selected
			src.setText(curDir.getAbsolutePath() + fileListing.getSelectedItem());
			 
			if (t == null || t.isDirectory()) {
				// do something for no file/directory
				mesg.setText("Not a valid target");
			} else {
				src.setText(curDir.getAbsolutePath() + fileListing.getSelectedItem());
				targeted = true;
			}
		} else {
			mesg.setText("Unknown Event Occured");
		}
		//adjust
		this.pack();
	}


//copy files
public void copy()
{
	String buff = null;
	BufferedReader in = null;
			PrintWriter out = null;
	try {
		in = new BufferedReader(new FileReader(src.getText()));
	} catch (FileNotFoundException e1) {}
	
	try {
		out = new PrintWriter(new FileWriter(fileNameField.getText()));
	} catch (IOException e1) {}
	
	boolean good = false;
			
			while(!good){
				try {
					buff = in.readLine();
				} catch (IOException e1) {}
				
				if(buff != null) 
					out.println(buff);
				else
					good = true;
			}
	try {
		in.close();
	} catch (IOException e1) {}
	out.close();	
	mesg.setText("Copied: "+ src.getText()+"  to: /"+ targetBase.getText()+fileNameField.getText());
	//call clear to reset our textbox and label
	clear(); 
}

public boolean checkCopy()		//checks to make sure it is good to copy
{
	if(src.getText().length()>0) {
		if(targeted) {
			if(fileNameField.getText().length()>0) {
				File file = new File(fileNameField.getText());
				if(file.exists())
					return true;
				else
					mesg.setText("'"+fileNameField.getText()+"' was not found in current directory.");
			}
			else
				mesg.setText("Select target file");
		}
		else
			mesg.setText("Select target Directory.");
	}
	else
		mesg.setText("Select source file.");
	return false;
}

//public boolean hasDirectory(String parent)      // to check for directory for "+"
//{
//	File parentFile = new File(curDir, parent);
//	if(parentFile.isDirectory()) {
//		String[] directory = parentFile.list();
//		for(String child : directory) {	
//				File childFile = new File(parentFile,child);
//				if(childFile.isDirectory()) 
//					return true;	
//		}
//	}
//	return false;
//}


//clear text box and label
public void clear()
{
	//clear
	src.setText("");
	fileNameField.setText("");
	targetBase.setText("");
}//end clear function
}
