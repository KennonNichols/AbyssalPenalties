package net.thathitmann.madeforabyss.event;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.ServerStatsCounter;
import net.minecraft.stats.Stats;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageSources;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.event.entity.living.LivingHealEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.thathitmann.madeforabyss.item.GasMask;
import net.thathitmann.madeforabyss.networking.ModMessages;
import net.thathitmann.madeforabyss.networking.packet.SanityDataSyncS2CPacket;
import net.thathitmann.madeforabyss.sanity.PlayerSanity;
import net.thathitmann.madeforabyss.sanity.PlayerSanityProvider;
import org.jetbrains.annotations.NotNull;

import static net.thathitmann.madeforabyss.MadeForAbyss.toxinImmunityTag;

public abstract class PlayerDepthPenalties {


    //private static DamageSource ambientDamageSource = new DamageSource("bypasses_armor");



    public static void onPlayerHealEvent(LivingHealEvent event) {
        Player player = (Player)event.getEntity();
        if (player.getBlockY() <= -250) {
            //Suppress small healing
            if (event.getAmount() <= 1.0f) {
                player.setHealth(player.getHealth() - event.getAmount());
            }
        }
    }



    private static void evaporateBlockAtPostion(BlockPos pos, @NotNull Level level) {
        Block block = level.getBlockState(pos).getBlock();

        if (block == Blocks.WATER) {
            level.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
        }
        else if (block == Blocks.WATER_CAULDRON) {
            level.setBlock(pos, Blocks.CAULDRON.defaultBlockState(), 3);
        }
    }



    /**
     * Material names:
     * minecraft:copper
     * minecraft:diamond
     * minecraft:netherite
     * et.c.
     * */
    private static Boolean isPlayerWearingAllTrimmedArmorOfMaterialType(Player player, String... materialNames) {
        int hitCount = 0;
        for (ItemStack itemStack : player.getArmorSlots()) {
            CompoundTag tag = itemStack.getTag();
            if (tag != null) {
                tag = tag.getCompound("Trim");
                if (tag != null) {
                    String material = tag.getString("material");
                    for (String materialName : materialNames) {
                        if (material.equals(materialName)) {
                            hitCount += 1;
                        }
                    }
                }
            }
        }
        return hitCount >= 4;
    }

    private static Boolean isPlayerWearingAllNetheriteArmor(Player player) {
        for (ItemStack itemStack : player.getArmorSlots()) {
            Item item = itemStack.getItem();
            if (item != Items.NETHERITE_BOOTS && item != Items.NETHERITE_LEGGINGS && item != Items.NETHERITE_CHESTPLATE && item != Items.NETHERITE_HELMET) {
                return false;
            }
        }
        return true;
    }

    private static ItemStack isPlayerWearingGasMask(Player player) {

        for (ItemStack itemStack : player.getArmorSlots()) {
            if (itemStack.getItem() instanceof GasMask) {
                return itemStack;
            }
        }
        return null;
    }


    public static void applyDepthPenalties(Player player) {
        //isPlayerWearingAllTrimmedArmorOfMaterialType("minecraft:copper", player);

        int yLevel = player.getBlockY();



        anyHeightPenalty(player);



        if (yLevel >= 0   ) {above0DepthBoon(player);}
        //======================================================================
        if (yLevel <= 0   ) {below0DepthPenalty(player);}
        if (yLevel <= -50 ) {belowMinus50DepthPenalty(player);}
        //  yLevel <= -100 Handled in PlayerSleepInBed event
        if (yLevel <= -150) {belowMinus150DepthPenalty(player);}
        if (yLevel <= -200) {belowMinus200DepthPenalty(player);}

        //  yLevel <= -250 Handled in CropGrow event and EntityHealed event

        if (yLevel <= -350) {belowMinus350DepthPenalty(player);}

        if (yLevel <= -450) {belowMinus450DepthPenalty(player);}
        if (yLevel <= -500) {belowMinus500DepthPenalty(player);}

    }


    private static void anyHeightPenalty(Player player) {
        if (((ServerPlayer)player).getStats().getValue(Stats.CUSTOM.get(Stats.TIME_SINCE_REST)) >= 48000) {
            //If player not fatigued
            if (!player.hasEffect(MobEffects.DIG_SLOWDOWN)) {
                player.addEffect(new MobEffectInstance(MobEffects.DIG_SLOWDOWN, 200, 2));
            }
        }
    }


