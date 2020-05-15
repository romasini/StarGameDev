package ru.romasini.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import ru.romasini.base.Sprite;
import ru.romasini.math.Rect;
import ru.romasini.math.Rnd;

public class Star extends Sprite {

    private Vector2 v;
    private Rect worldBounds;
    private float animateTimer, animateInterval;


    public Star(TextureAtlas atlas) {
        super(atlas.findRegion("star"));
        worldBounds = new Rect();
        v = new Vector2();
        float vX = Rnd.nextFloat(-0.005f, 0.005f);
        float vY = Rnd.nextFloat(-0.2f, -0.05f);
        v.set(vX, vY);
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(0.01f);
        this.worldBounds = worldBounds;
        float posX = Rnd.nextFloat(worldBounds.getLeft(), worldBounds.getRight());
        float posY = Rnd.nextFloat(worldBounds.getBottom(), worldBounds.getTop());
        pos.set(posX, posY);
        animateInterval = Rnd.nextFloat(0.5f, 1.5f);
    }

    @Override
    public void update(float delta) {
        setScale(getScale() - 0.01f);
        animateTimer += delta;
        if (animateTimer >= animateInterval){
            setScale(1f);
            animateTimer = 0f;
        }
        pos.mulAdd(v, delta);
        checkBounds();
    }

    private void checkBounds(){
        if(getRight() < worldBounds.getLeft()){
            setLeft(worldBounds.getRight());
        }
        if(getLeft() > worldBounds.getRight()){
            setRight(worldBounds.getLeft());
        }

        if(getTop() < worldBounds.getBottom()){
            setBottom(worldBounds.getTop());
        }
    }
}
