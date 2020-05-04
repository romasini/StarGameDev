package ru.romasini.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.romasini.base.BaseScreen;

public class MenuScreen extends BaseScreen {

    private static final float V_LEN = 0.8f;
    private Texture backScreen;
    private Sprite backScreenSprite;
    private Texture spaceCat;
    private TextureRegion spaceCatFace;
    private Vector2 posSpaceCat, newPosSpaceCat, velSpaceCat, common;


    @Override
    public void resize(int width, int height) {
        backScreenSprite.setSize(width, height);
        batch.getProjectionMatrix().setToOrtho2D(0,0,width,height);
    }

    @Override
    public void show() {
        super.show();
        backScreen = new Texture("backScreenSpace.jpg");
        backScreenSprite = new Sprite(backScreen);
        backScreenSprite.setSize(Gdx.graphics.getWidth(),  Gdx.graphics.getHeight());
        backScreenSprite.setPosition(0,0);

        spaceCat = new Texture("spaceCat.jpg");
        spaceCatFace = new TextureRegion(spaceCat, 150,270,250,200 );

        posSpaceCat = new Vector2(50,50);
        newPosSpaceCat = new Vector2(posSpaceCat);
        velSpaceCat = new Vector2(0,0);
        common = new Vector2(0,0);
    }


    @Override
    public void render(float delta) {
        super.render(delta);
        batch.begin();
        batch.draw(backScreenSprite, -100,-100);
        if (common.set(newPosSpaceCat).sub(posSpaceCat).len2() > velSpaceCat.len2())
            posSpaceCat.add(velSpaceCat);
        else
            posSpaceCat.set(newPosSpaceCat);
        batch.draw(spaceCatFace, posSpaceCat.x, posSpaceCat.y, 150, 120);
        batch.end();
    }


    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        newPosSpaceCat.set(screenX, Gdx.graphics.getHeight() - screenY);
        velSpaceCat.
                set(newPosSpaceCat.
                        cpy().
                        sub(posSpaceCat)).
                setLength(V_LEN);
        return false;
    }

    @Override
    public void dispose() {
        super.dispose();
        backScreen.dispose();
        spaceCat.dispose();
    }


}
