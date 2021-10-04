package com.gamesense.client.module.modules.combat;

import com.gamesense.client.module.Module;
import net.minecraft.init.Items;
import com.gamesense.api.setting.values.IntegerSetting;
import com.gamesense.api.util.player.InventoryUtil;
import com.gamesense.api.util.player.PlayerUtil;
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
            PlayerUtil.disconnectFromWorld(this);

        if (InventoryUtil.getItemCount(Items.TOTEM_OF_UNDYING) <= noTotems.getValue())
            PlayerUtil.disconnectFromWorld(this);

    }

	

}
