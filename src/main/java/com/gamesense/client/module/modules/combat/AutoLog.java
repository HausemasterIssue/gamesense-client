package com.gamesense.client.module.modules.combat;

import com.gamesense.api.setting.values.BooleanSetting;
import com.gamesense.api.setting.values.IntegerSetting;
import com.gamesense.api.util.player.InventoryUtil;
import com.gamesense.client.module.Category;
import com.gamesense.client.module.Module;
import net.minecraft.init.Items;
import net.minecraft.util.text.TextComponentString;

/*
* @author hausemasterissue
* @since 20/10/2021
* credits to momentum, cookie client
*/

@Module.Declaration(name = "AutoLog", category = Category.Combat)
public class AutoLog extends Module {
	
	BooleanSetting healthLog = registerBoolean("HealthLog", false);
	IntegerSetting health = registerInteger("Health", 4, 0, 36);
	BooleanSetting totemLog = registerBoolean("TotemLog", true);
	IntegerSetting noTotems = registerInteger("Totems", 1, 0, 36);
	
	@Override
    public void onUpdate() {
        if (mc.world == null || mc.player == null)
            return;
	    
	if(healthLog.getValue()) {
		if(mc.player.getHealth() <= health.getValue()) {
			log("[SpiderSense] [AutoLog] You have been logged out do to being lower than the minimum health allowed");
		}
	}
	    
	if(totemLog.getValue()) {
		if(InventoryUtil.getItemCount(Items.TOTEM_OF_UNDYING) <= noTotems.getValue()) {
			log("[SpiderSense] [AutoLog] You have been logged out do to having the minimum amount of totems allowed");
		}
	}

    }
	
	public void log(String message) {
		if (mc.player == null || mc.player.connection == null) {
			return;
		}
		
		mc.player.connection.getNetworkManager().closeChannel(new TextComponentString(message));
		disable();
	}

	

}
