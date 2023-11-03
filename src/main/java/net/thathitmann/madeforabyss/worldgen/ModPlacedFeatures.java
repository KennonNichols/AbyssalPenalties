package net.thathitmann.madeforabyss.worldgen;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.LayerConfiguration;
import net.minecraft.world.level.levelgen.placement.*;
import net.thathitmann.madeforabyss.MadeForAbyss;

import java.util.List;

public class ModPlacedFeatures {

    public static final ResourceKey<PlacedFeature> COAL_IN_STONE_PLACED_KEY = createKey("coal_ore_in_stone_placed");
    public static final ResourceKey<PlacedFeature> COPPER_IN_STONE_PLACED_KEY = createKey("copper_ore_in_stone_placed");
    public static final ResourceKey<PlacedFeature> IRON_IN_STONE_PLACED_KEY = createKey("iron_ore_in_stone_placed");
    public static final ResourceKey<PlacedFeature> LAPIS_IN_STONE_PLACED_KEY = createKey("lapis_ore_in_stone_placed");
    public static final ResourceKey<PlacedFeature> REDSTONE_IN_STONE_PLACED_KEY = createKey("redstone_ore_in_stone_placed");
    public static final ResourceKey<PlacedFeature> GOLD_IN_DEEPSLATE_PLACED_KEY = createKey("gold_ore_in_deepslate_placed");
    public static final ResourceKey<PlacedFeature> DIAMOND_IN_DEEPSLATE_PLACED_KEY = createKey("diamond_ore_in_deepslate_placed");
    public static final ResourceKey<PlacedFeature> EMERALD_IN_DEEPSLATE_PLACED_KEY = createKey("emerald_ore_in_deepslate_placed");
    public static final ResourceKey<PlacedFeature> GOLD_IN_NETHERRACK_PLACED_KEY = createKey("gold_ore_in_netherrack_placed");
    public static final ResourceKey<PlacedFeature> GLOWSTONE_IN_NETHERRACK_PLACED_KEY = createKey("glowstone_in_netherrack_placed");
    public static final ResourceKey<PlacedFeature> QUARTZ_IN_NETHERRACK_PLACED_KEY = createKey("quartz_ore_in_netherrack_placed");
    public static final ResourceKey<PlacedFeature> DEBRIS_IN_NETHERRACK_PLACED_KEY = createKey("debris_in_netherrack_placed");





    private static final ResourceKey<PlacedFeature>[] bedrocks = new ResourceKey[] {
        createKey("second_bedrock_placed"),
        createKey("third_bedrock_placed"),
        createKey("fourth_bedrock_placed"),
        createKey("fifth_bedrock_placed"),
        createKey("sixth_bedrock_placed"),
        createKey("seventh_bedrock_placed"),
        createKey("eighth_bedrock_placed"),
        createKey("ninth_bedrock_placed"),
        createKey("tenth_bedrock_placed"),
    };


