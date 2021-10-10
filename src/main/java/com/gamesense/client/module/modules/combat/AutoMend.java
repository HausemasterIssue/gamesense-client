package com.gamesense.client.module.modules.combat;

import com.gamesense.client.module.Module;
import com.gamesense.api.setting.values.IntegerSetting;
import com.gamesense.api.util.misc.MessageBus;
import com.gamesense.client.module.Category;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.ClickType;
import net.minecraft.item.ItemExpBottle;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.EnumHand;
import java.util.HashMap;
import java.util.Map;

/*
* @author hausemasterissue
* @since 9/10/2021
* creds to travis
*/


@Module.Declaration(name = "AutoMend", category = Category.Combat)
public class AutoMend extends Module {
	
	IntegerSetting delay = registerInteger("Delay", 16, 0, 24);
	IntegerSetting damage = registerInteger("HealAmount", 90, 0, 100);
	
	private int mostDamagedSlot;
    private int mostDamage;
    private int lastSlot;
    private int counter;
    private int armorCount;
    private int wait;

    private int[] slots;

    private boolean shouldThrow;
    private boolean shouldArmor;
    
    @Override
    public void onEnable() {
        mostDamage = -1;
        mostDamagedSlot = -1;
        shouldArmor = false;
        armorCount = 0;
        slots = new int[3];
        wait = 0;
        takeOffArmor();
    }
    
    @Override
    public void onUpdate() {
        if (mc.player == null || !isEnabled() || mc.currentScreen instanceof GuiContainer) {
            return;
        }
        if (shouldThrow) {
        	mc.player.connection.sendPacket(new CPacketPlayer.Rotation(0, 90, true));
            mc.player.inventory.currentItem = findXP();
            mc.playerController.processRightClick(mc.player, mc.world, EnumHand.MAIN_HAND);
            if (isRepaired() || counter > 40) {
                shouldThrow = false;
                shouldArmor = true;
                mc.player.inventory.currentItem = lastSlot;
                MessageBus.sendClientPrefixMessage("Finished repearing!");
            }
            counter++;
        }
        if (shouldArmor) {
            if (wait >= delay.getValue()) {
                wait = 0;
                mc.playerController.windowClick(0, slots[armorCount], 0, ClickType.QUICK_MOVE, (EntityPlayer)mc.player);
                mc.playerController.updateController();
                armorCount++;
                if (armorCount > 2) {
                    armorCount = 0;
                    shouldArmor = false;
                    disable();
                    return;
                }
            }
            wait++;
        }
    }
    
    public int getMostDamagedSlot() {
        for (final Map.Entry<Integer, ItemStack> armorSlot : getArmor().entrySet()) {
            final ItemStack stack = armorSlot.getValue();
            if (stack.getItemDamage() > mostDamage) {
                mostDamage = stack.getItemDamage();
                mostDamagedSlot = armorSlot.getKey();
            }
        }
        return mostDamagedSlot;
    }
    
    public boolean isRepaired() {
        for (final Map.Entry<Integer, ItemStack> armorSlot : getArmor().entrySet()) {
            final ItemStack stack = armorSlot.getValue();
            if (armorSlot.getKey() == mostDamagedSlot) {
                float percent = ( (float) damage.getValue() / (float) 100);
                int dam = Math.round(stack.getMaxDamage() * percent);
                int goods = stack.getMaxDamage() - stack.getItemDamage();
                if (dam <= goods) {
                    return true;
                } else {
                    return false;
                }
            }
        }
        return false;
    }
    
    public int findXP() {
        lastSlot = mc.player.inventory.currentItem;
        int slot = -1;
        for (int i = 0; i < 9; ++i) {
            ItemStack stack = mc.player.inventory.getStackInSlot(i);
            if (stack == ItemStack.EMPTY || !(stack.getItem() instanceof ItemExpBottle)) continue;
            slot = i;
            break;
        }
        if (slot == -1) {
        	MessageBus.sendClientPrefixMessage("You have no XP in your hotbar!");
            disable();
            return 1;
        }
        return slot;
    }
    
    public boolean isSpace() {
        int spareSlots = 0;
        for (final Map.Entry<Integer, ItemStack> invSlot : getInventory().entrySet()) {
            final ItemStack stack = invSlot.getValue();
            if (stack.getItem() == Items.AIR) {
                slots[spareSlots] = invSlot.getKey();
                spareSlots++;
                if (spareSlots > 2) {
                    return true;
                }
            }
        }
        return false;
    }
    
    public void takeOffArmor() {
        if (isSpace()) {
            getMostDamagedSlot();
            if (mostDamagedSlot != -1) {
                for (final Map.Entry<Integer, ItemStack> armorSlot : getArmor().entrySet()) {
                    if (armorSlot.getKey() != mostDamagedSlot) {
                        mc.playerController.windowClick(0, armorSlot.getKey(), 0, ClickType.QUICK_MOVE, (EntityPlayer)mc.player);
                    }
                }
                counter = 0;
                shouldThrow = true;
                return;
            }
        }
        MessageBus.sendClientPrefixMessage("You need at least 3 available inventory slots to use this module! Disabling...");
        disable();
        return;
    }
    
    private static Map<Integer, ItemStack> getInventory() {
        return getInventorySlots(9, 44);
    }

    private static Map<Integer, ItemStack> getArmor() {
        return getInventorySlots(5, 8);
    }
    
    private static Map<Integer, ItemStack> getInventorySlots(int current, final int last) {
        final Map<Integer, ItemStack> fullInventorySlots = new HashMap<Integer, ItemStack>();
        while (current <= last) {
            fullInventorySlots.put(current, (ItemStack)mc.player.inventoryContainer.getInventory().get(current));
            ++current;
        }
        return fullInventorySlots;
    }



}
