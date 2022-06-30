package dev.tauri.jsgcore.utils.registry;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import dev.tauri.jsgcore.api.ICommandRegistry;
import net.minecraft.commands.CommandSourceStack;
import dev.tauri.jsgcore.commands.InfoCommand;

public class CommandRegistry implements ICommandRegistry {
    @Override
    public void register(CommandDispatcher<CommandSourceStack> dispatcher){
        dispatcher.register(
                LiteralArgumentBuilder.<CommandSourceStack>literal("jsg")
                        .then(new InfoCommand().register())
        );
    }
}
