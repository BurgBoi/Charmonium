package svenhjol.charmonium.module.situational_ambience.sounds;

import svenhjol.charmonium.handler.SoundHandler;
import svenhjol.charmonium.helper.RegistryHelper;
import svenhjol.charmonium.helper.WorldHelper;
import svenhjol.charmonium.module.situational_ambience.SituationalSound;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;

public class MansionSound extends SituationalSound {
    public static SoundEvent SOUND;
    public static int baseDelay = 0;

    private MansionSound(PlayerEntity player, Predicate<SituationalSound> validCondition, Function<SituationalSound, SoundEvent> soundCondition) {
        super(player, validCondition, soundCondition);
    }

    public static void init(SoundHandler<SituationalSound> handler) {
        SOUND = RegistryHelper.sound("situational.mansion");

        Predicate<SituationalSound> validCondition = situation -> {
            PlayerEntity player = situation.getPlayer();
            ClientWorld level = situation.getLevel();

            if (WorldHelper.isBelowSeaLevel(player)) return false;

            if (!level.getBiome(player.getBlockPos()).getGenerationSettings().isValidStart(StructureFeatures.WOODLAND_MANSION.feature)) {
                baseDelay = 600; // potentially an expensive check, increase check delay to 30 seconds
                return false;
            }

            baseDelay = 120;

            Box bb = new Box(player.getBlockPos()).expand(10);
            List<HostileEntity> monsters = level.getNonSpectatingEntities(HostileEntity.class, bb);

            Optional<BlockPos> optBlock1 = BlockPos.findClosest(player.getBlockPos(), 8, 8, pos -> {
                Block block = level.getBlockState(pos).getBlock();
                return block == Blocks.DARK_OAK_PLANKS;
            });

            Optional<BlockPos> optBlock2 = BlockPos.findClosest(player.getBlockPos(), 8, 8, pos -> {
                Block block = level.getBlockState(pos).getBlock();
                return block == Blocks.BIRCH_PLANKS;
            });

            if (optBlock1.isPresent() && optBlock2.isPresent()) {
                // get a hostile mob's location as the source of the sound
                Optional<HostileEntity> optMonster = monsters.stream().findAny();
                if (optMonster.isPresent()) {
                    situation.setPos(optMonster.get().getBlockPos());
                    return true;
                }
            }

            return false;
        };

        Function<SituationalSound, SoundEvent> soundCondition = situation -> SOUND;
        handler.getSounds().add(new MansionSound(handler.getPlayer(), validCondition, soundCondition));
    }

    @Override
    public int getDelay() {
        return level.random.nextInt(200) + baseDelay;
    }

    @Override
    public float getVolume() {
        return 0.82F;
    }
}