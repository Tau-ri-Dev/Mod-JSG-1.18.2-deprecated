package dev.tauri.jsgcore.registry;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import dev.tauri.jsgcore.commands.AbstractCommand;
import dev.tauri.jsgcore.utils.Logging;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.world.entity.player.Player;

import java.util.ArrayList;
import java.util.List;

public class CommandRegistry {

    private final List<AbstractCommand> COMMANDS = new ArrayList<>();

    public void registerCommand(AbstractCommand command){
        COMMANDS.add(command);
    }

    public void registerCommands(AbstractCommand... commands){
        COMMANDS.addAll(List.of(commands));
    }

    public void register(CommandDispatcher<CommandSourceStack> dispatcher){
        try {
            for (AbstractCommand cmd : COMMANDS) {
                dispatcher.register(Commands.literal(cmd.getCommandName()).executes((command) -> {
                    if (command.getSource().getEntity() instanceof Player player) {
                        if(player.hasPermissions(cmd.getMinPerms()))
                            cmd.execute(command, player);
                    }
                    else
                        cmd.execute(command);
                    return Command.SINGLE_SUCCESS;
                }));
            }
            Logging.info("Commands successfully registered!");
        }
        catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
