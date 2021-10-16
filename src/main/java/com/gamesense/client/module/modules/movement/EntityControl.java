package com.gamesense.client.module.modules.movement;

import com.gamesense.api.event.events.EntitySaddledEvent;
import com.gamesense.api.event.events.SteerEntityEvent;
import com.gamesense.client.module.Category;
import com.gamesense.client.module.Module;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import me.zero.alpine.type.Cancellable;

/*
* @author hausemasterissue
* @since 4/10/2021
*/

@Module.Declaration(name = "EntityControl", category = Category.Movement)
public class EntityControl extends Module {
	
	@EventHandler
    private final Listener<SteerEntityEvent> onSteerEntity = new Listener<>(Cancellable::cancel);

    @EventHandler
    private final Listener<EntitySaddledEvent> onEntitySaddled = new Listener<>(Cancellable::cancel);
    
}
