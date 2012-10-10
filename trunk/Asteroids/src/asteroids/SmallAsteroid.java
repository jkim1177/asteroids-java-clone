package asteroids;

import java.awt.Color;
import java.awt.Graphics;

public class SmallAsteroid extends Asteroid {
	
	final double[] startingXPts = {-15,-5,5,15,15,5,-5,-15};
	final double[] startingYPts = {5,15,15,5,-5,-15,-15,-5};

	public SmallAsteroid(double x, double y, double angle,double rotationalSpeed, double xAcceleration, double yAcceleration,double asteroidRadius) {
		super(x, y, angle, rotationalSpeed, xAcceleration, yAcceleration,asteroidRadius);
		
		this.x = x;
		this.y = y;
		this.angle = angle;
		this.rotationalSpeed = rotationalSpeed/10;
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
	


}
