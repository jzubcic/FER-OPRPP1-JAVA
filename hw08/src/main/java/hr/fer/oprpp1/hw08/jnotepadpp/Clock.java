package hr.fer.oprpp1.hw08.jnotepadpp;

import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Rectangle;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import javax.swing.JComponent;
import javax.swing.SwingUtilities;

public class Clock extends JComponent {

	private static final long serialVersionUID = 1L;
	volatile String time; 
	volatile String date; 
	private boolean turnOff; 
	DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("uuuu/MM/dd"); //yyyy/MM/dd  HH:mm:ss
	DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
	
	public Clock() {
		updateTime(); 
		
		Thread t = new Thread(()->{
			while(!turnOff) {
				try {
					Thread.sleep(500);
				} catch(Exception ex) {}
				SwingUtilities.invokeLater(()->{
					updateTime();
				});
			}
		});
		t.setDaemon(true);
		t.start();
	}
	
	public void killClock() {
		turnOff = true; 
	}
	
	private void updateTime() {
		date = dateFormatter.format(LocalDate.now()); 
		time = timeFormatter.format(LocalTime.now());
		repaint(); 
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		Insets ins = getInsets();
		Dimension dim = getSize();
		Rectangle r = new Rectangle(
				ins.left, 
				ins.top, 
				dim.width-ins.left-ins.right,
				dim.height-ins.top-ins.bottom);
		if(isOpaque()) {
			g.setColor(getBackground());
			g.fillRect(r.x, r.y, r.width, r.height);
		}
		g.setColor(getForeground());
		
		FontMetrics fm = g.getFontMetrics();
		int w = fm.stringWidth(time);
		int h = fm.getAscent();
		
		g.drawString(
			date + " " + time, 
			r.x + (r.width-w)/2, 
			r.y+r.height-(r.height-h)/2
		);
	}
}
