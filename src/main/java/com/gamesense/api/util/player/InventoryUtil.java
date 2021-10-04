package com.gamesense.api.util.player;

import com.gamesense.client.module.modules.combat.OffHand;
import net.minecraft.block.Block;
import net.minecraft.block.BlockObsidian;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemSkull;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.CPacketHeldItemChange;

import java.util.ArrayList;
import java.util.List;

public class InventoryUtil {

    private static final Minecraft mc = Minecraft.getMinecraft();

    public static int findObsidianSlot(boolean offHandActived, boolean activeBefore) {
        int slot = -1;
        List<ItemStack> mainInventory = mc.player.inventory.mainInventory;

        if (offHandActived && OffHand.isActive()) {
            if (!activeBefore) {
                OffHand.requestObsidian();
            }
            return 9;
        }

        for (int i = 0; i < 9; i++) {
            ItemStack stack = mainInventory.get(i);

            if (stack == ItemStack.EMPTY || !(stack.getItem() instanceof ItemBlock)) {
                continue;
            }

            Block block = ((ItemBlock) stack.getItem()).getBlock();
            if (block instanceof BlockObsidian) {
                slot = i;
                break;
            }
        }
        return slot;
    }

    public static int findSkullSlot(boolean offHandActived, boolean activeBefore) {
        int slot = -1;
        List<ItemStack> mainInventory = mc.player.inventory.mainInventory;

        if (offHandActived) {
            if (!activeBefore)
                OffHand.requestSkull();
            return 9;
        }

        for (int i = 0; i < 9; i++) {
            ItemStack stack = mainInventory.get(i);

            if (stack != ItemStack.EMPTY && stack.getItem() instanceof ItemSkull)
                return i;
        }
        return slot;
    }

    public static int findTotemSlot(int lower, int upper) {
        int slot = -1;
        List<ItemStack> mainInventory = mc.player.inventory.mainInventory;
        for (int i = lower; i <= upper; i++) {
            ItemStack stack = mainInventory.get(i);
            if (stack == ItemStack.EMPTY || stack.getItem() != Items.TOTEM_OF_UNDYING)
                continue;

            slot = i;
            break;
        }
        return slot;
    }

    public static int findFirstItemSlot(Class<? extends Item> itemToFind, int lower, int upper) {
        int slot = -1;
        List<ItemStack> mainInventory = mc.player.inventory.mainInventory;

        for (int i = lower; i <= upper; i++) {
            ItemStack stack = mainInventory.get(i);

            if (stack == ItemStack.EMPTY || !(itemToFind.isInstance(stack.getItem()))) {
                continue;
            }

            if (itemToFind.isInstance(stack.getItem())) {
                slot = i;
                break;
            }
        }
        return slot;
    }

    public static int findFirstBlockSlot(Class<? extends Block> blockToFind, int lower, int upper) {
        int slot = -1;
        List<ItemStack> mainInventory = mc.player.inventory.mainInventory;

        for (int i = lower; i <= upper; i++) {
            ItemStack stack = mainInventory.get(i);

            if (stack == ItemStack.EMPTY || !(stack.getItem() instanceof ItemBlock)) {
                continue;
            }

            if (blockToFind.isInstance(((ItemBlock) stack.getItem()).getBlock())) {
                slot = i;
                break;
            }
        }
        return slot;
    }

    public static List<Integer> findAllItemSlots(Class<? extends Item> itemToFind) {
        List<Integer> slots = new ArrayList<>();
        List<ItemStack> mainInventory = mc.player.inventory.mainInventory;

        for (int i = 0; i < 36; i++) {
            ItemStack stack = mainInventory.get(i);

            if (stack == ItemStack.EMPTY || !(itemToFind.isInstance(stack.getItem()))) {
                continue;
            }

            slots.add(i);
        }
        return slots;
    }

    public static List<Integer> findAllBlockSlots(Class<? extends Block> blockToFind) {
        List<Integer> slots = new ArrayList<>();
        List<ItemStack> mainInventory = mc.player.inventory.mainInventory;

        for (int i = 0; i < 36; i++) {
            ItemStack stack = mainInventory.get(i);

            if (stack == ItemStack.EMPTY || !(stack.getItem() instanceof ItemBlock)) {
                continue;
            }

            if (blockToFind.isInstance(((ItemBlock) stack.getItem()).getBlock())) {
                slots.add(i);
            }
        }
        return slots;
    }
    
     public static int getHotbarItemSlot(Item item, boolean offhand) {
        if (offhand && mc.player.getHeldItemOffhand().item == item) {
            return 45;
        }

        for (int i = 0; i < 9; ++i) {
            ItemStack stack = mc.player.inventory.getStackInSlot(i);
            if (!stack.isEmpty && stack.item == item) {
                return i;
            }
        }

        return -1;
    }
    
    public static int getHotbarBlockSlot(Block block, boolean offhand) {
        if (offhand && mc.player.getHeldItemOffhand().item instanceof ItemBlock && ((ItemBlock) mc.player.getHeldItemOffhand().item).getBlock() == block) {
            return 45;
        }

        for (int i = 0; i < 9; ++i) {
            ItemStack stack = mc.player.inventory.getStackInSlot(i);
            if (!stack.isEmpty && stack.item instanceof ItemBlock && ((ItemBlock) stack.item).getBlock() == block) {
                return i;
            }
        }

        return -1;
    }
    
     public static int getHotbarBlockSlot(Class<? extends Block> block, boolean offhand) {
        if (offhand && mc.player.getHeldItemOffhand().item instanceof ItemBlock && block.isInstance(((ItemBlock) mc.player.getHeldItemOffhand().item).getBlock())) {
            return 45;
        }

        for (int i = 0; i < 9; ++i) {
            ItemStack stack = mc.player.inventory.getStackInSlot(i);
            if (!stack.isEmpty && stack.item instanceof ItemBlock && block.isInstance(((ItemBlock) stack.item).getBlock())) {
                return i;
            }
        }

        return -1;
    }
    
    public static void switchTo(int slot, boolean silent) {
        if (silent) {
            mc.player.connection.sendPacket(new CPacketHeldItemChange(slot));
        } else {
            mc.player.inventory.currentItem = slot;
        }

        mc.playerController.updateController();
    }
    
    public static boolean isHolding(Item item) {
        return mc.player.getHeldItemMainhand().getItem().equals(item) || mc.player.getHeldItemOffhand().getItem().equals(item);
    }
}
