package svenhjol.charmonium.handler;

import org.jetbrains.annotations.NotNull;
import svenhjol.charmonium.iface.IAmbientSound;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.entity.player.PlayerEntity;

public class SoundHandler<T extends IAmbientSound> {
    private PlayerEntity player;
    private final List<T> sounds = new ArrayList<>();

    public SoundHandler(@NotNull PlayerEntity player) {
        updatePlayer(player);
    }

    public void updatePlayer(@NotNull PlayerEntity player) {
        this.player = player;
        sounds.forEach(s -> s.updatePlayer(this.player));
    }

    public List<T> getSounds() {
        return sounds;
    }

    public PlayerEntity getPlayer() {
        return player;
    }

    public void tick() {
        if (player.world == null) return;
        sounds.forEach(T::tick);
    }

    public void stop() {
        sounds.forEach(T::stop);
    }
}
