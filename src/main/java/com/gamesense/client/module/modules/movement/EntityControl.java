package com.gamesense.client.module.modules.movement;

import com.gamesense.client.module.Category;
import com.gamesense.client.module.Module;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import com.gamesense.api.event.events.EntitySaddledEvent;
import com.gamesense.api.event.events.SteerEntityEvent;

@Module.Declaration(name = "EntityControl", category = Category.Movement)
public class EntityControl extends Module {
	
	@EventHandler
    private Listener<SteerEntityEvent> onSteerEntity = new Listener<>(event -> {
    	event.cancel();
    });

    @EventHandler
    private Listener<EntitySaddledEvent> onEntitySaddled = new Listener<>(event -> {
        event.cancel();
    });
    
}
