package ru.romasini.sprite;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import ru.romasini.base.Sprite;
import ru.romasini.math.Rect;

public class Cursor extends Sprite {

    private float alpha = 1f;

    public Cursor(TextureAtlas atlas, boolean flip) {
        super(atlas.findRegion("wsda"));
        if(flip) {
            this.angle = 180;
        }
    }

    @Override
    public void update(float delta) {
        if(alpha >0) {
            alpha-=0.003f;
        }
    }

    private void setBatchColor(SpriteBatch batch){
        batch.setColor(1f,1f, 1f, alpha );
    }

    private void setDefaultBatchColor(SpriteBatch batch){
        batch.setColor(1f,1f, 1f, 1f );
    }

    @Override
    public void draw(SpriteBatch batch) {
        if(alpha >0) {
            setBatchColor(batch);
            super.draw(batch);
            setDefaultBatchColor(batch);
        }
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(0.2f);
    }


}
