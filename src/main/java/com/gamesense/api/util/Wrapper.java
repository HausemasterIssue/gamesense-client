package com.gamesense.api.util;

/*
@author Christallinqq
@since 10/3/21
 */

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.world.World;

import org.lwjgl.input.Keyboard;

public class Wrapper {

    private static FontRenderer fontRenderer;

    public static Minecraft mc = Minecraft.getMinecraft();

    public static Minecraft getMinecraft(){
        return Minecraft.getMinecraft();
    }

    public static EntityPlayerSP getPlayer(){
        return getMinecraft().player;
    }

    public static World getWorld(){
        return getMinecraft().world;
    }

    public static int getKey(String keyname){
        return Keyboard.getKeyIndex(keyname.toUpperCase());
    }

    public static FontRenderer getFontRenderer(){
        return fontRenderer;
    }
}
