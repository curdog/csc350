/* Laurel Miller, Sean Curtis, Kris Fielding
 * CET 350 - Java, Group 3
 * Program 6 - CannonVSBall
 * MIL1484, FIE4795, CUR3040
 */


package src;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.CheckboxMenuItem;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Label;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.MenuShortcut;
import java.awt.Panel;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.Scrollbar;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Timer;

import javax.swing.JOptionPane;
import javax.swing.plaf.OptionPaneUI;
import javax.swing.text.StyleContext.SmallAttributeSet;

public class CannonVSBall extends java.applet.Applet implements Runnable,
		AdjustmentListener, ActionListener, ItemListener, MouseListener {

	private static final long serialVersionUID = 1L;
	
	//main elements
	MenuBar menu;
	Button fire;
	Label fireLabel;
	Label angleLabel;
	Label velocLabel;
	Label computer;
	Label user;
	
	Scrollbar angle;
	Scrollbar velocity;
	Panel ControlPanel;
	GamePanelDraw GamePanel;
	GridBagLayout gblayout;
	GridBagConstraints gbc;
	
	Timer timeUnit;
	//TODO
	Label timeWasted;
	//sub elements
	Menu control, parameters, size, speed, env;
	MenuItem pause, run, restart;
	CheckboxMenuItem xsm_size, sm_size, med_size, lg_size, xlg_size, barn_size;
	CheckboxMenuItem mercuryp, venusp, earthp, marsp,
		jupiterp, saturnp, uranusp,
		neptunep, plutop, planet_xp;
	CheckboxMenuItem xslow, slow, avg, fast, xfast;
	
	//game values
	boolean gravity_en;
	float grav_const;
	float tar_velocity;
	float gun_velocity;
	
	Thread theThread;
	boolean p,q;
	//private Ballc ball;
	private int delay = 20;
	
	//for double buffering
	Image buffer;
	
	//constant values
	public static final float MERCURY 	= 3.59f;
	public static final float VENUS 	= 8.87f;
	public static final float EARTH 	= 9.81f;
	public static final float MARS 		= 3.77f;
	public static final float JUPITER 	= 25.95f;
	public static final float SATURN 	= 11.08f;
	public static final float URANUS 	= 10.67f;
	public static final float NEPTUNE 	= 14.07f;
	public static final float PLUTO 	= 0.42f;
	public static final float MOON 		= 1.62f;
	public static final float PLANET_X	= -3.14f;
	
	//angle boundaries, in degrees
	private final int MaxAng = 90;
	private final int MinAng = 1;
	
	//velocity boundaries, in m/sec
	private final int MaxVel = 363;
	private final int MinVel = 30;
	
	public void init(){
		//pause, quit
		p = true;
		q = false;
		
		
		setLayout(new BorderLayout());
		setVisible(true);
		
		//Menu Bar
		menu = new MenuBar();
		menuSetup();
		
		Object f = getParent();
		
		while (!(f instanceof Frame))
			f = ((Component) f).getParent();
		((Frame) f).setMenuBar( menu );
		
		while (!(f instanceof Frame))
			f = ((Component) f).getParent();
		((Frame) f).setTitle("Cannon vs Ball");;
		//listen
		
		
		//show
		setControlPanel();
		setGamePanel();
		
		//add listeners
		fire.addActionListener(this);
		angle.addAdjustmentListener(this);
		velocity.addAdjustmentListener(this);
		
		add(ControlPanel, BorderLayout.SOUTH);
		add(GamePanel, BorderLayout.CENTER);
		GamePanel.addMouseListener(this);
		
		//ControlPanel.setLayout(gblayout);
	}
	
	private void setGamePanel() {
		GamePanel = new GamePanelDraw();
		GamePanel.setVisible(true);
		GamePanel.setLayout(new BorderLayout(0,0));
		GamePanel.setSize(900,400);
		//ball.setSize(900,490);
		//GamePanel.add("Center",ball);
		GamePanel.setBackground(Color.WHITE);
	}

	
	private void setControlPanel() {
		
		ControlPanel = new Panel();
			
		
		angle = new Scrollbar(Scrollbar.HORIZONTAL, 0, 1, 0, 91);
		velocity = new Scrollbar(Scrollbar.HORIZONTAL, 0, 1,MinVel,MaxVel);
		
		int scrollHeightd = 900;
		
		angle.setSize(100, 15);
		velocity.setSize(100, 15);
		
		angle.setValue(45);
		velocity.setValue(34);
		
		angle.setVisible(true);
		velocity.setVisible(true);
		

	//	ControlPanel.add(angle);
	//	ControlPanel.add(velocity);

		
		gbc = new GridBagConstraints();
		gblayout = new GridBagLayout();
		
		ControlPanel.setVisible(true);
		ControlPanel.setLayout(gblayout);

		
		//labels
		angleLabel = new Label("45");
		velocLabel = new Label("400");
		
		//fire button
		fire = new Button("Fire");
		
		/********** SETTING UP THE GUI **********/
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		ControlPanel.add(angleLabel,gbc);
		
		//buttons
		gbc.gridx = 3;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		ControlPanel.add(fire,gbc);
		
		//scroll bars
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.gridwidth = 2;
		gbc.ipadx=50;
		
		ControlPanel.add(angle,gbc);
		
		gbc.gridx = 5;
		gbc.gridy = 0;

		ControlPanel.add(velocity, gbc);
		
	}

	public void menuSetup(){
		menu.add(control = new Menu("Control"));
		menu.add(size = new Menu("Size"));
		menu.add(speed = new Menu("Speed"));
		menu.add(env = new Menu ("Environment"));
		
		control.add(pause = new MenuItem("Pause", 
				new MenuShortcut( KeyEvent.getExtendedKeyCodeForChar('P'))));
		control.add(run = new MenuItem("Run", 
				new MenuShortcut( KeyEvent.getExtendedKeyCodeForChar('R'))));
		
		control.addSeparator();
		
		control.add(restart = new MenuItem("Restart", 
				new MenuShortcut( KeyEvent.getExtendedKeyCodeForChar('O'))));
		
		size.add(xsm_size = (new CheckboxMenuItem("X-Small")));
		xsm_size.addItemListener(this);
		size.add(sm_size = new CheckboxMenuItem("Small"));
		sm_size.addItemListener(this);
		size.add(med_size = new CheckboxMenuItem("Medium"));
		med_size.addItemListener(this);
		size.add(lg_size = new CheckboxMenuItem("Large"));
		lg_size.addItemListener(this);
		size.add(xlg_size = new CheckboxMenuItem("X-Large"));
		xlg_size.addItemListener(this);
		size.add(barn_size = new CheckboxMenuItem("Barn"));
		barn_size.addItemListener(this);
		
		env.add(mercuryp = new CheckboxMenuItem("Mercury"));
		mercuryp.addItemListener(this);
		env.add(venusp = new CheckboxMenuItem("Venus"));
		venusp.addItemListener(this);
		env.add(marsp = new CheckboxMenuItem("Mars"));
		marsp.addItemListener(this);
		env.add(earthp = new CheckboxMenuItem("Earth"));
		earthp.addItemListener(this);
		env.add(jupiterp = new CheckboxMenuItem("Jupiter"));
		jupiterp.addItemListener(this);
		env.add(saturnp = new CheckboxMenuItem("Saturn"));
		saturnp.addItemListener(this);
		env.add(uranusp = new CheckboxMenuItem("Uranus"));
		uranusp.addItemListener(this);
		env.add(neptunep = new CheckboxMenuItem("Neptune"));
		neptunep.addItemListener(this);
		env.add(plutop = new CheckboxMenuItem("Pluto"));
		plutop.addItemListener(this);
		
		speed.add(xslow = new CheckboxMenuItem("X-Slow"));
		xslow.addItemListener(this);
		speed.add(slow = new CheckboxMenuItem("Slow"));
		slow.addItemListener(this);
		speed.add(avg = new CheckboxMenuItem("Average"));
		avg.addItemListener(this);
		speed.add(fast = new CheckboxMenuItem("Fast"));
		fast.addItemListener(this);
		speed.add(xfast = new CheckboxMenuItem("X-Fast"));
		xfast.addItemListener(this);
	}
	
	public void start(){
		if(theThread == null){
			//create thread
			theThread = new Thread(this);
			theThread.start();
		}
		
	}
	
	private boolean more = true;
	
	public void update(Graphics g){
		
		paint( g );
	}
	
	public void paint( Graphics g){
		if(buffer == null)
			buffer = createImage(900,490);
		//Graphics gpg = GamePanel.getGraphics();
		int height = 890;
		int width = 480;
		
		//draw frame
		//gpg.drawRect(5,5,height,width);
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		
		// button selection
				if (source == "Quit") {
					q = true;
					theThread.stop();;
					System.exit(0);
					
				}else if(source == "Run"){
					
					if(p){
						p = false;
					}
					
				}else if(source == "Start"){
					
					if(p){
						p = false;
					}
				}else if(source == "Pause"){
					if(!p){
						p = true;
					}
				} else if (source.equals(fire) ){
					GamePanel.bullet.fire();
				}
				
				repaint();
				this.validate();
}
	
	public void stop(){
		//remove listeners
		angle.removeAdjustmentListener(this);
		velocity.removeAdjustmentListener(this);
		GamePanel.removeMouseListener(this);
		GamePanel.removeMouseListener(this);
		fire.removeActionListener(this);
		
		//kill the thread
		more = false;
	}

	@Override
	public void adjustmentValueChanged(AdjustmentEvent e) {
		Scrollbar sb = (Scrollbar) e.getSource();

		// get angle value
		if (sb == angle) {
			GamePanel.setAngle(angle.getValue());
			GamePanel.bullet.setSpeed( velocity.getValue(), angle.getValue());
			angleLabel.setText("A:"+ angle.getValue());
		}

		// get velocity value
		if (sb == velocity) {
			GamePanel.bullet.setSpeed( velocity.getValue(), angle.getValue());
			velocLabel.setText("V: " + velocity.getValue());
		}
	}

	/*
	 * For rendering the game,
	 */
	public void run() {

		long s;
		long e;
		while (true == true) {
			if (p) {
				s = System.currentTimeMillis();
				GamePanel.bullet.updateProjectile();
				GamePanel.tar.updateTarget();
				GamePanel.collision();
				GamePanel.repaint();
				e = System.currentTimeMillis();
				
				//adjust for sucky vs super computer
				long adj = 33 - (e - s);
				if( adj < 0){
					adj = 0;
				}
				try {
					Thread.sleep(adj);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}

			}
		}
	}
	

	@Override
	public void itemStateChanged(ItemEvent e) {
		Object o = e.getSource();

		//do not allow more than one check box to be selected for size
		if(o == xsm_size){
			GamePanel.tar.setDiameter(10);
			sm_size.setState(false);
			med_size.setState(false);
			lg_size.setState(false);
			xlg_size.setState(false);
			barn_size.setState(false);
		}else if(o == sm_size){
			GamePanel.tar.setDiameter(20);
			xsm_size.setState(false);
			med_size.setState(false);
			lg_size.setState(false);
			xlg_size.setState(false);
			barn_size.setState(false);
		}else if(o == med_size){
			GamePanel.tar.setDiameter(40);
			xsm_size.setState(false);
			sm_size.setState(false);
			lg_size.setState(false);
			xlg_size.setState(false);
			barn_size.setState(false);
		}else if(o == lg_size){
			GamePanel.tar.setDiameter(80);
			xsm_size.setState(false);
			sm_size.setState(false);
			med_size.setState(false);
			xlg_size.setState(false);
			barn_size.setState(false);
		}else if(o == xlg_size){
			GamePanel.tar.setDiameter(120);
			xsm_size.setState(false);
			sm_size.setState(false);
			lg_size.setState(false);
			med_size.setState(false);
			barn_size.setState(false);
		}else if( o == barn_size){
			GamePanel.tar.setDiameter(200);
			xsm_size.setState(false);
			sm_size.setState(false);
			lg_size.setState(false);
			med_size.setState(false);
			xlg_size.setState(false);
		}
		
		//do not allow more than one check box to be selected for the environment
		if(o == mercuryp){
			GamePanel.bullet.setAccel(MERCURY);
			venusp.setState(false);
			earthp.setState(false);
			plutop.setState(false);
			uranusp.setState(false);
			jupiterp.setState(false);
			marsp.setState(false);
			saturnp.setState(false);
			neptunep.setState(false);
			GamePanel.bullet.setAccel(MERCURY);
		}else if(o == venusp){
			GamePanel.bullet.setAccel(VENUS);
			mercuryp.setState(false);
			earthp.setState(false);
			plutop.setState(false);
			uranusp.setState(false);
			jupiterp.setState(false);
			marsp.setState(false);
			saturnp.setState(false);
			neptunep.setState(false);
			GamePanel.bullet.setAccel(VENUS);
		}else if(o == earthp){
			GamePanel.bullet.setAccel(EARTH);
			venusp.setState(false);
			mercuryp.setState(false);
			plutop.setState(false);
			uranusp.setState(false);
			jupiterp.setState(false);
			marsp.setState(false);
			saturnp.setState(false);
			neptunep.setState(false);
			GamePanel.bullet.setAccel(EARTH);
		}else if(o == plutop){
			GamePanel.bullet.setAccel(PLUTO);
			venusp.setState(false);
			earthp.setState(false);
			mercuryp.setState(false);
			uranusp.setState(false);
			jupiterp.setState(false);
			marsp.setState(false);
			saturnp.setState(false);
			neptunep.setState(false);
			GamePanel.bullet.setAccel(PLUTO);
		}else if( o == uranusp){
			GamePanel.bullet.setAccel(URANUS);
			venusp.setState(false);
			earthp.setState(false);
			plutop.setState(false);
			mercuryp.setState(false);
			jupiterp.setState(false);
			marsp.setState(false);
			saturnp.setState(false);
			neptunep.setState(false);
			GamePanel.bullet.setAccel(URANUS);
		}else if(o == jupiterp){
			GamePanel.bullet.setAccel(JUPITER);
			venusp.setState(false);
			earthp.setState(false);
			plutop.setState(false);
			uranusp.setState(false);
			mercuryp.setState(false);
			marsp.setState(false);
			saturnp.setState(false);
			neptunep.setState(false);
			GamePanel.bullet.setAccel(JUPITER);
		}else if(o == marsp){
			GamePanel.bullet.setAccel(MARS);
			venusp.setState(false);
			earthp.setState(false);
			plutop.setState(false);
			uranusp.setState(false);
			jupiterp.setState(false);
			mercuryp.setState(false);
			saturnp.setState(false);
			neptunep.setState(false);
			GamePanel.bullet.setAccel(MARS);
		}else if(o == saturnp){
			GamePanel.bullet.setAccel(SATURN);
			venusp.setState(false);
			earthp.setState(false);
			plutop.setState(false);
			uranusp.setState(false);
			jupiterp.setState(false);
			marsp.setState(false);
			mercuryp.setState(false);
			neptunep.setState(false);	
			GamePanel.bullet.setAccel(SATURN);
		}else if(o == neptunep){
			GamePanel.bullet.setAccel(NEPTUNE);
			venusp.setState(false);
			earthp.setState(false);
			plutop.setState(false);
			uranusp.setState(false);
			jupiterp.setState(false);
			marsp.setState(false);
			saturnp.setState(false);
			mercuryp.setState(false);
			GamePanel.bullet.setAccel(NEPTUNE);
		}
		
		//do not allow more than one speed to be selected 
		if(o == xslow){
			GamePanel.tar.setSpeed(1);
			slow.setState(false);
			avg.setState(false);
			fast.setState(false);
			xfast.setState(false);
		}else if(o == slow){
			GamePanel.tar.setSpeed(10);
			xslow.setState(false);
			avg.setState(false);
			fast.setState(false);
			xfast.setState(false);
		}else if(o == avg){
			GamePanel.tar.setSpeed(20);
			slow.setState(false);
			xslow.setState(false);
			fast.setState(false);
			xfast.setState(false);
		}else if(o == fast){
			GamePanel.tar.setSpeed(50);
			slow.setState(false);
			avg.setState(false);
			xslow.setState(false);
			xfast.setState(false);
		}else if(o == xfast){
			GamePanel.tar.setSpeed(120);
			slow.setState(false);
			avg.setState(false);
			fast.setState(false);
			xslow.setState(false);
		}
	}

	public void mouseClicked(MouseEvent arg0) {
		
	}
	public void mouseEntered(MouseEvent arg0) {}
	public void mouseExited(MouseEvent arg0) {}
	public void mousePressed(MouseEvent arg0) {}
	public void mouseReleased(MouseEvent arg0) {
		if( arg0.getX() > 560 && arg0.getY() > 340 ){
			GamePanel.bullet.fire();
		}
	}

}

