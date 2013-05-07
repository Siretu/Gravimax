package objects;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.particles.ConfigurableEmitter;

public class Goal extends GameObject {
	public static final float scale = 0.15f;
	private ConfigurableEmitter emitter;
	
	Image goal;
	
	public Goal(Shape s, Color c) throws SlickException {
		super(s, c);
		goal = new Image("data/redspiral.png");
		this.flag.add(OBJECT_FLAG_MAPONLY);
		this.flag.add(OBJECT_FLAG_GOAL);
		
	}
	
	public void onInit(Room r){
		emitter = new ConfigurableEmitter("data/test_emitter.xml");
		emitter.setPosition(x, y);
		r.particleSystem.addEmitter(emitter);
		System.out.println("Particle system");
	}
	
	public void onRender(Graphics g){
		this.shape.setCenterX(this.x);
		this.shape.setCenterY(this.y);
		goal.draw(x-goal.getWidth()/2*scale,y-goal.getHeight()/2*scale,scale);
		goal.rotate(5);

		
	}
	
	public void update(Room room){
		System.out.println("Updating");
		
	}
	
	public void setX(float x) {
		this.x = x;
		emitter.setPosition(x, y);
	}
	
	public void setY(float y) {
		this.y = y;
		emitter.setPosition(x, y);
	}

}