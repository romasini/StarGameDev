package ru.romasini.utils;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.romasini.math.Rect;
import ru.romasini.math.Rnd;
import ru.romasini.pool.EnemyPool;
import ru.romasini.sprite.Enemy;

public class EnemyEmitter {

    private static final float GENERATE_INTERVAL = 4f;

    private Rect worldBounds;
    private float generateTimer;
    private final TextureRegion bulletRegion;
    private final EnemyPool enemyPool;

    //сделать через ENUM
    private static final float ENEMY_SMALL_HEIGHT = 0.1f;
    private static final int ENEMY_SMALL_HEALTH_POINTS = 1;
    private static final float ENEMY_SMALL_BULLET_HEIGHT = 0.01f;
    private static final float ENEMY_SMALL_BULLET_VEL_Y = -0.3f;
    private static final int ENEMY_SMALL_BULLET_DAMAGE = 1;
    private static final float ENEMY_SMALL_RELOAD_INTERVAL = 2f;
    private static final int ENEMY_SMALL_DAMAGE = 1;

    private static final float ENEMY_MEDIUM_HEIGHT = 0.15f;
    private static final int ENEMY_MEDIUM_HEALTH_POINTS = 5;
    private static final float ENEMY_MEDIUM_BULLET_HEIGHT = 0.02f;
    private static final float ENEMY_MEDIUM_BULLET_VEL_Y = -0.25f;
    private static final int ENEMY_MEDIUM_BULLET_DAMAGE = 5;
    private static final float ENEMY_MEDIUM_RELOAD_INTERVAL = 4f;
    private static final int ENEMY_MEDIUM_DAMAGE = 3;

    private static final float ENEMY_BIG_HEIGHT = 0.2f;
    private static final int ENEMY_BIG_HEALTH_POINTS = 10;
    private static final float ENEMY_BIG_BULLET_HEIGHT = 0.04f;
    private static final float ENEMY_BIG_BULLET_VEL_Y = -0.3f;
    private static final int ENEMY_BIG_BULLET_DAMAGE = 10;
    private static final float ENEMY_BIG_RELOAD_INTERVAL = 1f;
    private static final int ENEMY_BIG_DAMAGE = 5;

    private final TextureRegion[] enemySmallRegions;
    private final Vector2 enemySmallVelocity;

    private final TextureRegion[] enemyMediumRegions;
    private final Vector2 enemyMediumVelocity;

    private final TextureRegion[] enemyBigRegions;
    private final Vector2 enemyBigVelocity;

    public EnemyEmitter(TextureAtlas atlas, EnemyPool enemyPool, Rect worldBounds) {
        this.enemySmallRegions = Regions.split(atlas.findRegion("enemy0"), 1, 2, 2);
        this.enemySmallVelocity = new Vector2(0, -0.15f);

        this.enemyMediumRegions = Regions.split(atlas.findRegion("enemy1"), 1, 2, 2);
        this.enemyMediumVelocity = new Vector2(0, -0.03f);

        this.enemyBigRegions = Regions.split(atlas.findRegion("enemy2"), 1, 2, 2);
        this.enemyBigVelocity = new Vector2(0, -0.005f);

        this.bulletRegion = atlas.findRegion("bulletEnemy");
        this.enemyPool = enemyPool;
        this.worldBounds = worldBounds;
    }

    public void generate(float delta) {
        generateTimer += delta;
        if (generateTimer >= GENERATE_INTERVAL) {
            generateTimer = 0f;
            Enemy enemy = enemyPool.obtain();
            float type = (float) Math.random();
            if(type <0.5f) {
                   enemy.set(enemySmallRegions,
                        enemySmallVelocity,
                        bulletRegion,
                        ENEMY_SMALL_BULLET_HEIGHT,
                        ENEMY_SMALL_BULLET_VEL_Y,
                        ENEMY_SMALL_BULLET_DAMAGE,
                        ENEMY_SMALL_RELOAD_INTERVAL,
                        ENEMY_SMALL_HEIGHT,
                        ENEMY_SMALL_HEALTH_POINTS,
                        ENEMY_SMALL_DAMAGE
                );
            }else if(type<0.8f){
                enemy.set(enemyMediumRegions,
                        enemyMediumVelocity,
                        bulletRegion,
                        ENEMY_MEDIUM_BULLET_HEIGHT,
                        ENEMY_MEDIUM_BULLET_VEL_Y,
                        ENEMY_MEDIUM_BULLET_DAMAGE,
                        ENEMY_MEDIUM_RELOAD_INTERVAL,
                        ENEMY_MEDIUM_HEIGHT,
                        ENEMY_MEDIUM_HEALTH_POINTS,
                        ENEMY_MEDIUM_DAMAGE
                );
            }else{
                enemy.set(enemyBigRegions,
                        enemyBigVelocity,
                        bulletRegion,
                        ENEMY_BIG_BULLET_HEIGHT,
                        ENEMY_BIG_BULLET_VEL_Y,
                        ENEMY_BIG_BULLET_DAMAGE,
                        ENEMY_BIG_RELOAD_INTERVAL,
                        ENEMY_BIG_HEIGHT,
                        ENEMY_BIG_HEALTH_POINTS,
                        ENEMY_BIG_DAMAGE
                );   
            }
            enemy.pos.x = Rnd.nextFloat(worldBounds.getLeft() + enemy.getHalfWidth(), worldBounds.getRight() - enemy.getHalfWidth());
            enemy.setBottom(worldBounds.getTop());
        }
    }

    public void resize(Rect worldBounds){
        this.worldBounds = worldBounds;
    }
}