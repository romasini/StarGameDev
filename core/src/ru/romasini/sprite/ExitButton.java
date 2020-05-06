package ru.romasini.sprite;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.romasini.base.Sprite;
import ru.romasini.math.Rect;
import ru.romasini.screen.ScreenController;

public class ExitButton extends Sprite {
    public ExitButton(TextureRegion region, ScreenController screenController) {
        super(region, screenController);
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(0.12f);
        this.pos.set(-0.25f, 0.412f);
    }

    @Override
    public void draw(SpriteBatch batch) {
        super.draw(batch);
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        if(isMe(touch)){
            setScale(0.9f);
            Gdx.app.exit();
        }
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer, int button) {
        if(isMe(touch)){
            setScale(1f);
        }
        return false;
    }
}
