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

    ModeSetting Separator = registerMode("Separator", Arrays.asList(">>", "<<", "|"), "|");
    BooleanSetting unicode = registerBoolean("Unicode", true);

    @SuppressWarnings("unused")
    @EventHandler
    private final Listener<PacketEvent.Send> listener = new Listener<>(event -> {
        if (PacketEvent.getPacket() instanceof CPacketChatMessage) {
            if (((CPacketChatMessage) PacketEvent.getPacket()).getMessage().startsWith("/") || ((CPacketChatMessage) PacketEvent.getPacket()).getMessage().startsWith(CommandManager.getCommandPrefix()))
                return;
            String Separator2 = null;
            if (Separator.getValue().equalsIgnoreCase(">>")) {
                Separator2 = " \u300b";
            }
            if (Separator.getValue().equalsIgnoreCase("<<")) {
                Separator2 = " \u300a";
            } else if (Separator.getValue().equalsIgnoreCase("|")) {
                Separator2 = " \u23D0 ";
            }
            String old = ((CPacketChatMessage) PacketEvent.getPacket()).getMessage();
            String suffix = Separator2 + toUnicode(GameSense.MODNAME);
            String strictSuffix = Separator2 + "SpiderSense";
            if(unicode.getValue()) {
                String s = old + suffix;
                if (s.length() > 255) return;
                ((CPacketChatMessage) PacketEvent.getPacket()).message = s;
            } else {
                String s = old + strictSuffix;
                if (s.length() > 255) return;
                ((CPacketChatMessage) PacketEvent.getPacket()).message = s;
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
