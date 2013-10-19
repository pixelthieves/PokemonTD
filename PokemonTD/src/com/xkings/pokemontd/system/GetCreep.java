package com.xkings.pokemontd.system;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.systems.EntityProcessingSystem;
import com.badlogic.gdx.math.Vector3;
import com.xkings.core.component.PositionComponent;
import com.xkings.core.component.SizeComponent;
import com.xkings.pokemontd.component.WaveComponent;

public class GetCreep extends EntityProcessingSystem {
    @Mapper
    ComponentMapper<PositionComponent> positionMapper;
    @Mapper
    ComponentMapper<SizeComponent> sizeMapper;

    private Entity entity;
    private float x;
    private float y;

    /**
     * Finds desired creep on giver coordinates
     */
    public GetCreep() {
        super(Aspect.getAspectForAll(PositionComponent.class, SizeComponent.class, WaveComponent.class));
    }

    @Override
    protected void process(Entity entity) {
        Vector3 position = positionMapper.get(entity).getPoint();
        Vector3 size = sizeMapper.get(entity).getPoint();
        if (x >= position.x - size.x / 2 && x < position.x + size.x / 2) {
            if (y >= position.y - size.y / 2 && y < position.y + size.y / 2) {
                this.entity = entity;
            }
        }
    }

    public Entity getEntity(float x, float y) {
        this.x = x;
        this.y = y;
        this.entity = null;
        this.process();
        return entity;
    }

}