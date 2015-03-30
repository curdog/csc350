package src;

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

public class G3E2 extends Applet implements ActionListener, AdjustmentListener,
 {

	private static final long serialVersionUID = 10L;
	public static final int OFFSETX = 100;
	public static final int OFFSETY = 25;
	public static final int MIN = -50;
	public static final int MAX = 252;

	// creates labels on GUI
	Label RminLabel;
	Label RmaxLabel;
	Label KminLabel;
	Label KmaxLabel;
	Label HotLabel;
	Label ColdLabel;

	// lists temp from scollbar value
	Label RValueLabel;
	Label KValueLabel;

	// scroll bar
	Scrollbar rankin, kelvin;
	private int sliderW = 25;
	private int sliderH = 300;

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
		HotLabel = new Label("COLD");
		ColdLabel = new Label("HOT");

		// speed scroll bars
		kelvin = new Scrollbar(Scrollbar.VERTICAL, 0, 1, 2276, 3943);
		rankin = new Scrollbar(Scrollbar.VERTICAL, 0, 1, 4097, 7097);

		kelvin.setValue(300);
		kelvin.setBackground(Color.GRAY);
		kelvin.setSize(sliderW, sliderH);
		kelvin.setLocation(100, 100);
		kelvin.setEnabled(true);
		kelvin.addAdjustmentListener(this);
		kelvin.setVisible(true);
		this.add(kelvin);

		rankin.setValue(500);
		rankin.setBackground(Color.GRAY);
		rankin.setSize(sliderW, sliderH);
		rankin.setLocation(575, 100);
		rankin.setEnabled(true);
		rankin.addAdjustmentListener(this);
		rankin.setVisible(true);
		this.add(rankin);

		// adding labels to the GUI
		RminLabel.setBounds(525, 50, 130, 25);
		this.add(RminLabel);
		RmaxLabel.setBounds(530, 420, 130, 25);
		this.add(RmaxLabel);
		KminLabel.setBounds(75, 50, 100, 25);
		this.add(KminLabel);
		KmaxLabel.setBounds(75, 420, 100, 25);
		this.add(KmaxLabel);
		// moving the labels with the scroll bar
		KValueLabel.setBounds(10, (100 + 100), 100, 25);
		this.add(RValueLabel);
		RValueLabel.setBounds(610, (100 + 100), 100, 25);
		this.add(KValueLabel);
		HotLabel.setBounds(335, 50, 100, 25);
		this.add(HotLabel);
		ColdLabel.setBounds(335, 430, 100, 25);
		this.add(ColdLabel);

		// TODO
		// how to get parameter
		String deg = getParameter("Deg");
		String un = getParameter("Unit");

		double inideg = (deg != null ) ? Double.parseDouble(deg) : 300;

		if (un != null) {
			if (un.equals("K")) {
				if( inideg * 10 > 2276 && inideg * 10 < 3943 ){
					kelvin.setValue( (int)(inideg * 10));
					this.adjustmentValueChanged( new AdjustmentEvent(kelvin, 0, 0, 0));
				}
				else{
					kelvin.setValue(  3000);
					this.adjustmentValueChanged( new AdjustmentEvent(kelvin, 0, 0, 0));
				}

			} else if (un.equals("R")) {
				if( inideg * 10 > 4097 && inideg * 10 < 7097 ){
					rankin.setValue( (int)(inideg * 10));
					this.adjustmentValueChanged( new AdjustmentEvent(rankin, 0, 0, 0));
				}
				else{
					rankin.setValue(  3000);
					this.adjustmentValueChanged( new AdjustmentEvent(rankin, 0, 0, 0));
				}
			} else {
				rankin.setValue(  3000);
				this.adjustmentValueChanged( new AdjustmentEvent(rankin, 0, 0, 0));
			}

		} else {
			rankin.setValue(  3000);
			this.adjustmentValueChanged( new AdjustmentEvent(rankin, 0, 0, 0));
		}
	}

	private int yval = 0;

	// setting up the gradient
	public void paint(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		// creating the gradient
		GradientPaint redblue = new GradientPaint(300, 200, Color.BLUE, 300,
				300, Color.RED);
		g2.setPaint(redblue);
		// filling the rectangles with color
		g2.fill(new Rectangle2D.Double(200, 100, 300, 300));
		g2.setPaint(redblue);
		g2.fill(new Rectangle2D.Double(200, 100, 300, 300));

		// TODO: clean up
		// draw bar graph over gradient
		g.setColor(Color.BLACK);
		g.drawRect(250, 100, 200, yval - 100);

		// g.fillRect(250, 100, 200, yval - 150);
	}

	public void adjustmentValueChanged(AdjustmentEvent e) {
		int val1, val2;

		// conversions
		float rankinv = 0.0f;
		float kelvinv = 0.0f;

		rankinv = (float) kelvin.getValue() / 10.0f * 9.0f / 5.0f;
		kelvinv = (float) rankin.getValue() / 10.0f * 5.0f / 9.0f;

		// get rankin temp from scrollbar
		if (e.getSource().equals(rankin)) {

			kelvin.setValue((int) (kelvinv * 10.0f));

		}

		// get kelvin temp from scrollbar
		if (e.getSource().equals(kelvin)) {

			rankin.setValue((int) (rankinv * 10.0f));

		}

		yval = (int) (280.0f
				/ ((float) (rankin.getMaximum() / 10 - rankin.getMinimum() / 10))
				* (float) rankin.getValue() / 10 - 280);

		//System.out.println("Ybal" + yval);

		this.remove(RValueLabel);
		this.remove(KValueLabel);

		RValueLabel.setLocation(RValueLabel.getX(), yval);
		KValueLabel.setLocation(KValueLabel.getX(), yval);

		RValueLabel.setText(String.format("Temp: %.1f", rankinv));
		KValueLabel.setText(String.format("Temp: %.1f", kelvinv));

		this.add(RValueLabel);
		this.add(KValueLabel);

		this.invalidate();
		this.validate();

	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub

	}


}
