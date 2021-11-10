package com.gamesense.client.module.modules.movement;

import com.gamesense.client.module.Category;
import com.gamesense.client.module.Module;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.settings.KeyBinding;
import org.lwjgl.input.Keyboard;

@Module.Declaration(name = "InventoryMove", category = Category.Movement)
public class InventoryMove extends Module {
	
	private static final KeyBinding[] MOVEMENT_KEYS = new KeyBinding[] {
            mc.gameSettings.keyBindForward,
            mc.gameSettings.keyBindBack,
            mc.gameSettings.keyBindRight,
            mc.gameSettings.keyBindLeft,
            mc.gameSettings.keyBindSneak,
            mc.gameSettings.keyBindJump,
            mc.gameSettings.keyBindSprint
    };
	
	public void onUpdate() {
		if (mc.currentScreen != null && !(mc.currentScreen instanceof GuiChat)) {
            for (KeyBinding binding : InventoryMove.MOVEMENT_KEYS) {
                KeyBinding.setKeyBindState(binding.getKeyCode(), Keyboard.isKeyDown(binding.getKeyCode()));
            }
        }
	}

}
