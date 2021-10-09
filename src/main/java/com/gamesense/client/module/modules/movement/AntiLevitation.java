package com.gamesense.client.module.modules.movement;

import com.gamesense.api.setting.values.ModeSetting;
import com.gamesense.client.module.Category;
import com.gamesense.client.module.Module;
import net.minecraft.init.MobEffects;
import java.util.Arrays;

@Module.Declaration(name = "AntiLevitation", category = Category.Movement)
public class AntiLevitation extends Module {

	ModeSetting mode = registerMode("Mode", Arrays.asList("Remove", "Motion"), "Remove");
	
    public void onUpdate() {
        if (mc.player.isPotionActive(MobEffects.LEVITATION)) {
            if (mode.getValue() == "Remove") {
                mc.player.removePotionEffect(MobEffects.LEVITATION);
            } else if (mode.getValue() == "Motion") {
                mc.player.motionY = 0.0f;
            }
        }
    }
	
}
