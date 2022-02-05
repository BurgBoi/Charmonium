package svenhjol.charmonium.init;

import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.util.Identifier;
import svenhjol.charmonium.Charmonium;

public class CharmoniumPacks {
    public static void init() {
        Identifier packId = new Identifier(Charmonium.MOD_ID, "journeyman");
        FabricLoader.getInstance().getModContainer(Charmonium.MOD_ID).ifPresent(container
            -> ResourceManagerHelper.registerBuiltinResourcePack(packId, container, ResourcePackActivationType.DEFAULT_ENABLED));
    }
}
