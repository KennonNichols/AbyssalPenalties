package net.thathitmann.madeforabyss.entity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.thathitmann.madeforabyss.MadeForAbyss;
import net.thathitmann.madeforabyss.entity.entities.DeepslateGolemEntity;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, MadeForAbyss.MOD_ID);


    public static final RegistryObject<EntityType<DeepslateGolemEntity>> DEEPSLATE_GOLEM =
            ENTITY_TYPES.register("deepslate_golem", () -> EntityType.Builder.of(DeepslateGolemEntity::new, MobCategory.MONSTER).sized(1f,1f).build("deepslate_golem"));



    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}
