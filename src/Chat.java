/* Laurel Miller, Sean Curtis, Kris Fielding
 * CET 350 - Java, Group 3
 * Program 7 - Chat
 * MIL1484, FIE4795, CUR3040
 */

import java.awt.Button;
import java.awt.CheckboxMenuItem;
import java.awt.Color;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Label;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.Scrollbar;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

//package src;

public class Chat extends Frame implements Runnable, AdjustmentListener, ActionListener, WindowListener, ItemListener{

	private static final long serialVersionUID = 1L;
	protected final static boolean auto_flush = true;
	private final static int DEFAULT_PORT = 44004;
	
	Socket client;
	Socket server;
	ServerSocket listen_socket;
	BufferedReader br;
	InputStreamReader is;
	PrintWriter pw;
	Frame DisplayFrame = new Frame("Chat");
	Thread theThread;
	
	GridBagLayout gbl;
	GridBagConstraints gbc;
	
	MenuBar menu;
	Menu Color;
	CheckboxMenuItem red, blue, green, orange;

	
	//labels
	Label hostLabel;
	Label portLabel;
	
	//buttons
	Button send;
	Button changeHost;
	Button changePort;
	Button disconnect;
	Button connect;
	Button startServer;
	
	//text area
	TextArea dialogue;
	TextArea mesg;
	
	//text field
	TextField port;
	TextField host;
	TextField outMesg;
	
	//scrollbar
	Scrollbar SB;
	
	//listen_socket = new Server Socket(PORT);
	//client = listen_socket.accept():
	
	public static void main(String[] args){
		Chat x = new Chat();
		x.setVisible(true);
		
		BufferedReader kbd = new BufferedReader( new InputStreamReader(System.in) );
	}
	
	public Chat() {
		
		gbl = new GridBagLayout();
		gbc = new GridBagConstraints();
		
		this.addWindowListener(this);
		this.setResizable(true);
		this.setLayout(null);
		this.setVisible(true);
		
		Color.add(red = (new CheckboxMenuItem("Red")));
		red.addItemListener(this);
		Color.add(green = new CheckboxMenuItem("Green"));
		green.addItemListener(this);
		Color.add(blue = new CheckboxMenuItem("Blue"));
		blue.addItemListener(this);
		Color.add(orange = new CheckboxMenuItem("Orange"));
		orange.addItemListener(this);
		
			
		//labels, buttons, scrollbar
		hostLabel = new Label("Host:");
		portLabel = new Label("Port:");
		send = new Button("Send");
		changeHost = new Button("Change Host");
		changePort = new Button("Change Port");
		disconnect = new Button("Disconnect");
		connect = new Button("Connect");
		startServer = new Button("Start Server");
		SB = new Scrollbar(Scrollbar.VERTICAL, 0, 1, 0, 100);
		
/*		SB.setValue(300);
		SB.setBackground(Color.GRAY);
		SB.setSize(sliderW, sliderH);
		SB.setLocation(100, 100);
		SB.setEnabled(true);
		SB.addAdjustmentListener(this);
		SB.setVisible(true);
		this.add(SB);
*/	
		
		/**********SETTING UP GUI**********/

		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		this.add(hostLabel, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		this.add(portLabel, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 3;
		this.add(host, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 1;
		gbc.gridwidth = 3;
		this.add(port, gbc);
		
		gbc.gridx = 3;
		gbc.gridy = 0;
		gbc.gridwidth = 4;
		this.add(outMesg, gbc);
		
		gbc.gridx = 4;
		gbc.gridy = 0;
		gbc.gridwidth = 4;
		this.add(mesg, gbc);
		
		gbc.gridx = 3;
		gbc.gridy = 4;
		gbc.gridwidth = 1;
		this.add(send, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 4;
		gbc.gridwidth = 1;
		this.add(changeHost, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 4;
		gbc.gridwidth = 1;
		this.add(changePort, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 5;
		gbc.gridwidth = 1;
		this.add(connect, gbc);
		
		gbc.gridx = 2;
		gbc.gridy = 5;
		gbc.gridwidth = 1;
		this.add(disconnect, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 5;
		gbc.gridwidth = 1;
		this.add(startServer, gbc);
		
		send.addActionListener(this);
		connect.addActionListener(this);
		disconnect.addActionListener(this);
		startServer.addActionListener(this);
		changeHost.addActionListener(this);
		changePort.addActionListener(this);
	}
	
	public void itemStateChanged(ItemEvent e) {
		Object o = e.getSource();

		//menu for colors
		if(o == red){
			orange.setState(false);
			blue.setState(false);
			green.setState(false);
		}else if(o == orange){
			red.setState(false);
			blue.setState(false);
			green.setState(false);
		}else if(o == green){
			orange.setState(false);
			blue.setState(false);
			red.setState(false);
		}else if(o == blue){
			orange.setState(false);
			red.setState(false);
			green.setState(false);
		}
}
		
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

	public void actionPerformed(ActionEvent e) {}
	public void adjustmentValueChanged(AdjustmentEvent e) {}
	
	public void windowActivated(WindowEvent e) {}
	public void windowClosed(WindowEvent e) {}
	public void windowClosing(WindowEvent e) {System.exit(0);}
	public void windowDeactivated(WindowEvent e) {}	
	public void windowDeiconified(WindowEvent e) {}
	public void windowIconified(WindowEvent e) {}
	public void windowOpened(WindowEvent e) {}
	

}
