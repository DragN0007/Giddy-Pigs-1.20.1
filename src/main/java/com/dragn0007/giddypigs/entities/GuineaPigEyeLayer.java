package com.dragn0007.giddypigs.entities;

import com.dragn0007.giddypigs.GiddyGuineaPigs;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoRenderer;
import software.bernie.geckolib.renderer.layer.GeoRenderLayer;

public class GuineaPigEyeLayer extends GeoRenderLayer<GuineaPig> {
    public GuineaPigEyeLayer(GeoRenderer entityRendererIn) {
        super(entityRendererIn);
    }

    @Override
    public void render(PoseStack poseStack, GuineaPig animatable, BakedGeoModel bakedModel, RenderType renderType, MultiBufferSource bufferSource, VertexConsumer buffer, float partialTick, int packedLight, int packedOverlay) {
        RenderType renderMarkingType = RenderType.entityCutout(((GuineaPig)animatable).getEyeLocation());
        poseStack.pushPose();
        poseStack.scale(1.0f, 1.0f, 1.0f);
        poseStack.translate(0.0d, 0.0d, 0.0d);
        poseStack.popPose();
        getRenderer().reRender(getDefaultBakedModel(animatable),
                poseStack,
                bufferSource,
                animatable,
                renderMarkingType,
                bufferSource.getBuffer(renderMarkingType), partialTick, packedLight, OverlayTexture.NO_OVERLAY,
                1, 1, 1, 1);
    }

    public enum Overlay {
        BLACK(new ResourceLocation(GiddyGuineaPigs.MODID, "textures/guinea_pig/eye/black.png")),
        DARK_BROWN(new ResourceLocation(GiddyGuineaPigs.MODID, "textures/guinea_pig/eye/dark_brown.png")),
        BROWN(new ResourceLocation(GiddyGuineaPigs.MODID, "textures/guinea_pig/eye/brown.png")),
        BLUE(new ResourceLocation(GiddyGuineaPigs.MODID, "textures/guinea_pig/eye/blue.png"));

        public final ResourceLocation resourceLocation;
        Overlay(ResourceLocation resourceLocation) {
            this.resourceLocation = resourceLocation;
        }

        public static Overlay eyeFromOrdinal(int eye) { return Overlay.values()[eye % Overlay.values().length];
        }
    }

}