class GamePanelDraw extends Panel{
	public static final int height = 400;
	public static final int width = 600;
	
	// these are for speed
	int dx = 10;
	int dy = 10;
	
	//canon vars
	int angle;
	boolean recalc;
	public void setAngle( int angle){
		this.angle = angle;
		bullet.setSpeed(100, angle);
		recalc = true;
	}
	
	public Projectile bullet;
	public Target tar;
	
	public GamePanelDraw(){
		super();
		
		tar = new Target();
		bullet = new Projectile();
		//cannon
		cannonPoints = new Point[4];
		cannonPoints[3] = new Point( width - 60, height );
		cannonPoints[1] = new Point( width - 60, height-10 );
		cannonPoints[2] = new Point( width , height -10 );
		cannonPoints[0] = new Point( width, height); 
		
		angle = 70;
		recalc = true;
		drawCannonPoints = new Point[4];
		
		for( int i = 0; i < cannonPoints.length; i++){
			drawCannonPoints[i] = cannonPoints[i];
		}
		
	}
	
	
	public void collision(){
		if( bullet.getBoundingRectangle().intersects( tar.getBoundingRectangle() )){
			rscore++;
			tar.reset();
			bullet.reset();
		}
		if( tar.getBoundingRectangle().intersects( new Rectangle(width-40, height-40, 40, 40))){
			cscore++;
			JOptionPane.showMessageDialog(null, "You loose, try x-slow and barn-size next time", "Matrix wins", JOptionPane.INFORMATION_MESSAGE);
			tar.reset();
			bullet.reset();
		}
	}
	
