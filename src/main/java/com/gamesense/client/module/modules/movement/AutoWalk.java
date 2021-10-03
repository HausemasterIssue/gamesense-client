package com.gamesense.client.module.modules.movement;

/*
@author Christallinqq
@since 10/3/21
 */

import com.gamesense.client.module.Category;
import com.gamesense.client.module.Module;

import net.minecraft.client.settings.KeyBinding;

@Module.Declaration(name = "AutoWalk", category = Category.Movement)
public class AutoWalk extends Module {
    public void onDisable() {
        KeyBinding.setKeyBindState(mc.gameSettings.keyBindForward.getKeyCode(), false);
    }

    public void onUpdate() {
        if(mc.currentScreen == null) {
            KeyBinding.setKeyBindState(mc.gameSettings.keyBindForward.getKeyCode(), true);
        }
    }

}
