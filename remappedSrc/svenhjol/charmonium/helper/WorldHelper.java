package svenhjol.charmonium.helper;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.block.MushroomBlock;
import net.minecraft.block.PillarBlock;
import net.minecraft.block.StemBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;

/**
 * @version 1.0.0-charmonium
 */
@SuppressWarnings("unused")
public class WorldHelper {
    public static boolean isDay(PlayerEntity player) {
        long dayTime = player.world.getTimeOfDay() % 24000;
        return dayTime >= 0 && dayTime < 12700;
    }

    public static boolean isNight(PlayerEntity player) {
        long dayTime = player.world.getTimeOfDay() % 24000;
        return dayTime >= 12700;
    }

    public static boolean isOutside(PlayerEntity player) {
        if (!DimensionHelper.isOverworld(player.world))
            return false; // TODO: configurable outdoor dimensions

        if (player.isSubmergedInWater()) return false;

        int blocks = 16;
        int start = 1;

        BlockPos playerPos = player.getBlockPos();

        for (int i = start; i < start + blocks; i++) {
            BlockPos check = new BlockPos(playerPos.getX(), playerPos.getY() + i, playerPos.getZ());
            BlockState state = player.world.getBlockState(check);
            Block block = state.getBlock();

            if (player.world.isSkyVisibleAllowingSea(check)) return true;
            if (player.world.isAir(check)) continue;

            // TODO: configurable clear blocks
            if (state.getMaterial() == Material.GLASS
                || (block instanceof PillarBlock && state.getMaterial() == Material.WOOD)
                || block instanceof MushroomBlock
                || block instanceof StemBlock
            ) continue;

            if (state.isOpaque()) return false;
        }

        return player.getBlockPos().getY() >= 48;
    }

    public static boolean isBelowSeaLevel(PlayerEntity player) {
        return player.getBlockPos().getY() < player.world.getSeaLevel();
    }

    public static double getDistanceSquared(BlockPos pos1, BlockPos pos2) {
        double d0 = pos1.getX();
        double d1 = pos1.getZ();
        double d2 = d0 - pos2.getX();
        double d3 = d1 - pos2.getZ();
        return d2 * d2 + d3 * d3;
    }
}
