package com.gamesense.client.module.modules.movement;

import com.gamesense.api.setting.values.BooleanSetting;
import com.gamesense.client.module.Category;
import com.gamesense.client.module.Module;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.item.Item;
import net.minecraft.network.play.client.CPacketHeldItemChange;
import net.minecraftforge.client.event.InputUpdateEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;

/*
 * @author hausemasterissue, fencingf
 * @since 11/11/2021
 */

@Module.Declaration(name = "NoSlow", category = Category.Movement)
public class NoSlow extends Module {
	
	public BooleanSetting soulSand = registerBoolean("SoulSand", true);
	BooleanSetting strict = registerBoolean("NCP Strict", false);
	
	private Item currentItem = mc.player.getHeldItemMainhand().getItem();


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
			if(mc.player.isHandActive()) {
				mc.player.connection.sendPacket(new CPacketHeldItemChange(findItemInHotbar()));
			}
		}
	});
	
	private int findItemInHotbar() {
        int slot = 0;

        for (int i = 0; i < 9; ++i) {
            if (mc.player.inventory.getStackInSlot(i).getItem() == currentItem) {
                slot = i;
                break;
            }
        }

        return slot;
    }

	
}