package src;

/*******************************************

 Sean Curtis, Kris Fielding, Laurel Miller
 CET 350 - Java
 Program 5 - Bounce2
 Group Number: 3
 email: MIL1484, FIE4795, CUR3040

 *******************************************/

//Enter file contents here import java.applet.Applet;
import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Label;
import java.awt.Panel;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Scrollbar;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Vector;

public class Bounce2 extends Applet implements ActionListener, MouseListener,
		AdjustmentListener, Runnable, MouseMotionListener {

	private static final long serialVersionUID = 10L;
	public static final int OFFSETX = 5;
	public static final int OFFSETY = 5;

	//points
	Point x1,x2;
	
	// scroll bars
	Scrollbar speed, size;

	// labels
	Label sizeLabel, speedLabel;

	// panels
	Panel control, sheet;

	// buttons
	Button start, quit, clear;

	// vector
	Vector<Rectangle> Walls;
	
	//good = true, mouse on screen
	//good - false, mouse not on screen
	boolean good,drag;

	private Ballc ball;
	private int delay = 20;
	boolean p, q;

	// the thread
	private Thread theThread;

	public void init() {

		// vector
		Walls = new Vector<Rectangle>();
		ball = new Ballc();
		// pause, quit
		p = true;
		q = false;

	//	theThread = new Thread(this);

		// sets up the control panel
		setControlPanel();

		// sets up the sheet panel
		setSheetPanel();

		// set Layout
		setLayout(new BorderLayout(0, 0));
		setVisible(true);

		// add listeners
		start.addActionListener(this);
		clear.addActionListener(this);
		quit.addActionListener(this);
		size.addAdjustmentListener(this);
		speed.addAdjustmentListener(this);
		add(sheet, BorderLayout.CENTER);
		add(control, BorderLayout.SOUTH);

	}

	private void setSheetPanel() {

		sheet = new Panel();
		sheet.setVisible(true);
		sheet.setLayout(new BorderLayout(0, 0));
		sheet.setSize(900, 490);
		// ball = new Ballc(w,h,rectangle screen);
		ball.addMouseListener(this);
		ball.addMouseMotionListener(this);
		ball.setSize(900, 490);
		sheet.add("Center", ball);
		sheet.setBackground(Color.WHITE);
	}

	private void setControlPanel() {

		control = new Panel();

		// creates the grid lines
		GridBagLayout gbl;
		GridBagConstraints gbc;

		// GridBag Layout
		gbc = new GridBagConstraints();
		gbl = new GridBagLayout();

		control.setVisible(true);
		control.setLayout(gbl);

		// scroll bars
		speed = new Scrollbar(Scrollbar.HORIZONTAL, 0, 1, 1, 100);
		size = new Scrollbar(Scrollbar.HORIZONTAL, 0, 1, 1, 100);
		size.setMaximum(100);
		speed.setMaximum(50);
		
		size.setBlockIncrement(2);
		speed.setBlockIncrement(2);
		
		size.setMinimum(5);
		speed.setMinimum(0);
		
		size.setUnitIncrement(1);
		speed.setUnitIncrement(1);
		
		size.setSize(100, 25);
		speed.setSize(100, 25);
		
		size.setBackground(Color.BLUE);
		speed.setBackground(Color.RED);
		
		size.setEnabled(true);
		speed.setEnabled(true);

		size.validate();
		speed.validate();
		
		// set text for the labels
		speedLabel = new Label("Speed:");
		sizeLabel = new Label("Size:");

		// set text for the buttons
		clear = new Button("CLEAR");
		quit = new Button("QUIT");
		start = new Button("START");

		/********* SETTING UP THE GUI *************/
		
		// SIZE LABEL
		gbc.gridx = 2;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		control.add(sizeLabel, gbc);

		// SPEED LABEL
		gbc.gridwidth = 1;
		gbc.gridx = 2;
		gbc.gridy = 2;
		control.add(speedLabel, gbc);

		// START BUTTON
		gbc.gridwidth = 1;
		gbc.gridx = 2;
		gbc.gridy = 3;
		control.add(start, gbc);

		// QUIT BUTTON
		gbc.gridx = 4;
		gbc.gridy = 3;
		gbc.gridwidth = 1;
		control.add(quit, gbc);

		// CLEAR BUTTON
		gbc.gridx = 6;
		gbc.gridy = 3;
		gbc.gridwidth = 1;
		control.add(clear, gbc);

		//scrollbars
		gbc.gridwidth = 3;
		gbc.ipadx = 100;
		
		// SIZE SCROLL BAR
		gbc.gridx = 3;
		gbc.gridy = 1;
		
		control.add(size, gbc);

		// SPEED SCROLL BAR
		gbc.gridx = 3;
		gbc.gridy = 2;
		control.add(speed, gbc);

	}

	public void start() {

		if (theThread == null) {
			// create the thread, if there isn't one
			theThread = new Thread(this);
			// triggers the program
			theThread.start();
		}
	}

	private boolean more = true;
	public void run() {
		
		//Thread.currentThread().setPriority(Thread.MAX_PRIORITY);

		while (more) {
			//if (!p) {
				// paint
				ball.repaint();
				// update, move ball
				ball.move();
			//}

			try {
				// wait
				Thread.sleep(delay);
			} catch (InterruptedException e) {
			}
		}
		
	}

	public void stop() {

		// remove all listeners
		clear.removeActionListener(this);
		quit.removeActionListener(this);
		start.removeActionListener(this);
		size.removeAdjustmentListener(this);
		speed.removeAdjustmentListener(this);
		ball.removeMouseListener(this);
		ball.removeMouseMotionListener(this);

		// kill the thread
		more = false;
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void adjustmentValueChanged(AdjustmentEvent e) {
		if (e.getSource().equals(speed)) {
			// TODO get speed value
			// obj.setSpeed(speed.getValue());
		} else if (e.getSource().equals(size)) {
			// TODO get size
			// obj.setRadius(size.getValue()/2 + 1);
		}

	}

	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();

		// button selection
		if (source == start) {
			p = !p;
			if (p) {
				// pause the ball
				theThread.suspend();
				start.setLabel("Start");
			} else {
				// start the ball
				theThread.resume();
				start.setLabel("Stop");
			}

		} else if (source.equals(clear)) {
			// TODO clear everything
			// obj.reset();
			p = true;
			theThread.suspend();
			start.setLabel("Start");
			repaint();
		} else if (source == quit) {
			// exit
			System.exit(0);
		}

		repaint();
		this.validate();
	}

	public void mouseClicked(MouseEvent e) {
			Point pt = new Point(e.getPoint());
	}
	
	public void mouseDragged(MouseEvent e) {
			//if we are on the screen to draw
			if(good)
			{
				//we are dragging
				drag=true;
				//get x2 point
				x2 = e.getPoint();
			}
	}

	
	public void mouseEntered(MouseEvent e) {
		//mouse is on the screen to draw
		good = true;
	}
	public void mouseExited(MouseEvent e) {
		//mouse is not on screen to draw
		good = false;
	}
	public void mousePressed(MouseEvent e) {
		//if mouse is on the screen to draw
		if(good)
		{
			//get x1 point
			x1 = e.getPoint();
		}
	}
	public void mouseReleased(MouseEvent e) {
		//if mouse is on the screen to draw
		if(good)
		{
			//done dragging
			drag= false;
			x2 = e.getPoint();
		}
	}
	/************ RETURN VALUES TO DRAW ************/
	public int returnx1() {
		return (int)Math.min(x1.getX(), x2.getX());
		}

	public int returny1() {
		return (int)Math.min(x1.getY(), x2.getY());
		}

	public int returnx2() {
		return (int)Math.min(x2.getX(), -x1.getX());
		}
	public int returny2() {
		return (int)Math.min(x2.getY(), -x1.getY());
		}

}//end Bounce2	

