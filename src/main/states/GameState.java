package main.states;

import java.io.File;

import objects.GameObject;
import objects.Player;
import objects.Room;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Transform;
import org.newdawn.slick.particles.ConfigurableEmitter;
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
	public void init(GameContainer gc, StateBasedGame game) throws SlickException {
		System.out.println("Initializing");
		
		r = new Room();
		r.onInit(gc);
		
		Image img = new Image("data/test_particle.png");
		system = new ParticleSystem(img,1500);
	}

	@Override
	public void render(GameContainer gc, StateBasedGame game, Graphics g) throws SlickException {
		r.onRender(g);
		system.render();
	}

	@Override
	public void update(GameContainer gc, StateBasedGame game, int delta) throws SlickException {
		tick++;
		r.onUpdate();
		//gameLogic(gc,game,delta);

	}
	
	/*public void handleInput(GameContainer gc){
		// Print debug output
		if(gc.getInput().isKeyDown(Input.KEY_ESCAPE) && (tick - lastTick > 30 || lastTick == 0)){
			System.out.println(p.getX()+"/"+p.getY());
			lastTick = tick;
			
			
			try{
				File xmlfile = new File("data/test_emitter.xml");
				emitter = ParticleIO.loadEmitter(xmlfile);
				emitter.setPosition(p.getX(), p.getY());
				system.addEmitter(emitter);
			} catch (Exception e){
				e.printStackTrace();
				
			}
		}
		
		// Handle movement
		if(gc.getInput().isKeyDown(Input.KEY_A)){
			p.moveLeft(r);
		} else if(gc.getInput().isKeyDown(Input.KEY_D)){
			p.moveRight(r);
		}
		if(gc.getInput().isKeyDown(Input.KEY_SPACE) && !p.isJumping() && (tick - lastJump >= 20 || lastJump == 0)){
			p.jump(r);
			lastJump = tick;
		}
	}
	
	public void gameLogic(GameContainer gc, StateBasedGame game, int delta){
		handleInput(gc);
		
		system.update(delta);
		
		// Move player
		p.setX(p.getX() + p.getSpeedX());
		p.setY(p.getY() + p.getSpeedY());
		
		p.handleCollisions(r.objects, r, gc);
		
		// Handle friction and gravity
		if(r.getxGravity() == 0) {
			p.setSpeedX(p.getSpeedX() * r.getFriction());
		}
		if(r.getyGravity() == 0) {
			p.setSpeedY(p.getSpeedY() * r.getFriction());
		}
		p.setSpeedX(Math.max(-p.getMaxSpeed(), Math.min(p.getMaxSpeed(),p.getSpeedX() + r.getxGravity())));
		p.setSpeedY(Math.max(-p.getMaxSpeed(), Math.min(p.getMaxSpeed(),p.getSpeedY() + r.getyGravity())));
	//	System.out.println(p.getSpeedY());
		
	}*/


	@Override
	public int getID() {
		return ID;
	}

}
