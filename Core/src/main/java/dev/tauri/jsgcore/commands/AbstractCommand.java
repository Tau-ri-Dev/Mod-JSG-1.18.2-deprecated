package dev.tauri.jsgcore.commands;

import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.world.entity.player.Player;

public abstract class AbstractCommand {
    public abstract String getCommandName();
    public abstract void execute(CommandContext<CommandSourceStack> command);
    public abstract void execute(CommandContext<CommandSourceStack> command, Player player);
    public abstract boolean broadcastToAdmins();
    public abstract int getMinPerms();
}
