package dev.tauri.jsgcore.commands;

import dev.tauri.jsgcore.JSGCore;
import net.minecraft.network.chat.Component;

public class InfoCommand extends AbstractCommand{
    @Override
    public boolean broadcastToAdmins(){ return false; }
    @Override
    public int getMinPerms(){ return 0; }
    @Override
    public String getCommandName(){ return "info"; }

    @Override
    public Component getSuccessMess(){
        return Component.translatable(
                "Mod by: " + JSGCore.AUTHORS + "\n" +
                "Version: " + JSGCore.MOD_VERSION + "\n" +
                "MC Version: " + JSGCore.MC_VERSION + "\n" +
                "Installed addons: " + JSGCore.INSTALLED_ADDONS);
    }
}