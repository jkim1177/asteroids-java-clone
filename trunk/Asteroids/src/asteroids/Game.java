package asteroids;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import java.awt.geom.Point2D;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JPanel;

public class Game extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private Timer timer;
	private ArrayList<Asteroid> asteroids;
	private int numOfAsteroids = 5;
	
	public Game() {
		
		setBackground(Color.BLACK);
		
		//create a bunch of asteroid objects and add them to the asteroids ArrayList
		asteroids = new ArrayList<Asteroid>();
		
		for(int i = 1 ; i < numOfAsteroids ; i++) {
			asteroids.add(new Asteroid());
			Asteroid a = asteroids.get(i);
			Point2D aCenter = a.getCenter();
			
			for(Asteroid b : asteroids) {
				Point2D bCenter = b.getCenter();
				
				double distanceBetween = aCenter.distance(bCenter);
				if(distanceBetween < (a.getRadius() + b.getRadius())) {
					asteroids.remove(i);
					i--;
				}
			}
		}
		
		timer = new Timer();
		timer.scheduleAtFixedRate(new MotionListener(), 100, 10);
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		
        //give the graphics smooth edges
        RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        rh.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHints(rh);
        
        //use loop to iterated through asteroids ArrayList, adding each object to the JPanel in turn
        for (Asteroid a :  asteroids) {
          g2d.setColor(Color.RED);
          g2d.fillOval(a.getX(), a.getY(), a.getDiameter(), a.getDiameter());
        }
        
        repaint();
	}
	
	public class MotionListener extends TimerTask {
		public void run() {
			for(int i = 0 ; i < asteroids.size() ; i++) {
				asteroids.get(i).move();
				collisions();
			}
		}
	}
	
	public void collisions() {
		//get boundary for the first asteroid
		for(int i = 0 ; i < asteroids.size(); i++) {
			Asteroid a1 = asteroids.get(i);
			Point2D a1Center = a1.getCenter();
			
			//check against boundaries of all the other asteroids
			for(int j = i + 1 ; j < asteroids.size(); j++) {
				
				Asteroid a2 = asteroids.get(j);
				Point2D a2Center = a2.getCenter();
				
				double distanceBetween = a1Center.distance(a2Center);
				if(distanceBetween < (a1.getRadius() + a2.getRadius())) {
					a1.hVelocity *= -1;
					a1.vVelocity *= -1;
					a2.hVelocity *= -1;
					a2.vVelocity *= -1;
				}
			}
		}
	}
}
