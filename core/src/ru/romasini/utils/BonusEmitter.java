package ru.romasini.utils;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;

import ru.romasini.base.BonusType;
import ru.romasini.base.Ship;
import ru.romasini.math.Rect;
import ru.romasini.pool.BonusPool;
import ru.romasini.sprite.Bonus;

public class BonusEmitter {

    private Rect worldBounds;
    private BonusPool bonusPool;
    private Vector2 common;
    private BonusKeeper[] bonusKeepers;
    private Random rand;

    private static class BonusKeeper{
        private BonusType bonusType;
        private TextureRegion[] regions;

        public BonusKeeper(BonusType bonusType, TextureAtlas atlas) {
            this.bonusType = bonusType;
            this.regions = new TextureRegion[1];
            this.regions[0] = atlas.findRegion(bonusType.getRegionName());
        }
    }

    public BonusEmitter(TextureAtlas atlas, BonusPool bonusPool, Rect worldBounds) {
        this.bonusPool = bonusPool;
        this.worldBounds = worldBounds;
        this.common = new Vector2();
        this.rand = new Random();
        this.bonusKeepers = new BonusKeeper[BonusType.values().length-1];//NORMAL исключаем
        int i = 0;
        for (BonusType bonus: BonusType.values()) {
            if (bonus == BonusType.NORMAL){
                continue;
            }
            bonusKeepers[i] = new BonusKeeper(bonus, atlas);
            i++;
        }

    }

    public void generate(Ship ship){
        int temp = rand.nextInt(bonusKeepers.length);
        common.set(ship.pos.x, ship.pos.y - ship.getHalfHeight());
        Bonus bonus = bonusPool.obtain();
        bonus.set(
                bonusKeepers[temp].regions,
                common,
                worldBounds,
                bonusKeepers[temp].bonusType
        );
    }

    public void resize(Rect worldBounds){
        this.worldBounds = worldBounds;
    }
}
