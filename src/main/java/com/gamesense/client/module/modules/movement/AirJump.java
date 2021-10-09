package com.gamesense.client.module.modules.movement;

import com.gamesense.api.setting.values.BooleanSetting;
import com.gamesense.client.module.Category;
import com.gamesense.client.module.Module;
import net.minecraft.network.play.client.CPacketPlayer;

/*
* @author hausemasterissue
* @since 9/10/2021
* creds to momentum
* intellij is sexy
*/

@Module.Declaration(name = "AirJump", category = Category.Movement)
public class AirJump extends Module {
	
	BooleanSetting packet = registerBoolean("Packet", true);
	
	@Override
    public void onUpdate() {
        if (mc.world == null || mc.player == null)
            return;

        mc.player.onGround = true;

        if (packet.getValue()) {
            mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY + 0.41999998688698D, mc.player.posZ, true));
            mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY + 0.7531999805211997D, mc.player.posZ, true));
            mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY + 1.00133597911214D, mc.player.posZ, true));
            mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY + 1.16610926093821D, mc.player.posZ, true));
        }
    }

}
