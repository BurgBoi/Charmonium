package svenhjol.charmonium.mixin.extra_music;

import net.minecraft.client.MinecraftClient;
import net.minecraft.sound.MusicSound;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import svenhjol.charmonium.module.extra_music.ExtraMusic;

@Mixin(MinecraftClient.class)
public class TryCustomMusicMixin {
    @Inject(
        method = "getSituationalMusic",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/level/Level;getBiome(Lnet/minecraft/core/BlockPos;)Lnet/minecraft/world/level/biome/Biome;"
        ),
        cancellable = true
    )
    private void hookSituationalMusic(CallbackInfoReturnable<MusicSound> cir) {
        if (ExtraMusic.isEnabled) {
            MusicSound music = ExtraMusic.getMusic();
            if (music != null)
                cir.setReturnValue(music);
        }
    }
}
