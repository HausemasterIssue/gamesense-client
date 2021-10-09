package com.gamesense.client.module.modules.misc;

import com.gamesense.api.event.events.DestroyBlockEvent;
import com.gamesense.api.event.events.PacketEvent;
import com.gamesense.api.setting.values.BooleanSetting;
import com.gamesense.client.module.Category;
import com.gamesense.client.module.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.init.SoundEvents;
import net.minecraft.network.play.server.SPacketBlockChange;
import net.minecraft.network.play.server.SPacketSoundEffect;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;

/*
* @author hausemasterissue
* @since 4/10/2021
* creds to seppuku
*/

@Module.Declaration(name = "AntiDesync", category = Category.Misc)
public class AntiDesync extends Module {
	
	BooleanSetting crystals = registerBoolean("Crystals", true);
	BooleanSetting destroyedBlocks = registerBoolean("Blocks", true);
	
	private boolean destroy;
    private BlockPos pos;
    
    
    public void receivePacket(PacketEvent event) {
            if (event.getPacket() instanceof SPacketSoundEffect) {
                if (this.crystals.getValue()) {
                    final SPacketSoundEffect packet = (SPacketSoundEffect) event.getPacket();
                    if (packet.getCategory() == SoundCategory.BLOCKS && packet.getSound() == SoundEvents.ENTITY_GENERIC_EXPLODE) {
                        final Minecraft mc = Minecraft.getMinecraft();
                        if (mc.world != null) {
                            for (int i = mc.world.loadedEntityList.size() - 1; i > 0; i--) {
                                Entity entity = mc.world.loadedEntityList.get(i);
                                if (entity != null) {
                                    if (entity.isEntityAlive() && entity instanceof EntityEnderCrystal) {
                                        if (entity.getDistance(packet.getX(), packet.getY(), packet.getZ()) <= 6.0f) {
                                            entity.setDead();
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            } else if (event.getPacket() instanceof SPacketBlockChange) {
                if (this.destroyedBlocks.getValue()) {
                    SPacketBlockChange packet = (SPacketBlockChange) event.getPacket();
                    if (packet.getBlockPosition() == this.pos) {
                        this.destroy = true;
                    }
                }
            }
        }
    
    public void onDestroyBlock(DestroyBlockEvent event) {
        if (this.destroyedBlocks.getValue()) {
            this.pos = event.getBlockPos();
            if (this.destroy) {
                this.destroy = false;
                this.pos = null;
            } else {
                event.cancel();
            }
        }
    }
}
