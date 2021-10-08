package com.gamesense.client.module.modules.combat;

import java.util.Objects;

import com.gamesense.api.setting.values.BooleanSetting;
import com.gamesense.api.util.misc.MessageBus;
import com.gamesense.client.module.Category;
import com.gamesense.client.module.Module;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.init.Items;
import net.minecraft.inventory.ClickType;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.network.play.client.CPacketPlayerTryUseItem;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;

/*
* @author hausemasterissue
* @since 8/10/2021
* creds to linustouchtips
*/

@Module.Declaration(name = "Quiver", category = Category.Combat)
public class Quiver extends Module {
	
	BooleanSetting speed = registerBoolean("Speed", true);
	BooleanSetting strength = registerBoolean("Strength", true);
	
	 int randomVariation;
	 
	 @Override
	    public void onUpdate() {
	        PotionEffect speedEffect = mc.player.getActivePotionEffect( Objects.requireNonNull ( Potion.getPotionById ( 1 ) ) );
	        PotionEffect strengthEffect = mc.player.getActivePotionEffect( Objects.requireNonNull ( Potion.getPotionById ( 5 ) ) );

	        boolean hasSpeed;
	        boolean hasStrength;

	        hasSpeed = speedEffect != null;

	        hasStrength = strengthEffect != null;

	        if (mc.player.inventory.currentItem == find_bow_hotbar())
	            mc.player.connection.sendPacket(new CPacketPlayer.Rotation(0, -90, true));

	        if (strength.getValue() && !hasStrength)
	            if (mc.player.inventory.getCurrentItem().getItem() == Items.BOW && isArrowInInventory("Arrow of Strength")) {
	                if (mc.player.getItemInUseMaxCount() >= getBowCharge()) {
	                        mc.player.connection.sendPacket((Packet<?>) new CPacketPlayerDigging(CPacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, mc.player.getHorizontalFacing()));
	                        mc.player.stopActiveHand();
	                        mc.player.connection.sendPacket((Packet<?>) new CPacketPlayerTryUseItem(EnumHand.MAIN_HAND));
	                        mc.player.setActiveHand(EnumHand.MAIN_HAND);
	                } else if (mc.player.getItemInUseMaxCount() == 0) {
	                        mc.player.connection.sendPacket(new CPacketPlayerTryUseItem(EnumHand.MAIN_HAND));
	                        mc.player.setActiveHand(EnumHand.MAIN_HAND);
	                }
	            }
	        if (speed.getValue() && !hasSpeed) {
	            if (mc.player.inventory.getCurrentItem().getItem() == Items.BOW && isArrowInInventory("Arrow of Speed")) {
	                if (mc.player.getItemInUseMaxCount() >= getBowCharge()) {
	                    mc.player.connection.sendPacket(new CPacketPlayerDigging(CPacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, mc.player.getHorizontalFacing()));
	                    mc.player.stopActiveHand();
	                    mc.player.connection.sendPacket(new CPacketPlayerTryUseItem(EnumHand.MAIN_HAND));
	                    mc.player.setActiveHand(EnumHand.MAIN_HAND);
	                } else if (mc.player.getItemInUseMaxCount() == 0) {
	                    mc.player.connection.sendPacket(new CPacketPlayerTryUseItem(EnumHand.MAIN_HAND));
	                    mc.player.setActiveHand(EnumHand.MAIN_HAND);
	                }
	            }
	        }
	    }
	 
	 	private int find_bow_hotbar() {
	        for (int i = 0; i < 9; i++) {
	            if (mc.player.inventory.getStackInSlot(i).getItem() == Items.END_CRYSTAL) {
	                return i;
	            }
	        }
	        return -1;
	    }
	 	
	 	private boolean isArrowInInventory(String type) {
	        boolean inInv = false;
	        for (int i = 0; i < 36; i++) {
	            ItemStack itemStack = mc.player.inventory.getStackInSlot(i);
	            if (itemStack.getItem() == Items.TIPPED_ARROW) {
	                if (itemStack.getDisplayName().equalsIgnoreCase(type)) {
	                    inInv = true;
	                    switchArrow(i);
	                    break;
	                }
	            }
	        }

	        return inInv;
	    }
	 	
	 	private void switchArrow(int oldSlot) {
	 		MessageBus.sendClientPrefixMessage("Switching Arrows");
	        int bowSlot = mc.player.inventory.currentItem;
	        int placeSlot = bowSlot +1;

	        if (placeSlot > 8)
	            placeSlot = 1;

	        if (placeSlot != oldSlot) {
	            if (mc.currentScreen instanceof GuiContainer)
	                return;

	            mc.playerController.windowClick(0, oldSlot, 0, ClickType.PICKUP, mc.player);
	            mc.playerController.windowClick(0, placeSlot, 0, ClickType.PICKUP, mc.player);
	            mc.playerController.windowClick(0, oldSlot, 0, ClickType.PICKUP, mc.player);
	        }
	    }
	 	
	 	private int getBowCharge() {
	        if (randomVariation == 0)
	            randomVariation = 1;

	        return 1 + randomVariation;
	    }


}
