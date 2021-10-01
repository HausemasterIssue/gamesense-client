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
 * @author hausemasterissue
 * @since 9/30/2021
 * HUGE HUGE HUGE thanks to aestheticall and inferno
 */

@Module.Declaration(name = "NoFall", category = Category.Movement)
public class NoFall extends Module {
	
	ModeSetting mode = registerMode("Mode", Arrays.asList("Packet", "Prevent", "WaterBucket", "Web"), "Prevent");
	DoubleSetting distance = registerDouble("Distance", 6.0, 1.0, 255.0);
	
	private int oldSlot = -1;
    private EnumHand hand;
    private float oldPitch = -1.0f;
    private boolean packetLook = true;
    
    @Override
    public void onDisable() {
        oldSlot = -1;
        hand = null;
        oldPitch = -1.0f;
    }
    
    @SubscribeEvent
    public void onUpdate() {
        if (mc.player.fallDistance > this.distance.getValue()) {
            if (mode.getValue() == "Packet") {
                mc.player.connection.sendPacket(new CPacketPlayer(true));
            } else if (mode.getValue() == "WaterBucket" || mode.getValue() == "Web") {
                int slot = mode.getValue() == "WaterBucket" ?
                        InventoryUtil.getHotbarItemSlot(Items.WATER_BUCKET, true) :
                        InventoryUtil.getHotbarBlockSlot(BlockWeb.class, true);

                if (slot == -1) {
                    return;
                }

                if (slot == 45) {
                    hand = EnumHand.OFF_HAND;
                } else {
                    hand = EnumHand.MAIN_HAND;
                    oldSlot = mc.player.inventory.currentItem;

                    InventoryUtil.switchTo(slot, false);

                    dreamClutch();
                } } else if (mode.getValue() == "Prevent") {
                    mc.player.fallDistance = 0;
                    mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX +420420, mc.player.posY, mc.player.posZ, false));
                    mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY + 1, mc.player.posZ, true));
                
            }
        }

        if (mc.player.onGround && (mode.getValue() == "WaterBucket" || mode.getValue() == "Web")) {
            if (oldPitch != -1.0f) {
                mc.player.rotationPitch = oldPitch;
                oldPitch = -1.0f;
            }

            if (oldSlot != -1) {
                InventoryUtil.switchTo(oldSlot, false);
                oldSlot = -1;
                hand = null;
            }
        }
    }
    
    private void dreamClutch() {
        if (oldPitch == -1.0f) {
            oldPitch = mc.player.rotationPitch;
        }

        if (packetLook == true) {
            mc.player.connection.sendPacket(new CPacketPlayer.Rotation(mc.player.rotationYaw, 90.0f, false));
        } else {
            mc.player.rotationPitch = 90.0f;
        }

        mc.playerController.processRightClick(mc.player, mc.world, this.hand);
    }
	public String getHudInfo() {
        String t = "";
        if (mode.getValue().equalsIgnoreCase("Packet")){
            t = "[" + ChatFormatting.WHITE + mode.getValue() + ChatFormatting.GRAY + "]";
        } else if (mode.getValue().equalsIgnoreCase("Prevent")) {
        	t = "[" + ChatFormatting.WHITE + mode.getValue() + ChatFormatting.GRAY + "]";
        } else if (mode.getValue().equalsIgnoreCase("WaterBucket")) {
        	t = "[" + ChatFormatting.WHITE + mode.getValue() + ChatFormatting.GRAY + "]";
        } else if (mode.getValue().equalsIgnoreCase("Web")) {
        	t = "[" + ChatFormatting.WHITE + mode.getValue() + ChatFormatting.GRAY + "]";
        }

        return t;
    }

}
