package com.gamesense.client.module.modules.combat;


import java.util.List;
import java.util.Objects;
import com.gamesense.api.setting.values.BooleanSetting;
import com.gamesense.api.setting.values.IntegerSetting;
import com.gamesense.api.util.player.InventoryUtil;
import com.gamesense.client.module.Category;
import com.gamesense.client.module.Module;
import net.minecraft.init.Items;
import net.minecraft.item.ItemBow;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.potion.PotionUtils;

/*
* @author hausemasterissue
* @since 9/10/2021
* creds to cr33pyware leak
*/


@Module.Declaration(name = "Quiver", category = Category.Combat)
public class Quiver extends Module {
	
	BooleanSetting speed = registerBoolean("Speed", true);
	BooleanSetting strength = registerBoolean("Strength", true);
	IntegerSetting tickDelay = registerInteger("TickDelay", 3, 0, 20);
	
	@Override
    public
    void onUpdate() {
        if (Quiver.mc.player != null) {
            List <Integer> arrowSlots;
            if (Quiver.mc.player.inventory.getCurrentItem().getItem() instanceof ItemBow && Quiver.mc.player.isHandActive() && Quiver.mc.player.getItemInUseMaxCount() >= this.tickDelay.getValue()) {
                Quiver.mc.player.connection.sendPacket(new CPacketPlayer.Rotation(Quiver.mc.player.cameraYaw, - 90.0f, Quiver.mc.player.onGround));
                Quiver.mc.playerController.onStoppedUsingItem(Quiver.mc.player);
            }
            if ((arrowSlots = InventoryUtil.getItemInventory(Items.TIPPED_ARROW)).get(0) == - 1) {
                return;
            }
            int speedSlot = - 1;
            int strengthSlot = - 1;
            for (Integer slot : arrowSlots) {
                if (Objects.requireNonNull(PotionUtils.getPotionFromItem(Quiver.mc.player.inventory.getStackInSlot(slot)).getRegistryName()).getPath().contains("swiftness") && speed.getValue()) {
                    speedSlot = slot;
                    continue;
                }
                if (! Objects.requireNonNull(PotionUtils.getPotionFromItem(Quiver.mc.player.inventory.getStackInSlot(slot)).getRegistryName()).getPath().contains("strength") && strength.getValue())
                    continue;
                strengthSlot = slot;
            }
        }
    }
	
	 
	private int findBow() {
	      return InventoryUtil.getItemHotbar(Items.BOW);
	}
	
	 


}