    public static void bootstrap(BootstapContext<PlacedFeature> context) {
        HolderGetter<ConfiguredFeature<?, ?>> configuredFeatures = context.lookup(Registries.CONFIGURED_FEATURE);


        //Ores
        register(context, COAL_IN_STONE_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.COAL_IN_STONE_KEY),
                ModOrePlacement.commonOrePlacement(30,
                        HeightRangePlacement.uniform(VerticalAnchor.absolute(-100), VerticalAnchor.absolute(63))));
        register(context, COPPER_IN_STONE_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.COPPER_IN_STONE_KEY),
                ModOrePlacement.commonOrePlacement(30,
                        HeightRangePlacement.uniform(VerticalAnchor.absolute(-50), VerticalAnchor.absolute(0))));
        register(context, IRON_IN_STONE_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.IRON_IN_STONE_KEY),
                ModOrePlacement.commonOrePlacement(16,
                        HeightRangePlacement.uniform(VerticalAnchor.absolute(-200), VerticalAnchor.absolute(-50))));
        register(context, LAPIS_IN_STONE_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.LAPIS_IN_STONE_KEY),
                ModOrePlacement.commonOrePlacement(6,
                        HeightRangePlacement.uniform(VerticalAnchor.absolute(-150), VerticalAnchor.absolute(-100))));
        register(context, REDSTONE_IN_STONE_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.REDSTONE_IN_STONE_KEY),
                ModOrePlacement.commonOrePlacement(10,
                        HeightRangePlacement.uniform(VerticalAnchor.absolute(-250), VerticalAnchor.absolute(-150))));
        register(context, GOLD_IN_DEEPSLATE_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.GOLD_IN_DEEPSLATE_KEY),
                ModOrePlacement.commonOrePlacement(10,
                        HeightRangePlacement.uniform(VerticalAnchor.absolute(-350), VerticalAnchor.absolute(-250))));
        register(context, DIAMOND_IN_DEEPSLATE_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.DIAMOND_IN_DEEPSLATE_KEY),
                ModOrePlacement.commonOrePlacement(3,
                        HeightRangePlacement.uniform(VerticalAnchor.absolute(-350), VerticalAnchor.absolute(-300))));
        register(context, EMERALD_IN_DEEPSLATE_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.EMERALD_IN_DEEPSLATE_KEY),
                ModOrePlacement.commonOrePlacement(3,
                        HeightRangePlacement.uniform(VerticalAnchor.absolute(-400), VerticalAnchor.absolute(-350))));
        register(context, GOLD_IN_NETHERRACK_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.GOLD_IN_NETHERRACK_KEY),
                ModOrePlacement.commonOrePlacement(15,
                        HeightRangePlacement.uniform(VerticalAnchor.absolute(-512), VerticalAnchor.absolute(-400))));
        register(context, GLOWSTONE_IN_NETHERRACK_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.GLOWSTONE_IN_NETHERRACK_KEY),
                ModOrePlacement.commonOrePlacement(20,
                        HeightRangePlacement.uniform(VerticalAnchor.absolute(-450), VerticalAnchor.absolute(-400))));
        register(context, QUARTZ_IN_NETHERRACK_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.QUARTZ_IN_NETHERRACK_KEY),
                ModOrePlacement.commonOrePlacement(20,
                        HeightRangePlacement.uniform(VerticalAnchor.absolute(-475), VerticalAnchor.absolute(-400))));
        register(context, DEBRIS_IN_NETHERRACK_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.DEBRIS_IN_NETHERRACK_KEY),
                ModOrePlacement.commonOrePlacement(1,
                        HeightRangePlacement.uniform(VerticalAnchor.absolute(-512), VerticalAnchor.absolute(-450))));


        //region bedrock because minecraft has a shitass fill system

        //Second bedrock
        for (ResourceKey<PlacedFeature> key : bedrocks) {
            register(context, key, configuredFeatures.getOrThrow(ModConfiguredFeatures.SECOND_BEDROCK),
                    CountPlacement.of(256),
                    InSquarePlacement.spread(),
                    HeightRangePlacement.uniform(VerticalAnchor.absolute(0), VerticalAnchor.absolute(0)),
                    BiomeFilter.biome()
            );
        }

        //endregion
    }

    public static ResourceKey<PlacedFeature> createKey(String name) {
        return ResourceKey.create(Registries.PLACED_FEATURE, new ResourceLocation(MadeForAbyss.MOD_ID, name));
    }


    private static void register(BootstapContext<PlacedFeature> context, ResourceKey<PlacedFeature> key, Holder<ConfiguredFeature<?, ?>> configuration, List<PlacementModifier> modifiers) {
        context.register(key, new PlacedFeature(configuration, List.copyOf(modifiers)));
    }

    private static void register(BootstapContext<PlacedFeature> context, ResourceKey<PlacedFeature> key, Holder<ConfiguredFeature<?, ?>> configuration, PlacementModifier... modifiers) {
        register(context, key, configuration, List.of(modifiers));
    }

}
