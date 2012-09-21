package SpaceShipTesting;
import java.awt.event.KeyEvent;

public class SpaceShip {

	//variable to hold polygon coordinates.  Used to draw the ship as a triangle.
	int[] xPoints;
	int[] yPoints;
	
	int xMove = 0; //moves ship horizontally
	int yMove = 0; //moves ship vertically
	
	//create coordinates for triangle that will represent the spaceship
	public SpaceShip(int x, int y) {
		xPoints = new int[3];
		xPoints[0] = x-10;
		xPoints[1] = x;
		xPoints[2] = x+10;
		
		yPoints = new int[3];
		yPoints[0] = y+10;
		yPoints[1] = y-20;
		yPoints[2] = y+10;
		
	}
	
	public int[] getXPoints() {
		return xPoints;
	}
	
	public int[] getYPoints() {
		return yPoints;
	}
	
	public void moveShip() {
		for(int i = 0; i < xPoints.length ; i++) {
			xPoints[i] += xMove;
		}
		for(int i = 0; i < yPoints.length ; i++) {
			yPoints[i] += yMove;
		}
	}
	
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		
		if(key == KeyEvent.VK_LEFT) {
			xMove = -1;
		}
		if(key == KeyEvent.VK_RIGHT) {
			xMove = 1;
		}
		if(key == KeyEvent.VK_UP) {
			yMove = -1;
		}
		if(key == KeyEvent.VK_DOWN) {
			yMove = 1;
		}
	}
	
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		
		if(key == KeyEvent.VK_LEFT) {
			xMove = 0;
		}
		if(key == KeyEvent.VK_RIGHT) {
			xMove = 0;
		}
		if(key == KeyEvent.VK_UP) {
			yMove = 0;
		}
		if(key == KeyEvent.VK_DOWN) {
			yMove = 0;
		}
		
	}
	
}
