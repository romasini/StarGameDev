package ru.romasini.screen;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import ru.romasini.base.BaseScreen;
import ru.romasini.math.Rect;
import ru.romasini.sprite.Background;
import ru.romasini.sprite.ExitButton;
import ru.romasini.sprite.PlayButton;

public class MenuScreen extends BaseScreen {

    private Texture backScreen, menuButtons;
    private Background background;
    private ExitButton exitButton;
    private PlayButton playButton;

    @Override
    public void show() {
        super.show();
        backScreen = new Texture("backScreenSpace.jpg");
        menuButtons = new Texture("menuAtlas.png");
        background = new Background(backScreen, getScreenController());
        exitButton = new ExitButton(new TextureRegion(menuButtons, 0,0 ,256,256), getScreenController());
        playButton = new PlayButton(new TextureRegion(menuButtons, 0,256 ,256,270), getScreenController());
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        background.resize(worldBounds);
        exitButton.resize(worldBounds);
        playButton.resize(worldBounds);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        batch.begin();
        background.draw(batch);
        exitButton.draw(batch);
        playButton.draw(batch);
        batch.end();
    }

    @Override
    public void dispose() {
        backScreen.dispose();
        menuButtons.dispose();
        super.dispose();
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        exitButton.touchDown(touch, pointer, button);
        playButton.touchDown(touch, pointer, button);
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer, int button) {
        exitButton.touchUp(touch, pointer, button);
        playButton.touchUp(touch, pointer, button);
        return false;
    }
}
