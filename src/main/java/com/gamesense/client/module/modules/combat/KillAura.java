package com.gamesense.client.module.modules.combat;

import com.gamesense.api.setting.values.BooleanSetting;
import com.gamesense.api.setting.values.DoubleSetting;
import com.gamesense.api.setting.values.ModeSetting;
import com.gamesense.api.util.misc.Pair;
import com.gamesense.api.util.player.InventoryUtil;
import com.gamesense.api.util.player.PlayerPacket;
import com.gamesense.api.util.player.RotationUtil;
import com.gamesense.api.util.player.social.SocialManager;
import com.gamesense.api.util.world.EntityUtil;
import com.gamesense.client.manager.managers.PlayerPacketManager;
import com.gamesense.client.module.Category;
import com.gamesense.client.module.Module;
import com.gamesense.client.module.ModuleManager;
import com.gamesense.client.module.modules.misc.AutoGG;
import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.monster.EntityPolarBear;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityLlama;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.entity.projectile.EntityShulkerBullet;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.item.Item;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.Vec2f;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

/**
 * @author 0b00101010, hausemasterissue
 * @since 07/02/2021
 */

@Module.Declaration(name = "Aura", category = Category.Combat)
public class KillAura extends Module {

    BooleanSetting players = registerBoolean("Players", true);
    BooleanSetting hostileMobs = registerBoolean("Monsters", true);
    BooleanSetting passiveMobs = registerBoolean("Animals", true);
    BooleanSetting neutralMobs = registerBoolean("Neutrals", true);
    BooleanSetting projectiles = registerBoolean("Projectiles", true);
    BooleanSetting vehicles = registerBoolean("Vehicles", true);
    DoubleSetting range = registerDouble("Range", 5, 0, 10);
    DoubleSetting wallsRange = registerDouble("WallsRange", 3.5, 0, 10);
    ModeSetting itemUsed = registerMode("Item", Arrays.asList("Sword", "Axe", "Both", "All"), "Sword");
    ModeSetting enemyPriority = registerMode("Priority", Arrays.asList("Closest", "Health"), "Closest");
    BooleanSetting swordPriority = registerBoolean("SwordPriority", true);
    BooleanSetting caCheck = registerBoolean("AC Check", false);
    BooleanSetting rotation = registerBoolean("Rotation", true);
    BooleanSetting autoSwitch = registerBoolean("Switch", false);
    BooleanSetting stopSprint = registerBoolean("StopSprint", true);
    BooleanSetting delay = registerBoolean("HitDelay", true);
    DoubleSetting switchHealth = registerDouble("Min Switch Health", 0f, 0f, 20f);

    private Entity renderEntity;

    public void onUpdate() {
        if (mc.player == null || !mc.player.isEntityAlive()) return;

        final double rangeSq = range.getValue() * range.getValue();
        final double wallsRangeSq = wallsRange.getValue() * wallsRange.getValue();
        Optional<Entity> optionalTarget = mc.world.loadedEntityList.stream()
                .filter(entity -> entity instanceof EntityLivingBase)
                .filter(entity -> !EntityUtil.basicChecksEntity(entity))
                .filter(entity -> mc.player.getDistanceSq(entity) <= rangeSq)
                .filter(entity -> mc.player.getDistanceSq(entity) <= wallsRangeSq)
                .filter(this::attackCheck)
                .min(Comparator.comparing(e -> (enemyPriority.getValue().equals("Closest") ? mc.player.getDistanceSq(e) : ((EntityLivingBase) e).getHealth())));

        boolean sword = itemUsed.getValue().equalsIgnoreCase("Sword");
        boolean axe = itemUsed.getValue().equalsIgnoreCase("Axe");
        boolean both = itemUsed.getValue().equalsIgnoreCase("Both");
        boolean all = itemUsed.getValue().equalsIgnoreCase("All");

        if (optionalTarget.isPresent()) {
            Pair<Float, Integer> newSlot = new Pair<>(0.0f, -1);

            if (autoSwitch.getValue() && (mc.player.getHealth() + mc.player.getAbsorptionAmount() >= switchHealth.getValue())) {
                if (sword || both || all) {
                    newSlot = findSwordSlot();
                }
                if ((axe || both || all) && !(swordPriority.getValue() && newSlot.getValue() != -1)) {
                    Pair<Float, Integer> possibleSlot = findAxeSlot();
                    if (possibleSlot.getKey() > newSlot.getKey()) {
                        newSlot = possibleSlot;
                    }
                }
            }

            int temp = mc.player.inventory.currentItem;
            if ((newSlot.getValue() != -1)) {
                mc.player.inventory.currentItem = newSlot.getValue();
            }

            if (shouldAttack(sword, axe, both, all)) {
                Entity target = optionalTarget.get();

                if (rotation.getValue()) {
                    Vec2f rotation = RotationUtil.getRotationTo(target.getEntityBoundingBox());
                    PlayerPacket packet = new PlayerPacket(this, rotation);
                    PlayerPacketManager.INSTANCE.addPacket(packet);
                }

                if (ModuleManager.isModuleEnabled(AutoGG.class)) {
                    AutoGG.INSTANCE.addTargetedPlayer(target.getName());
                }

                attack(target);
            } else {
                mc.player.inventory.currentItem = temp;
            }
        }
    }


