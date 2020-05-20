package ru.romasini.base;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.romasini.math.Rect;
import ru.romasini.pool.BulletPool;
import ru.romasini.pool.ExplosionPool;
import ru.romasini.sprite.Bullet;
import ru.romasini.sprite.Explosion;

public class Ship extends Sprite {

    protected Vector2 vel, velStart;

    protected Rect worldBounds;

    protected ExplosionPool explosionPool;
    protected BulletPool bulletPool;
    protected TextureRegion bulletRegion;
    protected Vector2 bulletVelocity;
    protected float bulletHeight;
    protected int bulletDamage;

    protected int healthPoints;

    protected float reloadInterval;
    protected float reloadTimer;

    protected Sound shootSound;

    public Ship(TextureRegion region, int rows, int cols, int frames) {
        super(region, rows, cols, frames);

        this.velStart = new Vector2();
        this.vel = new Vector2();
    }

    public Ship(BulletPool bulletPool, ExplosionPool explosionPool, Rect worldBounds, Sound shootSound) {
        this.worldBounds = worldBounds;
        this.bulletPool = bulletPool;
        this.explosionPool = explosionPool;
        this.velStart = new Vector2();
        this.vel = new Vector2();
        this.shootSound = shootSound;
        this.bulletVelocity = new Vector2();
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
        if (reloadTimer >= reloadInterval && getTop() <= worldBounds.getTop()){
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

    @Override
    public void destroy() {
        super.destroy();
        boom();
    }

    private void boom(){
        Explosion explosion = explosionPool.obtain();
        explosion.set(getHeight(), pos);
    }

    public int getHealthPoints() {
        return healthPoints;
    }

    public void setHealthPoints(int healthPoints) {
        this.healthPoints = healthPoints;
    }
}
