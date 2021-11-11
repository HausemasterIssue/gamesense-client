package com.gamesense.client.module.modules.movement;

import com.gamesense.api.setting.values.BooleanSetting;
import com.gamesense.client.module.Category;
import com.gamesense.client.module.Module;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.network.play.client.CPacketHeldItemChange;
import net.minecraftforge.client.event.InputUpdateEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;

/*
 * @author hausemasterissue
 * @since 11/11/2021
 * thanks to fencingf he is a fucking legend
 */

@Module.Declaration(name = "NoSlow", category = Category.Movement)
public class NoSlow extends Module {
	
	public BooleanSetting soulSand = registerBoolean("SoulSand", true);
	BooleanSetting strict = registerBoolean("NCP Strict", false);


	@EventHandler
	private final Listener<InputUpdateEvent> inputUpdateEventListener = new Listener<>(event -> {
		if (mc.player.isHandActive() && !mc.player.isRiding()) {
			event.getMovementInput().moveForward *= 5.0f;
			event.getMovementInput().moveStrafe *= 5.0f;
		}
	});

	@EventHandler
	private final Listener<LivingEntityUseItemEvent.Tick> livingEntityUseItemEventTickListener = new Listener<>(event -> {
		if(strict.getValue()) {
			if (mc.player.getItemStackFromSlot(EntityEquipmentSlot.MAINHAND).getItem() == Items.GOLDEN_APPLE && mc.gameSettings.keyBindUseItem.isKeyDown()) {
                		mc.player.connection.sendPacket(new CPacketHeldItemChange(findGappleInHotbar()));
           		}
		}
	});
	
   	private int findGappleInHotbar() {
        int slot = 0;

        for (int i = 0; i < 9; ++i) {
            		if (mc.player.inventory.getStackInSlot(i).getItem() == Items.GOLDEN_APPLE) {
                		slot = i;
                		break;
            		}
        	}

        	return slot;
    	}

	
}
