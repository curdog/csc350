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
import java.io.File;

//assignment 3
//csc java
//Laurel Kris Sean Group 3
//

class Program3 extends Frame implements ActionListener, WindowListener {
	
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
		
		wind.setVisible( true);
		
		//file handling with arguments
		if(args.length >= 1){
			defFile = new File(args[0]);
		} else {
			//default to cur Dir
			defFile= new File(".");
			
		}
		
		if(defFile.isDirectory()){
			new Program3( defFile.getAbsolutePath() );
		} else {
			new Program3(".");
		}
	}
	
	public Program3( String path){
		//creates GUI options with their readings
		okButt = new Button("ok");
		tarSelect = new Button("sel");
		fileListing = new List();
		src=new Label("source");
		mesg=new Label("message");
		targetBase=new Label("base");
		fileNameField = new TextField();
		fileLabel = new Label("File Name: ");
		srcLabel = new Label("Source: ");
		
		//sets the current directory
		curDir = new File( path );
		gbl = new GridBagLayout();
		
		gbc = new GridBagConstraints();
		
		this.addWindowListener( this );
		this.setResizable(true);
		this.setLayout(gbl);
		
		fileListing = new List();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth =3;
		gbc.ipady = 200;
		gbc.ipadx = 400;
		this.add( fileListing, gbc );
		fileListing.add("..");
		for( int i = 0; i < curDir.listFiles().length; i++ ){
			fileListing.add( curDir.listFiles()[i].getName() );
		}
		
		gbc.ipady = 0;
		gbc.ipadx = 0;
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		this.add( srcLabel, gbc );
		
		gbc.gridx = 1;
		gbc.gridy = 1;
		this.add( src, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 2;
		this.add( tarSelect, gbc );
		
		gbc.gridx = 1;
		gbc.gridy = 2;
		this.add(targetBase, gbc);
		
		gbc.gridx = 0;
		gbc.gridy =3;
		this.add(fileLabel, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 3;
		this.add(fileNameField, gbc);
		
		gbc.gridx = 2; 
		gbc.gridy = 3;
		this.add(okButt, gbc);

		
		gbc.gridx = 0;
		gbc.gridy = 4;
		this.add( mesg, gbc);
		
		this.pack();
		
		//listen
		tarSelect.addActionListener(this);
		okButt.addActionListener(this);
		fileListing.addActionListener(this);
	}

	/*	
	public void display(String name){
		String[] filenames;
		int count;
		
		if(name != null){
			//go up a directory 
			if(name.equals ".."){
				//get parent
				curDir = new File(curDir.getParent());
			}else{
			//new file	
			File f = new File(curDir, name);}
			
			//if new file is a directory?
			if(f.isDirectory()){
				curDirectory = new File(curDir, name)
				//its a file
			}else if(){
					//is it a source?
					//is it a target?
			}
		}else{
			filenames = curDir.list();
		}
		
		//filename = null?
		if(filenames = null){
			filenames = new String[];
		}else{
			//update title bar
			//step through each entry of the filenames
		}
		
		if(current item is a directory){
			//get new string array of the directory list
			//check each item child for a directory
		}
		
		//
		if(directory original get a "+"){
			//after going through the entire list
			list.removeAll();
		}
		
		//if curDir parent is null
		if(curDir.getParent != null){
			list.add("..");
			//copy filenames to this list
		}
}//display 
*/

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosing(WindowEvent e) {
		// TODO Auto-generated method stub
		System.exit(0);
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub
		//this.setState(Frame.NORMAL);
	}

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(okButt)){
			
		} else if ( e.getSource().equals(fileListing)){
			if( fileListing.getSelectedItem().equals("..") ){
				File t =  curDir.getAbsoluteFile().getParentFile();
				if ( t == null ){
					mesg.setText("Heirachy Exceeded");
					return;
				}
				curDir = t;
				fileListing.removeAll();
				fileListing.add("..");
				for( int i = 0; i < curDir.listFiles().length; i++ ){
					
					fileListing.add( curDir.listFiles()[i].getName() );
				}
			} else {
				
				
			}
		} else if (e.getSource().equals(tarSelect)){
			
		} else {
			mesg.setText("Unknown Event Occuredd");
		}
	}

	
}
