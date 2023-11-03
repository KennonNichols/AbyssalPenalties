package net.thathitmann.madeforabyss.worldgen;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.*;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProviderType;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;
import net.thathitmann.madeforabyss.MadeForAbyss;

import java.security.Provider;

public class ModConfiguredFeatures {


    public static final ResourceKey<ConfiguredFeature<?, ?>> COAL_IN_STONE_KEY = registerKey("coal_ore_in_stone");
    public static final ResourceKey<ConfiguredFeature<?, ?>> COPPER_IN_STONE_KEY = registerKey("copper_ore_in_stone");
    public static final ResourceKey<ConfiguredFeature<?, ?>> IRON_IN_STONE_KEY = registerKey("iron_ore_in_stone");
    public static final ResourceKey<ConfiguredFeature<?, ?>> LAPIS_IN_STONE_KEY = registerKey("lapis_ore_in_stone");
    public static final ResourceKey<ConfiguredFeature<?, ?>> REDSTONE_IN_STONE_KEY = registerKey("redstone_ore_in_stone");
    public static final ResourceKey<ConfiguredFeature<?, ?>> GOLD_IN_DEEPSLATE_KEY = registerKey("gold_ore_in_deepslate");
    public static final ResourceKey<ConfiguredFeature<?, ?>> DIAMOND_IN_DEEPSLATE_KEY = registerKey("diamond_ore_in_deepslate");
    public static final ResourceKey<ConfiguredFeature<?, ?>> EMERALD_IN_DEEPSLATE_KEY = registerKey("emerald_ore_in_deepslate");
    public static final ResourceKey<ConfiguredFeature<?, ?>> GOLD_IN_NETHERRACK_KEY = registerKey("gold_ore_in_netherrack");
    public static final ResourceKey<ConfiguredFeature<?, ?>> GLOWSTONE_IN_NETHERRACK_KEY = registerKey("glowstone_in_netherrack");
    public static final ResourceKey<ConfiguredFeature<?, ?>> QUARTZ_IN_NETHERRACK_KEY = registerKey("quartz_ore_in_netherrack");
    public static final ResourceKey<ConfiguredFeature<?, ?>> DEBRIS_IN_NETHERRACK_KEY = registerKey("debris_in_netherrack");



    public static final ResourceKey<ConfiguredFeature<?, ?>> SECOND_BEDROCK = registerKey("second_bedrock");


    public static void bootstrap(BootstapContext<ConfiguredFeature<?, ?>> context) {
        RuleTest stoneReplaceables = new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES);
        RuleTest deepslateReplaceables = new TagMatchTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES);
        RuleTest netherrackReplaceables = new BlockMatchTest(Blocks.NETHERRACK);

        //Ores
        registerOre(context, COAL_IN_STONE_KEY, stoneReplaceables, Blocks.COAL_ORE, 17);
        registerOre(context, COPPER_IN_STONE_KEY, stoneReplaceables, Blocks.COPPER_ORE, 20);
        registerOre(context, IRON_IN_STONE_KEY, stoneReplaceables, Blocks.IRON_ORE, 10);
        registerOre(context, LAPIS_IN_STONE_KEY, stoneReplaceables, Blocks.LAPIS_ORE, 6);
        registerOre(context, REDSTONE_IN_STONE_KEY, stoneReplaceables, Blocks.REDSTONE_ORE, 8);
        registerOre(context, GOLD_IN_DEEPSLATE_KEY, deepslateReplaceables, Blocks.DEEPSLATE_GOLD_ORE, 8);
        registerOre(context, DIAMOND_IN_DEEPSLATE_KEY, deepslateReplaceables, Blocks.DEEPSLATE_DIAMOND_ORE, 6);
        registerOre(context, EMERALD_IN_DEEPSLATE_KEY, deepslateReplaceables, Blocks.DEEPSLATE_EMERALD_ORE, 3);
        registerOre(context, GOLD_IN_NETHERRACK_KEY, netherrackReplaceables, Blocks.NETHER_GOLD_ORE, 8);
        registerOre(context, GLOWSTONE_IN_NETHERRACK_KEY, netherrackReplaceables, Blocks.GLOWSTONE, 17);
        registerOre(context, QUARTZ_IN_NETHERRACK_KEY, netherrackReplaceables, Blocks.NETHER_QUARTZ_ORE, 17);
        registerOre(context, DEBRIS_IN_NETHERRACK_KEY, netherrackReplaceables, Blocks.ANCIENT_DEBRIS, 1);

        register(context, SECOND_BEDROCK, Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(Blocks.BEDROCK)));
    }


    private static void registerOre(BootstapContext<ConfiguredFeature<?, ?>> context, ResourceKey<ConfiguredFeature<?, ?>> key, RuleTest rule, Block block, int size) {
        register(context, key, Feature.ORE, new OreConfiguration(rule, block.defaultBlockState(), size));
    }



    public static ResourceKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, new ResourceLocation(MadeForAbyss.MOD_ID, name));
    }


    private static <FC extends FeatureConfiguration, F extends Feature<FC>> void register(BootstapContext<ConfiguredFeature<?, ?>> context, ResourceKey<ConfiguredFeature<?, ?>> key, F feature, FC configuration) {
        context.register(key, new ConfiguredFeature<>(feature, configuration));
    }

}
