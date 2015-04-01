package src;

import java.awt.Button;
import java.awt.CheckboxMenuItem;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Label;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.Panel;
import java.awt.Scrollbar;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class CannonVSBall extends java.applet.Applet implements Runnable,
		AdjustmentListener, ActionListener, ItemListener {

	//main elements
	MenuBar menu;
	Button fire;
	Label fireLabel;
	Label angleLabel;
	Label velocLabel;
	Scrollbar angle;
	Scrollbar velocity;
	Panel game;
	GridBagLayout gblayout;
	GridBagConstraints gbc;
	
	//sub elements
	Menu control, parameters, size, speed, env;
	MenuItem pause, run, restart;
	MenuItem sizee,speede;
	CheckboxMenuItem mercuryp, venusp, earthp, marsp,
		jupiterp, saturnp, uranusp,
		neptunep, plutop, planet_xp;
	
	//game values
	boolean gravity_en;
	float grav_const;
	float tar_velocity;
	float gun_velocity;
	
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
	
	public void init(){
		//crap
		gblayout = new GridBagLayout();
		gbc = new GridBagConstraints();
		
		//create
		fire = new Button("Fire");
		fireLabel = new Label("PIC");
		angleLabel = new Label("45");
		velocLabel = new Label("400");
		angle = new Scrollbar(Scrollbar.HORIZONTAL, 45, 1, 0, 90);
		velocity = new Scrollbar(Scrollbar.HORIZONTAL, 400, 1,100,1200);
		menu = new MenuBar();
		menuSetup();
		
		//listen
		
		
		//show
		
		this.setLayout(gblayout);
	}
	
	public void menuSetup(){
		
	}
	
	public void start(){
		
	}
	
	public void update(Graphics g){
		paint( g );
	}
	
	public void paint( Graphics g){
		
	}
	
	public void render( ){
		
	}
	
	public void model(){
		
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void adjustmentValueChanged(AdjustmentEvent arg0) {
		// TODO Auto-generated method stub

	}

	/*
	 * For rendering the game, 
	 * 
	 */
	@Override
	public void run() {
		// TODO Auto-generated method stub

	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		// TODO Auto-generated method stub
		
	}

}

class Projectile{

}

class Target{
	
}
