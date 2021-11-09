package com.gamesense.client.module.modules.combat;

import com.gamesense.client.module.Category;
import com.gamesense.client.module.Module;
import com.gamesense.api.setting.values.BooleanSetting;
import com.gamesense.api.setting.values.IntegerSetting;
import com.gamesense.api.setting.values.ModeSetting;
import com.gamesense.api.util.player.InventoryUtil;
import com.gamesense.api.util.world.MotionUtil;
import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Items;
import net.minecraft.inventory.ClickType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import java.util.Arrays;
import org.lwjgl.input.Mouse;

/*
* @author hausemasterissue
* @since 9/11/2021
*/

@Module.Declaration(name = "AutoTotem", category = Category.Combat)
public class AutoTotem extends Module {

	ModeSetting item = registerMode("Item", Arrays.asList("Totem", "Crystal", "Gapple"), "Totem");
	ModeSetting fallBack = registerMode("Fallback", Arrays.asList("Totem", "Crystal", "Gapple"), "Totem");
	IntegerSetting health = registerInteger("HealthSwap", 14, 0, 36);
    IntegerSetting fallDistance = registerInteger("FallDistance", 30, 0, 280);
    BooleanSetting forceGapple = registerBoolean("ForceGapple", true);
    BooleanSetting motionStrict = registerBoolean("MotionStrict", false);
    IntegerSetting delay = registerInteger("Delay", 0, 0, 200);

    private int totems;
    private int totemsOffHand;
    private int totemSwtichDelay = 0;
    private int totalTotems;
    private Item itemFallback = null;
    private Item itemDefault = null;
    private Item itemTotem = Items.TOTEM_OF_UNDYING;


    @Override
    public void onUpdate() {
        if (mc.world == null) {
        	return;
        }
        
        if(item.getValue().equalsIgnoreCase("Totem")) {
        	itemDefault = Items.TOTEM_OF_UNDYING;
        } else if (item.getValue().equalsIgnoreCase("Crystal")) {
        	itemDefault = Items.END_CRYSTAL;
        } else if (item.getValue().equalsIgnoreCase("Gapple")) {
        	itemDefault = Items.GOLDEN_APPLE;
        }
            
        if(fallBack.getValue().equalsIgnoreCase("Totem")) {
        	itemFallback = Items.TOTEM_OF_UNDYING;
        } else if (fallBack.getValue().equalsIgnoreCase("Crystal")) {
        	itemFallback = Items.END_CRYSTAL;
        } else if (fallBack.getValue().equalsIgnoreCase("Gapple")) {
        	itemFallback = Items.GOLDEN_APPLE;
        }
        
        totems = mc.player.inventory.mainInventory.stream().filter(itemStack -> itemStack.getItem() == Items.TOTEM_OF_UNDYING).mapToInt(ItemStack::getCount).sum();
        totemsOffHand = mc.player.inventory.offHandInventory.stream().filter(itemStack -> itemStack.getItem() == Items.TOTEM_OF_UNDYING).mapToInt(ItemStack::getCount).sum();
        totalTotems = totems + totemsOffHand;
        
        
        if(getHealth(mc.player) <= health.getValue() || mc.player.fallDistance >= fallDistance.getValue()) {
        	swap(itemFallback, motionStrict.getValue());
        	return;
        }
        
        if(InventoryUtil.isHolding(Items.DIAMOND_SWORD) && forceGapple.getValue() && Mouse.isButtonDown(1)) {
        	swap(Items.GOLDEN_APPLE, motionStrict.getValue());
        	return;
        }
        
        swap(itemDefault, motionStrict.getValue());
        return;

    }
    
    public void swap(Item item, boolean stopMotion) {
    	if(mc.player.getHeldItemOffhand().getItem() == item) {
    		return;
    	}
    	
    	for (int i = 0; i < 45; i++) {
                if (mc.currentScreen instanceof GuiInventory || mc.currentScreen == null) {
                    ItemStack stacks = mc.player.openContainer.getSlot(i).getStack();

                if (stacks == ItemStack.EMPTY)
                    continue;
                if (mc.player.getHeldItemOffhand().getItem() != item) {
                    totemSwtichDelay++;
                        if (stacks.getItem() == item) {
                            if (totemSwtichDelay >= delay.getValue()) {
                            	if(motionStrict.getValue() && MotionUtil.isMovingPlayer()) {
                            		mc.player.motionX = 0;
                            		mc.player.motionY = 0;
                            		mc.player.motionZ = 0;
                            		mc.player.setVelocity(0, 0, 0);
                            	}
                                mc.playerController.windowClick(0, i, 1, ClickType.PICKUP, mc.player);
                                mc.playerController.windowClick(0, 45, 1, ClickType.PICKUP, mc.player);
                                totemSwtichDelay = 0;
                            }
                        }
                    }

             }
        }
    }
    

    @Override
    public void onEnable() {
        totemSwtichDelay = 0;
    }
    
    public static float getHealth(Entity entity) {
        if (!(entity instanceof EntityLivingBase)) {
            return -1.0f;
        }

        EntityLivingBase entityLivingBase = (EntityLivingBase) entity;
        return entityLivingBase.getHealth() + entityLivingBase.getAbsorptionAmount();
    }

    public String getHudInfo() {
        return "[" + ChatFormatting.WHITE + totalTotems + ChatFormatting.GRAY + "]";
    }
}
