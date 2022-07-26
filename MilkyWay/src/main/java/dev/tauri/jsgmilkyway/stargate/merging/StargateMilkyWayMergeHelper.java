package dev.tauri.jsgmilkyway.stargate.merging;

import dev.tauri.jsgcore.block.stargate.StargateAbstractMemberBlock;
import dev.tauri.jsgcore.block.stargate.base.StargateAbstractBaseBlock;
import dev.tauri.jsgcore.stargate.merging.StargateClassicMergeHelper;
import dev.tauri.jsgcore.utils.BlockMatcher;
import dev.tauri.jsgcore.utils.FacingToRotation;
import dev.tauri.jsgcore.utils.JSGAxisBox;
import dev.tauri.jsgmilkyway.block.MilkyWayBlocks;
import dev.tauri.jsgmilkyway.stargate.StargateSize;
import dev.tauri.jsgmilkyway.tileentity.StargateMilkyWayBaseTile;
import dev.tauri.jsgmilkyway.utils.Logging;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

public class StargateMilkyWayMergeHelper extends StargateClassicMergeHelper {

    public static final StargateMilkyWayMergeHelper INSTANCE = new StargateMilkyWayMergeHelper();

    /**
     * Bounding box used for {@link StargateMilkyWayBaseTile} search.
     * Searches 3 blocks to the left/right and 7 blocks down.
     */
    private static final JSGAxisBox BASE_SEARCH_BOX_SMALL = new JSGAxisBox(-3, -7, 0, 3, 0, 0);
    private static final JSGAxisBox BASE_SEARCH_BOX_LARGE = new JSGAxisBox(-5, -9, 0, 5, 0, 0);

    public static final BlockMatcher BASE_MATCHER = BlockMatcher.forBlock(MilkyWayBlocks.SG_BASE_BLOCK.get());
    public static final BlockMatcher CHEVRON_MATCHER = BlockMatcher.forBlock(MilkyWayBlocks.SG_CHEVRON_BLOCK.get());
    public static final BlockMatcher RING_MATCHER = BlockMatcher.forBlock(MilkyWayBlocks.SG_RING_BLOCK.get());

    private static final List<BlockPos> RING_BLOCKS_SMALL = Arrays.asList(
            new BlockPos(1, 7, 0),
            new BlockPos(3, 5, 0),
            new BlockPos(3, 3, 0),
            new BlockPos(2, 1, 0),
            new BlockPos(-2, 1, 0),
            new BlockPos(-3, 3, 0),
            new BlockPos(-3, 5, 0),
            new BlockPos(-1, 7, 0));

    private static final List<BlockPos> CHEVRON_BLOCKS_SMALL = Arrays.asList(
            new BlockPos(2, 6, 0),
            new BlockPos(3, 4, 0),
            new BlockPos(3, 2, 0),
            new BlockPos(-3, 2, 0),
            new BlockPos(-3, 4, 0),
            new BlockPos(-2, 6, 0),
            new BlockPos(1, 0, 0),
            new BlockPos(-1, 0, 0),
            new BlockPos(0, 7, 0));

    private static final List<BlockPos> RING_BLOCKS_LARGE = Arrays.asList(
            new BlockPos(-1, 0, 0),
            new BlockPos(-3, 1, 0),
            new BlockPos(-4, 3, 0),
            new BlockPos(-5, 4, 0),
            new BlockPos(-4, 6, 0),
            new BlockPos(-4, 7, 0),
            new BlockPos(-2, 9, 0),
            new BlockPos(-1, 9, 0),
            new BlockPos(1, 9, 0),
            new BlockPos(2, 9, 0),
            new BlockPos(4, 7, 0),
            new BlockPos(4, 6, 0),
            new BlockPos(5, 4, 0),
            new BlockPos(4, 3, 0),
            new BlockPos(3, 1, 0),
            new BlockPos(1, 0, 0));

    private static final List<BlockPos> CHEVRON_BLOCKS_LARGE = Arrays.asList(
            new BlockPos(3, 8, 0),
            new BlockPos(5, 5, 0),
            new BlockPos(4, 2, 0),
            new BlockPos(-4, 2, 0),
            new BlockPos(-5, 5, 0),
            new BlockPos(-3, 8, 0),
            new BlockPos(2, 0, 0),
            new BlockPos(-2, 0, 0),
            new BlockPos(0, 9, 0));

    @Override
    public @NotNull List<BlockPos> getRingBlocks() {
        return switch (StargateSize.defaultSize) {
            case SMALL, MEDIUM -> RING_BLOCKS_SMALL;
            case LARGE -> RING_BLOCKS_LARGE;
        };
    }

    @Override
    public @NotNull List<BlockPos> getChevronBlocks() {
        return switch (StargateSize.defaultSize) {
            case SMALL, MEDIUM -> CHEVRON_BLOCKS_SMALL;
            case LARGE -> CHEVRON_BLOCKS_LARGE;
        };
    }

    //@Override
    public JSGAxisBox getBaseSearchBox() {
        return switch (StargateSize.defaultSize) {
            case SMALL, MEDIUM -> BASE_SEARCH_BOX_SMALL;
            case LARGE -> BASE_SEARCH_BOX_LARGE;
        };
    }

    //@Override
    public boolean matchBase(BlockState state) {
        return BASE_MATCHER.apply(state);
    }

    @Override
    public boolean matchMember(BlockState state, boolean chevron) {
        if(chevron)
            return CHEVRON_MATCHER.apply(state);
        return RING_MATCHER.apply(state);
    }

    @Override
    public StargateAbstractMemberBlock getMemberBlock(boolean chevron) {
        if(chevron)
            return (StargateAbstractMemberBlock) MilkyWayBlocks.SG_CHEVRON_BLOCK.get();
        return (StargateAbstractMemberBlock) MilkyWayBlocks.SG_RING_BLOCK.get();
    }


    public void build(Level level, BlockPos basePos){
        StargateAbstractBaseBlock baseBlock = (StargateAbstractBaseBlock) level.getBlockState(basePos).getBlock();
        Direction facing = level.getBlockState(basePos).getValue(BlockStateProperties.FACING);
        Logging.info("WTF1");
        for(BlockPos pos : getRingBlocks()){
            Logging.info("WTF2");
            BlockPos newPos = FacingToRotation.rotatePos(pos, facing);
            BlockPos offPos = newPos.offset(basePos);
            level.setBlock(offPos, getMemberBlock(false).defaultBlockState().setValue(BlockStateProperties.FACING, facing), 11);
        }
        for(BlockPos pos : getChevronBlocks()){
            Logging.info("WTF3");
            BlockPos newPos = FacingToRotation.rotatePos(pos, facing);
            BlockPos offPos = newPos.offset(basePos);
            level.setBlock(offPos, getMemberBlock(true).defaultBlockState().setValue(BlockStateProperties.FACING, facing), 11);
        }
    }
}
