package entities;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.util.ArrayList;

import javax.swing.ImageIcon;

import map.loadMap;

public class Bullet {
	public int x;
	public int y;
	Image sprite;
	public int direction = 0;
	Rectangle collision = new Rectangle();
	
	public Bullet(int x, int y, int direction){
		loadSprite("bullet.png");
		setX(x);
		setY(y);
		this.direction =direction;
		collision.height = getImage().getHeight(null);
		collision.width = getImage().getWidth(null)/4 ;
		collision.x = x;
		collision.y = y;
	 }
	
	public void loadSprite(String path)
	{
		//set what the sprites image is
        ImageIcon image = new ImageIcon(this.getClass().getResource(path));
        sprite = image.getImage();
	}
	
	public Rectangle getCollision(){
		return collision;
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
	
	public void update(){
		if(direction == 0)
			setY(getY() -5);
		if(direction == 1)
			setX(getX() +5);
		if(direction == 2)
			setY(getY() +5);
		if(direction == 3)
			setX(getX() -5);
	}
	
	public void draw(Graphics g){
		 g.drawImage(getImage(), getX(), getY(),getX()+15, getY()+15,direction * 15, 0, direction * 15 + 15, 15, null);
	}
}
