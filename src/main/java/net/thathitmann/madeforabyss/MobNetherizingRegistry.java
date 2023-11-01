package net.thathitmann.madeforabyss;

import net.minecraft.world.entity.EntityType;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public abstract class MobNetherizingRegistry {

    private static Random random = new Random();

    private record MobNetherizingConversion(EntityType inType, EntityType outType, int weight) { }

    private static MobNetherizingConversion[] conversions = {
            new MobNetherizingConversion(EntityType.ZOMBIE, EntityType.ZOMBIFIED_PIGLIN, 3),
            new MobNetherizingConversion(EntityType.ZOMBIE, EntityType.PIGLIN, 2),
            new MobNetherizingConversion(EntityType.ZOMBIE, EntityType.PIGLIN_BRUTE, 1),
            new MobNetherizingConversion(EntityType.SKELETON, EntityType.WITHER_SKELETON, 1),
            new MobNetherizingConversion(EntityType.SLIME, EntityType.MAGMA_CUBE, 1),
            new MobNetherizingConversion(EntityType.SPIDER, EntityType.ZOMBIFIED_PIGLIN, 1),
            new MobNetherizingConversion(EntityType.WITCH, EntityType.BLAZE, 1),
            new MobNetherizingConversion(EntityType.BAT, null, 1),
            new MobNetherizingConversion(EntityType.CREEPER, EntityType.BLAZE, 1),
    };



    public static boolean isMobNetherizable(EntityType type) {
        for (MobNetherizingConversion conversion : conversions) {
            if (conversion.inType == type) {return true;}
        }
        return false;
    }

    private static List<MobNetherizingConversion> getAllConversions(EntityType type) {
        List<MobNetherizingConversion> conversionCandidates = new ArrayList<>();
        for (MobNetherizingConversion conversion : conversions) {
            if (conversion.inType == type) {conversionCandidates.add(conversion);}
        }
        return conversionCandidates;
    }

    public static EntityType getNetherizedMob(EntityType type) {
        List<MobNetherizingConversion> conversionCandidates = getAllConversions(type);

        int maxWeight = 0;
        for (MobNetherizingConversion conversion : conversionCandidates) {
            maxWeight += conversion.weight;
        }
        int weightMarker = random.nextInt(maxWeight);
        int currentWeight = 0;
        for (MobNetherizingConversion conversion : conversionCandidates) {
            currentWeight += conversion.weight;
            if (currentWeight >= weightMarker) {return conversion.outType;}
        }
        return null;
        //throw new Exception("I goofed it somehow");
    }

}
