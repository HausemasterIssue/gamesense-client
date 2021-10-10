package com.gamesense.client.module.modules.movement;

import com.gamesense.api.event.events.PacketEvent;
import com.gamesense.api.setting.values.BooleanSetting;
import com.gamesense.client.module.Category;
import com.gamesense.client.module.Module;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.client.event.InputUpdateEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;

/*
 * @author hausemasterissue, sxmurai
 * @since 9/30/2021
 */
@Module.Declaration(name = "NoSlow", category = Category.Movement)
public class NoSlow extends Module {
	
	BooleanSetting sneak = registerBoolean("Sneak", false);
	BooleanSetting strict = registerBoolean("NCP Strict", false);

	private boolean sneaking;

	@Override
	protected void onDisable() {
		if (this.sneaking) {
			mc.player.connection.sendPacket(new CPacketEntityAction(mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
			this.sneaking = false;
		}
	}

	@Override
	public void onUpdate() {
		if (this.sneak.getValue() && !mc.player.isHandActive() && this.sneaking) {
			mc.player.connection.sendPacket(new CPacketEntityAction(mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
			this.sneaking = false;
		}
	}

	@EventHandler
	private final Listener<InputUpdateEvent> inputUpdateEventListener = new Listener<>(event -> {
		if (mc.player.isHandActive() && !mc.player.isRiding()) {
			event.getMovementInput().moveForward *= 5.0f;
			event.getMovementInput().moveStrafe *= 5.0f;
		}
	});

	@EventHandler
	private final Listener<LivingEntityUseItemEvent.Tick> livingEntityUseItemEventTickListener = new Listener<>(event -> {
		if (this.sneak.getValue() && !mc.player.isRiding() && event.getEntityLiving() == mc.player && !this.sneaking) {
			mc.player.connection.sendPacket(new CPacketEntityAction(mc.player, CPacketEntityAction.Action.START_SNEAKING));
			this.sneaking = true;
		}
	});

	@EventHandler
	private final Listener<PacketEvent.Send> packetSendListener = new Listener<>(event -> {
		if (this.strict.getValue() && PacketEvent.getPacket() instanceof CPacketPlayer && mc.player.isHandActive() && !mc.player.isRiding()) {
			mc.player.connection.sendPacket(new CPacketPlayerDigging(CPacketPlayerDigging.Action.ABORT_DESTROY_BLOCK, new BlockPos(Math.floor(mc.player.posX), Math.floor(mc.player.posY), Math.floor(mc.player.posZ)), EnumFacing.DOWN));
		}
	});
	
}
