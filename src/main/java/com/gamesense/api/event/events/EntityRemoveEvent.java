package com.gamesense.api.event.events;

import net.minecraft.entity.Entity;
import com.gamesense.api.event.GameSenseEvent;

public class EntityRemoveEvent extends GameSenseEvent {
	
	private final Entity entity;

    public EntityRemoveEvent(Entity entity) {
        this.entity = entity;
    }

    public Entity getEntity() {
        return entity;
    }
  
}
