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

	// creates the grid lines
	GridBagLayout gbl;
	GridBagConstraints gbc;

	// creates buttons on GUI
	Button okButt;
	Button tarSelect;

	List fileListing;

	// creates labels on GUI
	Label src;
	Label srcLabel;
	Label mesg;
	Label targetBase;
	Label fileLabel;
	Label targetName;

	TextField fileNameField;

	// creates files for file structure
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
			defFile = new File(".").getParentFile();
		}

		if (defFile.isDirectory()) {
			new Program3(defFile.getAbsolutePath());
		} else {
			new Program3(System.getenv("user.dir"));
		}
	}

	public Program3(String path) {
		// creates GUI options with their readings
		okButt = new Button("OK");
		tarSelect = new Button("TARGET");
		fileListing = new List();
		src = new Label("SOURCE:");
		mesg = new Label("");
		targetBase = new Label("COPY TO:");
		fileNameField = new TextField();
		fileLabel = new Label("File Name: ");
		srcLabel = new Label("Source: ");

		// sets the current directory
		curDir = new File(path);
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
		// fileListing.
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
		fileNameField.addActionListener(this);
	}

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
				System.out.println(t.getAbsolutePath());
				fileListing.removeAll();
				fileListing.add("..");
			}

			if (targeted) {
				targetBase.setText(t.getAbsolutePath());
			}
			
			curDir = t.getAbsoluteFile();
			
			this.setTitle(curDir.getAbsolutePath());
			
			for (int i = 0; i < curDir.listFiles().length; i++) {
				if( isDirectory( curDir.listFiles()[i].getName() )){
					fileListing.add(curDir.listFiles()[i].getName() + "+");
				} else {
					fileListing.add(curDir.listFiles()[i].getName());
				}
			}
		} else {
			File t = new File(curDir.getAbsolutePath() + "/"
					+ fileListing.getSelectedItem().replace("+", ""));
			if (t == null) {
				mesg.setText("Bad Entry");
			} else if (t.isDirectory()) {
				curDir = t;
				fileListing.removeAll();
				fileListing.add("..");

				for (int i = 0; i < curDir.listFiles().length; i++) {
					
					if( isDirectory( curDir.listFiles()[i].getName() )){
						fileListing.add(curDir.listFiles()[i].getName() + "+");
					} else {
						fileListing.add(curDir.listFiles()[i].getName());
					}
				}

				if (targeted) {
					targetBase.setText(t.getAbsolutePath());
				}

				this.setTitle(curDir.getAbsolutePath());
				this.validate();
			} else {
				// target toggle
				if (!targeted) {
					src.setText(t.getAbsolutePath());
				}
			}
		}
		
	}
	
	

	@Override
	public void actionPerformed(ActionEvent e) {
		// clear messages
		mesg.setText("");

		if (e.getSource().equals(okButt) || e.getSource().equals(fileNameField)) {
			// do move file here
			// set label to targetBase
			targetBase.setText(curDir.getAbsolutePath()
					+ fileListing.getSelectedItem());
			// will copy if conditions are met
			if (checkCopy())
				// copy file
				copy();

			targeted = false;

		} else if (e.getSource().equals(fileListing)) {
			listingAction();
		} else if (e.getSource().equals(tarSelect)) {
			File t = new File(curDir.getAbsolutePath()
					+ fileListing.getSelectedItem());
			// current directory is placed in the target label
			// if target button is selected

			if (t == null || t.isDirectory()) {
				// do something for no file/directory
				mesg.setText("Not a valid target");
			} else {
				targeted = true;
			}
		} else {
			mesg.setText("Unknown Event Occured");
		}
		// adjust
		mesg.setSize(200, mesg.getHeight());
		this.pack();

	}

	// copy files
	public void copy() {
		String buff = null;
		BufferedReader in = null;
		PrintWriter out = null;
		try {
			in = new BufferedReader(new FileReader(src.getText()));
		} catch (FileNotFoundException e1) {
		}

		try {
			File t = new File( curDir.getAbsolutePath() + "/" + fileNameField.getText());
			out = new PrintWriter( t.getAbsolutePath() );
			
		} catch (IOException e1) {
			System.out.println( e1.toString() );
		}

		boolean good = false;
		if(out == null)
		{
			System.out.println("Hello");
		}
		
		while (!good) {
			try {
				buff = in.readLine();
			} catch (IOException e1) {
			}

			if (buff != null)
				out.println(buff);
			else
				good = true;
		}
		try {
			in.close();
		} catch (IOException e1) {
		}
		// out.close();
		mesg.setText("Copied: " + src.getText() + "  to: /"
				+ targetBase.getText() + "/" + fileNameField.getText());
		// call clear to reset our textbox and label
		clear();
	}

	public boolean checkCopy() // checks to make sure it is good to copy
	{
		if (src.getText().length() > 0) {
			if (targeted) {
				if (fileNameField.getText().length() > 0) {
					File file = new File(src.getText());
					if (file.exists())
						return true;
					else
						mesg.setText("'" + src.getText()
								+ "' was not found in current directory.");
				} else
					mesg.setText("Select target file");
			} else
				mesg.setText("Select target Directory.");
		} else
			mesg.setText("Select source file.");
		return false;
	}

	public boolean isDirectory(String child) // to check for directory for "+"
	{
		File parentFile = new File(curDir, child);
		
		if (parentFile.isDirectory()) {
			   return true;
		}
		
		return false;
	}

	// clear text box and label
	public void clear() {
		// clear
		src.setText("Source");
		fileNameField.setText("");
		targetBase.setText("Destination");
	}// end clear function
}