	Image gi;
	public void paint(Graphics gb){
		gi = createImage(width+1, height+1);
		Graphics g = gi.getGraphics();
		g.drawRect(0, 0, width, height);
		
		bullet.paintProjectile(g);
		drawCannon(g);
		tar.paintTarget(g);
		drawScore(g);
		
		gb.drawImage(gi,0,0,getBackground(),this);
		
		
	}
	
	public void update(Graphics g){
		paint(g);
	}
	Point[] cannonPoints;
	Point[] drawCannonPoints;
	
	int cscore = 0;
	int rscore = 0;
	
	public void reset(){
		cscore = 0;
		rscore = 0;
	}
	
	public void drawScore( Graphics g){
		Color c = g.getColor();
		g.setColor(Color.BLUE);
		g.drawString("Computer Score: " + cscore, width - 140, 20);
		g.drawString("The Rock Score: " + rscore, width - 140, 40);
		g.setColor(c);
	}
	
	public void drawCannon( Graphics g){
		//base
		int cannon_offset = 20;
		g.fillOval(width-cannon_offset*2, height - cannon_offset*2, cannon_offset*2, cannon_offset*2);
		g.fillRect(width-cannon_offset, height-cannon_offset, cannon_offset, cannon_offset);
		//cannon
		//lazy calculations
		if( recalc == true){
			float innerRad = 10;
			float outerRad = 100;
			drawCannonPoints[0] = new Point( width - (int)(innerRad * Math.cos( (double)((angle - 2.5f)) * Math.PI/180) ),
						height - (int)(innerRad * Math.sin( (double)((angle - 2.5f)) * Math.PI/180 )));
			
			drawCannonPoints[1] = new Point(width - (int)(innerRad * Math.cos( (double)((angle + 2.5f)) * Math.PI/180) ),
					height - (int)(innerRad * Math.sin( (double)((angle + 2.5f)) * Math.PI/180 )));
		
			drawCannonPoints[2] = new Point(width - (int)(outerRad * Math.cos( (double)((angle + 2.5f)) * Math.PI/180) ),
					height - (int)(outerRad * Math.sin( (double)((angle + 2.5f)) * Math.PI/180 )));
			
			drawCannonPoints[3] = new Point(width -  (int)(outerRad * Math.cos( (double)((angle - 2.5f)) * Math.PI/180) ),
					height - (int)(outerRad * Math.sin( (double)( (angle - 2.5f)) * Math.PI/180 )));
			
			recalc = false;
		}
		
		Polygon p = new Polygon();
		for( int i = 0; i < drawCannonPoints.length; i++){
			p.addPoint((int)drawCannonPoints[i].getX(), (int)drawCannonPoints[i].getY());
		}
		Color old = g.getColor();
		g.setColor( new Color(0,0,51));
		g.fillPolygon(p);
		g.setColor(old);
		
	}
	
	
}

