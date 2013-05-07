package objects;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Shape;

public class Spikes extends GameObject{
	
	private Image spikeImg;

	public Spikes(Shape s, Color c) {
		super(s, c);
		try {
			spikeImg = new Image("data/spikes.png");
		} catch (SlickException e) {
			e.printStackTrace();
		}
		this.flag.add(OBJECT_FLAG_MAPONLY);
		this.flag.add(OBJECT_FLAG_HOSTILE);
		this.flag.add(OBJECT_FLAG_GHOST);
	}

	
	public void onRender(Graphics g){
		this.shape.setCenterX(this.x);
		this.shape.setCenterY(this.y);
//		g.draw(shape);
		spikeImg.draw(x-shape.getWidth()/2,y-shape.getHeight()/2,shape.getWidth(),shape.getHeight());
	}

}
