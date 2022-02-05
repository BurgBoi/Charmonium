package svenhjol.charmonium.module.biome_ambience;

import svenhjol.charmonium.handler.SoundHandler;
import svenhjol.charmonium.helper.RegistryHelper;
import svenhjol.charmonium.helper.WorldHelper;

import java.util.function.Predicate;
import net.minecraft.sound.SoundEvent;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biome.Category;

public class BiomeSounds {
    public static class Beach {
        public static SoundEvent DAY;
        public static SoundEvent NIGHT;

        public static void init(SoundHandler<BiomeSound> handler) {
            DAY = RegistryHelper.sound("ambience.beach.day");
            NIGHT = RegistryHelper.sound("ambience.beach.night");

            Predicate<Biome> biomeCondition = biome
                -> biome.getCategory() == Category.BEACH && WorldHelper.isOutside(handler.getPlayer());

            handler.getSounds().add(new BiomeSound(handler.getPlayer(), biomeCondition.and(b -> WorldHelper.isDay(handler.getPlayer())), () -> DAY));
            handler.getSounds().add(new BiomeSound(handler.getPlayer(), biomeCondition.and(b -> WorldHelper.isNight(handler.getPlayer())), () -> NIGHT));
        }
    }

    public static class Badlands {
        public static SoundEvent DAY;
        public static SoundEvent NIGHT;

        public static void init(SoundHandler<BiomeSound> handler) {
            DAY = RegistryHelper.sound("ambience.badlands.day");
            NIGHT = RegistryHelper.sound("ambience.badlands.night");

            Predicate<Biome> biomeCondition = biome
                -> biome.getCategory() == Category.MESA && WorldHelper.isOutside(handler.getPlayer());

            handler.getSounds().add(new BiomeSound(handler.getPlayer(), biomeCondition.and(b -> WorldHelper.isDay(handler.getPlayer())), () -> DAY));
            handler.getSounds().add(new BiomeSound(handler.getPlayer(), biomeCondition.and(b -> WorldHelper.isNight(handler.getPlayer())), () -> NIGHT));
        }
    }

    public static class Desert {
        public static SoundEvent DAY;
        public static SoundEvent NIGHT;

        public static void init(SoundHandler<BiomeSound> handler) {
            DAY = RegistryHelper.sound("ambience.desert.day");
            NIGHT = RegistryHelper.sound("ambience.desert.night");

            Predicate<Biome> biomeCondition = biome
                -> biome.getCategory() == Category.DESERT && WorldHelper.isOutside(handler.getPlayer());

            handler.getSounds().add(new BiomeSound(handler.getPlayer(), biomeCondition.and(b -> WorldHelper.isDay(handler.getPlayer())), () -> DAY));
            handler.getSounds().add(new BiomeSound(handler.getPlayer(), biomeCondition.and(b -> WorldHelper.isNight(handler.getPlayer())), () -> NIGHT));
        }
    }

    public static class Forest {
        public static SoundEvent DAY;
        public static SoundEvent NIGHT;

        public static void init(SoundHandler<BiomeSound> handler) {
            DAY = RegistryHelper.sound("ambience.forest.day");
            NIGHT = RegistryHelper.sound("ambience.forest.night");

            Predicate<Biome> biomeCondition = biome
                -> biome.getCategory() == Category.FOREST && WorldHelper.isOutside(handler.getPlayer());

            handler.getSounds().add(new BiomeSound(handler.getPlayer(), biomeCondition.and(b -> WorldHelper.isDay(handler.getPlayer())), () -> DAY));
            handler.getSounds().add(new BiomeSound(handler.getPlayer(), biomeCondition.and(b -> WorldHelper.isNight(handler.getPlayer())), () -> NIGHT));
        }
    }

    public static class Icy {
        public static SoundEvent DAY;
        public static SoundEvent NIGHT;

        public static void init(SoundHandler<BiomeSound> handler) {
            DAY = RegistryHelper.sound("ambience.icy.day");
            NIGHT = RegistryHelper.sound("ambience.icy.night");

            Predicate<Biome> biomeCondition = biome
                -> biome.getCategory() == Category.ICY && WorldHelper.isOutside(handler.getPlayer());

            handler.getSounds().add(new BiomeSound(handler.getPlayer(), biomeCondition.and(b -> WorldHelper.isDay(handler.getPlayer())), () -> DAY));
            handler.getSounds().add(new BiomeSound(handler.getPlayer(), biomeCondition.and(b -> WorldHelper.isNight(handler.getPlayer())), () -> NIGHT));
        }
    }

    public static class Jungle {
        public static SoundEvent DAY;
        public static SoundEvent NIGHT;

        public static void init(SoundHandler<BiomeSound> handler) {
            DAY = RegistryHelper.sound("ambience.jungle.day");
            NIGHT = RegistryHelper.sound("ambience.jungle.night");

            Predicate<Biome> biomeCondition = biome
                -> biome.getCategory() == Category.JUNGLE && WorldHelper.isOutside(handler.getPlayer());

            handler.getSounds().add(new BiomeSound(handler.getPlayer(), biomeCondition.and(b -> WorldHelper.isDay(handler.getPlayer())), () -> DAY));
            handler.getSounds().add(new BiomeSound(handler.getPlayer(), biomeCondition.and(b -> WorldHelper.isNight(handler.getPlayer())), () -> NIGHT));
        }
    }

    public static class Mountains {
        public static SoundEvent DAY;
        public static SoundEvent NIGHT;