class Projectile{
	float x = GamePanelDraw.width - SIZE;
	float y = GamePanelDraw.height - SIZE/2;
	float dx =0.0f;
	float dy = 0.0f;
	float rdx = 0.0f;
	float rdy = 0.0f;
	float accel = 1.0f;
	float timeStep = 0.03f;
	public static final int SIZE = 10;
	public Projectile(){
		
	}
	
	public void reset(){
		x = GamePanelDraw.width - SIZE;
		y = GamePanelDraw.height - SIZE/2;
		dx =0.0f;
		  dy = 0.0f;
		  rdx = 0.0f;
		  rdy = 0.0f;  
		  timeStep = 0.03f;
		fire = false;
		
		
	}
	
	public void setTimeStep( float t){
		
	}
	
	public void setAccel( float a){
		accel = a;
	}
	
	public void setSpeed( float s, float ang){
		System.out.println(s);
		System.out.println(ang);
		dx = (-s) * Math.abs( (float)Math.cos((double)ang * Math.PI / 180));
		
		dy = (-s) * Math.abs((float)Math.sin((double)ang * Math.PI /180));
		System.out.println( dx);
		System.out.println( dy);
	}
	
	public Rectangle getBoundingRectangle(){
		return new Rectangle( (int)x, (int)y,SIZE,SIZE );
	}
	
