package dev.tauri.jsgcore.registry;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import dev.tauri.jsgcore.commands.AbstractCommand;
import dev.tauri.jsgcore.utils.Logging;
import net.minecraft.commands.CommandSourceStack;

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
            LiteralArgumentBuilder<CommandSourceStack> c = LiteralArgumentBuilder.literal("jsg");
            for (AbstractCommand command : COMMANDS) {
                c.then(command.register());
            }
            dispatcher.register(c);
            Logging.info("Commands successfully registered!");
        }
        catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
