package ru.romasini.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import ru.romasini.base.Sprite;
import ru.romasini.math.Rect;

public class MainShip extends Sprite {

    public MainShip(TextureAtlas atlas) {
        //super(atlas.findRegion("main_ship").split(2,1)[0][0]);
        super(atlas.findRegion("main_ship"));
    }

    @Override
    public void update(float delta) {

    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(0.2f);
        pos.setZero();
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
