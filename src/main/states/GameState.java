package main.states;

import java.io.File;

import main.Game;
import main.MapReader;
import objects.GameObject;
import objects.Goal;
import objects.Player;
import objects.Room;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.KeyListener;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Transform;
import org.newdawn.slick.particles.ConfigurableEmitter;
import org.newdawn.slick.particles.Particle;
import org.newdawn.slick.particles.ParticleEmitter;
import org.newdawn.slick.particles.ParticleIO;
import org.newdawn.slick.particles.ParticleSystem;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

public class GameState extends BasicGameState{
	public static final int ID = 1;
	
	int tick = 0;
	int lastTick = 0;
	int lastJump = 0;
	
	private Room r;
	
	public boolean change = false;
	

	@Override
	public void init(GameContainer gc, StateBasedGame game) throws SlickException {}
	
	@Override
	public void enter(GameContainer gc, StateBasedGame game) throws SlickException {
		System.out.println("Initializing");
		
		r = new MapReader("level" + ((Game)game).getLevel()+".map").getRoom();
//		r.addObject(new Goal(new Rectangle(400,400,50,50),Color.red));
		r.onInit(gc,game);
		
		
		
		onInput(gc,game);
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
		    public void keyPressed(int key, char c) {
		    	switch(key) {
		    	case Input.KEY_A: r.getObjects()[0].moveLeft(); break;
		    	case Input.KEY_D: r.getObjects()[0].moveRight(); break;
		    	case Input.KEY_W: r.getObjects()[0].tryJump(r.getGravityDir()); break;
		    	case Input.KEY_SPACE: r.getObjects()[0].tryJump(r.getGravityDir()); break;
		    	case Input.KEY_ESCAPE: game.enterState(3, new FadeOutTransition(Color.black), new FadeInTransition(Color.black)); break;
		    	case Input.KEY_TAB: game.enterState(1, new FadeOutTransition(Color.red), new FadeInTransition(Color.red));
		    	default: break;
		    	}
		    }

		    @Override
		    public void keyReleased(int key, char c) {
		    	switch(key) {
		    	case Input.KEY_A: r.getObjects()[0].stopLeft(); break;
		    	case Input.KEY_D: r.getObjects()[0].stopRight(); break;
		    	default: break;
		    	}
		    }
		  }.init(game);
		  i.addKeyListener(kl); //Add the KeyListener to Input so that Input can tell KeyListener when events take place
	}

	@Override
	public void render(GameContainer gc, StateBasedGame game, Graphics g) throws SlickException {
		r.onRender(g);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame game, int delta) throws SlickException {
		tick++;
		r.onUpdate(delta);

	}

	@Override
	public int getID() {
		return ID;
	}


}
