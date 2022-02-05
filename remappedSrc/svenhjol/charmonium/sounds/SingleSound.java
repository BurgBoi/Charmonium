package svenhjol.charmonium.sounds;

import net.minecraft.client.sound.MovingSoundInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;
import svenhjol.charmonium.mixin.accessor.AbstractTickableSoundInstanceAccessor;

public class SingleSound extends MovingSoundInstance {
    private final PlayerEntity player;

    public SingleSound(PlayerEntity player, SoundEvent sound, float volume) {
        this(player, sound, volume, 1.0F, null);
    }

    public SingleSound(PlayerEntity player, SoundEvent sound, float volume, float pitch, @Nullable BlockPos pos) {
        super(sound, SoundCategory.AMBIENT);

        this.player = player;
        this.repeat = false;
        this.repeatDelay = 0;
        this.pitch = pitch;
        this.volume = volume;

        if (pos != null) {
            this.x = pos.getX();
            this.y = pos.getY();
            this.z = pos.getZ();
        } else {
            this.relative = true;
        }
    }

    @Override
    public void tick() {
        if (player.world == null || !player.isAlive())
            ((AbstractTickableSoundInstanceAccessor)this).setStopped(true);
    }
}
