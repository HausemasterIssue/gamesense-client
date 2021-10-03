package com.gamesense.client.module.modules.movement;

import com.gamesense.api.setting.values.ModeSetting;
import com.gamesense.client.module.Category;
import com.gamesense.client.module.Module;
import com.mojang.realmsclient.gui.ChatFormatting;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraftforge.client.event.InputUpdateEvent;
import java.util.Arrays;


/*
 * @author hausemasterissue
 * @since 9/30/2021
 */

@Module.Declaration(name = "NoSlow", category = Category.Movement)
public class NoSlow extends Module {
	
	ModeSetting mode = registerMode("Mode", Arrays.asList("Normal", "Strict"), "Normal");
	
	private boolean sneaking;
	

    @Override
    public void onUpdate() {
        if (mode.getValue() == "Strict") {
        	if(mc.world != null) {
        		if(!mc.player.isHandActive() && sneaking == true) {
    				mc.player.connection.sendPacket(new CPacketEntityAction(mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
    				sneaking = false;
    			}
    		}
        }
   }

    
    @EventHandler
	private final Listener<InputUpdateEvent> eventListener = new Listener<>(event -> {
			if(mc.player.isHandActive() && sneaking == false && mode.getValue() == "Strict") {
				sneaking = true;
				mc.player.connection.sendPacket(new CPacketEntityAction(mc.player, CPacketEntityAction.Action.START_SNEAKING));
			} else if (mode.getValue() == "Normal" && mc.player.isHandActive() && !mc.player.isRiding()) {
				event.getMovementInput().moveStrafe *= 5;
                event.getMovementInput().moveForward *= 5;
			}
		
		}
	);
    
    
    public String getHudInfo() {
        String t = "";
        if (mode.getValue().equalsIgnoreCase("Normal")){
            t = "[" + ChatFormatting.WHITE + mode.getValue() + ChatFormatting.GRAY + "]";
        } else if (mode.getValue().equalsIgnoreCase("Strict")) {
        	t = "[" + ChatFormatting.WHITE + mode.getValue() + ChatFormatting.GRAY + "]";
        }

        return t;
    }

}
