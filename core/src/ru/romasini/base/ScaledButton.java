package ru.romasini.base;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public abstract class ScaledButton extends Sprite {

    private static final float PRESSED_SCALE = 0.9f;
    private static final float UNPRESSED_SCALE = 1f;
    private boolean pressed;
    private int pointer;

    public ScaledButton(TextureRegion region) {
        super(region);
    }

    public abstract void action();

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        if(pressed || !isMe(touch)) return false;
        this.pointer = pointer;
        pressed = true;
        setScale(PRESSED_SCALE);
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer, int button) {
        if (this.pointer != pointer || !pressed) return false;
        if (isMe(touch)) action();
        pressed = false;
        setScale(UNPRESSED_SCALE);
        return false;
    }
}
