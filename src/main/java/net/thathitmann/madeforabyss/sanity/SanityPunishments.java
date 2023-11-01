package net.thathitmann.madeforabyss.sanity;

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.event.entity.EntityTeleportEvent;

import java.util.Random;

public abstract class SanityPunishments {
    //Player max sanity is 1000

    private static final Random random = new Random();


    private static final String[] friendlyMessages = {
        ":3",
        "Wonder what's for dinner.",
        "We need to go deeper.",
    };
    private static final String[] unfriendlyMessages = {
        "Aren't you forgetting something?",
        "Hurry up.",
        "L + no diamonds + green top."

    };
    private static final String[] meanMessages = {
        "Aren't you forgetting someone?",
        "This mine is your tomb.",
        "We want out. This body is weak.",
    };
    private static final String[] hostileMessages = {
        "Log off.",
        "Get out.",
        "LEAVE.",
        "Why are you here? You aren't wanted.",
    };

    private static boolean randomChanceReadable(float percentChancePerSecond) {
        return randomChance(percentChancePerSecond / 2000);
    }
    private static boolean randomChance(float chance) {
        return random.nextFloat() <= chance;
    }

    private static void sendHallucinationMessage(ServerPlayer player, float aggression) {
        String playerNameTag = "<" + player.getName().getString() + "> ";

        if (randomChance(0.05f)) {
            playerNameTag = "<Herobrine> ";
        }
        else if (randomChance(0.05f)) {
            playerNameTag = "<Knife Guy> ";
        }
        else if (randomChance(0.05f)) {
            playerNameTag = "<Kevin> ";
        }

        String message;

        if (aggression <= 0.25) {
            message = friendlyMessages[random.nextInt(friendlyMessages.length)];
        }
        else if (aggression <= 0.5) {
            message = unfriendlyMessages[random.nextInt(unfriendlyMessages.length)];
        }
        else if (aggression <= 0.75) {
            message = meanMessages[random.nextInt(meanMessages.length)];
        }
        else {
            message = hostileMessages[random.nextInt(hostileMessages.length)];
        }










        player.sendSystemMessage(Component.literal(playerNameTag + message));
    }

    public static void InflictInsanityPunishments(ServerPlayer player, int sanity) {
        if (sanity <= 600) {
            if (randomChanceReadable(1f)) {
                player.playNotifySound(SoundEvents.STONE_STEP, SoundSource.PLAYERS, 1.0f, 1.0f);
            }
        }
        if (sanity <= 500) {
            if (randomChanceReadable(0.5f)) {
                player.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 80));
            }
        }
        if (sanity <= 400) {
            if (randomChanceReadable(0.25f)) {
                sendHallucinationMessage(player, (400f - sanity) / 400f);
            }
            if (randomChanceReadable(0.1f)) {
                player.playNotifySound(SoundEvents.CREEPER_PRIMED, SoundSource.PLAYERS, 1.0f, 1.0f);
            }
        }
        if (sanity <= 300) {
            if (randomChanceReadable(0.25f)) {
                player.teleportTo((ServerLevel)player.level(), player.getX(), player.getY(), player.getZ(), player.getYRot() + 180, player.getXRot());
            }
        }
        if (sanity <= 200) {
            if (randomChanceReadable(0.5f)) {
                player.addEffect(new MobEffectInstance(MobEffects.DARKNESS, 80));
            }
        }
        if (sanity <= 100) {
            if (randomChanceReadable(0.01f)) {
                for(int i = 0; i < 16; ++i) {
                    double d3 = player.getX() + (player.getRandom().nextDouble() - 0.5D) * 16.0D;
                    double d4 = Mth.clamp(player.getY() + (double)(player.getRandom().nextInt(16) - 8), player.level().getMinBuildHeight(), 0);
                    double d5 = player.getZ() + (player.getRandom().nextDouble() - 0.5D) * 16.0D;
                    if (player.isPassenger()) {
                        player.stopRiding();
                    }

                    Vec3 vec3 = player.position();
                    player.level().gameEvent(GameEvent.TELEPORT, vec3, GameEvent.Context.of(player));
                    EntityTeleportEvent.ChorusFruit event = ForgeEventFactory.onChorusFruitTeleport(player, d3, d4, d5);
                    if (player.randomTeleport(event.getTargetX(), event.getTargetY(), event.getTargetZ(), true)) {
                        break;
                    }
                }
            }
            if (randomChanceReadable(0.1f)) {
                player.hurt(player.level().damageSources().wither(), 6f);
            }
        }
    }
}
