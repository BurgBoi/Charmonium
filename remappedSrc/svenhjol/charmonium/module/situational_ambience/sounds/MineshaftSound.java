package svenhjol.charmonium.module.situational_ambience.sounds;

import svenhjol.charmonium.helper.DimensionHelper;
import svenhjol.charmonium.helper.RegistryHelper;
import svenhjol.charmonium.helper.WorldHelper;
import svenhjol.charmonium.handler.SoundHandler;
import svenhjol.charmonium.module.situational_ambience.SituationalSound;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.BlockPos;

public class MineshaftSound extends SituationalSound {
    public static SoundEvent SOUND;

    private MineshaftSound(PlayerEntity player, Predicate<SituationalSound> validCondition, Function<SituationalSound, SoundEvent> soundCondition) {
        super(player, validCondition, soundCondition);
    }

    public static void init(SoundHandler<SituationalSound> handler) {
        SOUND = RegistryHelper.sound("situational.mineshaft");

        Predicate<SituationalSound> validCondition = situation -> {
            PlayerEntity player = situation.getPlayer();
            ClientWorld level = situation.getLevel();

            if (!DimensionHelper.isOverworld(level)) return false;
            if (WorldHelper.isOutside(player)) return false;
            if (!WorldHelper.isBelowSeaLevel(player)) return false;

            Optional<BlockPos> optBlock = BlockPos.findClosest(player.getBlockPos(), 16, 8, pos -> {
                Block block = level.getBlockState(pos).getBlock();
                return block == Blocks.OAK_PLANKS || block == Blocks.OAK_FENCE
                    || block == Blocks.DARK_OAK_PLANKS || block == Blocks.DARK_OAK_FENCE;
            });

            Optional<BlockPos> optRail = BlockPos.findClosest(player.getBlockPos(), 8, 16, pos -> {
                Block block = level.getBlockState(pos).getBlock();
                return block == Blocks.RAIL;
            });

            if (optBlock.isPresent() && optRail.isPresent()) {
                situation.setPos(optRail.get());
                return true;
            }

            return false;
        };

        Function<SituationalSound, SoundEvent> soundCondition = situation -> SOUND;
        handler.getSounds().add(new MineshaftSound(handler.getPlayer(), validCondition, soundCondition));
    }

    @Override
    public int getDelay() {
        return level.random.nextInt(300) + 300;
    }

    @Override
    public float getVolume() {
        return 0.8F;
    }
}