package asteroids;

import javax.swing.JFrame;

public class AsteroidsLauncher extends JFrame{
	private static final long serialVersionUID = 1L;
	public static final int B_WIDTH = 1000;
	public static final int B_HEIGHT = 800;
	
	public AsteroidsLauncher() {
		add(new Game());
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(B_WIDTH,B_HEIGHT);
		setLocationRelativeTo(null);
		setTitle("Asteroids");
		setResizable(false);
		setVisible(true);
	}
	
	public static void main(String[] args) {
		new AsteroidsLauncher();
	}

}
