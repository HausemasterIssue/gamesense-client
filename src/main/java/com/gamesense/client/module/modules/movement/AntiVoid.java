package com.gamesense.client.module.modules.movement;

import java.util.Arrays;
import com.gamesense.api.event.events.AddCollisionBoxToListEvent;
import com.gamesense.api.setting.values.ModeSetting;
import com.gamesense.client.module.Category;
import com.gamesense.client.module.Module;
import com.gamesense.mixin.mixins.accessor.IEntity;
import com.mojang.realmsclient.gui.ChatFormatting;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.init.Blocks;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.math.AxisAlignedBB;

@Module.Declaration(name = "AntiWeb", category = Category.Movement)
public class AntiWeb extends Module {
	
	ModeSetting downMode = registerMode("Mode", Arrays.asList("Strict", "Packet", "Timer", "Float", "Other"), "Strict");
	
	private static final AxisAlignedBB webFloat = new AxisAlignedBB(0.D, 0.D, 0.D, 1.D, 0.999D, 1.D);
	boolean collided;
    int time = 0;
    int delay = 0;
    public boolean getisInWeb;
    
    @Override
    public void onUpdate() {
        if (mc.world == null || mc.player == null)
            return;


        if (((IEntity) mc.player).getIsInWeb()) {
            delay++;
            if (downMode.getValue().equalsIgnoreCase("Strict")) {
                mc.player.motionY = 1.1 / -5;

            }
            if (downMode.getValue().equalsIgnoreCase("Other")) {
                mc.player.motionY = 20.7 / -5;
            }
            if (downMode.getValue().equalsIgnoreCase("Packet")) {
                if (!mc.player.collidedHorizontally && !mc.player.collidedVertically && !(time >= 9)) {
                    time++;
                    mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY, mc.player.posZ - .1, mc.player.onGround));
                    mc.player.setPosition(mc.player.posX, mc.player.posY - .09, mc.player.posZ);
                }
            }
            if (downMode.getValue().equalsIgnoreCase("Timer")) {
                if (!(mc.player.onGround)) {
                    if (delay > 5) {
                        mc.player.motionX *= .0001;
                        mc.player.motionZ *= .0001;

                    }
                } else {
                    delay = 0;
                }


            }
        } else {
            delay = 0;
        }
        if (!downMode.getValue().equalsIgnoreCase("Timer")) {
        	// i pasted this and this was empty so... rip ig lmao
        }

    }
    
    
    @EventHandler
    private final Listener<AddCollisionBoxToListEvent> collisionBoxEvent = new Listener<>(event -> {
    	if (downMode.getValue().equalsIgnoreCase("Float")) {
            if (event.getBlock().equals(Blocks.WEB)) {
                AxisAlignedBB axisalignedbb = webFloat.offset(event.getPos());
                if (event.getEntityBox().intersects(axisalignedbb)) event.getCollidingBoxes().add(axisalignedbb);
                event.cancel();
            }
    	}
    });
    
    @Override
    public void onEnable() {
        collided = false;
    }
    
    public String getHudInfo() {
        return "[" + ChatFormatting.WHITE + downMode.getValue() + ChatFormatting.GRAY + "]";
    }


}
