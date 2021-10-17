package com.gamesense.client.module.modules.combat;

import com.gamesense.api.setting.values.BooleanSetting;
import com.gamesense.api.setting.values.DoubleSetting;
import com.gamesense.api.setting.values.ModeSetting;
import com.gamesense.api.util.player.InventoryUtil;
import com.gamesense.api.util.player.PlacementUtil;
import com.gamesense.client.module.Category;
import com.gamesense.client.module.Module;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;

import java.util.Arrays;

/*
* @author Sxmurai
* @since 11/10/2021
* previously "made" by Hause, but the code made me wanna commit not live so
* taken from my client, Inferno. (instant burrow mode)
* origin of code: https://github.com/ciruu1/InstantBurrow
*/
@Module.Declaration(name = "SelfFill", category = Category.Combat)
public class SelfFill extends Module {
    DoubleSetting rubberband = registerDouble("Rubberband", 3.0, -5.0, 5.0);
    BooleanSetting rotate = registerBoolean("Rotate", true);
    BooleanSetting swing = registerBoolean("Swing", true);
    ModeSetting block = registerMode("Block", Arrays.asList("Obsidian", "EChest", "EndRod"), "Obsidian");
    BooleanSetting silent = registerBoolean("SilentSwitch", false);

    private BlockPos origin = null;
    private EnumHand hand = EnumHand.MAIN_HAND;
    private int oldSlot = -1;

    @Override
    protected void onEnable() {
        if (mc.world == null || mc.player == null) {
            this.toggle();
            return;
        }

        this.origin = new BlockPos(mc.player.posX, mc.player.posY, mc.player.posZ);
        if (!mc.world.isAirBlock(this.origin.add(0.0, 1.0, 0.0)) || !mc.world.isAirBlock(this.origin.add(0.0, 2.0, 0.0))) {
            this.toggle();
            return;
        }

        Block block = this.block.getValue().equals("Obsidian") ? Blocks.OBSIDIAN : this.block.getValue().equals("EChest") ? Blocks.ENDER_CHEST : Blocks.END_ROD;
        int slot = InventoryUtil.getHotbarBlockSlot(block, true);

        if (slot == -1) {
            this.toggle();
            return;
        }

        this.hand = slot == 45 ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND;
        if (this.hand == EnumHand.MAIN_HAND) {
            this.oldSlot = mc.player.inventory.currentItem;
            InventoryUtil.switchTo(slot, this.silent.getValue());
        }
    }

    @Override
    protected void onDisable() {
        this.origin = null;
        this.hand = EnumHand.MAIN_HAND;

        if (mc.world != null && mc.player != null && this.oldSlot != -1) {
            InventoryUtil.switchTo(this.oldSlot, this.silent.getValue());
        }

        this.oldSlot = -1;
    }

    @Override
    public void onUpdate() {
        if (mc.world == null || mc.player == null) {
            return;
        }

        if (this.origin != null) {
            mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY + 0.41999998688698, mc.player.posZ, true));
            mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY + 0.7531999805211997, mc.player.posZ, true));
            mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY + 1.00133597911214, mc.player.posZ, true));
            mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY + 1.16610926093821, mc.player.posZ, true));

            PlacementUtil.place(this.origin, this.hand, this.rotate.getValue(), this.swing.getValue(), true);

            mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY + this.rubberband.getValue(), mc.player.posZ, true));

            this.toggle();
        }
    }
}