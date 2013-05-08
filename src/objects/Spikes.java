package objects;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Shape;

public class Spikes extends GameObject{
	
	private Image spikeImg;
	private int width;
	private int height;

	public Spikes(Shape s, Color c) {
		super(s, c);
		try {
			spikeImg = new Image("data/images/spikes.png");
		} catch (SlickException e) {
			e.printStackTrace();
		}
		this.flag.add(OBJECT_FLAG_MAPONLY);
		this.flag.add(OBJECT_FLAG_HOSTILE);
		this.flag.add(OBJECT_FLAG_GHOST);
		
		width = (int) shape.getWidth();
		height = (int) shape.getHeight();
	}
	
	protected void onRotation(int turn){
		if(turn == 2) {
			spikeImg.rotate(180);
		} else if(turn == 1) {
			spikeImg.rotate(-90);
		} else if(turn == 3) {
			spikeImg.rotate(90);
		}
	}
	
	public void onRender(Graphics g){
		this.shape.setCenterX(this.x);
		this.shape.setCenterY(this.y);
		spikeImg.draw(x-width/2,y-height/2,width,height);
	}

}
