package com.gamesense.client.module.modules.misc;

/*
@author Christallinqq
@since 10/3/21
 */

import com.gamesense.client.module.Category;
import com.gamesense.client.module.Module;

import net.minecraft.client.settings.KeyBinding;

@Module.Declaration(name = "AutoMine", category = Category.Misc)
public class AutoMine extends Module {
    @Override
    public void onUpdate() {
        if(mc.currentScreen == null) KeyBinding.setKeyBindState(mc.gameSettings.keyBindAttack.getKeyCode(), true);
        else mc.playerController.isHittingBlock = true;
    }

    public void onDisable() {
        KeyBinding.setKeyBindState(mc.gameSettings.keyBindAttack.getKeyCode(), false);
    }
}
