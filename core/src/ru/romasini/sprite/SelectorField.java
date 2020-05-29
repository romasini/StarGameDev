package ru.romasini.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.romasini.base.Selector;
import ru.romasini.base.Sprite;
import ru.romasini.math.Rect;

public class SelectorField extends Sprite {

    public SelectorField(TextureAtlas atlas) {
        super(atlas.findRegion("option"));
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(0.24f);
    }


    public void updatePosEffects(Selector selector){

    }

    public void updatePosMusic(Selector selector){

    }
}
