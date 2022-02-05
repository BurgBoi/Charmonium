package svenhjol.charmonium.module.extra_music;

import svenhjol.charmonium.Charmonium;
import svenhjol.charmonium.annotation.ClientModule;
import svenhjol.charmonium.annotation.Config;
import svenhjol.charmonium.helper.DimensionHelper;
import svenhjol.charmonium.helper.RegistryHelper;
import svenhjol.charmonium.loader.CharmModule;
import svenhjol.charmonium.module.player_state.PlayerState;

import javax.annotation.Nullable;
import net.minecraft.client.MinecraftClient;
import net.minecraft.sound.MusicSound;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.biome.Biome;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Predicate;

@SuppressWarnings("unused")
@ClientModule(mod = Charmonium.MOD_ID, description = "Plays custom music tracks according to the location of the player.")
public class ExtraMusic extends CharmModule {
    private static final List<MusicCondition> musicConditions = new ArrayList<>();
    public static boolean isEnabled;

    public static SoundEvent MUSIC_OVERWORLD;
    public static SoundEvent MUSIC_COLD;
    public static SoundEvent MUSIC_NETHER;
    public static SoundEvent MUSIC_RUIN;

    @Config(name = "Play Creative music", description = "If true, the six Creative music tracks may play in survival mode.")
    public static boolean playCreativeMusic = true;

    @Config(name = "Play Overworld music", description = "If true, custom overworld-themed tracks may play.")
    public static boolean playOverworldMusic = true;

    @Config(name = "Play Nether music", description = "If true, custom nether-themed tracks may play.")
    public static boolean playNetherMusic = true;

    @Config(name = "Play Ruin music", description = "If true, underground ruin-themed tracks may play.  This requires Charm on the server.")
    public static boolean playRuinMusic = true;

    @Override
    public void register() {
        MUSIC_OVERWORLD = RegistryHelper.sound("music.overworld");
        MUSIC_NETHER = RegistryHelper.sound("music.nether");
        MUSIC_COLD = RegistryHelper.sound("music.cold");
        MUSIC_RUIN = RegistryHelper.sound("music.ruin");
    }

    @Override
    public void runWhenEnabled() {
        // static boolean for mixins to check
        isEnabled = Charmonium.LOADER.isEnabled("extra_music");

        // overworld music
        if (playOverworldMusic) {
            getMusicConditions().add(new MusicCondition(MUSIC_OVERWORLD, 1200, 3600, mc ->
                mc.player != null
                    && DimensionHelper.isOverworld(mc.player.world)
                    && mc.player.world.random.nextFloat() < 0.08F
            ));

            // cold music, look for player pos in icy biome
            getMusicConditions().add(new MusicCondition(MUSIC_COLD, 1200, 3600, mc ->
                mc.player != null
                    && mc.player.world.getBiome(mc.player.getBlockPos()).getCategory() == Biome.Category.ICY
                    && mc.player.world.random.nextFloat() < 0.2F
            ));
        }

        // nether music, look for player at low position in the nether
        if (playNetherMusic) {
            getMusicConditions().add(new MusicCondition(MUSIC_NETHER, 1200, 3600, mc ->
                mc.player != null
                    && DimensionHelper.isNether(mc.player.world)
                    && mc.player.getBlockPos().getY() < 48
                    && mc.player.world.random.nextFloat() < 0.33F
            ));
        }

        // ruin music
        if (playRuinMusic) {
            getMusicConditions().add(new MusicCondition(MUSIC_RUIN, 1200, 3600, mc ->
                mc.player != null
                    && PlayerState.insideOverworldRuin
                    && mc.player.world.random.nextFloat() < 0.33F
            ));
        }

        // creative tracks in survival mode
        if (playCreativeMusic) {
            getMusicConditions().add(new MusicCondition(SoundEvents.MUSIC_CREATIVE, 1200, 3600, mc ->
                mc.player != null
                    && (!mc.player.isCreative() || !mc.player.isSpectator())
                    && DimensionHelper.isOverworld(mc.player.world)
                    && new Random().nextFloat() < 0.25F
            ));
        }
    }

    @Nullable
    public static MusicSound getMusic() {
        for (MusicCondition c : musicConditions) {
            if (c.handle())
                return c.getMusic();
        }

        return null;
    }

    public static List<MusicCondition> getMusicConditions() {
        return musicConditions;
    }

    public static class MusicCondition {
        private final SoundEvent sound;
        private final int minDelay;
        private final int maxDelay;
        private Predicate<MinecraftClient> condition;

        public MusicCondition(SoundEvent sound, int minDelay, int maxDelay, Predicate<MinecraftClient> condition) {
            this.sound = sound;
            this.minDelay = minDelay;
            this.maxDelay = maxDelay;
            this.condition = condition;
        }

        public MusicCondition(MusicSound music) {
            this.sound = music.getSound();
            this.minDelay = music.getMinDelay();
            this.maxDelay = music.getMaxDelay();
        }

        public boolean handle() {
            if (condition == null) return false;
            return condition.test(MinecraftClient.getInstance());
        }

        public MusicSound getMusic() {
            return new MusicSound(sound, minDelay, maxDelay, false);
        }

        public SoundEvent getSound() {
            return sound;
        }

        public int getMaxDelay() {
            return maxDelay;
        }

        public int getMinDelay() {
            return minDelay;
        }
    }
}
