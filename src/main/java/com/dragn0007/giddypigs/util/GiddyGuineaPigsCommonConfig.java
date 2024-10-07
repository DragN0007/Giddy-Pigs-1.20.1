package com.dragn0007.giddypigs.util;

import net.minecraftforge.common.ForgeConfigSpec;

public class GiddyGuineaPigsCommonConfig {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;

    public static final ForgeConfigSpec.ConfigValue<Integer> GUINEA_PIG_WEIGHT;


    static {
        BUILDER.push("Configs for Giddy Guinea Pigs!");

        GUINEA_PIG_WEIGHT = BUILDER.comment("How often should guinea pigs spawn? Default is 4.")
                .define("Guinea Pig Spawn Weight", 4);

        BUILDER.pop();
        SPEC = BUILDER.build();
    }
}
