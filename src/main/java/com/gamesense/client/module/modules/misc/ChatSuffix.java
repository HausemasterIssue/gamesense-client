package com.gamesense.client.module.modules.misc;

import com.gamesense.api.setting.values.BooleanSetting;
import com.gamesense.api.event.events.PacketEvent;
import com.gamesense.api.setting.values.ModeSetting;
import com.gamesense.client.GameSense;
import com.gamesense.client.command.CommandManager;
import com.gamesense.client.module.Category;
import com.gamesense.client.module.Module;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.network.play.client.CPacketChatMessage;

import java.util.Arrays;

@Module.Declaration(name = "ChatSuffix", category = Category.Misc)
public class ChatSuffix extends Module {
    ModeSetting separator = registerMode("Separator", Arrays.asList(">>", "<<", "|"), "|");
    BooleanSetting unicode = registerBoolean("Unicode", true);

    @EventHandler
    private final Listener<PacketEvent.Send> listener = new Listener<>(event -> {
        if (PacketEvent.getPacket() instanceof CPacketChatMessage) {
            CPacketChatMessage packet = (CPacketChatMessage) PacketEvent.getPacket();
            if (!packet.message.startsWith("/") && !packet.message.startsWith(CommandManager.getCommandPrefix())) {
                String divider = "";
                switch (this.separator.getValue()) {
                    case ">>": {
                        divider += (this.unicode.getValue() ? " \u300b " : " >> ");
                        break;
                    }

                    case "<<": {
                        divider += (this.unicode.getValue() ? " \u300a " : " << ");
                        break;
                    }

                    case "|": {
                        divider += (this.unicode.getValue() ? " \u23D0 " : " | ");
                        break;
                    }
                }

                String message = packet.message + divider + (this.unicode.getValue() ? this.toUnicode(GameSense.MODNAME) : GameSense.MODNAME);
                packet.message = message.substring(0, Math.min(255, message.length()));
            }
        }
    });

    private String toUnicode(String s) {
        return s.toLowerCase()
                .replace("a", "\u1d00")
                .replace("b", "\u0299")
                .replace("c", "\u1d04")
                .replace("d", "\u1d05")
                .replace("e", "\u1d07")
                .replace("f", "\ua730")
                .replace("g", "\u0262")
                .replace("h", "\u029c")
                .replace("i", "\u026a")
                .replace("j", "\u1d0a")
                .replace("k", "\u1d0b")
                .replace("l", "\u029f")
                .replace("m", "\u1d0d")
                .replace("n", "\u0274")
                .replace("o", "\u1d0f")
                .replace("p", "\u1d18")
                .replace("q", "\u01eb")
                .replace("r", "\u0280")
                .replace("s", "\ua731")
                .replace("t", "\u1d1b")
                .replace("u", "\u1d1c")
                .replace("v", "\u1d20")
                .replace("w", "\u1d21")
                .replace("x", "\u02e3")
                .replace("y", "\u028f")
                .replace("z", "\u1d22");
    }
}
