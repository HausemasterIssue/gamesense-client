package com.gamesense.client.module.modules.render;

import com.gamesense.api.setting.values.DoubleSetting;
import com.gamesense.client.module.Category;
import com.gamesense.client.module.Module;
import net.minecraft.client.renderer.ItemRenderer;

/*
* @author hauemasterissue
* @since 8/10/2021
*/

@Module.Declaration(name = "SmallShield", category = Category.Render)
public class SmallShield extends Module {
	
	DoubleSetting height = registerDouble("Height", 0.5, 0.1, 1.0);
	
	ItemRenderer itemRenderer = mc.entityRenderer.itemRenderer;
	
	public void onUpdate() {
        	itemRenderer.equippedProgressOffHand = height.getValue().floatValue();
	}


}
