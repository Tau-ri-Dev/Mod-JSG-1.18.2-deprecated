package dev.tauri.jsgcore;

import dev.tauri.jsgcore.commands.InfoCommand;
import dev.tauri.jsgcore.commands.TestCommand;
import dev.tauri.jsgcore.registry.CommandRegistry;
import dev.tauri.jsgcore.stargate.network.StargateNetworkFile;
import dev.tauri.jsgcore.utils.Logging;
import dev.tauri.jsgcore.utils.ModLoading;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(JSGCore.MOD_ID)
public class JSGCore{
    public static final String MOD_VERSION = "@VERSION@";
    public static final String MC_VERSION = "@MCVERSION@";
    public static final String AUTHORS = "@AUTHORS@";

    public static int INSTALLED_ADDONS = 0;

    public static final String MOD_BASE_ID = "jsg";
    public static final String MOD_ID = MOD_BASE_ID + "_@MODID@";
    //public static final String MOD_NAME = "Just Stargate Mod: @MODNAME@";

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
        COMMAND_REGISTRY.registerCommand(new TestCommand());
        COMMAND_REGISTRY.register(event.getDispatcher());
    }

    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
        }
    }


    @SubscribeEvent
    public void onWorldLoad(WorldEvent.Load event){
        MinecraftServer server = event.getWorld().getServer();
        if(server != null) {
            ServerLevel overworld = server.overworld();

            if (!event.getWorld().isClientSide()) {
                Logging.info("Loading sg network...");
                StargateNetworkFile.NETWORK = StargateNetworkFile.load(overworld);
            }
        }
    }
    @SubscribeEvent
    public void onWorldSave(WorldEvent.Unload event){
        MinecraftServer server = event.getWorld().getServer();
        if(server != null) {
            ServerLevel overworld = server.overworld();

            if (!event.getWorld().isClientSide()) {
                Logging.info("Saving sg network...");
                StargateNetworkFile.save(overworld, StargateNetworkFile.NETWORK);
            }
        }
    }
}
