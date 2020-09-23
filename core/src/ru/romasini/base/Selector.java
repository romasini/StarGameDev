package ru.romasini.base;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.romasini.math.Rect;

public abstract class Selector extends Sprite {

    private boolean pressed;
    private int pointer;
    protected boolean switchedOn;

    public Selector(TextureAtlas atlas) {
        super();
        this.regions = new TextureRegion[2];
        this.regions[0] = atlas.findRegion("on");
        this.regions[1] = atlas.findRegion("off");
        changeFrame();
    }

    public abstract void action();

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        if(pressed || !isMe(touch)) return false;
        this.pointer = pointer;
        pressed = true;
        return false;
    }

    public void changeFrame(){
        if(switchedOn){
            frame = 0;
        }else {
            frame = 1;
        }
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(0.05f);
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer, int button) {
        if (this.pointer != pointer || !pressed) return false;
        if (isMe(touch)) {
            pressed = false;
            switchedOn = !switchedOn;
            changeFrame();
            action();
        }
        return false;
    }
}
