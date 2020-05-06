package ru.romasini.screen;

import ru.romasini.StarGame;

public class ScreenController {

    private final StarGame starGame;
    private MenuScreen menuScreen;
    private GameScreen gameScreen;

    public ScreenController(StarGame starGame) {
        this.starGame = starGame;
        this.menuScreen = new MenuScreen();
        this.menuScreen.setScreenController(this);
        setMenuScreen();
    }

    public void setMenuScreen(){
        starGame.setScreen(menuScreen);
    }

    public void setGameScreen(){
        if (gameScreen == null){
            gameScreen = new GameScreen();
            gameScreen.setScreenController(this);
        }
        starGame.setScreen(gameScreen);
    }

}
