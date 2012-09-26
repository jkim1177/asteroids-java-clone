package asteroids;

import java.awt.geom.Point2D;

public class Asteroid {
    int startX;		//starting x-coordinate 
    int startY;		//starting y-coordinate
    int diameter;	//asteroid diameter
    int hVelocity;	//horizontal velocity
    int vVelocity;	//vertical velocity
	
	public Asteroid() {
	      //assign starting coordinates and diameter
	    startX = (int)(Math.random()*600) + 1; //starting x-coordinate 
	    startY = (int)(Math.random()*600) + 1; //starting y-coordinate
	    diameter = (int)(Math.random() * 100) + 50;   //asteroid diameter
	    
	    hVelocity = (int)(Math.random() * 5) + 1; //horizontal velocity
	    vVelocity = (int)(Math.random() * 5) + 1; //vertical velocity
	      
	    
	    //used randomize starting direction
	    int hDirection = (int)(Math.random() * 2);
	    int vDirection = (int)(Math.random() * 2);
	    	//randomize horizontal direction
	      	if (hDirection == 1) {
	      		hVelocity *= (-1);
	      	}
	      	//randomize vertical direction
	      	if (vDirection == 1) {
	      		vVelocity *= (-1);
	      	}
	}

	
	public void move() {
		if( AsteroidsLauncher.B_WIDTH - diameter <= startX || startX <= 0 ) {
			hVelocity *= -1;
		}
		
		if( 772 - diameter <= startY || startY <= 0) {
			vVelocity *= -1;
		}
		
		startX += hVelocity;
		startY += vVelocity;
		
	}
	
	public int getX() {
		return startX;
	}
	
	public int getY() {
		return startY;
	}
	
	public int getDiameter() {
		return diameter;
	}
	
	public void updateVelocity(double x, double y) {
		hVelocity = (int) x;
		vVelocity = (int) y;
	}
	
	public void updatePos (double newX, double newY) {
		startX = (int) newX;
		startY = (int) newY;
	}
	
	public Point2D getCenter() {
		return new Point2D.Double(startX + (diameter/2), startY + (diameter/2));
	}
	
	public Vector2D velVector() {
		return new Vector2D(this.hVelocity, this.vVelocity);
	}
	
	public double getRadius() {
		return (diameter/2);
	}
}
