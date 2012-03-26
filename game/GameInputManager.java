package game;




import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import entities.Player;


/**
 * The method used for handling the games input.
 * This includes handling toe ability to detect key presses
 * as well as remap keys to any key people want!
 */
public class GameInputManager implements KeyListener,MouseListener {
	 public Player player = new Player();
	int [] action = {27,38,39,40,37,32,49,50,51,52,53}; //the keycode for the keys
	int mouseX = 0;
	int mouseY = 0;
	
	
	/**
	 * Remaps a key, demands the keycode and the index in the array for the action
	 * action index is currently set to 0 = escape, 1 = move up, 2 = move right, 3 = move down
	 * 4 = move left, 5 = shoot
	 */
	public void mapKey(int actionIndex,int keycode){
		action[actionIndex] = keycode;
	}
	
	public int convertToKeyCode(KeyEvent e){
		return e.getKeyCode();
	}
	

	@Override
	public void keyPressed(KeyEvent e) {
		int keycode =  e.getKeyCode();
		
		if (keycode == action[0]){ //escape
			System.out.println("escpe");
			return;
		}
		if (keycode == action[1]) {//up
			player.move(1);
			return;
		}
		if (keycode == action[2]){ //right
			player.move(2);
			return;
		}
		if (keycode == action[3]) {//down
			player.move(3);
			return;
		}
		if (keycode == action[4]){ // left
			player.move(4);
			return;
		}
		if (keycode == action[5]){ //shoot
			player.fire();
			return;
		}

	}

	@Override
	public void keyReleased(KeyEvent e) {
		int keycode =  e.getKeyCode();
		
		if (keycode == action[0]){ //escape
			System.out.println("escpe");
			return;
		}
		if (keycode == action[1]) {//up
			player.stopMove();
			return;
		}
		if (keycode == action[2]){ //right
			player.stopMove();
			return;
		}
		if (keycode == action[3]) {//down
			player.stopMove();
			return;
		}
		if (keycode == action[4]){ // left
			player.stopMove();
			return;
		}
		if (keycode == action[5]){ // jump
			return;
		}
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {

		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}

