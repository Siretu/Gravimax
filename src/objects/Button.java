package objects;

import java.awt.Font;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.state.StateBasedGame;

public class Button {
	protected Color standardColor;
	protected Color highlightColor;
	protected TrueTypeFont font;
	protected TrueTypeFont highlightFont;
	protected float x;
	protected float y;
	protected String text;
	protected float width;
	protected float height;
	protected float highlightWidth;
	protected float highlightHeight;
	
	public Button(Color standardColor, Color highlightColor, float x, float y, String text){
		this.standardColor = standardColor;
		this.highlightColor = highlightColor;
		this.x = x;
		this.y = y;
		this.text = text;
		Font normalFont = new Font("Verdana", Font.BOLD, 30);
		Font highlight = new Font("Verdana", Font.BOLD, 35);
		this.font = new TrueTypeFont(normalFont, true);
		this.highlightFont = new TrueTypeFont(highlight, true);
		this.width = font.getWidth(text);
		this.height = font.getHeight(text);
		this.highlightWidth = highlightFont.getWidth(text);
		this.highlightHeight = highlightFont.getHeight(text);
		
	}
	
	public void onRender(GameContainer gc, Graphics g, StateBasedGame game){
		float mouseX = gc.getInput().getMouseX();
		float mouseY = gc.getInput().getMouseY();
		if(mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y+height){
			highlightFont.drawString(x, y, text, highlightColor);
		} else {
			font.drawString(x + (highlightWidth - width) / 2, y + (highlightHeight - height) / 2, text, standardColor);
		}
		if(gc.getInput().isMousePressed(0)){
			game.enterState(1);
		}
	}

	

}
