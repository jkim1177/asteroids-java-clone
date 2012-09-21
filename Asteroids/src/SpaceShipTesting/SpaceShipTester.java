package SpaceShipTesting;


import javax.swing.JFrame;

public class SpaceShipTester extends JFrame {
	private static final long serialVersionUID = 1L;
	
	public SpaceShipTester() {
		
		add(new SpaceShipPanel());
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(1000,800);
		setLocationRelativeTo(null);
		setTitle("Space Ship Test Program for Asteroids Program");
		setResizable(false);
		setVisible(true);
		
	}

	public static void main(String[] args) {
		new SpaceShipTester();
	}
}
