package svenhjol.charmonium.module.underground_ambience;

import svenhjol.charmonium.helper.DimensionHelper;
import svenhjol.charmonium.helper.RegistryHelper;
import svenhjol.charmonium.handler.SoundHandler;

import java.util.function.Predicate;
import java.util.function.Supplier;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.BlockPos;

public class UndergroundSounds {
    public static class Cave extends UndergroundSound {
        public static SoundEvent SOUND;

        protected Cave(PlayerEntity player, Predicate<UndergroundSound> validCondition, Supplier<SoundEvent> soundCondition) {
            super(player, validCondition, soundCondition);
        }

        public static void init(SoundHandler<UndergroundSound> handler) {
            SOUND = RegistryHelper.sound("ambience.cave");

            // config check
            if (!UndergroundAmbience.playCaveAmbience) return;

            // prepare conditions to play the sound
            Predicate<UndergroundSound> validCondition = underground -> {
                PlayerEntity player = underground.getPlayer();
                ClientWorld level = underground.getLevel();

                if (!UndergroundAmbience.validDimensions.contains(DimensionHelper.getDimension(level))) return false;
                if (player.isSubmergedInWater()) return false;

                BlockPos pos = player.getBlockPos();
                int light = level.getLightLevel(pos);

                if (!level.isSkyVisibleAllowingSea(pos) && pos.getY() <= player.world.getSeaLevel())
                    return pos.getY() <= UndergroundAmbience.caveDepth || light <= UndergroundAmbience.lightLevel;

                return false;
            };

            handler.getSounds().add(new Cave(handler.getPlayer(), validCondition, () -> SOUND));
        }
    }

    public static class DeepCave extends UndergroundSound {
        public static SoundEvent SOUND;

        protected DeepCave(PlayerEntity player, Predicate<UndergroundSound> validCondition, Supplier<SoundEvent> soundCondition) {
            super(player, validCondition, soundCondition);
        }

        public static void init(SoundHandler<UndergroundSound> handler) {
            SOUND = RegistryHelper.sound("ambience.deep_cave");

            // config check
            if (!UndergroundAmbience.playDeepCaveAmbience) return;

            // prepare conditions to play the sound
            Predicate<UndergroundSound> validCondition = underground -> {
                PlayerEntity player = underground.getPlayer();
                ClientWorld level = underground.getLevel();

                if (!UndergroundAmbience.validDimensions.contains(DimensionHelper.getDimension(level))) return false;
                if (player.isSubmergedInWater()) return false;

                BlockPos pos = player.getBlockPos();
                int light = level.getLightLevel(pos);
                int bottom = level.getBottomY() < 0 ? 0 : 32;
                return !level.isSkyVisibleAllowingSea(pos) && pos.getY() <= bottom && light < UndergroundAmbience.lightLevel;
            };

            handler.getSounds().add(new DeepCave(handler.getPlayer(), validCondition, () -> SOUND));
        }
    }
}
