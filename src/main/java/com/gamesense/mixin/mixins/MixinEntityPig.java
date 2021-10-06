package com.gamesense.mixin.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import com.gamesense.api.event.events.EntitySaddledEvent;
import com.gamesense.api.event.events.SteerEntityEvent;
import com.gamesense.client.GameSense;
import net.minecraft.entity.passive.EntityPig;


@Mixin(EntityPig.class)
public class MixinEntityPig {
	 @Inject(method = "canBeSteered", at = @At("HEAD"), cancellable = true)
	    public void canBeSteered(CallbackInfoReturnable<Boolean> cir) {
	        SteerEntityEvent event = new SteerEntityEvent();
	        GameSense.EVENT_BUS.post(event);

	        if (event.isCancelled()) {
	            cir.cancel();
	            cir.setReturnValue(true);
	        }
	    }

	    @Inject(method = "getSaddled", at = @At("HEAD"), cancellable = true)
	    public void getSaddled(CallbackInfoReturnable<Boolean> cir) {
	        EntitySaddledEvent event = new EntitySaddledEvent();
	        GameSense.EVENT_BUS.post(event);

	        if (event.isCancelled()) {
	            cir.cancel();
	            cir.setReturnValue(true);
	        }
	    }
}
