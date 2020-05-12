package ru.romasini.sprite;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.romasini.base.Sprite;
import ru.romasini.math.Rect;
import ru.romasini.screen.ScreenController;

public class ButtonPlay extends Sprite {
    public ButtonPlay(TextureRegion region) {
        super(region);
    }

    @Override
    public void draw(SpriteBatch batch) {
        super.draw(batch);
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(0.15f);
        this.pos.set(0.25f, 0.425f);
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        if(isMe(touch)){
            setScale(0.9f);
        }
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer, int button) {
        if(isMe(touch)){
            setScale(1f);
            screenController.setGameScreen();
        }
        return false;
    }
}
