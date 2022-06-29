package dev.tauri.jsgcore.commands;

import com.mojang.brigadier.builder.ArgumentBuilder;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;

public abstract class AbstractCommand {
    public ArgumentBuilder<CommandSourceStack, ?> register(){
        return Commands.literal(getCommandName())
                .requires(cs->cs.hasPermission(getMinPerms()))
                .executes(ctx -> {
                            ctx.getSource().sendSuccess(getSuccessMess(), broadcastToAdmins());
                            return 0;
                        }
                );
    }

    public abstract String getCommandName();
    public abstract Component getSuccessMess();
    public abstract boolean broadcastToAdmins();
    public abstract int getMinPerms();
}