class Ballc extends Canvas {

	private static final long serialVersionUID = 1L;
	Image buffer;

	// Graphics g;

	public Ballc() {
		super();
	}

	public void paint(Graphics g) {
		if (buffer == null) {
			buffer = createImage(900, 490);
			
			//if drag is true, get rect points
			if(drag)
			{
				g.drawRect(returnx1(), returnx2(), returny1(), returny2());
			}
		}

		Graphics cg = buffer.getGraphics();
		
		// eliminate previous drawing;
		cg.clearRect(0, 0, 900, 490);

		int height = 890;
		int width = 480;
/*		
	    // these are for speed
		int dx = 1;
		int dy = 1;
*/		
		// this is size
		int radius = 5;
		
		// draws the rectangle for the frame
		cg.drawRect(5, 5, height, width);
		cg.drawRect(x - radius, y - radius, radius * 2, radius * 2);
		
		//buffer
		g.drawImage(buffer, 0, 0, this);
		Rectangle r = getBoundingRectangle();
		
		//checking the walls
		boolean draw = false;
	
		if( (int)r.getMaxX() >= height + Bounce2.OFFSETX - getRadius()){
			rightSide();
			draw = true;
		}
		if( (int)r.getMinX() <= Bounce2.OFFSETX + getRadius() ){
			leftSide();
			draw = true;
		}
		if( (int)r.getMaxY() >= width + Bounce2.OFFSETY - getRadius()){
			bottomSide();
			draw = true;
		}
		if( (int)r.getMinY() <= Bounce2.OFFSETY + getRadius() ){
			topSide();
			draw = true;
		}
		//if( !draw)
		//	drawBall(g);
	}//end paint
	
/************ GETS AND SETS ************/
	public int getX() {
		return x - radius;
	}

