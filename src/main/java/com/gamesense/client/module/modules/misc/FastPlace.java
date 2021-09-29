package com.gamesense.client.module.modules.misc;

import com.gamesense.api.setting.values.BooleanSetting;
import com.gamesense.api.setting.values.IntegerSetting;
import com.gamesense.client.module.Category;
import com.gamesense.client.module.Module;
import net.minecraft.init.Items;

/*
 * @author hausemasterissue
 * @since 9/29/2021
 * creds to inferno lol
 */

@Module.Declaration(name = "FastUse", category = Category.Misc)
public class FastPlace extends Module {

	IntegerSetting delay = registerInteger("Delay", 0, 0, 20);
    IntegerSetting speed = registerInteger("Speed", 0, 0, 4);
    BooleanSetting exp = registerBoolean("XP", false);
    BooleanSetting crystals = registerBoolean("Crystals", false);
    BooleanSetting fireworks = registerBoolean("Fireworks", false);
    
    private int ticks = 0;

    public void onUpdate() {
    	if (this.delay.getValue() != 0) {
            ++this.ticks;
            if (this.ticks <= this.delay.getValue()) {
                return;
            }

            this.ticks = 0;
        }
    	
        if (exp.getValue() && mc.player.getHeldItemMainhand().getItem() == Items.EXPERIENCE_BOTTLE || mc.player.getHeldItemOffhand().getItem() == Items.EXPERIENCE_BOTTLE) {
        	 mc.rightClickDelayTimer = this.speed.getValue();
        }

        if (crystals.getValue() && mc.player.getHeldItemMainhand().getItem() == Items.END_CRYSTAL || crystals.getValue() && mc.player.getHeldItemOffhand().getItem() == Items.END_CRYSTAL) {
        	 mc.rightClickDelayTimer = this.speed.getValue();
        }
        
        if (exp.getValue() && mc.player.getHeldItemMainhand().getItem() == Items.FIREWORKS|| mc.player.getHeldItemOffhand().getItem() == Items.FIREWORKS) {
       	 mc.rightClickDelayTimer = this.speed.getValue();
       }


    }
}
