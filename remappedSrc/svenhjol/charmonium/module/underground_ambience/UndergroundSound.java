package svenhjol.charmonium.module.underground_ambience;

import org.jetbrains.annotations.Nullable;
import svenhjol.charmonium.helper.LogHelper;
import svenhjol.charmonium.iface.IAmbientSound;
import svenhjol.charmonium.sounds.LoopingSound;

import java.util.ConcurrentModificationException;
import java.util.function.Predicate;
import java.util.function.Supplier;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.sound.MovingSoundInstance;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundEvent;

public class UndergroundSound implements IAmbientSound {
    protected MinecraftClient client;
    protected boolean isValid = false;
    protected PlayerEntity player;
    protected ClientWorld level;
    protected LoopingSound soundInstance = null;
    protected Supplier<SoundEvent> soundCondition;
    protected Predicate<UndergroundSound> validCondition;

    protected UndergroundSound(PlayerEntity player, Predicate<UndergroundSound> validCondition, Supplier<SoundEvent> soundCondition) {
        this.client = MinecraftClient.getInstance();
        this.player = player;
        this.level = (ClientWorld) player.world;
        this.soundCondition = soundCondition;
        this.validCondition = validCondition;
    }

    @Override
    public ClientWorld getLevel() {
        return level;
    }

    @Override
    public PlayerEntity getPlayer() {
        return player;
    }

    @Override
    public void updatePlayer(PlayerEntity player) {
        this.player = player;
        this.level = (ClientWorld) player.world;
    }

    @Override
    public MovingSoundInstance getSoundInstance() {
        return soundInstance;
    }

    @Override
    public void tick() {
        boolean nowValid = isValid();

        if (isValid && !nowValid)
            isValid = false;

        if (!isValid && nowValid)
            isValid = true;

        if (isValid && !isPlaying()) {
            soundInstance = new LoopingSound(player, getSound(), getVolume() * getVolumeScaling(), getPitch(), p -> isValid);
            try {
                getSoundManager().play(soundInstance);
            } catch (ConcurrentModificationException e) {
                LogHelper.debug(this.getClass(), "Exception in tick");
            }
        }
    }

    @Override
    public boolean isValid() {
        if (client.world == null || level == null)
            return false;

        if (!player.isAlive())
            return false;

        return validCondition.test(this);
    }

    @Nullable
    @Override
    public SoundEvent getSound() {
        return soundCondition.get();
    }

    @Override
    public float getVolumeScaling() {
        return UndergroundAmbience.volumeScaling;
    }
}
