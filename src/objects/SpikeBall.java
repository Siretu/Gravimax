package objects;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Shape;

public class SpikeBall extends GameObject{
	
	private Image spikeImg;

	public SpikeBall(Shape s, Color c) {
		super(s, c);
		try {
			spikeImg = new Image("data/spikeball.png");
		} catch (SlickException e) {
			e.printStackTrace();
		}
//		this.flag.add(OBJECT_FLAG_MAPONLY);
		this.flag.add(OBJECT_FLAG_HOSTILE);
		this.flag.add(OBJECT_FLAG_GHOST);
	}
	
	@Override
	protected void onCollide(GameObject obj, int gravityDir, int colDir, Room room) {
//		System.out.println("Colliding!");
		if(obj.getClass() == Player.class){
			obj.die(room);
		}
		if(!obj.flag.has(OBJECT_FLAG_GHOST)){
			switch(colDir) {
			case UP: 
				this.correctCollisionUp(obj); 
				this.y_speed = 0;
				break;
			case DOWN:
				this.correctCollisionDown(obj);
				this.y_speed = 0;
				break;
			case LEFT: 
				this.correctCollisionLeft(obj); 
				this.x_speed = 0;
				break;
			case RIGHT: 
				this.correctCollisionRight(obj); 
				this.x_speed = 0;
				break;
			}
		}
	}
	
	public void onRender(Graphics g){
		this.shape.setCenterX(this.x);
		this.shape.setCenterY(this.y);
//		g.draw(shape);
		spikeImg.draw(x-shape.getWidth()/2,y-shape.getHeight()/2,shape.getWidth(),shape.getHeight());
	}

}
