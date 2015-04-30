package src;

/* Laurel Miller, Sean Curtis, Kris Fielding
 * CET 350 - Java, Group 3
 * Program 7 - Chat
 * MIL1484, FIE4795, CUR3040
 */

import java.awt.Button;
import java.awt.Checkbox;
import java.awt.CheckboxGroup;
import java.awt.CheckboxMenuItem;
import java.awt.Color;
import java.awt.Font;
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
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Iterator;
import java.util.TimerTask;
import java.util.Vector;
import java.awt.font.*;

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
	
	//radio buttons
		CheckboxGroup fonts = new CheckboxGroup();
		CheckboxGroup font1, font2, font3;
		

	// listen_socket = new Server Socket(PORT);
	// client = listen_socket.accept():

	public static void main(String[] args) {
		Chat x = new Chat();
		x.setSize(400, 600);
		x.setVisible(true);

	//	BufferedReader kbd = new BufferedReader(
	//			new InputStreamReader(System.in));
	}

	Font style1, style2, style3;
	
	public Chat() {
		theThread = new Thread(this);
		gbl = new GridBagLayout();
		gbc = new GridBagConstraints();
		menu = new MenuBar();
		colorMenu = new Menu("Color");
		
		Checkbox font1 = new Checkbox("Monospaced", fonts, false);
		Checkbox font2 = new Checkbox("Sans Serif", fonts, false);
		Checkbox font3 = new Checkbox("Serif", fonts, false);
	
		Font style1 = Font.getFont(Font.MONOSPACED);
		Font style2 = Font.getFont(Font.SANS_SERIF);
		Font style3 = Font.getFont(Font.SERIF);
		
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
		
		gbc.gridx = 0;
		gbc.gridy = 7;
		gbc.gridwidth = 1;
		this.add(font1, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 7;
		gbc.gridwidth = 1;
		this.add(font2, gbc);
		
		gbc.gridx = 2;
		gbc.gridy = 7;
		gbc.gridwidth = 1;
		this.add(font3, gbc);
		
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

		gbc.gridx = 2;
		gbc.gridy = 4;
		gbc.gridwidth = 1;
		this.add(startServer, gbc);

		
		port.setText(Integer.toString(DEFAULT_PORT));
		host.setText("127000000001");
		send.addActionListener(this);
		outMesg.addActionListener(this);
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
	
		//changing the font
		if(o == font1)
		{
			changeHost.setFont(style1);
			changePort.setFont(style1);
			startServer.setFont(style1);
			connect.setFont(style1);
			disconnect.setFont(style1);
			send.setFont(style1);
			portLabel.setFont(style1);
			hostLabel.setFont(style1);
		}else if ( o == font2){
			changeHost.setFont(style2);
			changePort.setFont(style2);
			startServer.setFont(style2);
			connect.setFont(style2);
			disconnect.setFont(style2);
			send.setFont(style2);
			portLabel.setFont(style2);
			hostLabel.setFont(style2);
		}else if(o == font3){
			changeHost.setFont(style3);
			changePort.setFont(style3);
			startServer.setFont(style3);
			connect.setFont(style3);
			disconnect.setFont(style3);
			send.setFont(style3);
			portLabel.setFont(style3);
			hostLabel.setFont(style3);
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
			switchHost();
		} else if ( e.getSource() == changePort){
			switchPort();
		} else if ( e.getSource() == send || e.getSource()==outMesg ){
			if( !(outMesg.getText()).equals("") ){
				sendMesg( outMesg.getText() );
			} else {
				mesg.append("You need someting to say to chat, telepathy not allowed\n");
			}
			outMesg.setText("");
		} else if ( e.getSource() == disconnect ){
			stopClient();
		} else if ( e.getSource() == startServer){
			try {
				startServer();
			} catch (NumberFormatException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} else if ( e.getSource() == connect ){
			startClient();
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
		if( serverGuy == null){
			serverGuy = new ChatServer();
			serverGuy.setPort( Integer.parseInt(port.getText() ) );
		}

	}

	public void sendMesg( String s ){
		if( clientGuy != null ){
			clientGuy.sendMesg(s);
		} else{
			mesg.append("You have no friends\n");
		}
	}
	
	//for server mode
	
	public void stopServer() {
		serverListenState = false;
	}
	
	public void startClient() {
		if( clientGuy == null)
			clientGuy = new ChatClient();
		try {
			clientGuy.setServer( host.getText() ,Integer.parseInt(port.getText()));
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		clientGuy.addListener(this);
	}
	
	public void stopClient(){
		System.exit(0);
	}

	public void switchHost() {
		clientGuy = null;
		startClient();
	}

	public void switchPort() {
		if( server != null){
			stopServer();
			try {
				startServer();
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		switchHost();
	}
	

	@Override
	public void chatMessageRecieved(String mesg, ChatClient l) {
		System.out.println("Got one");
		dialogue.append(mesg);
		
	}

}

class ChatClient implements Runnable{
	Vector<ChatListener> listeners;
	Socket server;
	String colorSel;
	Thread servLis;
	
	public ChatClient(){
		listeners = new Vector<ChatListener>();
	}
	public void sendMesg(String s) {
		try {
			PrintWriter pw = new PrintWriter(server.getOutputStream());
			pw.println( s + "\n");
			System.out.println("Sending");
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
		//InetAddress ia = InetAddress.
		server = new Socket(InetAddress.getByName(serv), port);
		server.setKeepAlive(true);
		
		servLis = new Thread( this );
		servLis.start();
		
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
	
	boolean activeListen = true;
	
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
		System.out.println("In thread");
		try {
			bfreader = new BufferedReader(  new InputStreamReader( server.getInputStream() ) );
			while ( activeListen ){
				System.out.println("WOrking");
				String s = bfreader.readLine();
				System.out.println( s );
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
		
		try {
			serv = new ServerSocket(port);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if( acceptor == null)
			acceptor = new Thread(this);
		acceptor.start();
		
	}
	
	public void stopServer() {
		serverListenState = false;
		//stop listening to peons
		
		Iterator<ChatClient> i = peons.iterator();
		while( i.hasNext() ){
			ChatClient c = i.next();
			c.setActiveListen(false);
			try {
				c.server.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		peons.clear();
		
	}
	
	String lastMesg = "";
	
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
		Iterator<ChatClient> i = peons.iterator();
		System.out.println("Got Message");
		while ( i.hasNext() ){
			ChatClient c = i.next();
			c.doMessage(mesg);
		}
		
	}
}
