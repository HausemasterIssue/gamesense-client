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
	DoubleSetting glideSpeed = registerDouble("GlideSpeed", 1.5, 0.0, 5.0);
	DoubleSetting upSpeed = registerDouble("UpSpeed", 1.0, 0, 5.0);
	DoubleSetting downSpeed = registerDouble("DownSpeed", 1.0, 0, 5.0);
	DoubleSetting fallSpeed = registerDouble("FallSpeed", 1.5, 0.0, 5.0);
	
	public void onEnable() {
		if(!mc.player.onGround && mc.player.isElytraFlying() && packet.getValue()) {
			mc.player.connection.sendPacket((Packet<?>) new CPacketEntityAction(mc.player, CPacketEntityAction.Action.START_FALL_FLYING));
		}
	}
	
	@EventHandler
    private final Listener<PlayerMoveEvent> playerMoveEvent = new Listener<>(event -> {

    	noMotion();
    	
    	final double[] dir = MotionUtil.forward(glideSpeed.getValue());
    	
    	
    	if (mc.player.movementInput.moveStrafe != 0f || mc.player.movementInput.moveForward != 0f) {
    		mc.player.motionX = dir[0];
    		mc.player.motionY = -(fallSpeed.getValue() / 2500f);
    		mc.player.motionZ = dir[1];
    	}
    	
    	if(mc.gameSettings.keyBindJump.isKeyDown()) {
    		mc.player.motionX = 0;
    		mc.player.motionY = upSpeed.getValue();
    		mc.player.motionZ = 0;
    	}
    	
    	if(mc.gameSettings.keyBindSneak.isKeyDown()) {
    		mc.player.motionX = 0;
    		mc.player.motionY -= downSpeed.getValue();
    		mc.player.motionZ = 0;
    	}
    	
    	
    	event.cancel();
    	
    	mc.player.prevLimbSwingAmount = 0f;
        mc.player.limbSwingAmount = 0f;
        mc.player.limbSwing = 0f;
    	
    });
	
	public void onDisable() {
		if(!mc.player.onGround && mc.player.isElytraFlying() && packet.getValue()) {
			mc.player.connection.sendPacket((Packet<?>) new CPacketEntityAction(mc.player, CPacketEntityAction.Action.START_FALL_FLYING));
		}
	}
	
	public static void noMotion() {
		mc.player.motionY = 0.0;
    		mc.player.motionX = 0.0;
    		mc.player.motionY = 0.0;
	}

}
