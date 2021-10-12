package com.gamesense.client.module.modules.movement;

import com.gamesense.api.event.events.PlayerMoveEvent;
import com.gamesense.api.setting.values.DoubleSetting;
import com.gamesense.client.module.Category;
import com.gamesense.client.module.Module;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketEntityAction;

@Module.Declaration(name = "ElytraFly", category = Category.Movement)
public class ElytraFly extends Module {
	
	DoubleSetting glideSpeed = registerDouble("GlideSpeed", 2.5, 0.0, 5.0);
	DoubleSetting upSpeed = registerDouble("UpSpeed", 1.0, 0, 5.0);
	DoubleSetting downSpeed = registerDouble("DownSpeed", 1.0, 0, 5.0);
	
	public void onEnable() {
		if(!mc.player.onGround && mc.player.isElytraFlying()) {
			mc.player.connection.sendPacket((Packet<?>) new CPacketEntityAction(mc.player, CPacketEntityAction.Action.START_FALL_FLYING));
		}
	}
	
	@EventHandler
    private final Listener<PlayerMoveEvent> playerMoveEvent = new Listener<>(event -> {

    	mc.player.motionY = 0.0;
    	mc.player.motionX = 0.0;
    	mc.player.motionY = 0.0;
    	
    	if(mc.player.isElytraFlying()) {
    		if(mc.gameSettings.keyBindJump.isKeyDown()) {
    			mc.player.motionY += upSpeed.getValue();
    		}
    		
    		if(mc.gameSettings.keyBindSneak.isKeyDown()) {
    			mc.player.motionY -= downSpeed.getValue();
    		}
    		
    		if(mc.gameSettings.keyBindRight.isKeyDown() || mc.gameSettings.keyBindLeft.isKeyDown()) {
    			mc.player.motionZ = glideSpeed.getValue();
    		}
    		
    		if(mc.gameSettings.keyBindForward.isKeyDown() || mc.gameSettings.keyBindBack.isKeyDown()) {
    			mc.player.motionX = glideSpeed.getValue();
    		}
    	}
    	
    });
	
	public void onDisable() {
		if(mc.player.isElytraFlying()) {
			mc.player.connection.sendPacket((Packet<?>) new CPacketEntityAction(mc.player, CPacketEntityAction.Action.START_FALL_FLYING));
		}
	}

}
