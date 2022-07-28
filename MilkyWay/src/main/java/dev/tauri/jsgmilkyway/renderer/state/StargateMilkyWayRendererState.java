package dev.tauri.jsgmilkyway.renderer.state;

import dev.tauri.jsgcore.loader.texture.TextureLoader;
import dev.tauri.jsgcore.render.state.StargateClassicRendererState;
import dev.tauri.jsgmilkyway.stargate.StargateSize;
import io.netty.buffer.ByteBuf;

import static dev.tauri.jsgmilkyway.JSGMilkyWay.TEXTURE_LOADER;

public class StargateMilkyWayRendererState extends StargateClassicRendererState {
    public StargateMilkyWayRendererState() {}

    private StargateMilkyWayRendererState(StargateMilkyWayRendererStateBuilder builder) {
        super(builder);

        this.stargateSize = builder.stargateSize;
    }

    // Gate
    // Saved
    public StargateSize stargateSize = StargateSize.defaultSize;

    // Chevrons
    // Not saved
    public boolean chevronOpen;
    public long chevronActionStart;
    public boolean chevronOpening;
    public boolean chevronClosing;

    public void openChevron(long totalWorldTime) {
        chevronActionStart = totalWorldTime;
        chevronOpening = true;
    }

    public void closeChevron(long totalWorldTime) {
        chevronActionStart = totalWorldTime;
        chevronClosing = true;
    }

    @Override
    protected String getChevronTextureBase() {
        return "milkyway/chevron";
    }

    @Override
    protected TextureLoader getTextureLoader() {
        return TEXTURE_LOADER;
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(stargateSize.id);

        super.toBytes(buf);
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        stargateSize = StargateSize.fromId(buf.readInt());

        super.fromBytes(buf);
    }


    // ------------------------------------------------------------------------
    // Builder

    public static StargateMilkyWayRendererStateBuilder builder() {
        return new StargateMilkyWayRendererStateBuilder();
    }

    public static class StargateMilkyWayRendererStateBuilder extends StargateClassicRendererStateBuilder {
        public StargateMilkyWayRendererStateBuilder() {}

        private StargateSize stargateSize;

        public StargateMilkyWayRendererStateBuilder(StargateClassicRendererStateBuilder superBuilder) {
            super(superBuilder);
            setSymbolType(superBuilder.symbolType);
            setActiveChevrons(superBuilder.activeChevrons);
            setFinalActive(superBuilder.isFinalActive);
            setCurrentRingSymbol(superBuilder.currentRingSymbol);
            setSpinDirection(superBuilder.spinDirection);
            setSpinning(superBuilder.isSpinning);
            setTargetRingSymbol(superBuilder.targetRingSymbol);
            setSpinStartTime(superBuilder.spinStartTime);
            setBiomeOverride(superBuilder.biomeOverride);
            setIrisState(superBuilder.irisState);
            setIrisType(superBuilder.irisType);
            setIrisCode(superBuilder.irisCode);
            setIrisMode(superBuilder.irisMode);
            setIrisAnimation(superBuilder.irisAnimation);
            setPlusRounds(superBuilder.plusRounds);
        }

        public StargateMilkyWayRendererStateBuilder setStargateSize(StargateSize stargateSize) {
            this.stargateSize = stargateSize;
            return this;
        }

        @Override
        public StargateMilkyWayRendererState build() {
            return new StargateMilkyWayRendererState(this);
        }
    }
}
