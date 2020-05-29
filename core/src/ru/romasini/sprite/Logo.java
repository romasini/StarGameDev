package ru.romasini.sprite;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;


import ru.romasini.base.Sprite;
import ru.romasini.math.Rect;

public class Logo extends Sprite {

    private static final float MARGIN = 0.25f;

    public Logo(TextureAtlas atlas) {
        super(atlas.findRegion("logo"));
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(0.15f);
        setBottom(worldBounds.getTop() - MARGIN);
    }
}
