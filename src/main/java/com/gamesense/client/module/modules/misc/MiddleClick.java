package com.gamesense.client.module.modules.misc;

import com.gamesense.api.setting.values.BooleanSetting;
import com.gamesense.api.util.misc.MessageBus;
import com.gamesense.api.util.player.InventoryUtil;
import com.gamesense.api.util.player.social.SocialManager;
import com.gamesense.client.module.Category;
import com.gamesense.client.module.Module;
import com.gamesense.client.module.ModuleManager;
import com.gamesense.client.module.modules.gui.ColorMain;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.item.ItemEnderPearl;
import net.minecraft.util.math.RayTraceResult;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import org.lwjgl.input.Mouse;

@Module.Declaration(name = "MiddleClick", category = Category.Misc)
public class MiddleClick extends Module {
    BooleanSetting friend = registerBoolean("Friend", true);
    BooleanSetting pearl = registerBoolean("Pearl", false);

    @EventHandler
    private final Listener<InputEvent.MouseInputEvent> mouseInputEventListener = new Listener<>(event -> {
        RayTraceResult result = mc.objectMouseOver;
        if (result != null && Mouse.isButtonDown(2)) {
            if (result.typeOfHit == RayTraceResult.Type.ENTITY) {
                if (friend.getValue()) {
                    if (SocialManager.isFriend(mc.objectMouseOver.entityHit.getName())) {
                        SocialManager.delFriend(mc.objectMouseOver.entityHit.getName());
                        MessageBus.sendClientPrefixMessage(ModuleManager.getModule(ColorMain.class).getDisabledColor() + "Removed " + mc.objectMouseOver.entityHit.getName() + " from friends list");
                    } else {
                        SocialManager.addFriend(mc.objectMouseOver.entityHit.getName());
                        MessageBus.sendClientPrefixMessage(ModuleManager.getModule(ColorMain.class).getEnabledColor() + "Added " + mc.objectMouseOver.entityHit.getName() + " to friends list");
                    }
                }
            } else if (result.typeOfHit == RayTraceResult.Type.MISS) {
                if (pearl.getValue()) {
                    int oldSlot = mc.player.inventory.currentItem;
                    int pearlSlot = InventoryUtil.findFirstItemSlot(ItemEnderPearl.class, 0, 8);

                    if (pearlSlot != -1) {
                        mc.player.inventory.currentItem = pearlSlot;
                        mc.rightClickMouse();
                        mc.player.inventory.currentItem = oldSlot;
                    }
                }
            }
        }
    });
}
