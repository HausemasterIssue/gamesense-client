package com.gamesense.mixin.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.gamesense.api.event.events.SteerEntityEvent;
import com.gamesense.client.GameSense;
import net.minecraft.entity.passive.EntityLlama;

@Mixin(EntityLlama.class)
public class MixinEntityLlama {
	@Inject(method = "canBeSteered", at = @At("HEAD"), cancellable = true)
    public void canBeSteered(CallbackInfoReturnable<Boolean> cir) {
        SteerEntityEvent event = new SteerEntityEvent();
        GameSense.EVENT_BUS.post(event);

        if (event.isCancelled()) {
            cir.cancel();
            cir.setReturnValue(true);
        }
    }
}
