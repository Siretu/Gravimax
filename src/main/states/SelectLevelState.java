package main.states;

import java.awt.Font;
import java.io.File;
import java.io.FilenameFilter;

import main.MapReader;

import objects.Level;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.RoundedRectangle;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class SelectLevelState extends BasicGameState{
	public static final int ID = 4;
	public static final int startLevelX = 100;
	public static final int startLevelY = 100;
	public static final int levelGap = 20;
	
	private Level[] levels;

	@Override
	public void init(GameContainer gc, StateBasedGame game) throws SlickException {

		Color[] colors = {Color.red, Color.blue, Color.green, Color.yellow};
		
		File dir = new File("data/maps");
		File[] foundFiles = dir.listFiles(new FilenameFilter() {
		    public boolean accept(File dir, String name) {
		        return name.matches("level\\d+\\.map");
		    }
		});
		
		levels = new Level[foundFiles.length];
		
		int currX = startLevelX;
		int currY = startLevelY;
		int currIndex = 0;
		
		for(File f : foundFiles){
			levels[currIndex] = new Level(f.getName(), currX, currY,colors[currIndex % colors.length],currIndex+1);
			currIndex++;
			currX += levelGap + Level.levelWidth;
			if(currX+Level.levelWidth > gc.getWidth() - startLevelX){
				currX = startLevelX;
				currY += levelGap + Level.levelHeight;
			}
			
		}
		
	}

	@Override
	public void render(GameContainer gc, StateBasedGame game, Graphics g) throws SlickException {
		for(Level l : levels){
			l.onRender(gc, g, game);
		}
	}

	@Override
	public void update(GameContainer gc, StateBasedGame game, int delta) throws SlickException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getID() {
		return ID;
	}

}
