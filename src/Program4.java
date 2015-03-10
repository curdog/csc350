/*******************************************

Sean Curtis, Kris Fielding, Laurel Miller
CET 350 - Java
Program 4 - Bounce
Group Number: 3
email: MIL1484, FIE4795, CUR3040

 *******************************************/

package src;

//Enter file contents here import java.applet.Applet;
import java.applet.Applet;
import java.awt.Button;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Label;
import java.awt.Rectangle;
import java.awt.Scrollbar;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;

/*
 <applet code="Bounce.class" width=500 height = 500>
 </applet>
 */

public class Program4 extends Applet implements ActionListener,
		AdjustmentListener, Runnable {

	private static final long serialVersionUID = 10L;

	private static final int MAXSPEED = 100;

	private static final int MINSPEED = 0;

	private static final int SPEED = 0;

	private static final int MAXSIZE = 100;

	private static final int MINSIZE = 0;

	private static final int SIZE = 1;

	public static final int OFFSETX = 100;
	public static final int OFFSETY = 25;

	// graphics page
	private Graphics page;

	// Ball Object
	private ObjBall obj;

	// creates buttons on GUI
	Button startstop;
	Button rect;
	Button clear;
	Button tails;
	Button quit;

	// for scroll bar
	int MAXIMUM;
	int MINIMUM;

	boolean pause, exit;

	// creates labels on GUI
	Label speedLabel;
	Label sizeLabel;

	// scroll bar
	Scrollbar speed, size;

	// Thread
	Thread ballmover;

	private int currentspeed;

	private int sliderW;

	private int sliderH;

	public static void main(String[] args) {
		new Program4();
	}

	public Program4() {
		super();

		ballmover = new Thread(this);
		setLayout(null);
		setVisible(true);
		setBackground(Color.white);
		page = getGraphics();

		// buttons
		startstop = new Button("Stop");
		clear = new Button("Clear");
		quit = new Button("Quit");
		rect = new Button("Rectangle");
		tails = new Button("Tails");

		// labels
		speedLabel = new Label("Speed:");
		sizeLabel = new Label("Size:");

		// speed scroll bars
		speed = new Scrollbar(Scrollbar.HORIZONTAL);
		speed.setMaximum(MAXSPEED);
		speed.setMinimum(MINSPEED);
		speed.setUnitIncrement(SPEED);
		speed.setBlockIncrement(SPEED * 10);
		speed.setValue(currentspeed);
		speed.setBackground(Color.GRAY);
		speed.setSize(sliderW, sliderH);
		speed.setLocation(200, 375);
		speed.addAdjustmentListener(this);
		speed.setVisible(true);
		this.add(speed);

		// size scroll bar
		// Sc
		size = new Scrollbar(Scrollbar.HORIZONTAL, 0, 1, 1, 100);
		/*
		 * size.setMaximum(MAXSIZE); size.setMinimum(MINSIZE);
		 * size.setUnitIncrement(SIZE); size.setBlockIncrement(SIZE*10);
		 * size.setValue(currentspeed); size.setBackground(Color.GRAY);
		 * size.setSize(sliderW, sliderH); size.setLocation(250, 675);
		 * size.addAdjustmentListener(this); size.setVisible(true);
		 */

		this.add(size);

		// adding buttons to the GUI
		startstop.setBounds(100, 600, 75, 25);
		this.add(startstop);
		tails.setBounds(200, 600, 75, 25);
		this.add(tails);
		rect.setBounds(300, 600, 75, 25);
		this.add(rect);
		clear.setBounds(400, 600, 75, 25);
		this.add(clear);
		quit.setBounds(500, 600, 75, 25);
		this.add(quit);

		// adding labels to the GUI
		speedLabel.setBounds(100, 650, 130, 25);
		this.add(speedLabel);
		sizeLabel.setBounds(100, 675, 130, 25);
		this.add(sizeLabel);
		obj = new ObjBall();
		// Obj.Start;
		validate();
	}

	public void init() {
		// obj = new ObjBall (page, getBackground(), Wobj, Hobj, WIDTH, HEIGHT,
		// MAXSPEED, currentspeed, pause, step);
		pause = true;
		exit = false;

		startstop.addActionListener(this);
		clear.addActionListener(this);
		quit.addActionListener(this);
		tails.addActionListener(this);
		rect.addActionListener(this);
		size.addAdjustmentListener(this);
		speed.addAdjustmentListener(this);

		// draws the rectangle for the frame that the ball will be bouncing in
		// paint(g);
	}

	// draws the frame for the bouncing ball
	public void paint(Graphics g) {
		// maybe make these boundaries constants?
		g.drawRect(OFFSETX, OFFSETY, 900, 490);
		obj.drawBall(g);

	}

	public void start() {
		ballmover.start();
	}

	public void stop() {

		// remove listeners
		clear.removeActionListener(this);
		quit.removeActionListener(this);
		startstop.removeActionListener(this);
		tails.removeActionListener(this);
		rect.removeActionListener(this);
		size.removeAdjustmentListener(this);
		speed.removeAdjustmentListener(this);
	}

	public void run() {
		while (1 == 1) {
			obj.updatePos();
			repaint();

			try {
				Thread.sleep(300);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	public void actionPerformed(ActionEvent e) {

		Object source = e.getSource();

		if (source == startstop) {
			pause = !pause;
			if( pause){
				ballmover.suspend();
				startstop.setLabel("Start");
			} else {
				ballmover.resume();
				startstop.setLabel("Stop");
			}

		} else if (source.equals(clear)) {
			// clear everything
			obj.reset();
			pause = true;
			ballmover.suspend();
			startstop.setLabel("Start");
			repaint();
		} else if (source == quit) {
			// exit
			System.exit(0);
		} else if (source.equals(rect)) {
			if (rect.getLabel().equals("Rectangle")) {
				obj.setRectangle(true);
				rect.setLabel("Circle");
			} else {
				obj.setRectangle(false);
				rect.setLabel("Rectangle");
			}
			repaint();
		}

		this.validate();
	}

	public void adjustmentValueChanged(AdjustmentEvent e) {
		int v;
		int w;

		Scrollbar sb = (Scrollbar) e.getSource();

		// speed scroll bar
		if (sb == speed) {
			// get value
			v = sb.getValue();
			if (v > MAXIMUM) {
				// set max
				sb.setValue(MAXIMUM);
			}
			if (v < MINIMUM) {
				sb.setValue(MINIMUM);
			}
		}

		// size scroll bar
		if (sb == size) {
			// get value
			w = sb.getValue();
			if (w > MAXSIZE) {
				// set to maxsize
				sb.setValue(MAXSIZE);
			}
			if (w < MINSIZE) {
				// set to minsize
				sb.setValue(MINSIZE);
			}
		}

	} // end

	class ObjBall {
		// placement of the ball
		int x = OFFSETX + 20;
		int y = OFFSETY + 20;
		// these are for speed
		int dx = 1;
		int dy = 1;
		// this is size
		int radius = 5;

		boolean rectangle = false;

		public ObjBall() {

		}

		public void reset() {
			 x = OFFSETX + 20;
			 y = OFFSETY + 20;
		}

		public void updatePos() {
			x += dx;
			y += dy;
		}

		public boolean isRectangle() {
			return rectangle;
		}

		public void setRectangle(boolean rectangle) {
			this.rectangle = rectangle;
		}

		// convience for drawing
		public void drawBall(Graphics g) {

			if (!rectangle) {
				g.drawOval(x + radius / 2, y + radius / 2, radius, radius);
			} else {
				g.drawRect(x + radius / 2, y + radius / 2, radius, radius);
			}
		}

		// convience functions for hiting stuff
		public void leftSide() {
			dy = -dy;
		}

		public void rightSide() {
			dy = -dy;
		}

		public void bottomSide() {
			dx = -dx;
		}

		public void topSide() {
			dx = -dx;
		}

		// gets and sets
		public int getX() {
			return x - radius;
		}

		public int getY() {
			return y - radius;
		}

		public void setRadius(int r) {
			radius = r;
		}

		public Rectangle getBoundingRectange() {

			return new Rectangle(x - radius, y - radius, radius * 2, radius * 2);

		}

		/*
		 * this is a absolute for pixel based jump speed direction is calculated
		 * from scaling the vector [dx dy] according to [1 1] aka WARNING: math
		 * ahead
		 */
		// TODO: Maths
		public void setSpeed(int s) {
			dx = s;
			dy = s;
		}

	}
}
