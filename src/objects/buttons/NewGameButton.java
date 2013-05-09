package objects.buttons;

import main.Game;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

public class NewGameButton extends Button {

	public NewGameButton(Color standardColor, Color highlightColor, String text) {
		super(standardColor, highlightColor, text);
	}

	@Override
	public void onClick(GameContainer gc, Graphics g, StateBasedGame game) {
		((Game)game).setLevel("01");
		game.enterState(1, new FadeOutTransition(Color.black), new FadeInTransition(Color.black));
	}

}
