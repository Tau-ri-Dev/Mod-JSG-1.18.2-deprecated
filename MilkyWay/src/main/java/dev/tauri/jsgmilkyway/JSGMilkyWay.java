package dev.tauri.jsgmilkyway;

import com.mojang.logging.LogUtils;
import dev.tauri.jsgcore.config.files.JSGServerConfig;
import dev.tauri.jsgcore.loader.model.ModelLoader;
import dev.tauri.jsgcore.loader.texture.TextureLoader;
import dev.tauri.jsgcore.registry.CommandRegistry;
import dev.tauri.jsgcore.stargate.symbols.SymbolsRegistry;
import dev.tauri.jsgmilkyway.block.MilkyWayBlocks;
import dev.tauri.jsgmilkyway.commands.MWCommand;
import dev.tauri.jsgmilkyway.config.MainConfig;
import dev.tauri.jsgmilkyway.item.MilkyWayItems;
import dev.tauri.jsgmilkyway.renderer.TESRRegistry;
import dev.tauri.jsgmilkyway.stargate.symbols.MilkyWaySymbolsManager;
import dev.tauri.jsgmilkyway.tileentity.TileEntityRegistry;
import dev.tauri.jsgmilkyway.utils.Logging;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

import static dev.tauri.jsgmilkyway.utils.corenetwork.CoreChecker.isCoreInstalled;

@Mod(JSGMilkyWay.MOD_ID)
public class JSGMilkyWay {
    public static final String MOD_VERSION = "@VERSION@";
    public static final String MC_VERSION = "@MCVERSION@";
    public static final String AUTHORS = "@AUTHORS@";

    public static final String CORE_MOD_ID = "jsg_core";

    public static final String BASE_ID = "jsg";
    public static final String MOD_ID = BASE_ID + "_@MODID@";
    public static final String MOD_NAME = "Just Stargate Mod: @MODNAME@";
    public static final Logger LOGGER = LogUtils.getLogger();

    public static final ModelLoader MODEL_LOADER = new ModelLoader(MOD_ID, JSGMilkyWay.class);
    public static final TextureLoader TEXTURE_LOADER = new TextureLoader(MOD_ID, JSGMilkyWay.class);


    public static final CommandRegistry COMMAND_REGISTRY = new CommandRegistry();

    public JSGMilkyWay() {
        if (!isCoreInstalled()) {
            Logging.error("Error while loading!");
            Logging.error("Missing: JSG: Core mod!");
            Minecraft.getInstance().close();
            return;
        }

        IEventBus eb = FMLJavaModLoadingContext.get().getModEventBus();

        JSGServerConfig.configs.add(new MainConfig());
        MilkyWayBlocks.register(eb);
        TileEntityRegistry.register(eb);
        MilkyWayItems.register(eb);

        SymbolsRegistry.registerManager(new MilkyWaySymbolsManager());

        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
    }

    @SubscribeEvent
    public void onCommandsRegister(RegisterCommandsEvent event) {
        COMMAND_REGISTRY.registerCommand(new MWCommand());
        COMMAND_REGISTRY.register(event.getDispatcher());
    }

    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            MODEL_LOADER.loadModels();
            TEXTURE_LOADER.loadTextures();
            TESRRegistry.register();
        }
    }
}
