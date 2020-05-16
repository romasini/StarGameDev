package ru.romasini.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import ru.romasini.base.BaseScreen;
import ru.romasini.math.Rect;
import ru.romasini.sprite.Background;
import ru.romasini.sprite.MainShip;
import ru.romasini.sprite.Star;

public class GameScreen extends BaseScreen {

    private Texture backScreen;
    private TextureAtlas atlas;
    private Background background;
    private Star[] stars;
    private MainShip mainShip;

    @Override
    public void show() {
        super.show();
        backScreen = new Texture(Gdx.files.internal("textures/backScreenSpace.jpg"));
        background = new Background(backScreen);
        atlas = new TextureAtlas(Gdx.files.internal("textures/mainAtlas.tpack"));
        mainShip = new MainShip(atlas);
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
        draw();
    }

    private void update(float delta){
        for (Star star:stars)
            star.update(delta);
        mainShip.update(delta);
    }

    private void draw(){
        batch.begin();
        background.draw(batch);
        for (Star star:stars)
            star.draw(batch);
        mainShip.draw(batch);
        batch.end();
    }

    @Override
    public void dispose() {
        backScreen.dispose();
        atlas.dispose();
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
