package dev.tauri.jsgcore.api;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandSourceStack;

public interface ICommandRegistry {
    void register(CommandDispatcher<CommandSourceStack> dispatcher);
}