	public int getY() {
		return y - radius;
	}

	public int getRadius() {
		
		return radius;
	}
	
	public Rectangle getBoundingRectangle() {

		return new Rectangle(x - radius, y - radius, radius * 2, radius * 2);
	}
	
/************ WALLS ************/
	public void leftSide() {
		dx = -dx;
	}
	public void rightSide() {
		dx = -dx;
	}

	public void bottomSide() {
		dy = -dy;
	}

	public void topSide() {
		dy = -dy;
	}
	
	public void wall( int dx, int dy){
		x += dx;
		y += dy;
	}
	
	public void move() {
		x += dx;
		y += dy;
	}

	public void updatePos() {
		x += dx;
		y += dy;
	} 
	
	public void update(Graphics cg) {
		
		paint(cg);
	}

	// placement of the ball
	int x = Bounce2.OFFSETX + 20;
	int y = Bounce2.OFFSETY + 20;
	// these are for speed
	int dx = 10;
	int dy = 10;
	// this is size
	int radius = 5;

	public void reset() {
		// reset the ball to the start position
		x = Bounce2.OFFSETX + 20;
		y = Bounce2.OFFSETY + 20;
	}

	public void setSpeed(int s) {
		dx = s * dx / (int) Math.abs(dx);
		dy = s * dy / (int) Math.abs(dy);
	}

	public void setRadius(int r) {
		radius = r;
	}
	


}//end Ballc
	

/*
 * class ObjBall { // placement of the ball int x = OFFSETX + 20; int y =
 * OFFSETY + 20; // these are for speed int dx = 1; int dy = 1; // this is size
 * int radius = 5;
 * 
 * Rectangle[] tails;
 * 
 * boolean rectangle = false; boolean drawTails = false; int t =0; int numTails
 * = 500; public ObjBall() { tails = new Rectangle[numTails]; }
 * 
 * public int getRadius() {
 * 
 * return radius; }
 * 
 * public void reset() { x = OFFSETX + 20; y = OFFSETY + 20; tails = new
 * Rectangle[numTails]; }
 * 
 * public void updatePos() { x += dx; y += dy; }
 * 
 * public boolean isRectangle() { return rectangle; }
 * 
 * public void setRectangle(boolean rectangle) { this.rectangle = rectangle; }
 * 
 * 
 * // convenice for drawing public void drawBall(Graphics g) {
 * 
 * if (!rectangle) { g.drawOval(x + radius / 2, y + radius / 2, radius*2,
 * radius*2); } else { g.drawRect(x + radius / 2, y + radius / 2, radius*2,
 * radius*2); } //draw tails, for the furries if( drawTails){ //save current
 * tails[t % numTails] = new Rectangle(x + radius / 2, y + radius / 2, radius*2,
 * radius*2); //draw rest for(int i = 0; i < tails.length; i++ ){ if( tails[i]
 * != null){ if (!rectangle) { //draw oval g.drawOval(tails[i].x, tails[i].y,
 * tails[i].width, tails[i].height); } else { //draw rect g.drawRect(tails[i].x,
 * tails[i].y, tails[i].width, tails[i].height); } //g.drawRect( tails[i].x,
 * tails[i].y, tails[i].width, tails[i].height); } } t++; } }
 * 
 * // convience functions for hiting stuff public void leftSide() { dx = -dx; }
 * 
 * public boolean isDrawTails() { return drawTails; }
 * 
 * public void setDrawTails(boolean drawTails) { this.drawTails = drawTails; if(
 * drawTails == false){ tails = null; tails= new Rectangle[numTails]; } }
 * 
 * public void rightSide() { dx = -dx; }
 * 
 * public void bottomSide() { dy = -dy; }
 * 
 * public void topSide() { dy = -dy; } public void wall( int dx, int dy){ x +=
 * dx; y += dy; }
 * 
 * // gets and sets public int getX() { return x - radius; }
 * 
 * public int getY() { return y - radius; }
 * 
 * public void setRadius(int r) { radius = r; }
 * 
 * public Rectangle getBoundingRectange() {
 * 
 * return new Rectangle(x - radius, y - radius, radius * 2, radius * 2);
 * 
 * }
 * 
 * 
 * public void setSpeed(int s) { dx = s * dx / (int)Math.abs(dx); dy = s * dy /
 * (int)Math.abs(dy); }
 * 
 * } }
 */
