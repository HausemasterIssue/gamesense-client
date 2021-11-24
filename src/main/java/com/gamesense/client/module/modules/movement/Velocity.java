package com.gamesense.client.module.modules.movement;

import com.gamesense.api.event.events.BlockPushEvent;
import com.gamesense.api.event.events.EntityCollisionEvent;
import com.gamesense.api.event.events.PacketEvent;
import com.gamesense.client.module.Category;
import com.gamesense.client.module.Module;
import com.gamesense.api.setting.values.BooleanSetting;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.network.play.server.SPacketEntityVelocity;
import net.minecraft.network.play.server.SPacketExplosion;

@Module.Declaration(name = "Velocity", category = Category.Movement)
public class Velocity extends Module {
	BooleanSetting blocks = registerBoolean("Blocks", true);
	BooleanSetting noPush = registerBoolean("No Push", true);
	BooleanSetting explosions = registerBoolean("Explosions", true);

	@EventHandler
	private final Listener<BlockPushEvent> blockPushEventListener = new Listener<>(event -> {
	    if (blocks.getValue()) {
	        event.cancel();
        }
    });

    @EventHandler
    private final Listener<EntityCollisionEvent> entityCollisionEventListener = new Listener<>(event -> {
        if (noPush.getValue()) {
            event.cancel();
        }
    });
    
    @EventHandler
    private final Listener<PacketEvent.Receive> receiveListener = new Listener<>(event -> {
    	if (explosions.getValue()) {
    		if (PacketEvent.getPacket() instanceof SPacketEntityVelocity) {
                if (((SPacketEntityVelocity) PacketEvent.getPacket()).getEntityID() == mc.player.getEntityId()) {
                    event.cancel();
                }
            } else if (PacketEvent.getPacket() instanceof SPacketExplosion) {
                event.cancel();
            }
    	}
    });
}
