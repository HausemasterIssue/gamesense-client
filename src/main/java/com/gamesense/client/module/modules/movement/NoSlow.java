package com.gamesense.client.module.modules.movement;

import com.gamesense.api.setting.values.BooleanSetting;
import com.gamesense.api.setting.values.ModeSetting;
import com.gamesense.client.module.Category;
import com.gamesense.client.module.Module;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemPotion;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraftforge.client.event.InputUpdateEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Arrays;

import org.lwjgl.input.Keyboard;

/*
 * @author hausemasterissue
 * @since 9/30/2021
 * credits to momentum
 */

@Module.Declaration(name = "NoSlow", category = Category.Movement)
public class NoSlow extends Module {
	
	ModeSetting mode = registerMode("Mode", Arrays.asList("Normal", "Strict"), "Normal");
	BooleanSetting inventoryMove = registerBoolean("InventoryMove", true);
	
	private boolean sneaking;
	

    @Override
    public void onUpdate() {
        /*if (mc.player == null || mc.world == null)
            return;
	*/

        if (mode.getValue() == "Strict") {
            Item item = mc.player.getActiveItemStack().getItem();
            if (sneaking && ((!mc.player.isHandActive() && item instanceof ItemFood || item instanceof ItemBow || item instanceof ItemPotion) || (!(item instanceof ItemFood) || !(item instanceof ItemBow) || !(item instanceof ItemPotion)))) {
                mc.player.connection.sendPacket(new CPacketEntityAction(mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
                sneaking = false;
            }
        }

        if (mc.currentScreen != null && !(mc.currentScreen instanceof GuiChat) && inventoryMove.getValue()) {

            if (Keyboard.isKeyDown(200))
                mc.player.rotationPitch -= 5;

            if (Keyboard.isKeyDown(208))
                mc.player.rotationPitch += 5;

            if (Keyboard.isKeyDown(205))
                mc.player.rotationYaw += 5;

            if (Keyboard.isKeyDown(203))
                mc.player.rotationYaw -= 5;

            if (mc.player.rotationPitch > 90)
                mc.player.rotationPitch = 90;

            if (mc.player.rotationPitch < -90)
                mc.player.rotationPitch = -90;
        }
    }
    
    @SubscribeEvent
    public void onUseItem(LivingEntityUseItemEvent event) {
        if (mode.getValue() == "Strict") {
            if (!sneaking) {
                mc.player.connection.sendPacket(new CPacketEntityAction(mc.player, CPacketEntityAction.Action.START_SNEAKING));
                sneaking = true;
            }
        }
    }
    
    @SubscribeEvent
    public void onInputUpdate(InputUpdateEvent event) {
        if (mode.getValue() == "Normal") {
            if (mc.player.isHandActive() && !mc.player.isRiding()) {
                event.getMovementInput().moveStrafe *= 5;
                event.getMovementInput().moveForward *= 5;
            }
        }
    }

}
