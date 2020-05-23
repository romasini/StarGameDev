package ru.romasini.sprite;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.romasini.base.Ship;
import ru.romasini.math.Rect;
import ru.romasini.pool.BulletPool;
import ru.romasini.pool.ExplosionPool;

public class Enemy extends Ship {

    public Enemy(BulletPool bulletPool, ExplosionPool explosionPool, Rect worldBounds, Sound shootSound) {
        super(bulletPool, explosionPool, worldBounds, shootSound);
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        if(getTop()<=worldBounds.getTop()){
            vel.set(velStart);
        }

        if(getBottom()<=worldBounds.getBottom() || healthPoints <=0){
            destroy();
        }
    }

    public void set(
            TextureRegion[] regions,
            Vector2 velStart,
            TextureRegion bulletRegion,
            float bulletHeight,
            float bulletVelY,
            int bulletDamage,
            float reloadInterval,
            float height,
            int healthPoints
    ){
        this.regions = regions;
        this.velStart.set(velStart);
        this.vel.y = -1f;
        this.bulletRegion = bulletRegion;
        this.bulletHeight = bulletHeight;
        this.bulletVelocity.set(0, bulletVelY);
        this.bulletDamage = bulletDamage;
        this.reloadInterval = reloadInterval;
        this.reloadTimer = reloadInterval;
        this.healthPoints = healthPoints;
        setHeightProportion(height);
    }


}