        public static void init(SoundHandler<BiomeSound> handler) {
            DAY = RegistryHelper.sound("ambience.mountains.day");
            NIGHT = RegistryHelper.sound("ambience.mountains.night");

            Predicate<Biome> biomeCondition = biome
                -> biome.getCategory() == Category.EXTREME_HILLS && WorldHelper.isOutside(handler.getPlayer());

            handler.getSounds().add(new BiomeSound(handler.getPlayer(), biomeCondition.and(b -> WorldHelper.isDay(handler.getPlayer())), () -> DAY));
            handler.getSounds().add(new BiomeSound(handler.getPlayer(), biomeCondition.and(b -> WorldHelper.isNight(handler.getPlayer())), () -> NIGHT));
        }
    }

    public static class Ocean {
        public static SoundEvent DAY;
        public static SoundEvent NIGHT;

        public static void init(SoundHandler<BiomeSound> handler) {
            DAY = RegistryHelper.sound("ambience.ocean.day");
            NIGHT = RegistryHelper.sound("ambience.ocean.night");

            Predicate<Biome> biomeCondition = biome
                -> biome.getCategory() == Category.OCEAN && WorldHelper.isOutside(handler.getPlayer());

            handler.getSounds().add(new BiomeSound(handler.getPlayer(), biomeCondition.and(b -> WorldHelper.isDay(handler.getPlayer())), () -> DAY));
            handler.getSounds().add(new BiomeSound(handler.getPlayer(), biomeCondition.and(b -> WorldHelper.isNight(handler.getPlayer())), () -> NIGHT));
        }
    }

    public static class Plains {
        public static SoundEvent DAY;
        public static SoundEvent NIGHT;

        public static void init(SoundHandler<BiomeSound> handler) {
            DAY = RegistryHelper.sound("ambience.plains.day");
            NIGHT = RegistryHelper.sound("ambience.plains.night");

            Predicate<Biome> biomeCondition = biome
                -> (biome.getCategory() == Category.PLAINS
                    || biome.getCategory() == Category.RIVER)
                && WorldHelper.isOutside(handler.getPlayer());

            handler.getSounds().add(new BiomeSound(handler.getPlayer(), biomeCondition.and(b -> WorldHelper.isDay(handler.getPlayer())), () -> DAY));
            handler.getSounds().add(new BiomeSound(handler.getPlayer(), biomeCondition.and(b -> WorldHelper.isNight(handler.getPlayer())), () -> NIGHT));
        }
    }

    public static class Savanna {
        public static SoundEvent DAY;
        public static SoundEvent NIGHT;

        public static void init(SoundHandler<BiomeSound> handler) {
            DAY = RegistryHelper.sound("ambience.savanna.day");
            NIGHT = RegistryHelper.sound("ambience.savanna.night");

            Predicate<Biome> biomeCondition = biome
                -> biome.getCategory() == Category.SAVANNA && WorldHelper.isOutside(handler.getPlayer());

            handler.getSounds().add(new BiomeSound(handler.getPlayer(), biomeCondition.and(b -> WorldHelper.isDay(handler.getPlayer())), () -> DAY));
            handler.getSounds().add(new BiomeSound(handler.getPlayer(), biomeCondition.and(b -> WorldHelper.isNight(handler.getPlayer())), () -> NIGHT));
        }
    }

    public static class Swamp {
        public static SoundEvent DAY;
        public static SoundEvent NIGHT;

        public static void init(SoundHandler<BiomeSound> handler) {
            DAY = RegistryHelper.sound("ambience.swamp.day");
            NIGHT = RegistryHelper.sound("ambience.swamp.night");

            Predicate<Biome> biomeCondition = biome
                -> (biome.getCategory() == Category.SWAMP
                    || biome.getCategory() == Category.MUSHROOM)
                && WorldHelper.isOutside(handler.getPlayer());

            handler.getSounds().add(new BiomeSound(handler.getPlayer(), biomeCondition.and(b -> WorldHelper.isDay(handler.getPlayer())), () -> DAY));
            handler.getSounds().add(new BiomeSound(handler.getPlayer(), biomeCondition.and(b -> WorldHelper.isNight(handler.getPlayer())), () -> NIGHT));
        }
    }

    public static class Taiga {
        public static SoundEvent DAY;
        public static SoundEvent NIGHT;

        public static void init(SoundHandler<BiomeSound> handler) {
            DAY = RegistryHelper.sound("ambience.taiga.day");
            NIGHT = RegistryHelper.sound("ambience.taiga.night");

            Predicate<Biome> biomeCondition = biome
                -> biome.getCategory() == Category.TAIGA && WorldHelper.isOutside(handler.getPlayer());

            handler.getSounds().add(new BiomeSound(handler.getPlayer(), biomeCondition.and(b -> WorldHelper.isDay(handler.getPlayer())), () -> DAY));
            handler.getSounds().add(new BiomeSound(handler.getPlayer(), biomeCondition.and(b -> WorldHelper.isNight(handler.getPlayer())), () -> NIGHT));
        }
    }

    public static class TheEnd {
        public static SoundEvent SOUND;

        public static void init(SoundHandler<BiomeSound> handler) {
            SOUND = RegistryHelper.sound("ambience.the_end");

            Predicate<Biome> biomeCondition = biome
                -> biome.getCategory() == Category.THEEND;

            handler.getSounds().add(new BiomeSound(handler.getPlayer(), biomeCondition, () -> SOUND));
        }
    }
}
