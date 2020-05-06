package ru.romasini.sprite;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import ru.romasini.base.Sprite;
import ru.romasini.math.Rect;
import ru.romasini.screen.ScreenController;

public class Background extends Sprite {

    public Background(Texture texture, ScreenController screenController) {
        super(new TextureRegion(texture), screenController);
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(1f);
        this.pos.set(worldBounds.pos);
    }
}
