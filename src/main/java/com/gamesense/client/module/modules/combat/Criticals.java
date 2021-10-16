package com.gamesense.client.module.modules.combat;

import com.gamesense.api.event.events.PacketEvent;
import com.gamesense.api.setting.values.ModeSetting;
import com.gamesense.client.module.Category;
import com.gamesense.client.module.Module;
import com.mojang.realmsclient.gui.ChatFormatting;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketUseEntity;

import java.util.Arrays;

/*
 * @author hausemasterissue, sexmurai
 * @since 9/29/2021
 */

@Module.Declaration(name = "Criticals", category = Category.Combat)
public class Criticals extends Module {
	
	ModeSetting critMode = registerMode("Mode", Arrays.asList("Packet", "Jump", "MiniJump", "NCPStrict"), "NCPStrict");

    @EventHandler
    private final Listener<PacketEvent.Send> packetSendListener = new Listener<>(event -> {
        if (PacketEvent.getPacket() instanceof CPacketUseEntity) {
            CPacketUseEntity packet = (CPacketUseEntity) PacketEvent.getPacket();
            if (packet.getAction() != CPacketUseEntity.Action.ATTACK || !(packet.getEntityFromWorld(mc.world) instanceof EntityLivingBase) || !mc.player.onGround || mc.player.isInLava() || mc.player.isInWater()) {
                return;
            }

            switch (critMode.getValue()) {
                case "Jump": {
                    mc.player.jump();
                    mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY - 0.05, mc.player.posZ, false));
                    mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY, mc.player.posZ, false));
                    break;
                }

                case "Packet": {
                    mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY, mc.player.posZ, false));
                    mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY + 0.3, mc.player.posZ, false));
                    mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY, mc.player.posZ, false));
                    break;
                }
		
		case "MiniJump": {
		    mc.player.motionY = 0.2;
                    mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY - 0.02, mc.player.posZ, false));
                    mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY, mc.player.posZ, false));
                    break;
		}

                case "NCPStrict": {
                    mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY + 0.062602401692772D, mc.player.posZ, false));
                    mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY + 0.0726023996066094D, mc.player.posZ, false));
                    mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY, mc.player.posZ, false));
                    break;
                }
            }
        }
    });
    
    public String getHudInfo() {
        return "[" + ChatFormatting.WHITE + this.critMode.getValue() + ChatFormatting.GRAY + "]";
    }
}


