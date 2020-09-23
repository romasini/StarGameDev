package ru.romasini.pool;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

import ru.romasini.base.SpritesPool;
import ru.romasini.math.Rect;
import ru.romasini.screen.ScreenController;
import ru.romasini.sprite.Enemy;

public class EnemyPool extends SpritesPool <Enemy> {

    private Rect worldBounds;
    private BulletPool bulletPool;
    private ExplosionPool explosionPool;
    private Sound shootSound;

    public EnemyPool(BulletPool bulletPool, ExplosionPool explosionPool, Rect worldBounds, ScreenController screenController) {
        super(screenController);
        this.worldBounds = worldBounds;
        this.bulletPool = bulletPool;
        this.explosionPool = explosionPool;
        this.shootSound = Gdx.audio.newSound(Gdx.files.internal("sounds/shoot.mp3"));
    }

    @Override
    protected Enemy newObject() {
        Enemy newEnemy = new Enemy(bulletPool, explosionPool, worldBounds, shootSound);
        newEnemy.setScreenController(screenController);
        return newEnemy;
    }

    @Override
    public void dispose() {
        super.dispose();
        shootSound.dispose();
    }
}
