package asteroids;

import java.awt.*;
import java.awt.geom.Point2D;

public class Asteroid {
	
	double x, y, asteroidRadius; //positional coordinates
	double angle, rotationalSpeed; //used to rotate the ship
	double xAcceleration, yAcceleration;  //speed
	
	boolean active; //flag to check whether the asteroid is alive
	
	//polygon points for drawing the asteroid
	final double[] startingXPts = {-30,-10,10,30,30,10,-10,-30};
	final double[] startingYPts = {10,30,30,10,-10,-30,-30,-10};
	int[] xPts, yPts; //need this to hold int values to be passed to fillPolygon()
	
	//constructor statement
	public Asteroid (double x, double y, double angle, double rotationalSpeed, double xAcceleration, double yAcceleration, double asteroidRadius) {
		this.x = x;
		this.y = y;
		this.angle = angle;
		this.rotationalSpeed = rotationalSpeed;
		this.xAcceleration = xAcceleration;
		this.yAcceleration = yAcceleration;
		this.asteroidRadius = asteroidRadius;
		active = true;
		
		//set aside space for the coordinate holder arrays
		xPts = new int[8];
		yPts = new int[8];
	}
	
	public void draw(Graphics g) {
		if(active) {
			for(int i=0 ; i<8 ; i++) {
				xPts[i] = (int)(startingXPts[i] * Math.cos(angle) - startingYPts[i] * Math.sin(angle) + x + 0.5);
				yPts[i] = (int)(startingXPts[i] * Math.sin(angle) + startingYPts[i] * Math.cos(angle) + y + 0.5);
			}
			g.setColor(Color.GRAY);
			g.drawPolygon(xPts, yPts, 8);
		}
	}
	
	public void move(int sWidth, int sHeight) {
		if(active) {
			angle += rotationalSpeed;
			x += xAcceleration;
			y += yAcceleration;
		}
		
		if(x<0)
			x += sWidth;
		else if(x>sWidth)
			x -= sWidth;
		
		if(y<0) {
			y += sHeight;
		}
		else if(y>sHeight)
			y -= sHeight;
	}
	
	//getters and setters
	public void setActive(boolean active) {
		this.active = active;
	}
	
	public Point2D getCenter() {
		return new Point2D.Double(x, y);
	}
	
	public double getRadius() {
		return asteroidRadius;
	}
	
	public double getX() {
		return x;
	}
	
	public double getY() {
		return y;
	}
}
