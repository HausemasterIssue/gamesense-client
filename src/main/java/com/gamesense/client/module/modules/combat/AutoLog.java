package com.gamesense.client.module.modules.combat;

import com.gamesense.client.module.Module;
import net.minecraft.init.Items;
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
        	log("[SpiderSense] [AutoLog] You have been logged out do to being lower than the minimum health allowed");

        if (InventoryUtil.getItemCount(Items.TOTEM_OF_UNDYING) <= noTotems.getValue())
        	log("[SpiderSense] [AutoLog] You have been logged out do to exceeding the minimum amount of Totems");

    }
	
	public void log(String message) {
		if (mc.player == null || mc.player.connection == null) {
			return;
		}
		
		mc.player.connection.getNetworkManager().closeChannel(new TextComponentString(message));
		disable();
	}

	

}
