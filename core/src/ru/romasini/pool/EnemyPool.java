package ru.romasini.pool;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

import ru.romasini.base.SpritesPool;
import ru.romasini.math.Rect;
import ru.romasini.sprite.Enemy;

public class EnemyPool extends SpritesPool <Enemy> {

    private Rect worldBounds;
    private BulletPool bulletPool;
    private Sound shootSound;

    public EnemyPool(BulletPool bulletPool, Rect worldBounds) {
        this.worldBounds = worldBounds;
        this.bulletPool = bulletPool;
        this.shootSound = Gdx.audio.newSound(Gdx.files.internal("sounds/shoot.mp3"));
    }

    @Override
    protected Enemy newObject() {
        return new Enemy(bulletPool, worldBounds, shootSound);
    }

    @Override
    public void dispose() {
        super.dispose();
        shootSound.dispose();
    }
}
