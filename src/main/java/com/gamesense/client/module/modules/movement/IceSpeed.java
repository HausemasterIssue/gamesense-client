package com.gamesense.client.module.modules.movement;

import com.gamesense.client.module.Category;
import com.gamesense.client.module.Module;
import net.minecraft.init.Blocks;

/*
* @author hausemasterissue
* @since 4/10/2021
*/

@Module.Declaration(name = "IceSpeed", category = Category.Movement)
public class IceSpeed extends Module {
		
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
