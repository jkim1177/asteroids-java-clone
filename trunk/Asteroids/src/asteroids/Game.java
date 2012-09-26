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
		setDoubleBuffered(true);
		
		//create a bunch of asteroid objects and add them to the asteroids ArrayList
		asteroids = new ArrayList<Asteroid>();
		
		for(int i = 0 ; i < numOfAsteroids ; i++) {
			asteroids.add(new Asteroid());
			System.out.println("made asteroid " + i + " at location x: " + asteroids.get(i).startX + " y: " + asteroids.get(i).startY + " and diameter: " + asteroids.get(i).diameter);
			
			if(i > 0) {
				for(int j = i ; j-i != i ; j++) {
					//first asteroid
					Asteroid a = asteroids.get(j-i);
					Point2D aCenter = a.getCenter();
					//second asteroid
					Asteroid b = asteroids.get(i);
					Point2D bCenter = b.getCenter();
				
					double distanceBetween = aCenter.distance(bCenter);
					if(distanceBetween < (a.getRadius() + b.getRadius())) {
						asteroids.remove(i);
						System.out.println("Asteroid " + i + " intersects with asteroid " + (j-i));
						System.out.println("Removing asteroid " + i);
						i--;
						j=((i*2)-1);
					}
				}
			}
		}
		
		timer = new Timer();
		timer.scheduleAtFixedRate(new MotionListener(), 100, 20);
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
				if(distanceBetween <= (a1.getRadius() + a2.getRadius())) {
					a1.hVelocity *= -1;
					a1.vVelocity *= -1;
					a2.hVelocity *= -1;
					a2.vVelocity *= -1;
					
				}
			}
		}
	}
	
	public void collide(Asteroid a1, Asteroid a2, double distanceBetween) {
		//set variables for the relative distances between the x & y coordinates of the asteroids
		double relX = a1.getX() - a2.getX();
		double relY = a1.getY() - a2.getY();
		
		//Take the arctan to find the collision angle
		double collisionAngle = Math.atan2(relY, relX);
		// if (collisionAngle < 0) collisionAngle += 2 * Math.PI;
		// Rotate the coordinate systems for each object's velocity to align
		// with the collision angle. We do this by supplying the collision angle
		// to the vector's rotateCoordinates method.
		Vector2D a1Vel = a1.velVector(), a2Vel = a2.velVector();
		a1Vel.rotateCoordinates(collisionAngle);
		a2Vel.rotateCoordinates(collisionAngle);
		
		// In the collision coordinate system, the contact normals lie on the
		// x-axis. Only the velocity values along this axis are affected. We can
		// now apply a simple 1D momentum equation where the new x-velocity of
		// the first object equals a negative times the x-velocity of the
		// second.
		double swap = a1Vel.x;
		a1Vel.x = a2Vel.x;
		a2Vel.x = swap;
		
		//Now we need to get the vectors back into normal coordinate space
		a1Vel.restoreCoordinates();
		a2Vel.restoreCoordinates();
		
		//Give each object its new velocity
		a1.updateVelocity(a1Vel.x, a1Vel.y);
		a2.updateVelocity(a2Vel.x, a2Vel.y);
		
		//Back them up in the opposite angle so they are not overlapping
		double minDist = a1.getRadius() + a2.getRadius();
		double overlap = minDist - distanceBetween;
		double toMove = overlap / 2;
		double newX = a1.getX() - (toMove * Math.cos(collisionAngle));
		double newY = a1.getY() - (toMove * Math.cos(collisionAngle));
		a1.updatePos(newX, newY);
		newX = a2.getX() - (toMove * Math.cos(collisionAngle));
		newY = a2.getY() - (toMove * Math.cos(collisionAngle));
		a2.updatePos(newX, newY);
		
	}
}
