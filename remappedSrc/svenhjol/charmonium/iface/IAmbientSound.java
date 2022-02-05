package svenhjol.charmonium.iface;

import javax.annotation.Nullable;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.sound.MovingSoundInstance;
import net.minecraft.client.sound.SoundManager;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundEvent;

@SuppressWarnings("BooleanMethodIsAlwaysInverted")
public interface IAmbientSound {
    ClientWorld getLevel();

    PlayerEntity getPlayer();

    MovingSoundInstance getSoundInstance();

    boolean isValid();

    void tick();

    void updatePlayer(PlayerEntity player);

    default SoundManager getSoundManager() {
        return MinecraftClient.getInstance().getSoundManager();
    }

    default boolean isPlaying() {
        return getSoundInstance() != null && getSoundManager().isPlaying(getSoundInstance());
    }

    default void stop() {
        getSoundManager().stop(getSoundInstance());
    }

    default int getDelay() { return 0; }

    default float getVolume() { return 1.0F; }

    default float getVolumeScaling() { return 0.55F; }

    default float getPitch() { return 1.0F; }

    @Nullable
    default SoundEvent getSound() { return null; }
}
