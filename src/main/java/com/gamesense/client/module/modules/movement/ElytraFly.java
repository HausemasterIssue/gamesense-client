package com.gamesense.client.module.modules.movement;

import com.gamesense.api.event.events.PlayerMoveEvent;
import com.gamesense.api.setting.values.BooleanSetting;
import com.gamesense.api.setting.values.DoubleSetting;
import com.gamesense.api.util.world.MotionUtil;
import com.gamesense.client.module.Category;
import com.gamesense.client.module.Module;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketEntityAction;

/*
* @author hausemasterissue
* @since 12/10/2021
*/

@Module.Declaration(name = "ElytraFly", category = Category.Movement)
public class ElytraFly extends Module {
	
	BooleanSetting packet = registerBoolean("Packet", false);
	DoubleSetting glideSpeed = registerDouble("GlideSpeed", 2.5, 0.0, 5.0);
	DoubleSetting upSpeed = registerDouble("UpSpeed", 1.0, 0, 5.0);
	DoubleSetting downSpeed = registerDouble("DownSpeed", 1.0, 0, 5.0);
	
	public void onEnable() {
		if(mc.player.isElytraFlying() && packet.getValue()) {
			mc.player.connection.sendPacket((Packet<?>) new CPacketEntityAction(mc.player, CPacketEntityAction.Action.START_FALL_FLYING));
		}
	}
	
	@EventHandler
    private final Listener<PlayerMoveEvent> playerMoveEvent = new Listener<>(event -> {

    	final double[] dir = MotionUtil.forward(glideSpeed.getValue());
    	
    	if(mc.player.isElytraFlying()) {
    		if(mc.gameSettings.keyBindJump.isKeyDown()) {
    			mc.player.motionY += upSpeed.getValue();
    		} else {
    			noMotion();
    		}
    		
    		if(mc.gameSettings.keyBindSneak.isKeyDown()) {
    			mc.player.motionY -= downSpeed.getValue();
    		}
    		
    		if(mc.gameSettings.keyBindRight.isKeyDown()) {
    			mc.player.motionZ = dir[1];
    		} else {
    			noMotion();
    		}
    		
    		if(mc.gameSettings.keyBindLeft.isKeyDown()) {
    			mc.player.motionZ = dir[1];
    		} else {
    			noMotion();
    		}
    		
    		if(mc.gameSettings.keyBindForward.isKeyDown()) {
    			mc.player.motionX = dir[0];
    		} else {
    			noMotion();
    		}
    		
    		if(mc.gameSettings.keyBindBack.isKeyDown()) {
    			mc.player.motionX = dir[0];;
    		} else {
    			noMotion();
    		}
    	}
    	
    });
	
	public void onDisable() {
		if(mc.player.isElytraFlying() && packet.getValue()) {
			mc.player.connection.sendPacket((Packet<?>) new CPacketEntityAction(mc.player, CPacketEntityAction.Action.START_FALL_FLYING));
		}
	}
	
	public static void noMotion() {
		mc.player.motionY = 0.0;
    		mc.player.motionX = 0.0;
    		mc.player.motionY = 0.0;
	}

}
