package com.gamesense.client.command.commands;

import com.gamesense.api.util.misc.MessageBus;
import com.gamesense.client.command.Command;
import com.gamesense.client.module.modules.render.Xray;
import net.minecraft.block.Block;

import java.util.Objects;
import java.util.stream.Collectors;

@Command.Declaration(name = "Xray", syntax = "xray [block]", alias = {"x", "xra", "xrayblocks"})
public class XrayCommand extends Command {
    @Override
    public void onCommand(String command, String[] message) {
        if (message.length == 0) {
            MessageBus.sendCommandMessage(
                    "Here are the current blocks: " +
                            Xray.blocks.stream().map((b) -> Objects.requireNonNull(b.getRegistryName()).toString()).collect(Collectors.joining(", ")),
                    true
            );

            return;
        }

        Block block = Block.getBlockFromName(String.join("_", message));
        if (block == null) {
            MessageBus.sendCommandMessage("That is not a valid block name.", true);
            return;
        }

        if (Xray.blocks.contains(block)) {
            Xray.blocks.remove(block);
            MessageBus.sendCommandMessage("Removed " + block, true);
        } else {
            Xray.blocks.add(block);
            MessageBus.sendCommandMessage("Added " + block, true);
        }

        if (Xray.INSTANCE.isOn() && mc.world != null && mc.player != null) {
            mc.renderGlobal.loadRenderers();
        }
    }
}
