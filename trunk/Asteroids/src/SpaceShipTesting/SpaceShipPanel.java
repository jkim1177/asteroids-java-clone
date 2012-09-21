package SpaceShipTesting;


import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JPanel;
import javax.swing.Timer;

public class SpaceShipPanel extends JPanel implements ActionListener {
	private static final long serialVersionUID = 1L;
	
	private SpaceShip spaceShip;
	private Timer timer;
	
	public SpaceShipPanel() {
		addKeyListener(new SpaceShipMover());
		setFocusable(true);
		setBackground(Color.BLACK);
		setDoubleBuffered(true);
		
		spaceShip = new SpaceShip(500, 400);
		timer = new Timer(5, this);
		timer.start();
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		
        //give the graphics smooth edges
        RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        rh.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHints(rh);
        
		
		//draw polygon to represent spaceship
		g2d.setColor(Color.WHITE);
		g2d.fillPolygon(spaceShip.getXPoints(), spaceShip.getYPoints(),3);	
	}
	
	public void actionPerformed(ActionEvent e) {
		spaceShip.moveShip();
		repaint();
	}
	
	private class SpaceShipMover extends KeyAdapter {
		
		public void keyReleased(KeyEvent e) {
			spaceShip.keyReleased(e);
		}
		
		public void keyPressed(KeyEvent e) {
			spaceShip.keyPressed(e);
		}
	}
		
}
