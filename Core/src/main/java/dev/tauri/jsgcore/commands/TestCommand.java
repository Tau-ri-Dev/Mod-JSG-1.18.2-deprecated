package dev.tauri.jsgcore.commands;

import com.mojang.brigadier.context.CommandContext;
import dev.tauri.jsgcore.stargate.address.StargateAddress;
import dev.tauri.jsgcore.stargate.network.StargateNetworkFile;
import dev.tauri.jsgcore.stargate.network.StargatePos;
import dev.tauri.jsgcore.utils.Logging;
import net.minecraft.Util;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import java.util.Random;

public class TestCommand extends AbstractCommand{
    @Override
    public String getCommandName() {
        return "jsg-sgnet";
    }

    @Override
    public void execute(CommandContext<CommandSourceStack> command) {

    }

    @Override
    public void execute(CommandContext<CommandSourceStack> command, Player player) {
        player.sendMessage(new TextComponent("Test!"), Util.NIL_UUID);
    }

    @Override
    public boolean broadcastToAdmins() {
        return false;
    }

    @Override
    public int getMinPerms() {
        return 0;
    }
}
