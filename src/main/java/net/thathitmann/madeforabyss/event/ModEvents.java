package net.thathitmann.madeforabyss.event;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingHealEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;
import net.thathitmann.madeforabyss.MadeForAbyss;



@Mod.EventBusSubscriber(modid = MadeForAbyss.MOD_ID)
public class ModEvents {


    //Player events
    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.side == LogicalSide.SERVER) {
            Player player = event.player;
            PlayerDepthPenalties.applyDepthPenalties(player);
        }
    }


    //Prevent growth below -250
    @SubscribeEvent
    public static void onCropGrow(BlockEvent.CropGrowEvent.Pre event) {
        if (event.getPos().getY() <= -250) {
            event.getLevel().setBlock(event.getPos(), Blocks.AIR.defaultBlockState(), 3);
        }
    }



    //Suppress healing
    @SubscribeEvent
    public static void onEntityHeal(LivingHealEvent event) {
        if (event.getEntity() instanceof Player) {
            PlayerDepthPenalties.onPlayerHealEvent(event);
        }
    }



}
