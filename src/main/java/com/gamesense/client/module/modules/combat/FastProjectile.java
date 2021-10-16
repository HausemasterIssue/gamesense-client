package com.gamesense.client.module.modules.combat;

import com.gamesense.api.event.events.PacketEvent;
import com.gamesense.api.util.player.InventoryUtil;
import com.gamesense.client.module.Category;
import com.gamesense.client.module.Module;
import net.minecraft.init.Items;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketPlayerDigging;

/*
* @author hausemasterissue
* since 5/10/2021
* pasted from cosmos, fuck 0x22
*/


@Module.Declaration(name = "BowStrength", category = Category.Exploits)
public class FastProjectile extends Module {
	
    public void onPacketSend(PacketEvent.Send event) {
        if (PacketEvent.getPacket() instanceof CPacketPlayerDigging && ((CPacketPlayerDigging) PacketEvent.getPacket()).getAction().equals(CPacketPlayerDigging.Action.RELEASE_USE_ITEM)) {
            if (InventoryUtil.isHolding(Items.BOW) && mc.player.getItemInUseMaxCount() >= 20) {
                for (int ticks = 0; ticks < 10; ticks++) {
                    double sin = -Math.sin(Math.toRadians(mc.player.rotationYaw));
                    double cos = Math.cos(Math.toRadians(mc.player.rotationYaw));
                    mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY - 0.1, mc.player.posZ, false));
                    mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX + sin * 100, mc.player.posY, mc.player.posZ + cos * 100, true));
                }
            }
        }
    }

}
