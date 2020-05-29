package ru.romasini.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;

import java.util.List;

import ru.romasini.base.BaseScreen;
import ru.romasini.base.BonusType;
import ru.romasini.base.Font;
import ru.romasini.math.Rect;
import ru.romasini.pool.BonusPool;
import ru.romasini.pool.BulletPool;
import ru.romasini.pool.EnemyPool;
import ru.romasini.pool.ExplosionPool;
import ru.romasini.sprite.Background;
import ru.romasini.sprite.Bonus;
import ru.romasini.sprite.Bullet;
import ru.romasini.sprite.ButtonExit;
import ru.romasini.sprite.ButtonNewGame;
import ru.romasini.sprite.Cursor;
import ru.romasini.sprite.Enemy;
import ru.romasini.sprite.GameOver;
import ru.romasini.sprite.MainShip;
import ru.romasini.sprite.Star;
import ru.romasini.utils.BonusEmitter;
import ru.romasini.utils.EnemyEmitter;

public class GameScreen extends BaseScreen {

    private enum State{
        PLAYING,
        GAME_OVER
    }

    private static final float TEXT_MARGIN = 0.01f;
    private static final float FONT_SIZE = 0.03f;
    private static final String FRAGS = "Frags: ";
    private static final String HP = "HP: ";
    private static final String LEVEL = "Level: ";

    private Texture backScreen;
    private TextureAtlas atlas, bonusAtlas;
    private Background background;
    private Star[] stars;
    private MainShip mainShip;
    private BulletPool bulletPool;
    private EnemyPool enemyPool;
    private ExplosionPool explosionPool;
    private BonusPool bonusPool;
    private EnemyEmitter enemyEmitter;
    private BonusEmitter bonusEmitter;
    private Music mainMusic;
    private GameOver gameOver;
    private ButtonNewGame buttonNewGame;
    private ButtonExit buttonExit;
    private Font font;
    private int frags;
    private StringBuilder sbFrags, sbHP, sbLevel;
    private Cursor cursLeft, cursRight;

    private State state;

    @Override
    public void show() {
        super.show();
        backScreen = new Texture(Gdx.files.internal("textures/backScreenSpace.png"));
        background = new Background(backScreen);

        mainMusic = Gdx.audio.newMusic(Gdx.files.internal("sounds/mainMusic.mp3"));
        mainMusic.setVolume(0.3f);
        mainMusic.setLooping(true);
        playMusic();

        atlas = new TextureAtlas(Gdx.files.internal("textures/mainAtlas.tpack"));
        bulletPool = new BulletPool(getScreenController());
        bonusAtlas = new TextureAtlas(Gdx.files.internal("textures/mainAtlas.tpack"));
        bonusPool = new BonusPool(getScreenController());
        bonusEmitter = new BonusEmitter(bonusAtlas, bonusPool, worldBounds);
        explosionPool = new ExplosionPool(atlas, getScreenController());
        enemyPool = new EnemyPool(bulletPool, explosionPool, worldBounds, getScreenController());
        enemyEmitter = new EnemyEmitter(atlas,enemyPool,worldBounds);
        mainShip = new MainShip(atlas, bulletPool, explosionPool);
        mainShip.setScreenController(getScreenController());
        stars = new Star[64];
        for (int i = 0; i<stars.length; i++) {
            stars[i] = new Star(atlas);
        }
        gameOver = new GameOver(atlas);
        buttonNewGame = new ButtonNewGame(atlas, this);
        buttonExit = new ButtonExit(atlas);

        cursLeft = new Cursor(atlas, true);
        cursRight = new Cursor(atlas, false);

        font = new Font("font/font.fnt", "font/font.png");
        sbFrags = new StringBuilder();
        sbHP = new StringBuilder();
        sbLevel = new StringBuilder();
        state = State.PLAYING;
    }

    public void startNewGame(){
        frags = 0;
        mainShip.startNewGame();
        enemyPool.freeAllActiveObjects();
        bulletPool.freeAllActiveObjects();
        explosionPool.freeAllActiveObjects();
        bonusPool.freeAllActiveObjects();
        state = State.PLAYING;
    }

    @Override
    public void resize(Rect worldBounds) {
        this.worldBounds = worldBounds;
        font.setSize(FONT_SIZE);
        background.resize(worldBounds);
        for (Star star:stars) {
            star.resize(worldBounds);
        }
        mainShip.resize(worldBounds);
        enemyEmitter.resize(worldBounds);
        bonusEmitter.resize(worldBounds);
        gameOver.resize(worldBounds);
        buttonNewGame.resize(worldBounds);
        buttonExit.resize(worldBounds);

        cursRight.resize(worldBounds);
        cursLeft.resize(worldBounds);

        gameOver.pos.set(0, worldBounds.getTop() - gameOver.getHalfHeight()-0.1f);
        buttonNewGame.pos.set(0, gameOver.getBottom() - buttonNewGame.getHalfHeight()-0.02f );
        buttonExit.pos.set(0, buttonNewGame.getBottom() - buttonExit.getHalfHeight()-0.02f );

        cursRight.pos.set(worldBounds.getRight() - cursRight.getHalfWidth() , -0.25f);
        cursLeft.pos.set(worldBounds.getLeft() + cursLeft.getHalfWidth() , -0.25f);


    }

