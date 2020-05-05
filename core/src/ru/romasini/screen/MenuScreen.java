package ru.romasini.screen;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import ru.romasini.base.BaseScreen;
import ru.romasini.math.Rect;
import ru.romasini.sprite.Background;

public class MenuScreen extends BaseScreen {

    private Texture backScreen;
    private Texture spaceCat;
    private Vector2 posSpaceCat;
    private Background background;

    @Override
    public void show() {
        super.show();
        backScreen = new Texture("backScreenSpace.jpg");
        spaceCat = new Texture("spaceCat.jpg");
        posSpaceCat = new Vector2(0,0);
        background = new Background(backScreen);
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        background.resize(worldBounds);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        batch.begin();
        background.draw(batch);
        batch.draw(spaceCat, posSpaceCat.x, posSpaceCat.y, 0.4f, 0.5f);
        batch.end();
    }

    @Override
    public void dispose() {
        backScreen.dispose();
        spaceCat.dispose();
        super.dispose();
    }


}
