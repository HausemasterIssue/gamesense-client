package com.gamesense.client.module.modules.render;

import java.util.Arrays;
import com.gamesense.api.setting.values.ModeSetting;
import com.gamesense.client.module.Category;
import com.gamesense.client.module.Module;

@Module.Declaration(name = "NoBob", category = Category.Render)
public class NoBob extends Module {
	
	ModeSetting mode = registerMode("Mode", Arrays.asList("Chad", "Remove"), "Chad");
	
	@Override
	public void onUpdate() {
		if(mc.player == null || mc.world == null) {
			return;
		}
		
		if(mode.getValue().equalsIgnoreCase("Chad")) {
			mc.player.distanceWalkedModified = 4.0f;
		} else {
			mc.gameSettings.viewBobbing = false;
		}
	}

}
