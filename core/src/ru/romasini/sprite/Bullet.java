package ru.romasini.sprite;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.romasini.base.Sprite;
import ru.romasini.math.Rect;

public class Bullet extends Sprite {

    private Rect worldBounds;
    private Vector2 velocity;
    private int damage;
    private Sprite owner;

    public Bullet() {
        this.regions = new TextureRegion[1];
        this.velocity = new Vector2();
    }

    public void set(
            Sprite owner,
            TextureRegion region,
            Vector2 posStart,
            Vector2 velStart,
            float height,
            Rect worldBounds,
            int damage){
        this.owner = owner;
        this.regions[0] = region;
        this.pos.set(posStart);
        this.velocity.set(velStart);
        setHeightProportion(height);
        this.worldBounds = worldBounds;
        this.damage = damage;
    }

    public int getDamage() {
        return damage;
    }

    public Sprite getOwner() {
        return owner;
    }

    @Override
    public void update(float delta) {
        pos.mulAdd(velocity, delta);
        if (isOutside(worldBounds))
            destroy();
    }
}
