package com.xkings.pokemontd.entity.tower;

import com.xkings.pokemontd.Treasure;
import com.xkings.pokemontd.component.attack.AbilityComponent;
import com.xkings.pokemontd.component.attack.ProjectileComponent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.Math.PI;
import static java.lang.Math.pow;

/**
 * User: Seda
 * Date: 5.10.13
 * Time: 12:03
 */

public class TowerTypeBuilder {

    public static final float SIZE = 1;
    public static final float SUPER_SMALL_RANGE = 1.25f;
    public static final float SMALL_RANGE = 1.75f;
    public static final float NORMAL_RANGE = 2.25f;
    public static final float LONG_RANGE = 2.75f;
    public static final float SUPER_LONG_RANGE = 3.25f;
    public static final int NORMAL_SPEED = 1;
    public static final float SLOW_SPEED = 2f;
    public static final int INITIAL_BASE_DAMAGE = 15;
    public static final int INITIAL_GOLD = 10;
    private static final float koefficient =
            (float) (scaleByRange(SUPER_SMALL_RANGE) - scaleByRange(SUPER_LONG_RANGE)) * 2f;

    private List<TowerType> getData(float scale) {
        List<Specs> specs = new ArrayList<Specs>();
        // Basic
        specs.add(new Specs(TowerName.Needle, 1, NORMAL_SPEED, NORMAL_RANGE, ProjectileComponent.getNormal(scale)));
        specs.add(new Specs(TowerName.Pinch, 2, NORMAL_SPEED, NORMAL_RANGE, ProjectileComponent.getNormal(scale)));
        specs.add(new Specs(TowerName.Sting, 3, NORMAL_SPEED, NORMAL_RANGE, ProjectileComponent.getNormal(scale)));
        specs.add(new Specs(TowerName.Scratch, 1, SLOW_SPEED, NORMAL_RANGE, ProjectileComponent.getSplash(scale, 2)));
        specs.add(new Specs(TowerName.Bite, 2, SLOW_SPEED, NORMAL_RANGE, ProjectileComponent.getSplash(scale, 2)));
        specs.add(new Specs(TowerName.Smash, 3, SLOW_SPEED, NORMAL_RANGE, ProjectileComponent.getSplash(scale, 2)));
        // New 15
        specs.add(new Specs(TowerName.Spooky, 4, NORMAL_SPEED, SUPER_LONG_RANGE, ProjectileComponent.getNormal(scale)));
        specs.add(
                new Specs(TowerName.Haunted, 5, NORMAL_SPEED, SUPER_LONG_RANGE, ProjectileComponent.getNormal(scale)));
        specs.add(new Specs(TowerName.Nightmare, 10, NORMAL_SPEED, SUPER_LONG_RANGE,
                ProjectileComponent.getTemLifeSteal(scale, 0.3f, 5f)));

        List<TowerType> data = new ArrayList<TowerType>();
        for (Specs specification : specs) {
            data.add(createTowerType(scale, specification));
        }
        return data;
    }

    private TowerType createTowerType(float scale, Specs specs) {
        float damage = getDamage(getBaseDamage(INITIAL_BASE_DAMAGE, specs.level), specs.range, specs.speed);
        System.out.println(damage);
        return new TowerType(specs.name, SIZE * scale, specs.speed, damage, specs.range * scale, specs.attackComponent,
                new Treasure(getGold(INITIAL_GOLD, specs.level)));
    }

    private float getDamage(float baseDamage, float range, float speed) {
        System.out.println(baseDamage * (float) scaleByRange(range) / koefficient);
        return (baseDamage + baseDamage * (float) scaleByRange(range) / koefficient) * speed;
    }

    private static double scaleByRange(float range) {
        return (PI * pow(NORMAL_RANGE, 2) - (PI * pow(range, 2)));
    }

    public float getBaseDamage(float initialBaseDamage, int levels) {
        return (float) (initialBaseDamage * pow(3, levels - 1));
    }

    public int getGold(int initialGold, int levels) {
        return (int) (initialGold * pow(2, levels - 1));
    }


    private static class Specs {
        private final TowerName name;
        private final int level;
        private final float speed;
        private final float range;
        private final AbilityComponent attackComponent;

        private Specs(TowerName name, int level, float speed, float range, AbilityComponent attackComponent) {
            this.name = name;
            this.level = level;
            this.speed = speed;
            this.range = range;
            this.attackComponent = attackComponent;
        }
    }


    public Map<TowerName, TowerType> build(float scale) {
        Map<TowerName, TowerType> map = new HashMap<TowerName, TowerType>();
        for (TowerType towerType : getData(scale)) {
            map.put(towerType.getName(), towerType);
        }
        return map;
    }
}
