package net.thathitmann.madeforabyss.event;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingHealEvent;

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




        //  yLevel <= -250

        if (yLevel <= -350) {belowMinus350DepthPenalty(player);}

        if (yLevel <= -500) {belowMinus500DepthPenalty(player);}

    }





    private static void belowMinus0DepthPenalty(Player player) {

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
