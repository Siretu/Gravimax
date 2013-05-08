package main.states;

import java.awt.Font;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Arrays;

import main.MapReader;
import main.Stopwatch;

import objects.Level;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.KeyListener;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.RoundedRectangle;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

public class SelectLevelState extends BasicGameState{
	public static final int ID = 4;
	public static final int startLevelX = 100;
	public static final int startLevelY = 100;
	public static final int levelGap = 20;
	
	public Level[] levels;

	@Override
	public void enter(GameContainer gc, StateBasedGame game) throws SlickException {
		
	}
	
	@Override
	public void init(GameContainer gc, StateBasedGame game) throws SlickException {
		onInput(gc,game);
		
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
		int lastComplete = 0;
		for(File f : foundFiles){
			BufferedReader reader = null;
			String highscore = "";
			String lastTry = "";
			Color c = Color.red;
			try {
				String name = "data/maps/"+f.getName().split("\\.")[0] + ".score";
				File score = new File(name);
//				System.out.println("Searching for file \"" + name + "\". Exists = " + score.exists());
				if(score.exists()){
					reader = new BufferedReader(new FileReader(name));
					highscore = Stopwatch.toString(Integer.parseInt(reader.readLine()));
					lastTry = Stopwatch.toString(Integer.parseInt(reader.readLine()));
					lastComplete++;
					c = Color.green;
				} else {
					highscore = "None";
					lastTry = "None";
				}
			} catch (IOException e) {
				System.err.printf("%s%n in MapReader", e);
			} finally {
				try {
					if (reader != null) {
						reader.close();
					}
				} catch (IOException e) {
					System.err.printf("%s%n in MapReader", e);
				}
			}
			
			if(currIndex == lastComplete || (currIndex == 0 && !c.equals(Color.green))){
				c = Color.blue;
			}
			levels[currIndex] = new Level(f.getName(), currX, currY,c,currIndex+1,highscore,lastTry);
			currIndex++;
			currX += levelGap + Level.levelWidth;
			if(currX+Level.levelWidth > gc.getWidth() - startLevelX){
				currX = startLevelX;
				currY += levelGap + Level.levelHeight;
			}
			
		}
		
	}
	
	public void onInput(GameContainer gc, StateBasedGame game) {
		Input i = gc.getInput(); //Get reference to instance of Input
		KeyListener kl = new KeyListener() { //Define an implementation of KeyListener
			StateBasedGame game;
			
			public KeyListener init(StateBasedGame g){
				this.game = g;
				return this;
			}
			
			@Override
		    public void inputStarted() {} 
			  
		   @Override
		    public void inputEnded() {}

		    @Override
		    public boolean isAcceptingInput() {
		       return true;
		    }

		    @Override
		    public void setInput(Input arg0) {}

		    @Override
		    public void keyPressed(int key, char c) {;
		    	switch(key) {
		    	case Input.KEY_ESCAPE: game.enterState(3, new FadeOutTransition(Color.black), new FadeInTransition(Color.black)); break;
		    	default: break;
		    	}
		    }

			@Override
			public void keyReleased(int arg0, char arg1) {
				// TODO Auto-generated method stub
				
			}

		  }.init(game);
		  i.addKeyListener(kl); //Add the KeyListener to Input so that Input can tell KeyListener when events take place
	}

	@Override
	public void render(GameContainer gc, StateBasedGame game, Graphics g) throws SlickException {
		for(Level l : levels){
			l.onRender(gc, g, game);
		}
		for(Level l : levels){
			l.onHover(gc, g, game);
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
