package dev.tauri.jsgcore.render.state;

import dev.tauri.jsgcore.loader.texture.TextureLoader;
import dev.tauri.jsgcore.render.ChevronTextureList;
import dev.tauri.jsgcore.stargate.iris.AbstractIrisType;
import dev.tauri.jsgcore.stargate.iris.IrisTypeRegistry;
import dev.tauri.jsgcore.stargate.spinning.EnumSpinDirection;
import dev.tauri.jsgcore.stargate.iris.EnumIrisMode;
import dev.tauri.jsgcore.stargate.iris.EnumIrisState;
import dev.tauri.jsgcore.stargate.spinning.ISpinHelper;
import dev.tauri.jsgcore.stargate.spinning.StargateClassicSpinHelper;
import dev.tauri.jsgcore.stargate.symbols.AbstractSymbolType;
import dev.tauri.jsgcore.stargate.symbols.SymbolInterface;
import dev.tauri.jsgcore.utils.BiomeOverlayEnum;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;

public abstract class StargateClassicRendererState extends StargateAbstractRendererState {

    public StargateClassicRendererState() {
    }

    public StargateClassicRendererState(StargateClassicRendererStateBuilder builder) {
        super(builder);

        this.chevronTextureList = new ChevronTextureList(getChevronTextureBase(), builder.activeChevrons, builder.isFinalActive, getTextureLoader());
        this.spinHelper = new StargateClassicSpinHelper(builder.symbolType, builder.currentRingSymbol, builder.spinDirection, builder.isSpinning, builder.targetRingSymbol, builder.spinStartTime, builder.plusRounds);
        this.biomeOverride = builder.biomeOverride;
        this.irisState = builder.irisState;
        this.irisType = builder.irisType;
        this.irisAnimation = builder.irisAnimation;
    }

    public void startIrisAnimation(long animationStart) {
        this.irisAnimation = animationStart;
    }

    @Override
    public StargateAbstractRendererState initClient(BlockPos pos, Direction facing, BiomeOverlayEnum biomeOverlay) {
        chevronTextureList.initClient();

        return super.initClient(pos, facing, biomeOverlay);
    }

    protected abstract String getChevronTextureBase();
    protected abstract TextureLoader getTextureLoader();

    // Chevrons
    // Saved
    public ChevronTextureList chevronTextureList;

    // Spin
    // Saved
    public ISpinHelper spinHelper;


    // Biome override
    // Saved
    public BiomeOverlayEnum biomeOverride;

    // Iris
    public AbstractIrisType irisType;
    // Saved
    public EnumIrisState irisState;
    public long irisAnimation;

    @Override
    public BiomeOverlayEnum getBiomeOverlay() {
        if (biomeOverride != null) return biomeOverride;

        return super.getBiomeOverlay();
    }

    public void clearChevrons(long time) {
        chevronTextureList.clearChevrons(time);
    }

    // ------------------------------------------------------------------------
    // Saving

    @Override
    public void toBytes(ByteBuf buf) {
        chevronTextureList.toBytes(buf);
        spinHelper.toBytes(buf);

        if (biomeOverride != null) {
            buf.writeBoolean(true);
            buf.writeInt(biomeOverride.ordinal());
        } else {
            buf.writeBoolean(false);
        }
        buf.writeByte(irisState.id);
        buf.writeByte(irisType.id);
        buf.writeLong(irisAnimation);
        super.toBytes(buf);
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        fromBytes(buf, StargateClassicSpinHelper.class);
        //super.fromBytes(buf);
    }

    protected void fromBytes(ByteBuf buf, Class<? extends ISpinHelper> type) {
        chevronTextureList = new ChevronTextureList(getChevronTextureBase(), getTextureLoader());
        chevronTextureList.fromBytes(buf);

        try {
            spinHelper = type.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        spinHelper.fromBytes(buf);

        if (buf.readBoolean()) {
            biomeOverride = BiomeOverlayEnum.values()[buf.readInt()];
        }
        irisState = EnumIrisState.getValue(buf.readByte());
        irisType = IrisTypeRegistry.getIrisTypeById(buf.readByte());
        irisAnimation = buf.readLong();
        super.fromBytes(buf);
    }



    // ------------------------------------------------------------------------
    // Builder

    public static StargateClassicRendererStateBuilder builder() {
        return new StargateClassicRendererStateBuilder();
    }

    public static class StargateClassicRendererStateBuilder extends StargateAbstractRendererStateBuilder {

        public StargateClassicRendererStateBuilder() {
        }

        public AbstractSymbolType symbolType;

        // Chevrons
        public int activeChevrons;
        public boolean isFinalActive;

        // Spinning
        public SymbolInterface currentRingSymbol;
        public EnumSpinDirection spinDirection;
        public boolean isSpinning;
        public SymbolInterface targetRingSymbol;
        public long spinStartTime;
        public int plusRounds;

        // Biome override
        public BiomeOverlayEnum biomeOverride;

        //Iris
        public EnumIrisState irisState;
        public AbstractIrisType irisType;
        public int irisCode;
        public EnumIrisMode irisMode;
        public long irisAnimation;

        public StargateClassicRendererStateBuilder(StargateAbstractRendererStateBuilder superBuilder) {
            setStargateState(superBuilder.stargateState);
        }

        public StargateClassicRendererStateBuilder setSymbolType(AbstractSymbolType symbolType) {
            this.symbolType = symbolType;
            return this;
        }

        public StargateClassicRendererStateBuilder setActiveChevrons(int activeChevrons) {
            this.activeChevrons = activeChevrons;
            return this;
        }

        public StargateClassicRendererStateBuilder setFinalActive(boolean isFinalActive) {
            this.isFinalActive = isFinalActive;
            return this;
        }

        public StargateClassicRendererStateBuilder setCurrentRingSymbol(SymbolInterface currentRingSymbol) {
            this.currentRingSymbol = currentRingSymbol;
            return this;
        }

        public StargateClassicRendererStateBuilder setSpinDirection(EnumSpinDirection spinDirection) {
            this.spinDirection = spinDirection;
            return this;
        }

        public StargateClassicRendererStateBuilder setSpinning(boolean isSpinning) {
            this.isSpinning = isSpinning;
            return this;
        }

        public StargateClassicRendererStateBuilder setTargetRingSymbol(SymbolInterface targetRingSymbol) {
            this.targetRingSymbol = targetRingSymbol;
            return this;
        }

        public StargateClassicRendererStateBuilder setSpinStartTime(long spinStartTime) {
            this.spinStartTime = spinStartTime;
            return this;
        }

        public StargateClassicRendererStateBuilder setBiomeOverride(BiomeOverlayEnum biomeOverride) {
            this.biomeOverride = biomeOverride;
            return this;
        }

        public StargateClassicRendererStateBuilder setIrisState(EnumIrisState irisState) {
            this.irisState = irisState;
            return this;
        }

        public StargateClassicRendererStateBuilder setIrisType(AbstractIrisType irisType) {
            this.irisType = irisType;
            return this;
        }

        public StargateClassicRendererStateBuilder setIrisCode(int code) {
            this.irisCode = code;
            return this;
        }

        public StargateClassicRendererStateBuilder setIrisMode(EnumIrisMode mode) {
            this.irisMode = mode;
            return this;
        }

        public StargateClassicRendererStateBuilder setIrisAnimation(long irisAnimation) {
            this.irisAnimation = irisAnimation;
            return this;
        }

        public StargateClassicRendererStateBuilder setPlusRounds(int rounds){
            this.plusRounds = rounds;
            return this;
        }
    }
}
