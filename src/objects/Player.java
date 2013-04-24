package objects;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

public class Player extends GameObject{
	private boolean isJumping = false;
	private float speed = 4f;
	private float jumpSpeed = 10f;
	private float maxSpeed = 20;

	public Player(Shape s){
		super(s,Color.pink);
		this.speed = 4;
		this.jumpSpeed = 10;
		this.maxSpeed = 20;
	}

	/**
	 * @return the isJumping
	 */
	public boolean isJumping() {
		return isJumping;
	}

	/**
	 * @param isJumping the isJumping to set
	 */
	public void setJumping(boolean isJumping) {
		this.isJumping = isJumping;
	}
	
	public void moveLeft(Room r){
		if(r.getyGravity() != 0){
			setSpeedX(-speed * Math.signum(r.getyGravity()));
		}
		if(r.getxGravity() != 0){
			setSpeedY(speed * Math.signum(r.getxGravity()));
		}
	}
	
	public void moveRight(Room r){
		if(r.getyGravity() != 0){
			setSpeedX(speed * Math.signum(r.getyGravity()));
		}
		if(r.getxGravity() != 0){
			setSpeedY(-speed * Math.signum(r.getxGravity()));
		}
	}
	
	public void jump(Room r){
		if(r.getyGravity() > 0){
			setSpeedY(-jumpSpeed);
		} else if(r.getyGravity() < 0){
			setSpeedY(jumpSpeed);
		}
		if(r.getxGravity() > 0){
			setSpeedX(-jumpSpeed);
		} else if(r.getxGravity() < 0){
			setSpeedX(jumpSpeed);
		}
		setJumping(true);
	}

	/**
	 * @return the speed
	 */
	public float getSpeed() {
		return speed;
	}

	/**
	 * @param speed the speed to set
	 */
	public void setSpeed(float speed) {
		this.speed = speed;
	}

	/**
	 * @return the jumpSpeed
	 */
	public float getJumpSpeed() {
		return jumpSpeed;
	}

	/**
	 * @param jumpSpeed the jumpSpeed to set
	 */
	public void setJumpSpeed(float jumpSpeed) {
		this.jumpSpeed = jumpSpeed;
	}

	/**
	 * @return the maxSpeed
	 */
	public float getMaxSpeed() {
		return maxSpeed;
	}

	/**
	 * @param maxSpeed the maxSpeed to set
	 */
	public void setMaxSpeed(float maxSpeed) {
		this.maxSpeed = maxSpeed;
	}

	
}
