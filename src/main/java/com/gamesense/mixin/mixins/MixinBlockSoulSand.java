package com.gamesense.mixin.mixins;

import com.gamesense.client.module.ModuleManager;
import com.gamesense.client.module.modules.movement.NoSlow;
import net.minecraft.block.BlockSoulSand;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BlockSoulSand.class)
public class MixinBlockSoulSand {

    @Inject(method = "onEntityCollision", at = @At("HEAD"), cancellable = true)
    public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn, CallbackInfo callbackInfo) {
        NoSlow noSlow = ModuleManager.getModule(NoSlow.class);

        if (noSlow.isEnabled() && noSlow.soulSand.getValue()) {
            callbackInfo.cancel();
        }
    }
}
