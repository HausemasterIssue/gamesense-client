package com.gamesense.client.module.modules.movement;

import com.gamesense.client.module.Category;
import com.gamesense.client.module.Module;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemPotion;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Module.Declaration(name = "NoSlowBypass", category = Category.Movement)
public class NoSlowBypass extends Module {
	
	private boolean sneaking = false;
	
	public void onUpdate() {
		if(mc.world != null) {
			if(!mc.player.isHandActive() && sneaking) {
				mc.player.connection.sendPacket(new CPacketEntityAction(mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
				sneaking = false;
			}
	}
	
    	@SubscribeEvent
    	public void onUseItem(LivingEntityUseItemEvent event) {
		if(!sneaking) {
			sneaking = true;
			mc.player.connection.sendPacket(new CPacketEntityAction(mc.player, CPacketEntityAction.Action.START_SNEAKING));
		}
    	}
	

}
