package com.gamesense.client.module.modules.movement;

import com.gamesense.api.event.events.EntitySaddledEvent;
import com.gamesense.api.event.events.SteerEntityEvent;
import com.gamesense.api.event.events.TravelEvent;
import com.gamesense.api.setting.values.BooleanSetting;
import com.gamesense.api.setting.values.DoubleSetting;
import com.gamesense.client.module.Category;
import com.gamesense.client.module.Module;
import com.gamesense.client.module.modules.render.Freecam;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import me.zero.alpine.type.Cancellable;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;

/*
* @author hausemasterissue, aesthetical
* @since 4/10/2021
*/
@Module.Declaration(name = "EntityControl", category = Category.Movement)
public class EntityControl extends Module {
    BooleanSetting speedUp = registerBoolean("SpeedUp", false);
    DoubleSetting speed = registerDouble("Speed", 0.5, 0.0, 10.0);
    BooleanSetting antiStuck = registerBoolean("AntiStuck", true);

	@EventHandler
    private final Listener<SteerEntityEvent> onSteerEntity = new Listener<>(Cancellable::cancel);

    @EventHandler
    private final Listener<EntitySaddledEvent> onEntitySaddled = new Listener<>(Cancellable::cancel);

    @EventHandler
    private final Listener<TravelEvent> onTravel = new Listener<>(event -> {
        if (speedUp.getValue()) {

            if (mc.player.ridingEntity != null && mc.player.ridingEntity.getControllingPassenger().equals(mc.player)) {
                moveEntity(mc.player.ridingEntity, speed.getValue(), antiStuck.getValue());
                mc.player.ridingEntity.rotationYaw = mc.player.rotationYaw;
            }
        }
    });

    public static boolean isInputting() {
        return mc.gameSettings.keyBindForward.isKeyDown() || mc.gameSettings.keyBindBack.isKeyDown() || mc.gameSettings.keyBindRight.isKeyDown() || mc.gameSettings.keyBindLeft.isKeyDown();
    }

    // this whole thing isnt needed though -
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
}