    private void destroyAllEnemies(){
        background.setBonusType(BonusType.ALL_DESTROY);
        List<Enemy> enemies = enemyPool.getActiveObjects();
        for (Enemy enemy: enemies) {
            if(!enemy.isDestroyed()){
                enemy.destroy();
                frags++;
            }
        }
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
        playMusic();
        background.update(delta);
        for (Star star:stars) {
            star.update(delta);
        }
        explosionPool.updateActiveSprites(delta);
        if(state == State.PLAYING){
            mainShip.update(delta);
            bulletPool.updateActiveSprites(delta);
            enemyPool.updateActiveSprites(delta);
            bonusPool.updateActiveSprites(delta);
            enemyEmitter.generate(delta, frags);

            cursRight.update(delta);
            cursLeft.update(delta);
        }else if(state == State.GAME_OVER){
            buttonNewGame.update(delta);
        }
    }

    private void playMusic(){
        if(getScreenController().isMusic() && !mainMusic.isPlaying()){
            mainMusic.play();
        }else if(!getScreenController().isMusic() && mainMusic.isPlaying()){
            mainMusic.stop();
        }
    }

    private void checkCollision() {
        if(state != State.PLAYING) {
            return;
        }

        List<Enemy> enemyList = enemyPool.getActiveObjects();
        List<Bullet> bulletList = bulletPool.getActiveObjects();
        List<Bonus> bonusList = bonusPool.getActiveObjects();

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
                    if (enemy.isDestroyed()) {
                        frags++;
                        if (enemy.isGiveBonus()){
                            bonusEmitter.generate(enemy);
                        }
                    }
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

        for (Bonus bonus: bonusList){
            if(bonus.isDestroyed()){
                continue;
            }
            if(mainShip.isBonusCollision(bonus)){
                mainShip.setBonusType(bonus.getBonusType());
                bonus.destroy();
            }
        }

        if(mainShip.isDestroyed()){
            state = State.GAME_OVER;
        }else if(mainShip.getBonusType() == BonusType.ALL_DESTROY){
            destroyAllEnemies();
        }

    }

    private void free(){
        bulletPool.freeAllDestroyed();
        enemyPool.freeAllDestroyed();
        explosionPool.freeAllDestroyed();
        bonusPool.freeAllDestroyed();
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
            bonusPool.drawActiveSprites(batch);
            cursLeft.draw(batch);
            cursRight.draw(batch);
        }else if (state == State.GAME_OVER){
            gameOver.draw(batch);
            buttonNewGame.draw(batch);
            buttonExit.draw(batch);
        }
        explosionPool.drawActiveSprites(batch);
        printInfo();
        batch.end();
    }

    private void printInfo(){
        sbFrags.setLength(0);
        sbHP.setLength(0);
        sbLevel.setLength(0);
        font.draw(batch, sbFrags.append(FRAGS).append(frags), worldBounds.getLeft() + TEXT_MARGIN, worldBounds.getTop() - TEXT_MARGIN);
        font.draw(batch, sbHP.append(HP).append(mainShip.getHealthPoints()), worldBounds.pos.x, worldBounds.getTop() - TEXT_MARGIN, Align.center);
        font.draw(batch, sbLevel.append(LEVEL).append(enemyEmitter.getLevel()), worldBounds.getRight() - TEXT_MARGIN, worldBounds.getTop() - TEXT_MARGIN, Align.right);
    }

    @Override
    public void dispose() {
        backScreen.dispose();
        atlas.dispose();
        bonusAtlas.dispose();
        bulletPool.dispose();
        enemyPool.dispose();
        explosionPool.dispose();
        bonusPool.dispose();
        mainMusic.dispose();
        mainShip.dispose();
        font.dispose();
        super.dispose();
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        if(state == State.PLAYING) {
            mainShip.touchDown(touch, pointer, button);
        }else if(state == State.GAME_OVER){
            buttonNewGame.touchDown(touch, pointer, button);
            buttonExit.touchDown(touch, pointer, button);
        }
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer, int button) {
        if(state == State.PLAYING) {
            mainShip.touchUp(touch, pointer, button);
        }else if (state == State.GAME_OVER){
            buttonNewGame.touchUp(touch, pointer, button);
            buttonExit.touchUp(touch, pointer, button);
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
