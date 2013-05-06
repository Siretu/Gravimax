package objects;

import java.io.File;

import main.Game;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

public class Player extends GameObject {
	public Player(Shape s){
		super(s,Color.pink);
		canJump = true;
	}
	
	@Override
	public void onInit(Room r) {
		flag.add(OBJECT_FLAG_GRAVITY);
	}
	
	@Override
	protected void onCollide(GameObject obj, int gravityDir, int colDir, Room room) {
		
		if(obj.flag.has(OBJECT_FLAG_MAPONLY)) {
//			System.out.println("In collision");
			if(obj.flag.has(OBJECT_FLAG_GOAL)){
				completeLevel(room);
			}
			if(gravityDir == colDir) {
				this.canJump = true;
			}
			
			switch(colDir) {
			case UP: 
				this.correctCollisionUp(obj); 
				this.y_speed = 0;
//				System.out.println("Collided: " + gravityDir + " " + colDir);
				break;
			case DOWN:
				this.correctCollisionDown(obj);
				this.y_speed = 0;
//				System.out.println("Collided: " + gravityDir + " " + colDir);
				break;
			case LEFT: 
				this.correctCollisionLeft(obj); 
				this.x_speed = 0;
//				System.out.println("Collided: " + gravityDir + " " + colDir);
				break;
			case RIGHT: 
				this.correctCollisionRight(obj); 
				this.x_speed = 0;
//				System.out.println("Collided: " + gravityDir + " " + colDir);
				break;
			}
	
			if(obj.getColorID() != -1) {
				if(obj.getColorID() == BLOCK_BLACK) {
					room.setGravityDir(colDir);
				} else {
					if(obj.getColorID() != room.getGravityColor()) {
						room.rotateGravity(obj.getColorID());
					} else {
						room.setGravityDir(DOWN);
					}
				}
			}
		}
	}
	
	protected void completeLevel(Room room){
		String level = "" + (Integer.parseInt(((Game)room.game).getLevel()) + 1);
		File f = new File("data/maps/level"+level+".map");
		if(f.exists()){
			((Game)room.game).setLevel(level);
			room.game.enterState(1, new FadeOutTransition(Color.black), new FadeInTransition(Color.black));
		} else {
			room.game.enterState(3, new FadeOutTransition(Color.black), new FadeInTransition(Color.black));
		}
	}

}
