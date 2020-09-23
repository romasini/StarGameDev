package ru.romasini.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;

import ru.romasini.base.BaseScreen;
import ru.romasini.base.Font;
import ru.romasini.math.Rect;
import ru.romasini.sprite.Background;
import ru.romasini.sprite.ButtonExit;
import ru.romasini.sprite.ButtonPlay;
import ru.romasini.sprite.Logo;
import ru.romasini.sprite.SelectorEffects;
import ru.romasini.sprite.SelectorField;
import ru.romasini.sprite.SelectorMusic;
import ru.romasini.sprite.Star;

public class MenuScreen extends BaseScreen {

    private static final String MUSIC_OPTION = "MUSIC", EFFECTS_OPTION = "EFFECTS";
    private static final float TEXT_MARGIN = 0.01f;
    private static final float FONT_SIZE = 0.025f;

    private Texture backScreen;
    private TextureAtlas atlas;
    private Background background;
    private ButtonExit buttonExit;
    private ButtonPlay buttonPlay;
    private SelectorEffects selectorEffects;
    private SelectorMusic selectorMusic;
    private Logo logo;
    private Star[] stars;
    private Music mainMusic;
    private Font font;
    private SelectorField selectorField;
    private StringBuilder musicOption, effectsOption;

    @Override
    public void show() {
        super.show();
        backScreen = new Texture(Gdx.files.internal("textures/backScreenMenu.png"));
        background = new Background(backScreen);
        mainMusic = Gdx.audio.newMusic(Gdx.files.internal("sounds/mainMusic.mp3"));
        playMusic();
        atlas = new TextureAtlas(Gdx.files.internal("textures/menuAtlas.tpack"));
        buttonExit = new ButtonExit(atlas);
        buttonPlay = new ButtonPlay(atlas);

        selectorEffects = new SelectorEffects(atlas, getScreenController());
        selectorMusic = new SelectorMusic(atlas, getScreenController());
        logo = new Logo(atlas);
        buttonPlay.setScreenController(getScreenController());
        selectorField = new SelectorField(atlas);

        stars = new Star[256];
        for (int i = 0; i<stars.length; i++)
            stars[i] = new Star(atlas);

        font = new Font("font/font.fnt", "font/font.png");
        font.setSize(FONT_SIZE);
        musicOption = new StringBuilder();
        effectsOption = new StringBuilder();
    }

    @Override
    public void resize(Rect worldBounds) {
        this.worldBounds = worldBounds;
        background.resize(worldBounds);
        buttonExit.resize(worldBounds);
        buttonPlay.resize(worldBounds);
        selectorField.resize(worldBounds);
        selectorMusic.resize(worldBounds);
        selectorEffects.resize(worldBounds);
        logo.resize(worldBounds);

        buttonPlay.pos.set(0, logo.getBottom() - buttonPlay.getHalfHeight() - 0.02f);
        buttonExit.pos.set(0, buttonPlay.getBottom() - buttonExit.getHalfHeight() - 0.02f);
        selectorField.pos.set(0, buttonExit.getBottom() - selectorField.getHalfHeight() - 0.02f);
        selectorField.setWidth(worldBounds.getWidth() - 0.1f);

        selectorMusic.pos.set(selectorField.getRight() - selectorMusic.getWidth() -0.04f, selectorField.getTop() -0.1f);
        selectorEffects.pos.set(selectorField.getRight() - selectorMusic.getWidth() -0.04f, selectorField.getBottom() + 0.07f);
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
        for (Star star:stars) {
            star.update(delta);
        }

        playMusic();
    }

    private void playMusic(){
        if(getScreenController().isMusic() && !mainMusic.isPlaying()){
            mainMusic.play();
        }else if(!getScreenController().isMusic() && mainMusic.isPlaying()){
            mainMusic.stop();
        }
    }

    private void draw(){
        batch.begin();
        background.draw(batch);
        for (Star star:stars)
            star.draw(batch);
        logo.draw(batch);
        buttonExit.draw(batch);
        buttonPlay.draw(batch);
        selectorField.draw(batch);
        selectorEffects.draw(batch);
        selectorMusic.draw(batch);
        printInfo();
        batch.end();
    }

    private void printInfo(){
        musicOption.setLength(0);
        effectsOption.setLength(0);
        font.draw(batch, musicOption.append(MUSIC_OPTION), selectorField.getLeft() + 0.1f , selectorMusic.getTop()-0.01f, Align.left);
        font.draw(batch, effectsOption.append(EFFECTS_OPTION), selectorField.getLeft() + 0.1f  , selectorEffects.getTop()-0.01f, Align.left);
    }

    @Override
    public void dispose() {
        backScreen.dispose();
        atlas.dispose();
        mainMusic.dispose();
        font.dispose();
        super.dispose();
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        buttonExit.touchDown(touch, pointer, button);
        buttonPlay.touchDown(touch, pointer, button);
        selectorEffects.touchDown(touch, pointer, button);
        selectorMusic.touchDown(touch, pointer, button);
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer, int button) {
        buttonExit.touchUp(touch, pointer, button);
        buttonPlay.touchUp(touch, pointer, button);
        selectorEffects.touchUp(touch, pointer, button);
        selectorMusic.touchUp(touch, pointer, button);
        return false;
    }
}
