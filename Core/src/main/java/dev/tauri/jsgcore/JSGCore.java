package dev.tauri.jsgcore;

import com.mojang.logging.LogUtils;
import dev.tauri.jsgcore.commands.InfoCommand;
import dev.tauri.jsgcore.registry.CommandRegistry;
import dev.tauri.jsgcore.utils.ModLoading;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(JSGCore.MOD_ID)
public class JSGCore {
    public static final String MOD_VERSION = "@VERSION@";
    public static final String MC_VERSION = "@MCVERSION@";
    public static final String AUTHORS = "@AUTHORS@";

    public static int INSTALLED_ADDONS = 0;

    public static final String BASE_ID = "jsg";
    public static final String MOD_ID = BASE_ID + "_@MODID@";
    public static final String MOD_NAME = "Just Stargate Mod: @MODNAME@";
    public static final Logger LOGGER = LogUtils.getLogger();

    public static final CommandRegistry COMMAND_REGISTRY = new CommandRegistry();

    public JSGCore() {
        ModLoading.loadMod(FMLJavaModLoadingContext.get().getModEventBus());
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
    }

    @SubscribeEvent
    public void onCommandsRegister(RegisterCommandsEvent event){
        COMMAND_REGISTRY.registerCommand(new InfoCommand());
        COMMAND_REGISTRY.register(event.getDispatcher());
    }

    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
        }
    }

    public static CommandRegistry getCommandRegistry(){
        return COMMAND_REGISTRY;
    }
}
