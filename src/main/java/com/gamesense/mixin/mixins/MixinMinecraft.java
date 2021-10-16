package com.gamesense.mixin.mixins;

import com.gamesense.api.config.SaveConfig;
import com.gamesense.client.module.ModuleManager;
import com.gamesense.mixin.mixins.accessor.AccessorEntityPlayerSP;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.crash.CrashReport;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.IOException;

@Mixin(Minecraft.class)
public class MixinMinecraft {

    @Shadow
    public EntityPlayerSP player;
    @Shadow
    public PlayerControllerMP playerController;

    private boolean handActive = false;
    private boolean isHittingBlock = false;

    // imagine pasting from c0mmie_ blue in 2021 lol
    @Inject(method = "rightClickMouse", at = @At("HEAD"))
    public void rightClickMousePre(CallbackInfo ci) {

    }

    @Inject(method = "rightClickMouse", at = @At("RETURN"))
    public void rightClickMousePost(CallbackInfo ci) {

    }

    @Inject(method = "sendClickBlockToController", at = @At("HEAD"))
    public void sendClickBlockToControllerPre(boolean leftClick, CallbackInfo ci) {

    }

    @Inject(method = "sendClickBlockToController", at = @At("RETURN"))
    public void sendClickBlockToControllerPost(boolean leftClick, CallbackInfo ci) {

    }

    @Inject(method = "crashed", at = @At("HEAD"))
    public void crashed(CrashReport crash, CallbackInfo callbackInfo) {
        SaveConfig.init();
    }

    @Inject(method = "shutdown", at = @At("HEAD"))
    public void shutdown(CallbackInfo callbackInfo) {
        SaveConfig.init();
    }
}