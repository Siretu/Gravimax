package objects;

import java.awt.Font;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
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
	
	public static final int tooltipWidth = 250;
	public static final int tooltipHeight = 80;
	public static final int tooltipBorder = 5;
	public static final int tooltipTextGap = 10;
	
	public static final Color levelLocked = Color.red;
	public static final Color levelComplete = Color.green;
	public static final Color levelUnlocked = Color.blue;
	
	
	private String levelPath;
	private int level;
	private float x;
	private float y;
	private Color c;
	private boolean showTooltip;
	
	private String highscore;
	private String lastTry;
	
	private TrueTypeFont font;
	private TrueTypeFont highscoreFont;
	
	public Level(String filename, float x, float y, Color c, int level, String highscore, String lastTry){
		this.levelPath = filename;
		this.x = x;
		this.y = y;
		this.c = c;
		this.level = level;
		this.highscore = highscore;
		this.lastTry = lastTry;
		this.showTooltip = false;
		Font f = new Font("Verdana", Font.BOLD, 45);
		font = new TrueTypeFont(f, true);
		Font f2 = new Font("Verdana", Font.BOLD, 15);
		highscoreFont = new TrueTypeFont(f2, true);
	}
	
	public void onRender(GameContainer gc, Graphics g, StateBasedGame game){
		float scale = 1;
		float mouseX = gc.getInput().getMouseX();
		float mouseY = gc.getInput().getMouseY();
		boolean scaled = false;
		if(mouseX >= x && mouseX <= x + levelWidth && mouseY >= y && mouseY <= y+levelHeight && !c.equals(levelLocked)){
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
		if(!c.equals(levelLocked)){
			font.drawString(x+(levelWidth * scale - font.getWidth(""+level))/2, y + (levelHeight* scale - font.getHeight(""+level))/2, ""+level,c);	
		} else {
			try {
				Image lock = new Image("data/images/lock.png");
				lock.draw(x + (levelWidth - lock.getWidth()) / 2, y + (levelHeight - lock.getHeight()) / 2);
			} catch (SlickException e) {
				e.printStackTrace();
			}
		}
		
		if(scaled){
			g.translate((scale-1) * levelWidth / 2,(scale-1) * levelHeight / 2);
			showTooltip = true;
		} else {
			showTooltip = false;
		}
	}
	
	public void onHover(GameContainer gc, Graphics g, StateBasedGame game){
		if(showTooltip){
			int mouseX = gc.getInput().getMouseX();
			int mouseY = gc.getInput().getMouseY();
			int startX = mouseX+levelWidth/2;
			int startY = mouseY+levelHeight/2;
			g.setColor(Color.black);
			g.fillRect(startX, startY, tooltipWidth, tooltipHeight);
			g.setColor(Color.white);
			g.fillRect(startX+tooltipBorder, startY+tooltipBorder, tooltipWidth-tooltipBorder*2, tooltipHeight-tooltipBorder*2);
			highscoreFont.drawString(startX + tooltipTextGap, startY + tooltipTextGap, "Highscore: "+ highscore,Color.black);
			highscoreFont.drawString(startX + tooltipTextGap, startY + 2 * tooltipTextGap + highscoreFont.getHeight("Highscore"), "Last try: "+ lastTry,Color.black);
		}
	}
	
	public void onClick(GameContainer gc, Graphics g, StateBasedGame game){
		((Game)game).setLevel("" + level);
		game.enterState(1, new FadeOutTransition(Color.black), new FadeInTransition(Color.black));
	}

	public String getHighscore() {
		return highscore;
	}

	public void setHighscore(String highscore) {
		this.highscore = highscore;
	}

	public String getLastTry() {
		return lastTry;
	}

	public void setLastTry(String lastTry) {
		this.lastTry = lastTry;
	}

	public Color getC() {
		return c;
	}

	public void setC(Color c) {
		this.c = c;
	}
}
