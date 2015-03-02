//Enter file contents hereimport java.applet.Applet;
import java.applet.Applet;
import java.awt.Button;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Label;
import java.awt.Rectangle;
import java.awt.Scrollbar;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.WindowEvent;


class Bounce extends Applet implements ActionListener, AdjustmentListener {

	private static final long serialVersionUID = 10L;

	private static final int MAXSPEED = 0;

	private static final int MINSPEED = 0;

	private static final int SPEED = 0;
	
	//graphics page
	private Graphics page;
	
	//Ball Object
	private ObjBall obj;
	
	// creates the grid lines
	GridBagLayout gbl;
	GridBagConstraints gbc;

	// creates buttons on GUI
	Button startstop;
	Button rect;
	Button clear;
	Button tails;
	Button quit;


	// creates labels on GUI
	Label speedLabel;
	Label sizeLabel;
	
	//scroll bar
	Scrollbar speed, size;
	
	//scroll bar boundaries
	int MAXIMUM = 100;
	int MINIMUM = 0;

	private int currentspeed;

	private int sliderW;

	private int sliderH;

public static void main(String[] args) {
	Bounce wind = new Bounce( );
	wind.setVisible(true);
}

public Bounce( ) {
	//buttons
	startstop = new Button("Start");
	clear = new Button("Clear");
	quit = new Button("Quit");
	rect = new Button("Rectangle");
	tails = new Button("Tails");
	
	//labels
	speedLabel = new Label("Speed:");
	sizeLabel = new Label("Size:");
	
	//grid bag layout
	gbl = new GridBagLayout();
	gbc = new GridBagConstraints();
	
	//this.addWindowListener(this);
	//this.setResizable(true);
	this.setLayout(gbl);
	
	//starts at cell 0,0
	gbc.gridx = 0;
	gbc.gridy = 0;
	
	//5 by 5 
	gbc.gridwidth = 5;
	gbc.gridheight = 5;
	gbc.ipady = 200;
	gbc.ipadx = 400;
	
	//speed label
	gbc.gridx = 0;
	gbc.gridy = 3;
	gbc.gridwidth = 2;
	this.add(speedLabel, gbc);

	//size label
	gbc.gridx = 0;
	gbc.gridy = 4;
	gbc.gridwidth = 2;
	this.add(sizeLabel, gbc);

	//rect button
	gbc.gridx = 2;
	gbc.gridy = 3;
	gbc.gridwidth = 1;
	this.add(rect, gbc);

	//tails button
	gbc.gridx = 3;
	gbc.gridy = 3;
	gbc.gridwidth = 1;
	this.add(tails, gbc);

	//startstop button
	gbc.gridx = 4;
	gbc.gridy = 3;
	gbc.gridwidth = 1;
	this.add(startstop, gbc);

	//clear button
	gbc.gridx = 3;
	gbc.gridy = 4;
	gbc.gridwidth = 1;
	this.add(clear, gbc);

	//quit
	gbc.gridx = 4;
	gbc.gridy = 4;
	gbc.gridwidth = 1;
	this.add(quit, gbc);

	
	//scroll bars
	speed = new Scrollbar(Scrollbar.HORIZONTAL);
	speed.setMaximum(MAXSPEED);
	speed.setMinimum(MINSPEED);
	speed.setUnitIncrement(SPEED);
	speed.setBlockIncrement(SPEED*10);
	speed.setValue(currentspeed);
	speed.setBackground(Color.GRAY);
	speed.setSize(sliderW, sliderH);
	speed.setLocation(10, HEIGHT);
	speed.addAdjustmentListener(this);
	add(speed);
	//Obj.Start;
}

public void init(){
	setLayout(null);
	setVisible(true);
	setBackground(Color.white);
	page = getGraphics();
	//obj = new ObjBall (page, getBackground(), Wobj, Hobj, WIDTH, HEIGHT, MAXSPEED, currentspeed, pause, step);
}

public void start(){}
public void stop(){}
public void run(){}


public void windowActivated(WindowEvent e) {}

public void windowClosed(WindowEvent e) {}

public void windowClosing(WindowEvent e) {
	System.exit(0);
}

public void windowDeactivated(WindowEvent e) {}

public void windowDeiconified(WindowEvent e) {}

public void windowIconified(WindowEvent e) {}

public void windowOpened(WindowEvent e) {}

public void actionPerformed(ActionEvent arg0) {}

public void adjustmentValueChanged(AdjustmentEvent e) {
	int v;
	
	Scrollbar sb = (Scrollbar) e.getSource();
	
	//speed scroll bar
	if(sb == speed)
	{
		//get value
		v = sb.getValue();
		if(v > MAXIMUM)
		{
			//set max
			sb.setValue(MAXIMUM);
		}
		if(v < MINIMUM)
		{
			sb.setValue(MINIMUM);
		}
	}
}

} //end 

class ObjBall{
	//placement of the ball
	int x = 0;
	int y = 0;
	//these are for speed
	int dx = 0;   
	int dy = 0;
	//this is size
	int radius = 0;
	
	
	public ObjBall(){
		
	}
	//convience for drawing
	public void drawBall( Graphics g) {
		
	}
	
	//convience functions for hiting stuff
	public void leftWall(){
		
	}

	public void rightSide(){
		
	}
	
	public void bottomSide(){
		
	}
	
	public void topSide(){
		
	}
	
	//gets and sets
	public int getX(){
		
	}
	
	public int getY(){
		
	}
	
	public void setRadius( int r) {
		
	}
	public Rectangle getBoundingRectange(){
		
		return new Rectangle(x -radius, y-radius, radius * 2, radius * 2);
		
	}
	/*  this is a absolute for pixel based jump speed
	*   direction is calculated from scaling the vector [dx dy]
	*   according to [1 1] 
	*   aka WARNING: math ahead
	*
	*/
	public void setSpeed( int s ){
		
	}
	
}
