package net.thathitmann.madeforabyss.event;

import net.minecraft.core.RegistryAccess;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DataPackRegistryEvent;
import net.thathitmann.madeforabyss.MadeForAbyss;
import net.thathitmann.madeforabyss.entity.ModEntities;
import net.thathitmann.madeforabyss.entity.entities.DeepslateGolemEntity;
import org.jetbrains.annotations.NotNull;

@Mod.EventBusSubscriber(modid = MadeForAbyss.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ServerEvents {
    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(ModEntities.DEEPSLATE_GOLEM.get(), DeepslateGolemEntity.createAttributes().build());
    }


}
