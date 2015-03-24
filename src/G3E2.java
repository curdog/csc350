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
	public static final int OFFSETX = 100;
	public static final int OFFSETY = 25;
	
	// creates labels on GUI
		Label RminLabel;
		Label RmaxLabel;
		Label KminLabel;
		Label KmaxLabel;
		Label HotLabel;
		Label ColdLabel;
		
	//lists temp from scollbar value
		Label RValueLabel;
		Label KValueLabel;
		
	// scroll bar
		Scrollbar rankin, kelvin;
		private int sliderW = 25;
		private int sliderH = 240;
		
		//convert double to int
		//min -50 Fahrenheit
		//max 250 Fahrenheit
		Double KELVINMIN = new Double(227.594);
		int KMIN = KELVINMIN.intValue();
		Double KELVINMAX = new Double(394.261);
		int KMAX = KELVINMAX.intValue();
		Double RANKINMIN = new Double(409.67);
		int RMIN = RANKINMIN.intValue();
		Double RANKINMAX = new Double(709.67);
		int RMAX = RANKINMAX.intValue();
	
		
	public void init() {

		setLayout(null);
		setVisible(true);
		setBackground(Color.white);
		
		// labels
		RminLabel = new Label("Rankin Minimum");
		RmaxLabel = new Label("Rankin Maximum");
		KminLabel = new Label("Kelvin Minimum");
		KmaxLabel = new Label("Kelvin Maximum");
		RValueLabel = new Label("Temp:");
		KValueLabel = new Label("Temp:");
		HotLabel = new Label("HOT");
		ColdLabel = new Label("COLD");
		
		// speed scroll bars
		kelvin = new Scrollbar(Scrollbar.VERTICAL, 0, 1, 1, 100);
		rankin = new Scrollbar(Scrollbar.VERTICAL, 0, 1, 1, 100);
		
		//kelvin scroll bar
		kelvin.setMaximum(KMAX);
		kelvin.setMinimum(KMIN);
		kelvin.setUnitIncrement(1);
		kelvin.setBlockIncrement( 1);
		kelvin.setValue(0);
		kelvin.setBackground(Color.GRAY);
		kelvin.setSize(sliderW, sliderH);
		kelvin.setLocation(100, 100);
		kelvin.setEnabled(true);
		kelvin.addAdjustmentListener(this);
		kelvin.setVisible(true);
		this.add(kelvin);

		//rankin scroll bar
		rankin.setMaximum(RMAX);
		rankin.setMinimum(RMIN);
		rankin.setUnitIncrement(1);
		rankin.setBlockIncrement( 1);
		rankin.setValue(0);
		rankin.setBackground(Color.GRAY);
		rankin.setSize(sliderW, sliderH);
		rankin.setLocation(575, 100);
		rankin.setEnabled(true);
		rankin.addAdjustmentListener(this);
		rankin.setVisible(true);
		this.add(rankin);

		// adding labels to the GUI
		RminLabel.setBounds(525, 60, 130, 25);
		this.add(RminLabel);
		RmaxLabel.setBounds(530, 380, 130, 25);
		this.add(RmaxLabel);
		KminLabel.setBounds(75, 50, 100, 25);
		this.add(KminLabel);
		KmaxLabel.setBounds(75, 380, 100, 25);
		this.add(KmaxLabel);
		KValueLabel.setBounds(0, 0, 300, 25);
		this.add(RValueLabel);
		RValueLabel.setBounds(500, 0, 300, 25);
		this.add(KValueLabel);
		HotLabel.setBounds(335, 50, 100, 25);
		this.add(HotLabel);
		ColdLabel.setBounds(335, 430, 100, 25);
		this.add(ColdLabel);
		
	}

//setting up the gradient
	public void paint(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		//creating the gradient
		GradientPaint redblue = new GradientPaint(300, 100, Color.RED,
				200, 200, Color.BLUE);
		g2.setPaint(redblue);
		//filling the rectangles with color
		g2.fill(new Rectangle2D.Double(200,100,300,300));
		g2.setPaint(redblue);
		g2.fill(new Rectangle2D.Double(200,100,300,300));
		
	}


	public void adjustmentValueChanged(AdjustmentEvent e) {
		//get rankin temp from scrollbar
		if(e.getSource().equals(rankin)){
			RValueLabel.setText("Temp: " + rankin.getValue());
		//get kalvin temp from scrollbar
		}else if(e.getSource().equals(kelvin)){
			KValueLabel.setText("Temp: " + kelvin.getValue());
		}
		
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