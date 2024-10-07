package com.dragn0007.giddypigs.entities;

import com.dragn0007.giddypigs.GiddyGuineaPigs;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class GuineaPigModel extends GeoModel<GuineaPig> {

    public enum Variant {
        ALBINO(new ResourceLocation(GiddyGuineaPigs.MODID, "textures/guinea_pig/albino.png")),
        BLACK(new ResourceLocation(GiddyGuineaPigs.MODID, "textures/guinea_pig/black.png")),
        BROWN(new ResourceLocation(GiddyGuineaPigs.MODID, "textures/guinea_pig/brown.png")),
        COCOA(new ResourceLocation(GiddyGuineaPigs.MODID, "textures/guinea_pig/cocoa.png")),
        CREAM(new ResourceLocation(GiddyGuineaPigs.MODID, "textures/guinea_pig/cream.png")),
        DARK_COCOA(new ResourceLocation(GiddyGuineaPigs.MODID, "textures/guinea_pig/dark_cocoa.png")),
        DARK_RED(new ResourceLocation(GiddyGuineaPigs.MODID, "textures/guinea_pig/dark_red.png")),
        GOLD(new ResourceLocation(GiddyGuineaPigs.MODID, "textures/guinea_pig/gold.png")),
        GREY(new ResourceLocation(GiddyGuineaPigs.MODID, "textures/guinea_pig/grey.png")),
        RED(new ResourceLocation(GiddyGuineaPigs.MODID, "textures/guinea_pig/red.png")),
        SLATE(new ResourceLocation(GiddyGuineaPigs.MODID, "textures/guinea_pig/slate.png")),
        WHITE(new ResourceLocation(GiddyGuineaPigs.MODID, "textures/guinea_pig/white.png")),

        ALBINO_FLUFFY(new ResourceLocation(GiddyGuineaPigs.MODID, "textures/guinea_pig/albino_fluffy.png")),
        BLACK_FLUFFY(new ResourceLocation(GiddyGuineaPigs.MODID, "textures/guinea_pig/black_fluffy.png")),
        BROWN_FLUFFY(new ResourceLocation(GiddyGuineaPigs.MODID, "textures/guinea_pig/brown_fluffy.png")),
        COCOA_FLUFFY(new ResourceLocation(GiddyGuineaPigs.MODID, "textures/guinea_pig/cocoa_fluffy.png")),
        CREAM_FLUFFY(new ResourceLocation(GiddyGuineaPigs.MODID, "textures/guinea_pig/cream_fluffy.png")),
        DARK_COCOA_FLUFFY(new ResourceLocation(GiddyGuineaPigs.MODID, "textures/guinea_pig/dark_cocoa_fluffy.png")),
        DARK_RED_FLUFFY(new ResourceLocation(GiddyGuineaPigs.MODID, "textures/guinea_pig/dark_red_fluffy.png")),
        GOLD_FLUFFY(new ResourceLocation(GiddyGuineaPigs.MODID, "textures/guinea_pig/gold_fluffy.png")),
        GREY_FLUFFY(new ResourceLocation(GiddyGuineaPigs.MODID, "textures/guinea_pig/grey_fluffy.png")),
        RED_FLUFFY(new ResourceLocation(GiddyGuineaPigs.MODID, "textures/guinea_pig/red_fluffy.png")),
        SLATE_FLUFFY(new ResourceLocation(GiddyGuineaPigs.MODID, "textures/guinea_pig/slate_fluffy.png")),
        WHITE_FLUFFY(new ResourceLocation(GiddyGuineaPigs.MODID, "textures/guinea_pig/white_fluffy.png"));

        public final ResourceLocation resourceLocation;
        Variant(ResourceLocation resourceLocation) {
            this.resourceLocation = resourceLocation;
        }

        public static Variant variantFromOrdinal(int variant) { return Variant.values()[variant % Variant.values().length];
        }
    }

    public static final ResourceLocation MODEL = new ResourceLocation(GiddyGuineaPigs.MODID, "geo/guinea_pig.geo.json");
    public static final ResourceLocation ANIMATION = new ResourceLocation(GiddyGuineaPigs.MODID, "animations/guinea_pig.animation.json");
    public static final ResourceLocation BABY_TEXTURE = new ResourceLocation(GiddyGuineaPigs.MODID, "textures/guinea_pig/baby/baby.png");

    @Override
    public ResourceLocation getModelResource(GuineaPig object) {
        return MODEL;
    }

    @Override
    public ResourceLocation getTextureResource(GuineaPig object) {
        if(object.isBaby())
            return BABY_TEXTURE;
        return object.getTextureLocation();
    }

    @Override
    public ResourceLocation getAnimationResource(GuineaPig animatable) {
        return ANIMATION;
    }
}

