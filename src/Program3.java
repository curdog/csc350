//package src;

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
	
	GridBagLayout gbl;
	GridBagConstraints gbc;
	Button ok;
	Button srcSelect;
	List fileListing;
	
	Label src;
	Label mesg;
	Label targetBase;
	
	TextField targetName;
	
	File curDir;
	public static void main(String[] args) {
		File defFile = null;
		Program3 wind = new Program3(".");
		
		wind.setVisible( true);
		
		//file handling with arguments
		if(args.length >= 1){
			defFile = new File(args[0]);
		} else {
			//prompt for the directory
			defFile= new File(".");
			
		}
		
		if(defFile.isDirectory()){
			new Program3( defFile.getAbsolutePath() );
		} else {
			new Program3(".");
		}
	}
	
	public Program3( String path){
		ok = new Button("ok");
		srcSelect = new Button("sel");
		fileListing = new List();
		src=new Label("source");
		mesg=new Label("message");
		targetBase=new Label("base");
		
		
		curDir = new File( path );
		gbl = new GridBagLayout();
		
		gbc = new GridBagConstraints();
		
		this.addWindowListener( this );
		this.setResizable(true);
		this.setLayout(gbl);
		
		fileListing = new List();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth =2;
		gbc.ipady = 200;
		this.add( fileListing, gbc );
		
		for( int i = 0; i < curDir.listFiles().length; i++ ){
			fileListing.add( curDir.listFiles()[i].getName() );
		}
		
		gbc.ipady=0;
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		this.add( srcSelect, gbc );
		
		gbc.gridx = 1;
		gbc.gridy = 1;
		this.add( src, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 2;
		this.add( ok, gbc );
		
		gbc.gridx = 1;
		gbc.gridy = 2;
		this.add(targetBase);
		this.pack();
		
		//listen
		srcSelect.addActionListener(this);
		ok.addActionListener(this);
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
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	
}
