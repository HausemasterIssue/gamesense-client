package com.gamesense.client.module.modules.movement;

import com.gamesense.client.module.Category;
import com.gamesense.client.module.Module;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemPotion;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.client.event.InputUpdateEvent;

@Module.Declaration(name = "NoSlowBypass", category = Category.Movement)
public class NoSlowBypass extends Module {
	
	private boolean sneaking = false;
	
	public void onUpdate() {
		if(mc.world != null) {
			if(!mc.player.isHandActive() && sneaking == true) {
				mc.player.connection.sendPacket(new CPacketEntityAction(mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
				sneaking = false;
			}
		}
	}
	
    	@SuppressWarnings("unused")
		@EventHandler
		private final Listener<InputUpdateEvent> eventListener = new Listener<>(event -> {
		if(mc.player.isHandActive() && sneaking == false) {
			sneaking = true;
			mc.player.connection.sendPacket(new CPacketEntityAction(mc.player, CPacketEntityAction.Action.START_SNEAKING));
		}
    });
	

}
