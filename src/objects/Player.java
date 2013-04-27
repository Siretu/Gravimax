package objects;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

public class Player extends GameObject {
	public Player(Shape s){
		super(s,Color.pink);
		canJump = true;
	}
	
	@Override
	public void onInit() {
		flag.add(OBJECT_FLAG_GRAVITY);
	}
	
	@Override
	protected void onCollide(GameObject obj, int gravityDir, int colDir, Room room) {
		if(obj.flag.has(OBJECT_FLAG_MAPONLY)) {
			if(gravityDir == colDir) {
				this.canJump = true;
			}
			if(gravityDir == UP || gravityDir == DOWN) {
				switch(colDir) {
				case UP: 
					this.correctCollisionUp(obj); 
					this.y_speed = 0;
					System.out.println("Collided: " + gravityDir + " " + colDir);
					break;
				case DOWN:
					this.correctCollisionDown(obj);
					room.setGravityDir(DOWN);
					this.y_speed = 0;
					break;
				case LEFT: 
					this.correctCollisionLeft(obj); 
					this.x_speed = 0;
					System.out.println("Collided: " + gravityDir + " " + colDir);
					break;
				case RIGHT: 
					this.correctCollisionRight(obj); 
					this.x_speed = 0;
					System.out.println("Collided: " + gravityDir + " " + colDir);
					break;
				}
			} else {
				switch(colDir) {
				case UP: 
					this.correctCollisionUp(obj); 
					this.x_speed = 0;
					System.out.println("Collided: " + gravityDir + " " + colDir);
					break;
				case DOWN: 
					this.correctCollisionDown(obj);
					room.setGravityDir(DOWN);
					this.x_speed = 0;
					break;
				case LEFT: 
					this.correctCollisionLeft(obj); 
					this.y_speed = 0;
					System.out.println("Collided: " + gravityDir + " " + colDir);
					break;
				case RIGHT: 
					this.correctCollisionRight(obj); 
					this.y_speed = 0;
					System.out.println("Collided: " + gravityDir + " " + colDir);
					break;			
				}
			}
	
			if(obj.getColorID() != -1) {
				if(obj.getColorID() == BLOCK_BLACK) {
					room.setGravityDir(colDir);
				} else {
					if(colDir != DOWN) {
						room.rotateGravity(obj.getColorID(), this);
					}
				}
			}
		}
	}

	/*
	public void resetJump(){
		this.setJumping(false); 
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
	*/
}
