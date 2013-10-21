package com.xkings.pokemontd.component.attack;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.xkings.core.main.Assets;

/**
 * Created by Tomas on 10/13/13.
 */
public class ProjectileComponent extends AbilityComponent {
    public static final float DEFAULT_SPEED = 2.0f;
    public static final float DEFAULT_SIZE = 0.1f;
    private TextureAtlas.AtlasRegion texture;
    private Type type;
    private float speed;
    private float size;
    private final AbilityComponent ability;

    public static AbilityComponent getNormal(float scale) {
        return new ProjectileComponent(new OneTimeDamageComponent(), "bullet", Type.FOLLOW_TARGET, DEFAULT_SIZE * scale,
                DEFAULT_SPEED * scale);
    }

    public static AbilityComponent getSplash(float scale, float range) {
        return new ProjectileComponent(new AoeComponent(range * scale), "bullet", Type.FOLLOW_TARGET,
                DEFAULT_SIZE * scale, DEFAULT_SPEED * scale);
    }

    public static AbilityComponent getTemLifeSteal(float scale, float ratio, float duration) {
        return new ProjectileComponent(new LifeStealData(ratio, duration), "bullet", Type.FOLLOW_TARGET,
                DEFAULT_SIZE * scale, DEFAULT_SPEED * scale);
    }

    public enum Type {
        FOLLOW_TARGET, LAST_KNOWN_PLACE, AHEAD_TARGET,
    }

    public ProjectileComponent(AbilityComponent ability, String texture, Type type, float size, float speed) {
        this.ability = ability;
        this.texture = Assets.getTexture(texture);
        this.type = type;
        this.speed = speed;
        this.size = size;
    }

    public TextureAtlas.AtlasRegion getTexture() {
        return texture;
    }

    public Type getType() {
        return type;
    }

    public float getSpeed() {
        return speed;
    }

    public float getSize() {
        return size;
    }

    public void setSize(float size) {
        this.size = size;
    }

    public AbilityComponent getAbility() {
        return ability;
    }
}
