package ru.romasini.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.romasini.base.Sprite;
import ru.romasini.math.Rect;

public class GameOver extends Sprite {

    public GameOver(TextureAtlas atlas) {
        super(atlas.findRegion("message_game_over"));
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(0.25f);
        setBottom(0.15f);
    }
}
