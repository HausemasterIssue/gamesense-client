package com.gamesense.client.module.modules.misc;

import com.gamesense.api.setting.values.IntegerSetting;
import com.gamesense.api.util.player.PlayerUtil;
import com.gamesense.client.module.Category;
import com.gamesense.client.module.Module;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.init.Items;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;

/*
* @author hausemasterissue
* @since 8/10/2021
* creds to salhack (spidermod B)) for the code
*/

@Module.Declaration(name = "AutoEat", category = Category.Misc)
public class AutoEat extends Module {
	
	IntegerSetting health = registerInteger("Health", 16, 1, 36);
	IntegerSetting hunger = registerInteger("Hunger", 8, 1, 36);
	
	private boolean m_WasEating = false;
	
	@Override
    protected void onDisable()
    {
        if (m_WasEating)
        {
            m_WasEating = false;
            KeyBinding.setKeyBindState(mc.gameSettings.keyBindUseItem.getKeyCode(), false);
        }
    }
	
	 @Override
	    public void onUpdate() {
	        float l_Health = mc.player.getHealth() + mc.player.getAbsorptionAmount();

	        if (health.getValue() >= l_Health && PlayerUtil.IsEating())
	        {
	            if (mc.player.getHeldItemMainhand().getItem() != Items.GOLDEN_APPLE)
	            {
	                for (int l_I = 0; l_I < 9; ++l_I)
	                {
	                    if (mc.player.inventory.getStackInSlot(l_I).isEmpty() || mc.player.inventory.getStackInSlot(l_I).getItem() != Items.GOLDEN_APPLE)
	                        continue;

	                    mc.player.inventory.currentItem = l_I;
	                    mc.playerController.updateController();
	                    break;
	                }

	                if (mc.currentScreen != null)
	                	KeyBinding.setKeyBindState(mc.gameSettings.keyBindUseItem.getKeyCode(), true);
	                else
	                    mc.playerController.processRightClick(mc.player, mc.world, EnumHand.MAIN_HAND);

	                m_WasEating = true;
	            }
	            return;
	        }

	        if (PlayerUtil.IsEating() && hunger.getValue() >= mc.player.getFoodStats().getFoodLevel())
	        {
	            boolean l_CanEat = false;

	            for (int l_I = 0; l_I < 9; ++l_I)
	            {
	                ItemStack l_Stack = mc.player.inventory.getStackInSlot(l_I);

	                if (mc.player.inventory.getStackInSlot(l_I).isEmpty())
	                    continue;

	                if (l_Stack.getItem() instanceof ItemFood)
	                {
	                    l_CanEat = true;
	                    mc.player.inventory.currentItem = l_I;
	                    mc.playerController.updateController();
	                    break;
	                }
	            }

	            if (l_CanEat)
	            {
	                if (mc.currentScreen != null)
	                	KeyBinding.setKeyBindState(mc.gameSettings.keyBindUseItem.getKeyCode(), true);
	                else
	                    mc.playerController.processRightClick(mc.player, mc.world, EnumHand.MAIN_HAND);

	                m_WasEating = true;
	            }
	        }

	        if (m_WasEating)
	        {
	            m_WasEating = false;
	            KeyBinding.setKeyBindState(mc.gameSettings.keyBindUseItem.getKeyCode(), false);
	        }
	    }

}
