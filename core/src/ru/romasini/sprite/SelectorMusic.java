package ru.romasini.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.romasini.base.Selector;
import ru.romasini.screen.ScreenController;

public class SelectorMusic extends Selector {

    private ScreenController screenController;

    public SelectorMusic(TextureAtlas atlas, ScreenController screenController) {
        super(atlas);
        this.screenController = screenController;
        this.switchedOn = screenController.isMusic();
        changeFrame();
    }

    @Override
    public void action() {
        screenController.setMusic(switchedOn);
    }


}
