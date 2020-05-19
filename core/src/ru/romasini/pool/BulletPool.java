package ru.romasini.pool;

import ru.romasini.base.Sprite;
import ru.romasini.base.SpritesPool;
import ru.romasini.sprite.Bullet;

public class BulletPool extends SpritesPool <Bullet>{
    @Override
    protected Bullet newObject() {
        return new Bullet();
    }
}
