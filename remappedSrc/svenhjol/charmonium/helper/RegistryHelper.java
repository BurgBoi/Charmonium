package svenhjol.charmonium.helper;

import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import svenhjol.charmonium.Charmonium;

/**
 * @version 1.0.0-charmonium
 */
public class RegistryHelper {
    public static SoundEvent sound(String id) {
        Identifier res = new Identifier(Charmonium.MOD_ID, id);
        LogHelper.debug(RegistryHelper.class, "Registering sound `" + res + "`");
        return Registry.register(Registry.SOUND_EVENT, res, new SoundEvent(res));
    }
}
