package com.dragn0007.giddypigs.entities;

import com.dragn0007.giddypigs.GiddyGuineaPigs;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class GuineaPigModel extends DefaultedEntityGeoModel<GuineaPig> {

    public GuineaPigModel() {
        super(new ResourceLocation(GiddyGuineaPigs.MODID, "guinea_pig"), true);
    }

    @Override
    public void setCustomAnimations(GuineaPig animatable, long instanceId, AnimationState<GuineaPig> animationState) {

        CoreGeoBone head = getAnimationProcessor().getBone("head");

        if (head != null) {
            EntityModelData entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);
            head.setRotX(head.getRotX() + (entityData.headPitch() * Mth.DEG_TO_RAD));
            float maxYaw = Mth.clamp(entityData.netHeadYaw(), -25.0f, 25.0f);
            head.setRotY(head.getRotY() + (maxYaw * Mth.DEG_TO_RAD));
        }
    }

    public enum Variant {
        BLACK(new ResourceLocation(GiddyGuineaPigs.MODID, "textures/guinea_pig/black.png")),
        BLUE(new ResourceLocation(GiddyGuineaPigs.MODID, "textures/guinea_pig/blue.png")),
        BROWN(new ResourceLocation(GiddyGuineaPigs.MODID, "textures/guinea_pig/brown.png")),
        CHOCOLATE(new ResourceLocation(GiddyGuineaPigs.MODID, "textures/guinea_pig/chocolate.png")),
        CREAM(new ResourceLocation(GiddyGuineaPigs.MODID, "textures/guinea_pig/cream.png")),
        GOLD(new ResourceLocation(GiddyGuineaPigs.MODID, "textures/guinea_pig/gold.png")),
        LILAC(new ResourceLocation(GiddyGuineaPigs.MODID, "textures/guinea_pig/lilac.png")),
        MAHOGANY(new ResourceLocation(GiddyGuineaPigs.MODID, "textures/guinea_pig/mahogany.png")),
        RED(new ResourceLocation(GiddyGuineaPigs.MODID, "textures/guinea_pig/red.png")),
        SILVER(new ResourceLocation(GiddyGuineaPigs.MODID, "textures/guinea_pig/silver.png")),
        TAN(new ResourceLocation(GiddyGuineaPigs.MODID, "textures/guinea_pig/tan.png")),
        WHITE(new ResourceLocation(GiddyGuineaPigs.MODID, "textures/guinea_pig/white.png")),
        HAIRLESS(new ResourceLocation(GiddyGuineaPigs.MODID, "textures/guinea_pig/hairless.png"));

        public final ResourceLocation resourceLocation;
        Variant(ResourceLocation resourceLocation) {
            this.resourceLocation = resourceLocation;
        }

        public static Variant variantFromOrdinal(int variant) { return Variant.values()[variant % Variant.values().length];
        }
    }

    public static final ResourceLocation ANIMATION = new ResourceLocation(GiddyGuineaPigs.MODID, "animations/guinea_pig.animation.json");

    @Override
    public ResourceLocation getModelResource(GuineaPig object) {
        return GuineaPig.Breed.breedFromOrdinal(object.getBreed()).resourceLocation;
    }

    @Override
    public ResourceLocation getTextureResource(GuineaPig object) {
        return object.getTextureLocation();
    }

    @Override
    public ResourceLocation getAnimationResource(GuineaPig animatable) {
        return ANIMATION;
    }
}

