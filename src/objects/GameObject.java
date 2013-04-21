package objects;

import org.newdawn.slick.Color;
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

	/**
	 * @param colored the colored to set
	 */
	public void setColored(boolean colored) {
		this.colored = colored;
	}
}
