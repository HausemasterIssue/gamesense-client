package com.gamesense.client.module.modules.movement;

import java.util.Arrays;
import com.gamesense.api.event.events.AddCollisionBoxToListEvent;
import com.gamesense.api.setting.values.ModeSetting;
import com.gamesense.client.module.Category;
import com.gamesense.client.module.Module;
import com.mojang.realmsclient.gui.ChatFormatting;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;

/*
* @author hausemasterissue
* @since 6/10/2021
* creds to cousinware
*/

@Module.Declaration(name = "AntiWeb", category = Category.Movement)
public class AntiWeb extends Module {
	
	ModeSetting downMode = registerMode("Mode", Arrays.asList("Strict", "Packet", "Timer", "Float", "Other"), "Timer");
	
	private static final AxisAlignedBB webFloat = new AxisAlignedBB(0.D, 0.D, 0.D, 1.D, 0.999D, 1.D);
	boolean collided;
    int time = 0;
    int delay = 0;
    public boolean getisInWeb;
    public float speed = 1.0f;
    
    @Override
    public void onUpdate() {
        if (mc.world == null || mc.player == null)
            return;


        if (mc.player.isInWeb) {
            delay++;
            if (downMode.getValue().equalsIgnoreCase("Strict")) {
                mc.player.motionY = -0.22000000000000003;

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
                	mc.timer.tickLength = 50.0f / ((speed == 0.0f) ? 0.1f : speed);
                } else {
                	mc.timer.tickLength = 50.0f;
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
	speed = 4.0f;
        collided = false;
    }
    
    public String getHudInfo() {
        return "[" + ChatFormatting.WHITE + downMode.getValue() + ChatFormatting.GRAY + "]";
    }


}
