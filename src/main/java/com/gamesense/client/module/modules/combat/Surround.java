package com.gamesense.client.module.modules.combat;

import com.gamesense.api.setting.values.BooleanSetting;
import com.gamesense.api.setting.values.IntegerSetting;
import com.gamesense.api.setting.values.ModeSetting;
import com.gamesense.api.util.misc.Offsets;
import com.gamesense.api.util.misc.Timer;
import com.gamesense.api.util.player.InventoryUtil;
import com.gamesense.api.util.player.PlacementUtil;
import com.gamesense.api.util.player.PlayerUtil;
import com.gamesense.api.util.world.BlockUtil;
import com.gamesense.api.util.world.HoleUtil;
import com.gamesense.client.module.Category;
import com.gamesense.client.module.Module;
import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.block.BlockObsidian;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.client.CPacketHeldItemChange;
import java.util.Arrays;

/**
 * @author Hoosiers, hausemasterissue
 * @since 20/10/2021
 */

@Module.Declaration(name = "AutoObsidian", category = Category.Combat)
public class Surround extends Module {

    ModeSetting jumpMode = registerMode("Jump", Arrays.asList("Continue", "Pause", "Disable"), "Disable");
    ModeSetting offsetMode = registerMode("Pattern", Arrays.asList("Normal", "Anti City"), "Normal");
    IntegerSetting delayTicks = registerInteger("Tick Delay", 3, 0, 10);
    IntegerSetting blocksPerTick = registerInteger("Blocks Per Tick", 4, 0, 8);
    BooleanSetting silent = registerBoolean("SilentSwap", true);
    BooleanSetting rotate = registerBoolean("Rotate", true);
    BooleanSetting centerPlayer = registerBoolean("Center Player", true);
    BooleanSetting sneakOnly = registerBoolean("Sneak Only", false);
    BooleanSetting disableNoBlock = registerBoolean("Disable No Obby", true);
    BooleanSetting offhandObby = registerBoolean("Offhand Obby", false);

    private final Timer delayTimer = new Timer();
    private Vec3d centeredBlock = Vec3d.ZERO;
    private static final boolean surrounded = false;

    private int oldSlot = -1;
    private int offsetSteps = 0;
    private boolean outOfTargetBlock = false;
    private boolean activedOff = false;
    private boolean isSneaking = false;
	private String safe = "Safe";
	private boolean isSafe = false;
	private int switches = 0;

    public void onEnable() {
        PlacementUtil.onEnable();
        if (mc.player == null || mc.world == null) {
            disable();
            return;
        }

        if (centerPlayer.getValue() && mc.player.onGround) {
            if (this.isSafeHole()) {
				isSafe = true;
				if(isSafe == true) {
					safe = "Safe";
				}
				switches++;
				if(switches <= 1) {
					if(silent.getValue() != true) {
						mc.player.inventory.currentItem = oldSlot;
						mc.playerController.updateController();
					} else {
						mc.player.connection.sendPacket(new CPacketHeldItemChange(oldSlot));
						mc.player.inventory.currentItem = oldSlot;
						mc.playerController.updateController();
					}
					
				}
				
				if (Block.getBlockFromItem(mc.player.getHeldItemMainhand().getItem()) == Blocks.OBSIDIAN) {
					switches = 0;
				} else {
					oldSlot = mc.player.inventory.currentItem;
				}
				
				return;
			}
			
            mc.player.motionX = 0;
            mc.player.motionZ = 0;
        }

        centeredBlock = BlockUtil.getCenterOfBlock(mc.player.posX, mc.player.posY, mc.player.posY);

        oldSlot = mc.player.inventory.currentItem;
    }

    private boolean isSafeHole() {
        BlockPos blockPos = new BlockPos(mc.player.posX, mc.player.posY, mc.player.posZ);
        return HoleUtil.isHole(blockPos, true, false).getType() != HoleUtil.HoleType.NONE;
    }

