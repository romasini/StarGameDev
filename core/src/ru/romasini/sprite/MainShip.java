package ru.romasini.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import ru.romasini.base.Sprite;
import ru.romasini.math.Rect;

public class MainShip extends Sprite {

    private static final float MARGIN = 0.05f;
    private static final float VELOCITY = 0.01f;
    private float newX;
    private Rect worldBounds;

    public MainShip(TextureAtlas atlas) {
        super(atlas.findRegion("main_ship").split(atlas.findRegion("main_ship").getRegionWidth()/2, atlas.findRegion("main_ship").getRegionHeight())[0]);
    }


    @Override
    public void update(float delta) {
        if((newX - pos.x)*(newX - pos.x) > VELOCITY*VELOCITY){
            if(newX > pos.x)
                pos.set(pos.x + VELOCITY, pos.y);
            else
                pos.set(pos.x - VELOCITY, pos.y);
        }else {
            pos.set(newX, pos.y);
        }
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(0.2f);
        setBottom(worldBounds.getBottom() + MARGIN);
        this.worldBounds = worldBounds;
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        newX = touch.x;
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer, int button) {
        newX = pos.x;
        return false;
    }

    @Override
    public boolean touchDragged(Vector2 touch, int pointer) {
        return false;
    }

    public boolean keyDown(int keycode) {
        if (keycode == 22)
            newX = worldBounds.getRight();
        else if (keycode==21)
            newX = worldBounds.getLeft();
        return false;
    }

    public boolean keyUp(int keycode) {
        if(keycode==22 || keycode==21)
            newX = pos.x;
        return false;
    }


}
