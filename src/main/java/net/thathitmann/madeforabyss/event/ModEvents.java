package net.thathitmann.madeforabyss.event;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.monster.MagmaCube;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingHealEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerSleepInBedEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;
import net.thathitmann.madeforabyss.MadeForAbyss;
import net.thathitmann.madeforabyss.entity.ModEntities;
import net.thathitmann.madeforabyss.networking.ModMessages;
import net.thathitmann.madeforabyss.networking.packet.SanityDataSyncS2CPacket;
import net.thathitmann.madeforabyss.sanity.PlayerSanity;
import net.thathitmann.madeforabyss.sanity.PlayerSanityProvider;


@Mod.EventBusSubscriber(modid = MadeForAbyss.MOD_ID)
public class ModEvents {



    //Player events
    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.side == LogicalSide.SERVER) {
            Player player = event.player;
            if (!player.isCreative()) {
                PlayerDepthPenalties.applyDepthPenalties(player);
            }
        }
    }


    //Prevent growth below -250
    @SubscribeEvent
    public static void onCropGrow(BlockEvent.CropGrowEvent.Pre event) {
        if (event.getPos().getY() <= -250) {
            event.getLevel().setBlock(event.getPos(), Blocks.AIR.defaultBlockState(), 3);
        }
    }


    //Suppress healing below -250
    @SubscribeEvent
    public static void onEntityHeal(LivingHealEvent event) {
        if (event.getEntity() instanceof Player) {
            PlayerDepthPenalties.onPlayerHealEvent(event);
        }
    }


    //Mobs spawn with strength below -150
    @SubscribeEvent
    public static void onEntitySpawn(EntityJoinLevelEvent event) {
        if (event.getEntity() instanceof LivingEntity entity) {
            if (!(entity instanceof Player) && entity.getY() <= -150) {
                    entity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 1000000, 1));
                }
            }
        }



    //Can't sleep below -100
    @SubscribeEvent
    public static void onSleep(PlayerSleepInBedEvent event) {
        if (event.getPos().getY() <= -100) {
            event.setResult(Player.BedSleepingProblem.NOT_POSSIBLE_HERE);
            event.getEntity().sendSystemMessage(Component.literal("The depth unsettles you."));
        }
    }



    @SubscribeEvent
    public static void onBlockMined(BlockEvent.BreakEvent event) {
        BlockPos pos = event.getPos();
        if (pos.getY() <= -300) {
            if (event.getState().getBlock() == Blocks.DEEPSLATE && event.getPlayer().getRandom().nextFloat() <= (1/128)) {
                ModEntities.DEEPSLATE_GOLEM.get().spawn((ServerLevel)event.getLevel(), event.getPos(), MobSpawnType.EVENT);
            }
            else if (event.getState().getBlock() == Blocks.NETHERRACK && event.getPlayer().getRandom().nextFloat() <= (1/64)) {
                EntityType.MAGMA_CUBE.spawn((ServerLevel)event.getLevel(), event.getPos(), MobSpawnType.EVENT);
            }
        }
    }



    //Sanity system
    @SubscribeEvent
    public static void onAttachCapabilitiesPlayer(AttachCapabilitiesEvent<Entity> event) {
        if(event.getObject() instanceof Player) {
            if (!event.getObject().getCapability(PlayerSanityProvider.PLAYER_SANITY).isPresent()) {
                event.addCapability(new ResourceLocation(MadeForAbyss.MOD_ID, "properties"), new PlayerSanityProvider());
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerCloned(PlayerEvent.Clone event) {
        if(event.isWasDeath()) {
            event.getOriginal().getCapability(PlayerSanityProvider.PLAYER_SANITY).ifPresent(oldStore -> {
                event.getOriginal().getCapability(PlayerSanityProvider.PLAYER_SANITY).ifPresent(newStore -> {
                    newStore.copyFrom(oldStore);
                });
            });
        }
    }

    @SubscribeEvent
    public static void onRegisterCapabilities(RegisterCapabilitiesEvent event) {
        event.register(PlayerSanity.class);
    }

    @SubscribeEvent
    public static void onPlayerJoinWorld(EntityJoinLevelEvent event) {
        if(!event.getLevel().isClientSide()) {
            if(event.getEntity() instanceof ServerPlayer player) {
                player.getCapability(PlayerSanityProvider.PLAYER_SANITY).ifPresent(sanity -> {
                    ModMessages.sendToPlayer(new SanityDataSyncS2CPacket(sanity.getSanity()), player);
                });
            }
        }
    }





    /*
    @SubscribeEvent
    public static void onBlockPlaced(BlockEvent.EntityPlaceEvent event) {
        if (event.getPos().getY() <= -450 && event.getPlacedBlock().getBlock() == Blocks.WATER) {
            event.setCanceled(true);
        }
    }*/







}