    public void onDisable() {
        PlacementUtil.onDisable();
        if (mc.player == null | mc.world == null) return;

        if (outOfTargetBlock) setDisabledMessage("No obsidian detected... Surround turned OFF!");

        if (oldSlot != mc.player.inventory.currentItem && oldSlot != -1 && oldSlot != 9) {
            oldSlot = -1;
        }

        if (isSneaking) {
            mc.player.connection.sendPacket(new CPacketEntityAction(mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
            isSneaking = false;
        }

        AutoCrystal.stopAC = false;

        if (offhandObby.getValue() && OffHand.isActive()) {
            OffHand.removeObsidian();
            activedOff = false;
        }

        centeredBlock = Vec3d.ZERO;
        outOfTargetBlock = false;
    }

    public void onUpdate() {
        if (mc.player == null || mc.world == null) {
            disable();
            return;
        }
		
		if(isSafe == false) {
			safe = "Unsafe";
		}

        if (sneakOnly.getValue() && !mc.player.isSneaking()) {
            return;
        }

        if (!(mc.player.onGround) && !(mc.player.isInWeb) || surrounded) {
            switch (jumpMode.getValue()) {
                case "Pause" : {
                    return;
                }
                case "Disable" : {
                    disable();
                    return;
                }
                default: {
                    break;
                }
            }
        }
        
        int targetBlockSlot = InventoryUtil.findObsidianSlot(offhandObby.getValue(), activedOff);

        if ((outOfTargetBlock || targetBlockSlot == -1) && disableNoBlock.getValue()) {
            outOfTargetBlock = true;
            disable();
            return;
        }

        activedOff = true;

        if (centerPlayer.getValue() && centeredBlock != Vec3d.ZERO && mc.player.onGround) {
            if (this.isSafeHole()) {
				isSafe = true;
				if(isSafe == true) {
					safe = "Safe";
				}
				switches++;
				if(switches <= 1) {
					if(silent.getValue() != true) {
						mc.player.inventory.currentItem = oldSlot;
						mc.playerController.updateController();
					} else {
						mc.player.connection.sendPacket(new CPacketHeldItemChange(oldSlot));
						mc.playerController.updateController();
					}
				}
				
				if (Block.getBlockFromItem(mc.player.getHeldItemMainhand().getItem()) == Blocks.OBSIDIAN) {
					switches = 0;
				} else {
					oldSlot = mc.player.inventory.currentItem;
				}
				return;
			}
			
			
            PlayerUtil.centerPlayer(centeredBlock);
        }

        while (delayTimer.getTimePassed() / 50L >= delayTicks.getValue()) {
            delayTimer.reset();

            int blocksPlaced = 0;

            while (blocksPlaced <= blocksPerTick.getValue()) {
                int maxSteps;
                Vec3d[] offsetPattern;

                if ("Anti City".equals(offsetMode.getValue())) {
                    offsetPattern = Offsets.SURROUND_CITY;
                    maxSteps = Offsets.SURROUND_CITY.length;
                } else {
                    offsetPattern = Offsets.SURROUND;
                    maxSteps = Offsets.SURROUND.length;
                }

                if (offsetSteps >= maxSteps) {
                    offsetSteps = 0;
                    break;
                }

                BlockPos offsetPos = new BlockPos(offsetPattern[offsetSteps]);
                BlockPos targetPos = new BlockPos(mc.player.getPositionVector()).add(offsetPos.getX(), offsetPos.getY(), offsetPos.getZ());

                boolean tryPlacing = true;

                if (mc.player.posY % 1 > 0.2) {
                    targetPos = new BlockPos(targetPos.getX(), targetPos.getY() + 1, targetPos.getZ());
                }

                if (!mc.world.getBlockState(targetPos).getMaterial().isReplaceable()) {
                    tryPlacing = false;
                }

                for (Entity entity : mc.world.getEntitiesWithinAABBExcludingEntity(null, new AxisAlignedBB(targetPos))) {
                    if (entity instanceof EntityPlayer) {
                        tryPlacing = false;
                        break;
                    }
                }

                if (tryPlacing && placeBlock(targetPos)) {
                    blocksPlaced++;
                }

                offsetSteps++;

                if (isSneaking) {
                    mc.player.connection.sendPacket(new CPacketEntityAction(mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
                    isSneaking = false;
                }
            }
        }
    }

    private boolean placeBlock(BlockPos pos) {
        EnumHand handSwing = EnumHand.MAIN_HAND;

        int targetBlockSlot = InventoryUtil.findObsidianSlot(offhandObby.getValue(), activedOff);

        if (targetBlockSlot == -1) {
            outOfTargetBlock = true;
            return false;
        }

        if (targetBlockSlot == 9) {
            activedOff = true;
            if (mc.player.getHeldItemOffhand().getItem() instanceof ItemBlock && ((ItemBlock) mc.player.getHeldItemOffhand().getItem()).getBlock() instanceof BlockObsidian) {
                handSwing = EnumHand.OFF_HAND;
            } else return false;
        }

        if (mc.player.inventory.currentItem != targetBlockSlot && targetBlockSlot != 9) {
		if(silent.getValue() == true) {
			mc.player.connection.sendPacket(new CPacketHeldItemChange(targetBlockSlot));
			mc.playerController.updateController();	
		} else {
			mc.player.inventory.currentItem = targetBlockSlot;
			mc.playerController.updateController();
		}
           
        }

        return PlacementUtil.place(pos, handSwing, rotate.getValue(), true);
    }
	
	public String getHudInfo() {
		if(safe.equalsIgnoreCase("Safe")) {
			return "[" + ChatFormatting.WHITE + "Safe" + ChatFormatting.GRAY + "]";
		} else {
			return "[" + ChatFormatting.WHITE + "Unsafe" + ChatFormatting.GRAY + "]";
		}
	}

}
