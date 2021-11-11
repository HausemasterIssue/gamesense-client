package com.gamesense.mixin.mixins;

import com.gamesense.client.module.ModuleManager;
import com.gamesense.client.module.modules.movement.InventoryMove;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.inventory.GuiEditSign;
import net.minecraft.client.settings.KeyBinding;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(KeyBinding.class)
public class MixinKeyBinding {
    @Shadow
    public boolean pressed;
    
    @Shadow
    Minecraft mc = Minecraft.getMinecraft();

    @Inject(method = "isKeyDown", at = @At("HEAD"), cancellable = true)
    public void hookIsKeyDown(CallbackInfoReturnable<Boolean> info) {
    	InventoryMove inventoryMove = ModuleManager.getModule(InventoryMove.class);
        if (inventoryMove.isEnabled()) {
            if (!(mc.currentScreen instanceof GuiChat) || !(mc.currentScreen instanceof GuiEditSign)) {
                info.setReturnValue(this.pressed);
            }
        }
    }
}
