package ru.romasini.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.romasini.base.BaseScreen;

public class MenuScreen extends BaseScreen {
    private Texture backScreen;
    private Texture spaceCat;
    private TextureRegion spaceCatFace;
    private Vector2 posSpaceCat, newPosSpaceCat, velSpaceCat, deltaSpace;

    @Override
    public void show() {
        super.show();
        backScreen = new Texture("backScreenSpace.jpg");
        spaceCat = new Texture("spaceCat.jpg");
        posSpaceCat = new Vector2(50,50);
        spaceCatFace = new TextureRegion(spaceCat, 150,270,250,200 );
        newPosSpaceCat = new Vector2(posSpaceCat);
        velSpaceCat = new Vector2(0,0);
        deltaSpace = new Vector2(0,0);

    }


    @Override
    public void render(float delta) {
        super.render(delta);
        batch.begin();
        batch.draw(backScreen, -200,-200);
        if(!stopMove())
            posSpaceCat.add(velSpaceCat);
        batch.draw(spaceCatFace, posSpaceCat.x, posSpaceCat.y, 150, 120);
        batch.end();
    }

    private void calcNewVelocity(){
        velSpaceCat.set(newPosSpaceCat.x-posSpaceCat.x, newPosSpaceCat.y-posSpaceCat.y).nor();
    }

    private boolean stopMove(){
        deltaSpace.set(newPosSpaceCat.x-posSpaceCat.x, newPosSpaceCat.y-posSpaceCat.y);
        if(velSpaceCat.len2() > 0 && velSpaceCat.len2()>= deltaSpace.len2()){
            posSpaceCat.set(newPosSpaceCat);
            velSpaceCat.set(0,0);
            deltaSpace.set(0,0);
            return true;
        }
        if (deltaSpace.len2()>0)
            velSpaceCat.scl(1.05f);//boost
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        newPosSpaceCat.set(screenX, Gdx.graphics.getHeight() - screenY);
        calcNewVelocity();
        return false;
    }

    @Override
    public void dispose() {
        super.dispose();
        backScreen.dispose();
        spaceCat.dispose();
    }


}
