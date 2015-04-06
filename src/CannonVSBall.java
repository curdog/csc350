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
	Panel GamePanel, ControlPanel;
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
	private final int MaxAng = 100;
	private final int MinAng = 0;
	
	//velocity boundaries, in feet/sec
	private final int MaxVel = 1210;
	private final int MinVel = 100;
	
	public void init(){
		//pause, quit
		p = true;
		q = false;
		
		setControlPanel();
		setGamePanel();
		
		//ball = new Ballc();
		
		setLayout(new BorderLayout(0,0));
		setVisible(true);
		
		
		//Menu Bar
		menu = new MenuBar();
		menuSetup();
		
		Object f = getParent();
		
		while (!(f instanceof Frame))
			f = ((Component) f).getParent();
		((Frame) f).setMenuBar( menu );
		//listen
		
		
		//show
		
		
		//add listeners
		fire.addActionListener(this);
		angle.addAdjustmentListener(this);
		velocity.addAdjustmentListener(this);
		add(ControlPanel, BorderLayout.CENTER);
		add(GamePanel, BorderLayout.SOUTH);
		
		this.setLayout(gblayout);
	}
	
	private void setGamePanel() {
		GamePanel = new Panel();
		GamePanel.setVisible(true);
		GamePanel.setLayout(new BorderLayout(0,0));
		GamePanel.setSize(900,490);
		//ball.setSize(900,490);
		//GamePanel.add("Center",ball);
		GamePanel.setBackground(Color.WHITE);
	}

	private void setControlPanel() {
		
		ControlPanel = new Panel();
		
		GridBagLayout gbl;
		GridBagConstraints gbc;
		
		gbc = new GridBagConstraints();
		gbl = new GridBagLayout();
		
		ControlPanel.setVisible(true);
		ControlPanel.setLayout(gbl);
		
		angle = new Scrollbar(Scrollbar.HORIZONTAL, 45, 1, 0, 90);
		velocity = new Scrollbar(Scrollbar.HORIZONTAL, 400, 1,100,1200);
		
		
		angle.setBlockIncrement(2);
		velocity.setBlockIncrement(2);
		
		angle.setMinimum(5);
		velocity.setMinimum(2);
		
		angle.setUnitIncrement(1);
		velocity.setUnitIncrement(1);
		
		angle.setSize(100,25);
		velocity.setSize(100,25);
		
		angle.setEnabled(true);
		velocity.setEnabled(true);
		
		angle.validate();
		velocity.validate();
		
		//labels
		fireLabel = new Label("PIC");
		angleLabel = new Label("45");
		velocLabel = new Label("400");
		computer = new Label("Computer:");
		user = new Label("User:");
		
		//fire button
		fire = new Button("Fire");
		

		
		/********** SETTING UP THE GUI **********/
		
		//fire, angle label
		gbc.gridx = 2;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		ControlPanel.add(fireLabel,gbc);
		
		gbc.gridx = 2;
		gbc.gridy = 2;
		gbc.gridwidth = 1;
		ControlPanel.add(angleLabel,gbc);
		
		//buttons
		gbc.gridx = 2;
		gbc.gridy = 3;
		gbc.gridwidth = 1;
		ControlPanel.add(fire,gbc);
		
		//scroll bars
		gbc.gridx = 0;
		gbc.gridy = 0;
		angle.setMaximum(MaxAng);
		angle.setMinimum(MinAng);
		ControlPanel.add(angle,gbc);
		
		gbc.gridx = 3;
		gbc.gridy = 2;
		velocity.setMaximum(MaxVel);
		velocity.setMaximum(MaxVel);
		ControlPanel.add(velocity, gbc);
		
		//score labels
		ControlPanel.add(computer,gbc);
		ControlPanel.add(user,gbc);
		
	}

	public void menuSetup(){
		menu.add(control = new Menu("Control"));
		menu.add(size = new Menu("Size"));
		menu.add( speed = new Menu("Speed"));
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
		
		size.add(xsm_size = new CheckboxMenuItem("X-Small"));
		size.add(sm_size = new CheckboxMenuItem("Small"));
		size.add(med_size = new CheckboxMenuItem("Medium"));
		size.add(lg_size = new CheckboxMenuItem("Large"));
		size.add(xlg_size = new CheckboxMenuItem("X-Large"));
		size.add(barn_size = new CheckboxMenuItem("Barn"));
		
		env.add(mercuryp = new CheckboxMenuItem("Mercury"));
		env.add(venusp = new CheckboxMenuItem("Venus"));
		env.add(marsp = new CheckboxMenuItem("Mars"));
		env.add(earthp = new CheckboxMenuItem("Earth"));
		env.add(jupiterp = new CheckboxMenuItem("Jupiter"));
		env.add(saturnp = new CheckboxMenuItem("Saturn"));
		env.add(uranusp = new CheckboxMenuItem("Uranus"));
		env.add(neptunep = new CheckboxMenuItem("Neptune"));
		env.add(plutop = new CheckboxMenuItem("Pluto"));
		
		speed.add(xslow = new CheckboxMenuItem("X-Slow"));
		speed.add(slow = new CheckboxMenuItem("Slow"));
		speed.add(avg = new CheckboxMenuItem("Average"));
		speed.add(fast = new CheckboxMenuItem("Fast"));
		speed.add(xfast = new CheckboxMenuItem("X-Fast"));
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
		
		int height = 890;
		int width = 480;
		
		//draw frame
		g.drawRect(5,5,height,width);
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

	}

	/*
	 * For rendering the game, 
	 * 
	 */
	public void run() {
		while(more){
			ball.repaint();
			
			if(!p){
				ball.move();
			}
			
			try{
				theThread.sleep(delay);
			} catch (InterruptedException e){}
		}

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
		}else if(o == sm_size){
			xsm_size.setState(false);
			med_size.setState(false);
			lg_size.setState(false);
			xlg_size.setState(false);
		}else if(o == med_size){
			xsm_size.setState(false);
			sm_size.setState(false);
			lg_size.setState(false);
			xlg_size.setState(false);
		}else if(o == lg_size){
			xsm_size.setState(false);
			sm_size.setState(false);
			med_size.setState(false);
			xlg_size.setState(false);
		}else if(o == xlg_size){
			xsm_size.setState(false);
			sm_size.setState(false);
			lg_size.setState(false);
			med_size.setState(false);
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

class Projectile{

}

class Target{
	
}
