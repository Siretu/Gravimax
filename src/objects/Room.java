package objects;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.geom.Transform;

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
	
	
	public void rotateGravity(GameContainer gc, int side, GameObject p){
		setxGravity(0);
		setyGravity(getGravity());
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
