package ru.romasini.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import java.util.List;

import ru.romasini.base.BaseScreen;
import ru.romasini.math.Rect;
import ru.romasini.pool.BulletPool;
import ru.romasini.pool.EnemyPool;
import ru.romasini.pool.ExplosionPool;
import ru.romasini.sprite.Background;
import ru.romasini.sprite.Bullet;
import ru.romasini.sprite.ButtonNewGame;
import ru.romasini.sprite.Enemy;
import ru.romasini.sprite.GameOver;
import ru.romasini.sprite.MainShip;
import ru.romasini.sprite.Star;
import ru.romasini.utils.EnemyEmitter;

public class GameScreen extends BaseScreen {

    private enum State{
        PLAYING,
        GAME_OVER
    }

    private Texture backScreen;
    private TextureAtlas atlas;
    private Background background;
    private Star[] stars;
    private MainShip mainShip;
    private BulletPool bulletPool;
    private EnemyPool enemyPool;
    private ExplosionPool explosionPool;
    private EnemyEmitter enemyEmitter;
    private Music mainMusic;
    private GameOver gameOver;
    private ButtonNewGame buttonNewGame;

    private State state;

    @Override
    public void show() {
        super.show();
        backScreen = new Texture(Gdx.files.internal("textures/backScreenSpace.jpg"));
        background = new Background(backScreen);

        mainMusic = Gdx.audio.newMusic(Gdx.files.internal("sounds/mainMusic.mp3"));
        mainMusic.setVolume(0.3f);
        mainMusic.setLooping(true);
        mainMusic.play();

        atlas = new TextureAtlas(Gdx.files.internal("textures/mainAtlas.tpack"));
        bulletPool = new BulletPool();
        explosionPool = new ExplosionPool(atlas);
        enemyPool = new EnemyPool(bulletPool, explosionPool, worldBounds);
        enemyEmitter = new EnemyEmitter(atlas,enemyPool,worldBounds);
        mainShip = new MainShip(atlas, bulletPool, explosionPool);
        stars = new Star[64];
        for (int i = 0; i<stars.length; i++) {
            stars[i] = new Star(atlas);
        }
        gameOver = new GameOver(atlas);
        buttonNewGame = new ButtonNewGame(atlas, this);
        state = State.PLAYING;
    }

    public void initialize(){
        mainShip.initialize();
        enemyPool.makeAllFree();
        bulletPool.makeAllFree();
        state = State.PLAYING;
    }

    @Override
    public void resize(Rect worldBounds) {
        this.worldBounds = worldBounds;

        background.resize(worldBounds);
        for (Star star:stars) {
            star.resize(worldBounds);
        }
        mainShip.resize(worldBounds);
        enemyEmitter.resize(worldBounds);
        gameOver.resize(worldBounds);
        buttonNewGame.resize(worldBounds);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        update(delta);
        checkCollision();
        free();
        draw();
    }

    private void update(float delta){
        for (Star star:stars) {
            star.update(delta);
        }
        explosionPool.updateActiveSprites(delta);
        if(state == State.PLAYING){
            mainShip.update(delta);
            bulletPool.updateActiveSprites(delta);
            enemyPool.updateActiveSprites(delta);
            enemyEmitter.generate(delta);
        }
    }

    private void checkCollision() {
        if(state != State.PLAYING) {
            return;
        }

        List<Enemy> enemyList = enemyPool.getActiveObjects();
        List<Bullet> bulletList = bulletPool.getActiveObjects();

        for(Enemy enemy: enemyList){
            float minDist = enemy.getHalfWidth() + mainShip.getHalfWidth();
            if(mainShip.pos.dst2(enemy.pos) <= minDist * minDist){
                enemy.destroy();
                mainShip.damage(enemy.getDamage());
                continue;
            }

            for(Bullet bullet: bulletList){
                if (bullet.isDestroyed() || enemy.isDestroyed() || bullet.getOwner() != mainShip ) {
                    continue;
                }
                if(enemy.isBulletCollision(bullet)){
                    enemy.damage(bullet.getDamage());
                    bullet.destroy();
                }
            }
        }

        for(Bullet bullet: bulletList){
            if (bullet.isDestroyed() || bullet.getOwner() == mainShip) {
                continue;
            }
            if(mainShip.isBulletCollision(bullet)){
                mainShip.damage(bullet.getDamage());
                bullet.destroy();
            }
        }

        if(mainShip.isDestroyed()){
            state = State.GAME_OVER;
        }

    }

    private void free(){
        bulletPool.freeAllDestroyed();
        enemyPool.freeAllDestroyed();
        explosionPool.freeAllDestroyed();
    }

    private void draw(){
        batch.begin();
        background.draw(batch);
        for (Star star:stars) {
            star.draw(batch);
        }
        if(state == State.PLAYING){
            mainShip.draw(batch);
            bulletPool.drawActiveSprites(batch);
            enemyPool.drawActiveSprites(batch);
        }else if (state == State.GAME_OVER){
            gameOver.draw(batch);
            buttonNewGame.draw(batch);
        }
        explosionPool.drawActiveSprites(batch);
        batch.end();
    }

    @Override
    public void dispose() {
        backScreen.dispose();
        atlas.dispose();
        bulletPool.dispose();
        enemyPool.dispose();
        explosionPool.dispose();
        mainMusic.dispose();
        mainShip.dispose();
        super.dispose();
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        if(state == State.PLAYING) {
            mainShip.touchDown(touch, pointer, button);
        }else if(state == State.GAME_OVER){
            buttonNewGame.touchDown(touch, pointer, button);
        }
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer, int button) {
        if(state == State.PLAYING) {
            mainShip.touchUp(touch, pointer, button);
        }else if (state == State.GAME_OVER){
            buttonNewGame.touchUp(touch, pointer, button);
        }
        return false;
    }

    @Override
    public boolean touchDragged(Vector2 touch, int pointer) {
        return false;
    }

    @Override
    public boolean keyDown(int keycode) {
        if(state == State.PLAYING) {
            mainShip.keyDown(keycode);
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        if(state == State.PLAYING) {
            mainShip.keyUp(keycode);
        }
        return false;
    }

}
