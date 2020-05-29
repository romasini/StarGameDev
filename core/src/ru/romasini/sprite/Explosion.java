package ru.romasini.sprite;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;


import ru.romasini.base.Sprite;

public class Explosion extends Sprite {

    private static final float ANIMATE_INTERVAL = 0.01f;

    private float animateTimer;
    private Sound explosionSound;

    public Explosion(TextureAtlas atlas, Sound explosionSound) {
        super(atlas.findRegion("explosion"), 9, 9, 74);
        this.explosionSound = explosionSound;
    }

    public void set(float height, Vector2 pos){
        setHeightProportion(height);
        this.pos.set(pos);
        explosionSound.play();
        frame = 0;
    }

    @Override
    public void update(float delta) {
        animateTimer =+ delta;
        if(animateTimer>=ANIMATE_INTERVAL){
            animateTimer = 0f;
            if(++frame == regions.length){
                destroy();
            }
        }
    }

    @Override
    public void destroy() {
        super.destroy();
        frame = 0;
    }
}
