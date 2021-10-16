package com.gamesense.client.module.modules.movement;

import java.util.Arrays;
import com.gamesense.client.module.Category;
import com.gamesense.client.module.Module;
import com.gamesense.api.setting.values.ModeSetting;
import com.mojang.realmsclient.gui.ChatFormatting;

@Module.Declaration(name = "AntiVoid", category = Category.Movement)
public class AntiVoid extends Module {
	
	ModeSetting mode = registerMode("Mode", Arrays.asList("Float", "Freeze", "SlowFall", "Teleport", "Timer", "Jump"), "Float");
	
	public void onUpdate() {
		if(mc.world == null || mc.player == null) {
			return;
		}
		
		if(mc.player.posY <= 0) {
			switch(mode.getValue()) {
			     case "Float":
	             mc.player.motionY = 0.5;
	             break;
	         case "Freeze":
	             mc.player.motionY = 0;
	             break;
	         case "SlowFall":
	             mc.player.motionY /= 12;
	             break;
	         case "Teleport":
	             mc.player.setPosition(mc.player.posX, mc.player.posY + 2, mc.player.posZ);
	             break;
	         case "Timer":
	             mc.timer.tickLength = 50f / 0.1f;
	             break;
	         case "Jump":
	        	 mc.player.jump();
			}
		}
	}
	
	public void onDisable() {
		mc.timer.tickLength = 50f;
	}
	
	public String getHudInfo() {
        return "[" + ChatFormatting.WHITE + mode.getValue() + ChatFormatting.GRAY + "]";
    }

}
