package com.gamesense.client.module.modules.movement;

import com.gamesense.api.event.events.PacketEvent;
import com.gamesense.api.setting.values.BooleanSetting;
import com.gamesense.api.setting.values.DoubleSetting;
import com.gamesense.api.setting.values.IntegerSetting;
import com.gamesense.client.module.Category;
import com.gamesense.client.module.Module;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketConfirmTeleport;
import net.minecraft.network.play.client.CPacketInput;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketVehicleMove;
import net.minecraft.network.play.server.SPacketMoveVehicle;
import net.minecraft.network.play.server.SPacketPlayerPosLook;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/*
* @author hausemasterissue
* @since 14/10/2021
* credits to haybale client
*/

@Module.Declaration(name = "BoatFly", category = Category.Movement)
public class BoatFly extends Module {
	
	DoubleSetting speed = registerDouble("Speed", 3.0, 0.0, 10.0);
	DoubleSetting verticalSpeed = registerDouble("UpSpeed", 3.0, 0.0, 10.0);
	BooleanSetting noKick = registerBoolean("AntiKick", true);
	BooleanSetting packet = registerBoolean("Packet", true);
	IntegerSetting packets = registerInteger("Packets", 3, 1, 5);
	IntegerSetting interact = registerInteger("Delay", 2, 1, 20);
	
	private EntityBoat target;
    private int teleportID;
	
	 @Override
	    public void onUpdate() {
	        if (mc.player == null) {
	            return;
	        }
	        if (mc.world == null || mc.player.getRidingEntity() == null) {
	            return;
	        }
	        if (mc.player.getRidingEntity() instanceof EntityBoat) {
	            target = (EntityBoat) mc.player.ridingEntity;
	        }
	        mc.player.getRidingEntity().setNoGravity(true);
	        mc.player.getRidingEntity().motionY = 0.0;
	        if (mc.gameSettings.keyBindJump.isKeyDown()) {
	            mc.player.getRidingEntity().onGround = false;
	            mc.player.getRidingEntity().motionY = verticalSpeed.getValue() / 10.0;
	        }
	        if (mc.gameSettings.keyBindSprint.isKeyDown()) {
	            mc.player.getRidingEntity().onGround = false;
	            mc.player.getRidingEntity().motionY = -(verticalSpeed.getValue() / 10.0);
	        }
	        double[] normalDir = directionSpeed(speed.getValue() / 2.0);
	        if (mc.player.movementInput.moveStrafe != 0.0f || mc.player.movementInput.moveForward != 0.0f) {
	            mc.player.getRidingEntity().motionX = normalDir[0];
	            mc.player.getRidingEntity().motionZ = normalDir[1];
	        } else {
	            mc.player.getRidingEntity().motionX = 0.0;
	            mc.player.getRidingEntity().motionZ = 0.0;
	        }
	        if (noKick.getValue().booleanValue()) {
	            if (mc.gameSettings.keyBindJump.isKeyDown()) {
	                if (mc.player.ticksExisted % 8 < 2) {
	                    mc.player.getRidingEntity().motionY = -0.04f;
	                }
	            } else if (mc.player.ticksExisted % 8 < 4) {
	                mc.player.getRidingEntity().motionY = -0.08f;
	            }
	        }
	        handlePackets(mc.player.getRidingEntity().motionX, mc.player.getRidingEntity().motionY, mc.player.getRidingEntity().motionZ);
	    }
	 
	 	public void handlePackets(double x, double y, double z) {
	        if (packet.getValue().booleanValue()) {
	            Vec3d vec = new Vec3d(x, y, z);
	            if (BoatFly.mc.player.getRidingEntity() == null) {
	                return;
	            }
	            Vec3d position = BoatFly.mc.player.getRidingEntity().getPositionVector().add(vec);
	            BoatFly.mc.player.getRidingEntity().setPosition(position.x, position.y, position.z);
	            BoatFly.mc.player.connection.sendPacket(new CPacketVehicleMove(BoatFly.mc.player.getRidingEntity()));
	            for (int i = 0; i < packets.getValue(); ++i) {
	                BoatFly.mc.player.connection.sendPacket((Packet<?>) new CPacketConfirmTeleport(teleportID++));
	            }
	        }
	    }
	 	

	    @SubscribeEvent
	    public void onSendPacket(PacketEvent.Send event) {
	        if (PacketEvent.getPacket() instanceof CPacketVehicleMove && BoatFly.mc.player.isRiding() && BoatFly.mc.player.ticksExisted % interact.getValue() == 0) {
	            BoatFly.mc.playerController.interactWithEntity(BoatFly.mc.player, BoatFly.mc.player.ridingEntity, EnumHand.OFF_HAND);
	        }
	        if ((PacketEvent.getPacket() instanceof CPacketPlayer.Rotation || PacketEvent.getPacket() instanceof CPacketInput) && BoatFly.mc.player.isRiding()) {
	            event.cancel();
	        }
	    }

	    @SubscribeEvent
	    public void onReceivePacket(PacketEvent.Receive event) {
	        if (PacketEvent.getPacket() instanceof SPacketMoveVehicle && BoatFly.mc.player.isRiding()) {
	            event.cancel();
	        }
	        if (PacketEvent.getPacket() instanceof SPacketPlayerPosLook) {
	            teleportID = ((SPacketPlayerPosLook) PacketEvent.getPacket()).teleportId;
	        }
	    }
	 
	 	private double[] directionSpeed(double speed) {
	        float forward = BoatFly.mc.player.movementInput.moveForward;
	        float side = BoatFly.mc.player.movementInput.moveStrafe;
	        float yaw = BoatFly.mc.player.prevRotationYaw + (BoatFly.mc.player.rotationYaw - BoatFly.mc.player.prevRotationYaw) * mc.getRenderPartialTicks();
	        if (forward != 0.0f) {
	            if (side > 0.0f) {
	                yaw += (float) (forward > 0.0f ? -45 : 45);
	            } else if (side < 0.0f) {
	                yaw += (float) (forward > 0.0f ? 45 : -45);
	            }
	            side = 0.0f;
	            if (forward > 0.0f) {
	                forward = 1.0f;
	            } else if (forward < 0.0f) {
	                forward = -1.0f;
	            }
	        }
	        double sin = Math.sin(Math.toRadians(yaw + 90.0f));
	        double cos = Math.cos(Math.toRadians(yaw + 90.0f));
	        double posX = (double) forward * speed * cos + (double) side * speed * sin;
	        double posZ = (double) forward * speed * sin - (double) side * speed * cos;
	        return new double[]{posX, posZ};
	    }

}
