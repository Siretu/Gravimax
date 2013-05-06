package objects;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.RoundedRectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

import main.Game;

public class Level {
	public static final int levelWidth = 80;
	public static final int levelHeight = 80;
	public static final int levelBorder = 5;
	
	private String level;
	private float x;
	private float y;
	private Color c;
	
	public Level(String filename, float x, float y, Color c){
		this.level = filename;
		this.x = x;
		this.y = y;
		this.c = c;
	}
	
	public void onRender(GameContainer gc, Graphics g, StateBasedGame game){
		float scale = 1;
		float mouseX = gc.getInput().getMouseX();
		float mouseY = gc.getInput().getMouseY();
		boolean scaled = false;
		if(mouseX >= x && mouseX <= x + levelWidth && mouseY >= y && mouseY <= y+levelHeight){
			scale = 1.1f;
			g.translate(-(scale-1) * levelWidth / 2,-(scale-1) * levelHeight / 2);
			scaled = true;
			if(gc.getInput().isMousePressed(0)){
				this.onClick(gc,g,game);
			}
		}
		g.setColor(c);
		g.fillRoundRect(x,y,levelWidth * scale,levelHeight * scale,10);
		g.setColor(Color.lightGray);
		g.fillRoundRect(x+levelBorder,y+levelBorder,(levelWidth * scale - levelBorder * 2),(levelHeight * scale - levelBorder * 2),10);
		
		if(scaled){
			g.translate((scale-1) * levelWidth / 2,(scale-1) * levelHeight / 2);
		}
	}
	
	public void onClick(GameContainer gc, Graphics g, StateBasedGame game){
		((Game)game).setLevel(level);
		game.enterState(1, new FadeOutTransition(Color.black), new FadeInTransition(Color.black));
	}
}
