package dev.tauri.jsgmilkyway.stargate.symbols;

import dev.tauri.jsgcore.stargate.symbols.AbstractSymbolType;
import dev.tauri.jsgcore.stargate.symbols.SymbolInterface;
import dev.tauri.jsgcore.stargate.symbols.SymbolsRegistry;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.resources.ResourceLocation;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.Objects;

import static dev.tauri.jsgmilkyway.JSGMilkyWay.MODEL_LOADER;
import static dev.tauri.jsgmilkyway.JSGMilkyWay.MOD_ID;

public enum SymbolMilkyWayEnum implements SymbolInterface {
    SCULPTOR(0, 19, "Sculptor", "0.obj"),
    SCORPIUS(1, 8, "Scorpius", "1.obj"),
    CENTAURUS(2, 4, "Centaurus", "2.obj"),
    MONOCEROS(3, 31, "Monoceros", "3.obj"),
    PEGASUS(5, 18, "Pegasus", "5.obj"),
    ANDROMEDA(6, 21, "Andromeda", "6.obj"),
    SERPENSCAPUT(7, 6, "Serpens Caput", "7.obj"),
    ARIES(8, 23, "Aries", "8.obj"),
    LIBRA(9, 5, "Libra", "9.obj"),
    ERIDANUS(10, 28, "Eridanus", "10.obj"),
    LEOMINOR(11, 37, "Leo Minor", "11.obj"),
    HYDRA(12, 33, "Hydra", "12.obj"),
    SAGITTARIUS(13, 11, "Sagittarius", "13.obj"),
    SEXTANS(14, 36, "Sextans", "14.obj"),
    SCUTUM(15, 10, "Scutum", "15.obj"),
    PISCES(16, 20, "Pisces", "16.obj"),
    VIRGO(17, 2, "Virgo", "17.obj"),
    BOOTES(18, 3, "Bootes", "18.obj"),
    AURIGA(19, 27, "Auriga", "19.obj"),
    CORONAAUSTRALIS(20, 9, "Corona Australis", "20.obj"),
    GEMINI(21, 32, "Gemini", "21.obj"),
    LEO(22, 38, "Leo", "22.obj"),
    CETUS(23, 25, "Cetus", "23.obj"),
    TRIANGULUM(24, 22, "Triangulum", "24.obj"),
    AQUARIUS(25, 17, "Aquarius", "25.obj"),
    MICROSCOPIUM(26, 13, "Microscopium", "26.obj"),
    EQUULEUS(27, 16, "Equuleus", "27.obj"),
    CRATER(28, 1, "Crater", "28.obj"),
    PERSEUS(29, 24, "Perseus", "29.obj"),
    CANCER(30, 35, "Cancer", "30.obj"),
    NORMA(31, 7, "Norma", "31.obj"),
    TAURUS(32, 26, "Taurus", "32.obj"),
    CANISMINOR(33, 30, "Canis Minor", "33.obj"),
    CAPRICORNUS(34, 14, "Capricornus", "34.obj"),
    LYNX(35, 34, "Lynx", "35.obj"),
    ORION(36, 29, "Orion", "36.obj"),
    PISCISAUSTRINUS(37, 15, "Piscis Austrinus", "37.obj"),


    BRB(40, -1, "Bright Red Button", "brb.obj"),

    TAURI_ORIGIN(50, 0, "Tauri Point of Origin", "50.obj"),
    ANOTHER_ORIGIN(51, 0, "Another Point of Origin", "51.obj")
    ;

    public final String name;
    public final String translationKey;
    public final int id;
    public final int angleIndex;
    public final float angle;
    public final ResourceLocation model;

    public static final float ANGLE_PER_GLYPH = 9.2307692f;

    SymbolMilkyWayEnum(int id, int angleIndex, String name, String model) {
        this.id = id;
        this.angleIndex = angleIndex;
        this.angle = 360 - (angleIndex * ANGLE_PER_GLYPH);
        this.name = name;
        this.translationKey = "glyph.jsg.milkyway." + name.toLowerCase().replace(" ", "_");
        this.model = MODEL_LOADER.getModelResource("symbols/" + model);
    }

    @Override
    public @NonNull String getName() {
        return name;
    }

    @Override
    public @NonNull int getId() {
        return id;
    }


    public boolean isBrb(){
        return this.id == 40;
    }
    public boolean isOrigin(){
        return this.id == 50 || this.id == 51;
    }

    public float getAngle(){
        return angle;
    }
    public int getAngleIndex(){
        return angleIndex;
    }

    @Override
    public String getEnglishName() {
        return name;
    }

    public ResourceLocation getIconResource(){
        return new ResourceLocation(MOD_ID, "textures/gui/symbols/" + name.toLowerCase() + ".png");
    }
    public String localize(){
        return I18n.get(translationKey);
    }
    public AbstractSymbolType getSymbolType(){
        return Objects.requireNonNull(SymbolsRegistry.managerBySymbolType("milkyway")).getSymbolType();
    }

    public static @Nullable SymbolInterface byId(int id){
        for(SymbolMilkyWayEnum symbol : SymbolMilkyWayEnum.values()){
            if(symbol.id == id) return symbol;
        }
        return null;
    }
}
