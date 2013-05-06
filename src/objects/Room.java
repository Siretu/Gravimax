package objects;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.KeyListener;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.command.InputProvider;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Transform;
import org.newdawn.slick.particles.ParticleSystem;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

public class Room {
	private static final int UP = 10;
	private static final int LEFT = 11;
	private static final int RIGHT = 12;
	private static final int DOWN = 13;
	
	protected static final int BLOCK_COLORLESS = -1;
	protected static final int BLOCK_BLUE = 1;
	protected static final int BLOCK_RED = 2;
	protected static final int BLOCK_GREEN = 3;
	protected static final int BLOCK_YELLOW = 4;
	protected static final int BLOCK_BLACK = 5;
	
	private Color background = new Color(130,130,130);
	private float friction = 0.88f;
	private float gravity = 0.5f;
	private int gravityDir = DOWN;
	private int gravityColor;
	
	private int gcWidth;
	private int gcHeight;
	
	private InputProvider provider;

	private GameObject[] objects;
	private ParticleSystem particleSystem;
	
	public Room(Color c, float friction, float gravity){
		this.background = c;
		this.friction = friction;
		this.gravity = gravity;
		this.gravityColor = BLOCK_BLUE;
	}
	
	public Room(){
		this.background = new Color(130,130,130);
		this.friction = 0.88f;
		this.gravity = 0.5f;
	}
	
	public void onInit(GameContainer gc) throws SlickException {
		Image img = new Image("data/test_particle.png");
		particleSystem = new ParticleSystem(img, 1500);
		gcWidth = gc.getWidth();
		gcHeight = gc.getHeight();
		
		for(GameObject obj : objects) {
			if(obj != null) {
				obj.onInit();
			}
		}
		
	}
	

	
	public void onUpdate(int delta) {
		for(GameObject obj : objects) {
			if(obj != null) {
				obj.onUpdate(this);
			}
		}
		particleSystem.update(delta);
	}
	
	public void onRender(Graphics g) {
		g.setBackground(background);
		for(GameObject go : objects) {
			if(go != null) {
				go.onRender(g);
			}
		}
		particleSystem.render();
	}
	
	/**
	 * Rotates the entire screen and all objects in the room towards the specified side. 
	 * It also switches gravity downwards.
	 * @param side specifies the side to rotate downwards.
	 */
	public void rotateGravity(int side){
		this.setGravityDir(DOWN);
		this.gravityColor = side;
		int turn = -1;
		if(objects[side].getX() == 400 && objects[side].getY() == 25){
			turn = 2;
		} else if(objects[side].getX() == 25 && objects[side].getY() == 400){
			turn = 1;
		} else if(objects[side].getX() == 775 && objects[side].getY() == 400){
			turn = 3;
		} else {
			return;
		}
		for(GameObject obj : objects){
			if(obj != null){
				obj.setSpeedX(0);
				obj.setSpeedY(0);
				if(turn == 2){
					obj.setX(gcWidth - obj.getX());
					obj.setY(gcHeight - obj.getY());
					obj.shape = obj.shape.transform(Transform.createRotateTransform((float) (Math.PI)));
				} else {
					// Magic formula for rotating entire screen
					float tempX = obj.getX();
					float tempY = obj.getY();
				
					obj.setX(tempY * (-turn + 2) + gcWidth * (1/2f*turn - 1/2f));
					obj.setY(tempX * (turn - 2) + gcHeight * (-1/2f*turn + 3/2f));
					obj.shape = obj.shape.transform(Transform.createRotateTransform((float) (Math.PI/2)));
					System.out.println("Converted from: " + tempX + "/" + tempY + " to: " + obj.getX() + "/" + obj.getY());
				}
			}
		}
	}

	/**
	 * @return the background
	 */
	public Color getBackground() {
		return background;
	}

	/**
	 * @param background the background to set
	 */
	public void setBackground(Color background) {
		this.background = background;
	}

	/**
	 * @return the friction
	 */
	public float getFriction() {
		return friction;
	}

	/**
	 * @param friction the friction to set
	 */
	public void setFriction(float friction) {
		this.friction = friction;
	}

	/**
	 * @return the gravity
	 */
	public float getGravity() {
		return gravity;
	}

	/**
	 * @param gravity the gravity to set
	 */
	public void setGravity(float gravity) {
		this.gravity = gravity;
	}

	public int getGravityDir() {
		return gravityDir;
	}
	
	/**
	 * @param gravityDir the gravity direction to set
	 */
	public void setGravityDir(int gravityDir) {
		this.gravityDir = gravityDir;
	}
	
	/**
	 * @return the objects in the room
	 */
	public GameObject[] getObjects() {
		return objects;
	}
	
	public void setObjects(GameObject[] obj) {
		objects = obj;
	}
	
	public int getGravityColor() {
		return this.gravityColor;
	}
}
