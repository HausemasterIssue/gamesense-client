package com.gamesense.client.module.modules.misc;

import com.gamesense.api.setting.values.BooleanSetting;
import com.gamesense.api.setting.values.IntegerSetting;
import com.gamesense.client.module.Category;
import com.gamesense.client.module.Module;
import net.minecraft.init.Items;
import net.minecraft.item.ItemBlock;

/*
 * @author hausemasterissue, sxmurai
 * @since 9/29/2021
 */

@Module.Declaration(name = "FastUse", category = Category.Misc)
public class FastUse extends Module {
	
    IntegerSetting delay = registerInteger("Delay", 0, 0, 20);
    IntegerSetting speed = registerInteger("Speed", 0, 0, 4);
    BooleanSetting exp = registerBoolean("XP", true);
    BooleanSetting crystals = registerBoolean("Crystals", false);
    BooleanSetting fireworks = registerBoolean("Fireworks", false);

    private int ticks = 0;
	
    public void onDisable() {
	  mc.rightClickDelayTimer = 6;
    }
	
    public void onUpdate() {
    	if (delay.getValue() != 0) {
            ++ticks;
            if (ticks <= delay.getValue()) {
                return;
            }

            ticks = 0;
        }
	    
	    if (exp.getValue() && mc.player.getHeldItemMainhand().getItem() == Items.EXPERIENCE_BOTTLE || mc.player.getHeldItemOffhand().getItem() == Items.EXPERIENCE_BOTTLE) {
        	 mc.rightClickDelayTimer = this.speed.getValue();
            } else if (crystals.getValue() && mc.player.getHeldItemMainhand().getItem() == Items.END_CRYSTAL || crystals.getValue() && mc.player.getHeldItemOffhand().getItem() == Items.END_CRYSTAL) {
		 mc.rightClickDelayTimer = this.speed.getValue();
	    } else if (fireworks.getValue() && mc.player.getHeldItemMainhand().getItem() == Items.FIREWORKS || mc.player.getHeldItemOffhand().getItem() == Items.FIREWORKS) {
		 mc.rightClickDelayTimer = this.speed.getValue(); 
	    }

    }
}
