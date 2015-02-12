package src;

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
//Laurel Cris Sean Group 3
//

class Main extends Frame implements ActionListener, WindowListener {
	
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
		Main wind = new Main();
		wind.setVisible( true);
		

	}
	
	public Main(){
		
		curDir = new File( "." );
		gbl = new GridBagLayout();
		gbc = new GridBagConstraints();
		
		this.addWindowListener( this );
		this.setResizable(false);
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
		this.add(targetBase);
		
		gbc.gridx = 2;
		this.add(targetName);
	}

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
		this.setState(Frame.NORMAL);
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
