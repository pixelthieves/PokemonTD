package com.pixelthieves.elementtd.system.autonomous;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.systems.EntityProcessingSystem;
import com.pixelthieves.elementtd.Health;
import com.pixelthieves.elementtd.component.CreepAbilityComponent;
import com.pixelthieves.elementtd.component.HealthComponent;
import com.pixelthieves.elementtd.entity.creep.CreepAbilityType;

/**
 * Created by Tomas on 10/4/13.
 */
public class HealingSystem extends EntityProcessingSystem {
    @Mapper
    ComponentMapper<HealthComponent> healthMapper;
    @Mapper
    ComponentMapper<CreepAbilityComponent> creepAbilityMapper;

    public HealingSystem() {
        super(Aspect.getAspectForAll(HealthComponent.class, CreepAbilityComponent.class));
    }

    @Override
    protected void process(Entity e) {
        if (creepAbilityMapper.get(e).getCreepAbilityType().equals(CreepAbilityType.HEALING)) {
            Health health = healthMapper.get(e).getHealth();
            if (health.isAlive()) {
                health.increase(health.getMaxHealth() / 2000f);
            }
        }
    }

}
