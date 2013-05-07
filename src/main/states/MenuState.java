package main.states;

import java.awt.Font;

import objects.buttons.Button;
import objects.buttons.ExitButton;
import objects.buttons.NewGameButton;
import objects.buttons.SelectLevelButton;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class MenuState extends BasicGameState{
	public static final int ID = 3;
	public static final int menuButtonStartX = 500;
	public static final int menuButtonStartY = 100;
	
	private float rotation;
	private Image redspiral;
	private Image greenspiral;
	private Image bluespiral;
	private Button[] buttons;
	private TrueTypeFont font;

	@Override
	public void init(GameContainer gc, StateBasedGame game) throws SlickException {
		rotation = 5;
		redspiral = new Image("data/redspiral.png");
		bluespiral = new Image("data/bluespiral.png");
		greenspiral = new Image("data/greenspiral.png");
		buttons = new Button[10];	
		buttons[0] = new NewGameButton(Color.blue,new Color(150,0,255),"New game");
		buttons[1] = new SelectLevelButton(Color.blue,new Color(150,0,255),"Select Level");
		buttons[2] = new ExitButton(Color.blue,new Color(150,0,255),"Exit");
		Font f = new Font("Verdana", Font.BOLD, 45);
		font = new TrueTypeFont(f, true);
		int maxWidth = 0;
		for(Button b : buttons){
			if(b != null){
				maxWidth = Math.max(maxWidth, font.getWidth(b.getText()));
			}
		}
		int currY = menuButtonStartY;
		for(Button b : buttons){
			if(b != null){
				System.out.println("For "+b.getText() + " the change is: "+(maxWidth - font.getWidth(b.getText()))/2);
				System.out.println(maxWidth);
				System.out.println(font.getWidth(b.getText()));
				b.setX(menuButtonStartX + (maxWidth - b.font.getWidth(b.getText()))/2 );
				b.setY(currY);
				currY += b.font.getHeight(b.getText()) + 10;
			}
		}
		
	}

	@Override
	public void render(GameContainer gc, StateBasedGame game, Graphics g) throws SlickException {
		g.setBackground(new Color(200,200,255));
		font.drawString(100, 50, "Gravimax",Color.red);
		redspiral.draw(50,400,0.4f);
		greenspiral.draw(200,200,0.8f);
		bluespiral.draw(250,500,0.6f);
//		g.drawImage(redspiral, 50,400);
//		g.drawImage(greenspiral, 50,30);
		for(Button b : buttons){
			if(b != null){
				b.onRender(gc, g, game);
			} else {
				break;
			}
		}
	}

	@Override
	public void update(GameContainer gc, StateBasedGame game, int delta) throws SlickException {
		redspiral.rotate(rotation);
		bluespiral.rotate(rotation);
		greenspiral.rotate(-rotation);
	}

	@Override
	public int getID() {
		return ID;
	}

}
