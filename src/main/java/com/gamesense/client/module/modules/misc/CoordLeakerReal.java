package com.gamesense.client.module.modules.misc;

import com.gamesense.client.module.Category;
import com.gamesense.client.module.Module;

import net.minecraft.entity.player.EntityPlayer;
import java.util.Random;

@Module.Declaration(name = "CoordLeaker", category = Category.Misc)
public class CoordLeakerReal extends Module {
    public void onEnable() {
        if (mc.player != null) {
            mc.player.sendChatMessage(">I just used the CoordLeaker module! Come grief my stash at " + (mc.player.posX) + ", " + (mc.player.posZ) + " thanks to SpiderSense!");
            disable();
        }
    }
}
