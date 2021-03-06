package com.pixelthieves.elementtd.system.resolve.ui;

import com.artemis.Aspect;
import com.artemis.Entity;
import com.artemis.EntitySystem;
import com.artemis.utils.ImmutableBag;
import com.pixelthieves.elementtd.component.ShopComponent;
import com.pixelthieves.elementtd.component.TowerTypeComponent;

public class FindAttackTower extends EntitySystem {

    protected ImmutableBag<Entity> entities;

    public FindAttackTower() {
        super(Aspect.getAspectForAll(TowerTypeComponent.class).exclude(ShopComponent.class));
    }

    @Override
    protected final void processEntities(ImmutableBag<Entity> entities) {
        this.entities = entities;
    }

    @Override
    protected boolean checkProcessing() {
        return true;
    }

    public ImmutableBag<Entity> getEntities() {
        return entities;
    }
}

