package com.gamesense.client.module.modules.movement;

import com.gamesense.api.setting.values.BooleanSetting;
import com.gamesense.client.module.Category;
import com.gamesense.client.module.Module;

/*
 * @author hausemasterissue
 * @since 9/29/2021
 * with help from inferno ;)
 */

@Module.Declaration(name = "Sprint", category = Category.Movement)
public class Sprint extends Module {

    private BooleanSetting hungerSafe = registerBoolean("HungerSafe", true);
    private BooleanSetting strict = registerBoolean("Strict", true);

    public void onUpdate() {
        if (mc.gameSettings.keyBindForward.isPressed()) {
            if ((hungerSafe.getValue() && mc.player.getFoodStats().getFoodLevel() <= 6) || (strict.getValue() && (mc.player.isSneaking() || mc.player.isHandActive() || mc.player.collidedHorizontally))) {
                mc.player.setSprinting(false);
                return;
            }

            mc.player.setSprinting(true);
        }
    }
    
}
