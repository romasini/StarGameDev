package ru.romasini.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import ru.romasini.base.BaseScreen;
import ru.romasini.base.Ship;
import ru.romasini.math.Rect;
import ru.romasini.pool.BulletPool;
import ru.romasini.pool.ShipPool;
import ru.romasini.sprite.Background;
import ru.romasini.sprite.MainShip;
import ru.romasini.sprite.Star;
import ru.romasini.utils.Regions;

public class GameScreen extends BaseScreen {

    private Texture backScreen;
    private TextureAtlas atlas;
    private Background background;
    private Star[] stars;
    private MainShip mainShip;
    private BulletPool bulletPool;
    private ShipPool shipPool;
    private Music mainMusic;

    private static final float NEW_SHIP_INTERVAL = 500f;
    private float newShipTimer;
    private Rect worldBounds;

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
        mainShip = new MainShip(atlas, bulletPool);
        shipPool = new ShipPool();
        stars = new Star[64];
        for (int i = 0; i<stars.length; i++)
            stars[i] = new Star(atlas);

        newShipTimer = 4f;
    }

    @Override
    public void resize(Rect worldBounds) {
        background.resize(worldBounds);
        for (Star star:stars)
            star.resize(worldBounds);
        mainShip.resize(worldBounds);
        this.worldBounds = worldBounds;
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
        newEnemyShip(delta);
        bulletPool.updateActiveSprites(delta);
        shipPool.updateActiveSprites(delta);
        mainShip.update(delta);
    }

    private void free(){
        bulletPool.freeAllDestroyed();
        shipPool.freeAllDestroyed();
    }

    private void draw(){
        batch.begin();
        background.draw(batch);
        for (Star star:stars)
            star.draw(batch);
        bulletPool.drawActiveSprites(batch);
        shipPool.drawActiveSprites(batch);
        mainShip.draw(batch);
        batch.end();
    }

    @Override
    public void dispose() {
        backScreen.dispose();
        atlas.dispose();
        bulletPool.dispose();
        shipPool.dispose();
        mainMusic.dispose();
        mainShip.dispose();
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

    private void newEnemyShip(float delta){
        newShipTimer += delta;
        if (newShipTimer > NEW_SHIP_INTERVAL ){
            newShipTimer = 0f;
            Ship newShip = shipPool.obtain();
            newShip.set(Regions.split(atlas.findRegion("enemy0"), 1, 2, 2),
                    0.15f,
                    -0.1f,
                    worldBounds,
                    bulletPool,
                    atlas.findRegion("bulletEnemy"),
                    -0.2f,
                    0.7f,
                    null);
        }
    }
}
