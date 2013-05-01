package objects;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.KeyListener;
import org.newdawn.slick.command.InputProvider;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Transform;
import org.newdawn.slick.particles.ParticleSystem;

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
	
	private int gcWidth;
	private int gcHeight;
	
	private InputProvider provider;

	private GameObject[] objects;
	
	public Room(Color c, float friction, float gravity, float xGrav, float yGrav){
		this.background = c;
		this.friction = friction;
		this.gravity = gravity;
	}
	
	public Room(){
		this.background = new Color(130,130,130);
		this.friction = 0.88f;
		this.gravity = 0.5f;
	}
	
	public void onInit(GameContainer gc) {
		gcWidth = gc.getWidth();
		gcHeight = gc.getHeight();
		  
		//TEMPORARY; Need to implement RoomReader
		GameObject p = new Player(new Rectangle(400,600,50,50));
		
		objects = new GameObject[20];
		objects[0] = p;
		objects[1] = new GameObject(new Rectangle(50,750,700,50),Color.blue,BLOCK_BLUE);
		objects[2] = new GameObject(new Rectangle(0,50,50,700),Color.red,BLOCK_RED);
		objects[3] = new GameObject(new Rectangle(50,0,700,50),Color.green,BLOCK_GREEN);
		objects[4] = new GameObject(new Rectangle(750,50,50,700),Color.yellow,BLOCK_YELLOW);
		objects[5] = new GameObject(new Rectangle(680,680,50,50), Color.yellow,BLOCK_YELLOW);
		objects[6] = new GameObject(new Rectangle(150,560,100,100),Color.black,BLOCK_BLACK);
		
		for(GameObject go : objects) {
			if(go != null) {
				go.onInit();
			}
		}
		
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
		    public void keyPressed(int key, char c) {
		    	switch(key) {
		    	case Input.KEY_A: objects[0].moveLeft(); break;
		    	case Input.KEY_D: objects[0].moveRight(); break;
		    	case Input.KEY_W: objects[0].tryJump(gravityDir); break;
		    	default: break;
		    	}
		    }

		    @Override
		    public void keyReleased(int key, char c) {
		    	switch(key) {
		    	case Input.KEY_A: objects[0].stopLeft(); break;
		    	case Input.KEY_D: objects[0].stopRight(); break;
		    	default: break;
		    	}
		    }
		  };
		  i.addKeyListener(kl); //Add the KeyListener to Input so that Input can tell KeyListener when events take place
	}
	
	public void onUpdate() {
		for(GameObject obj : objects) {
			if(obj != null) {
				obj.onUpdate(this);
			}
		}
	}
	
	public void onRender(Graphics g) {
		g.setBackground(background);
		for(GameObject go : objects) {
			if(go != null) {
				go.onRender(g);
			}
		}
	}
	
	
	public void rotateGravity(int side, GameObject p){
		this.setGravityDir(DOWN);
		int turn = -1;
		if(objects[side].getX() == 400 && objects[side].getY() == 25){
			turn = 2;
		} else if(objects[side].getX() == 25 && objects[side].getY() == 400){
			turn = 1;
		} else if(objects[side].getX() == 775 && objects[side].getY() == 400){
			turn = 3;
		} else {
//			System.out.println("Fucked up turn: "+ objects[side].getX() + " | " + objects[side].getY());
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
					if(obj.equals(p)){
						System.out.println("Yes, this is player");
						System.out.println(obj.shape.getX() + "/" + obj.shape.getY());
					}
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
	
	public void setGravityDir(int gravityDir) {
		this.gravityDir = gravityDir;
	}
	
	public GameObject[] getObjects() {
		return objects;
	}
}
