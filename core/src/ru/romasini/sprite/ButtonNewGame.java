package ru.romasini.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import ru.romasini.base.ScaledButton;
import ru.romasini.math.Rect;
import ru.romasini.screen.GameScreen;

public class ButtonNewGame extends ScaledButton {

    private GameScreen gameScreen;

    public ButtonNewGame(TextureAtlas atlas, GameScreen gameScreen) {
        super(atlas.findRegion("button_new_game"));
        this.gameScreen = gameScreen;
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(0.05f);
        setBottom(-0.2f);
    }

    @Override
    public void action() {
        gameScreen.initialize();
    }
}
