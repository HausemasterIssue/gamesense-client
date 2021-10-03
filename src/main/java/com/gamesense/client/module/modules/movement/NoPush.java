package com.gamesense.client.module.modules.movement;

/*
@author Christallinqq
@since 10/3/21
 */

import com.gamesense.api.event.events.WaterPushEvent;
import com.gamesense.client.GameSense;
import com.gamesense.client.module.Category;
import com.gamesense.client.module.Module;

import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;

@Module.Declaration(name = "NoPush", category = Category.Movement)
public class NoPush extends Module {
    @EventHandler
    private final Listener<WaterPushEvent> waterPushEventListener = new Listener<>(event -> {
        event.cancel();
    });

    public void onEnable() {
        GameSense.EVENT_BUS.subscribe(this);
    }

    public void onDisable() {
        GameSense.EVENT_BUS.unsubscribe(this);
    }
}
