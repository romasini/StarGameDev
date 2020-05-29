package ru.romasini.sprite;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import ru.romasini.base.ScaledButton;
import ru.romasini.math.Rect;

public class ButtonExit extends ScaledButton {

    private static final float MARGIN = 0.025f;

    public ButtonExit(TextureAtlas atlas) {
        super(atlas.findRegion("btExit"));
    }

    @Override
    public void action() {
        Gdx.app.exit();
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(0.1f);
    }
}
