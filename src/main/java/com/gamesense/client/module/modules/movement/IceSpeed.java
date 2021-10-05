package com.gamesense.client.module.modules.movement;

import com.gamesense.api.setting.values.DoubleSetting;
import com.gamesense.client.module.Category;
import com.gamesense.client.module.Module;
import net.minecraft.init.Blocks;

@Module.Declaration(name = "IceSpeed", category = Category.Movement)
public class IceSpeed extends Module {
	
    DoubleSetting iceSpeed = registerDouble("IceSpeed", 0.4, 0.0, 5.0);
    DoubleSetting packedSpeed = registerDouble("PackedSpeed", 0.4, 0.0, 5.0);
	DoubleSetting iceSpeed = registerDouble("IceSpeed", 0.4, 0.0, 5.0);
	
    public void onDisable() {
        Blocks.ICE.setDefaultSlipperiness(0.98f);
        Blocks.PACKED_ICE.setDefaultSlipperiness(0.98f);
        Blocks.FROSTED_ICE.setDefaultSlipperiness(0.98f);
    }
    
    public void onUpdate() {
    	Blocks.ICE.setDefaultSlipperiness(0.4f);
        Blocks.PACKED_ICE.setDefaultSlipperiness(0.4f);
        Blocks.FROSTED_ICE.setDefaultSlipperiness(0.4f);
    }

}
