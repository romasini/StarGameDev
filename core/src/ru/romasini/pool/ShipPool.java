package ru.romasini.pool;

import ru.romasini.base.Ship;
import ru.romasini.base.SpritesPool;

public class ShipPool extends SpritesPool <Ship> {
    @Override
    protected Ship newObject() {
        return new Ship();
    }
}
