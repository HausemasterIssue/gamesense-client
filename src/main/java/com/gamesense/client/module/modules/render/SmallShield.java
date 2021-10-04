package com.gamesense.client.module.modules.render;

import com.gamesense.api.setting.values.DoubleSetting;
import com.gamesense.client.module.Category;
import com.gamesense.client.module.Module;
import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.client.renderer.ItemRenderer;

/*
* @author hausemasterissue
* @since 4/10/2021
*/

@Module.Declaration(name = "SmallShield", category = Category.Render)
public class SmallShield extends Module {
	
	DoubleSetting height = registerDouble("Height", 1.0, 0.1, 1.0);
	
	ItemRenderer itemRenderer = mc.entityRenderer.itemRenderer;
	
	public void onUpdate() {
        itemRenderer.equippedProgressOffHand = height.getValue().floatValue();
	}
	
	public String getHudInfo() {
        return "[" + ChatFormatting.WHITE + height.getValue() + ChatFormatting.GRAY + "]";
    }


}
