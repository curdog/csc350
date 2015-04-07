/* Laurel Miller, Sean Curtis, Kris Fielding
 * CET 350 - Java, Group 3
 * Program 6 - CannonVSBall
 * MIL1484, FIE4795, CUR3040
 */


//package src;

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
	
	//velocity boundaries, in feet/sec
	private final int MaxVel = 1210;
	private final int MinVel = 100;
	
	public void init(){
		//pause, quit
		p = true;
		q = false;
		
		//ball = new Ballc();
		
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
			
		gbc = new GridBagConstraints();
		gblayout = new GridBagLayout();
		
		ControlPanel.setVisible(true);
		ControlPanel.setLayout(gblayout);
		
		angle = new Scrollbar(Scrollbar.HORIZONTAL, 45, 1, 0, 90);
		velocity = new Scrollbar(Scrollbar.HORIZONTAL, 400, 100,100,1200);
		
		/*
		angle.setBlockIncrement(1);
		velocity.setBlockIncrement(1);
		
		angle.setMinimum(5);
		velocity.setMinimum(2);
		
		angle.setUnitIncrement(1);
		velocity.setUnitIncrement(1);
		*/
		angle.setSize(100,25);
		velocity.setSize(100,25);
		
		angle.setVisible(true);
		velocity.setVisible(true);
		angle.setEnabled(true);
		velocity.setEnabled(true);
		
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
		angle.setMaximum(MaxAng);
		angle.setMinimum(MinAng);
		ControlPanel.add(angle,gbc);
		
		gbc.gridx = 5;
		gbc.gridy = 0;
		gbc.gridwidth = 2;
		velocity.setMaximum(MaxVel);
		velocity.setMaximum(MaxVel);
		ControlPanel.add(velocity, gbc);
		
		
	}

	public void menuSetup(){
		menu.add(control = new Menu("Control"));
		menu.add(size = new Menu("Size"));
		menu.add(speed = new Menu("Speed"));
		menu.add(env = new Menu ("Environment"));
		/*	
		menu.add(parameters);
		menu.add(size);
		
		*/
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
	
	public void render( ){
		
	}
	
	public void model(){
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		
		// button selection
				if (source == "Quit") {
					q = true;
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
		
		//get angle value
		if(sb == angle){
			int A = e.getValue();
			
			if(A > 90){
				sb.setValue(MaxAng);
			}			
			
			if(A < 90){
				sb.setValue(MinAng);
			}
		}
		
		//get velocity value
		if(sb == velocity){
			int V = e.getValue();
			
			if(V > MaxVel){
				sb.setValue(MaxVel);
			}
			
			if(V < MinVel){
				sb.setValue(MinVel);
			}
		}
}
	/*
	 * For rendering the game, 
	 * 
	 */
	public void run() {
	/*	while(more){
			ball.repaint();
			
			if(!p){
				ball.move();
			}
			
			try{
				theThread.sleep(delay);
			} catch (InterruptedException e){}
		}
*/
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		Object o = e.getSource();

		//do not allow more than one check box to be selected for size
		if(o == xsm_size){
			sm_size.setState(false);
			med_size.setState(false);
			lg_size.setState(false);
			xlg_size.setState(false);
			barn_size.setState(false);
		}else if(o == sm_size){
			xsm_size.setState(false);
			med_size.setState(false);
			lg_size.setState(false);
			xlg_size.setState(false);
			barn_size.setState(false);
		}else if(o == med_size){
			xsm_size.setState(false);
			sm_size.setState(false);
			lg_size.setState(false);
			xlg_size.setState(false);
			barn_size.setState(false);
		}else if(o == lg_size){
			xsm_size.setState(false);
			sm_size.setState(false);
			med_size.setState(false);
			xlg_size.setState(false);
			barn_size.setState(false);
		}else if(o == xlg_size){
			xsm_size.setState(false);
			sm_size.setState(false);
			lg_size.setState(false);
			med_size.setState(false);
			barn_size.setState(false);
		}else if( o == barn_size){
			xsm_size.setState(false);
			sm_size.setState(false);
			lg_size.setState(false);
			med_size.setState(false);
			xlg_size.setState(false);
		}
		
		//do not allow more than one check box to be selected for the environment
		if(o == mercuryp){
			venusp.setState(false);
			earthp.setState(false);
			plutop.setState(false);
			uranusp.setState(false);
			jupiterp.setState(false);
			marsp.setState(false);
			saturnp.setState(false);
			neptunep.setState(false);
		}else if(o == venusp){
			mercuryp.setState(false);
			earthp.setState(false);
			plutop.setState(false);
			uranusp.setState(false);
			jupiterp.setState(false);
			marsp.setState(false);
			saturnp.setState(false);
			neptunep.setState(false);	
		}else if(o == earthp){
			venusp.setState(false);
			mercuryp.setState(false);
			plutop.setState(false);
			uranusp.setState(false);
			jupiterp.setState(false);
			marsp.setState(false);
			saturnp.setState(false);
			neptunep.setState(false);	
		}else if(o == plutop){
			venusp.setState(false);
			earthp.setState(false);
			mercuryp.setState(false);
			uranusp.setState(false);
			jupiterp.setState(false);
			marsp.setState(false);
			saturnp.setState(false);
			neptunep.setState(false);	
		}else if( o == uranusp){
			venusp.setState(false);
			earthp.setState(false);
			plutop.setState(false);
			mercuryp.setState(false);
			jupiterp.setState(false);
			marsp.setState(false);
			saturnp.setState(false);
			neptunep.setState(false);	
		}else if(o == jupiterp){
			venusp.setState(false);
			earthp.setState(false);
			plutop.setState(false);
			uranusp.setState(false);
			mercuryp.setState(false);
			marsp.setState(false);
			saturnp.setState(false);
			neptunep.setState(false);	
		}else if(o == marsp){
			venusp.setState(false);
			earthp.setState(false);
			plutop.setState(false);
			uranusp.setState(false);
			jupiterp.setState(false);
			mercuryp.setState(false);
			saturnp.setState(false);
			neptunep.setState(false);	
		}else if(o == saturnp){
			venusp.setState(false);
			earthp.setState(false);
			plutop.setState(false);
			uranusp.setState(false);
			jupiterp.setState(false);
			marsp.setState(false);
			mercuryp.setState(false);
			neptunep.setState(false);	
		}else if(o == neptunep){
			venusp.setState(false);
			earthp.setState(false);
			plutop.setState(false);
			uranusp.setState(false);
			jupiterp.setState(false);
			marsp.setState(false);
			saturnp.setState(false);
			mercuryp.setState(false);	
		}
		
		//do not allow more than one speed to be selected 
		if(o == xslow){
			slow.setState(false);
			avg.setState(false);
			fast.setState(false);
			xfast.setState(false);
		}else if(o == slow){
			xslow.setState(false);
			avg.setState(false);
			fast.setState(false);
			xfast.setState(false);
		}else if(o == avg){
			slow.setState(false);
			xslow.setState(false);
			fast.setState(false);
			xfast.setState(false);
		}else if(o == fast){
			slow.setState(false);
			avg.setState(false);
			xslow.setState(false);
			xfast.setState(false);
		}else if(o == xfast){
			slow.setState(false);
			avg.setState(false);
			fast.setState(false);
			xslow.setState(false);
		}
	}

	public void mouseClicked(MouseEvent arg0) {}
	public void mouseEntered(MouseEvent arg0) {}
	public void mouseExited(MouseEvent arg0) {}
	public void mousePressed(MouseEvent arg0) {}
	public void mouseReleased(MouseEvent arg0) {}

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
		recalc = true;
	}
	
	public GamePanelDraw(){
		super();
		
		cannonPoints = new Point[4];
		cannonPoints[3] = new Point( width - 60, height );
		cannonPoints[1] = new Point( width - 60, height-10 );
		cannonPoints[2] = new Point( width , height -10 );
		cannonPoints[0] = new Point( width, height); 
		
		angle = 10;
		recalc = true;
		drawCannonPoints = new Point[4];
		
		for( int i = 0; i < cannonPoints.length; i++){
			drawCannonPoints[i] = cannonPoints[i];
		}
		
	}
	
	
	
	public void paint(Graphics g){
		g.drawRect(12, 12, 12, 12);
		
		g.drawRect(0, 0, width, height);
		drawCannon(g);
		
	}
	
	Point[] cannonPoints;
	Point[] drawCannonPoints;
	public void drawCannon( Graphics g){
		//base
		int cannon_offset = 20;
		g.fillOval(width-cannon_offset*2, height - cannon_offset*2, cannon_offset*2, cannon_offset*2);
		//cannon
		//lazy calculations
		
		//  x/y + w*sin/cos 
		if( recalc == true){
			for( int i = 0; i < cannonPoints.length; i++){
				drawCannonPoints[i] = new Point( (int)(cannonPoints[i].x * Math.cos((double)angle)+cannonPoints[i].y*Math.sin((double)(angle) )),
						(int)(-cannonPoints[i].x * Math.sin((double)angle) + cannonPoints[i].y*Math.cos((double)angle)));
				System.out.println("Translated X" + drawCannonPoints[i].x);
				System.out.println("Translated Y" + drawCannonPoints[i].y);
			}
			recalc = false;
		}
		
		Polygon p = new Polygon();
		for( int i = 0; i < drawCannonPoints.length; i++){
			p.addPoint((int)drawCannonPoints[i].getX(), (int)drawCannonPoints[i].getY());
		}
		
		g.drawPolygon(p);
		
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
/*	
	public void wall(int dx, int dy) {
		x += dx;
		y += dy;
	}

	public void move() {
		x += dx;
		y += dy;
	}

*/		
}

class Projectile{
	Point s;
	
}

class Target{
	
}