    private Pair<Float, Integer> findSwordSlot() {
        List<Integer> items = InventoryUtil.findAllItemSlots(ItemSword.class);
        List<ItemStack> inventory = mc.player.inventory.mainInventory;

        float bestModifier = 0f;
        int correspondingSlot = -1;
        for (Integer integer : items) {
            if (integer > 8) {
                continue;
            }

            ItemStack stack = inventory.get(integer);
            float modifier = (EnchantmentHelper.getModifierForCreature(stack, EnumCreatureAttribute.UNDEFINED) + 1f) * ((ItemSword) stack.getItem()).getAttackDamage();

            if (modifier > bestModifier) {
                bestModifier = modifier;
                correspondingSlot = integer;
            }
        }

        return new Pair<>(bestModifier, correspondingSlot);
    }

    private Pair<Float, Integer> findAxeSlot() {
        List<Integer> items = InventoryUtil.findAllItemSlots(ItemAxe.class);
        List<ItemStack> inventory = mc.player.inventory.mainInventory;

        float bestModifier = 0f;
        int correspondingSlot = -1;
        for (Integer integer : items) {
            if (integer > 8) {
                continue;
            }

            ItemStack stack = inventory.get(integer);
            float modifier = (EnchantmentHelper.getModifierForCreature(stack, EnumCreatureAttribute.UNDEFINED) + 1f) * ((ItemAxe) stack.getItem()).attackDamage;

            if (modifier > bestModifier) {
                bestModifier = modifier;
                correspondingSlot = integer;
            }
        }

        return new Pair<>(bestModifier, correspondingSlot);
    }

    private boolean shouldAttack(boolean sword, boolean axe, boolean both, boolean all) {
        Item item = mc.player.getHeldItemMainhand().getItem();
        return (all
                || (sword || both) && item instanceof ItemSword
                || (axe || both) && item instanceof ItemAxe)
                && (!caCheck.getValue() || !ModuleManager.getModule(AutoCrystal.class).isAttacking);
    }

    private void attack(Entity e) {
        if (mc.player.getCooledAttackStrength(0.0f) >= 1.0f || !pause.getValue()) {
            if(stopSprint.getValue()) {
            	 mc.player.connection.sendPacket((Packet<?>) new CPacketEntityAction(mc.player, CPacketEntityAction.Action.STOP_SPRINTING));
                 mc.player.setSprinting(false);
            }
            mc.playerController.attackEntity(mc.player, e);
            mc.player.swingArm(EnumHand.MAIN_HAND);
            if(stopSprint.getValue()) {
            	mc.player.connection.sendPacket((Packet<?>) new CPacketEntityAction(mc.player, CPacketEntityAction.Action.START_SPRINTING));
            	mc.player.setSprinting(true);
            }
        }
    }

    private boolean attackCheck(Entity entity) {
        if (players.getValue() && entity instanceof EntityPlayer && !SocialManager.isFriend(entity.getName())) {
            if (((EntityPlayer) entity).getHealth() > 0) {
                return true;
            }
        }

        if (passiveMobs.getValue() && entity instanceof EntityAnimal || entity instanceof EntitySquid) {
            return !(entity instanceof EntityTameable);
        }
        
        if (neutralMobs.getValue() && entity instanceof EntityPigZombie || entity instanceof EntityWolf ||
        		entity instanceof EntityEnderman || entity instanceof EntityIronGolem || entity instanceof EntityLlama || entity instanceof EntityPolarBear) {
            return true;
        }
        
        if(projectiles.getValue() && entity instanceof EntityShulkerBullet || entity instanceof EntityFireball) {
        	return true;
        }
        
        if(vehicles.getValue() && entity instanceof EntityBoat || entity instanceof EntityMinecart) {
            return true;
        }
        

        return hostileMobs.getValue() && entity instanceof EntityMob;
    }
    
    public String getHudInfo() {
        String t = "";
            if (renderEntity == null) {
                t = "[" + ChatFormatting.WHITE + "None" + ChatFormatting.GRAY + "]";
            } else {
                t = "[" + ChatFormatting.WHITE + renderEntity.getName() + ChatFormatting.GRAY + "]";
            }

        return t;
    }
}
