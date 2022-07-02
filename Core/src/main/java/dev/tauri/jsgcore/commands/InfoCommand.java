package dev.tauri.jsgcore.commands;

import com.mojang.brigadier.context.CommandContext;
import dev.tauri.jsgcore.JSGCore;
import net.minecraft.Util;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.entity.player.Player;

public class InfoCommand extends AbstractCommand {
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
        return "jsg-info";
    }

    @Override
    public void execute(CommandContext<CommandSourceStack> command) {
    }

    @Override
    public void execute(CommandContext<CommandSourceStack> command, Player player) {
        player.sendMessage(new TextComponent(
        "Mod by: " + JSGCore.AUTHORS + "\n" +
                "Version: " + JSGCore.MOD_VERSION + "\n" +
                "MC Version: " + JSGCore.MC_VERSION + "\n" +
                "Installed addons: " + JSGCore.INSTALLED_ADDONS), Util.NIL_UUID);
    }
}
