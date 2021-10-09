package com.gamesense.client.module.modules.misc;

import com.gamesense.api.setting.values.BooleanSetting;
import com.gamesense.api.setting.values.IntegerSetting;
import com.gamesense.client.module.Category;
import com.gamesense.client.module.Module;
import net.minecraft.init.Items;
import net.minecraft.item.Item;

/*
 * @author hausemasterissue
 * @since 9/29/2021
 * creds to inferno lol
 */
@Module.Declaration(name = "FastUse", category = Category.Misc)
public class FastUse extends Module {
	IntegerSetting delay = registerInteger("Delay", 0, 0, 20);
    IntegerSetting speed = registerInteger("Speed", 0, 0, 4);
    BooleanSetting everything = registerBoolean("Everything", false);
    BooleanSetting exp = registerBoolean("XP", true);
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

    	if (!this.everything.getValue()) {
            if (this.isHolding(Items.EXPERIENCE_BOTTLE) && this.exp.getValue()) {
                mc.rightClickDelayTimer = this.speed.getValue();
            }

            if (this.isHolding(Items.END_CRYSTAL) && this.crystals.getValue()) {
                mc.rightClickDelayTimer = this.speed.getValue();
            }

            if (this.isHolding(Items.FIREWORKS) && this.fireworks.getValue()) {
                mc.rightClickDelayTimer = this.speed.getValue();
            }
        } else {
            mc.rightClickDelayTimer = this.speed.getValue();
        }
    }

    private boolean isHolding(Item item) {
        return mc.player.getActiveItemStack().getItem() == item;
    }
}
