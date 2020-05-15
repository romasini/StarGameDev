package ru.romasini.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import ru.romasini.base.ScaledButton;
import ru.romasini.math.Rect;

public class ButtonPlay extends ScaledButton {

    private static final float MARGIN = 0.05f;

    public ButtonPlay(TextureAtlas atlas) {
        super(atlas.findRegion("btPlay"));
    }

    @Override
    public void action() {
        screenController.setGameScreen();
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(0.2f);
        setBottom(worldBounds.getBottom() + MARGIN);
        setLeft(worldBounds.getLeft() + MARGIN);
    }
}
