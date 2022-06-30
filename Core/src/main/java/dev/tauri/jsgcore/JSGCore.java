package dev.tauri.jsgcore;

import com.mojang.logging.LogUtils;
import dev.tauri.jsgcore.commands.InfoCommand;
import dev.tauri.jsgcore.config.JSGConfig;
import dev.tauri.jsgcore.utils.Logging;
import dev.tauri.jsgcore.utils.registry.CommandRegistry;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLLoader;
import net.minecraftforge.fml.loading.moddiscovery.ModInfo;
import org.slf4j.Logger;

import java.util.List;

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
        setUpAddonsCount();
        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, JSGConfig.CONFIG_SERVER);
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, JSGConfig.CONFIG_CLIENT);

        JSGConfig.load(JSGConfig.CONFIG_CLIENT, MOD_ID + "-client.toml");
        JSGConfig.load(JSGConfig.CONFIG_SERVER, MOD_ID + "-server.toml");

        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::commonSetup);

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setUpAddonsCount(){
        List<ModInfo> list = FMLLoader.getLoadingModList().getMods();
        for(ModInfo modInfo : list){
            String id = modInfo.getModId();
            if(id.startsWith("jsg") && !(id.equals(MOD_ID))){
                INSTALLED_ADDONS++;
            }
        }
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
    }

    public static CommandRegistry getCommandRegistry(){
        return COMMAND_REGISTRY;
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
}
