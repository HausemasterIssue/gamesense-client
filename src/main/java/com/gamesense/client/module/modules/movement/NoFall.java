package com.gamesense.client.module.modules.movement;

import java.util.Arrays;

import com.gamesense.api.setting.values.DoubleSetting;
import com.gamesense.api.setting.values.ModeSetting;
import com.gamesense.api.util.player.InventoryUtil;
import com.gamesense.client.module.Category;
import com.gamesense.client.module.Module;
import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.block.BlockWeb;
import net.minecraft.init.Items;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.EnumHand;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/*
 * @author hausemasterissue, sxmurai
 * @since 9/30/2021
 */

@Module.Declaration(name = "NoFall", category = Category.Movement)
public class NoFall extends Module {
	
	ModeSetting mode = registerMode("Mode", Arrays.asList("Packet", "Prevent", "WaterBucket", "Web"), "Prevent");
	DoubleSetting distance = registerDouble("Distance", 6.0, 1.0, 255.0);
	
	private int oldSlot = -1;
    private EnumHand hand;
    private float oldPitch = -1.0f;

    @Override
    public void onDisable() {
        oldSlot = -1;
        hand = null;
        oldPitch = -1.0f;
    }
    
    @SubscribeEvent
    public void onUpdate() {
        if (mc.player.fallDistance > this.distance.getValue()) {
            switch (mode.getValue()) {
                case "Packet": {
                    mc.player.connection.sendPacket(new CPacketPlayer(true));
                    break;
                }

                case "WaterBucket":
                case "Web": {
                    int slot = mode.getValue().equals("WaterBucket") ?
                            InventoryUtil.getHotbarItemSlot(Items.WATER_BUCKET, true) :
                            InventoryUtil.getHotbarBlockSlot(BlockWeb.class, true);

                    if (slot == -1) {
                        return;
                    }

                    if (slot == 45) {
                        this.hand = EnumHand.OFF_HAND;
                    } else {
                        this.hand = EnumHand.MAIN_HAND;
                        this.oldSlot = mc.player.inventory.currentItem;
                        InventoryUtil.switchTo(slot, false);
                    }

                    this.dreamClutch();
                    break;
                }

                case "Prevent": {
                    mc.player.fallDistance = 0;
                    mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX + 420420, mc.player.posY, mc.player.posZ, false));
                    mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY + 1, mc.player.posZ, true));
                    break;
                }
            }
        }

        if (mc.player.onGround && (mode.getValue().equals("WaterBucket") || mode.getValue().equals("Web"))) {
            if (this.oldPitch != -1.0f) {
                mc.player.connection.sendPacket(new CPacketPlayer.Rotation(mc.player.rotationYaw, this.oldPitch, false));
                this.oldPitch = -1.0f;
            }

            if (this.oldSlot != -1) {
                InventoryUtil.switchTo(this.oldSlot, false);
                this.oldSlot = -1;
                this.hand = null;
            }
        }
    }
    
    private void dreamClutch() {
        if (oldPitch == -1.0f) {
            oldPitch = mc.player.rotationPitch;
        }

        mc.player.connection.sendPacket(new CPacketPlayer.Rotation(mc.player.rotationYaw, 90.0f, false));
        mc.playerController.processRightClick(mc.player, mc.world, this.hand);
    }

	public String getHudInfo() {
        return "[" + ChatFormatting.WHITE + mode.getValue() + ChatFormatting.GRAY + "]";
    }
}