	public void paintProjectile( Graphics g){
		Color oldColor = g.getColor();
		g.setColor(Color.DARK_GRAY);
		g.fillOval((int)x, (int)y, SIZE, SIZE/2);
		g.setColor(oldColor);
	}
	
	
	boolean fire = false;
	public void fire(){
		rdx = dx;
		rdy = dy;
		fire = true;
	}
	
	public void updateProjectile(){
		if(fire ){
			y+= rdy;
			rdy += accel * timeStep;
			x += rdx;
			System.out.println( x );
		}
		
		if( x < 0 || y > 410){
			System.out.println("R");
			reset();
			JOptionPane.showMessageDialog(null, "Ball Left Play", "Wear Glasses", JOptionPane.INFORMATION_MESSAGE);
		}
		
	}
}

class Target {
	
	
	public void paintTarget( Graphics g){
		Color oldColor = g.getColor();
		g.setColor(Color.RED);
		g.fillOval(x, y, diameter, diameter);
		g.setColor(Color.BLACK);
		g.drawOval( x,y,diameter,diameter);
		g.fillOval(x + diameter/4+1, y +diameter/4, diameter/2, diameter/2);
		g.setColor(oldColor);
	}
	
	public void updateTarget(){
		x+=dx;
		y+=dy;
		
		if( x < 0 || x > 600 - diameter ){
			dx = -dx;
		}
		if( y < 0 || y > 400 - diameter){
			dy = - dy;
		}
	}

	public Rectangle getBoundingRectangle(){
		return new Rectangle(x, y, diameter, diameter);
	}
	
	// placement of the ball
	int x =  20;
	int y =  20;
	// these are for speed
	int dx = 1;
	int dy = 1;
	// this is size
	int diameter = 20;

	public void reset() {
		// reset the ball to the start position
		x =  20;
		y =  20;
		
		
	}

	public void setSpeed(int s) {
		dx = s * dx / (int) Math.abs(dx);
		dy = s * dy / (int) Math.abs(dy);
	}

	public void setDiameter(int d) {
		diameter = d;
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


}
