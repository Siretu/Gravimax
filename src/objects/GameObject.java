package objects;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.geom.Shape;

public class GameObject {
	protected float speed = 4f;
	protected float jumpSpeed = 10f;
	protected float maxSpeed = 10f;
	
	protected float y;
	protected float y_speed = 0;
	protected float x;
	protected float x_speed = 0;
	
	protected int type;
	protected boolean canJump = false;
	
	protected Flags flag = new Flags();
	protected boolean moveLeft = false;
	protected boolean moveRight = false;
	
	protected static final int OBJECT_FLAG_GRAVITY = 0x00000001;
	protected static final int OBJECT_FLAG_GHOST = 0x00000002;
	protected static final int OBJECT_FLAG_MAPONLY = 0x00000004;
	protected static final int OBJECT_FLAG_GOAL = 0x00000008;
	
	protected static final int UP = 10;
	protected static final int LEFT = 11;
	protected static final int RIGHT = 12;
	protected static final int DOWN = 13;
	
	protected static final int BLOCK_COLORLESS = -1;
	protected static final int BLOCK_BLUE = 1;
	protected static final int BLOCK_RED = 2;
	protected static final int BLOCK_GREEN = 3;
	protected static final int BLOCK_YELLOW = 4;
	protected static final int BLOCK_BLACK = 5;
	
	/**
	 * Flags class to handle flags for special object attributes.
	 *
	 */
	public class Flags {
		int flag = 0x00000000;
		
		Flags() {}
		
		public boolean has(int n) {
			int temp = flag & n;
			if(temp == n)
				return true;
			return false;
		}
		
		public void add(int n) {
			flag |= n;
		}
	}
	
	public Shape shape;
	public Color c;
	
	
	public GameObject(Shape s, Color c){
		shape = s;
		this.c = c;
		this.x = s.getX() + s.getWidth() / 2;
		this.y = s.getY() + s.getHeight() / 2;
		this.type = BLOCK_COLORLESS;
	}
	
	public GameObject(Shape s, Color c, int blockType){
		this(s,c);
		this.type = blockType;
		this.flag.add(OBJECT_FLAG_MAPONLY);
	}
	
	public void onInit(Room r) {
	}
	
	public void onUpdate(Room room) {
		if(this.flag.has(OBJECT_FLAG_MAPONLY)) 
			return;
		
		this.checkJumpStatus(room.getObjects());
		this.handleInput(room.getGravityDir(), room.getGravity());
		this.onMove(room);
	}
	
	private void checkJumpStatus(GameObject[] objects) {
		for (GameObject obj : objects) {
			if(obj != null) {
				if(!obj.flag.has(OBJECT_FLAG_MAPONLY)) {
					continue;
				}
				if(this.collideDown(obj) == false) {
					this.canJump = false;
				}
			}
		}
	}
	
	private void handleInput(int gravityDir, float gravity) {
		if(moveLeft) {
			if(gravityDir == DOWN) {
				this.x_speed = -this.speed * Math.signum(gravity);
			}
			if(gravityDir == UP) {
				this.x_speed = this.speed * Math.signum(gravity);
			}
			if(gravityDir == RIGHT) {
				this.y_speed = this.speed * Math.signum(gravity);
			}
			if(gravityDir == LEFT) {
				this.y_speed = -this.speed * Math.signum(gravity); 
			}
		}
		
		if(moveRight) {
			if(gravityDir == DOWN) {
				this.x_speed = this.speed * Math.signum(gravity);
			}
			if(gravityDir == UP) {
				this.x_speed = -this.speed * Math.signum(gravity);
			}
			if(gravityDir == RIGHT) {
				this.y_speed = -this.speed * Math.signum(gravity);
			}
			if(gravityDir == LEFT) {
				this.y_speed = this.speed * Math.signum(gravity);
			}
		}
	}
	
	private void onMove(Room room) {
		int gravityDir = room.getGravityDir();
		float gravity = room.getGravity();
		
		this.x += this.x_speed;
		this.y += this.y_speed;
		
		this.checkCollisions(room);
	
		if(gravityDir == UP || gravityDir == DOWN) {
			this.x_speed *= room.getFriction();
			if(gravityDir == UP) {
				this.y_speed -= gravity;
			} else {
				this.y_speed += gravity;
			}
		}
		if(gravityDir == LEFT || gravityDir == RIGHT) {
			this.y_speed *= room.getFriction();
			if(gravityDir == LEFT) {
				this.x_speed -= gravity;
			} else {
				this.x_speed += gravity;
			}
		}
		
		
		this.y_speed = Math.max(-this.maxSpeed, Math.min(this.maxSpeed, this.y_speed));
		this.x_speed = Math.max(-this.maxSpeed, Math.min(this.maxSpeed,this.x_speed));
	}
	
