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
import net.minecraft.init.Items;
import net.minecraft.inventory.ClickType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import java.util.Arrays;
import org.lwjgl.input.Mouse;

/*
* @author hausemasterissue
* @since 9/11/2021
* 
* horrible, horrible attempt at autototem, i have the right idea, i just need to execute it properly. can you clean it up please lol?
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
    private boolean gappling = false;
    private boolean handFallback = false;
	

    @Override
    public void onUpdate() {
        if (mc.world == null || mc.player == null) {
        	return;
        }
            
        totems = mc.player.inventory.mainInventory.stream().filter(itemStack -> itemStack.getItem() == Items.TOTEM_OF_UNDYING).mapToInt(ItemStack::getCount).sum();
        totemsOffHand = mc.player.inventory.offHandInventory.stream().filter(itemStack -> itemStack.getItem() == Items.TOTEM_OF_UNDYING).mapToInt(ItemStack::getCount).sum();
        totalTotems = totems + totemsOffHand;

        for (int i = 0; i < 45; i++) {
        	ItemStack stacks = mc.player.openContainer.getSlot(i).getStack();
            if (totems + totemsOffHand > 0) {
                if (mc.currentScreen instanceof GuiInventory || mc.currentScreen == null) {

                if (stacks == ItemStack.EMPTY) {
                	continue;
                }
                    
                if(fallBack.getValue().equalsIgnoreCase("Totem")) {
                	itemFallback = Items.TOTEM_OF_UNDYING;
                } else if (fallBack.getValue().equalsIgnoreCase("Gapple")) {
                	itemFallback = Items.GOLDEN_APPLE;
                } else if (fallBack.getValue().equalsIgnoreCase("Crystal")) {
                	itemFallback = Items.END_CRYSTAL;
                }
                
                Item itemGap = Items.GOLDEN_APPLE;
                
                if(item.getValue().equalsIgnoreCase("Totem")) {
                	itemDefault = Items.TOTEM_OF_UNDYING;
                } else if (item.getValue().equalsIgnoreCase("Gapple")) {
                	itemDefault = Items.GOLDEN_APPLE;
                } else if (item.getValue().equalsIgnoreCase("Crystal")) {
                	itemDefault = Items.END_CRYSTAL;
                }
                
                if (mc.player.fallDistance >= fallDistance.getValue() && !mc.player.isElytraFlying() && mc.player.getHeldItemOffhand().getItem() != itemTotem || mc.player.getHealth() <= health.getValue() && mc.player.getHeldItemOffhand().getItem() != itemTotem) {
                    totemSwtichDelay++;
                        if (stacks.getItem() == itemFallback) {
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
                    } else if (InventoryUtil.isHolding(Items.DIAMOND_SWORD) && forceGapple.getValue() && Mouse.isButtonDown(1)) {
                    	totemSwtichDelay++;
                        if (stacks.getItem() == itemGap) {
                            if (totemSwtichDelay >= delay.getValue()) {
				gappling = true;
                            	if(motionStrict.getValue() && MotionUtil.isMovingPlayer()) {
                            		mc.player.motionX = 0;
                            		mc.player.motionY = 0;
                            		mc.player.motionZ = 0;
                            		mc.player.setVelocity(0, 0, 0);
                            	}
                                mc.playerController.windowClick(0, i, 1, ClickType.PICKUP, mc.player);
                                mc.playerController.windowClick(0, 45, 1, ClickType.PICKUP, mc.player);
                                totemSwtichDelay = 0;
				gappling = false;
                            }
                        }
                    } else if (mc.player.getHeldItemOffhand().getItem() != itemTotem && mc.player.getHealth() <= health.getValue() && gappling == false) {
                    	totemSwtichDelay++;
                        if (stacks.getItem() == itemDefault) {
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
        }
    

    @Override
    public void onEnable() {
        totemSwtichDelay = 0;
    }

    public String getHudInfo() {
        return "[" + ChatFormatting.WHITE + totalTotems + ChatFormatting.GRAY + "]";
    }
}
