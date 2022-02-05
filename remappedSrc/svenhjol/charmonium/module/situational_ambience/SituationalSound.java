package svenhjol.charmonium.module.situational_ambience;

import org.jetbrains.annotations.Nullable;
import svenhjol.charmonium.helper.LogHelper;
import svenhjol.charmonium.iface.IAmbientSound;
import svenhjol.charmonium.sounds.SingleSound;

import java.util.ConcurrentModificationException;
import java.util.function.Function;
import java.util.function.Predicate;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.sound.SoundManager;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.BlockPos;

public class SituationalSound implements IAmbientSound {
    protected MinecraftClient client;
    protected PlayerEntity player;
    protected ClientWorld level;
    protected Function<SituationalSound, SoundEvent> soundCondition;
    protected Predicate<SituationalSound> validCondition;
    protected BlockPos pos;
    protected SingleSound soundInstance;

    protected int soundTicks = 100; // set something high here so it doesn't autoplay when player logs in
    protected boolean isValid;
    protected boolean playUnderWater = false;

    public SituationalSound(PlayerEntity player, Predicate<SituationalSound> validCondition, Function<SituationalSound, SoundEvent> soundCondition) {
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
    public SingleSound getSoundInstance() {
        return soundInstance;
    }

    @Override
    public boolean isValid() {
        // initial filters
        if (client.world == null || level == null) return false;
        if (!player.isAlive()) return false;
        if (player.isSubmergedInWater() && !playUnderWater) return false;

        return validCondition.test(this);
    }

    @Override
    public void tick() {
        if (--soundTicks >= 0)
            return;

        soundTicks = getDelay();
        isValid = isValid();

        if (isValid) {
            soundInstance = new SingleSound(getPlayer(), getSound(), getVolume() * getVolumeScaling(), getPitch(), getPos());
            SoundManager manager = getSoundManager();

            try {
                if (!manager.isPlaying(soundInstance))
                    manager.play(soundInstance);
            } catch (ConcurrentModificationException e) {
                LogHelper.debug(this.getClass(), "Exception in manager.play");
            }
        }
    }

    @Nullable
    @Override
    public SoundEvent getSound() {
        return soundCondition.apply(this);
    }

    @Override
    public int getDelay() {
        return level.random.nextInt(200) + 200;
    }

    @Override
    public float getVolumeScaling() {
        return SituationalAmbience.volumeScaling;
    }

    public void setPos(BlockPos pos) {
        this.pos = pos;
    }

    @Nullable
    public BlockPos getPos() {
        return pos;
    }
}
