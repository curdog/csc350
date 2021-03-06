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
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Vector;

public class Bounce2 extends Applet implements ActionListener, MouseListener,
		AdjustmentListener, Runnable, MouseMotionListener {

	private static final long serialVersionUID = 10L;
	public static final int OFFSETX = 5;
	public static final int OFFSETY = 5;

	// points
	Point x1, x2;

	// scroll bars
	Scrollbar speed, size;

	// labels
	Label sizeLabel, speedLabel;

	// panels
	Panel control, sheet;

	// buttons
	Button start, quit, clear;

	// good = true, mouse on screen
	// good - false, mouse not on screen
	public static boolean good, drag;

	private Ballc ball;
	private int delay = 20;
	boolean p, q;

	// the thread
	private Thread theThread;

	public int getStartx() {
		String s = getParameter("startx");
		if (s != null) {
			return Integer.parseInt(s);
		}
		return 100;
	}

	public int getStarty() {
		String s = getParameter("starty");
		if (s != null) {
			return Integer.parseInt(s);
		}
		return 100;
	}

	public int getStartSize() {
		String s = getParameter("startsize");
		if (s != null) {
			return Integer.parseInt(s);
		}
		return 5;
	}

	public void init() {

		ball = new Ballc();
		ball.setRef(this);
		// pause, quit
		p = true;
		q = false;
		drag = false;
		good = false;
		// theThread = new Thread(this);

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
		speed = new Scrollbar(Scrollbar.HORIZONTAL, 2, 1, 1, 100);
		size = new Scrollbar(Scrollbar.HORIZONTAL, 2, 1, 1, 100);
		size.setMaximum(100);
		speed.setMaximum(50);

		size.setBlockIncrement(2);
		speed.setBlockIncrement(2);

		size.setMinimum(5);
		speed.setMinimum(2);

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

		// scrollbars
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

		// Thread.currentThread().setPriority(Thread.MAX_PRIORITY);

		while (more) {
			// if (!p) {
			// paint
			ball.repaint();
			// update, move ball
			if (!p) {
				ball.move();
			}

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

	}

	@Override
	public void adjustmentValueChanged(AdjustmentEvent e) {
		if (e.getSource().equals(speed)) {
			
			ball.setSpeed(speed.getValue());
		} else if (e.getSource().equals(size)) {
			ball.setRadius(size.getValue() / 2 + 1);
		}

	}

	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();

		// button selection
		if (source == start) {
			p = !p;
			if (p) {
				// pause the ball
				// theThread.suspend();
				start.setLabel("Start");
			} else {
				// start the ball
				// theThread.resume();
				start.setLabel("Stop");
			}

		} else if (source.equals(clear)) {

			ball.reset();
			p = true;
			// theThread.suspend();
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
		Point p = new Point(e.getPoint());
		ListIterator<Rectangle> i = ball.getRectangles().listIterator();

		while (i.hasNext()) {
			Rectangle rect = i.next();

			if (rect.contains(p)) {
				i.remove();
			}
		}
	}

	public void mouseDragged(MouseEvent e) {
		// if we are on the screen to draw
		if (good) {
			// we are dragging
			drag = true;
			// get x2 point
			x2 = e.getPoint();
		}
	}

	public void mouseEntered(MouseEvent e) {
		// mouse is on the screen to draw
		good = true;
	}

	public void mouseExited(MouseEvent e) {
		// mouse is not on screen to draw
		good = false;
	}

	public void mousePressed(MouseEvent e) {
		// if mouse is on the screen to draw
		// get x1 point
		if (x1 == null) {
			x1 = (Point) e.getPoint().clone();
			x2 = x1;
		} else
			x2 = (Point) e.getPoint().clone();

		System.out.println(e.getPoint().toString());
		drag = true;

	}

	public void mouseReleased(MouseEvent e) {
		// if mouse is on the screen to draw

		// done dragging
		drag = false;
		x2 = (Point) e.getPoint().clone();
		System.out.println(e.getPoint().toString());

		ball.addRectangle(returnx1(), returny1(), returnx2() - returnx1(),
				returny2() - returny1());

		x1 = null;
		x2 = null;

	}

	/************ RETURN VALUES TO DRAW ************/
	public int returnx1() {
		return (int) Math.min(x1.getX(), x2.getX());
	}

	public int returny1() {
		return (int) Math.min(x1.getY(), x2.getY());
	}

	public int returnx2() {
		return (int) Math.max(x2.getX(), x1.getX());
	}

	public int returny2() {
		return (int) Math.max(x2.getY(), x1.getY());
	}

}// end Bounce2

class Ballc extends Canvas {

	private static final long serialVersionUID = 1L;
	Image buffer;

	Vector<Rectangle> boxes;
	Bounce2 ref;

	public void setRef(Bounce2 ref) {
		this.ref = ref;
		this.radius = ref.getStartSize();
		this.x = ref.getStartx();
		this.y = ref.getStarty();
	}

	public Ballc() {
		super();
		boxes = new Vector<Rectangle>();
	}

	public void addRectangle(int x, int y, int width, int height) {
		Rectangle temp = new Rectangle(x, y, width, height);
		// check bounds and intersections
		if (!temp.intersects(getBoundingRectangle()))
			if (x > 5 && y > 5 && x + width < 890 && y + height < 480)
				boxes.add(temp);

		// check for dominatrix
		Iterator<Rectangle> r = boxes.iterator();
		while (r.hasNext()) {
			Rectangle rr = r.next();
			if (temp.contains(rr) && !temp.equals(rr)) {
				boxes.remove(rr);
			}
		}

	}

	public Vector<Rectangle> getRectangles() {
		return boxes;
	}

	public void paint(Graphics g) {
		if (buffer == null)
			buffer = createImage(900, 490);

		Graphics cg = buffer.getGraphics();

		// eliminate previous drawing;
		cg.clearRect(0, 0, 900, 490);

		if (Bounce2.drag) {
			cg.drawRect(ref.returnx1(), ref.returny1(),
					ref.returnx2() - ref.returnx1(),
					ref.returny2() - ref.returny1());
		}

		int height = 890;
		int width = 480;

		// draw ball
		cg.drawRect(5, 5, height, width);
		cg.drawOval(x - radius, y - radius, radius * 2, radius * 2);

		// draw obstacles
		for (int i = 0; i < boxes.size(); i++) {
			Rectangle d = boxes.get(i);
			cg.fillRect(d.x, d.y, d.width, d.height);
		}

		// buffer
		g.drawImage(buffer, 0, 0, this);
		Rectangle r = getBoundingRectangle();

		// check for intersections
		for (int i = 0; i < boxes.size(); i++) {
			Rectangle inter;
			if (boxes.get(i).intersects(r)) {
				inter = boxes.get(i).intersection(r);

				if (inter.width >= inter.height) {
					topSide();
				} else if (inter.height >= inter.width) {
					leftSide();
				}

			}
		}

		if ((int) r.getMaxX() >= height + Bounce2.OFFSETX + getRadius() - 1) {
			rightSide();
		}
		if ((int) r.getMinX() <= Bounce2.OFFSETX + getRadius() + 1) {
			leftSide();
		}
		if ((int) r.getMaxY() >= width + Bounce2.OFFSETY + getRadius() - 1) {
			bottomSide();
		}
		if ((int) r.getMinY() <= Bounce2.OFFSETY + getRadius() + 1) {
			topSide();
		}

	}// end paint

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

		return new Rectangle(x, y, radius * 2, radius * 2);
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

	public void wall(int dx, int dy) {
		x += dx;
		y += dy;
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
	int dx = 10;
	int dy = 10;
	// this is size
	int radius = 5;

	public void reset() {
		// reset the ball to the start position
		x = Bounce2.OFFSETX + 20;
		y = Bounce2.OFFSETY + 20;
		radius = 5;
		boxes.clear();
	}

	public void setSpeed(int s) {
		dx = s * dx / (int) Math.abs(dx);
		dy = s * dy / (int) Math.abs(dy);
	}

	public void setRadius(int r) {
		radius = r;
	}

}