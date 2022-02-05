package svenhjol.charmonium.module.situational_ambience.sounds;

import svenhjol.charmonium.handler.SoundHandler;
import svenhjol.charmonium.helper.RegistryHelper;
import svenhjol.charmonium.helper.WorldHelper;
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

public class CaveWaterSound extends SituationalSound {
    public static SoundEvent SOUND;

    private CaveWaterSound(PlayerEntity player, Predicate<SituationalSound> validCondition, Function<SituationalSound, SoundEvent> soundCondition) {
        super(player, validCondition, soundCondition);
    }

    public static void init(SoundHandler<SituationalSound> handler) {
        SOUND = RegistryHelper.sound("situational.cave_water");

        Predicate<SituationalSound> validCondition = situation -> {
            PlayerEntity player = situation.getPlayer();
            ClientWorld level = situation.getLevel();

            if (WorldHelper.isOutside(player)) return false;
            if (player.isSubmergedInWater()) return false;
            if (!WorldHelper.isBelowSeaLevel(player)) return false;

            Optional<BlockPos> optWater = BlockPos.findClosest(player.getBlockPos(), 12, 8, pos -> {
                Block block = level.getBlockState(pos).getBlock();
                return block == Blocks.WATER;
            });

            if (optWater.isPresent()) {
                situation.setPos(optWater.get());
                return true;
            }

            return false;
        };

        Function<SituationalSound, SoundEvent> soundCondition = situation -> SOUND;
        handler.getSounds().add(new CaveWaterSound(handler.getPlayer(), validCondition, soundCondition));
    }

    @Override
    public int getDelay() {
        return level.random.nextInt(150) + 120;
    }

    @Override
    public float getVolume() {
        return 0.3F;
    }

    @Override
    public float getPitch() {
        return 0.77F + (0.3F * level.random.nextFloat());
    }
}