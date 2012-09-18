import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;


public class Bouncy {
  JFrame frame = new JFrame();
  GamePanel gamePanel;
  private List<Asteroid> asteroids;

  
  Timer moveAsteroid = new Timer(10, new MotionListener()); //controls motion of the asteroid
  
  public static void main(String[] args){
    //Create a new game
    Bouncy game = new Bouncy();
    game.buildGui();
  }
  
  public void buildGui() {
    //set frame parameters
    frame.setBounds(400,10,1000,1000);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setResizable(false);
    frame.setVisible(true);
    
    gamePanel = new GamePanel();
    gamePanel.setBackground(Color.BLACK);
    frame.getContentPane().add(gamePanel, BorderLayout.CENTER);
    
    moveAsteroid.start();
  }//end buildGui();
  
  
  //create GamePanel and add asteroid objects to it
  public class GamePanel extends JPanel{
    public GamePanel() {
      super();
      asteroids = new ArrayList<Asteroid>();
      //need to change to a for loop with rand() to add multiple asteroids
      for(int i = 0 ; i < 10 ; i++) {
          int startX = (int)(Math.random()*600) + 1;
          int startY = (int)(Math.random()*600) + 1;
          int size = (int)(Math.random() * 100) + 50;
          asteroids.add(new Asteroid(startX, startY, size));
      }
    }//end GamePanel constructor
  
    public void paintComponent(Graphics g) {
      super.paintComponent(g); //this allows me to have a black background.  Not sure why though...
      Graphics2D g2d = (Graphics2D) g;
        
        //give the graphics smooth edges
        RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        rh.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHints(rh);
      
      //use loop to iterated through asteroids ArrayList, adding each object to the JPanel in turn
      for (Asteroid a :  asteroids) {
        g2d.setColor(Color.RED);
        g2d.fillOval(a.startX, a.startY, a.size, a.size);
      }
    }//end paintComponent()
}//end GamePanel class
  
  
  //create an Asteroid object with values for coordinates and size
  public class Asteroid {
    int startX; //starting x-coordinate 
    int startY; //starting y-coordinate
    int size;   //asteroid size
    int hVelocity = (int)(Math.random() * 5) + 1; //horizontal velocity
    int vVelocity = (int)(Math.random() * 5) + 1; //vertical verlocity
    
    //constructor
    public Asteroid(int x, int y, int s) {
      startX = x;
      startY = y;
      size = s;
    }
  }
  
  public class MotionListener implements ActionListener {
      public void actionPerformed(ActionEvent ev){
          //use loop to check for boundaries, and when encountered turn asteroid around.
          for (int i = 0 ; i < asteroids.size() ; i++) {
            if((gamePanel.getWidth() - asteroids.get(i).size) <= asteroids.get(i).startX || asteroids.get(i).startX <= 0) {
                asteroids.get(i).hVelocity *= (-1);
            }
  
            if((gamePanel.getHeight() - asteroids.get(i).size) <= asteroids.get(i).startY || asteroids.get(i).startY <= 0) {
                asteroids.get(i).vVelocity *= (-1);
            }
            
            //increment coordinate positiong by object velocity
            asteroids.get(i).startX += asteroids.get(i).hVelocity;
            asteroids.get(i).startY += asteroids.get(i).vVelocity;
          }
          
          frame.repaint();
      }
  }
}

