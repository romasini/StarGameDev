package ru.romasini.screen;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import ru.romasini.base.BaseScreen;
import ru.romasini.math.Rect;
import ru.romasini.sprite.Background;
import ru.romasini.sprite.Logo;

public class GameScreen extends BaseScreen {

    private Texture backScreen, spaceCat;
    private Background background;
    private Logo logo;

    @Override
    public void show() {
        super.show();
        backScreen = new Texture("backScreenSpace.jpg");
        background = new Background(backScreen, getScreenController());
        spaceCat = new Texture("spaceCat.jpg");
        logo = new Logo(spaceCat, getScreenController());
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        background.resize(worldBounds);
        logo.resize(worldBounds);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        batch.begin();
        background.draw(batch);
        logo.draw(batch);
        batch.end();
    }

    @Override
    public void dispose() {
        backScreen.dispose();
        spaceCat.dispose();
        super.dispose();
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        logo.touchDown(touch, pointer, button);
        return super.touchDown(touch, pointer, button);
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer, int button) {
        return super.touchUp(touch, pointer, button);
    }
}
