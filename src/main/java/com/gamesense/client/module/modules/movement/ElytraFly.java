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
* @since 13/10/2021
*/

@Module.Declaration(name = "ElytraFly", category = Category.Movement)
public class ElytraFly extends Module {
	
	BooleanSetting packet = registerBoolean("Packet", false);
	DoubleSetting glideSpeed = registerDouble("GlideSpeed", 1.5, 0.0, 5.0);
	DoubleSetting upSpeed = registerDouble("UpSpeed", 1.0, 0, 5.0);
	DoubleSetting downSpeed = registerDouble("DownSpeed", 1.0, 0, 5.0);
	DoubleSetting fallSpeed = registerDouble("FallSpeed", 1.5, 0.0, 5.0);
	BooleanSetting autoTakeoff = registerBoolean("AutoTakeoff", false);
	BooleanSetting groundDisable = registerBoolean("GroundDisable", false);
	BooleanSetting antiKick = registerBoolean("AntiKick", true);
	BooleanSetting ncpStrict = registerBoolean("NCP Strict", true);
	DoubleSetting strictPitch = registerDouble("StrictPitch", 30.0, 0, 90.0);
	
	private boolean takingOff = false;
	
	public void onEnable() {
		if(!mc.player.onGround && packet.getValue() && !autoTakeoff.getValue()) {
			mc.player.connection.sendPacket((Packet<?>) new CPacketEntityAction(mc.player, CPacketEntityAction.Action.START_FALL_FLYING));
		}
		
		if(autoTakeoff.getValue()) {
			takingOff = true;
			mc.timer.tickLength = ((float)(50.0 * 10));
			mc.player.jump();
			mc.player.connection.sendPacket((Packet<?>) new CPacketEntityAction(mc.player, CPacketEntityAction.Action.START_FALL_FLYING));
			takingOff = false;
			mc.timer.tickLength = 50f;
		}
	}
	
	public void onUpdate() {
		if(mc.player.onGround && takingOff == false) {
			disable();
		}
			
	}
	
	@EventHandler
    private final Listener<PlayerMoveEvent> playerMoveEvent = new Listener<>(event -> {

    	noMotion();
    	
    	final double[] dir = MotionUtil.forward(glideSpeed.getValue());
    	
    	
    	if (mc.player.movementInput.moveStrafe != 0f || mc.player.movementInput.moveForward != 0f) {
    		mc.player.motionX = dir[0];
		if(antiKick.getValue() == true) {
			mc.player.motionY = -(fallSpeed.getValue() / 2500f);
		} else {
			mc.player.motionY = 0f;	
		}
    		mc.player.motionZ = dir[1];
    	}
    	
    	if(!ncpStrict.getValue()) {
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
    	} else {
    		if (mc.gameSettings.keyBindJump.isKeyDown()) {
                mc.player.rotationPitch = (float) -strictPitch.getValue();
                mc.player.motionY = upSpeed.getValue();
            }

            else if (mc.gameSettings.keyBindSneak.isKeyDown()) {
                mc.player.rotationPitch = (float) ((double) strictPitch.getValue());
                mc.player.motionY -= downSpeed.getValue();
            }
	}
    	
    	event.cancel();
    	
    	mc.player.prevLimbSwingAmount = 0f;
        mc.player.limbSwingAmount = 0f;
        mc.player.limbSwing = 0f;
    	
    });
	
	public void onDisable() {
		mc.timer.tickLength = 50f;
	}
	
	public static void noMotion() {
		mc.player.motionY = 0.0;
    		mc.player.motionX = 0.0;
    		mc.player.motionZ = 0.0;
	}
	

}
