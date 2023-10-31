package net.thathitmann.madeforabyss.entity.client;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.thathitmann.madeforabyss.MadeForAbyss;
import net.thathitmann.madeforabyss.entity.entities.DeepslateGolemEntity;

public class DeepslateGolemRenderer extends MobRenderer<DeepslateGolemEntity, DeepslateGolemModel<DeepslateGolemEntity>> {
    public DeepslateGolemRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new DeepslateGolemModel<>(pContext.bakeLayer(ModModelLayers.DEEPSLATE_GOLEM_LAYER)), 0.5f);
    }

    @Override
    public ResourceLocation getTextureLocation(DeepslateGolemEntity pEntity) {
        return new ResourceLocation(MadeForAbyss.MOD_ID, "textures/entity/deepslate_golem.png");
    }
}
