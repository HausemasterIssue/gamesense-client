package com.gamesense.client.module.modules.movement;

import com.gamesense.client.module.Category;
import com.gamesense.client.module.Module;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.inventory.GuiEditSign;
import net.minecraft.client.settings.KeyBinding;
import org.lwjgl.input.Keyboard;

/*
* @author hausemasterissue
* @since 11/11/2021
*/

@Module.Declaration(name = "InventoryMove", category = Category.Movement)
public class InventoryMove extends Module {
	
	private static final KeyBinding[] moveKeys = new KeyBinding[] {
            mc.gameSettings.keyBindForward,
            mc.gameSettings.keyBindBack,
            mc.gameSettings.keyBindRight,
            mc.gameSettings.keyBindLeft,
            mc.gameSettings.keyBindSneak,
            mc.gameSettings.keyBindJump,
            mc.gameSettings.keyBindSprint
    };
	
	public void onUpdate() {
		if (mc.currentScreen instanceof GuiChat || mc.currentScreen instanceof GuiEditSign || mc.currentScreen == null) {
            		return;
        	}
		
		for (KeyBinding bind : moveKeys) {
             		KeyBinding.setKeyBindState(bind.getKeyCode(), Keyboard.isKeyDown(bind.getKeyCode()));
        	}
	}

}
