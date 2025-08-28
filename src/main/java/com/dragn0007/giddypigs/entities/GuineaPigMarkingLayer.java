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

public class GuineaPigMarkingLayer extends GeoRenderLayer<GuineaPig> {
    public GuineaPigMarkingLayer(GeoRenderer entityRendererIn) {
        super(entityRendererIn);
    }

    @Override
    public void render(PoseStack poseStack, GuineaPig animatable, BakedGeoModel bakedModel, RenderType renderType, MultiBufferSource bufferSource, VertexConsumer buffer, float partialTick, int packedLight, int packedOverlay) {
        RenderType renderMarkingType = RenderType.entityCutout(((GuineaPig)animatable).getOverlayLocation());
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
        NONE(new ResourceLocation(GiddyGuineaPigs.MODID, "textures/guinea_pig/pattern/none.png")),
        BLACK_ALPACA(new ResourceLocation(GiddyGuineaPigs.MODID, "textures/guinea_pig/pattern/black_alpaca.png")),
        BLACK_BLOTCH(new ResourceLocation(GiddyGuineaPigs.MODID, "textures/guinea_pig/pattern/black_blotch.png")),
        BLACK_FRONT_END_ALPACA(new ResourceLocation(GiddyGuineaPigs.MODID, "textures/guinea_pig/pattern/black_front_end_alpaca.png")),
        BLACK_HALVED(new ResourceLocation(GiddyGuineaPigs.MODID, "textures/guinea_pig/pattern/black_halved.png")),
        BLACK_HALF_STRIPE(new ResourceLocation(GiddyGuineaPigs.MODID, "textures/guinea_pig/pattern/black_half_stripe.png")),
        BLACK_STRIPED(new ResourceLocation(GiddyGuineaPigs.MODID, "textures/guinea_pig/pattern/black_striped.png")),
        BLUE_ALPACA(new ResourceLocation(GiddyGuineaPigs.MODID, "textures/guinea_pig/pattern/blue_alpaca.png")),
        BLUE_BLOTCH(new ResourceLocation(GiddyGuineaPigs.MODID, "textures/guinea_pig/pattern/blue_blotch.png")),
        BLUE_FRONT_END_ALPACA(new ResourceLocation(GiddyGuineaPigs.MODID, "textures/guinea_pig/pattern/blue_front_end_alpaca.png")),
        BLUE_HALVED(new ResourceLocation(GiddyGuineaPigs.MODID, "textures/guinea_pig/pattern/blue_halved.png")),
        BLUE_HALF_STRIPE(new ResourceLocation(GiddyGuineaPigs.MODID, "textures/guinea_pig/pattern/blue_half_stripe.png")),
        BLUE_STRIPED(new ResourceLocation(GiddyGuineaPigs.MODID, "textures/guinea_pig/pattern/blue_striped.png")),
        BROWN_ALPACA(new ResourceLocation(GiddyGuineaPigs.MODID, "textures/guinea_pig/pattern/brown_alpaca.png")),
        BROWN_BLOTCH(new ResourceLocation(GiddyGuineaPigs.MODID, "textures/guinea_pig/pattern/brown_blotch.png")),
        BROWN_FRONT_END_ALPACA(new ResourceLocation(GiddyGuineaPigs.MODID, "textures/guinea_pig/pattern/brown_front_end_alpaca.png")),
        BROWN_HALVED(new ResourceLocation(GiddyGuineaPigs.MODID, "textures/guinea_pig/pattern/brown_halved.png")),
        BROWN_HALF_STRIPE(new ResourceLocation(GiddyGuineaPigs.MODID, "textures/guinea_pig/pattern/brown_half_stripe.png")),
        BROWN_STRIPED(new ResourceLocation(GiddyGuineaPigs.MODID, "textures/guinea_pig/pattern/brown_striped.png")),
        CHOCOLATE_ALPACA(new ResourceLocation(GiddyGuineaPigs.MODID, "textures/guinea_pig/pattern/chocolate_alpaca.png")),
        CHOCOLATE_BLOTCH(new ResourceLocation(GiddyGuineaPigs.MODID, "textures/guinea_pig/pattern/chocolate_blotch.png")),
        CHOCOLATE_FRONT_END_ALPACA(new ResourceLocation(GiddyGuineaPigs.MODID, "textures/guinea_pig/pattern/chocolate_front_end_alpaca.png")),
        CHOCOLATE_HALVED(new ResourceLocation(GiddyGuineaPigs.MODID, "textures/guinea_pig/pattern/chocolate_halved.png")),
        CHOCOLATE_HALF_STRIPE(new ResourceLocation(GiddyGuineaPigs.MODID, "textures/guinea_pig/pattern/chocolate_half_stripe.png")),
        CHOCOLATE_STRIPED(new ResourceLocation(GiddyGuineaPigs.MODID, "textures/guinea_pig/pattern/chocolate_striped.png")),
        CREAM_ALPACA(new ResourceLocation(GiddyGuineaPigs.MODID, "textures/guinea_pig/pattern/cream_alpaca.png")),
        CREAM_BLOTCH(new ResourceLocation(GiddyGuineaPigs.MODID, "textures/guinea_pig/pattern/cream_blotch.png")),
        CREAM_FRONT_END_ALPACA(new ResourceLocation(GiddyGuineaPigs.MODID, "textures/guinea_pig/pattern/cream_front_end_alpaca.png")),
        CREAM_HALVED(new ResourceLocation(GiddyGuineaPigs.MODID, "textures/guinea_pig/pattern/cream_halved.png")),
        CREAM_HALF_STRIPE(new ResourceLocation(GiddyGuineaPigs.MODID, "textures/guinea_pig/pattern/cream_half_stripe.png")),
        CREAM_STRIPED(new ResourceLocation(GiddyGuineaPigs.MODID, "textures/guinea_pig/pattern/cream_striped.png")),
        LILAC_ALPACA(new ResourceLocation(GiddyGuineaPigs.MODID, "textures/guinea_pig/pattern/lilac_alpaca.png")),
        LILAC_BLOTCH(new ResourceLocation(GiddyGuineaPigs.MODID, "textures/guinea_pig/pattern/lilac_blotch.png")),
        LILAC_FRONT_END_ALPACA(new ResourceLocation(GiddyGuineaPigs.MODID, "textures/guinea_pig/pattern/lilac_front_end_alpaca.png")),
        LILAC_HALVED(new ResourceLocation(GiddyGuineaPigs.MODID, "textures/guinea_pig/pattern/lilac_halved.png")),
        LILAC_HALF_STRIPE(new ResourceLocation(GiddyGuineaPigs.MODID, "textures/guinea_pig/pattern/lilac_half_stripe.png")),
        LILAC_STRIPED(new ResourceLocation(GiddyGuineaPigs.MODID, "textures/guinea_pig/pattern/lilac_striped.png")),
        MAHOGANY_ALPACA(new ResourceLocation(GiddyGuineaPigs.MODID, "textures/guinea_pig/pattern/mahogany_alpaca.png")),
        MAHOGANY_BLOTCH(new ResourceLocation(GiddyGuineaPigs.MODID, "textures/guinea_pig/pattern/mahogany_blotch.png")),
        MAHOGANY_FRONT_END_ALPACA(new ResourceLocation(GiddyGuineaPigs.MODID, "textures/guinea_pig/pattern/mahogany_front_end_alpaca.png")),
        MAHOGANY_HALVED(new ResourceLocation(GiddyGuineaPigs.MODID, "textures/guinea_pig/pattern/mahogany_halved.png")),
        MAHOGANY_HALF_STRIPE(new ResourceLocation(GiddyGuineaPigs.MODID, "textures/guinea_pig/pattern/mahogany_half_stripe.png")),
        MAHOGANY_STRIPED(new ResourceLocation(GiddyGuineaPigs.MODID, "textures/guinea_pig/pattern/mahogany_striped.png")),
        RED_ALPACA(new ResourceLocation(GiddyGuineaPigs.MODID, "textures/guinea_pig/pattern/red_alpaca.png")),
        RED_BLOTCH(new ResourceLocation(GiddyGuineaPigs.MODID, "textures/guinea_pig/pattern/red_blotch.png")),
        RED_FRONT_END_ALPACA(new ResourceLocation(GiddyGuineaPigs.MODID, "textures/guinea_pig/pattern/red_front_end_alpaca.png")),
        RED_HALVED(new ResourceLocation(GiddyGuineaPigs.MODID, "textures/guinea_pig/pattern/red_halved.png")),
        RED_HALF_STRIPE(new ResourceLocation(GiddyGuineaPigs.MODID, "textures/guinea_pig/pattern/red_half_stripe.png")),
        RED_STRIPED(new ResourceLocation(GiddyGuineaPigs.MODID, "textures/guinea_pig/pattern/red_striped.png")),
        SILVER_ALPACA(new ResourceLocation(GiddyGuineaPigs.MODID, "textures/guinea_pig/pattern/silver_alpaca.png")),
        SILVER_BLOTCH(new ResourceLocation(GiddyGuineaPigs.MODID, "textures/guinea_pig/pattern/silver_blotch.png")),
        SILVER_FRONT_END_ALPACA(new ResourceLocation(GiddyGuineaPigs.MODID, "textures/guinea_pig/pattern/silver_front_end_alpaca.png")),
        SILVER_HALVED(new ResourceLocation(GiddyGuineaPigs.MODID, "textures/guinea_pig/pattern/silver_halved.png")),
        SILVER_HALF_STRIPE(new ResourceLocation(GiddyGuineaPigs.MODID, "textures/guinea_pig/pattern/silver_half_stripe.png")),
        SILVER_STRIPED(new ResourceLocation(GiddyGuineaPigs.MODID, "textures/guinea_pig/pattern/silver_striped.png")),
        TAN_ALPACA(new ResourceLocation(GiddyGuineaPigs.MODID, "textures/guinea_pig/pattern/tan_alpaca.png")),
        TAN_BLOTCH(new ResourceLocation(GiddyGuineaPigs.MODID, "textures/guinea_pig/pattern/tan_blotch.png")),
        TAN_FRONT_END_ALPACA(new ResourceLocation(GiddyGuineaPigs.MODID, "textures/guinea_pig/pattern/tan_front_end_alpaca.png")),
        TAN_HALVED(new ResourceLocation(GiddyGuineaPigs.MODID, "textures/guinea_pig/pattern/tan_halved.png")),
        TAN_HALF_STRIPE(new ResourceLocation(GiddyGuineaPigs.MODID, "textures/guinea_pig/pattern/tan_half_stripe.png")),
        TAN_STRIPED(new ResourceLocation(GiddyGuineaPigs.MODID, "textures/guinea_pig/pattern/tan_striped.png")),
        WHITE_ALPACA(new ResourceLocation(GiddyGuineaPigs.MODID, "textures/guinea_pig/pattern/white_alpaca.png")),
        WHITE_BLOTCH(new ResourceLocation(GiddyGuineaPigs.MODID, "textures/guinea_pig/pattern/white_blotch.png")),
        WHITE_FRONT_END_ALPACA(new ResourceLocation(GiddyGuineaPigs.MODID, "textures/guinea_pig/pattern/white_front_end_alpaca.png")),
        WHITE_HALVED(new ResourceLocation(GiddyGuineaPigs.MODID, "textures/guinea_pig/pattern/white_halved.png")),
        WHITE_HALF_STRIPE(new ResourceLocation(GiddyGuineaPigs.MODID, "textures/guinea_pig/pattern/white_half_stripe.png")),
        WHITE_STRIPED(new ResourceLocation(GiddyGuineaPigs.MODID, "textures/guinea_pig/pattern/white_striped.png")),
        BEE(new ResourceLocation(GiddyGuineaPigs.MODID, "textures/guinea_pig/pattern/bee.png")),
        CALICO(new ResourceLocation(GiddyGuineaPigs.MODID, "textures/guinea_pig/pattern/calico.png")),
        DALMATION(new ResourceLocation(GiddyGuineaPigs.MODID, "textures/guinea_pig/pattern/dalmation.png")),
        HEAD_SPOT(new ResourceLocation(GiddyGuineaPigs.MODID, "textures/guinea_pig/pattern/head_spot.png")),
        PERUVIAN(new ResourceLocation(GiddyGuineaPigs.MODID, "textures/guinea_pig/pattern/peruvian.png")),
        RED_BLACK(new ResourceLocation(GiddyGuineaPigs.MODID, "textures/guinea_pig/pattern/red_black.png")),
        TRICOLORED(new ResourceLocation(GiddyGuineaPigs.MODID, "textures/guinea_pig/pattern/tricolored.png")),
        PURE_WHITE(new ResourceLocation(GiddyGuineaPigs.MODID, "textures/guinea_pig/pattern/pure_white.png"));

        public final ResourceLocation resourceLocation;
        Overlay(ResourceLocation resourceLocation) {
            this.resourceLocation = resourceLocation;
        }

        public static Overlay overlayFromOrdinal(int overlay) { return Overlay.values()[overlay % Overlay.values().length];
        }
    }

}
