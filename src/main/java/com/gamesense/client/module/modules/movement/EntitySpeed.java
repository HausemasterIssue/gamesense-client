package com.gamesense.client.module.modules.movement;

import com.gamesense.api.event.events.TravelEvent;
import com.gamesense.api.setting.values.BooleanSetting;
import com.gamesense.api.setting.values.DoubleSetting;
import com.gamesense.client.module.Category;
import com.gamesense.client.module.Module;
import com.gamesense.client.module.modules.render.Freecam;
import com.mojang.realmsclient.gui.ChatFormatting;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.AbstractHorse;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.util.math.Vec3d;

@Module.Declaration(name = "EntitySpeed", category = Category.Movement)
public class EntitySpeed extends Module {
	
	DoubleSetting speed = registerDouble("Speed", 0.5, 0.0, 10.0);
	BooleanSetting antiStuck = registerBoolean("AntiStuck", true);
	
    @EventHandler
    private Listener<TravelEvent> onTravel = new Listener<>(event -> {
    	if (mc.player == null || mc.player.ridingEntity == null) {
    		return;
    	}
    	
    	if (mc.player.ridingEntity instanceof EntityPig || mc.player.ridingEntity instanceof AbstractHorse && mc.player.ridingEntity.getControllingPassenger().equals(mc.player)) {
    		moveEntity(mc.player.ridingEntity, speed.getValue(), antiStuck.getValue());
    		
    		if (mc.player.ridingEntity instanceof AbstractHorse) {
    			mc.player.ridingEntity.rotationYaw = mc.player.rotationYaw;
    		}
    		
    	}
    });
    
    public static void moveEntity(Entity entity, double speed, boolean antiStuck) {
		double yawRad = Math.toRadians(mc.player.rotationYaw - Freecam.getRotationFromVec(new Vec3d(-mc.player.moveStrafing, 0.0, mc.player.moveForward))[0]);

		if (isInputting()) {
			entity.motionX = -Math.sin(yawRad) * speed;
			entity.motionZ = Math.cos(yawRad) * speed;
		} else {
			entity.motionX = 0;
			entity.motionZ = 0;
		}
		
		if (antiStuck && entity.posY > entity.lastTickPosY) {
			entity.motionX = -Math.sin(yawRad) * 0.1;
			entity.motionZ = Math.cos(yawRad) * 0.1;
		}
	}
    
    public static boolean isInputting() {
		return mc.gameSettings.keyBindForward.isKeyDown() || mc.gameSettings.keyBindBack.isKeyDown() || mc.gameSettings.keyBindRight.isKeyDown() || mc.gameSettings.keyBindLeft.isKeyDown();
	}
	
	public String getHudInfo() {
        return "[" + ChatFormatting.WHITE + speed.getValue() + ChatFormatting.GRAY + "]";
    }
    

}
