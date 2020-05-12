package ru.romasini.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import ru.romasini.base.BaseScreen;
import ru.romasini.math.Rect;
import ru.romasini.sprite.Background;
import ru.romasini.sprite.ButtonExit;
import ru.romasini.sprite.ButtonPlay;

public class MenuScreen extends BaseScreen {

    private Texture backScreen;
    private TextureAtlas atlas;
    private Background background;
    private ButtonExit buttonExit;
    private ButtonPlay buttonPlay;

    @Override
    public void show() {
        super.show();
        backScreen = new Texture(Gdx.files.internal("textures/backScreenSpace.jpg"));
        background = new Background(backScreen);
        atlas = new TextureAtlas(Gdx.files.internal("textures/menuAtlas.tpack"));
        buttonExit = new ButtonExit(atlas);
    }

    @Override
    public void resize(Rect worldBounds) {
        background.resize(worldBounds);
        buttonExit.resize(worldBounds);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        update(delta);
        draw();
    }

    private void update(float delta){

    }

    private void draw(){
        batch.begin();
        background.draw(batch);
        buttonExit.draw(batch);
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
        buttonExit.touchDown(touch, pointer, button);
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer, int button) {
        buttonExit.touchUp(touch, pointer, button);
        return false;
    }
}
