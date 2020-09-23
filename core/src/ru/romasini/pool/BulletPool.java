package ru.romasini.pool;

import ru.romasini.base.SpritesPool;
import ru.romasini.screen.ScreenController;
import ru.romasini.sprite.Bullet;

public class BulletPool extends SpritesPool <Bullet>{

    public BulletPool(ScreenController screenController) {
        super(screenController);
    }

    @Override
    protected Bullet newObject() {
        Bullet newBullet = new Bullet();
        newBullet.setScreenController(screenController);
        return newBullet;
    }
}
