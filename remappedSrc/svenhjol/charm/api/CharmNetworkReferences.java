package svenhjol.charm.api;

import net.minecraft.util.StringIdentifiable;

/**
 * @version 1.0.1
 */
@SuppressWarnings("unused")
public enum CharmNetworkReferences implements StringIdentifiable {
    ClientUpdatePlayerState("charm:client_update_player_state"),
    ServerUpdatePlayerState("charm:server_update_player_state");

    private final String name;

    CharmNetworkReferences(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }

    @Override
    public String asString() {
        return this.name;
    }
}
