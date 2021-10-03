package com.gamesense.client.module.modules.misc;

/**
 * @author Christallinqq
 * @since 10/3/21
 */

import com.gamesense.client.module.Category;
import com.gamesense.client.module.Module;

import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.EnumHand;

@Module.Declaration(name = "AutoUse", category = Category.Misc)
public class AutoUse extends Module {
    @Override
    public void onUpdate() {
        if(mc.currentScreen == null) KeyBinding.setKeyBindState(mc.gameSettings.keyBindUseItem.getKeyCode(), true);
        else mc.playerController.processRightClick(mc.player, mc.world, EnumHand.MAIN_HAND);
    }

    public void onDisable() {
        KeyBinding.setKeyBindState(mc.gameSettings.keyBindUseItem.getKeyCode(), false);
    }
}
