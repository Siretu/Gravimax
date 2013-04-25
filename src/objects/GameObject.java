package objects;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Shape;

public class GameObject {
	protected float x;
	protected float y;
	protected float x_speed = 0;
	protected float y_speed = 0;
	protected boolean colored = false;
	
	public Shape shape;
	public Color c;
	
	
	public GameObject(Shape s, Color c){
		shape = s;
		this.c = c;
		this.x = s.getX() + s.getWidth() / 2;
		this.y = s.getY() + s.getHeight() / 2;
//		shape.setCenterX(this.x);
//		shape.setCenterY(this.y);
	}
	
	public GameObject(Shape s, Color c, boolean colored){
		this(s,c);
		this.colored = true;
	}
	
	public void draw(Graphics g){
		shape.setCenterX(x);
		shape.setCenterY(y);
		Color oldC = g.getColor();
		g.setColor(c);
		g.fill(shape);
		g.setColor(oldC);
	}
	
	
	public boolean handleCollisions(GameObject[] objects, Room r, GameContainer gc){
		for(GameObject obj : objects){
			if(obj != null && obj != this){ // Avoid nullpointers and colliding with itself
				if(this.getSpeedX() < 0 && this.collideLeft(obj)){
					this.setSpeedX(0);
					if(r.getxGravity() < 0 && this instanceof Player){ // Reset jump
						// Ugly hack to do player-specific stuff. Thought a lot about it and can't think of a nice way.
						((Player) this).setJumping(false); 
					}
					if(obj.isColored()){
						r.rotateGravity(gc, obj.getColorID(),this);
						this.correctCollisionLeft(obj);
					} else {
						this.correctCollisionLeft(obj);
					}
					System.out.println("Collided left");
				}
				if(this.getSpeedX() > 0 && this.collideRight(obj)){
					this.setSpeedX(0);
					if(r.getxGravity() > 0 && this instanceof Player){ // Reset jump
						// Ugly hack to do player-specific stuff. Thought a lot about it and can't think of a nice way.
						((Player) this).setJumping(false); 
					}
					if(obj.isColored()){
						r.rotateGravity(gc, obj.getColorID(),this);
						this.correctCollisionRight(obj);
					} else {
						this.correctCollisionRight(obj);
					}
					System.out.println("Collided right");
				}
				if(this.getSpeedY() > 0 && this.collideDown(obj)){
					this.setSpeedY(0);
					if(r.getyGravity() > 0 && this instanceof Player){ // Reset jump
						// Ugly hack to do player-specific stuff. Thought a lot about it and can't think of a nice way.
						((Player) this).setJumping(false); 
					}
					r.setxGravity(0);
					r.setyGravity(r.getGravity());
					this.correctCollisionDown(obj);
				}
			}
		}
		
		return true;
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
	
	
	public boolean collideLeft(GameObject obj){
		if( (x - shape.getWidth() / 2 <= obj.getX() + obj.shape.getWidth() / 2) &&
			(x + shape.getWidth() / 2 >= obj.getX()) && 
			(y + shape.getHeight() / 2 > obj.getY() - obj.shape.getHeight() / 2 + 5) && 
			(y - shape.getHeight() / 2 + 5 < obj.getY() + obj.shape.getHeight() / 2)){
			return true;
		} else {
			return false;
		}
	}
	
	public boolean collideRight(GameObject obj){
		if( (x - shape.getWidth() / 2 <= obj.getX()) && 
			(x + shape.getWidth() / 2 >= obj.getX() - obj.shape.getWidth() / 2) && 
			(y + shape.getHeight() / 2 > obj.getY() - obj.shape.getHeight() / 2 + 5) && 
			(y - shape.getHeight() / 2 + 5 < obj.getY() + obj.shape.getHeight() / 2)){
			return true;
		} else {
			return false;
		}
	}
	
	public boolean collideDown(GameObject obj){
		if( (x + shape.getWidth() / 2 > obj.getX() - obj.shape.getWidth() / 2 + 5) && 
			(x - shape.getWidth() / 2 + 5< obj.getX() + obj.shape.getWidth() / 2) && 
			(y - shape.getHeight() / 2 < obj.getY() ) && 
			(y + shape.getHeight() / 2 > obj.getY() - obj.shape.getHeight() / 2)){
			return true;
		} else {
			return false;
		}
	}
	
	public boolean collideUp(GameObject obj){
		if( (x + shape.getWidth() / 2 > obj.getX() - obj.shape.getWidth() / 2 + 5) && 
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
//		System.out.println("modified x");
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
		return colored;
	}
	
	public int getColorID(){
		if(colored){
			if(c.b == 1){
				return 0;
			} else if(c.r == 1 && c.g == 0){
				return 1;
			} else if(c.g == 1 && c.r == 0){
				return 2;
			} else if(c.g == 1 && c.r == 1){
				return 3;
			}
		}
		return -1;
	}

	/**
	 * @param colored the colored to set
	 */
	public void setColored(boolean colored) {
		this.colored = colored;
	}
}
