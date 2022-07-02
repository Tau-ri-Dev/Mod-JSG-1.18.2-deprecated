package dev.tauri.jsgmilkyway.commands;

import com.mojang.brigadier.context.CommandContext;
import dev.tauri.jsgcore.commands.AbstractCommand;
import dev.tauri.jsgmilkyway.config.MainConfig;
import net.minecraft.Util;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.entity.player.Player;

public class MWCommand extends AbstractCommand {
    @Override
    public boolean broadcastToAdmins() {
        return false;
    }

    @Override
    public int getMinPerms() {
        return 0;
    }

    @Override
    public String getCommandName() {
        return "jsg-mw";
    }

    @Override
    public void execute(CommandContext<CommandSourceStack> command) {
    }

    @Override
    public void execute(CommandContext<CommandSourceStack> command, Player player) {
        player.sendMessage(new TextComponent("Hello from MW addon!"), Util.NIL_UUID);
        player.sendMessage(new TextComponent("Config test: " + MainConfig.test_int_value.get()), Util.NIL_UUID);
    }
}
