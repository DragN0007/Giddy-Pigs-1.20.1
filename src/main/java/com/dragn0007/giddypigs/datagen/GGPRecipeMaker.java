package com.dragn0007.giddypigs.datagen;

import com.dragn0007.giddypigs.GiddyGuineaPigs;
import com.dragn0007.giddypigs.blocks.GGPBlocks;
import com.dragn0007.giddypigs.items.GGPItems;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;

import java.util.function.Consumer;

public class GGPRecipeMaker extends RecipeProvider implements IConditionBuilder {
    public GGPRecipeMaker(PackOutput pOutput) {
        super(pOutput);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> pFinishedRecipeConsumer) {

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Items.LEATHER)
                .requires(GGPItems.GUINEA_PIG_HIDE.get())
                .requires(GGPItems.GUINEA_PIG_HIDE.get())
                .requires(GGPItems.GUINEA_PIG_HIDE.get())
                .requires(GGPItems.GUINEA_PIG_HIDE.get())
                .unlockedBy("guinea_pig_hide", inventoryTrigger(ItemPredicate.Builder.item()
                        .of(GGPItems.GUINEA_PIG_HIDE.get()).build()))
                .save(pFinishedRecipeConsumer,  new ResourceLocation(GiddyGuineaPigs.MODID, "leather_from_guinea_pig_hide"));

        SimpleCookingRecipeBuilder.smoking(Ingredient.of(GGPItems.GUINEA_PIG.get()), RecipeCategory.MISC, GGPItems.COOKED_GUINEA_PIG.get(), 0.35F, 100)
                .unlockedBy("has_hamster", has(GGPItems.GUINEA_PIG.get())).save(pFinishedRecipeConsumer, new ResourceLocation(GiddyGuineaPigs.MODID, "cooked_guinea_pig_smoking"));
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(GGPItems.GUINEA_PIG.get()), RecipeCategory.MISC, GGPItems.COOKED_GUINEA_PIG.get(), 0.35F, 200)
                .unlockedBy("has_guinea_pig", has(GGPItems.GUINEA_PIG.get())).save(pFinishedRecipeConsumer, new ResourceLocation(GiddyGuineaPigs.MODID, "cooked_guinea_pig_smelting"));
        SimpleCookingRecipeBuilder.campfireCooking(Ingredient.of(GGPItems.GUINEA_PIG.get()), RecipeCategory.MISC, GGPItems.COOKED_GUINEA_PIG.get(), 0.35F, 600)
                .unlockedBy("has_guinea_pig", has(GGPItems.GUINEA_PIG.get())).save(pFinishedRecipeConsumer, new ResourceLocation(GiddyGuineaPigs.MODID, "cooked_guinea_pig_campfire_cooking"));




        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, GGPBlocks.ENCLOSURE_PANE.get(), 16)
                .define('A', Items.IRON_INGOT)
                .define('B', Items.GLASS_PANE)
                .pattern("BAB")
                .pattern("BBB")
                .unlockedBy("has_iron_ingot", inventoryTrigger(ItemPredicate.Builder.item()
                        .of(Items.IRON_INGOT).build()))
                .save(pFinishedRecipeConsumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, GGPBlocks.ENCLOSURE_PANE_CORNER.get(), 4)
                .define('A', Items.IRON_INGOT)
                .define('B', Items.GLASS_PANE)
                .pattern("BAB")
                .pattern("BAB")
                .unlockedBy("has_iron_ingot", inventoryTrigger(ItemPredicate.Builder.item()
                        .of(Items.IRON_INGOT).build()))
                .save(pFinishedRecipeConsumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, GGPBlocks.GUINEA_PIG_HOUSE.get())
                .define('A', Items.IRON_NUGGET)
                .define('B', Items.OAK_WOOD)
                .pattern("BBB")
                .pattern("B B")
                .pattern("A A")
                .unlockedBy("has_iron_nugget", inventoryTrigger(ItemPredicate.Builder.item()
                        .of(Items.IRON_NUGGET).build()))
                .save(pFinishedRecipeConsumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, GGPBlocks.FOOD_DISH_BLACK.get())
                .define('A', Items.IRON_NUGGET)
                .define('B', Items.BLACK_DYE)
                .define('C', Items.PUMPKIN_SEEDS)
                .pattern("C ")
                .pattern("AB")
                .unlockedBy("has_iron_nugget", inventoryTrigger(ItemPredicate.Builder.item()
                        .of(Items.IRON_NUGGET).build()))
                .save(pFinishedRecipeConsumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, GGPBlocks.FOOD_DISH_BROWN.get())
                .define('A', Items.IRON_NUGGET)
                .define('B', Items.BROWN_DYE)
                .define('C', Items.PUMPKIN_SEEDS)
                .pattern("C ")
                .pattern("AB")
                .unlockedBy("has_iron_nugget", inventoryTrigger(ItemPredicate.Builder.item()
                        .of(Items.IRON_NUGGET).build()))
                .save(pFinishedRecipeConsumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, GGPBlocks.FOOD_DISH_WHITE.get())
                .define('A', Items.IRON_NUGGET)
                .define('B', Items.WHITE_DYE)
                .define('C', Items.PUMPKIN_SEEDS)
                .pattern("C ")
                .pattern("AB")
                .unlockedBy("has_iron_nugget", inventoryTrigger(ItemPredicate.Builder.item()
                        .of(Items.IRON_NUGGET).build()))
                .save(pFinishedRecipeConsumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, GGPBlocks.WATER_BOTTLE.get())
                .define('A', Items.IRON_NUGGET)
                .define('B', Items.GLASS_PANE)
                .define('C', Items.WATER_BUCKET)
                .pattern("BCB")
                .pattern("BAB")
                .unlockedBy("has_iron_nugget", inventoryTrigger(ItemPredicate.Builder.item()
                        .of(Items.IRON_NUGGET).build()))
                .save(pFinishedRecipeConsumer);
    }

}