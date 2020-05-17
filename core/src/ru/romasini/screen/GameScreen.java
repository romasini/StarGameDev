package ru.romasini.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import ru.romasini.base.BaseScreen;
import ru.romasini.math.Rect;
import ru.romasini.pool.BulletPool;
import ru.romasini.sprite.Background;
import ru.romasini.sprite.MainShip;
import ru.romasini.sprite.Star;

public class GameScreen extends BaseScreen {

    private Texture backScreen;
    private TextureAtlas atlas;
    private Background background;
    private Star[] stars;
    private MainShip mainShip;
    private BulletPool bulletPool;
    private Sound shootSound;
    private Music mainMusic;

    @Override
    public void show() {
        super.show();
        backScreen = new Texture(Gdx.files.internal("textures/backScreenSpace.jpg"));
        background = new Background(backScreen);
        mainMusic = Gdx.audio.newMusic(Gdx.files.internal("sounds/mainMusic.mp3"));
        mainMusic.setVolume(0.5f);
        mainMusic.play();
        atlas = new TextureAtlas(Gdx.files.internal("textures/mainAtlas.tpack"));
        shootSound = Gdx.audio.newSound(Gdx.files.internal("sounds/shoot.mp3"));
        bulletPool = new BulletPool();
        mainShip = new MainShip(atlas, bulletPool, shootSound);
        stars = new Star[64];
        for (int i = 0; i<stars.length; i++)
            stars[i] = new Star(atlas);
    }

    @Override
    public void resize(Rect worldBounds) {
        background.resize(worldBounds);
        for (Star star:stars)
            star.resize(worldBounds);
        mainShip.resize(worldBounds);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        update(delta);
        free();
        draw();
    }

    private void update(float delta){
        for (Star star:stars)
            star.update(delta);
        bulletPool.updateActiveSprites(delta);
        mainShip.update(delta);
    }

    private void free(){
        bulletPool.freeAllDestroyed();
    }

    private void draw(){
        batch.begin();
        background.draw(batch);
        for (Star star:stars)
            star.draw(batch);
        bulletPool.drawActiveSprites(batch);
        mainShip.draw(batch);
        batch.end();
    }

    @Override
    public void dispose() {
        backScreen.dispose();
        atlas.dispose();
        bulletPool.dispose();
        shootSound.dispose();
        mainMusic.dispose();
        super.dispose();
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        mainShip.touchDown(touch, pointer, button);
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer, int button) {
        mainShip.touchUp(touch, pointer, button);
        return false;
    }

    @Override
    public boolean touchDragged(Vector2 touch, int pointer) {
        return false;
    }

    @Override
    public boolean keyDown(int keycode) {
        mainShip.keyDown(keycode);
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        mainShip.keyUp(keycode);
        return false;
    }
}
