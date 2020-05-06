package ru.romasini.sprite;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.romasini.base.Sprite;
import ru.romasini.math.Rect;

public class Logo extends Sprite {

    private Vector2 newPos, velocity, common;

    public Logo(Texture texture) {
        super(new TextureRegion(texture, 150,270,250,200));
        newPos = new Vector2(pos);
        velocity = new Vector2(0,0);
        common = new Vector2(0,0);
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(0.2f);
        this.pos.set(worldBounds.pos);
    }

    @Override
    public void draw(SpriteBatch batch) {
        if (common.set(newPos).sub(pos).len2() > velocity.len2())
            pos.add(velocity);
        else{
            pos.set(newPos);
            velocity.set(0,0);
            common.set(0,0);
        }

        super.draw(batch);
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        newPos = touch;
        velocity.
                set(newPos.
                        cpy().
                        sub(pos)).
                setLength(0.01f);
        return false;
    }
}
