package com.dragn0007.giddypigs.entities;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.renderer.DynamicGeoEntityRenderer;

public class GuineaPigRender extends DynamicGeoEntityRenderer<GuineaPig> {

    public GuineaPigRender(EntityRendererProvider.Context renderManager) {
        super(renderManager, new GuineaPigModel());
        this.addRenderLayer(new GuineaPigMarkingLayer(this));
    }

    @Override
    public void render(GuineaPig entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {

        if(entity.isBaby()) {
            poseStack.scale(0.5F, 0.5F, 0.5F);
        } else {
            poseStack.scale(1F, 1F, 1F);
        }

        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }
}


