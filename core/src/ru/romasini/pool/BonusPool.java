package ru.romasini.pool;

import ru.romasini.base.SpritesPool;
import ru.romasini.screen.ScreenController;
import ru.romasini.sprite.Bonus;

public class BonusPool extends SpritesPool<Bonus> {

    public BonusPool(ScreenController screenController) {
        super(screenController);
    }

    @Override
    protected Bonus newObject() {
        Bonus newBonus = new Bonus();
        newBonus.setScreenController(screenController);
        return newBonus;
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}
