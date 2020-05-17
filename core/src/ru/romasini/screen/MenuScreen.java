package ru.romasini.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import ru.romasini.base.BaseScreen;
import ru.romasini.math.Rect;
import ru.romasini.sprite.Background;
import ru.romasini.sprite.ButtonExit;
import ru.romasini.sprite.ButtonPlay;
import ru.romasini.sprite.Star;

public class MenuScreen extends BaseScreen {

    private Texture backScreen;
    private TextureAtlas atlas;
    private Background background;
    private ButtonExit buttonExit;
    private ButtonPlay buttonPlay;
    private Star[] stars;
    private Music mainMusic;

    @Override
    public void show() {
        super.show();
        backScreen = new Texture(Gdx.files.internal("textures/backScreenSpace.jpg"));
        background = new Background(backScreen);
        mainMusic = Gdx.audio.newMusic(Gdx.files.internal("sounds/mainMusic.mp3"));
        mainMusic.play();
        atlas = new TextureAtlas(Gdx.files.internal("textures/menuAtlas.tpack"));
        buttonExit = new ButtonExit(atlas);
        buttonPlay = new ButtonPlay(atlas);
        buttonPlay.setScreenController(getScreenController());
        stars = new Star[256];
        for (int i = 0; i<stars.length; i++)
            stars[i] = new Star(atlas);
    }

    @Override
    public void resize(Rect worldBounds) {
        background.resize(worldBounds);
        buttonExit.resize(worldBounds);
        buttonPlay.resize(worldBounds);
        for (Star star:stars)
            star.resize(worldBounds);
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
    }

    private void draw(){
        batch.begin();
        background.draw(batch);
        for (Star star:stars)
            star.draw(batch);
        buttonExit.draw(batch);
        buttonPlay.draw(batch);
        batch.end();
    }

    @Override
    public void dispose() {
        backScreen.dispose();
        atlas.dispose();
        mainMusic.dispose();
        super.dispose();
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        buttonExit.touchDown(touch, pointer, button);
        buttonPlay.touchDown(touch, pointer, button);
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer, int button) {
        buttonExit.touchUp(touch, pointer, button);
        buttonPlay.touchUp(touch, pointer, button);
        return false;
    }
}