	private void jump(int gravityDir) {
		if(this.canJump) {
			Sound fx;
			try {
				fx = new Sound("data/sounds/jump.wav");
				fx.play(1f,0.1f);
			} catch (SlickException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			switch(gravityDir) {
			case UP: this.y_speed = this.jumpSpeed; break;
			case DOWN: this.y_speed = -this.jumpSpeed; break;
			case LEFT: this.x_speed = this.jumpSpeed; break;
			case RIGHT: this.x_speed = -this.jumpSpeed; break;
			}
		}
	}

	private void onAnimate() {
		//TODO
	}
	
	/**
	 * Checks this object's collisions. If it collides with an object, it'll run onCollide on it and handle the collision.
	 * If the object or the object it collides with has the GHOST flag, they wont collide.
	 * @param room the room to check for collisions in.
	 */
	private void checkCollisions(Room room) {
		if(!this.flag.has(OBJECT_FLAG_GHOST)){
			GameObject objects[] = room.getObjects();
			int gravityDir = room.getGravityDir();
			
			for(GameObject obj : objects) {
				if (obj != null && obj != this && !obj.flag.has(OBJECT_FLAG_GHOST)) {
					if (this.getSpeedY() > 0 && this.collideDown(obj)) {
						this.onCollide(obj, gravityDir, DOWN, room);
					} 
					if (this.getSpeedY() < 0 && this.collideUp(obj)) {
						this.onCollide(obj, gravityDir, UP, room);
					}
					if (this.getSpeedX() < 0 && this.collideLeft(obj)) {
						this.onCollide(obj, gravityDir, LEFT, room);
					}
					if (this.getSpeedX() > 0 && this.collideRight(obj)) {
						this.onCollide(obj, gravityDir, RIGHT, room);
					}
					 
				}
			}
		}
	}
	
	protected void onCollide(GameObject obj, int gravityDir, int colDir, Room room) {
	}
	
	public void onRender(Graphics g){
		this.shape.setCenterX(this.x);
		this.shape.setCenterY(this.y);
		Color oldC = g.getColor();
		g.setColor(c);
		g.fill(shape);
		g.setColor(oldC);
	}
	
	public void correctCollisionLeft(GameObject obj){
		this.setX(obj.getX() + (obj.shape.getWidth() + this.shape.getWidth()) /2);
	}
	
	public void correctCollisionRight(GameObject obj){
		this.setX(obj.getX() - (obj.shape.getWidth() + this.shape.getWidth()) /2);
	}
	
	public void correctCollisionUp(GameObject obj){
		this.setY(obj.getY() + (obj.shape.getHeight() + this.shape.getHeight()) /2);
	}
	
	public void correctCollisionDown(GameObject obj){
		this.setY(obj.getY() - (obj.shape.getHeight() + this.shape.getHeight()) /2);
	}
	
	/**
	 * @param obj object to check collisions with.
	 * @return true if this collides with obj from the left, false otherwise.
	 */
	public boolean collideLeft(GameObject obj){
		if( (x - shape.getWidth() / 2 <= obj.getX() + obj.shape.getWidth() / 2) &&
			(x + shape.getWidth() / 2 >= obj.getX()) && 
			(y + shape.getHeight() / 2 > obj.getY() - obj.shape.getHeight() / 2 + 10) && 
			(y - shape.getHeight() / 2 + 5 < obj.getY() + obj.shape.getHeight() / 2)) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * @param obj object to check collisions with.
	 * @return true if this collides with obj from the right, false otherwise.
	 */
	public boolean collideRight(GameObject obj){
		if( (x - shape.getWidth() / 2 <= obj.getX()) && 
			(x + shape.getWidth() / 2 >= obj.getX() - obj.shape.getWidth() / 2) && 
			(y + shape.getHeight() / 2 > obj.getY() - obj.shape.getHeight() / 2 + 10) && 
			(y - shape.getHeight() / 2 + 5 < obj.getY() + obj.shape.getHeight() / 2)) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * @param obj object to check collisions with.
	 * @return true if this collides with obj from below, false otherwise.
	 */
	public boolean collideDown(GameObject obj){
		if( (x + shape.getWidth() / 2 > obj.getX() - obj.shape.getWidth() / 2 + 10) && 
			(x - shape.getWidth() / 2 + 5< obj.getX() + obj.shape.getWidth() / 2) && 
			(y - shape.getHeight() / 2 < obj.getY() ) && 
			(y + shape.getHeight() / 2 > obj.getY() - obj.shape.getHeight() / 2)){
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * @param obj object to check collisions with.
	 * @return true if this collides with obj from above, false otherwise.
	 */
	public boolean collideUp(GameObject obj){
		if( (x + shape.getWidth() / 2 > obj.getX() - obj.shape.getWidth() / 2 + 10) && 
			(x - shape.getWidth() / 2 + 5 < obj.getX() + obj.shape.getWidth() / 2) && 
			(y - shape.getHeight() / 2 < obj.getY() + obj.shape.getHeight() / 2) && 
			(y + shape.getHeight() / 2 > obj.getY())){
			return true;
		} else {
			return false;
		}
	}
	
	
	/**
	 * @return the x
	 */
	public float getX() {
		return x;
	}

	/**
	 * @param x the x to set
	 */
	public void setX(float x) {
		this.x = x;
	}

	/**
	 * @return the y
	 */
	public float getY() {
		return y;
	}

	/**
	 * @param y the y to set
	 */
	public void setY(float y) {
		this.y = y;
	}

	/**
	 * @return the x_speed
	 */
	public float getSpeedX() {
		return x_speed;
	}

	/**
	 * @param x_speed the x_speed to set
	 */
	public void setSpeedX(float x_speed) {
		this.x_speed = x_speed;
	}

	/**
	 * @return the y_speed
	 */
	public float getSpeedY() {
		return y_speed;
	}

	/**
	 * @param y_speed the y_speed to set
	 */
	public void setSpeedY(float y_speed) {
		this.y_speed = y_speed;
	}

	/**
	 * @return the colored
	 */
	public boolean isColored() {
		if (this.type == BLOCK_COLORLESS)
			return false;
		return true;
	}
	
	public int getColorID(){
		return this.type;
	}

	/**
	 * @param colored the colored to set
	 */
	public void setColored(int newType) {
		this.type = newType;
	}
	
	public void moveLeft() {
		this.moveLeft = true;
	}
	
	public void moveRight() {
		this.moveRight = true;
	}
	
	public void stopLeft() {
		this.moveLeft = false;
	}
	
	public void stopRight() {
		this.moveRight = false;
	}
	
	public void tryJump(int gravityDir) {
		this.jump(gravityDir);
	}
}
