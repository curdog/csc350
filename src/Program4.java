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

	private static final int SPEED = 1;

	private static final int MAXSIZE = 100;

	private static final int MINSIZE = 0;

	private static final int SIZE = 1;

	public static final int OFFSETX = 100;
	public static final int OFFSETY = 25;

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

	private int sliderW = 100;

	private int sliderH = 25;

	public static void main(String[] args) {
		System.out.println("This is an applet");
	}

	public void init() {
		// obj = new ObjBall (page, getBackground(), Wobj, Hobj, WIDTH, HEIGHT,
		// MAXSPEED, currentspeed, pause, step);

		obj = new ObjBall();
		ballmover = new Thread(this);
		setLayout(null);
		setVisible(true);
		setBackground(Color.white);

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
		speed = new Scrollbar(Scrollbar.HORIZONTAL, 0, 1, 1, 100);
		
		speed.setMaximum(MAXSPEED);
		speed.setMinimum(MINSPEED);
		speed.setUnitIncrement(SPEED);
		speed.setBlockIncrement( 1);
		speed.setValue(0);
		speed.setBackground(Color.GRAY);
		speed.setSize(sliderW, sliderH);
		speed.setLocation(200, 640);
		speed.setEnabled(true);
		
		speed.addAdjustmentListener(this);
		speed.setVisible(true);

		this.add(speed);

		// size scroll bar
		size = new Scrollbar(Scrollbar.HORIZONTAL, 0, 1, 1, 100);

		size.setMaximum(MAXSIZE);
		size.setMinimum(MINSIZE);
		size.setUnitIncrement(SIZE);
		size.setBlockIncrement( 1);
		size.setValue(0);
		size.setBackground(Color.GRAY);
		size.setSize(sliderW, sliderH);
		size.setLocation(200, 675);
		size.setEnabled(true);
		size.addAdjustmentListener(this);
		size.setVisible(true);
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

		// Obj.Start;
		validate();

		pause = true;
		exit = false;

		//add listeners
		startstop.addActionListener(this);
		clear.addActionListener(this);
		quit.addActionListener(this);
		tails.addActionListener(this);
		rect.addActionListener(this);
	}

	Rectangle bounds;

	// draws the frame for the bouncing ball
	public void paint(Graphics g) {
		int height = 900;
		int width = 490;
		Rectangle r = obj.getBoundingRectange();
		g.drawRect(OFFSETX, OFFSETY, height, width);
		
		boolean cheat = false;
		
		if( (int)r.getMaxX() >= height){
			obj.rightSide();
			cheat = true;
		}
		if( (int)r.getMinX() <= OFFSETX ){
			obj.leftSide();
			cheat = true;
		}
		if( (int)r.getMaxY() >= width){
			obj.bottomSide();
			cheat = true;
		}
		if( (int)r.getMinY() <= OFFSETY ){
			obj.topSide();
			cheat = true;
		}
		if( !cheat)
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
		//infinite loop
		while (1 == 1) {
			obj.updatePos();
			repaint();

			//thread
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	public void actionPerformed(ActionEvent e) {

		Object source = e.getSource();
		
		//button selection
		if (source == startstop) {
			pause = !pause;
			if (pause) {
				//pause the ball
				ballmover.suspend();
				startstop.setLabel("Start");
			} else {
				//start the ball
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
				//set button label
				obj.setRectangle(false);
				rect.setLabel("Rectangle");
			}
			repaint();
		} else if ( source.equals(tails) ){
			//draw tails
			if (tails.getLabel().equals("Tails")) {
				obj.setDrawTails(true);
				//change button label
				tails.setLabel("No Tails");
			} else {
				obj.setDrawTails(false);
				//change button label
				tails.setLabel("Tails");
			}
			repaint();
			
		}

		this.validate();
	}

	public void adjustmentValueChanged(AdjustmentEvent e) {
		int spfac = 100;
		int sifac = 100;

		if( e.getSource().equals(speed) ){
			//get speed value
			obj.setSpeed(speed.getValue());
		} else if ( e.getSource().equals(size)){
			//get size
			obj.setRadius(size.getValue()/2 + 1);
		}

	}

	class ObjBall {
		// placement of the ball
		int x = OFFSETX + 20;
		int y = OFFSETY + 20;
		// these are for speed
		int dx = 1;
		int dy = 1;
		// this is size
		int radius = 5;
		
		Rectangle[] tails;

		boolean rectangle = false;
		boolean drawTails = false;
		int t =0;
		int numTails = 500;
		public ObjBall() {
			tails = new Rectangle[numTails];
		}

		public void reset() {
			x = OFFSETX + 20;
			y = OFFSETY + 20;
			tails = new Rectangle[numTails];
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
		

		// convenice for drawing
		public void drawBall(Graphics g) {
			
			if (!rectangle) {
				g.drawOval(x + radius / 2, y + radius / 2, radius*2, radius*2);
			} else {
				g.drawRect(x + radius / 2, y + radius / 2, radius*2, radius*2);
			}
			//draw tails, for the furries
			if( drawTails){
				//save current
				tails[t % numTails] = new Rectangle(x + radius / 2, y + radius / 2, radius*2, radius*2);
				//draw rest
				for(int i = 0; i < tails.length; i++ ){
					if( tails[i] != null){
						if (!rectangle) {
							//draw oval
							g.drawOval(tails[i].x, tails[i].y, tails[i].width, tails[i].height);
						} else {
							//draw rect
							g.drawRect(tails[i].x, tails[i].y, tails[i].width, tails[i].height);
						}
						//g.drawRect( tails[i].x, tails[i].y, tails[i].width, tails[i].height);
					}
				}
				t++;
			}
		}

		// convience functions for hiting stuff
		public void leftSide() {
			dx = -dx;
		}

		public boolean isDrawTails() {
			return drawTails;
		}

		public void setDrawTails(boolean drawTails) {
			this.drawTails = drawTails;
			if( drawTails == false){
				tails = null;
				tails= new Rectangle[numTails];
			}
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
