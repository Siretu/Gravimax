package main.states;

import java.io.File;

import main.Game;
import main.MapReader;
import objects.GameObject;
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

public class GameState extends BasicGameState{
	public static final int ID = 1;
	
	int tick = 0;
	int lastTick = 0;
	int lastJump = 0;
	
	private Room r;
	
	public boolean change = false;
	
	private ParticleSystem system;
	ConfigurableEmitter emitter;

	@Override
	public void init(GameContainer gc, StateBasedGame game) throws SlickException {}
	
	@Override
	public void enter(GameContainer gc, StateBasedGame game) throws SlickException {
		System.out.println("Initializing");
		
		r = new MapReader(((Game)game).getLevel()).getRoom();
		r.onInit(gc);
		
		onInput(gc);
	}
	
	public void onInput(GameContainer gc) {
		Input i = gc.getInput(); //Get reference to instance of Input
		KeyListener kl = new KeyListener() { //Define an implementation of KeyListener
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
		    	case Input.KEY_A: r.getObjects()[0].moveLeft(); break;
		    	case Input.KEY_D: r.getObjects()[0].moveRight(); break;
		    	case Input.KEY_W: r.getObjects()[0].tryJump(r.getGravityDir()); break;
		    	case Input.KEY_SPACE: r.getObjects()[0].tryJump(r.getGravityDir()); break;
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
		  };
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
