package com.gamesense.client.module.modules.combat;

import com.gamesense.api.setting.values.BooleanSetting;
import com.gamesense.api.util.player.social.SocialManager;
import com.gamesense.client.module.Category;
import com.gamesense.client.module.Module;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBow;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

/*
* @author hausemasterissue
* @since 15/10/2021
*/

@Module.Declaration(name = "BowAim", category = Category.Combat)
public class BowAim extends Module {
	
	BooleanSetting packet = registerBoolean("Packet", true);
	
	@Override
    public void onUpdate() {
        if (mc.player.getHeldItemMainhand().getItem() instanceof ItemBow && mc.player.isHandActive() && mc.player.getItemInUseMaxCount() >= 3) {
            EntityPlayer player = null;
            float tickDis = 100.0f;
            for (EntityPlayer p : mc.world.playerEntities) {
                float dis;
                if (p instanceof EntityPlayerSP || SocialManager.isFriend(p.getName()) || !((dis = p.getDistance(mc.player)) < tickDis)) continue;
                tickDis = dis;
                player = p;
            }
            if (player != null) {
                Vec3d pos = interpolateEntity(player, mc.getRenderPartialTicks());
                float[] angels = calcAngle(interpolateEntity(mc.player, mc.getRenderPartialTicks()), pos);
                if(packet.getValue()) {
                	mc.player.connection.sendPacket((Packet<?>) new CPacketPlayer.Rotation(angels[0], angels[1], mc.player.onGround));
                } else {
                	mc.player.rotationYaw = angels[0];
                	mc.player.rotationPitch = angels[1];
                }
                
            }
        }
    }
	
	 public static Vec3d interpolateEntity(final Entity entity, final float time) {
	        return new Vec3d(entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * time, entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * time, entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * time);
	 }
	 
	 public static float[] calcAngle(final Vec3d from, final Vec3d to) {
	        final double difX = to.x - from.x;
	        final double difY = (to.y - from.y) * -1.0;
	        final double difZ = to.z - from.z;
	        final double dist = MathHelper.sqrt(difX * difX + difZ * difZ);
	        return new float[] { (float)MathHelper.wrapDegrees(Math.toDegrees(Math.atan2(difZ, difX)) - 90.0), (float)MathHelper.wrapDegrees(Math.toDegrees(Math.atan2(difY, dist))) };
	 }
	    

}
