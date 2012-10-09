package asteroids;

import java.applet.*;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class Game extends Applet implements Runnable, KeyListener {
	
	//timing variables
	Thread thread;
	long startTime, endTime, framePeriod;
	
	//graphics variables
	Image img;
	Dimension dim;
	Graphics g;
	
	SpaceShip ship;
	ArrayList<Asteroid> asteroids = new ArrayList<Asteroid>();
	int numOfAsteroids = 8;
	//ArrayList to hold the lasers
	ArrayList<Laser> lasers = new ArrayList<Laser>();
	final double rateOfFire = 10; //limits rate of fire
	double rateOfFireRemaining; //decrements rate of fire 
	
	public void init() {
		resize(900,700); //set size of the applet
		framePeriod = 25; //set refresh rate
		
		addKeyListener(this); //to get commands from keyboard
		setFocusable(true); 
		
		ship = new SpaceShip(450, 350, 0, .15, .5, .15, .98); //add ship to game
		addAsteroids();
		

		
		dim = getSize(); //get dimension of the applet
		img = createImage(dim.width, dim.height); //create an off-screen image for double-buffering
		g = img.getGraphics(); //assign the off-screen image
		thread = new Thread(this);
		thread.start();
	}
	
	public void paint(Graphics gfx) {
		Graphics2D g2d = (Graphics2D) g;
        //give the graphics smooth edges
        RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        rh.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHints(rh);
        
        g2d.setColor(Color.BLACK);
        g2d.fillRect(0, 0, 900, 700); //add a black background
        for(Asteroid a: asteroids) { //draw asteroids
        	a.draw(g2d);
        }
        
        for(Laser l : lasers) { //draw lasers
        	l.draw(g2d);
        }
        
        ship.draw(g2d); //draw ship
        
        gfx.drawImage(img, 0, 0, this); //draw the off-screen image (double-buffering) onto the applet
	}
	
	public void update(Graphics gfx) {
		paint(gfx); //gets rid of white flickering
	}
	
	public void run() {
		for( ; ; ) {
			startTime = System.currentTimeMillis(); //timestamp
			ship.move(dim.width, dim.height);
			for(Asteroid a : asteroids) {
				a.move(dim.width, dim.height);
			}
			for(Laser l : lasers) {
				l.move(dim.width, dim.height);
			}
			for(int i = 0 ; i<lasers.size() ; i++) {
				if(!lasers.get(i).getActive())
					lasers.remove(i);
			}
			rateOfFireRemaining--;
			collisionCheck();
			if(asteroids.size() == 0)
				addAsteroids();
			repaint();
			try {
				endTime = System.currentTimeMillis(); //new timestamp
				if(framePeriod - (endTime-startTime) > 0) 				//if there is time left over after repaint, then sleep 
					Thread.sleep(framePeriod - (endTime - startTime)); 	//for whatever is  remaining in framePeriod
			} catch(InterruptedException e) {}
		}
	}
	
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		//fires laser
		if(key == KeyEvent.VK_SPACE) {
			if(rateOfFireRemaining <= 0 ) {
				lasers.add(ship.fire());
				rateOfFireRemaining = rateOfFire;
			}
		}
		if(key == KeyEvent.VK_UP) 
			ship.setAccelerating(true);
		if(key == KeyEvent.VK_RIGHT)
			ship.setTurningRight(true);
		if(key == KeyEvent.VK_LEFT)
			ship.setTurningLeft(true);
		if(key == KeyEvent.VK_DOWN)
			ship.setDecelerating(true);
	}
	
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		if(key == KeyEvent.VK_UP) 
			ship.setAccelerating(false);
		if(key == KeyEvent.VK_RIGHT)
			ship.setTurningRight(false);
		if(key == KeyEvent.VK_LEFT)
			ship.setTurningLeft(false);
		if(key == KeyEvent.VK_DOWN)
			ship.setDecelerating(false);
	}
	
	public void keyTyped(KeyEvent e) {
		
	}
	
	public void addAsteroids() {
		for(int i=0 ; i<numOfAsteroids ; i++) {		//add asteroids to game
			//randomize starting position
			int asteroidX = (int)(Math.random() * 900) + 1;
			int asteroidY = (int)(Math.random() * 700) + 1;
			//randomize speed and direction
			int xVelocity = (int)(Math.random() * 3) + 1; //horizontal velocity
		    int yVelocity = (int)(Math.random() * 3) + 1; //vertical velocity
		    //used randomize starting direction
		    int xDirection = (int)(Math.random() * 2);
		    int yDirection = (int)(Math.random() * 2);
		    	//randomize horizontal direction
		      	if (xDirection == 1)
		      		xVelocity *= (-1);
		      	//randomize vertical direction
		      	if (yDirection == 1)
		      		yVelocity *= (-1);
			asteroids.add(new Asteroid(asteroidX, asteroidY, 0, .1, xVelocity, yVelocity));
		}
	}
	
	public void collisionCheck() {
		//check for collisions between lasers and asteroids
		for(int i = 0 ; i < asteroids.size() ; i++) {
			Asteroid a = asteroids.get(i);
			Point2D aCenter = a.getCenter();
			
			for(int j = 0 ; j < lasers.size() ; j++) {
				Laser l = lasers.get(j);
				Point2D lCenter = l.getCenter();
				
				double distanceBetween = aCenter.distance(lCenter);
				if(distanceBetween <= (a.getRadius() + l.getRadius())) {
					asteroids.remove(i);
					lasers.remove(j);
				}
			}
		}
	}
	
	
}