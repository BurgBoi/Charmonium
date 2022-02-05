package svenhjol.charmonium.mixin.accessor;

import net.minecraft.client.sound.MovingSoundInstance;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(MovingSoundInstance.class)
public interface AbstractTickableSoundInstanceAccessor {
    @Accessor
    void setStopped(boolean flag);

    @Accessor
    boolean getStopped();
}
