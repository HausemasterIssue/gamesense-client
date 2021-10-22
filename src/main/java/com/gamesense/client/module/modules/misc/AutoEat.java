package com.gamesense.client.module.modules.misc;

import com.gamesense.api.setting.values.IntegerSetting;
import com.gamesense.client.module.Category;
import com.gamesense.client.module.Module;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.FoodStats;

/*
* @author hausemasterissue
* @since 22/10/2021
* creds to catalyst src leak
*/

@Module.Declaration(name = "AutoEat", category = Category.Misc)
public class AutoEat extends Module {
	
	IntegerSetting health = registerInteger("Health", 16, 1, 36);
	IntegerSetting hunger = registerInteger("Hunger", 8, 1, 36);
	
    public boolean eating;
    public int lastSlot;
    
    public void onUpdate() {
    	if(mc.player == null || mc.player == null) {
    		return;
    	}
    	
    	if (eating && !mc.player.isHandActive()) {
            if (lastSlot != -1) {
                mc.player.inventory.currentItem = lastSlot;
                lastSlot = -1;
            }
            eating = false;
            KeyBinding.setKeyBindState(mc.gameSettings.keyBindUseItem.getKeyCode(), false);
            return;
        }
        if (eating) {
            return;
        }
        final FoodStats foodStats = mc.player.getFoodStats();
        if (isValid(mc.player.getHeldItemOffhand(), foodStats.getFoodLevel())) {
            mc.player.setActiveHand(EnumHand.OFF_HAND);
            eating = true;
            KeyBinding.setKeyBindState(mc.gameSettings.keyBindUseItem.getKeyCode(), true);
            mc.rightClickMouse();
        }
        else {
            for (int i = 0; i < 9; ++i) {
                if (isValid(mc.player.inventory.getStackInSlot(i), foodStats.getFoodLevel())) {
                    lastSlot = mc.player.inventory.currentItem;
                    mc.player.inventory.currentItem = i;
                    eating = true;
                    KeyBinding.setKeyBindState(mc.gameSettings.keyBindUseItem.getKeyCode(), true);
                    mc.rightClickMouse();
                    return;
                }
            }
        }
    }
    
    public boolean isValid(final ItemStack itemStack, final int n) {
        return mc.player.getHealth() <= health.getValue() || (itemStack.getItem() instanceof ItemFood && 20 - n >= ((ItemFood)itemStack.getItem()).getHealAmount(itemStack));
    }
	
	

}
