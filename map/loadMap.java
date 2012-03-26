package map;

import java.awt.Graphics;
import java.awt.Image;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.ImageIcon;


public class loadMap {
	
	BufferedReader reader; 
	ArrayList<String> lines =  new ArrayList<String>();
	int width = 0;
	int height = 0;
	String[][] map;
	Image tileset;
	String line;

	public loadMap(){
        ImageIcon image = new ImageIcon(this.getClass().getResource("tileset.png"));
        tileset = image.getImage();
        try {
			reader =  new BufferedReader(new FileReader("C:\\Users\\Adam\\workspace\\zombieGame\\src\\map\\map1.txt"));
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		while(true){
			try {
				line = reader.readLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if(line == null){
				try {
					reader.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			}
			if(!line.startsWith("#")){
				lines.add(line);
				width = Math.max(width, line.length());
			}
		}

		
		height = lines.size();
		
		map = new String[width][height];

		
		for(int y = 0; y <height;y++){
			String line = (String)lines.get(y);
			for(int x=0; x< line.length(); x++){
				map[x][y] = Character.toString(line.charAt(x));
				if(!Character.isDigit(map[x][y].charAt(0)) || map[x][y] == null)
					map[x][y] = "0";
			}
		}
	}
	
	public String getGroundValue(int x, int y){
		try{
			return map[x][y];
		}catch(Exception ex){ return "-1";}
	}
	
	public void setGroundValue(int x, int y, String value ){
		try{
			map[x][y] = value;
		}catch(Exception ex){ }
	}
	
	
	public void drawGround(Graphics g){
		int tileValue  = 0;

		for(int y = 0; y < 22 ; y++){
			for(int x = 0; x < 24; x++ ){
				tileValue = Integer.parseInt(map[x][y]);
				g.drawImage(tileset,x*32,y*32, x*32 + 32, y*32 + 32, tileValue * 32,0, tileValue*32 + 32, 32,null);
			}
		}
	}
}
