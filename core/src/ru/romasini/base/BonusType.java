package ru.romasini.base;

import com.badlogic.gdx.graphics.Color;

public enum BonusType {
    NORMAL (0, 0f, Color.WHITE, ""),
    HEALTH (10, 0.05f, Color.GREEN, "bonusHealth"),//
    ALL_DESTROY(0,0.05f, Color.BLUE, "bonusAllDestroy"),
    BOOST_DAMAGE(2, 7f, Color.RED, "bonusBoostDamage"),//
    MANY_BULLETS(1, 7f, Color.ORANGE, "bonusManyBullets"),//
    GHOST(0, 7f, Color.LIGHT_GRAY, "bonusGhost"),//
    ;
    private final int value;
    private final float duration;
    private final Color color;
    private final String regionName;

    private BonusType(int value, float duration, Color color, String regionName){
        this.value = value;
        this.duration = duration;
        this.color = color;
        this.regionName = regionName;
    }

    public int getValue() {
        return value;
    }

    public float getDuration() {
        return duration;
    }

    public Color getColor() {
        return color;
    }

    public String getRegionName() {
        return regionName;
    }

}
