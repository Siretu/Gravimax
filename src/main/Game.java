package main;

import main.states.GameState;
import main.states.MenuState;

import org.newdawn.slick.*;
import org.newdawn.slick.state.StateBasedGame;

public class Game extends StateBasedGame{

	
	public Game() {
		super("Gravimax");
	}
	
	public void run() throws SlickException{

		AppGameContainer app = new AppGameContainer(new Game());
		app.setTargetFrameRate(60);
		app.setDisplayMode(800,800, false);
		app.start();
	}

	
	
	@Override
	public void initStatesList(GameContainer arg0) throws SlickException {
		
		addState(new GameState());
		addState(new MenuState());
		
	}
}
