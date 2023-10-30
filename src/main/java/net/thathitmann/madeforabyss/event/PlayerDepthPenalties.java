package net.thathitmann.madeforabyss.event;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingHealEvent;
import net.thathitmann.madeforabyss.networking.ModMessages;
import net.thathitmann.madeforabyss.networking.packet.SanityDataSyncS2CPacket;
import net.thathitmann.madeforabyss.sanity.PlayerSanity;
import net.thathitmann.madeforabyss.sanity.PlayerSanityProvider;

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



    /**
     * Material names:
     * minecraft:copper
     * minecraft:diamond
     * minecraft:netherite
     * et.c.
     * */
    public static Boolean isPlayerWearingAllTrimmedArmorOfMaterialType(Player player, String... materialNames) {
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



    public static void applyDepthPenalties(Player player) {
        //isPlayerWearingAllTrimmedArmorOfMaterialType("minecraft:copper", player);

        int yLevel = player.getBlockY();







        if (yLevel >= 0   ) {above0DepthBoon(player);}
        //======================================================================
        if (yLevel <= 0   ) {below0DepthPenalty(player);}

        //  yLevel <= -250 Handled in CropGrow event and EntityHealed event

        if (yLevel <= -350) {belowMinus350DepthPenalty(player);}

        if (yLevel <= -500) {belowMinus500DepthPenalty(player);}

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

    }
    private static void belowMinus100DepthPenalty(Player player) {

    }
    private static void belowMinus150DepthPenalty(Player player) {

    }
    private static void belowMinus200DepthPenalty(Player player) {

    }



    private static void belowMinus350DepthPenalty(Player player) {
        if (!isPlayerWearingAllTrimmedArmorOfMaterialType(player, "minecraft:copper", "minecraft:netherite")) {
            player.hurt(player.level().damageSources().cramming(), 0.5f);
        }
    }






    private static void belowMinus500DepthPenalty(Player player) {
        if (!isPlayerWearingAllTrimmedArmorOfMaterialType(player, "minecraft:netherite")) {
            player.hurt(player.level().damageSources().cramming(), 1000f);
        }
    }








}
