package com.gamesense.client.module.modules.movement;

import com.gamesense.api.event.events.PacketEvent;
import com.gamesense.api.setting.values.DoubleSetting;
import com.gamesense.api.setting.values.ModeSetting;
import com.gamesense.client.module.Category;
import com.gamesense.client.module.Module;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.init.MobEffects;
import net.minecraft.network.play.server.SPacketPlayerPosLook;
import net.minecraftforge.client.event.InputUpdateEvent;

import java.util.Collections;
import java.util.Objects;

/*
* @author hausemasterissue
* @since 16/10/2021
* CATCLIENT SRC LEAK LMFAO
*/

@Module.Declaration(name = "StrafeTest", category = Category.Movement)
public class StrafeTest extends Module {
	
	ModeSetting mode = registerMode("Mode", Collections.singletonList("StrictStrafe"), "StrictStrafe");
	DoubleSetting speed = registerDouble("Speed", 2.6, 0.0, 10.0);
	
	private double moveSpeed = 0.0;
    private double lastDist = 0.0;
    private int stage = 4;
    
    @Override
    public void onUpdate() {
        if (!(mc.player.isInLava() || mc.player.isInWater())) {
            return;
        }
        lastDist = Math.sqrt(Math.pow(mc.player.posX - mc.player.prevPosX, 2.0) + Math.pow(mc.player.posZ - mc.player.prevPosZ, 2.0));
    }
    
    @EventHandler
	private final Listener<InputUpdateEvent> eventListener = new Listener<>(event -> {
		int amplifier;
		if ((mc.player.moveForward != 0.0f || mc.player.moveStrafing != 0.0f) && mc.player.onGround) {
            stage = 2;
        }
        if (stage == 1) {
            moveSpeed = mode.getValue().equalsIgnoreCase("StrictStrafe") ? 0.33119999999999994 : 1.38 * (speed.getValue() / 10.0);
            if (mc.player.isPotionActive(MobEffects.SPEED)) {
                amplifier = Objects.requireNonNull(mc.player.getActivePotionEffect(MobEffects.SPEED)).getAmplifier();
                moveSpeed *= 1.0 + 0.2 * (double)(amplifier + 1);
            }
            ++stage;
        } else if (stage == 2) {
            if (mc.player.movementInput.moveStrafe != 0.0f || mc.player.movementInput.moveForward != 0.0f) {
                mc.player.motionY = 0.3995f;
            }
            moveSpeed *= 2.149;
            if (mc.player.isPotionActive(MobEffects.SPEED)) {
                amplifier = Objects.requireNonNull(mc.player.getActivePotionEffect(MobEffects.SPEED)).getAmplifier();
                moveSpeed *= 1.0 + 0.2 * (double)(amplifier + 1);
            }
            ++stage;
        } else if (stage == 3) {
            moveSpeed = mode.getValue().equalsIgnoreCase("StrictStrafe") ? lastDist - 0.66 * (lastDist - 0.24) : lastDist - 0.66 * (lastDist - speed.getValue() / 10.0);
            if (mc.player.isPotionActive(MobEffects.SPEED)) {
                amplifier = Objects.requireNonNull(mc.player.getActivePotionEffect(MobEffects.SPEED)).getAmplifier();
                moveSpeed *= 1.0 + 0.2 * (double)(amplifier + 1);
            }
            ++stage;
        } else {
            if (mc.world.getCollisionBoxes(mc.player, mc.player.getEntityBoundingBox().offset(0.0, mc.player.motionY, 0.0)).size() > 0 || mc.player.collidedVertically) {
                stage = 1;
            }
            moveSpeed = lastDist - lastDist / 159.0;
        }
        moveSpeed = mode.getValue().equalsIgnoreCase("StrictStrafe") ? Math.min(Math.max(moveSpeed, 0.24), 0.551) : Math.min(Math.max(moveSpeed, speed.getValue() / 10.0), 0.551);
        float forward = mc.player.movementInput.moveForward;
        float strafe = mc.player.movementInput.moveStrafe;
        float yaw = mc.player.rotationYaw;
        if ((double)mc.player.moveForward == 0.0 && (double)mc.player.moveStrafing == 0.0) {
        	mc.player.motionX = 0.0;
        	mc.player.motionZ = 0.0;
        } else if (forward != 0.0f) {
            if (strafe >= 1.0f) {
                yaw += (float)(forward > 0.0f ? -45 : 45);
                strafe = 0.0f;
            } else if (strafe <= -1.0f) {
                yaw += (float)(forward > 0.0f ? 45 : -45);
                strafe = 0.0f;
            }
            if (forward > 0.0f) {
                forward = 1.0f;
            } else if (forward < 0.0f) {
                forward = -1.0f;
            }
        }
        double sin = Math.sin(Math.toRadians(yaw + 90.0f));
        double cos = Math.cos(Math.toRadians(yaw + 90.0f));
        mc.player.motionX = (double)forward * moveSpeed * cos + (double)strafe * moveSpeed * sin;
        mc.player.motionZ = (double)forward * moveSpeed * sin - (double)strafe * moveSpeed * cos;
        if ((double)Speed.mc.player.moveForward == 0.0 && (double)Speed.mc.player.moveStrafing == 0.0) {
        	mc.player.motionX = 0.0;
        	mc.player.motionZ = 0.0;
        }}
	);
    
    @EventHandler
	private final Listener<PacketEvent.Receive> packetListener = new Listener<>(event -> {
		if (PacketEvent.getPacket() instanceof SPacketPlayerPosLook) {
            moveSpeed = 0.0;
            lastDist = 0.0;
            stage = 4;
        }
	});

}
