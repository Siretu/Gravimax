package objects;

import org.newdawn.slick.Color;

public class Room {

	private Color background = new Color(130,130,130);
	private float friction = 0.88f;
	private float gravity = 0.5f;
	
	private float xGravity;
	private float yGravity;


	public GameObject[] objects;
	
	public Room(Color c, float friction, float gravity, float xGrav, float yGrav){
		this.background = c;
		this.friction = friction;
		this.gravity = gravity;
		this.xGravity = xGrav;
		this.yGravity = yGrav;
	}
	
	public Room(){
		this.background = new Color(130,130,130);
		this.friction = 0.88f;
		this.gravity = 0.5f;
		this.xGravity = 0;
		this.yGravity = this.gravity;
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

	/**
	 * @return the xGravity
	 */
	public float getxGravity() {
		return xGravity;
	}

	/**
	 * @param xGravity the xGravity to set
	 */
	public void setxGravity(float xGravity) {
		this.xGravity = xGravity;
	}

	/**
	 * @return the yGravity
	 */
	public float getyGravity() {
		return yGravity;
	}

	/**
	 * @param yGravity the yGravity to set
	 */
	public void setyGravity(float yGravity) {
		this.yGravity = yGravity;
	}

	
}
