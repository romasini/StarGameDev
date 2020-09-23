package ru.romasini.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.romasini.base.Selector;
import ru.romasini.screen.ScreenController;

public class SelectorEffects extends Selector {

    private ScreenController screenController;

    public SelectorEffects(TextureAtlas atlas, ScreenController screenController) {
        super(atlas);
        this.screenController = screenController;
        this.switchedOn = screenController.isEffects();
        changeFrame();
    }

    @Override
    public void action() {
        screenController.setEffects(switchedOn);
    }
}
