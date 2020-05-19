package ru.romasini.base;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.romasini.math.Rect;
import ru.romasini.math.Rnd;
import ru.romasini.pool.BulletPool;
import ru.romasini.sprite.Bullet;

public class Ship extends Sprite {

    protected final Vector2 vel, velStart;

    protected Rect worldBounds;

    protected BulletPool bulletPool;
    protected TextureRegion bulletRegion;
    protected Vector2 bulletVelocity;
    protected float bulletHeight;
    protected int bulletDamage;

    protected float reloadInterval;
    protected float reloadTimer;

    protected Sound shootSound;

    public Ship(TextureRegion region, int rows, int cols, int frames) {
        super(region, rows, cols, frames);

        this.velStart = new Vector2();
        this.vel = new Vector2();

    }

    @Override
    public void resize(Rect worldBounds) {
        this.worldBounds = worldBounds;
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        pos.mulAdd(vel, delta);
        reloadTimer += delta;
        if (reloadTimer > reloadInterval){
            reloadTimer = 0f;
            shoot();
        }
    }

    protected void shoot(){
        Bullet bullet = bulletPool.obtain();
        bullet.set(this,
                bulletRegion,
                pos,
                bulletVelocity,
                bulletHeight,
                worldBounds,
                bulletDamage
        );
        shootSound.play();
    }
}
