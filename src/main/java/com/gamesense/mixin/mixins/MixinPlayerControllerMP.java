package com.gamesense.mixin.mixins;

import com.gamesense.api.event.events.DamageBlockEvent;
import com.gamesense.api.event.events.DestroyBlockEvent;
import com.gamesense.api.event.events.ReachDistanceEvent;
import com.gamesense.client.GameSense;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerControllerMP.class)
public abstract class MixinPlayerControllerMP {

    @Shadow
    public abstract void syncCurrentPlayItem();

    @Inject(method = "onPlayerDestroyBlock", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;playEvent(ILnet/minecraft/util/math/BlockPos;I)V"), cancellable = true)
    private void onPlayerDestroyBlock(BlockPos pos, CallbackInfoReturnable<Boolean> callbackInfoReturnable) {
        GameSense.EVENT_BUS.post(new DestroyBlockEvent(pos));
    }

    @Inject(method = "onPlayerDamageBlock(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/EnumFacing;)Z", at = @At("HEAD"), cancellable = true)
    private void onPlayerDamageBlock(BlockPos posBlock, EnumFacing directionFacing, CallbackInfoReturnable<Boolean> callbackInfoReturnable) {
        DamageBlockEvent event = new DamageBlockEvent(posBlock, directionFacing);
        GameSense.EVENT_BUS.post(event);
        if (event.isCancelled()) {
            callbackInfoReturnable.setReturnValue(false);
        }
    }

    @Inject(method = "getBlockReachDistance", at = @At("RETURN"), cancellable = true)
    private void getReachDistanceHook(final CallbackInfoReturnable<Float> distance) {
        ReachDistanceEvent reachDistanceEvent = new ReachDistanceEvent(distance.getReturnValue());
        GameSense.EVENT_BUS.post(reachDistanceEvent);

        distance.setReturnValue(reachDistanceEvent.getDistance());
    }

    // this needs to be added into a rewrite of the two modules (PacketUse, PacketXP)
//    @Inject(method = "onStoppedUsingItem", at = @At("HEAD"), cancellable = true)
//    public void onStoppedUsingItem(EntityPlayer playerIn, CallbackInfo ci) {
//        PacketUse packetUse = ModuleManager.getModule(PacketUse.class);
//
//        if (packetUse.isEnabled()) {
//            if ((packetUse.food.getValue() && playerIn.getHeldItem(playerIn.getActiveHand()).getItem() instanceof ItemFood)
//                    || (packetUse.potion.getValue() && playerIn.getHeldItem(playerIn.getActiveHand()).getItem() instanceof ItemPotion)
//                    || packetUse.all.getValue()) {
//                this.syncCurrentPlayItem();
//                playerIn.stopActiveHand();
//                ci.cancel();
//            }
//        }
//    }
}