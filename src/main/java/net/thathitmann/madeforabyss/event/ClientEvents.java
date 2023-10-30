package net.thathitmann.madeforabyss.event;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.thathitmann.madeforabyss.MadeForAbyss;
import net.thathitmann.madeforabyss.client.SanityHUDOverlay;

public class ClientEvents {
    @Mod.EventBusSubscriber(modid = MadeForAbyss.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientModBusEvents {
        @SubscribeEvent
        public static void registerGuiOverlays(RegisterGuiOverlaysEvent event) {
            event.registerAboveAll("sanity", SanityHUDOverlay.HUD_SANITY);
        }
    }
}
