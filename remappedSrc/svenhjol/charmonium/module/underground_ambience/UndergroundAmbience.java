package svenhjol.charmonium.module.underground_ambience;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientEntityEvents;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import svenhjol.charmonium.Charmonium;
import svenhjol.charmonium.annotation.ClientModule;
import svenhjol.charmonium.annotation.Config;
import svenhjol.charmonium.handler.SoundHandler;
import svenhjol.charmonium.loader.CharmModule;
import svenhjol.charmonium.module.underground_ambience.UndergroundSounds.Cave;
import svenhjol.charmonium.module.underground_ambience.UndergroundSounds.DeepCave;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SuppressWarnings("ArraysAsListWithZeroOrOneArgument")
@ClientModule(mod = Charmonium.MOD_ID, description = "Plays ambient background sound when the player is underground at different depths and light levels.")
public class UndergroundAmbience extends CharmModule {
    public Handler handler;

    @Config(name = "Volume scaling", description = "Affects the volume of all underground ambient sounds. 1.0 is full volume.")
    public static float volumeScaling = 0.55F;

    @Config(name = "Valid dimensions", description = "Dimensions in which underground ambience will be played.")
    public static List<String> configDimensions = Arrays.asList(
        "minecraft:overworld"
    );

    @Config(name = "Cave ambience", description = "If true, the cave ambience will be played. Cave ambience is normally a low frequency drone.")
    public static boolean playCaveAmbience = true;

    @Config(name = "Deepcave ambience", description = "If true, the deepcave ambience will be played. This takes effect when the player is deep underground and at low light level.")
    public static boolean playDeepCaveAmbience = true;

    @Config(name = "Cave depth", description = "When the player is lower than this depth then cave background sound will be always be triggered.")
    public static int caveDepth = 48;

    @Config(name = "Light level", description = "When the light is lower than this level then cave and deepcave background sound will be triggered.")
    public static int lightLevel = 10;

    public static List<Identifier> validDimensions = new ArrayList<>();

    @Override
    public void runWhenEnabled() {
        configDimensions.forEach(dim -> validDimensions.add(new Identifier(dim)));

        ClientEntityEvents.ENTITY_LOAD.register(this::handleEntityLoad);
        ClientEntityEvents.ENTITY_UNLOAD.register(this::handleEntityUnload);
        ClientTickEvents.END_CLIENT_TICK.register(this::handleClientTick);
    }

    private void handleEntityLoad(Entity entity, World level) {
        if (entity instanceof PlayerEntity)
            trySetupSoundHandler((PlayerEntity)entity);
    }

    private void handleEntityUnload(Entity entity, World level) {
        if (entity instanceof ClientPlayerEntity && handler != null)
            handler.stop();
    }

    private void handleClientTick(MinecraftClient client) {
        if (handler != null && !client.isPaused())
            handler.tick();
    }

    public void trySetupSoundHandler(PlayerEntity player) {
        if (!(player instanceof ClientPlayerEntity)) return;

        if (handler == null)
            handler = new Handler(player);

        handler.updatePlayer(player);
    }

    public static class Handler extends SoundHandler<UndergroundSound> {
        public Handler(@NotNull PlayerEntity player) {
            super(player);

            Cave.init(this);
            DeepCave.init(this);
        }
    }
}
