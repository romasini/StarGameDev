package ru.romasini.sprite;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import ru.romasini.base.BonusType;
import ru.romasini.base.Sprite;
import ru.romasini.math.Rect;

public class Background extends Sprite {

    private BonusType bonusType;
    private float bonusTimer;

    public Background(Texture texture) {
        super(new TextureRegion(texture));
        setBonusType(BonusType.NORMAL);
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(1f);
        this.pos.set(worldBounds.pos);
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        if(bonusType != BonusType.NORMAL){
            bonusTimer += delta;
            if (bonusTimer >= bonusType.getDuration()){
                setBonusType(BonusType.NORMAL);
            }
        }
    }

    public void setBonusType(BonusType bonusType) {
        this.bonusType = bonusType;
        this.bonusTimer = 0f;
    }


    @Override
    public void draw(SpriteBatch batch) {
        setBatchColor(batch);
        super.draw(batch);
        setDefaultBatchColor(batch);
    }

    private void setBatchColor(SpriteBatch batch){
        if(bonusType == BonusType.ALL_DESTROY) {
            batch.setColor(bonusType.getColor());
        }else{
            setDefaultBatchColor(batch);
        }
    }

    private void setDefaultBatchColor(SpriteBatch batch){
        batch.setColor(1f,1f, 1f, 1f );
    }

}
