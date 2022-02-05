package svenhjol.charmonium.module.situational_ambience.sounds;

import svenhjol.charmonium.handler.SoundHandler;
import svenhjol.charmonium.helper.RegistryHelper;
import svenhjol.charmonium.module.situational_ambience.SituationalSound;

import java.util.function.Function;
import java.util.function.Predicate;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.world.biome.Biome;

public class AlienSound extends SituationalSound {
    public static SoundEvent SOUND;

    private AlienSound(PlayerEntity player, Predicate<SituationalSound> validCondition, Function<SituationalSound, SoundEvent> soundCondition) {
        super(player, validCondition, soundCondition);
    }

    public static void init(SoundHandler<SituationalSound> handler) {
        SOUND = RegistryHelper.sound("situational.alien");

        Predicate<SituationalSound> validCondition = situation -> {
            ClientWorld level = situation.getLevel();
            PlayerEntity player = situation.getPlayer();
            return level.getBiome(player.getBlockPos()).getCategory() == Biome.Category.THEEND;
        };

        Function<SituationalSound, SoundEvent> soundCondition = situation -> SOUND;
        handler.getSounds().add(new AlienSound(handler.getPlayer(), validCondition, soundCondition));
    }

    @Override
    public int getDelay() {
        return level.random.nextInt(400) + 300;
    }

    @Override
    public float getVolume() {
        return 0.85F;
    }
}