import java.applet.Applet;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Label;
import java.awt.Scrollbar;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.geom.Rectangle2D;


public class G3E2 extends Applet implements ActionListener,
AdjustmentListener, Runnable{

	private static final long serialVersionUID = 10L;

	private static final int MAXSPEED = 100;

	private static final int MINSPEED = 0;

	private static final int SPEED = 1;

	private static final int MAXSIZE = 100;

	private static final int MINSIZE = 0;

	private static final int SIZE = 1;

	public static final int OFFSETX = 100;
	public static final int OFFSETY = 25;
	
	// creates labels on GUI
		Label RminLabel;
		Label RmaxLabel;
		Label KminLabel;
		Label KmaxLabel;
		
	// scroll bar
		Scrollbar rankin, kelvin;
		
		private int sliderW = 100;
		private int sliderH = 25;
	
		
	public void init() {

		setLayout(null);
		setVisible(true);
		setBackground(Color.white);
		
		// labels
		RminLabel = new Label("Rankin Minimum:");
		RmaxLabel = new Label("Rankin Maximum:");
		KminLabel = new Label("Kelvin Minimum:");
		KmaxLabel = new Label("Kelvin Maximum:");

		// speed scroll bars
		kelvin = new Scrollbar(Scrollbar.VERTICAL, 0, 1, 1, 100);
		rankin = new Scrollbar(Scrollbar.VERTICAL, 0, 1, 1, 100);
		
		//kelvin scroll bar
		kelvin.setMaximum(MAXSPEED);
		kelvin.setMinimum(MINSPEED);
		kelvin.setUnitIncrement(SPEED);
		kelvin.setBlockIncrement( 1);
		kelvin.setValue(0);
		kelvin.setBackground(Color.GRAY);
		kelvin.setSize(sliderW, sliderH);
		kelvin.setLocation(10, 100);
		kelvin.setEnabled(true);
		kelvin.addAdjustmentListener(this);
		kelvin.setVisible(true);
		this.add(kelvin);

		//rankin scroll bar
		rankin.setMaximum(MAXSIZE);
		rankin.setMinimum(MINSIZE);
		rankin.setUnitIncrement(SIZE);
		rankin.setBlockIncrement( 1);
		rankin.setValue(0);
		rankin.setBackground(Color.GRAY);
		rankin.setSize(sliderW, sliderH);
		rankin.setLocation(450, 100);
		rankin.setEnabled(true);
		rankin.addAdjustmentListener(this);
		rankin.setVisible(true);
		this.add(rankin);

		// adding labels to the GUI
		RminLabel.setBounds(450, 50, 130, 25);
		this.add(RminLabel);
		RmaxLabel.setBounds(450, 350, 130, 25);
		this.add(RmaxLabel);
		KminLabel.setBounds(10, 50, 100, 25);
		this.add(KminLabel);
		KmaxLabel.setBounds(10, 350, 100, 25);
		this.add(KmaxLabel);

	}

//setting up the gradient
	public void paint(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		//creating the gradient
		GradientPaint redblue = new GradientPaint(200, 200, Color.RED,
				300, 100, Color.BLUE);
		g2.setPaint(redblue);
		//filling the rectangles with color
		g2.fill(new Rectangle2D.Double(125,100,300,300));
		g2.setPaint(redblue);
		g2.fill(new Rectangle2D.Double(125,100,300,300));
		
	}


	@Override
	public void adjustmentValueChanged(AdjustmentEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}
	
}
