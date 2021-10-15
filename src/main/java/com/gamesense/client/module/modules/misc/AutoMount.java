package com.gamesense.client.module.modules.misc;

import com.gamesense.api.event.events.PlayerMoveEvent;
import com.gamesense.api.setting.values.BooleanSetting;
import com.gamesense.api.setting.values.IntegerSetting;
import com.gamesense.api.util.misc.Timer;
import com.gamesense.client.module.Category;
import com.gamesense.client.module.Module;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.entity.passive.*;
import net.minecraft.util.EnumHand;
import java.util.Comparator;

@Module.Declaration(name = "AutoMount", category = Category.Misc)
public class AutoMount extends Module {
	
	BooleanSetting boats = registerBoolean("Boats", true);
	BooleanSetting horses = registerBoolean("Horses", true);
	BooleanSetting donkeys = registerBoolean("Donkeys", true);
	BooleanSetting mules = registerBoolean("Mules", true);
	BooleanSetting pigs = registerBoolean("Pigs", true);
	BooleanSetting llamas = registerBoolean("Llamas", true);
	BooleanSetting eat = registerBoolean("Eat", true);
	IntegerSetting range = registerInteger("Delay", 4, 0, 15);
	IntegerSetting delay = registerInteger("Delay", 1, 0, 30);
	
	private Timer timer = new Timer();

    @EventHandler
    private Listener<PlayerMoveEvent> OnPlayerUpdate = new Listener<>(p_Event ->
    {
        if (mc.player.isRiding())
            return;

        if (!timer.passedS(delay.getValue() * 1000))
            return;

        timer.reset();

        Entity entity = mc.world.loadedEntityList.stream()
                .filter(p_Entity -> isValidEntity(p_Entity))
                .min(Comparator.comparing(p_Entity -> mc.player.getDistance(p_Entity)))
                .orElse(null);

        if (entity != null)
            mc.playerController.interactWithEntity(mc.player, entity, EnumHand.MAIN_HAND);
    });
    
    @SuppressWarnings("unused")
	private boolean isValidEntity(Entity entity) {
    
        if (entity.getDistance(mc.player) > range.getValue())
            return false;

        if (entity instanceof AbstractHorse) {
        
            AbstractHorse horse = (AbstractHorse) entity;

            if (horse.isChild())
                return false;
        }

        if (entity instanceof EntityBoat && boats.getValue()) {
            return true;
        }

        if (entity instanceof EntityHorse || entity instanceof EntitySkeletonHorse && horses.getValue()) {
        	return true;
        }
            

        if (entity instanceof EntityDonkey && donkeys.getValue()) {
        	return true;
        }
        
        if(entity instanceof EntityMule && mules.getValue()) {
        	return true;
        }
            

        if (entity instanceof EntityPig && pigs.getValue()) {
        
            EntityPig pig = (EntityPig) entity;

            if (pig.getSaddled())
                return true;

            return false;
        }

        if (entity instanceof EntityLlama && llamas.getValue()) {
        
            EntityLlama llama = (EntityLlama) entity;

            if (!llama.isChild())
                return true;
        }

        return false;
    }

}
