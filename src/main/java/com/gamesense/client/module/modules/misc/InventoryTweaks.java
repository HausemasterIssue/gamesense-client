package com.gamesense.client.module.modules.misc;

/**
 * @author Christallinqq
 * @since 10/4/21
 */

import com.gamesense.api.event.events.PacketEvent;
import com.gamesense.client.GameSense;
import com.gamesense.client.module.Module;

import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;

import net.minecraft.client.Minecraft;
import net.minecraft.network.play.client.CPacketCloseWindow;

public class InventoryTweaks extends Module {
    public void onEnable() {
        GameSense.EVENT_BUS.subscribe(this);
    }

    public void onDisable() {
        GameSense.EVENT_BUS.unsubscribe(this);
    }

    @EventHandler
    private final Listener<PacketEvent.Send> listener = new Listener<>(event -> {
        if (event.getPacket() instanceof CPacketCloseWindow) {
            final CPacketCloseWindow packet = (CPacketCloseWindow) event.getPacket();
            if (packet.windowId == Minecraft.getMinecraft().player.inventoryContainer.windowId) {
                event.cancel();
            }
        }
    });
}
