package entities;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.util.ArrayList;

import javax.swing.ImageIcon;

import map.loadMap;

public class Player {
	//used for movement
	private static int dx = 0;
	private static int dy = 0;
	public static int x = 320;
	public static int y = 500;

	//used for animation
	private static boolean moving = true;
	long currentTime = 0;
	long startTime = System.nanoTime(); //get the time of when the update method is called
	long elapsedTime = 150000000;
	Image sprite;
	//images may have mutiple frames in them for animation
	int spriteWidth = 24; //size of the width of what we want each sprite to be
	int spriteHeight = 32; //size of the height of what we want each sprite to be
	int maxFrame = 0; //how many frames there is in the x
	int maxDirection = 0; //how many frames there is in the y
	protected  int frame = 0;  //what frame of the x we want to draw
	protected static int direction = 2;  //what frame of the y we want to draw
	
	//combat stats
	int hearts = 5;
	static int   ammo = 25;
	static int currentStore = 0;
	public static ArrayList<Bullet> bulletList = new ArrayList<Bullet>();
	loadMap map = new loadMap();
	
	//collision
	Rectangle collision = new Rectangle();
	private long score;

	public Player(){
		loadSprite("player.png",3,3);
		//collision rectangles
		collision.height = getSpriteHeight()/maxDirection;
		collision.width =  getSpriteWidth()/maxFrame;
		collision.x = x;
		collision.y = y;
	 }

	public int getCurrentHearts(){
		return hearts;
	}
	
	public void removeHeart(){
		hearts--;
		
		if(hearts < 0){
			System.out.println("you lose");
		}
	}
	

	public void loadSprite(String path,int maxFrameX, int maxFrameY)
	{
		//set what the sprites image is
        ImageIcon image = new ImageIcon(this.getClass().getResource(path));
        sprite = image.getImage();
        
        //the image has been loaded so now we can work out how many frames there are
        this.maxFrame = maxFrameX;
        this.maxDirection = maxFrameY;
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
	
	public long getScore(){
		//get the sprites y cords
		return score;
	}
	
	public void setScore(long l){
		//set the sprites y cords
		
		if(Integer.parseInt(map.getGroundValue(getX()/32,getY()/32)) == 1)
			l = l*2;
		this.score += l;
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
	
	public Rectangle getCollision(){
		return collision;
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
		 
		 //draw the bullets
		for(int i =0; i < getBulletList().size(); i++){
			getBulletList().get(i).draw(g);
		}
	 }
	
	public void update(){
		currentTime = System.nanoTime(); //get the current time
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
		
		for(int i = 0; i < getBulletList().size(); i++){
			getBulletList().get(i).update();
			
			//remove the bullet if it gets too off screen
			if(getBulletList().get(i).x > 1000 || getBulletList().get(i).x < 0
					|| getBulletList().get(i).y > 1000 || getBulletList().get(i).y < 0){
				getBulletList().remove(i);
			}
		}
		
	}
	
	public int getdx(){
		return dx;
	}
	
	public int getdy(){
		return dy;
	}
	
	public void addBullets(int x){
		ammo +=x;
	}
	
	public void move(int direction){	
		int movement = 2;
		if(Integer.parseInt(map.getGroundValue(getX()/32,getY()/32)) == 1){
			movement = 1;
		}
		switch(direction){
			case 1:
				dy= -1 * movement;
				setDirection(0);
				moving = true;
				break;
			case 2:
				dx=movement;
				setDirection(1);
				moving = true;
				break;
			case 3:
				dy= movement;
				setDirection(2);
				moving = true;
				break;
			case 4:
				dx=-1 * movement;
				setDirection(3);
				moving = true;
				break;
			default:
				break;
		}
	}
	
	public void stopMove(){
		Player.dx = 0;
		Player.dy = 0;
		
		//stop the animation
		setFrameX(0);
		moving = false;
	}
	
	public int getAmmo(){
		return ammo;
	}
	
	public int currentBullets(){
		return ammo;
	}
	
	public ArrayList<Bullet> getBulletList(){
		return bulletList;
	}
	
	public void fire(){
		if(ammo <= 0)
			return;
		
		ammo--;
		bulletList.add(new Bullet(getX(), getY(), getDirection()));
	}
}
