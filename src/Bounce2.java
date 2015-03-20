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
	public static final int OFFSETX = 100;
	public static final int OFFSETY = 25;

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

	private Ballc ball;
	private int delay;
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

		theThread = new Thread(this);

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
		// ball = new Ballc(w,h,rectangle screen);
		ball.addMouseListener(this);
		ball.addMouseMotionListener(this);
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
		speed = new Scrollbar(Scrollbar.HORIZONTAL);
		size = new Scrollbar(Scrollbar.HORIZONTAL);

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

		// SIZE SCROLL BAR
		gbc.gridx = 3;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		control.add(size, gbc);

		// SPEED SCROLL BAR
		gbc.gridx = 3;
		gbc.gridy = 2;
		gbc.gridwidth = 1;
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

	public void run() {
		boolean more = true;
		Thread.currentThread().setPriority(Thread.MAX_PRIORITY);

		while (more) {
			if (!p) {
				// paint
				ball.repaint();
				// update, move ball
				ball.move();
			}

			try {
				// wait
				Thread.sleep(delay);
			} catch (InterruptedException e) {
			}
		}
		stop();
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
		Thread.currentThread().setPriority(Thread.MIN_PRIORITY);
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub

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

	public void mouseClicked(MouseEvent arg0) {
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

	public void mousePressed(MouseEvent e) {
	}

	public void mouseReleased(MouseEvent e) {
	}

}

class Ballc extends Canvas {

	private static final long serialVersionUID = 1L;
	Image buffer;

	// Graphics g;

	public Ballc() {
		super();
	}

	public void paint(Graphics g) {
		if (buffer == null) {
			buffer = createImage(490, 900);
		}

		Graphics cg = buffer.getGraphics();
		// eliminate previous drawing;
		cg.clearRect(0, 0, 490, 900);

		int height = 900;
		int width = 490;
		// draws the rectangle for the frame
		cg.drawRect(Bounce2.OFFSETX, Bounce2.OFFSETY, height, width);
		// update(cg);

		cg.drawRect(x - radius, y - radius, radius * 2, radius * 2);

		g.drawImage(buffer, 0, 0, this);
	}

	public void move() {
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
	int dx = 1;
	int dy = 1;
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
}

// end Ballc

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