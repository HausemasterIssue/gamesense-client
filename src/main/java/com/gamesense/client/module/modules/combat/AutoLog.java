package com.gamesense.client.module.modules.combat;

import com.gamesense.client.module.Module;
import net.minecraft.init.Items;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.SPacketDisconnect;
import net.minecraft.util.text.TextComponentString;

import com.gamesense.api.setting.values.IntegerSetting;
import com.gamesense.api.util.player.InventoryUtil;
import com.gamesense.client.module.Category;

@Module.Declaration(name = "AutoLog", category = Category.Combat)
public class AutoLog extends Module {
	
	IntegerSetting health = registerInteger("Health", 0, 0, 36);
	IntegerSetting noTotems = registerInteger("Totems", 0, 0, 36);
	
	@Override
    public void onUpdate() {
        if (mc.world == null || mc.player == null)
            return;

        if (mc.player.getHealth() <= health.getValue())
        	mc.player.connection.sendPacket((Packet<?>) new SPacketDisconnect(new TextComponentString("[SpiderSense AutoLog] You have been logged out")));;

        if (InventoryUtil.getItemCount(Items.TOTEM_OF_UNDYING) <= noTotems.getValue())
        	mc.player.connection.sendPacket((Packet<?>) new SPacketDisconnect(new TextComponentString("[SpiderSense AutoLog] You have been logged out")));

    }

	

}
