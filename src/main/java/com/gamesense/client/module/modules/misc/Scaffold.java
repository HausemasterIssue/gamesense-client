package com.gamesense.client.module.modules.misc;

import com.gamesense.api.setting.values.BooleanSetting;
import com.gamesense.api.util.player.InventoryUtil;
import com.gamesense.api.util.player.PlacementUtil;
import com.gamesense.api.util.world.BlockUtil;
import com.gamesense.api.util.misc.Timer;
import com.gamesense.client.module.Category;
import com.gamesense.client.module.Module;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @author aesthetical, hausemasterissue
 * Taken from my client, Inferno.
 */

@Module.Declaration(name = "Scaffold", category = Category.Misc)
public class Scaffold extends Module {
    private static final BlockPos[] DIRECTION_OFFSETS = new BlockPos[] {
            new BlockPos(0, 0, 0),
            new BlockPos(-1, 0, 0),
            new BlockPos(1, 0, 0),
            new BlockPos(0, 0, -1),
            new BlockPos(0, 0, 1)
    };

    BooleanSetting tower = registerBoolean("Tower", true);
    BooleanSetting silent = registerBoolean("Silent", false);
    BooleanSetting rotate = registerBoolean("Rotate", true);
    BooleanSetting roundRotation = registerBoolean("RoundRotation", false);

    private final Timer timer = new Timer();
    private final Queue<BlockPos> blocks = new ConcurrentLinkedQueue<>();

    @Override
    protected void onDisable() {
        this.blocks.clear();
    }

    @Override
    public void onUpdate() {
        BlockPos base = new BlockPos(mc.player.posX, mc.player.posY - 1.0, mc.player.posZ);
        if (mc.world.isAirBlock(base)) {
            int slot = InventoryUtil.findFirstItemSlot(ItemBlock.class, 0, 8);
            if (slot == -1) {
                // rip
                return;
            }

            int oldSlot = mc.player.inventory.currentItem;
            if(silent.getValue()) {
                InventoryUtil.switchTo(slot, true);
            } else {
                InventoryUtil.switchTo(slot, false);   
            }
            

            this.updateBlocks(base);
            while (!this.blocks.isEmpty()) {
                BlockPos pos = this.blocks.poll();
                if (pos == null) {
                    return;
                }

                if (mc.player.getDistance(pos.x, pos.y, pos.z) > 4.5) {
                    continue;
                }
                
                if (tower.getValue() && mc.gameSettings.keyBindJump.isKeyDown()) {
                    mc.player.motionX *= 0.3;
                    mc.player.motionZ *= 0.3;
                    mc.player.jump();
                    if (timer.passedMs(1500L)) {
                        mc.player.motionY = -0.28;
                        timer.reset();
                    } else {
                        final float towerMotion = 0.41999998688f;
                        mc.player.setVelocity(0, towerMotion, 0);
                        timer.reset();
                    }
                }

                if (this.rotate.getValue()) {
                    BlockUtil.faceVectorPacketInstant(new Vec3d(pos.x + 0.5, pos.y + 0.5, pos.z + 0.5), this.roundRotation.getValue());
                }

                PlacementUtil.place(pos, EnumHand.MAIN_HAND, false);
            }
            
            if(silent.getValue()) {
                InventoryUtil.switchTo(oldSlot, true);
            }
            
        }
        
    }

    private void updateBlocks(BlockPos base) {
        if (!mc.world.isAirBlock(base)) {
            return;
        }

        for (BlockPos offset : Scaffold.DIRECTION_OFFSETS) {
            for (EnumFacing facing : EnumFacing.values()) {
                if (facing == EnumFacing.DOWN) {
                    continue;
                }

                BlockPos neighbor = base.add(offset);
                if (facing == EnumFacing.UP) {
                    this.blocks.add(neighbor);
                    return;
                } else {
                    this.blocks.add(neighbor.add(facing.getDirectionVec()));
                    return;
                }
            }
        }
    }
}
