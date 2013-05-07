package objects.buttons;

import java.awt.Font;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

public abstract class Button {
	protected Color standardColor;
	protected Color highlightColor;
	public TrueTypeFont font;
	public TrueTypeFont highlightFont;
	protected float x = 0;
	protected float y = 0;
	protected String text;
	protected float width;

	protected float height;
	protected float highlightWidth;
	protected float highlightHeight;
	
	public Button(Color standardColor, Color highlightColor, String text){
		this.standardColor = standardColor;
		this.highlightColor = highlightColor;

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
			if(gc.getInput().isMousePressed(0)){
				this.onClick(gc,g,game);
			}
		} else {
			font.drawString(x + (highlightWidth - width) / 2, y + (highlightHeight - height) / 2, text, standardColor);
		}
	}
	
	public abstract void onClick(GameContainer gc, Graphics g, StateBasedGame game);

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

}
