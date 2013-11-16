package com.xkings.pokemontd.manager;

import com.artemis.World;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector3;
import com.xkings.core.logic.Clock;
import com.xkings.core.logic.UpdateFilter;
import com.xkings.core.logic.Updateable;
import com.xkings.pokemontd.App;
import com.xkings.pokemontd.Player;
import com.xkings.pokemontd.component.WaveComponent;
import com.xkings.pokemontd.entity.creep.Creep;
import com.xkings.pokemontd.entity.creep.CreepAbilityType;
import com.xkings.pokemontd.entity.creep.CreepName;
import com.xkings.pokemontd.entity.creep.CreepType;
import com.xkings.pokemontd.map.Path;
import com.xkings.pokemontd.map.PathPack;
import com.xkings.pokemontd.system.resolve.FireTextInfo;

import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Tomas on 10/5/13.
 */
public class WaveManager implements Updateable {
    private final World world;
    private final PathPack pathPack;

    private final Iterator<CreepName> creeps;
    private final UpdateFilter filter;
    private final Player player;
    private final App app;
    private boolean active;
    private CreepType nextWave;
    private final List<WaveComponent> waves = new LinkedList<WaveComponent>();

    public WaveManager(App app, PathPack pathPack, float interval) {
        this.app = app;
        this.world = app.getWorld();
        this.player = app.getPlayer();
        this.pathPack = pathPack;
        this.creeps = Arrays.asList(CreepName.values()).iterator();
        this.active = true;
        this.filter = new UpdateFilter(this, interval);
        updateWave();
        app.getClock().addService(filter);
    }

    @Override
    public void update(float delta) {
        if (nextWave != null) {
            fireNextWave(nextWave);
        } else{
            app.endGame();
        }
    }

    public void fireNextWave(CreepType next) {
        WaveComponent wave = new WaveComponent(this,next.getId());
        for (int i = 0; i < next.getCreepsInWave(); i++) {
            Path path = getAppropriatePath(pathPack, next);
            Vector3 startPoint = path.getPath().get(0);
            Vector3 nextPoint = path.getPath().get(1);
            double angleToNextPoint = Math.atan2(nextPoint.y - startPoint.y, nextPoint.x - startPoint.x);
            float xOffset = (float) (Math.cos(angleToNextPoint + Math.PI) * next.getDistanceBetweenCreeps() * i);
            float yOffset = (float) (Math.sin(angleToNextPoint + Math.PI) * next.getDistanceBetweenCreeps() * i);
            Creep.registerCreep(world, new Path(path), wave, next, startPoint.x + xOffset, startPoint.y + yOffset);
        }
        registerWave(wave);
        updateWave();
    }

    private void registerWave(WaveComponent wave) {
        waves.add(wave);
    }

    private void unregisterWave(WaveComponent wave) {
        if ((wave.getId() + 1) % 5 == 0) {
            player.addFreeElement();
            world.getSystem(FireTextInfo.class).fireText("FREE ELEMENT", Color.GREEN);
        }
        waves.remove(wave);
    }

    private Path getAppropriatePath(PathPack pathPack, CreepType next) {
        if (next.getAbilityType() == CreepAbilityType.SWARM) {
            return pathPack.get(App.RANDOM.nextInt(pathPack.size()));
        } else {
            return pathPack.getMain();
        }
    }

    private void updateWave() {
        nextWave = creeps.hasNext() ? CreepType.getWave(creeps.next()) : null;
    }

    @Override
    public boolean isActive() {
        return active;
    }

    @Override
    public void setActive(boolean active) {
        this.active = active;
    }

    public int getRemainingTime() {
        return (int) filter.getRemainingTime() + 1;
    }

    public CreepType getNextWave() {
        return nextWave;
    }

    public void removeWave(WaveComponent waveComponent) {
        this.unregisterWave(waveComponent);
        if (waves.isEmpty()) {
            filter.triggerUpdate();
        }
    }

    public void triggerNextWave() {
        filter.triggerUpdate();
    }

}
