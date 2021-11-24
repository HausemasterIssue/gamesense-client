package com.gamesense.client.module.modules.movement;

import com.gamesense.api.setting.values.DoubleSetting;
import com.gamesense.api.setting.values.BooleanSetting;
import com.gamesense.client.module.Category;
import com.gamesense.client.module.Module;
import com.gamesense.client.module.ModuleManager;

@Module.Declaration(name = "FastFall", category = Category.Movement)
public class FastFall extends Module {

    DoubleSetting height = registerDouble("Height", 2.5, 0.5, 5.0);
    DoubleSetting speed = registerDouble("Speed", 10.0, 0.1, 10.0);
    BooleanSetting strict = registerBoolean("Strict", false);

    public void onUpdate() {
        if (mc.world == null || mc.player == null || mc.player.isInWater() || mc.player.isInLava() || mc.player.isOnLadder()
                || mc.gameSettings.keyBindJump.isKeyDown()) {
            return;
        }

        if (ModuleManager.isModuleEnabled(Speed.class)) return;

        if (mc.player != null && mc.player.onGround && !mc.player.isInWater() && !mc.player.isOnLadder()) {
            for (double y = 0.0; y < this.height.getValue() + 0.5; y += 0.01) {
                if (!mc.world.getCollisionBoxes(mc.player, mc.player.getEntityBoundingBox().offset(0.0, -y, 0.0)).isEmpty()) {
                    mc.player.motionY = strict.getValue() ? -0.22 : -speed.getValue();
                    break;
                }
            }
        }
    }
}
