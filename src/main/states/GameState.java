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
	private Player p;
	
	private ParticleSystem system;
	ConfigurableEmitter emitter;
	
	@Override
	public void init(GameContainer gc, StateBasedGame game) throws SlickException {
		System.out.println("Initializing");
		
		r = new Room();
		
		p = new Player(new Rectangle(400,600,50,50));
		
		Image img = new Image("data/test_particle.png");
		system = new ParticleSystem(img,1500);
		
		r.objects = new GameObject[20];
		r.objects[0] = new GameObject(new Rectangle(50,750,700,50),Color.blue,true);
		r.objects[1] = new GameObject(new Rectangle(0,50,50,700),Color.red,true);
		r.objects[2] = new GameObject(new Rectangle(50,0,700,50),Color.green,true);
		r.objects[3] = new GameObject(new Rectangle(750,50,50,700),Color.yellow,true);
		r.objects[4] = p;
		r.objects[5] = new GameObject(new Rectangle(680,680,50,50), Color.yellow,true);
		r.objects[6] = new GameObject(new Rectangle(150,560,50,90),Color.black);
	}

	@Override
	public void render(GameContainer gc, StateBasedGame game, Graphics g) throws SlickException {
		g.setBackground(r.getBackground());
		
		// Draw Borders
		for (GameObject b : r.objects){
			if(b != null){
				b.draw(g);
			}
		}
		
		p.draw(g);
		g.setColor(Color.white);
		g.drawLine(475, 0, 475, 800);
		g.drawLine(0, 725, 800, 725);
		
		
		g.setColor(Color.black);
		g.drawLine(725, 0, 725, 800);
		g.drawLine(0, 325, 800, 325);
		
		system.render();
		
	}

	@Override
	public void update(GameContainer gc, StateBasedGame game, int delta) throws SlickException {
		tick++;
		
		gameLogic(gc,game,delta);

	}
	
	public void handleInput(GameContainer gc){
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
		if(r.getxGravity() == 0){
			p.setSpeedX(p.getSpeedX() * r.getFriction());
		}
		if(r.getyGravity() == 0) {
			p.setSpeedY(p.getSpeedY() * r.getFriction());
		}
		p.setSpeedX(Math.max(-p.getMaxSpeed(), Math.min(p.getMaxSpeed(),p.getSpeedX() + r.getxGravity())));
		p.setSpeedY(Math.max(-p.getMaxSpeed(), Math.min(p.getMaxSpeed(),p.getSpeedY() + r.getyGravity())));
	//	System.out.println(p.getSpeedY());
		
	}


	@Override
	public int getID() {
		return ID;
	}

}
