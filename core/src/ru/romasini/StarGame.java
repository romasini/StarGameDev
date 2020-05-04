package ru.romasini;


import com.badlogic.gdx.Game;
import ru.romasini.screen.MenuScreen;

public class StarGame extends Game {

	@Override
	public void create() {
		setScreen(new MenuScreen());
	}
}
