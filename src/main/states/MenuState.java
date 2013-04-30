package main.states;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class MenuState extends BasicGameState{
	private float rotation;
	private Image redspiral;
	private Image greenspiral;
	

	@Override
	public void init(GameContainer gc, StateBasedGame game) throws SlickException {
		rotation = 5;
		redspiral = new Image("data/redspiral.png");
		greenspiral = new Image("data/greenspiral.png");
		
		
		
	}

	@Override
	public void render(GameContainer gc, StateBasedGame game, Graphics g) throws SlickException {
		g.setBackground(new Color(200,200,255));
		g.drawImage(redspiral, 50,400);
		g.drawImage(greenspiral, 50,30);
		
		
	}

	@Override
	public void update(GameContainer gc, StateBasedGame game, int delta) throws SlickException {
//		rotation += 0.01f;
		redspiral.rotate(rotation);
		greenspiral.rotate(-rotation);
		
	}

	@Override
	public int getID() {
		return 3;
	}

}
