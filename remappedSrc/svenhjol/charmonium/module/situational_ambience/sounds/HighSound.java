package svenhjol.charmonium.module.situational_ambience.sounds;

import svenhjol.charmonium.helper.DimensionHelper;
import svenhjol.charmonium.helper.RegistryHelper;
import svenhjol.charmonium.helper.WorldHelper;
import svenhjol.charmonium.handler.SoundHandler;
import svenhjol.charmonium.module.situational_ambience.SituationalSound;

import java.util.function.Function;
import java.util.function.Predicate;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundEvent;

public class HighSound extends SituationalSound {
    public static SoundEvent SOUND;

    private HighSound(PlayerEntity player, Predicate<SituationalSound> validCondition, Function<SituationalSound, SoundEvent> soundCondition) {
        super(player, validCondition, soundCondition);
    }

    public static void init(SoundHandler<SituationalSound> handler) {
        SOUND = RegistryHelper.sound("situational.high");

        Predicate<SituationalSound> validCondition = situation -> {
            PlayerEntity player = situation.getPlayer();
            ClientWorld level = situation.getLevel();

            if (!DimensionHelper.isOverworld(level)) return false;
            if (!WorldHelper.isOutside(player)) return false;

            int top = level.getTopY() > 256 ? 200 : 150;
            return DimensionHelper.isOverworld(level) && player.getBlockPos().getY() > top;
        };

        Function<SituationalSound, SoundEvent> soundCondition = situation -> SOUND;
        handler.getSounds().add(new HighSound(handler.getPlayer(), validCondition, soundCondition));
    }

    @Override
    public int getDelay() {
        return level.random.nextInt(100) + 100;
    }

    @Override
    public float getVolume() {
        return 0.9F;
    }
}