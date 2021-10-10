package com.gamesense.mixin.mixins;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.block.BlockSoulSand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import com.gamesense.api.event.events.SoulSandEvent;
import com.gamesense.client.GameSense;

@Mixin(BlockSoulSand.class)
public class MixinBlockSoulSand {
	

    @Inject(method = "onEntityCollidedWithBlock", at = @At("HEAD"), cancellable = true)
    public void onEntityCollidedWithBlock(World world, BlockPos blockPos, IBlockState iBlockState, Entity entity, CallbackInfo info) {
        SoulSandEvent soulSandEvent = new SoulSandEvent();
        GameSense.EVENT_BUS.post(soulSandEvent);

        if (soulSandEvent.isCancelled())
            info.cancel();
    }

}
