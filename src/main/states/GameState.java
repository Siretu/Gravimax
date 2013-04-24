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
		r.objects[5] = new GameObject(new Rectangle(500,650,50,50), Color.red,true);
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
		
		system.render();
		
	}

	@Override
	public void update(GameContainer gc, StateBasedGame game, int delta) throws SlickException {
		tick++;
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
//		if(emitter != null){
//			emitter.setPosition(p.getX(), p.getY());
//		}
//		system.setPosition(p.getX(),p.getY());
		system.update(delta);
		if(system != null){
			
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
		
		
		
		// Move player
		p.setX(p.getX() + p.getSpeedX());
		p.setY(p.getY() + p.getSpeedY());
		
		
		// Handle all collisions
		GameObject collisionObject = null;
		GameObject collisionObject2 = null;
		int dir = 1;
		int dir2 = 1;
		
		// Loop through all r.objects and check for collision with player
		for(GameObject obj : r.objects){
			if(obj != null && obj != p){
				if(p.getSpeedX() < 0 && p.collideLeft(obj) && Math.abs(p.getX() - (obj.getX() + obj.shape.getWidth() * 1)) < 50){ // Player collided with an object to the left
					System.out.println("pX: " + p.getX());
					System.out.println("obj width: "+ obj.shape.getWidth());
					System.out.println("obj X: "+ obj.getX());
					p.setSpeedX(0);
					if(r.getxGravity() < 0){
						System.out.println("Fixed jumping");
						p.setJumping(false);
					}
					if(obj.isColored()){
						rotateGravity(gc, obj.getColorID());
						collisionObject2 = obj;
						dir2 = -1;
					} else {
						r.setyGravity(0);
						r.setxGravity(-r.getGravity());
						collisionObject = obj;
						dir = 1;
					}
				} else if(p.getSpeedX() > 0 && p.collideRight(obj) && Math.abs(p.getX() - (obj.getX() + obj.shape.getWidth() * -1)) < 50){ // Player collided with something to the right
					p.setSpeedX(0);
					if(r.getxGravity() > 0){
						p.setJumping(false);
					}
					if(obj.isColored()){
						rotateGravity(gc, obj.getColorID());
						collisionObject2 = obj;
						dir2 = -1;
					} else {
						r.setyGravity(0);
						r.setxGravity(r.getGravity());
						collisionObject = obj;
						dir = -1;
					}
//					System.out.println("Collided right");
				} 
				
				if(p.getSpeedY() > 0 && p.collideDown(obj) && Math.abs(p.getY() - (obj.getY() + (obj.shape.getHeight()/2 + p.shape.getWidth()/2) * -1)) < 20){ // Player collided with an object below
					collisionObject2 = obj;
					dir2 = -1;
					p.setSpeedY(0);
					if(r.getyGravity() > 0){
						p.setJumping(false);
					}
					r.setxGravity(0);
					r.setyGravity(r.getGravity());
//					System.out.println("Collided down");
				} else if(p.getSpeedY() < 0 && p.collideUp(obj) && Math.abs(p.getY() - (obj.getY() + (obj.shape.getHeight()/2 + p.shape.getHeight() / 2) * 1)) < 20){ // Player collided with an object above
					p.setSpeedY(0);
					if(r.getyGravity() < 0){
						p.setJumping(false);
					}
					collisionObject2 = obj;
					if(obj.isColored()){
						System.out.println("Collided upwards");
						rotateGravity(gc, obj.getColorID());
						
						dir2 = -1;
					} else {
						r.setxGravity(0);
						r.setyGravity(-r.getGravity());
						
						
					}
//					System.out.println("Collided up");
				}
					
			}
		}

		// Correct the collision so it doesn't go through solid r.objects but only do it if the distance is not too long (to prevent collision with several sides of one object)
		if(collisionObject != null){
			System.out.println("Object X: " + collisionObject.getX());
			System.out.println("Width: " + collisionObject.shape.getWidth());
			System.out.println("Dir: " + dir);
			System.out.println("playerX : " + p.getX() + "/" + p.getY());
			p.setX(collisionObject.getX() + (collisionObject.shape.getWidth()/2 + p.shape.getWidth() / 2)* dir);
			System.out.println("Corrected playerX to: " + p.getX() + "/" + p.getY());
		}
		if(collisionObject2 != null){
//			System.out.println("Object Y: " + collisionObject2.getY());
//			System.out.println("Height: " + collisionObject2.shape.getHeight());
//			System.out.println("Dir2: " + dir2);
			p.setY(collisionObject2.getY() + (collisionObject2.shape.getHeight()/2 + p.shape.getHeight() / 2) * dir2);
//			System.out.println("Corrected playerY to: " + p.getX() + "/" + p.getY());
		}
		
		// Handle friction and gravity
		if(r.getxGravity() == 0){
			p.setSpeedX(p.getSpeedX() * r.getFriction());
		}
		if(r.getyGravity() == 0) {
			p.setSpeedY(p.getSpeedY() * r.getFriction());
		}
		p.setSpeedX(Math.max(-p.getMaxSpeed(), Math.min(p.getMaxSpeed(),p.getSpeedX() + r.getxGravity())));
		p.setSpeedY(Math.max(-p.getMaxSpeed(), Math.min(p.getMaxSpeed(),p.getSpeedY() + r.getyGravity())));
//		System.out.println(p.getSpeedY());
		
	}
	
	public void rotateGravity(GameContainer gc, int side){
		r.setxGravity(0);
		r.setyGravity(r.getGravity());
		int turn = -1;
		if(r.objects[side].getX() == 400 && r.objects[side].getY() == 25){
			turn = 2;
		} else if(r.objects[side].getX() == 25 && r.objects[side].getY() == 400){
			turn = 1;
		} else if(r.objects[side].getX() == 775 && r.objects[side].getY() == 400){
			turn = 3;
		} else {
			System.out.println("Fucked up turn: "+ r.objects[side].getX() + " | " + r.objects[side].getY());
			return;
		}
		for(GameObject obj : r.objects){
			if(obj != null){
				obj.setSpeedX(0);
				obj.setSpeedY(0);
				if(turn == 2){
					obj.setX(gc.getWidth() - obj.getX());
					obj.setY(gc.getHeight() - obj.getY());
					obj.shape = obj.shape.transform(Transform.createRotateTransform((float) (Math.PI)));
				} else {
					// Magic formula for rotating entire screen
					float tempX = obj.getX();
					float tempY = obj.getY();
				
					obj.setX(tempY * (-turn + 2) + gc.getWidth() * (1/2f*turn - 1/2f));
					obj.setY(tempX * (turn - 2) + gc.getHeight() * (-1/2f*turn + 3/2f));
					obj.shape = obj.shape.transform(Transform.createRotateTransform((float) (Math.PI/2)));
					if(obj.equals(p)){
						System.out.println("Yes, this is player");
						System.out.println(obj.shape.getX() + "/" + obj.shape.getY());
					}
					System.out.println("Converted from: " + tempX + "/" + tempY + " to: " + obj.getX() + "/" + obj.getY());
				}
			}
		}
	}

	@Override
	public int getID() {
		return ID;
	}

}
