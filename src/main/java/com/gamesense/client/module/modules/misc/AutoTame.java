package com.gamesense.client.module.modules.misc;

import com.gamesense.api.event.events.PacketEvent;
import com.gamesense.api.setting.values.DoubleSetting;
import com.gamesense.api.util.misc.MessageBus;
import com.gamesense.api.util.misc.Timer;
import com.gamesense.client.module.Category;
import com.gamesense.client.module.Module;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.AbstractHorse;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketUseEntity;
import net.minecraft.util.EnumHand;

import java.util.Objects;

/*
* @author hausemasterissue
* @since 7/10/2021
* creds to salhack
*/

@Module.Declaration(name = "AutoTame", category = Category.Misc)
public class AutoTame extends Module {
	
	DoubleSetting delay = registerDouble("Delay", 0.1, 0, 1.0);
	
	 private AbstractHorse EntityToTame = null;
	 private Timer timer = new Timer();
	
	 public void onEnable() {
		 MessageBus.sendClientPrefixMessage("Right click on the mob you want to tame");
		 EntityToTame = null;
	 }
	 
	 @EventHandler
	    private final Listener<PacketEvent.Receive> packetReceiveListener = new Listener<>(event -> {
	        Packet<?> packet = PacketEvent.getPacket();
	        if (packet instanceof CPacketUseEntity) {
	        	if (EntityToTame != null) {
	        		return;
	        	}
	        	
	        	CPacketUseEntity packet1 = (CPacketUseEntity) PacketEvent.getPacket();
	        	
	        	Entity entity = packet1.getEntityFromWorld(mc.world);
	        	
	        	if (entity instanceof AbstractHorse)
	            {
	                if (!((AbstractHorse) entity).isTame())
	                {
	                    EntityToTame = (AbstractHorse)entity;
	                    MessageBus.sendClientPrefixMessage("Will try to tame " + entity.getName());
	                }
	            }
	                
	        }
	    });
	 
	 public void onUpdate() {
		 if (EntityToTame == null)
	            return;
	        
	        if (EntityToTame.isTame())
	        {
	            MessageBus.sendClientPrefixMessage("Successfully tamed " + EntityToTame.getName() + ", disabling.");
	            toggle();
	            return;
	        }
	        
	        if (mc.player.isRiding())
	            return;
	        
	        if (mc.player.getDistance(EntityToTame) > 5.0f)
	            return;
	        
	        if (!timer.passedS(delay.getValue()))
	            return;
	        
	        timer.reset();
	        Objects.requireNonNull(mc.getConnection()).sendPacket(new CPacketUseEntity(EntityToTame, EnumHand.MAIN_HAND));
	 }

}
