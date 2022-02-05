package svenhjol.charmonium.module.player_state;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;
import svenhjol.charm.api.CharmNetworkReferences;
import svenhjol.charm.api.CharmPlayerStateKeys;
import svenhjol.charmonium.Charmonium;
import svenhjol.charmonium.annotation.ClientModule;
import svenhjol.charmonium.helper.NetworkHelper;
import svenhjol.charmonium.loader.CharmModule;

@ClientModule(mod = Charmonium.MOD_ID, description = "Updates player state from Charm, if present.", alwaysEnabled = true)
public class PlayerState extends CharmModule {
    private static final Identifier MSG_CLIENT = new Identifier(CharmNetworkReferences.ClientUpdatePlayerState.asString());

    // special state properties fetched from server
    public static boolean insideOverworldRuin;

    @Override
    public void register() {
        // listen to network requests from Charm
        ClientPlayNetworking.registerGlobalReceiver(MSG_CLIENT, this::handleUpdatePlayerState);
    }

    private void handleUpdatePlayerState(MinecraftClient client, ClientPlayNetworkHandler handler, PacketByteBuf data, PacketSender sender) {
        NbtCompound nbt = NetworkHelper.decodeNbt(data);
        if (nbt == null) return;

        client.execute(()
            -> insideOverworldRuin = nbt.getBoolean(CharmPlayerStateKeys.InsideOverworldRuin.toString()));
    }
}
