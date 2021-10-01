package com.gamesense.client.module.modules.movement;

import com.gamesense.api.setting.values.ModeSetting;
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
    
    ModeSetting mode = registerMode("Mode", Arrays.asList("Legit", "Rage"), "Legit");
    public BooleanSetting hungerSafe = registerBoolean("HungerSafe", true);
    public BooleanSetting strict = registerBoolean("Strict", true);

    public void onUpdate() {
        
        if (mode.getValue() == "Legit" && mc.gameSettings.keyBindForward.pressed || mode.getValue() == "Rage") {
            if ((hungerSafe.getValue() && mc.player.getFoodStats().getFoodLevel() <= 6) || (strict.getValue() && (mc.player.isSneaking() || mc.player.isHandActive() || mc.player.collidedHorizontally))) {
                mc.player.setSprinting(false);
                return;
            }
             
            mc.player.setSprinting(true); 
        }
        
    }
    
    public String getHudInfo() {
        String t = "";
        if (mode.getValue().equalsIgnoreCase("Legit")){
            t = "[" + ChatFormatting.WHITE + mode.getValue() + ChatFormatting.GRAY + "]";
        } else if (mode.getValue().equalsIgnoreCase("Rage")) {
        	t = "[" + ChatFormatting.WHITE + mode.getValue() + ChatFormatting.GRAY + "]";
        }

        return t;
    }
    
}
