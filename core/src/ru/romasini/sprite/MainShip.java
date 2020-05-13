package ru.romasini.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.romasini.base.Sprite;
import ru.romasini.math.Rect;

public class MainShip extends Sprite {

    public static final float MARGIN = 0.05f;

    public MainShip(TextureAtlas atlas) {
        super(atlas.findRegion("main_ship").split(atlas.findRegion("main_ship").getRegionWidth()/2, atlas.findRegion("main_ship").getRegionHeight())[0]);
    }


    @Override
    public void update(float delta) {

    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(0.2f);
        setBottom(worldBounds.getBottom() + MARGIN);
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(Vector2 touch, int pointer) {
        return false;
    }
}
