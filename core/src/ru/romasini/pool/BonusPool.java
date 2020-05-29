package ru.romasini.pool;

import ru.romasini.base.SpritesPool;
import ru.romasini.sprite.Bonus;

public class BonusPool extends SpritesPool<Bonus> {
    @Override
    protected Bonus newObject() {
        return new Bonus();
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}
