import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GameLauncher {
  
  //class variables
  JFrame gameFrame;
  JPanel gamePanel;
  Asteroid asteroid; //our friendly asteroid
  int startX = 10; //horizontal coordinate 
  int startY = 30; //vertical coordinate
  int hVelocity = 1; //horizontal velocity
  int vVelocity = 1; //vertical velocity
  int size = 50; //denotes size of asteroid
  
  Timer moveAsteroid = new Timer(5, new AsteroidListener());

  public static void main(String[] args) {
    GameLauncher game = new GameLauncher();
    game.buildGUI();
  } //END main()
  
  //build a simple gui
  public void buildGUI() {
    //create frame
    gameFrame = new JFrame("Bouncing Asteroid");
    gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    //create and add panel
    gamePanel = new JPanel();
    gameFrame.getContentPane().add(BorderLayout.CENTER, gamePanel);
    gamePanel.setLayout(new BorderLayout());
    //set frame attributes
    gameFrame.setBounds(500,150,800,600);
    gameFrame.setVisible(true);
    gameFrame.setResizable(false);


    //add asteroid object to panel    
    asteroid = new Asteroid(); //create an asteroid object
    gamePanel.add(asteroid);
    
    //make asteroid move
    moveAsteroid.start();
    

    
  } //END buildGUI()

  //Asteroid object
  public class Asteroid extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void paintComponent(Graphics g) {
      //set background color
      g.setColor(Color.BLACK);
      g.fillRect(0,0,gameFrame.getWidth(),gameFrame.getHeight());
    
      //create asteroid
      g.setColor(Color.RED);
      g.fillOval(startX,startY,size,size);
    }
  } //END Asteroid
  
  public class AsteroidListener implements ActionListener {
    public void actionPerformed(ActionEvent ev) {
    
    //create an infinite loop
      if((gamePanel.getWidth() - size) == startX || startX == 0) {
        hVelocity *= (-1);
      }
      if((gamePanel.getHeight() - size) == startY || startY == 0) {
        vVelocity *= (-1);
      }
      startX += hVelocity;
      startY += vVelocity;
      gameFrame.repaint();

    }
  }
}





