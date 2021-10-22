package com.gamesense.mixin.mixins;

import com.gamesense.api.config.SaveConfig;
import com.gamesense.client.module.ModuleManager;
import com.gamesense.client.module.modules.exploits.MultiTask;
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

@Mixin(Minecraft.class)
public class MixinMinecraft {
    
    @Shadow
    public EntityPlayerSP player;
    @Shadow
    public PlayerControllerMP playerController;

    private boolean handActive = false;
    private boolean isHittingBlock = false;

    // c0mmie blue made this lol
    @Inject(method = "rightClickMouse", at = @At("HEAD"))
    public void rightClickMousePre(CallbackInfo ci) {
        if (ModuleManager.isModuleEnabled(MultiTask.class)) {
            isHittingBlock = playerController.getIsHittingBlock();
            playerController.isHittingBlock = false;
        }
    }

    @Inject(method = "rightClickMouse", at = @At("RETURN"))
    public void rightClickMousePost(CallbackInfo ci) {
        if (ModuleManager.isModuleEnabled(MultiTask.class) && !playerController.getIsHittingBlock()) {
            playerController.isHittingBlock = isHittingBlock;
        }
    }

    @Inject(method = "sendClickBlockToController", at = @At("HEAD"))
    public void sendClickBlockToControllerPre(boolean leftClick, CallbackInfo ci) {
        if (ModuleManager.isModuleEnabled(MultiTask.class)) {
            handActive = player.isHandActive();
            ((AccessorEntityPlayerSP) player).gsSetHandActive(false);
        }
    }

    @Inject(method = "sendClickBlockToController", at = @At("RETURN"))
    public void sendClickBlockToControllerPost(boolean leftClick, CallbackInfo ci) {
        if (ModuleManager.isModuleEnabled(MultiTask.class) && !player.isHandActive()) {
            ((AccessorEntityPlayerSP) player).gsSetHandActive(handActive);
        }
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
