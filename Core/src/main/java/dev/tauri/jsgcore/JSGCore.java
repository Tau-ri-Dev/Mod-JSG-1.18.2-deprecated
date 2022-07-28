package dev.tauri.jsgcore;

import dev.tauri.jsgcore.commands.InfoCommand;
import dev.tauri.jsgcore.commands.TestCommand;
import dev.tauri.jsgcore.config.AbstractConfigFile;
import dev.tauri.jsgcore.loader.model.ModelLoader;
import dev.tauri.jsgcore.loader.texture.TextureLoader;
import dev.tauri.jsgcore.registry.CommandRegistry;
import dev.tauri.jsgcore.screen.ScreenRegistry;
import dev.tauri.jsgcore.screen.ScreenTypes;
import dev.tauri.jsgcore.screen.stargate.StargateScreen;
import dev.tauri.jsgcore.stargate.network.StargateNetworkFile;
import dev.tauri.jsgcore.utils.Logging;
import dev.tauri.jsgcore.utils.ModLoading;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLLoader;
import net.minecraftforge.fml.loading.moddiscovery.ModInfo;

import java.util.List;

import static dev.tauri.jsgcore.config.JSGConfigStorage.configFiles;

@Mod(JSGCore.MOD_ID)
public class JSGCore{
    public static final String MOD_VERSION = "@VERSION@";
    public static final String MC_VERSION = "@MCVERSION@";
    public static final String AUTHORS = "@AUTHORS@";

    public static int INSTALLED_ADDONS = 0;

    public static final String MOD_BASE_ID = "jsg";
    public static final String MOD_ID = MOD_BASE_ID + "_@MODID@";

    public static final CommandRegistry COMMAND_REGISTRY = new CommandRegistry();
    public static final ScreenRegistry SCREEN_REGISTRY = new ScreenRegistry(MOD_ID);

    public static final ModelLoader MODEL_LOADER = new ModelLoader(MOD_ID, JSGCore.class);
    public static final TextureLoader TEXTURE_LOADER = new TextureLoader(MOD_ID, JSGCore.class);

    public JSGCore() {
        IEventBus eb = FMLJavaModLoadingContext.get().getModEventBus();
        ModLoading.loadMod(eb);
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
            MODEL_LOADER.loadModels();
            TEXTURE_LOADER.loadTextures();
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
                Logging.debug("Saving sg network...");
                StargateNetworkFile.save(overworld, StargateNetworkFile.NETWORK);
            }
        }
    }
}
