import java.applet.Applet;
import java.awt.Button;
import java.awt.Color;
import java.awt.Label;
import java.awt.Scrollbar;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;


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
		RminLabel = new Label("Min:");
		RmaxLabel = new Label("Max:");
		KminLabel = new Label("Min:");
		KmaxLabel = new Label("Max:");

		// speed scroll bars
		kelvin = new Scrollbar(Scrollbar.VERTICAL, 0, 1, 1, 100);
		rankin = new Scrollbar(Scrollbar.VERTICAL, 0, 1, 1, 100);
		
		kelvin.setMaximum(MAXSPEED);
		kelvin.setMinimum(MINSPEED);
		kelvin.setUnitIncrement(SPEED);
		kelvin.setBlockIncrement( 1);
		kelvin.setValue(0);
		kelvin.setBackground(Color.GRAY);
		kelvin.setSize(sliderW, sliderH);
		kelvin.setLocation(200, 640);
		kelvin.setEnabled(true);
		
		kelvin.addAdjustmentListener(this);
		kelvin.setVisible(true);

		this.add(kelvin);


		rankin.setMaximum(MAXSIZE);
		rankin.setMinimum(MINSIZE);
		rankin.setUnitIncrement(SIZE);
		rankin.setBlockIncrement( 1);
		rankin.setValue(0);
		rankin.setBackground(Color.GRAY);
		rankin.setSize(sliderW, sliderH);
		rankin.setLocation(200, 675);
		rankin.setEnabled(true);
		rankin.addAdjustmentListener(this);
		rankin.setVisible(true);
		this.add(rankin);

		// adding labels to the GUI
		RminLabel.setBounds(100, 650, 130, 25);
		this.add(RminLabel);
		RmaxLabel.setBounds(100, 675, 130, 25);
		this.add(RmaxLabel);
		KminLabel.setBounds(100, 650, 130, 25);
		this.add(RminLabel);
		KmaxLabel.setBounds(100, 650, 130, 25);
		this.add(RminLabel);

	}


	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void adjustmentValueChanged(AdjustmentEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
}
