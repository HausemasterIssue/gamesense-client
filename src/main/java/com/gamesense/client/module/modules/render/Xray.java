package com.gamesense.client.module.modules.render;

import com.gamesense.client.module.Category;
import com.gamesense.client.module.Module;
import net.minecraft.block.Block;

import java.util.ArrayList;

@Module.Declaration(name = "Xray", category = Category.Render)
public class Xray extends Module {
    public static Xray INSTANCE;

    public static ArrayList<Block> blocks = new ArrayList<>();

    public Xray() {
        INSTANCE = this;
    }

    @Override
    protected void onEnable() {
        if (mc.world == null || mc.player == null) {
            this.toggle();
            return;
        }

        mc.renderGlobal.loadRenderers();
    }

    @Override
    protected void onDisable() {
        if (mc.world == null || mc.player == null) {
            return;
        }

        mc.renderGlobal.loadRenderers();
    }

    public static boolean isXrayBlock(Block block) {
        return blocks.contains(block);
    }
}
