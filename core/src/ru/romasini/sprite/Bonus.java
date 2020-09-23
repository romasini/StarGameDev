package ru.romasini.sprite;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.romasini.base.BonusType;
import ru.romasini.base.Sprite;
import ru.romasini.math.Rect;

public class Bonus extends Sprite {

    private static final float VELOCITY = -0.1f;
    private static final float HEIGHT = 0.1f;
    private static final float ANIMATE_INTERVAL = 0.5f;
    private BonusType bonusType;
    private Vector2 velocity;
    protected Rect worldBounds;
    private float animateTimer;
    private boolean scaleUp = true;

    public Bonus() {
        this.regions = new TextureRegion[1];
        this.velocity = new Vector2(0, VELOCITY);
        this.animateTimer = 0f;
        this.frame = 0;
    }

    public void set(
            TextureRegion[] regions,
            Vector2 posStart,
            Rect worldBounds,
            BonusType bonusType){
        this.regions = regions;
        this.pos.set(posStart);
        this.bonusType = bonusType;
        this.worldBounds = worldBounds;
        this.frame = 0;
        this.animateTimer = 0f;
        this.scaleUp = false;
        this.scale = 1f;
        setHeightProportion(HEIGHT);
    }

    public BonusType getBonusType() {
        return bonusType;
    }

    @Override
    public void update(float delta) {
        pos.mulAdd(velocity, delta);

        animateTimer += delta;
        if(animateTimer >= ANIMATE_INTERVAL){
            animateTimer = 0f;
            scaleUp = !scaleUp;
        }

        if(scaleUp){
            setScale(getScale() + 0.009f);
        }else {
            setScale(getScale() - 0.009f);
        }

        if (isOutside(worldBounds))
            destroy();
    }
}
