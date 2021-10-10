package com.gamesense.mixin.mixins;

import com.gamesense.api.event.events.SlimeEvent;
import com.gamesense.client.GameSense;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.minecraft.block.BlockSlime;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

@Mixin(BlockSlime.class)
public class MixinBlockSlime {
	
	@Inject(method = "onEntityWalk", at = @At("HEAD"), cancellable = true)
    private void onEntityCollidedWithBlock(World world, BlockPos blockPos, Entity entity, CallbackInfo info) {
        SlimeEvent slimeEvent = new SlimeEvent();
        GameSense.EVENT_BUS.post(slimeEvent);

        if (slimeEvent.isCancelled())
            info.cancel();
    }

}
