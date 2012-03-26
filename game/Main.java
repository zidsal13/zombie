package game;


import java.awt.DisplayMode;

import javax.swing.JFrame;


/**
 * The entry point into the game!
 * also loads and sets up the Jframe
 */

public class Main extends JFrame {
	
	private static final long serialVersionUID = 3664358616839817425L;
	GameInputManager input = new GameInputManager(); 
    
    public Main() {
    	setTitle("game");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		setSize(768,720);
	 
		add(new GameFrame(768,720));
		addKeyListener(input);
		addMouseListener(input);
	    setLocationRelativeTo(null);
		setVisible(true);
    }
    public static void main(String[] args) {
        new Main();
    }
}