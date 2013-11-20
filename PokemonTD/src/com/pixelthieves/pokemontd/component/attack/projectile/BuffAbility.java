package com.pixelthieves.pokemontd.component.attack.projectile;

import com.pixelthieves.pokemontd.component.attack.EffectName;
import com.pixelthieves.pokemontd.component.attack.projectile.data.EffectData;

/**
 * Created by Tomas on 10/25/13.
 */
public class BuffAbility extends EffectData {
    private final Type type;
    private final float duration;

    public BuffAbility(Type type, float duration) {
        this.type = type;
        this.duration = duration;
    }

    public enum Type {
        SPEED, DAMAGE, RANGE;
    }

    public static BuffAbility getSpeed(float duration) {
        return new BuffAbility(Type.SPEED, duration);
    }

    public static BuffAbility getDamage(float duration) {
        return new BuffAbility(Type.DAMAGE, duration);
    }

    public Type getType() {
        return type;
    }

    public float getDuration() {
        return duration;
    }

    public String getEffectDescription(EffectName effectName, float speed, float damage) {
        return "";
    }
}