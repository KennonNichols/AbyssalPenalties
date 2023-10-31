package net.thathitmann.madeforabyss.entity.client;


import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.world.entity.Entity;
import net.thathitmann.madeforabyss.entity.animations.ModAnimationDefinitions;
import net.thathitmann.madeforabyss.entity.entities.DeepslateGolemEntity;

public class DeepslateGolemModel<T extends Entity> extends HierarchicalModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	private final ModelPart root;

	public DeepslateGolemModel(ModelPart root) {this.root = root.getChild("root");}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition right_leg = root.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(0, 0).addBox(-10.0F, -2.0F, -2.0F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -4.0F, 0.0F));

		PartDefinition left_leg = root.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(0, 20).addBox(6.0F, -2.0F, -2.0F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -4.0F, 0.0F));

		PartDefinition jaw = root.addOrReplaceChild("jaw", CubeListBuilder.create().texOffs(46, 40).addBox(-7.0F, 2.0F, -8.25F, 14.0F, 7.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -12.0F, 0.0F));

		PartDefinition head = root.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 20).addBox(-8.0F, 4.0F, -8.0F, 16.0F, 4.0F, 16.0F, new CubeDeformation(0.0F))
				.texOffs(0, 0).addBox(-8.0F, -8.0F, -8.0F, 16.0F, 4.0F, 16.0F, new CubeDeformation(0.0F))
				.texOffs(48, 0).addBox(4.0F, -4.0F, -8.0F, 4.0F, 8.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(0, 40).addBox(-8.0F, -4.0F, -8.0F, 4.0F, 8.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(0, 40).addBox(-8.0F, -4.0F, -6.0F, 16.0F, 8.0F, 14.0F, new CubeDeformation(0.0F))
				.texOffs(48, 10).addBox(1.0F, -1.0F, -7.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(8, 31).addBox(-3.0F, -1.0F, -7.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -12.0F, 0.0F));

		PartDefinition cube_r1 = head.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(0, 30).addBox(-1.5F, 0.2F, -1.0F, 3.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.5F, -1.5F, -6.1F, 0.0F, 0.0F, -0.3927F));

		PartDefinition cube_r2 = head.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(0, 33).addBox(-1.5F, 0.2F, -1.0F, 3.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.5F, -1.5F, -6.1F, 0.0F, 0.0F, 0.3927F));

		PartDefinition cube_r3 = head.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(0, 10).addBox(-2.0F, -2.0F, -1.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.0F, -3.0F, -6.99F, 0.0F, 0.0F, 0.7854F));

		PartDefinition cube_r4 = head.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(8, 10).addBox(0.0F, -2.0F, -1.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.0F, -3.0F, -6.99F, 0.0F, 0.0F, -0.7854F));

		return LayerDefinition.create(meshdefinition, 128, 128);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		root.getAllParts().forEach(ModelPart::resetPose);

		this.animateWalk(ModAnimationDefinitions.GOLEM_WALK, limbSwing, limbSwingAmount, 2f, 2.5f);
		this.animate(((DeepslateGolemEntity)entity).idleAnimationState, ModAnimationDefinitions.GOLEM_IDLE, ageInTicks, 1f);
	}


	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		root.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	@Override
	public ModelPart root() {
		return root;
	}
}