    private static void above0DepthBoon(Player player) {
        //BlockPos blockPos = player.blockPosition();
        player.getCapability(PlayerSanityProvider.PLAYER_SANITY).ifPresent(sanity -> {
            if (sanity.getSanity() < PlayerSanity.getMaxSanity() && player.getRandom().nextFloat() < 0.5f) {
                sanity.addSanity(1);
                ModMessages.sendToPlayer(new SanityDataSyncS2CPacket(sanity.getSanity()), (ServerPlayer)player);
            }
        });

    }
    private static void below0DepthPenalty(Player player) {
        BlockPos blockPos = player.blockPosition();
        int lightLevel = player.level().getRawBrightness(blockPos, 0);
            player.getCapability(PlayerSanityProvider.PLAYER_SANITY).ifPresent(sanity -> {
                if (lightLevel <= 0) {
                    if (sanity.getSanity() > 0 && player.getRandom().nextFloat() < 0.1f) {
                        sanity.removeSanity(1);
                        ModMessages.sendToPlayer(new SanityDataSyncS2CPacket(sanity.getSanity()), (ServerPlayer)player);
                    }
                }
                else if (lightLevel >= 8) {
                    if (sanity.getSanity() < PlayerSanity.getMaxSanity() && player.getRandom().nextFloat() < 0.02f) {
                        sanity.addSanity(1);
                        ModMessages.sendToPlayer(new SanityDataSyncS2CPacket(sanity.getSanity()), (ServerPlayer)player);
                    }
                } else {
                    sanity.setSanityModToNeutral();
                }
            });
    }
    private static void belowMinus50DepthPenalty(Player player) {
        if (player.getRandom().nextFloat() <= 0.001f) {
            player.causeFoodExhaustion(1);
        }
    }
    //-100
    private static void belowMinus150DepthPenalty(Player player) {
        if (!player.hasEffect(MobEffects.WEAKNESS)) {
            //Inflict weakness
            player.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 1200));
        }
    }
    private static void belowMinus200DepthPenalty(Player player) {


        ItemStack itemStack = isPlayerWearingGasMask(player);

        //If player has a toxin immunity amulet, do nothing
        if (player.getInventory().contains(toxinImmunityTag)) {

        }
        //If the player is wearing a gas mask
        else if (itemStack != null) {
            int durability = itemStack.getMaxDamage() - itemStack.getTag().getInt("Damage");


            //If it's still got durability
            if (durability > 1) {
                if (player.getRandom().nextFloat() <= 0.005f) {
                    if (durability == 10) {
                        player.sendSystemMessage(Component.literal("Your gas mask is low on durability!").withStyle(ChatFormatting.YELLOW).withStyle(ChatFormatting.BOLD));
                    }
                    itemStack.getTag().putInt("Damage", itemStack.getTag().getInt("Damage") + 1);
                }
            } else {
                //If player not withering
                if (!player.hasEffect(MobEffects.WITHER)) {
                    //Inflict wither
                    player.sendSystemMessage(Component.literal("Toxic gas burns your lungs!").withStyle(ChatFormatting.RED).withStyle(ChatFormatting.BOLD));
                    player.addEffect(new MobEffectInstance(MobEffects.WITHER, 200, 2));
                }
            }
        }
        //Otherwise
        else {
            //If player not withering
            if (!player.hasEffect(MobEffects.WITHER)) {
                //Inflict wither
                player.sendSystemMessage(Component.literal("Toxic gas burns your lungs!").withStyle(ChatFormatting.RED).withStyle(ChatFormatting.BOLD));
                player.addEffect(new MobEffectInstance(MobEffects.WITHER, 200, 2));
            }
        }





    }
    //-250
    private static void belowMinus300DepthPenalty(Player player) {

    }

    private static void belowMinus350DepthPenalty(Player player) {
        if (!isPlayerWearingAllTrimmedArmorOfMaterialType(player, "minecraft:copper", "minecraft:netherite")) {
            player.hurt(player.level().damageSources().cramming(), 0.5f);
        }
    }


    private static void belowMinus400DepthPenalty(Player player) {

    }

    private static void belowMinus450DepthPenalty(Player player) {
        if (!player.isOnFire() && !player.hasEffect(MobEffects.FIRE_RESISTANCE) && !isPlayerWearingAllNetheriteArmor(player)) {

            BlockPos blockPosition = player.blockPosition();
            evaporateBlockAtPostion(blockPosition, player.level());
            evaporateBlockAtPostion(blockPosition.offset(0, 1, 0), player.level());
            player.setSecondsOnFire(1);
        }
    }



    private static void belowMinus500DepthPenalty(Player player) {
        if (!isPlayerWearingAllTrimmedArmorOfMaterialType(player, "minecraft:netherite")) {
            player.hurt(player.level().damageSources().cramming(), 1000f);
        }
    }








}
