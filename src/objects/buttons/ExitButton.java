package objects.buttons;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

public class ExitButton extends Button {

	public ExitButton(Color standardColor, Color highlightColor, String text) {
		super(standardColor, highlightColor, text);
	}

	@Override
	public void onClick(GameContainer gc, Graphics g, StateBasedGame game) {
		gc.exit();
	}

}
