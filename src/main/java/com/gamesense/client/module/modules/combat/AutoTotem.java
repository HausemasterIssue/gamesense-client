package com.gamesense.client.module.modules.combat;

import com.gamesense.client.module.Category;
import com.gamesense.client.module.Module;
import com.gamesense.api.setting.values.IntegerSetting;
import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.init.Items;
import net.minecraft.inventory.ClickType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

/*
* @author hausemasterissue
* @since 9/29/2021
* creds to cousinware
* disclaimer, meant for 2b2t
*/

@Module.Declaration(name = "AutoTotem", category = Category.Combat)
public class AutoTotem extends Module {
    
    IntegerSetting delay = registerInteger("Delay", 0, 0, 200);
    
    int totems;
    int totemsOffHand;
    int totemSwtichDelay = 0;
    int totalTotems;


    @Override
    public void onUpdate() {
        if (mc.world == null)
            return;
        totems = mc.player.inventory.mainInventory.stream().filter(itemStack -> itemStack.getItem() == Items.TOTEM_OF_UNDYING).mapToInt(ItemStack::getCount).sum();
        totemsOffHand = mc.player.inventory.offHandInventory.stream().filter(itemStack -> itemStack.getItem() == Items.TOTEM_OF_UNDYING).mapToInt(ItemStack::getCount).sum();
        totalTotems = totems + totemsOffHand;

        for (int i = 0; i < 45; i++) {
            if (totems + totemsOffHand > 0) {
                if (mc.currentScreen instanceof GuiInventory || mc.currentScreen == null) {
                    ItemStack stacks = mc.player.openContainer.getSlot(i).getStack();

                if (stacks == ItemStack.EMPTY)
                    continue;
                Item itemTotem = Items.TOTEM_OF_UNDYING;
                if (mc.player.getHeldItemOffhand().isEmpty()) {
                    totemSwtichDelay++;
                        if (stacks.getItem() == itemTotem) {
                            if (totemSwtichDelay >= delay.getValue()) {
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
