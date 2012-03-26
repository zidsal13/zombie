package entities;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.ImageIcon;

public class Zombie {
	//used for movement
	private int dx = 0;
	private int dy = 0;
	public int x;
	public int y;

	//used for animation
	private static boolean moving = true;
	long currentTime = 0;
	long startTime = System.nanoTime(); //get the time of when the update method is called
	long elapsedTime = 150000000;
	Image sprite;
	//images may have mutiple frames in them for animation
	int spriteWidth = 20; //size of the width of what we want each sprite to be
	int spriteHeight = 32; //size of the height of what we want each sprite to be
	int maxFrame = 0; //how many frames there is in the x
	int maxDirection = 0; //how many frames there is in the y
	protected  int frame = 0;  //what frame of the x we want to draw
	protected  int direction = 2;  //what frame of the y we want to draw
	
	//combat stats
	Rectangle collision = new Rectangle();
	
	Player player = new Player();

	public Zombie(){
		Random rand = new Random();
		int zombieNumber = rand.nextInt(3);
		loadSprite("zombie" + zombieNumber + ".png",3,3);
		Random randX = new Random();
		Random randY = new Random();
		setX(randX.nextInt(740));
		setY(randY.nextInt(256));
	 }	

	public void loadSprite(String path,int maxFrameX, int maxFrameY)
	{
		//set what the sprites image is
        ImageIcon image = new ImageIcon(this.getClass().getResource(path));
        sprite = image.getImage();
        
        //the image has been loaded so now we can work out how many frames there are
        this.maxFrame = maxFrameX;
        this.maxDirection = maxFrameY;
        
		//collision rectangles
		collision.height = getSpriteHeight()/maxDirection;
		collision.width =  getSpriteWidth()/maxFrame;
		collision.x = x;
		collision.y = y;
	}
	
	public Image getImage()
	{
		//return the sprites image
		return sprite;
	}
	
	public int getSpriteWidth(){
		//get the images width
		return sprite.getWidth(null);
	}
	
	public int getSpriteHeight(){
		//get the images height
		return sprite.getHeight(null);
	}
	
	public int getX(){
		//get the sprites x cords
		return x;
	}
	
	public void setX(int x){
		//set the sprites x cords
		this.x = x;
		collision.x = x;
	}
	
	public int getY(){
		//get the sprites y cords
		return y;
	}
	
	public void setY(int y){
		//set the sprites y cords
		this.y = y;
		collision.y = y;
	}
	
	public int getDx(){
		//get the sprites y cords
		return x;
	}
	
	public void setDy(int DY){
		//set the sprites y cords
		this.dy = dy;
	}
	
	public int getDy(){
		//get the sprites y cords
		return y;
	}
	
	public void setDx(int dx){
		//set the sprites y cords
		this.dx = dx;
	}
	
	public void setDirection(int direction){
		this.direction = direction;
		
		if(direction > maxDirection)
			direction = 2; //return to default
		if(direction < 0)
			direction  = 2; //return to default 
	}
	
	public int getDirection(){
		return direction;
	}
	
	public void setFrameX(int frame){
		this.frame = frame;
		
		if(this.frame >= maxFrame)
			this.frame = 0; //go back to the beginning frame
	}
	
	public int getFrame(){
		return frame;
	}
	

	 public void draw(Graphics g){
		  	//draw the sprite
		 if(maxFrame >0 && maxDirection > 0)
	        g.drawImage(sprite,getX(),getY(), getX() + spriteWidth, getY() + spriteHeight, 
	        		frame *spriteWidth,direction * spriteHeight, (frame *spriteWidth) + spriteWidth,
	        		(direction*spriteHeight) + spriteHeight,null);
		 else 
		        g.drawImage(sprite,getX(),getY(),null);
	 }
	
	public void update(){
		currentTime = System.nanoTime(); //get the current time

		//check they havn't collide with anything);
			setX(x+ dx);
			setY(y+ dy);
	
		//animate them if they are moving
		if(moving == true){
			if(currentTime >= startTime + elapsedTime){ //enought time has passed for us to move to a new frame
				//animation is over
				setFrameX(getFrame() +1);
				startTime = System.nanoTime();
			}
			
			if(getFrame() == 0)
				moving = false;
		}
		
		if(moving != true){
			if(player.getX() > x)
				move(2);
			if (player.getX() < x)
				move(4);
			if(player.getY() > y)
				move(3);
			if(player.getY() < y)
				move(1);
		}
	}
	
	public int getdx(){
		return dx;
	}
	
	public Rectangle getCollision(){
		return collision;
	}
	public int getdy(){
		return dy;
	}
	
	public void move(int direction){
		
		switch(direction){
			case 1:
				dy= -2;
				setDirection(0);
				moving = true;
				break;
			case 2:
				dx=2;
				setDirection(1);
				moving = true;
				break;
			case 3:
				dy= 2;
				setDirection(2);
				moving = true;
				break;
			case 4:
				dx=-2;
				setDirection(3);
				moving = true;
				break;
			default:
				break;
		}
		
		//cap there movement speed.
		if(dx > 1)
			dx = 1;
		if(dy > 1)
			dy = 1;
		if(dx < -1)
			dx = -1;
		if(dy < -1)
			dy = -1;
	}
	
	public void stopMove(){
		dx = 0;
		dy = 0;
		
		//stop the animation
		setFrameX(0);
		moving = false;
	}
}
