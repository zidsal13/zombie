package game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Toolkit;

import java.awt.Image;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import map.loadMap;

import entities.Player;
import entities.Zombie;
import entities.ammoPack;

/**
 * The game frame its self!
 * This includes the animation loop, loading images etc
 */
public class GameFrame extends JPanel implements Runnable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -688556193724511893L;
	//for the game loop
    private Thread animator;
    boolean running = false;
    private Graphics dbg; 
    private Image dbImage = null;
    private int screenWidth = 0;
    private int screenHeight = 0;
    
    //for the player
	 public Player player = new Player();
	 loadMap map = new loadMap();
	 public ArrayList<Zombie> zombie = new ArrayList<Zombie>();
	 public ArrayList<ammoPack> ammo = new ArrayList<ammoPack>();
	 boolean gameOver = false;
	 
	 //used for timer to spawn zombies
		private static boolean moving = true;
		long currentTime = 0;
		long startTime = System.nanoTime(); //get the time of when the update method is called
		long elapsedTime = 800000000;
		
		//heart
        ImageIcon heart = new ImageIcon(this.getClass().getResource("\\heart.png"));
        Random rand = new Random();

    public GameFrame(int screenWidth, int screenHeight) {
    	setBackground(Color.BLACK);
    	this.screenWidth = screenWidth;
    	this.screenHeight = screenHeight;
    	
    	for(int i =0; i < 3; i++){
    		zombie.add(i, new Zombie());
    	}
    }
    
    public void addNotify(){
    	super.addNotify();
    	startGame();
    }
    
    public void startGame(){
    	if(animator == null || !running){
    		animator = new Thread(this);
    		animator.start();
    	}
    }
    
    public void stopGame(){
    	running = false;
	}
    
	public void run() {
		running = true;
		while(running){
			gameUpdate();
			gameRender();
			paintScreen();
			
			Thread.yield();
		}
			System.exit(0);//out of the game loop so exit the gme
	}
	
	public void gameUpdate(){
		if(gameOver== false){
			currentTime = System.nanoTime(); //get the current time
			player.update();
			
			for(int i = 0; i < zombie.size(); i++){
				zombie.get(i).update();
			}
			
			if(currentTime >= startTime + elapsedTime){ //enought time has passed for us to move to a new frame
				//spawn a new zombie
				zombie.add(new Zombie());
				startTime = System.nanoTime();
			}
			
			//check for collision between bullets and zombies
			for(int i = 0; i < player.getBulletList().size(); i++){
				for(int z = 0; z < zombie.size(); z++){
					if(zombie.get(z).getCollision().intersects(player.getBulletList().get(i).getCollision())){
						player.getBulletList().remove(i);
						player.setScore(100);
						
						//spawn the creates
						if(rand.nextInt(4) == 3)
							ammo.add(new ammoPack(zombie.get(z).x, zombie.get(z).y));
						
						//kill the zombie
						zombie.remove(z);
						break; //we're looping througt all the size but we have removed one so we need to exit the loop
					}
				}
			}
		}
		
		//check for collision between zombies and player
			for(int z = 0; z < zombie.size(); z++){
				if(zombie.get(z).getCollision().intersects(player.getCollision())){
					zombie.remove(z);
					player.removeHeart();
					if(player.getCurrentHearts() <= 0)
						gameOver = true;
					break; //we're looping througt all the size but we have removed one so we need to exit the loop
				}
			}
			
		//collision between players and ammo pack
			for(int a = 0; a < ammo.size(); a++){
				if(ammo.get(a).getCollision().intersects(player.getCollision())){
					ammo.remove(a);
					player.addBullets(5);
					break; //we're looping througt all the size but we have removed one so we need to exit the loop
				}
			}
	}
	
	public void gameRender(){
		if (dbImage == null){
			dbImage = createImage(screenWidth, screenHeight);
		    if (dbImage == null) {
		    	System.out.println("dbImage is null");
		        return;
		    }
		    else
		    	dbg = dbImage.getGraphics();
		    }

		    // clear the background
		    dbg.setColor(Color.BLACK);
		    dbg.fillRect(0, 0, screenWidth, screenHeight);
		    
		    //draw stuff here
		    map.drawGround(dbg);
		    drawUI(dbg);
		    player.draw(dbg);
		    
			for(int i = 0; i < zombie.size(); i++){
				zombie.get(i).draw(dbg);
			}
			
			for(int i = 0; i < ammo.size(); i++){
				ammo.get(i).draw(dbg);
			}
	}
	
	public void paintScreen()
	{
		Graphics g;
		try{
			g = this.getGraphics();
			if((g != null) && (dbImage != null))
				g.drawImage(dbImage,0,0,null);
			Toolkit.getDefaultToolkit().sync();
			g.dispose();
		}catch(Exception e)
		{System.out.println("Error:  + e");}
	}

	public void drawUI(Graphics g){
		g.setColor(Color.white);
		for(int i = 0; i < player.getCurrentHearts(); i++ ){
			g.drawImage(heart.getImage(), 0 + i*20, 0,null);
		}
		
		Font font1= new Font("Verdana", Font.PLAIN, 14);
		dbg.setFont(font1);
		g.drawString("Bullets: " + player.currentBullets(), 10, 30);
		g.drawString("Score: " + player.getScore(), 10, 50);
		
	    if(gameOver == true){
	    	dbg.setColor(Color.RED);
			Font font2 = new Font("Verdana", Font.BOLD, 48);
			dbg.setFont(font2);
			dbg.drawString("Game Over!", 200,400);
	    }
	    
	}

}