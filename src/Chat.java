package src;

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
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Iterator;
import java.util.TimerTask;
import java.util.Vector;

//package src;

public class Chat extends Frame implements Runnable, AdjustmentListener,
		ActionListener, WindowListener, ItemListener, ChatListener {

	private static final long serialVersionUID = 1L;
	protected final static boolean auto_flush = true;
	private final static int DEFAULT_PORT = 44004;

	Vector<Socket> clients;
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
	Menu colorMenu;
	CheckboxMenuItem red, blue, green, orange;

	// labels
	Label hostLabel;
	Label portLabel;

	// buttons
	Button send;
	Button changeHost;
	Button changePort;
	Button disconnect;
	Button connect;
	Button startServer;

	// text area
	TextArea dialogue;
	TextArea mesg;

	// text field
	TextField port;
	TextField host;
	TextField outMesg;

	// scrollbar
	Scrollbar SB;
	
	ChatClient clientGuy;
	ChatServer serverGuy;

	// listen_socket = new Server Socket(PORT);
	// client = listen_socket.accept():

	public static void main(String[] args) {
		Chat x = new Chat();
		x.setSize(400, 600);
		x.setVisible(true);

	//	BufferedReader kbd = new BufferedReader(
	//			new InputStreamReader(System.in));
	}

	public Chat() {
		theThread = new Thread(this);
		gbl = new GridBagLayout();
		gbc = new GridBagConstraints();
		menu = new MenuBar();
		colorMenu = new Menu("Color");

		this.addWindowListener(this);
		this.setResizable(true);
		this.setLayout(gbl);
		this.setVisible(true);

		colorMenu.add(red = (new CheckboxMenuItem("Red")));
		red.addItemListener(this);
		colorMenu.add(green = new CheckboxMenuItem("Green"));
		green.addItemListener(this);
		colorMenu.add(blue = new CheckboxMenuItem("Blue"));
		blue.addItemListener(this);
		colorMenu.add(orange = new CheckboxMenuItem("Orange"));
		orange.addItemListener(this);

		menu.add(colorMenu);
		this.setMenuBar(menu);

		// labels, buttons, scrollbar
		hostLabel = new Label("Host:");
		portLabel = new Label("Port:");
		send = new Button("Send");
		changeHost = new Button("Change Host");
		changePort = new Button("Change Port");
		disconnect = new Button("Disconnect");
		connect = new Button("Connect");
		startServer = new Button("Start Server");
		SB = new Scrollbar(Scrollbar.VERTICAL, 0, 1, 0, 100);

		host = new TextField();
		port = new TextField();
		
		outMesg = new TextField();
		mesg = new TextArea();
		dialogue = new TextArea();

		/*
		 * SB.setValue(300); SB.setBackground(Color.GRAY); SB.setSize(sliderW,
		 * sliderH); SB.setLocation(100, 100); SB.setEnabled(true);
		 * SB.addAdjustmentListener(this); SB.setVisible(true); this.add(SB);
		 */

		/********** SETTING UP GUI **********/

		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.gridwidth = 1;
		this.add(hostLabel, gbc);

		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.gridwidth = 1;
		this.add(portLabel, gbc);

		gbc.ipadx = 100;
		gbc.gridx = 1;
		gbc.gridy = 2;
		gbc.gridwidth = 1;
		this.add(host, gbc);

		gbc.gridx = 1;
		gbc.gridy = 3;
		gbc.gridwidth = 1;
		this.add(port, gbc);
		gbc.ipadx = 0;
		
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 2;
		gbc.ipadx = 250;
		this.add(outMesg, gbc);
		gbc.ipadx = 0;
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 4;
		this.add(dialogue, gbc);

		gbc.gridwidth = 4;
		gbc.gridx = 0;
		gbc.gridy = 5;
		mesg.setRows( 5);
		mesg.setSize(mesg.getWidth(), 200);
		this.add(mesg,gbc);
		
		gbc.gridx = 2;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		this.add(send, gbc);

		gbc.gridx = 2;
		gbc.gridy = 2;
		gbc.gridwidth = 1;
		this.add(changeHost, gbc);

		gbc.gridx = 2;
		gbc.gridy = 3;
		gbc.gridwidth = 1;
		this.add(changePort, gbc);

		gbc.gridx = 3;
		gbc.gridy = 3;
		gbc.gridwidth = 1;
		this.add(connect, gbc);

		gbc.gridx = 3;
		gbc.gridy = 4;
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

	String colSel = "R";

	public void itemStateChanged(ItemEvent e) {
		Object o = e.getSource();

		// menu for colors
		if (o == red) {
			orange.setState(false);
			blue.setState(false);
			green.setState(false);
			colSel = "R";
		} else if (o == orange) {
			red.setState(false);
			blue.setState(false);
			green.setState(false);
			colSel = "O";
		} else if (o == green) {
			orange.setState(false);
			blue.setState(false);
			red.setState(false);
			colSel = "G";
		} else if (o == blue) {
			orange.setState(false);
			red.setState(false);
			green.setState(false);
			colSel = "B";
		}
	}

	boolean serverListenState = false;

	@Override
	public void run() {
		while (serverListenState == true) {
			try {
				clients.add(listen_socket.accept());
			} catch (IOException e) {
				System.err.println("Invalid Client");
				e.printStackTrace();
			}
		}

		try {
			listen_socket.close();
			clients.removeAllElements();
		} catch (IOException e) {
			System.err.println("Can't close server");
			e.printStackTrace();
		}

	}

	public void actionPerformed(ActionEvent e) {
		if( e.getSource() == changeHost){
			
		} else if ( e.getSource() == changePort){
			
		} else if ( e.getSource() == send || e.getSource()==mesg ){
			
		} else if ( e.getSource() == disconnect ){
			
		} else if ( e.getSource() == startServer){
			
		} else if ( e.getSource() == connect ){
			
		} else {
			
		}
	}

	public void adjustmentValueChanged(AdjustmentEvent e) {
	}

	public void windowActivated(WindowEvent e) {
	}

	public void windowClosed(WindowEvent e) {
	}

	public void windowClosing(WindowEvent e) {
		System.exit(0);
	}

	public void windowDeactivated(WindowEvent e) {
	}

	public void windowDeiconified(WindowEvent e) {
	}

	public void windowIconified(WindowEvent e) {
	}

	public void windowOpened(WindowEvent e) {
	}

	public void startServer() throws NumberFormatException, IOException {
		clients = new Vector<Socket>();
		listen_socket = new ServerSocket((Integer.parseInt(port.getText())));
		//server = listen_socket.
		//serverListenState = true;
		theThread.run();
		

	}

	//for server mode
	
	public void stopServer() {
		serverListenState = false;
	}
	
	public void startClient() {
		
	}
	
	public void endClient(){
		
	}

	public void switchHost() {

	}

	public void switchPort() {

	}
	
	public void recvMesg( String s){
		
	}

	@Override
	public void chatMessageRecieved(String mesg, ChatClient l) {
		dialogue.append(mesg);
		
	}

}

class ChatClient implements Runnable{
	Vector<ChatListener> listeners;
	Socket server;
	String colorSel;
	
	public ChatClient(){
		listeners = new Vector<ChatListener>();
	}
	public void sendMesg(String s) {
		try {
			PrintWriter pw = new PrintWriter(server.getOutputStream());
			pw.println(colorSel + s);
		} catch (Exception e) {

		}

	}
	
	public void setColor(){
		colorSel = "B";
	}
	
	public void setServer( String serv, int port ) throws UnknownHostException, IOException{
		if( server != null){
			try{
				server.close();
			} catch (IOException e){
				System.out.println("Blocked in IO");
			}
		}
			
		server = new Socket(serv, port);
		server.setKeepAlive(true);
		
	}
	
	public void setSocket( Socket s ){
		server = s;
		try {
			server.setKeepAlive(true);
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void addListener( ChatListener c){
		listeners.add(c);
	}
	
	//notify listeners
	public void doMessage( String mesg ){
		Iterator<ChatListener> i = listeners.iterator();
		while( i.hasNext())
			i.next().chatMessageRecieved(mesg, this);
	}
	
	boolean activeListen;
	
	public void setActiveListen( boolean b ){
		activeListen = b;
	}
	
	public boolean getActiveListen(){
		return activeListen;
	}
	
	//listen for messages
	@Override
	public void run() {
		BufferedReader  bfreader = null;
		try {
			bfreader = new BufferedReader(  new InputStreamReader( server.getInputStream() ) );
			while ( activeListen ){
				String s = bfreader.readLine();
				doMessage( s );
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if( bfreader != null)
			try {
				bfreader.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
}

interface ChatListener{
	public void chatMessageRecieved( String mesg, ChatClient src);
}

class ChatServer implements Runnable, ChatListener{
	ServerSocket serv;
	Thread acceptor;
	Vector<ChatClient> peons;
	int port = 44004;
	
	public ChatServer(){
		peons = new Vector<ChatClient>();
	}

	public void setPort( int p ){
		port = p;
		stopServer();
		startServer();
	}
	
	public int getPort( ){
		return port;
	}
	
	
	boolean serverListenState = false;
	
	public void startServer(){
		serverListenState = true;
	}
	
	public void stopServer() {
		serverListenState = false;
		//stop listening to peons
		
		Iterator<ChatClient> i = peons.iterator();
		while( i.hasNext() ){
			i.next().setActiveListen(false);
		}
		
		peons.clear();
		
	}
	
	String lastMesg = "";
	
	public void broadcast(){
		if( serverListenState == true ){
			Iterator<Socket> i = clients.iterator();
			while( i.hasNext() ){
				try {
					PrintWriter pw = new PrintWriter(i.next().getOutputStream());
					pw.println(lastMesg);
				} catch (Exception e) {

				}

			}
		}
	}

	
	@Override
	public void run() {
		while( serverListenState == true){
			Socket t = null;
			try {
				t = serv.accept();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if ( t != null ){
				ChatClient tc = new ChatClient();
				tc.setSocket(t);
				tc.addListener(this);
				peons.add( tc );
			}
		}
		
	}

	@Override
	public void chatMessageRecieved(String mesg, ChatClient src) {
		// TODO Auto-generated method stub
		
	}
}