package ru.romasini.base;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.romasini.math.Rect;
import ru.romasini.math.Rnd;
import ru.romasini.pool.BulletPool;
import ru.romasini.sprite.Bullet;

public class Ship extends Sprite {

    private float shootInterval;
    private Vector2 vel;
    private Rect worldBounds;
    private BulletPool bulletPool;
    private TextureRegion bulletRegion;
    private Vector2 bulletVelocity;
    private Sound shootSound;
    private float shootTimer;
    
    public Ship() {
        this.regions = new TextureRegion[1];
        this.vel = new Vector2();
        this.bulletVelocity = new Vector2();
        this.shootTimer = 0f;
    }
    
    public void set(TextureRegion[] regions,
                    float height,
                    float velocityShip,
                    Rect worldBounds,
                    BulletPool bulletPool,
                    TextureRegion bulletRegion,
                    float bulletVelocity,
                    float shootInterval,
                    Sound shootSound
                    ){
        this.regions = regions;
        this.vel.set(0, velocityShip);
        this.worldBounds = worldBounds;
        this.bulletPool = bulletPool;
        this.bulletRegion = bulletRegion;
        this.bulletVelocity.set(0, bulletVelocity);
        this.shootInterval = shootInterval;
        this.shootSound = shootSound;

        setHeightProportion(height);
        setBottom(worldBounds.getTop());
        setLeft(Rnd.nextFloat(worldBounds.getLeft(), worldBounds.getRight() - getWidth()));

    }

    @Override
    public void update(float delta) {
        pos.mulAdd(vel, delta);

        shootTimer += delta;
        if (shootTimer > shootInterval){
            shootTimer = 0f;
            shoot();
        }

        if (isOutside(worldBounds))
            destroy();
    }

    private void shoot(){
        if (isDestroyed()) return;
        Bullet bullet = bulletPool.obtain();
        bullet.set(this,
                bulletRegion,
                pos,
                bulletVelocity,
                0.01f,
                worldBounds,
                1
        );
        shootSound.play();
    }
}
