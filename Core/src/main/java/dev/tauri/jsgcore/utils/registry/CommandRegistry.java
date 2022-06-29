package dev.tauri.jsgcore.utils.registry;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.commands.CommandSourceStack;
import dev.tauri.jsgcore.commands.InfoCommand;

public class CommandRegistry {
    public CommandRegistry(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(
                LiteralArgumentBuilder.<CommandSourceStack>literal("jsg")
                        .then(new InfoCommand().register())
        );
    }
}
