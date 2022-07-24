package dev.tauri.jsgcore.utils;

import net.minecraft.ChatFormatting;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;

import java.util.EnumSet;

public enum BiomeOverlayEnum {
    NORMAL("", ChatFormatting.GRAY),
    FROST("_frost", ChatFormatting.DARK_AQUA),
    MOSSY("_mossy", ChatFormatting.DARK_GREEN),
    AGED("_aged", ChatFormatting.GRAY),
    SOOTY("_sooty", ChatFormatting.DARK_GRAY),
    ;

    public String suffix;
    private final ChatFormatting color;
    private final String unlocalizedName;

    BiomeOverlayEnum(String suffix, ChatFormatting color) {
        this.suffix = suffix;
        this.color = color;
        this.unlocalizedName = "gui.stargate.biome_overlay." + name().toLowerCase();
    }

    public String getLocalizedColorizedName() {
        return color + I18n.get(unlocalizedName);
    }

    public static BiomeOverlayEnum updateBiomeOverlay(Level world, BlockPos topmostBlock, EnumSet<BiomeOverlayEnum> supportedOverlays) {
        BiomeOverlayEnum ret = getBiomeOverlay(world, topmostBlock);

        if (supportedOverlays.contains(ret))
            return ret;

        return NORMAL;
    }

    private static BiomeOverlayEnum getBiomeOverlay(Level world, BlockPos topmostBlock) {
        Biome biome = world.getBiome(topmostBlock).value();

        // If not Nether and block not under sky
        if (world.dimension() != Level.NETHER && !BlockHelpers.isBlockDirectlyUnderSky(world, topmostBlock))
            return NORMAL;

        if (biome.coldEnoughToSnow(topmostBlock))
            return FROST;

        /*BiomeOverlayEnum overlay = MainConfig.;

        if (overlay != null)
            return overlay;*/

        return NORMAL;
    }

    public static BiomeOverlayEnum fromString(String name) {
        for (BiomeOverlayEnum biomeOverlay : values()) {
            if (biomeOverlay.toString().equals(name)) {
                return biomeOverlay;
            }
        }

        return null;
    }
}
