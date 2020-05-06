package ru.romasini;


import com.badlogic.gdx.Game;
import ru.romasini.screen.MenuScreen;
import ru.romasini.screen.ScreenController;

public class StarGame extends Game {

	@Override
	public void create() {
		new ScreenController(this);
	}
}
