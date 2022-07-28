package dev.tauri.jsgcore.render.state;

import dev.tauri.jsgcore.render.EnumVortexState;
import dev.tauri.jsgcore.render.StargateRendererStatic;
import dev.tauri.jsgcore.stargate.state.EnumStargateState;
import dev.tauri.jsgcore.state.State;
import dev.tauri.jsgcore.utils.BiomeOverlayEnum;
import dev.tauri.jsgcore.utils.FacingToRotation;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;

public class StargateAbstractRendererState extends State {

    public StargateAbstractRendererState() {}

    protected StargateAbstractRendererState(StargateAbstractRendererStateBuilder builder) {
        if (builder.stargateState.engaged()) {
            doEventHorizonRender = true;
            vortexState = EnumVortexState.STILL;
        }
    }

    public StargateAbstractRendererState initClient(BlockPos pos, Direction facing, BiomeOverlayEnum biomeOverlay) {
        this.pos = pos;
        this.facing = facing;

        if (facing.getAxis() == Direction.Axis.X)
            facing = facing.getOpposite();

        this.horizontalRotation = FacingToRotation.getHorizontal(facing);
        this.verticalRotation = FacingToRotation.getVertical(facing);
        this.biomeOverlay = biomeOverlay;

        return this;
    }

    // Global
    // Not saved
    public BlockPos pos;
    public Direction facing;
    public float horizontalRotation;
    public float verticalRotation;
    private BiomeOverlayEnum biomeOverlay;

    // Gate
    // Saved
    public boolean doEventHorizonRender = false;
    public EnumVortexState vortexState = EnumVortexState.FORMING;

    // Event horizon
    // Not saved
    public StargateRendererStatic.QuadStrip backStrip;
    public boolean backStripClamp;
    public Float whiteOverlayAlpha;
    public long gateWaitStart = 0;
    public long gateWaitClose = 0;
    public boolean zeroAlphaSet;
    public boolean horizonUnstable = false;
    public int horizonSegments = 0;

    public void openGate(long totalWorldTime) {
        gateWaitStart = totalWorldTime;

        zeroAlphaSet = false;
        backStripClamp = true;
        whiteOverlayAlpha = 1.0f;

        vortexState = EnumVortexState.FORMING;
        doEventHorizonRender = true;
    }

    public void closeGate(long totalWorldTime) {
        gateWaitClose = totalWorldTime;
        vortexState = EnumVortexState.CLOSING;
    }

    public BiomeOverlayEnum getBiomeOverlay() {
        return biomeOverlay;
    }

    public void setBiomeOverlay(BiomeOverlayEnum biomeOverlay) {
        this.biomeOverlay = biomeOverlay;
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeBoolean(doEventHorizonRender);
        buf.writeInt(vortexState.index);
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        doEventHorizonRender = buf.readBoolean();
        vortexState = EnumVortexState.valueOf( buf.readInt() );
    }


    // ------------------------------------------------------------------------
    // Builder

    public static StargateAbstractRendererStateBuilder builder() {
        return new StargateAbstractRendererStateBuilder();
    }

    public static class StargateAbstractRendererStateBuilder {

        // Gate
        protected EnumStargateState stargateState;

        public StargateAbstractRendererStateBuilder setStargateState(EnumStargateState stargateState) {
            this.stargateState = stargateState;
            return this;
        }

        public StargateAbstractRendererState build() {
            return new StargateAbstractRendererState(this);
        }
    }
}
